package me.ooi.testagent.requesthandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class QueryDecoder {
	
	private static final String PROJECT_NAME = "[a-z|A-Z|_]+" ; 
	private static final String VAR_NAME = "[a-z|A-Z|_]+" ; 
	private static final String VAL = "[a-z|A-Z|_|0-9]+" ; 
	
	private Pattern queryPattern = Pattern.compile(String.format("/%s%s", 
			String.format("(?:%s)?", PROJECT_NAME),
			String.format("(?:\\?(%s)=(%s)(.*))?", VAR_NAME, VAL))) ; 
	
	private Pattern param2sPattern = Pattern.compile(String.format("(?:&(%s)=(%s))", VAR_NAME, VAL)) ; 
	
	private Map<String, String> params = new LinkedHashMap<String, String>() ; 
	
	private String query ; 
	
	//&后面的参数
	private String param2s ; 
	
	public QueryDecoder(String query) {
		super();
		this.query = query;
		init();
	}
	
	private void init(){
		decodeParam1s();
		decodeParam2s();
	}
	
	private void decodeParam1s(){
		Matcher m = queryPattern.matcher(query) ; 
		if( m.find() ){
			params.put(m.group(1), m.group(2)) ; 
			param2s = m.group(3) ; 
		}
	}

	private void decodeParam2s(){
		if( param2s == null || "".equals(param2s) ){
			return ; 
		}
		Matcher m = param2sPattern.matcher(param2s) ; 
		while( m.find() ){
			params.put(m.group(1), m.group(2)) ; 
		}
	}
	
	public Map<String, String> getParams() {
		return params;
	} 
	
}
