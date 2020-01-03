package me.ooi.testagent.requesthandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class Writter {
	
	private SocketChannel socketChannel ; 
	
	public Writter(SocketChannel socketChannel) {
		super();
		this.socketChannel = socketChannel;
	}

	public void write(byte[] data) throws IOException{
		socketChannel.write(ByteBuffer.wrap(data)) ; 
	}

}
