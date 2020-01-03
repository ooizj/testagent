package me.ooi.httpserver.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import me.ooi.httpserver.ProxyCreationException;
import me.ooi.utils.XLog;



/**
 * @author jun.zhao
 * @since 1.0
 */
public abstract class NIOProxyServer implements NIOExecutor {
	
	private static final String TAG = "NIOProxyServer" ; 
	
	private ServerSocketChannel serverSocketChannel ;
	private SelectionKey selectionKey ; 
	
	private SelectorMananger selectorMananger ;
	
	private int port ; 
	
	public NIOProxyServer(SelectorMananger selectorMananger, int port) {
		this.selectorMananger = selectorMananger ; 
		this.port = port ; 
	}
	
	public void start(){
		try {
			selectorMananger.open() ; 
			Selector selector = selectorMananger.getSelector() ; 
			
			serverSocketChannel = ServerSocketChannel.open() ; 
	        serverSocketChannel.socket().bind(new InetSocketAddress(port));
	        serverSocketChannel.configureBlocking(false);
	        selector.wakeup();
	        selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, this);
	        
	        selectorMananger.start() ; 
		} catch (IOException e) {
			throw new ProxyCreationException(e) ; 
		} finally {
			stop();
		}
	}
	
	public void stop(){
		if( selectorMananger != null ){
			selectorMananger.stop() ; 
		}
		
		try {
			if( serverSocketChannel != null ){
				serverSocketChannel.close();
			}
		} catch (IOException e) {
			XLog.error(TAG, e.getMessage(), e);
		}
	}
	
	@Override
	public void execute() {
		
		if( selectionKey.isAcceptable() ){
			SocketChannel socketChannel = null ; 
			try {
				socketChannel = serverSocketChannel.accept();
				
				XLog.info(TAG, "---- createClientConnector ----");
				if( socketChannel != null ){
					createClientConnector(socketChannel);
				}
			} catch (Exception e) {
				XLog.error(TAG, e.getMessage(), e);
			}
		}
	}
	
	public abstract void createClientConnector(SocketChannel socketChannel) throws IOException ; 

}
