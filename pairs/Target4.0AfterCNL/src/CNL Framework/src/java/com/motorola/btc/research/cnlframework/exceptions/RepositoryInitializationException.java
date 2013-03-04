/*
 * @(#)RepositoryInitializationException.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    May 19, 2008   LIBqq41824   Initial creation.
 */
package com.motorola.btc.research.cnlframework.exceptions;


/**
 * Exception related to repository initialization errors.
 *
 */
@SuppressWarnings("serial")
public class RepositoryInitializationException extends RepositoryException {
	
    /** The name of the repository that threw the error. */
	private String repositoryName;
	
	/** The original exception that caused the creation of the repository initialization exception */
	private Throwable originalException;

	/**
	 * 
	 * Constructor that sets the error message given the repository name.
	 * 
	 * @param repositoryName The name of the repository in which the exception occurred.
	 * @param originalException The original exception.
	 */
	public RepositoryInitializationException(String repositoryName, Throwable originalException) {

		super("Error on initialization of \"" + repositoryName + "\" repository");
		this.repositoryName = repositoryName;
		this.originalException = originalException;
	}

	/**
	 * Get method for the repository name.
	 * 
	 * @return The repository name.
	 */
	public String getRepositoryName() {
		return this.repositoryName;
	}

	/**
	 *
	 * Get method for the original exception.
	 * 
	 * @return The original exception.
	 */
	public Throwable getOriginalException() {
		return this.originalException;
	}
	
}
