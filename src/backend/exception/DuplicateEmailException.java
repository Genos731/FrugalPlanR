package backend.exception;

public class DuplicateEmailException extends RuntimeException {

	private static final long serialVersionUID = 962190162919700649L;

	public DuplicateEmailException() {
		super();
	}
	
	public DuplicateEmailException(String message) {
		super(message);
	}
}
