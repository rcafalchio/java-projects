package br.com.tecway.gerenciadorloja.exception;


public class ExportadorDadosException extends Exception {

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

	public ExportadorDadosException(String mensagemNegocio, Throwable e) {
		this.mensagemNegocio = mensagemNegocio;
		this.exception = e;
	}
	
	public ExportadorDadosException(Throwable e) {
		this.exception = e;
	}

}
