package me.ooi.httpserver;


/**
 * @author jun.zhao
 * @since 1.0
 */
public class RequestStartLine {
	/*
	An HTTP method, a verb (like GET, PUT or POST) or a noun (like HEAD or OPTIONS), that describes the action to be performed. For example, GET indicates that a resource should be fetched or POST means that data is pushed to the server (creating or modifying a resource, or generating a temporary document to send back).
	The request target, usually a URL, or the absolute path of the protocol, port, and domain are usually characterized by the request context. The format of this request target varies between different HTTP methods. It can be
	An absolute path, ultimately followed by a '?' and query string. This is the most common form, known as the origin form, and is used with GET, POST, HEAD, and OPTIONS methods.
	POST / HTTP/1.1
	GET /background.png HTTP/1.0
	HEAD /test.html?query=alibaba HTTP/1.1
	OPTIONS /anypage.html HTTP/1.0
	A complete URL, known as the absolute form, is mostly used with GET when connected to a proxy.
	GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1
	The authority component of a URL, consisting of the domain name and optionally the port (prefixed by a ':'), is called the authority form. It is only used with CONNECT when setting up an HTTP tunnel.
	CONNECT developer.mozilla.org:80 HTTP/1.1
	The asterisk form, a simple asterisk ('*') is used with OPTIONS, representing the server as a whole.
	OPTIONS * HTTP/1.1
	The HTTP version, which defines the structure of the remaining message, acting as an indicator of the expected version to use for the response.
	
	 */
	
	private String method ; 
	private String target ; 
	private String version ; 
	
	public RequestStartLine(String text){
		String[] segments = text.split(" ") ; 
		this.method = segments[0] ; 
		this.target = segments[1] ; 
		this.version = segments[2] ; 
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString(){
		return method + " " + target + " " + version ;
	}
	
}
