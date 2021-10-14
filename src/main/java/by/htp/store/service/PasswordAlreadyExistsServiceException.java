package by.htp.store.service;

public class PasswordAlreadyExistsServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public PasswordAlreadyExistsServiceException() {
		super();
	}

	public PasswordAlreadyExistsServiceException(String message) {
		super(message);
	}

	public PasswordAlreadyExistsServiceException(Exception e) {
		super(e);
	}

	public PasswordAlreadyExistsServiceException(String message, Exception e) {
		super(message, e);
	}

}
