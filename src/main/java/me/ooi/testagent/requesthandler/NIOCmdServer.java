package me.ooi.testagent.requesthandler;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import me.ooi.httpserver.nio.NIORequestHandler;
import me.ooi.httpserver.nio.NIOProxyServer;
import me.ooi.utils.XLog;



/**
 * @author jun.zhao
 * @since 1.0
 */
public class NIOCmdServer extends NIOProxyServer {
	
	private static final String TAG = "NIOCmdServer" ; 

	public NIOCmdServer(int port) {
		super(SingleCmdSelectorMananger.INSTANCE, port);
	}
	
	@Override
	public void createClientConnector(SocketChannel socketChannel) throws IOException {
//		new NIOCmdRequestHandler(socketChannel);
		new NIORequestHandler(socketChannel) ; 
	}
	
	public static void main(String[] args) {
		//http://localhost:9998/?a=tt
		int pacPort = 9998 ; 
		XLog.info(TAG, "启动cmd服务：port["+pacPort+"]");
		new NIOCmdServer(pacPort).start();
	}
	
}
