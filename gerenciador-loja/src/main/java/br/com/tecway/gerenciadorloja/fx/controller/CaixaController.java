package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.business.GerenciadorEstoque;
import br.com.tecway.gerenciadorloja.business.IGerenciadorEstoque;
import br.com.tecway.gerenciadorloja.common.EstoqueTableVO;
import br.com.tecway.gerenciadorloja.common.ProdutoVO;
import br.com.tecway.gerenciadorloja.constants.ConstantesMensagens;
import br.com.tecway.gerenciadorloja.constants.TypeNumberEnum;
import br.com.tecway.gerenciadorloja.entity.ProdutoEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.exception.ConverterException;
import br.com.tecway.gerenciadorloja.fx.components.NumberTextField;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.fx.components.StagePopup;
import br.com.tecway.gerenciadorloja.utils.AppConstants;
import br.com.tecway.gerenciadorloja.utils.CaixaTelaHelper;
import br.com.tecway.gerenciadorloja.utils.ConverterUtils;
import br.com.tecway.gerenciadorloja.utils.NumberUtils;
import br.com.tecway.gerenciadorloja.utils.TelaUtilitarios;
import br.com.tecway.gerenciadorloja.utils.TextUtils;

public class CaixaController extends PrincipalController {

	private static final Logger LOGGER = LogManager.getLogger(CaixaController.class);

	@FXML
	protected TextField codigoBarrasText;

	@FXML
	protected Label totalLabel;

	@FXML
	protected TextField quantidadeText;

	@FXML
	protected TableView<EstoqueTableVO> caixaTableView;

	protected StagePopup popupFinalizarCompra = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		totalLabel.setText("R$ 0,00");
		quantidadeText.setText("1");

