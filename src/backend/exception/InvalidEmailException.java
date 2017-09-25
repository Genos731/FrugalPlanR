package backend.exception;

public class InvalidEmailException extends RuntimeException {

	private static final long serialVersionUID = -6512604375505470123L;
	
	public InvalidEmailException() {
		super();
	}
	
	public InvalidEmailException(String message) {
		super(message);
	}
}
