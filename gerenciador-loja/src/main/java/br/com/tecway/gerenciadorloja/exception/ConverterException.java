package br.com.tecway.gerenciadorloja.exception;

import org.apache.log4j.Logger;

public class ConverterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConverterException(Throwable e) {
		super(e);
	}

	public ConverterException(String msg, Throwable e,Logger logger) {
		super(e);
		logger.error(msg, e);
	}

}
