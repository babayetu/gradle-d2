package com.babayetu.crack;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class ObjectAidCrack {

	public static void main(String[] args) throws CannotCompileException, NotFoundException, IOException {
			ClassPool.getDefault().insertClassPath("D:\\temp\\temp\\com.objectaid.uml_1.2.2.jar");
			CtClass ctClass = ClassPool.getDefault().get("com.objectaid.uml.license.LicenseManager");
			CtMethod ctMethod = ctClass.getDeclaredMethod("needsLicense"); // 获取方法
			System.out.println(ctMethod.getSignature());
			ctMethod.setBody("return false;"); 
			ctClass.writeFile();
	}

}
