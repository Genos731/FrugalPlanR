package exception;

public class InvalidAccountException extends RuntimeException {

	private static final long serialVersionUID = 9053645626697139786L;
	
	public InvalidAccountException(){
		super();
	}
	
	public InvalidAccountException(String message){
		super(message);
	}

}
