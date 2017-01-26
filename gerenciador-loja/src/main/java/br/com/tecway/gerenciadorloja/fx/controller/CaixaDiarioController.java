package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
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

public class CaixaDiarioController extends PrincipalController {

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
	private TableView<CaixaTableVO> caixaTableView;

	protected StagePopup popupRetirada = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			dataLabel.setText(dataLabel.getText().concat(DataUtils.dataSistemaAgora()));
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

	public void retirar() {
		try {
			this.popupRetirada = new StagePopup("Retirar Dinheiro", TelaUtilitarios.loaderPopupFXML(
					AppConstants.PAGINA_POPUP_RETIRADA, this), Modality.APPLICATION_MODAL, 800.0, 310.0, Boolean.FALSE,
					Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
			this.popupRetirada.show();
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

	public void ajustar() {
		try {
			this.popupRetirada = new StagePopup("Ajustar Dinheiro", TelaUtilitarios.loaderPopupFXML(
					AppConstants.PAGINA_POPUP_AJUSTA, this), Modality.APPLICATION_MODAL, 800.0, 310.0, Boolean.FALSE,
					Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
			this.popupRetirada.show();
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

}
