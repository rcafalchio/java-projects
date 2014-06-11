package br.com.tecway.gerenciadorloja.fx.controller;

import java.util.List;

import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.common.ObjectReport;
import br.com.tecway.gerenciadorloja.constants.RelatorioEnum;
import br.com.tecway.gerenciadorloja.fx.components.StagePopup;
import br.com.tecway.gerenciadorloja.fx.launcher.GerenciadorLojaApplication;
import br.com.tecway.gerenciadorloja.utils.RelatorioUtils;

/**
 * Implementacao da classe base de todos os controllers
 * 
 * @author Ricardo Cafalchio
 * @since 08/03/2013
 */
public abstract class AbstractController implements Initializable {

	/** Definicao de log */
	protected static final Logger LOGGER = LogManager.getLogger(AbstractController.class);


	/** Definicao da classe principal */
	private GerenciadorLojaApplication application;
	protected TableView dadosTableView;
	protected StagePopup popup;
	protected List<ObjectReport> objectsReport;

	/**
	 * Post Constructor.
	 * 
	 * @throws ApplicationException
	 *             - ApplicationException
	 */
	public abstract void customInitialize() throws ApplicationException;

	/**
	 * @return the application
	 */
	public GerenciadorLojaApplication getApplication() {
		return this.application;
	}

	/**
	 * @param application
	 *            the application to set
	 */
	public void setApplication(final Application application) {
		this.application = (GerenciadorLojaApplication) application;
	}

	public void setObjectsReport(RelatorioEnum relatorioEnum) {
		List<ObjectReport> lista = RelatorioUtils.recuperarParametrosRelatorio(relatorioEnum);
		this.objectsReport = lista;
	}

}