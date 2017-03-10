package com.noriakijr.spring_jsf_hib.persistence;

import org.apache.log4j.Logger;

public class PersistenceException extends Exception {

	private static final long serialVersionUID = 1L;

	private Logger log;
	
	public PersistenceException(String message) {
		super(message);
	}
	
	public PersistenceException(String message, Exception exception) {
		super(message, exception);
		log = Logger.getLogger(exception.getClass());
		log.error(message, exception);
	}
}
