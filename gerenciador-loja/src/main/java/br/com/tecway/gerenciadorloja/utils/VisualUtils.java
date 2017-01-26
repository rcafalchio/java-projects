package br.com.tecway.gerenciadorloja.utils;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import br.com.tecway.gerenciadorloja.fx.controller.AbstractController;
import br.com.tecway.gerenciadorloja.fx.controller.ApplicationException;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 20/06/2013
 */
public final class VisualUtils {

	/**
	 * Método para carregar o FXML.
	 * 
	 * @param <T>
	 *            - Tipo genérico
	 * @param url
	 *            - url
	 * @param clazz
	 *            - clazz
	 * @return - T
	 * @throws IOException
	 *             - {@link IOException}
	 * @throws ApplicationException
	 */
	public static <T extends AbstractController> Node loaderFXML(final String url, final Class<T> clazz,
			Application application) throws IOException, ApplicationException {
		return (Node) getFXMLLoader(url, clazz, application);
	}

	/**
	 * Método responsável por retornar um FXMLLoader.
	 * 
	 * @param <T>
	 *            - Tipo genérico
	 * @param url
	 *            - url
	 * @param clazz
	 *            - clazz
	 * @return - T
	 * @throws IOException
	 *             - {@link IOException}
	 * @throws ApplicationException
	 */
	private static <T extends AbstractController> Node getFXMLLoader(final String url, final Class<T> clazz,
			Application application) throws IOException, ApplicationException {
		final FXMLLoader appLoader = new FXMLLoader();
		appLoader.setLocation(clazz.getResource(url));
		appLoader.setBuilderFactory(new JavaFXBuilderFactory());
		
		final Node retorno = (Node) appLoader.load();
		
		// Inicializa os postConstructors dos controllers
		if (appLoader.getNamespace().values() != null && appLoader.getNamespace().values().size() > 0) {
			for (final Object node : appLoader.getNamespace().values()) {
				if (node instanceof AbstractController) {
					final AbstractController controller = (AbstractController) node;
					controller.setApplication(application);
					controller.customInitialize();
				}
			}
		}
		return retorno;
	}


}
