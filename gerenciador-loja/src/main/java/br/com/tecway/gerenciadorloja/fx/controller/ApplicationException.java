package br.com.tecway.gerenciadorloja.fx.controller;

import org.apache.log4j.Logger;

public class ApplicationException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ApplicationException(Logger logger, Throwable e){
		super(e);
		logger.error(e);
	}
	
}
