package com.leilaopecuario.exception;

public class ValidacaoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidacaoException(String string, Throwable t) {
		super(string, t);
	}

	public ValidacaoException(Throwable t) {
		super(t);
	}

}
