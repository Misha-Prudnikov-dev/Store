package by.htp.store.service;

public class InvalidInputServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidInputServiceException() {
		super();
	}

	public InvalidInputServiceException(String message) {
		super(message);
	}

	public InvalidInputServiceException(Exception e) {
		super(e);
	}

	public InvalidInputServiceException(String message, Exception e) {
		super(message, e);
	}

}
