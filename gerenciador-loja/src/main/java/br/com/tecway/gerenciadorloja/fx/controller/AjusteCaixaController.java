package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.transaction.SystemException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.business.GerenciadorCaixa;
import br.com.tecway.gerenciadorloja.business.IGerenciadorCaixa;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.fx.components.NumberTextField;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.utils.AppConstants;
import br.com.tecway.gerenciadorloja.utils.TelaUtilitarios;

public class AjusteCaixaController extends AbstractSubController<ConsultaCaixaController> {

	/** Definicao de log */
	protected static final Logger LOGGER = LogManager.getLogger(AjusteCaixaController.class);

	@FXML
	private Label totalLabel;

	@FXML
	private NumberTextField valorAjusteTextField;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@Override
	public void customInitialize() throws ApplicationException {
		totalLabel.setText(getParentController().dinheiroLabel.getText());
	}

	/**
	 * Fecha a compra
	 * 
	 * @throws SystemException
	 * @throws IllegalStateException
	 */
	public void ajustarCaixa() {
		try {
			if (valorAjusteTextField.getText().isEmpty()) {
				throw new BusinessException("Digite um valor para ajustar!");
			}
			final StageAlert alert = new StageAlert(AppConstants.AVISO, "Deseja atualizar o caixa para "
					+ valorAjusteTextField.getText() + " ?", Severity.WARN, Boolean.TRUE, AppConstants.SIM,
					AppConstants.NAO);
			final int response = alert.showAndWaitResponse();

			if (response == 0) {
				final IGerenciadorCaixa gerenciadorCaixa = new GerenciadorCaixa();
				gerenciadorCaixa.ajustarCaixa(TelaUtilitarios.recuperarValorDoubleTextField(valorAjusteTextField));
				final StageAlert alertS = new StageAlert(AppConstants.AVISO, "Caixa ajustado com sucesso!",
						Severity.INFO, Boolean.TRUE, AppConstants.BOTAO_OK);
				alertS.show();
				getParentController().popupRetirada.close();
				getApplication().chamarTelaPrincipal();
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
}
