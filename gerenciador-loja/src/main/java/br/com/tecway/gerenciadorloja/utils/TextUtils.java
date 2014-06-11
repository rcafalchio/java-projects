package br.com.tecway.gerenciadorloja.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TextUtils {

	private static final Logger LOGGER = LogManager.getLogger(TextUtils.class);

	/**
	 * Converte qualquer objeto para String
	 * 
	 * @param entrada
	 * @return String
	 */
	public static String toString(Object entrada) {

		String retorno = null;

		try {

			retorno = String.valueOf(String.valueOf(entrada));

		} catch (NumberFormatException e) {
			LOGGER.error(e);
		}

		return retorno;
	}

}
