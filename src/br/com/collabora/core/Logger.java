package br.com.collabora.core;

import java.util.logging.Level;

public class Logger {

	private static Logger instance = null;

	private final java.util.logging.Logger logger = java.util.logging.Logger.getGlobal();

	private Logger() {

	}

	public void logInfo(String message) {
		logger.log(Level.INFO, message);
	}

	public void logWarning(String message) {
		logger.log(Level.WARNING, message);
	}

	public void logError(String message) {
		logger.log(Level.SEVERE, message);
	}

	public void logError(String message, Exception ex) {
		logger.log(Level.SEVERE, message, ex);
	}

	public static Logger getInstance() {
		return (instance == null) ? (instance = new Logger()) : instance;
	}
}
