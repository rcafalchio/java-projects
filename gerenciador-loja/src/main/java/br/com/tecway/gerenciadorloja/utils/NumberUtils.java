package br.com.tecway.gerenciadorloja.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Classe responsável por tratar conversão de números
 * 
 * @author Ricardo Cafalchio
 * @since 25/06/2013
 */
public class NumberUtils {

	private static final Logger LOGGER = LogManager.getLogger(NumberUtils.class);

	/**
	 * Converte qualquer objeto para Short
	 * 
	 * @param entrada
	 * @return Short
	 */
	public static Short toShort(Object entrada) {
		Short retorno = null;
		try {
			retorno = Short.valueOf(String.valueOf(entrada));
		} catch (NumberFormatException e) {
			LOGGER.error(e);
		}
		return retorno;
	}

	/**
	 * Converte qualquer objeto para Integer
	 * 
	 * @param entrada
	 * @return Integer
	 */
	public static Integer toInteger(Object entrada) {
		Integer retorno = null;
		try {
			retorno = Integer.valueOf(String.valueOf(entrada));
		} catch (NumberFormatException e) {
			LOGGER.error(e);
		}
		return retorno;
	}

	/**
	 * Converte qualquer objeto para Long
	 * 
	 * @param entrada
	 * @return Long
	 */
	public static Long toLong(Object entrada) {
		Long retorno = null;
		try {
			retorno = Long.valueOf(String.valueOf(entrada));
		} catch (NumberFormatException e) {
			LOGGER.error(e);
		}
		return retorno;
	}

	/**
	 * Converte qualquer objeto para Double
	 * 
	 * @param entrada
	 * @return Double
	 */
	public static Double toDouble(Object entrada) {
		Double retorno = null;
		try {
			retorno = Double.valueOf(entrada.toString());
		} catch (NumberFormatException e) {
			LOGGER.error(e);
		}
		return retorno;
	}

	/**
	 * Método responsável por formatar um BigDecimal.
	 * 
	 * @since 14/02/2013 18:59:17
	 * @param bigDecimal
	 *            - bigDecimal
	 * @return - {@link String}
	 */
	public static String formatBigDecimal(final BigDecimal bigDecimal) {
		final DecimalFormat decimalFormat = new DecimalFormat("¤ #,##0.00");
		return decimalFormat.format(bigDecimal);
	}

	/**
	 * Método para formatar valor BigDecimal, para exibição na tela. Ex: 65000 (R$ 65.000,00), Se o valor é nulo,
	 * retorna vazio.
	 * 
	 * @param bigDecimal
	 *            {@link BigDecimal} Valor para formatar.
	 * @return {@link String} Retorna uma String com o valor formatado.
	 */
	public static String formatDecimal(final BigDecimal bigDecimal) {
		if (bigDecimal != null) {
			return AppConstants.VAZIO;
		}
		return NumberFormat.getCurrencyInstance().format(bigDecimal).toString();
	}

}
