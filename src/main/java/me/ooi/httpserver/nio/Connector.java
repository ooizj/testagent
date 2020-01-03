package me.ooi.httpserver.nio;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import me.ooi.utils.XLog;

/**
 * @author jun.zhao
 * @since 1.0
 */
public abstract class Connector implements NIOExecutor {
	
	public static final ReentrantLock LOCK = new ReentrantLock();
	
	private static final String TAG = "Connector" ; 
	
	protected Selector selector ; 
	protected SocketChannel socketChannel ;
	protected SelectionKey selectionKey ; 
	protected ByteBuffer readBuff = ByteBuffer.allocate(1024) ;
	
	protected boolean inited = false ; 
	
	//cache the data of read before inited
	private List<byte[]> cachedBytes = new ArrayList<byte[]>() ; 
	
	public Connector(Selector selector, SocketChannel socketChannel) {
		this.selector = selector;
		this.socketChannel = socketChannel;
	}
	
	protected synchronized void register() throws IOException{
		socketChannel.configureBlocking(false);
		selector.wakeup();
		selectionKey = socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE, this);
	}
	
	public abstract void onRead(byte[] data, int length) throws IOException ; 
	public abstract void onWrite() throws IOException ; 
	public abstract void close() ; 
	
	@Override
	public void execute() {
		if( !selectionKey.isValid() ){
			return ; 
		}
		
		try {
			
			if( selectionKey.isReadable() ){
				while( true ){
					readBuff.clear() ; 
					int length = socketChannel.read(readBuff) ;
					if( length <= 0 ){
						break ; 
					}
					
					if( !inited ){
						byte[] newBytes = new byte[length] ; 
						System.arraycopy(readBuff.array(), 0, newBytes, 0, length);
						cachedBytes.add(newBytes) ; 
					}else {
						if( !cachedBytes.isEmpty() ){
							for (byte[] cb : cachedBytes) {
								onRead(cb, cb.length);
							}
							cachedBytes.clear();
						}
						
						onRead(readBuff.array(), length);
					}
				}
			}else if( selectionKey.isWritable() ){
				onWrite();
			}
			
		} catch(SocketTimeoutException e){
			XLog.error(TAG, e.getMessage());
			close();
		} catch(ClosedChannelException e){
			XLog.error(TAG, e.getMessage());
			close();
		} catch (Exception e) {
			XLog.error(TAG, "connector error "+e.getMessage(), e);
			close();
		} 
	} 
	
	protected void doClose(){
		try {
			if( socketChannel != null ){
				socketChannel.close() ; 
			}
			if( selectionKey != null ){
				selectionKey.attach(null) ; 
				selectionKey.cancel();
			}
		} catch (IOException e) {
			XLog.error(TAG, "failed to close socket channel! "+e.getMessage());
		}
	}
	
}
