package me.ooi.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class ResourceUtils {
	
	public static InputStream getResourceFromClassPath(String path){
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(path) ; 
	}
	
	public static byte[] toByteArray(InputStream is) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
		copy(is, baos);
		byte[] ret = baos.toByteArray() ;
		is.close();
		return ret ; 
	}

	public static void copy(InputStream input, OutputStream output) throws IOException{
		final int BUFF_SIZE = 1024 ;
		final int EOF = -1;

		int len = EOF;
		byte[] buff = new byte[BUFF_SIZE];

		while ((len = input.read(buff)) != EOF) {
			output.write(buff, 0, len);
		}
	}

}
