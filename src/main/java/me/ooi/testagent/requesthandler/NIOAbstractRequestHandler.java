package me.ooi.testagent.requesthandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import me.ooi.httpserver.HttpHeadReader;
import me.ooi.httpserver.HttpRequestHead;
import me.ooi.httpserver.RequestStartLine;
import me.ooi.httpserver.nio.Connector;
import me.ooi.utils.XLog;

/**
 * @author jun.zhao
 * @since 1.0
 */
public abstract class NIOAbstractRequestHandler extends Connector {
	
	private static final String TAG = "AbstractRequestHandler" ; 

	protected HttpHeadReader requestHeadReader = new HttpHeadReader() ; 
	protected HttpRequestHead clientRequestHead ; 
	
	private boolean closed = false ; 
	
	public NIOAbstractRequestHandler(SocketChannel socketChannel) throws IOException {
		super(SingleCmdSelectorMananger.INSTANCE.getSelector(), socketChannel) ; 
		register();
		inited = true ; 
	}
	
	@Override
	public void onRead(byte[] data, int length) throws IOException {
		if( clientRequestHead == null ){
			readRequestHead(readBuff.array(), length) ; 
		}
	}
	
	@Override
	public void onWrite() throws IOException {
		if( clientRequestHead != null ){
			handle();
			close();
		}
	}
	
	public void write(byte[] data) throws IOException{
		socketChannel.write(ByteBuffer.wrap(data)) ; 
	}
	
	public abstract void handle() ; 
	
	private void readRequestHead(byte[] data, int len) throws IOException{
		requestHeadReader.read(data, len);
		
		if( requestHeadReader.isReadHeadCompleted() ){
			RequestStartLine startLine = new RequestStartLine(requestHeadReader.getStartLineText()) ; 
			XLog.info(TAG, startLine.toString());
			clientRequestHead = new HttpRequestHead(requestHeadReader.getHeadText()) ; 
		}
	}
	
	public void close(){
		if( closed ){
			return ; 
		}
		
		if( clientRequestHead != null ){
			XLog.info(TAG, "client["+clientRequestHead.getRequestStartLine()+"] close");
		}else {
			XLog.info(TAG, "client[null] close");
		}
		
		closed = true ; 
		super.doClose();
	}
	
}
