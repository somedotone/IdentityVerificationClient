package logic;

import nxt.addons.JO;

public class MessageException extends Exception{
	
	JO error;
	
	public MessageException(JO error) {
		this.error = error;
	}
	
	
	@Override
    public String getMessage(){
		return error.toJSONString();
	}
}
