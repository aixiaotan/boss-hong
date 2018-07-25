package com.boss.shar.exception;

public class IdeaXException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public IdeaXException(String bussinessErrorMsg) {
		super(bussinessErrorMsg);
	}
	
	public IdeaXException(Throwable cause) {
		super(cause);
	}
	
	public IdeaXException(String bussinessErrorMsg,Throwable cause) {
		super(bussinessErrorMsg+":"+cause.getMessage(),cause);
	}
}
