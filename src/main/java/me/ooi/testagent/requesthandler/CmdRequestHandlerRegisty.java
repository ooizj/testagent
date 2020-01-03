package me.ooi.testagent.requesthandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class CmdRequestHandlerRegisty {
	
	public static final CmdRequestHandlerRegisty INSTANCE = new CmdRequestHandlerRegisty() ; 
	
	private Map<String, CmdRequestHandler> requestHandlerMap = new HashMap<String, CmdRequestHandler>() ; 
	
	private CmdRequestHandlerRegisty(){
		registry("subData", new SubDataRequestHandler()) ; 
	}
	
	public void registry(String cmd, CmdRequestHandler requestHandler){
		requestHandlerMap.put(cmd, requestHandler) ; 
	}
	
	public CmdRequestHandler getRequestHandler(String cmd){
		return requestHandlerMap.get(cmd) ; 
	}

}
