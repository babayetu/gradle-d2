package com.babayetu.crack;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class HelloDemo {

	public static void main(String[] args) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		 CtClass ctClass = ClassPool.getDefault().getCtClass("com.babayetu.crack.Hello");
	        
	        
	        CtMethod ctMethod = ctClass.getDeclaredMethod("sayHello");
	        ctMethod.setBody("System.out.println(\"hi\");");    
	        
	        
	        Class ch=ctClass.toClass();
	        
	        Hello h=(Hello) ch.newInstance();
	        h.sayHello();        
	        
	        Hello hel=new Hello();
	        hel.sayHello();

	}

}
