package br.com.tecway.gerenciadorloja.exception;

import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.constants.ConstantesMensagens;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert;
import br.com.tecway.gerenciadorloja.fx.components.StageAlert.Severity;
import br.com.tecway.gerenciadorloja.utils.AppConstants;

/**
 * Classe de controlle da exception
 * 
 * @author Ricardo Cafalchio
 * @since 25/06/2013
 */
public class ControllerException extends Exception {

	public static boolean avisou = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1213837657580984213L;

	public ControllerException(Logger logger, Throwable e) {
		super(e);
		logger.error(e);
	}

	public static void registrarErro(Logger logger, Exception e) {
		logger.error("Error",e);
		final StageAlert alert = new StageAlert(AppConstants.ERRO, ConstantesMensagens.MENSAGEM_GENERICA_ERRO,
				Severity.ERROR, Boolean.TRUE, AppConstants.BOTAO_OK);
		alert.show();
	}

}
