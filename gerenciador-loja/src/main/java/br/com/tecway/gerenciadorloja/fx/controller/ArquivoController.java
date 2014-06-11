package br.com.tecway.gerenciadorloja.fx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.transaction.SystemException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.business.GerenciadorArquivo;
import br.com.tecway.gerenciadorloja.business.IGerenciadorArquivo;
import br.com.tecway.gerenciadorloja.common.DadosArquivoVO;
import br.com.tecway.gerenciadorloja.constants.TipoArquivoEnum;
import br.com.tecway.gerenciadorloja.exception.BusinessException;
import br.com.tecway.gerenciadorloja.exception.ControllerException;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.utils.AppConstants;
import br.com.tecway.gerenciadorloja.utils.DataUtils;
import br.com.tecway.gerenciadorloja.utils.ModalLoadUtils;
import br.com.tecway.gerenciadorloja.utils.RelatorioUtils;

public class ArquivoController extends AbstractSubController<AbstractController> {

	/** Definicao de log */
	protected static final Logger LOGGER = LogManager.getLogger(ArquivoController.class);

	@FXML
	private TextField diretorioText;

	@FXML
	private TextField arquivoText;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		diretorioText.setText("C:\\");
		arquivoText.setText("Planilha_" + DataUtils.dataSistemaAgora().replace("/", "_"));
	}

	@Override
	public void customInitialize() throws ApplicationException {

	}

	/**
	 * Fecha a compra
	 * 
	 * @throws SystemException
	 * @throws IllegalStateException
	 */
	private void gerarArquivo(final TipoArquivoEnum tipoArquivoEnum) throws IllegalStateException, SystemException {
		final StageAlert alert = new StageAlert(AppConstants.AVISO, "Deseja exportar os dados para "
				+ tipoArquivoEnum.toString() + " ?", Severity.WARN, Boolean.TRUE, AppConstants.SIM, AppConstants.NAO);
		final int response = alert.showAndWaitResponse();

		if (response == 0) {
			ModalLoadUtils.getInstance().showModalLoad();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						if (diretorioText == null || diretorioText.getText().isEmpty()) {
							throw new BusinessException("É necessário informar o diretório que o arquivo será gerado!");
						}

						if (arquivoText == null || arquivoText.getText().isEmpty()) {
							throw new BusinessException("É necessário informar o nome do arquivo!");
						}
						getParentController();
						final DadosArquivoVO dadosArquivoVO = RelatorioUtils.atribuirDadosArquivo(
								getParentController().dadosTableView, getParentController().objectsReport);
						dadosArquivoVO.setCaminhoArquivo(diretorioText.getText() + arquivoText.getText());
						dadosArquivoVO.setTipoArquivoEnum(tipoArquivoEnum);
						dadosArquivoVO.setTitulo("Planilha " + DataUtils.dataSistemaAgora().replace("/", "_"));
						final IGerenciadorArquivo gerenciadorArquivo = new GerenciadorArquivo();
						gerenciadorArquivo.gerarArquivo(dadosArquivoVO);
						final StageAlert alertS = new StageAlert(AppConstants.AVISO, "Arquivo gerado com sucesso("
								+ dadosArquivoVO.getCaminhoArquivo() + ")!", Severity.INFO, Boolean.TRUE,
								AppConstants.BOTAO_OK);
						alertS.show();
						getParentController().popup.close();
					} catch (BusinessException e) {
						final StageAlert alert = new StageAlert(AppConstants.AVISO, e.getMensagemNegocio(),
								Severity.WARN, Boolean.TRUE, AppConstants.BOTAO_OK);
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
	}

	public void exportarExcel() {
		try {
			this.gerarArquivo(TipoArquivoEnum.EXCEL);
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

	public void exportarNotepad() {
		try {
			this.gerarArquivo(TipoArquivoEnum.NOTEPAD);
		} catch (Exception e) {
			ControllerException.registrarErro(LOGGER, e);
		}
	}

}
