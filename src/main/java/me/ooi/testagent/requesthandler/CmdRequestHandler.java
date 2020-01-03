package me.ooi.testagent.requesthandler;

import java.util.Map;

/**
 * @author jun.zhao
 * @since 1.0
 */
public interface CmdRequestHandler {
	
	void handle(Writter writter, Map<String, String> params) ; 

}
