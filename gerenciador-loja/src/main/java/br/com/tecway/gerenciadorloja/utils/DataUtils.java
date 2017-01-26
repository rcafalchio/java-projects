package br.com.tecway.gerenciadorloja.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.tecway.gerenciadorloja.fx.launcher.GerenciadorLojaApplication;

/**
 * 
 * @author Ricardo Cafalchio
 * @since 17/12/2013
 */
public final class DataUtils {

	private static final Logger LOGGER = LogManager.getLogger(GerenciadorLojaApplication.class);
	
	/**
	 * Constante de formato padrão para data.
	 */
	public static final String FORMATO_DATA_PADRAO = "dd/MM/yyyy";

	/**
	 * Obtém a data do sistema no momento<br>
	 * 
	 * @return String data atual no formato dd/MM/yyyy
	 * 
	 **/

	public static String dataSistemaAgora() {
		final Date dataSistema = new java.util.Date();
		try {
			final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			return dateFormat.format(dataSistema.getTime());
		} catch (final Exception e) {
			final String erro = "Falha ao tentar obter a data atual do sistema: ";
			LOGGER.error(erro, e);
			return null;
		}
	}

	/**
	 * Remove dias da data
	 * 
	 * @param data
	 *            Data
	 * @param quantidade
	 *            quantidade de dias a somar
	 * @return Data com "quantidade" de dias subtraidos
	 */
	public static java.util.Date removerDias(final java.util.Date data, final int quantidade) {
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		calendar.add(Calendar.DAY_OF_MONTH, quantidade * -1);
		return calendar.getTime();
	}

	/**
	 * Faz o parse de uma string para data
	 * 
	 * @param stringDt
	 *            String a transformar em data
	 * @param formato
	 *            Formato que será utilizado
	 * 
	 * @return java.util.Date Data parseada
	 */
	public static java.util.Date parseDateUtil(final String stringDt, final String formato) {
		try {
			if (stringDt == null || stringDt.trim().equals("")) {
				return null;
			}
			final DateFormat dateFormat = new SimpleDateFormat(formato);
			return dateFormat.parse(stringDt.trim());
		} catch (final Exception e) {
			LOGGER.debug("Falha ao tentar formatar data em string para o tipo java.util.Date: ", e);
			return null;
		}
	}
	
	/**
	 * Retorna uma String dd/mm/aaaa
	 * 
	 * @param data
	 *            - data que será convertida
	 * @param formato
	 *            - Formato da data
	 * @return String <br>
	 * @author F0105619<br>
	 *         <i>Finalizada em: </i>31/10/2011<br>
	 *         <i>Última atualização: </i>31/10/2011
	 */
	public static String deUtilDateParaString(final java.util.Date data, final String formato) {

		String retorno = "";

		try {
			final SimpleDateFormat format = new SimpleDateFormat(formato);
			retorno = format.format(data);
		} catch (final Exception e) {
			LOGGER.debug("Erro ao formatar a data no método deSqlDateParaString " + e.getMessage());
		}
		return retorno;
	}

}
