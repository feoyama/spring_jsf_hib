package com.feoyama.spring_jsf_hib.service;

import org.apache.log4j.Logger;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	private Logger log;
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(String message, Exception exception) {
		super(message, exception);
		log = Logger.getLogger(exception.getClass());
		log.error(message, exception);
	}
}
