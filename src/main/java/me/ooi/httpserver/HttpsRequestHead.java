package me.ooi.httpserver;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class HttpsRequestHead extends HttpRequestHead {

	public HttpsRequestHead(String headText) {
		super(headText);
	}

	@Override
	public Integer getServerPort() {
		if( serverPort == null ){
			serverPort = 443 ; 
		}
		return serverPort;
	}
}
