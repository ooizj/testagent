package me.ooi.httpserver;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class HttpHeadReader {
	
	//读取头部后的那次剩余的数据
	private byte[] readHeadOverflowBytes ; 
	
	private byte[] readHeadBytes = new byte[512] ; 
	private int readHeadPosition = 0 ; //不一定等于head的长度，可能多一些
	
	private boolean readHeadCompleted = false ; 
	private int headLength = 0 ; 
	private String headText ; 
	
	public boolean isReadHeadCompleted() {
		return readHeadCompleted;
	}

	public int getHeadLength() {
		return headLength;
	}

	public String getHeadText() {
		return headText;
	}
	
	public String getStartLineText(){
		if( !isReadHeadCompleted() ){
			return null ; 
		}
		
		int idx = headText.indexOf(HttpHead.BREAK_LINE) ; 
		return headText.substring(0, idx) ; 
	}

	public byte[] getReadHeadOverflowBytes() {
		return readHeadOverflowBytes;
	}

	public void read(byte[] data, int dataLength){
		
		if( dataLength <= 0 ){
			return ; 
		}
		
		if( !readHeadCompleted ){ //read head
			
			//readHeadBytes += data
			int left = readHeadBytes.length-readHeadPosition ; 
			if( left < dataLength ){
				byte[] newBytes = new byte[dataLength+readHeadPosition] ; 
				System.arraycopy(readHeadBytes, 0, newBytes, 0, readHeadBytes.length); 
				readHeadBytes = newBytes ; 
			}
			System.arraycopy(data, 0, readHeadBytes, readHeadPosition, dataLength) ; 
			readHeadPosition += dataLength ; 
			
			if( readHeadPosition >= 4 ){
				for (int i = 0; i < readHeadPosition-3; i++) {
					if( readHeadBytes[i] == '\r' && readHeadBytes[i+1] == '\n' 
							&& readHeadBytes[i+2] == '\r' && readHeadBytes[i+3] == '\n' ){
						headText = new String(readHeadBytes, 0, i+4) ; 
						readHeadCompleted = true ; 
						headLength = i+4 ; 
						
						//此次剩余的数据
						if( readHeadPosition > headLength ){
							byte[] restBytes = new byte[readHeadPosition-headLength] ;
							System.arraycopy(readHeadBytes, headLength, restBytes, 0, restBytes.length);
							readHeadOverflowBytes = restBytes ; 
						}
						
						readHeadBytes = null ; 
						break ; 
					}
				}
			}
		}		
	}
	
}
