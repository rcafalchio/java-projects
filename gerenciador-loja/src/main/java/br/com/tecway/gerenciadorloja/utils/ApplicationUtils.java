package br.com.tecway.gerenciadorloja.utils;

public final class ApplicationUtils {

	private ApplicationUtils() {
	}

	/**
	 * Verifica se uma exception lançada tem alguma classe de outra exception encapsulada
	 * 
	 * @param throwableLancada
	 * @param throwable
	 * @return
	 */
	public static boolean verificarException(Throwable throwableLancada, Class c) {
		boolean retorno = false;
		if (c.getName().equals(throwableLancada.getClass().getName())) {
			retorno = true;
		} else {
			final Throwable cause = throwableLancada.getCause();
			if (cause != null) {
				retorno = ApplicationUtils.verificarException(cause, c);
			}
		}
		return retorno;
	}

}
