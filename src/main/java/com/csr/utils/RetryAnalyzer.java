/**
 * 
 */
package com.csr.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * @author akaushi3
 *
 */
public class RetryAnalyzer implements IRetryAnalyzer {

	int count = 1;

	int retryMaxLimit = Integer.valueOf(PropertiesOperations.getPropertyValueByKey("retryLimit"));

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
	 */
	@Override
	public boolean retry(ITestResult result) {

		if (count < retryMaxLimit) {
			count++;
			return true;
		}
		return false;
	}

}
