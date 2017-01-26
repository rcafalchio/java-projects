package br.com.tecway.gerenciadorloja.exception;

import org.apache.log4j.Logger;

public class TelaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TelaException(Throwable e) {
		super(e);
	}

	public TelaException(String msg, Throwable e,Logger logger) {
		super(e);
		logger.error(msg, e);
	}

}
