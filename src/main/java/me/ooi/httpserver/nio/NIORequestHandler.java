package me.ooi.httpserver.nio;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Map;

import me.ooi.httpserver.RequestStartLine;
import me.ooi.testagent.requesthandler.CmdRequestHandler;
import me.ooi.testagent.requesthandler.CmdRequestHandlerRegisty;
import me.ooi.testagent.requesthandler.NIOAbstractRequestHandler;
import me.ooi.testagent.requesthandler.QueryDecoder;
import me.ooi.testagent.requesthandler.Writter;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class NIORequestHandler extends NIOAbstractRequestHandler {

	public NIORequestHandler(SocketChannel socketChannel) throws IOException {
		super(socketChannel);
	}

	@Override
	public void handle() {
		RequestStartLine startLine = new RequestStartLine(requestHeadReader.getStartLineText()) ; 
		Map<String, String> params = new QueryDecoder(startLine.getTarget()).getParams() ;
		if( params == null ){
			//TODO 
			return ; 
		}
		
		String cmd = params.get("cmd"); 
		CmdRequestHandler requestHandler = CmdRequestHandlerRegisty.INSTANCE.getRequestHandler(cmd) ;
		if( requestHandler == null ){
			//TODO 
		}else {
			requestHandler.handle(new Writter(socketChannel), params); 
		}
	}

}
