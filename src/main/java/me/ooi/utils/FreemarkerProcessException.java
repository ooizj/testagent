package me.ooi.utils;
/**
 * @author jun.zhao
 * @since 1.0
 */
public class FreemarkerProcessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public FreemarkerProcessException(String message) {
		super(message) ; 
	}
	
	public FreemarkerProcessException(String message, Throwable cause) {
		super(message, cause) ; 
	}
	
	public FreemarkerProcessException(Throwable cause) {
		super(cause) ; 
	}

}
