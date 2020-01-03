package me.ooi.utils;

import java.io.IOException;

import me.ooi.httpserver.HttpHead;
import me.ooi.testagent.requesthandler.Writter;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class ResponseUtils {
	
	public static void writeResponseHead(Writter writter, String contentType, int contentLength) throws IOException{
		String headStr = 
				"HTTP/1.1 200 OK" + HttpHead.BREAK_LINE +
				"Content-Type: " + contentType + HttpHead.BREAK_LINE +
				"content-length: "+contentLength + HttpHead.BREAK_LINE +
				HttpHead.BREAK_LINE
				; 
		writter.write(headStr.getBytes()) ; 
	}

}
