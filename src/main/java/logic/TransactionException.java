package logic;

public class TransactionException extends Exception{
	
	String error;
	
	public TransactionException(String error) {
		this.error = error;
	}
	
	
	@Override
	public String getMessage(){
		return error;
	}
}
