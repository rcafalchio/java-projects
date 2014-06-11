package br.com.tecway.gerenciadorloja.fx.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.transaction.SystemException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.business.GerenciadorVenda;
import br.com.tecway.gerenciadorloja.business.IGerenciadorVenda;
import br.com.tecway.gerenciadorloja.common.EstoqueTableVO;
import br.com.tecway.gerenciadorloja.common.FechamentoVendaVO;
import br.com.tecway.gerenciadorloja.common.ProdutoVO;
import br.com.tecway.gerenciadorloja.common.ProdutoVendaVO;
import br.com.tecway.gerenciadorloja.constants.ConstantesMensagens;
import br.com.tecway.gerenciadorloja.constants.TipoPagamentoEnum;
import br.com.tecway.gerenciadorloja.constants.TypeNumberEnum;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.fx.components.NumberTextField;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.utils.AppConstants;
import br.com.tecway.gerenciadorloja.utils.ModalLoadUtils;
import br.com.tecway.gerenciadorloja.utils.SegurancaUtils;
import br.com.tecway.gerenciadorloja.utils.TelaUtilitarios;
import br.com.tecway.gerenciadorloja.utils.TextUtils;

public class FechamentoController extends AbstractSubController<CaixaController> {

	/** Definicao de log */
	protected static final Logger LOGGER = LogManager.getLogger(FechamentoController.class);

	@FXML
	private Label totalLabel;
	@FXML
	private Label trocoLabel;
	@FXML
	private NumberTextField valorPagoText;
	@FXML
	private NumberTextField percentualDescontoText;
	@FXML
	private TextField descontoText;
	@FXML
	private ComboBox<TipoPagamentoEnum> tipoPagamentoComboBox;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tipoPagamentoComboBox.getItems().clear();
		tipoPagamentoComboBox.getItems().addAll(TipoPagamentoEnum.values());
		tipoPagamentoComboBox.getSelectionModel().select(0);
	}

	@Override
	public void customInitialize() throws ApplicationException {
		this.totalLabel.setText(getParentController().totalLabel.getText());
		descontoText.setDisable(true);
	}

	/**
	 * Fecha a compra
	 * 
	 * @throws SystemException
	 * @throws IllegalStateException
	 */
	public void fecharVenda() throws IllegalStateException, SystemException {

		ModalLoadUtils.getInstance().showModalLoad();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					final StageAlert alert = new StageAlert(AppConstants.AVISO, "Deseja fechar a venda?",
							Severity.WARN, Boolean.TRUE, AppConstants.SIM, AppConstants.NAO);
					final int response = alert.showAndWaitResponse();

					if (response == 0) {
						final IGerenciadorVenda gerenciadorVenda = new GerenciadorVenda();
						final FechamentoVendaVO fechamentoVendaVO = new FechamentoVendaVO();
						final TableView<EstoqueTableVO> caixaTableView = getParentController().caixaTableView;
						ProdutoVendaVO produtoVendaVO = null;

						for (EstoqueTableVO estoqueTableVO : caixaTableView.getItems()) {
							produtoVendaVO = new ProdutoVendaVO();
							produtoVendaVO.setProdutoVO(new ProdutoVO());
							produtoVendaVO.getProdutoVO().setCodigo(estoqueTableVO.getProdutoTableVO().getCodigo());
							produtoVendaVO.setQuantidade(estoqueTableVO.getQuantidade());
							fechamentoVendaVO.getListaProdutos().add(produtoVendaVO);
						}
						//
						fechamentoVendaVO.setPercentualDesconto(TelaUtilitarios
								.recuperarValorDoubleTextField(percentualDescontoText));
						fechamentoVendaVO.setValorBruto(TelaUtilitarios.recuperarValorDoubleLabel(totalLabel));
						fechamentoVendaVO.setVendedor(SegurancaUtils.getUsuarioEntity());
						fechamentoVendaVO.setTipoPagamentoEnum(tipoPagamentoComboBox.getSelectionModel()
								.getSelectedItem());
						fechamentoVendaVO.setValorPago(TelaUtilitarios.recuperarValorDoubleTextField(valorPagoText));
						gerenciadorVenda.fecharVenda(fechamentoVendaVO);
						final StageAlert alertS = new StageAlert(AppConstants.AVISO,
								ConstantesMensagens.MENSAGEM_GENERICA_SUCESSO, Severity.INFO, Boolean.TRUE,
								AppConstants.BOTAO_OK);
						alertS.show();
						getParentController().popupFinalizarCompra.close();
						getParentController().carregarTelaFluxoCaixa(null);
					}
				} catch (BusinessException e) {
					final StageAlert alert = new StageAlert(AppConstants.AVISO, e.getMensagemNegocio(), Severity.WARN,
							Boolean.TRUE, AppConstants.BOTAO_OK);
					alert.show();
					LOGGER.error(e.getMensagemNegocio(), e);
				} catch (Exception e) {
					ControllerException.registrarErro(LOGGER, e);
				} finally {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							ModalLoadUtils.getInstance().hideModalLoad();
						}
					});
				}
			}
		});
	}

	public void gerarTroco() {
		final Double troco = NumberTextField.converterCurrencyToDouble(valorPagoText.getText())
				- NumberTextField.converterCurrencyToDouble(totalLabel.getText());
		this.trocoLabel.setText(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, TextUtils.toString(troco)));
	}

	/**
	 * Adiciona o produto com o ENTER
	 * 
	 * @param event
	 */
	public void gerarTrocoKey(final KeyEvent event) {
		try {
			if (event.getCode().equals(KeyCode.ENTER)) {
				this.fecharVenda();
			}
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

	/**
	 * Calcula o desconto
	 */
	public void calcularValorDesconto() {
		try {
			descontoText.setText("");
			final Double valorDesconto = new BigDecimal(TelaUtilitarios.recuperarValorDoubleTextField(
					percentualDescontoText).toString())
					.multiply(new BigDecimal("0.01"))
					.multiply(
							new BigDecimal(NumberTextField.converterCurrencyToDouble(totalLabel.getText()).toString()))
					.doubleValue();
			descontoText.setText(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, valorDesconto.toString()));
			this.calcularValorTroco();
		} catch (Exception e) {
			LOGGER.error("Erro ao gerar o valor do desconto!", e);
		}
	}

	/**
	 * Calcula o troco
	 */
	public void calcularValorTroco() {
		try {
			if (descontoText == null || descontoText.getText().isEmpty()) {
				descontoText.setText(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, "0.0"));
			}
			if (totalLabel != null && valorPagoText != null && !valorPagoText.getText().isEmpty()) {
				if (valorPagoText.getText() == null || valorPagoText.getText().isEmpty()) {
					valorPagoText.setText("0");
				}
				final Double valorDesconto = NumberTextField.converterCurrencyToDouble(descontoText.getText());
				final Double valorTotal = NumberTextField.converterCurrencyToDouble(totalLabel.getText());
				Double valorTroco = valorTotal - valorDesconto;
				valorTroco = TelaUtilitarios.recuperarValorDoubleTextField(valorPagoText) - valorTroco;
				if (valorTroco >= 0.0) {
					trocoLabel.setText(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, valorTroco.toString()));
				}

			}
		} catch (Exception e) {
			LOGGER.error("Erro ao gerar o valor do troco!", e);
		}
	}
}
