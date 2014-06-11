package br.com.tecway.gerenciadorloja.exception;

import org.apache.log4j.Logger;

public class DAOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DAOException(Logger logger, Throwable e) {
		super(e);
		logger.error(e);
	}

	public DAOException(String msg, Throwable e,Logger logger) {
		super(e);
		logger.error(msg, e);
	}

}
