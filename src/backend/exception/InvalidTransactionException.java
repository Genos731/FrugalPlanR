package backend.exception;

public class InvalidTransactionException extends RuntimeException {

	private static final long serialVersionUID = -8500849974716211630L;

	public InvalidTransactionException() {
		super();
	}
	
	public InvalidTransactionException(String message) {
		super(message);
	}
}
