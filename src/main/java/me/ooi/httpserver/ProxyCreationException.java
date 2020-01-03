package me.ooi.httpserver;
/**
 * @author jun.zhao
 * @since 1.0
 */
public class ProxyCreationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ProxyCreationException(String message) {
		super(message) ; 
	}
	
	public ProxyCreationException(String message, Throwable cause) {
		super(message, cause) ; 
	}
	
	public ProxyCreationException(Throwable cause) {
		super(cause) ; 
	}

}
