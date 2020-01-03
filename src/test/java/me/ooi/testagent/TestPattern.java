package me.ooi.testagent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import me.ooi.testagent.requesthandler.QueryDecoder;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class TestPattern {
	
	@Test
	public void t1(){
		Pattern p = Pattern.compile("\\$") ; 
		System.out.println(p.matcher("akjsldfj$sdkf").find());
		System.out.println(p.matcher("akjsldfjsdkf").find());
	}
	
	@Test
	public void t2(){
		Pattern p = Pattern.compile("^com\\.ibm\\.") ; 
		System.out.println(p.matcher("a.com.ibm.asdf").find());
		System.out.println(p.matcher("com.ibm.asdf").find());
		System.out.println(p.matcher("com.ibms").find());
	}
	
	@Test
	public void t3(){
		Pattern p = Pattern.compile(".") ; 
		System.out.println(p.matcher("lksdjf").find());
		System.out.println(p.matcher("sldf$lsdf").find());
		System.out.println(p.matcher("com.ibms").find());
	}
	
	@Test
	public void t4(){
		
		String query = "/?a=123&b=321&c=4567" ; 
		final String PROJECT_NAME = "[a-z|A-Z|_]+" ; 
		final String VAR_NAME = "[a-z|A-Z|_]+" ; 
		final String VAL = "[a-z|A-Z|_|0-9]+" ; 
		
		String str = String.format("/%s%s", 
				String.format("(?:%s)?", PROJECT_NAME),
				String.format("(?:\\?(%s)=(%s)(.*))?", VAR_NAME, VAL)) ; 
		System.out.println("正则\t"+str);
		Pattern queryPattern = Pattern.compile(str) ; 
		
		Matcher m = queryPattern.matcher(query) ; 
		while( m.find() ){
			for (int i = 1; i < m.groupCount()+1; i++) {
				System.out.println(i+":"+m.group(i));
			}
		}
	}
	
	@Test
	public void t5(){
		String query = "&b=321&c=4567" ; 
		final String VAR_NAME = "[a-z|A-Z|_]+" ; 
		final String VAL = "[a-z|A-Z|_|0-9]+" ; 
		
		String str = String.format("(?:&(%s)=(%s))", VAR_NAME, VAL) ; 
		System.out.println("正则\t"+str);
		Pattern queryPattern = Pattern.compile(str) ; 
		Matcher m = queryPattern.matcher(query) ; 
		while( m.find() ){
			for (int i = 1; i < m.groupCount()+1; i++) {
				System.out.println(i+":"+m.group(i));
			}
		}
	}
	
	@Test
	public void t6(){
		String query = "/?a=123&b=321&c=4567" ; 
		QueryDecoder c = new QueryDecoder(query) ; 
		System.out.println(c);
	}


}
