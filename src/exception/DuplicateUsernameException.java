package exception;

public class DuplicateUsernameException extends RuntimeException {
	
	private static final long serialVersionUID = 1700037927656046042L;

	public DuplicateUsernameException() {
		super();
	}
	
	public DuplicateUsernameException(String message) {
		super(message);
	}
}
