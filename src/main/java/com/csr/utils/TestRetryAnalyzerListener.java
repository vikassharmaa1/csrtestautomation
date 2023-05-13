/**
 * 
 */
package com.csr.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

/**
 * @author akaushi3
 *
 */
public class TestRetryAnalyzerListener implements IAnnotationTransformer{

	/* (non-Javadoc)
	 * @see org.testng.IAnnotationTransformer#transform(org.testng.annotations.ITestAnnotation, java.lang.Class, java.lang.reflect.Constructor, java.lang.reflect.Method)
	 */
	@Override
	public void transform(ITestAnnotation annotation, Class classname, Constructor constName, Method method) {
		// TODO Auto-generated method stub
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
		
		
	}

}
