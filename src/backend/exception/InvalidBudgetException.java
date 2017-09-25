package backend.exception;

public class InvalidBudgetException extends RuntimeException {

	private static final long serialVersionUID = 3140374563653534009L;

	public InvalidBudgetException() {
		super();
	}
	
	public InvalidBudgetException(String message) {
		super(message);
	}
}
