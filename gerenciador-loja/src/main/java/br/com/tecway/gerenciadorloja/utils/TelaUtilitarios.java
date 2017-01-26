package br.com.tecway.gerenciadorloja.utils;


import java.io.IOException;
import java.util.Date;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.constants.TypeNumberEnum;
import br.com.tecway.gerenciadorloja.fx.components.NumberTextField;
import br.com.tecway.gerenciadorloja.fx.controller.AbstractController;
import br.com.tecway.gerenciadorloja.fx.controller.AbstractSubController;
import br.com.tecway.gerenciadorloja.fx.controller.ApplicationException;
import br.com.tecway.gerenciadorloja.fx.controller.LoginController;

/**
 * Classe util de processamento dos dados da tela
 * 
 * @author Ricardo Cafalchio
 * @since 25/06/2013
 */
public class TelaUtilitarios {

	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

	/**
	 * Recupera o valor de um TextField e joga numa String
	 * 
	 * @param textField
	 * @return String
	 */
	public static String recuperarValorTextField(TextInputControl textField) {
		String retorno = null;
		try {
			retorno = textField.getText();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return retorno;
	}

	/**
	 * Recupera o valor de um TextField e joga num Double
	 * 
	 * @param textField
	 * @return Double
	 */
	public static Double recuperarValorDoubleTextField(NumberTextField textField) {
		Double retorno = null;
		try {
			if (textField.getTypeNumber().equals(TypeNumberEnum.CURRENCY)) {
				retorno = NumberTextField.converterCurrencyToDouble(textField.getText());
			}
			if (textField.getTypeNumber().equals(TypeNumberEnum.PERCENTAGE)) {
				retorno = NumberTextField.converterPercentageToDouble(textField.getText());
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return retorno;
	}

	/**
	 * Recupera o valor de um Label e joga num Double
	 * 
	 * @param label
	 * @return Double
	 */
	public static Double recuperarValorDoubleLabel(Label label) {
		Double retorno = null;
		try {
			retorno = NumberTextField.converterCurrencyToDouble(label.getText());
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return retorno;
	}

	/**
	 * Recupera o valor de um TextField e joga num Long
	 * 
	 * @param textField
	 * @return Long
	 */
	public static Long recuperarValorLongTextField(TextInputControl textField) {
		Long retorno = null;
		try {
			retorno = Long.valueOf(textField.getText());
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return retorno;
	}

	/**
	 * Recupera o valor de um TextField e joga num Double
	 * 
	 * @param textField
	 * @return Integer
	 */
	public static Integer recuperarValorIntegerTextField(TextInputControl textField) {
		Integer retorno = null;
		try {
			retorno = Integer.valueOf(textField.getText());
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return retorno;
	}

	/**
	 * Método para carregar um popup/modal FXML.
	 * 
	 * @param <T>
	 *            - Tipo genérico
	 * @param url
	 *            - url
	 * @param parentController
	 *            - parentController
	 * @param userData
	 *            - userData
	 * @return - T
	 * @throws IOException
	 *             - {@link IOException}
	 * @throws ApplicationException
	 *             - {@link ApplicationException}
	 */
	@SuppressWarnings("unchecked")
	public static <T extends AbstractController> Node loaderPopupFXML(final String url,
			final AbstractController parentController, final Object userData) throws IOException, ApplicationException {
		final FXMLLoader loader = getFXMLLoader(url, parentController.getClass());
		final Node root = (Node) loader.load();
		if (userData != null) {
			root.setUserData(userData);
		}
		if (loader.getNamespace().values() != null && loader.getNamespace().values().size() > 0) {
			for (final Object node : loader.getNamespace().values()) {
				if (node instanceof AbstractSubController) {
					final AbstractSubController<AbstractController> controller = (AbstractSubController<AbstractController>) node;
					controller.setParentController(parentController);
					// controller.setSpringContext(parentController.getSpringContext());
					controller.setApplication(parentController.getApplication());
					controller.customInitialize();
				}
			}
		}
		return root;
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
	 */
	private static <T extends AbstractController> FXMLLoader getFXMLLoader(final String url, final Class<T> clazz)
			throws IOException {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(clazz.getResource(url));
		// loader.setResources(ResourceBundle.getBundle(AppConstants.PROPERTIES_LOCATION, new
		// Locale(AppConstants.LANGUAGE_PORTUGUESE, AppConstants.LANGUAGE_COUNTRY)));
		return loader;
	}

	/**
	 * Método para carregar um popup/modal FXML.
	 * 
	 * @param <T>
	 *            - Tipo genérico
	 * @param url
	 *            - url
	 * @param parentController
	 *            - parentController
	 * @return - T
	 * @throws IOException
	 *             - {@link IOException}
	 * @throws ApplicationException
	 *             - {@link ApplicationException}
	 */
	public static <T extends AbstractController> Node loaderPopupFXML(final String url,
			final AbstractController parentController) throws IOException, ApplicationException {
		return loaderPopupFXML(url, parentController, null);
	}

	/**
	 * 
	 * @param dataInicialText
	 * @return
	 */
	public static Date recuperarDataTextField(TextField dataInicialText) {
		Date data = null;
		if (!dataInicialText.getText().isEmpty()) {
			data = DataUtils.parseDateUtil(dataInicialText.getText(), DataUtils.FORMATO_DATA_PADRAO);
		}
		return data;
	}

}
