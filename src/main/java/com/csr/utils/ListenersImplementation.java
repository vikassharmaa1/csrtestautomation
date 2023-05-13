/**
 * 
 */
package com.csr.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.base.ExtentReport;

/**
 * @author akaushi3
 *
 */
public class ListenersImplementation implements ITestListener {
	
	//static ExtentReports report;
	//ExtentTest test;

	private static ExtentReports extent = ExtentReport.getInstance();
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	ThreadLocal<String> testName = new ThreadLocal<String>();
	
	private String getTestMethodName(ITestResult result) {
		return result.getName();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.ITestListener#onFinish(org.testng.ITestContext)
	 */
	public void onFinish(ITestContext result) {
		
		extent.flush();
		//report.flush();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
	 */
	public void onStart(ITestContext result) {

		
		testName.set(result.getName());
		ExtentTest parent = extent.createTest(testName.get());
		parentTest.set(parent);
		/*try {
			report = ExtentReport.setupExtentReport();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.ITestListener#onTestFailedButWithinSuccessPercentage(org.
	 * testng.ITestResult)
	 */
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.ITestListener#onTestFailure(org.testng.ITestResult)
	 */
	public void onTestFailure(ITestResult result) {

		test.get().log(Status.FAIL,
				"Test Case: " + result.getMethod().getMethodName() + " is failed");
		//ExtentFactory.getInstance().closeExtentReport();

		File src = ((TakesScreenshot) DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
		Date date = new Date();
		String actualDate = format.format(date);

		String screenshotPath = System.getProperty("user.dir") + "\\Reports\\Screenshots\\" + actualDate + ".png";
		File dest = new File(screenshotPath);

		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			test.get().addScreenCaptureFromPath(screenshotPath,"Test case failure screenshot");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ExtentFactory.getInstance().closeExtentReport();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult)
	 */
	public void onTestSkipped(ITestResult result) {
		test.get().log(Status.SKIP,
				"Test Case: " + result.getMethod().getMethodName() + " is skipped");
		//ExtentFactory.getInstance().closeExtentReport();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.ITestListener#onTestStart(org.testng.ITestResult)
	 */
	public void onTestStart(ITestResult result) {

		ExtentTest child = parentTest.get().createNode(result.getName());
		test.set(child);
		//test = report.createTest(result.getMethod().getMethodName());
		//ExtentFactory.getInstance().setExtent(test);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
	 */
	public void onTestSuccess(ITestResult result) {

		test.get().log(Status.PASS,
				"Test Case: " + result.getMethod().getMethodName() + " is passed");
		//ExtentFactory.getInstance().closeExtentReport();
	}

}
