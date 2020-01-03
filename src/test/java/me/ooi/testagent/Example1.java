package me.ooi.testagent;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class Example1 {
	
	private String str ; 
	
	public Example1() {
		str = "str" ;
		System.out.println("construct");
	}
	
	public void m1(int a){
		System.out.println("m1-"+a);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) { }
	}
	
	public String m2(){
		System.out.println("m2");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) { }
		return "return-m2" ; 
	}
	
	public String m3(){
		System.out.println("m3");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) { }
		return "return-m3-"+str ; 
	}
}
