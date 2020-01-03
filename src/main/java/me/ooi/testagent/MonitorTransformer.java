package me.ooi.testagent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.regex.Pattern;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class MonitorTransformer implements ClassFileTransformer {
	
	private Pattern excludeClassNamePattern ; 
	private Pattern includeClassNamePattern ; 
	private Pattern excludeMethodNamePattern ; 
	private Pattern includeMethodNamePattern ; 
	
	public MonitorTransformer(String excludeClassNamePatternStr, String includeClassNamePatternStr,
			String excludeMethodNamePatternStr, String includeMethodNamePatternStr) {
		this(Pattern.compile(excludeClassNamePatternStr), Pattern.compile(includeClassNamePatternStr), 
				Pattern.compile(excludeMethodNamePatternStr), Pattern.compile(includeMethodNamePatternStr)) ; 
	}

	public MonitorTransformer(Pattern excludeClassNamePattern, Pattern includeClassNamePattern,
			Pattern excludeMethodNamePattern, Pattern includeMethodNamePattern) {
		super();
		this.excludeClassNamePattern = excludeClassNamePattern;
		this.includeClassNamePattern = includeClassNamePattern;
		this.excludeMethodNamePattern = excludeMethodNamePattern;
		this.includeMethodNamePattern = includeMethodNamePattern;
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) {
		
		final String ST = "MonitorTransformer$L$startTime" ; 
		final String ET = "MonitorTransformer$L$endTime" ; 
		
		className = className.replace("/", ".");
		
		//剔除不需要的
		if( match(excludeClassNamePattern, className) || 
				( !match(includeClassNamePattern, className) )){
			return null ; 
		}
		
		CtClass ctClass = null; 
		try {
			ctClass = ClassPool.getDefault().get(className);
			if( Modifier.isAbstract(ctClass.getModifiers()) || Modifier.isInterface(ctClass.getModifiers()) ){
				return null;
			}
			for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
				String methodName = ctMethod.getName() ; 
				//剔除不需要的
				if( match(excludeMethodNamePattern, methodName) ||
						( !match(includeMethodNamePattern, methodName) )){
					continue ; 
				}
				ctMethod.addLocalVariable(ST, CtClass.longType);
				ctMethod.addLocalVariable(ET, CtClass.longType);
				ctMethod.insertBefore(
						String.format(" %s = System.currentTimeMillis() ; ", ST));
				ctMethod.insertAfter(
						String.format(" %s = System.currentTimeMillis() ; ", ET) + 
						String.format(" me.ooi.testagent.Monitor.INSTANCE.put(\"%s\", \"%s\", \"%s\", %s, %s); ", 
								getMethodId(ctClass, ctMethod), ctClass.getName(), ctMethod.getName(), ST, ET));
			}
			return ctClass.toBytecode();
		} catch (Exception e) {
			me.ooi.utils.XLog.error(null, e.getMessage(), e) ; 
		}
		return null;
	}
	
	private boolean match(Pattern p, String str){
		return p.matcher(str).find() ; 
	}
	
	private String getMethodId(CtClass ctClass, CtMethod ctMethod) throws NotFoundException{
		CtClass[] paramTypes = ctMethod.getParameterTypes() ; 
		String params = "(" ; 
		if( paramTypes != null ){
			for (int i = 0; i < paramTypes.length; i++) {
				CtClass ctType = paramTypes[i] ; 
				params += ctType.getName() ; 
				if( i != paramTypes.length-1 ){
					params += "," ; 
				}
			}
		}
		params += ")" ; 
		return ctClass.getName() + "." + ctMethod.getName() + params ; 
	}
	
}