package me.ooi.httpserver;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class HttpHead {
	
	public static final int UNKNOW_CONTENT_LENGTH = -1;
	
	public static final String BREAK_LINE = "\r\n" ; 

	protected String text ; 
	
	protected String[] lines ; 
	
	protected String startLineString ; 
	
	protected Map<String, String> headerMap = new LinkedHashMap<String, String>() ; 
	
	public HttpHead(String headText) {
		this.text = headText ; 
		
		lines = text.split(BREAK_LINE) ; 
		initByLines(lines);
	}
	
	protected void initByLines(String[] lines) {
		if( lines != null ){
			if( lines.length >= 1 ){
				startLineString = lines[0] ; 
			}
			
			for (String line : lines) {
				String[] segments = line.split(":") ;
				headerMap.put(segments[0].trim(), segments.length==1?"":segments[1].trim()) ; 
			}
		}
	}
	
//	public void modifyStartLine(String startLineString){
//		lines[0] = startLineString ; 
//		initByLines(lines);
//		text = String.join(BREAK_LINE, lines)+BREAK_LINE+BREAK_LINE ; 
//	}
	
	public String get(String key){
		return headerMap.get(key) ; 
	}
	
	public boolean containsKey(String key){
		return headerMap.containsKey(key) ; 
	}
	
	public int getContentLength(){
		int contentLength = UNKNOW_CONTENT_LENGTH ; 
		if( containsKey("Content-Length") ){
			contentLength = Integer.valueOf(get("Content-Length")) ; 
		}
		return contentLength ; 
	}

	public Map<String, String> getHeaderAsMap() {
		return headerMap ; 
	}

	public String getText() {
		return text;
	} 
	
	@Override
	public String toString() {
		return text ; 
	}
	
}
