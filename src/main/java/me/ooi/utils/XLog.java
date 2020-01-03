package me.ooi.utils;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class XLog {
	
	private XLog() {
	}
	
	public static void info(String tag, String message) {
		System.out.println(tag+"\t"+message) ; 
	}
	
	public static void error(String tag, String message) {
		System.err.println(tag+"\t"+message) ; 
	}

	public static void error(String tag, String message, Throwable throwable) {
		System.err.println(tag+"\t"+message) ;
		throwable.printStackTrace(System.err);
	}
	
}