		quantidadeText.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(final ObservableValue<? extends String> observableValue, final String oldValue,
					final String newValue) {

				if (TelaUtilitarios.recuperarValorIntegerTextField(quantidadeText) == null
						|| TelaUtilitarios.recuperarValorIntegerTextField(quantidadeText).equals(0)) {
					quantidadeText.setText("1");
				}
			}
		});
	}

	public void adicionarProduto() {

		try {

			final IGerenciadorEstoque gerenciadorEstoque = new GerenciadorEstoque();

			if (TelaUtilitarios.recuperarValorLongTextField(codigoBarrasText) == null) {

				final StageAlert alertS = new StageAlert(AppConstants.AVISO, ConstantesMensagens.CAMPO_CODIGO_BARRAS
						+ ConstantesMensagens.MENSAGEM_CAMPO_NAO_PREENCHIDO, Severity.WARN, Boolean.TRUE,
						AppConstants.BOTAO_OK);
				alertS.show();

			} else {

				Integer quantidade = TelaUtilitarios.recuperarValorIntegerTextField(quantidadeText);

				for (EstoqueTableVO estoqueTableVO : caixaTableView.getItems()) {

					if (estoqueTableVO.getCodigoBarras().equals(
							TelaUtilitarios.recuperarValorLongTextField(codigoBarrasText))) {

						quantidade = quantidade + estoqueTableVO.getQuantidade();

					}

				}

				final ProdutoEntity produtoEntity = gerenciadorEstoque.verificarExistenciaProdutos(quantidade,
						TelaUtilitarios.recuperarValorLongTextField(codigoBarrasText));
				montarEstoqueTableVO(produtoEntity);
				somarTotal();

				codigoBarrasText.setText("");

			}

		} catch (BusinessException e) {

			final StageAlert alert = new StageAlert(AppConstants.AVISO, e.getMensagemNegocio(), Severity.WARN,
					Boolean.TRUE, AppConstants.BOTAO_OK);
			alert.show();
			LOGGER.error(e.getMensagemNegocio(), e);

		} catch (Exception e) {

			ControllerException.registrarErro(LOGGER, e);

		}

	}

	private void somarTotal() {

		Double totalFinal = new Double(0.0);

		for (EstoqueTableVO estoqueTableVO : caixaTableView.getItems()) {

			Double total = NumberTextField.converterCurrencyToDouble(estoqueTableVO.getProdutoTableVO().getPreco())
					* NumberUtils.toDouble(estoqueTableVO.getQuantidade());
			totalFinal = totalFinal + total;

		}

		totalLabel.setText(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, TextUtils.toString(totalFinal)));

	}

	private void montarEstoqueTableVO(ProdutoEntity produtoEntity) throws ConverterException {
		final List<EstoqueTableVO> listaEstoque = new ArrayList<EstoqueTableVO>();
		EstoqueTableVO estoqueTableVO = new EstoqueTableVO();
		estoqueTableVO.setCodigoBarras(produtoEntity.getCodigoBarras());

		if (caixaTableView.getItems().contains(estoqueTableVO)) {
			for (EstoqueTableVO estoqueTableVO2 : caixaTableView.getItems()) {
				if (estoqueTableVO2.getCodigoBarras().equals(produtoEntity.getCodigoBarras())) {
					estoqueTableVO = estoqueTableVO2;
					estoqueTableVO.setQuantidade(estoqueTableVO2.getQuantidade()
							+ TelaUtilitarios.recuperarValorIntegerTextField(quantidadeText));
					listaEstoque.add(estoqueTableVO);
				} else {
					listaEstoque.add(estoqueTableVO2);
				}
			}
			final ObservableList<TableColumn<EstoqueTableVO, String>> listaColunas = CaixaTelaHelper
					.montarListaColunas();

			caixaTableView.getColumns().clear();
			caixaTableView.getColumns().addAll(listaColunas);
			caixaTableView.getItems().clear();
			caixaTableView.getItems().addAll(FXCollections.observableArrayList(listaEstoque));
		} else {
			final ProdutoVO produtoVO = ConverterUtils.deProdutoEntityParaProdutoVO(produtoEntity);
			estoqueTableVO.setQuantidade(TelaUtilitarios.recuperarValorIntegerTextField(quantidadeText));
			estoqueTableVO.setProdutoTableVO(produtoVO);
			caixaTableView.getItems().add(estoqueTableVO);
		}

	}

	public void removerProduto() {

		if (caixaTableView == null || caixaTableView.getSelectionModel().getSelectedItem() == null) {
			final StageAlert alertS = new StageAlert(AppConstants.AVISO, "Selecione um produto da lista remover!",
					Severity.WARN, Boolean.TRUE, AppConstants.BOTAO_OK);
			alertS.show();
		} else {
			final StageAlert alert = new StageAlert(AppConstants.AVISO, "Deseja remover o produto "
					+ caixaTableView.getSelectionModel().getSelectedItem().getProdutoTableVO().getNome() + " ?",
					Severity.WARN, Boolean.TRUE, AppConstants.SIM, AppConstants.NAO);
			final int response = alert.showAndWaitResponse();

			if (response == 0) {
				EstoqueTableVO estoqueTableVO = null;
				for (EstoqueTableVO estoqueTableVO2 : caixaTableView.getItems()) {
					if (estoqueTableVO2.getCodigoBarras().equals(
							caixaTableView.getSelectionModel().getSelectedItem().getCodigoBarras())) {
						estoqueTableVO = estoqueTableVO2;
					}
				}
				if (estoqueTableVO != null) {
					caixaTableView.getItems().remove(estoqueTableVO);
				}
				somarTotal();
			}
		}
	}

	public void finalizarCompra() {
		try {
			if (caixaTableView == null || caixaTableView.getItems().size() < 1) {
				final StageAlert alertS = new StageAlert(AppConstants.AVISO, "Não existem produtos na lista de venda.",
						Severity.WARN, Boolean.TRUE, AppConstants.BOTAO_OK);
				alertS.show();
			} else {
				this.popupFinalizarCompra = new StagePopup("Finalizar compra", TelaUtilitarios.loaderPopupFXML(
						AppConstants.PAGINA_POPUP_COMPRA, this), Modality.APPLICATION_MODAL, 800.0, 310.0,
						Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
				this.popupFinalizarCompra.show();
			}
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

	/**
	 * Adiciona o produto com o ENTER
	 * 
	 * @param event
	 */
	public void adicionarProdutoKey(final KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			this.adicionarProduto();
		}
	}

}
