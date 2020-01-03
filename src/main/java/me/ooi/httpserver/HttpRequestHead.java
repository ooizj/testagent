package me.ooi.httpserver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class HttpRequestHead extends HttpHead {
	
	// ^ http://  [^:/]+  (:([0-9]{1,}))?
	private static final Pattern URL_PATTERN = Pattern.compile("^(http://)?([^:/]+)(:([0-9]{1,}))?") ; 
	
	protected RequestStartLine requestStartLine ; 
	
	protected String serverHost ; 
	protected Integer serverPort ; 
	
	public HttpRequestHead(String headText) {
		super(headText);
		
		if( startLineString != null ){
			requestStartLine = new RequestStartLine(startLineString) ; 
			decodeServerHostAndPort();
		}
	}
	
	private void decodeServerHostAndPort(){
		
		String hostStr = get("Host") ; 
		if( hostStr != null ){
			String[] segments = hostStr.split(":") ; 
			serverHost = segments[0] ; 
			if( segments.length == 2 ){
				serverPort = Integer.valueOf(segments[1]) ; 
			}
		}
		
		if( requestStartLine != null ){
			String url = requestStartLine.getTarget() ; 
			Matcher m = URL_PATTERN.matcher(url) ; 
			if( m.find() ){
				if( serverHost == null ){
					serverHost = m.group(2) ; 
				}
				
				if( serverPort == null ){
					String portStr = m.group(4) ; 
					if( portStr != null ){
						serverPort = Integer.valueOf(portStr) ; 
					}
				}
			}
		}
	}

	public RequestStartLine getRequestStartLine() {
		return requestStartLine;
	}

	public String getServerHost() {
		return serverHost;
	}

	public Integer getServerPort() {
		if( serverPort == null ){
			serverPort = 80 ; 
		}
		return serverPort;
	}

}
