package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.business.GerenciadorCaixa;
import br.com.tecway.gerenciadorloja.business.IGerenciadorCaixa;
import br.com.tecway.gerenciadorloja.common.CaixaTableVO;
import br.com.tecway.gerenciadorloja.common.CaixaVO;
import br.com.tecway.gerenciadorloja.constants.RelatorioEnum;
import br.com.tecway.gerenciadorloja.constants.TypeNumberEnum;
import br.com.tecway.gerenciadorloja.entity.CaixaEntity;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.exception.DAOException;
import br.com.tecway.gerenciadorloja.fx.components.NumberTextField;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.fx.components.StagePopup;
import br.com.tecway.gerenciadorloja.utils.AppConstants;
import br.com.tecway.gerenciadorloja.utils.DataUtils;
import br.com.tecway.gerenciadorloja.utils.TelaUtilitarios;

public class ConsultaCaixaController extends PrincipalController {

	private static final Logger LOGGER = LogManager.getLogger(ConsultaCaixaController.class);

	@FXML
	private Label dataLabel;
	@FXML
	protected Label dinheiroLabel;
	@FXML
	private Label cartaoDebitoLabel;
	@FXML
	private Label cartaoCreditoLabel;
	@FXML
	private TextField dataInicialText;
	@FXML
	private TextField dataFinalText;
	@FXML
	private HBox exportarHBox;
	@FXML
	private Accordion vendasAccordion;
	@FXML
	private TableView<CaixaTableVO> caixaTableView;

	protected StagePopup popupRetirada = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			dataLabel.setText(dataLabel.getText().concat(DataUtils.dataSistemaAgora()));
			dataInicialText.setText(DataUtils.dataSistemaAgora());
			dataFinalText.setText(DataUtils.dataSistemaAgora());
			final IGerenciadorCaixa gerenciadorCaixa = new GerenciadorCaixa();
			final CaixaVO caixaVO = gerenciadorCaixa.recuperarCaixa(new Date());
			dinheiroLabel.setText(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, caixaVO.getValorDinheiro()
					.toString()));
			cartaoCreditoLabel.setText(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, caixaVO
					.getValorCartaoCredito().toString()));
			cartaoDebitoLabel.setText(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, caixaVO
					.getValorCartaoDebito().toString()));
			super.dadosTableView = caixaTableView;
		} catch (DAOException e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

	@Override
	public void customInitialize() throws ApplicationException {
		super.setObjectsReport(RelatorioEnum.CAIXA);
	}

	public void pesquisar() {
		try {
			final Date dataInicial = TelaUtilitarios.recuperarDataTextField(dataInicialText);
			final Date dataFinal = TelaUtilitarios.recuperarDataTextField(dataFinalText);
			if (dataInicial == null || dataFinal == null) {
				throw new BusinessException("Formato de data incorreto! (DD/MM/AAAA)");
			}
			final IGerenciadorCaixa gerenciadorCaixa = new GerenciadorCaixa();
			final List<CaixaEntity> listaCaixas = gerenciadorCaixa.pesquisarCaixas(dataInicial, dataFinal);

			if (listaCaixas != null && !listaCaixas.isEmpty()) {
				caixaTableView.getItems().clear();
				caixaTableView.setVisible(Boolean.TRUE);
				exportarHBox.setVisible(Boolean.TRUE);
				CaixaTableVO caixaTableVO = null;
				for (CaixaEntity caixaEntity : listaCaixas) {
					caixaTableVO = new CaixaTableVO();
					if (caixaEntity.getValorCartaoCredito() == null) {
						caixaEntity.setValorCartaoCredito(0.0);
					}
					if (caixaEntity.getValorCartaoDebito() == null) {
						caixaEntity.setValorCartaoDebito(0.0);
					}
					if (caixaEntity.getValorDinheiro() == null) {
						caixaEntity.setValorDinheiro(0.0);
					}
					caixaTableVO.setValorData(DataUtils.deUtilDateParaString(caixaEntity.getData(),
							DataUtils.FORMATO_DATA_PADRAO));
					caixaTableVO.setValorCredito(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, caixaEntity
							.getValorCartaoCredito().toString()));
					caixaTableVO.setValorDebito(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, caixaEntity
							.getValorCartaoDebito().toString()));
					caixaTableVO.setValorDinheiro(NumberTextField.aplicarMascara(TypeNumberEnum.CURRENCY, caixaEntity
							.getValorDinheiro().toString()));

					caixaTableView.getItems().add(caixaTableVO);
				}

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

	/**
	 * Exporta para excel
	 */
	public void exportar() {
		try {
			this.popupRetirada = new StagePopup("Exportar Dados", TelaUtilitarios.loaderPopupFXML(
					AppConstants.PAGINA_POPUP_GERAR_ARQUIVO, this), Modality.APPLICATION_MODAL, 800.0, 310.0,
					Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
			super.popup = popupRetirada;
			this.popupRetirada.show();
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

}
