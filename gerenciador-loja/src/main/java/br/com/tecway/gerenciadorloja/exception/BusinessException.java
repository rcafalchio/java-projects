package br.com.tecway.gerenciadorloja.exception;


public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mensagemNegocio;
	private Throwable exception;

	public String getMensagemNegocio() {
		return mensagemNegocio;
	}

	public Throwable getException() {
		return exception;
	}

	public BusinessException(String mensagemNegocio, Throwable e) {

		this.mensagemNegocio = mensagemNegocio;
		this.exception = e;

	}
	
	public BusinessException(String mensagemNegocio) {

		this.mensagemNegocio = mensagemNegocio;

	}

}
