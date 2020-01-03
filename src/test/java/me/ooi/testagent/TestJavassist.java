package me.ooi.testagent;

import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.Test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class TestJavassist {
	
	private CtClass cc ; 
	
	@Before
	public void init() throws NotFoundException{
		ClassPool pool = ClassPool.getDefault();
		cc = pool.get("me.ooi.testagent.Example1");
	}
	
	@SuppressWarnings("rawtypes")
	private void testAllMethod(CtClass cc) throws Exception{
		Class c = cc.toClass();
		Example1 t = (Example1) c.newInstance() ; 
		System.out.println("------------------------------------1");
		t.m1(111);
		System.out.println("------------------------------------2");
		System.out.println(t.m2());
		System.out.println("------------------------------------3");
		System.out.println(t.m3());
	}
	
	@Test
	public void t1() throws Exception {
		testAllMethod(cc);
	}
	
	@Test
	public void t2() throws Exception {
		CtMethod[] ctMethods = cc.getDeclaredMethods() ; 
		for (CtMethod ctMethod : ctMethods) {
			ctMethod.insertBefore("System.out.println(\"开头\");");
			ctMethod.insertAfter("System.out.println(\"结尾\");");
		}
		testAllMethod(cc);
	}
	
	@Test
	public void t3() throws Exception {
		CtMethod[] ctMethods = cc.getDeclaredMethods() ; 
		for (CtMethod ctMethod : ctMethods) {
			ctMethod.addLocalVariable("st___", CtClass.longType);
			ctMethod.addLocalVariable("et___", CtClass.longType);
			ctMethod.insertBefore("st___ = System.currentTimeMillis() ; ");
			ctMethod.insertAfter("et___ = System.currentTimeMillis() ; "+
					"System.out.println(\"耗时[\"+(et___-st___)+\"]\");");
		}
		testAllMethod(cc);
	}
	
	@Test
	public void t4() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		cc = pool.get("me.ooi.testagent.Example1");
		System.out.println(cc.isInterface());
		cc = pool.get("me.ooi.testagent.Example2");
		System.out.println(Modifier.isAbstract(cc.getModifiers()));
		cc = pool.get("me.ooi.testagent.Example3");
		System.out.println(Modifier.isAbstract(cc.getModifiers()));
	}
	
}
