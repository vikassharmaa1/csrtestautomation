/**
 * 
 */
package com.csr.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.csr.utils.PropertiesOperations;

/**
 * @author akaushi3
 *
 */
public class ExtentReport {

	private static ExtentReports extent;
	
	public static ExtentReports getInstance(){
		
		if(extent==null){
			
			try {
				setupExtentReport();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return extent;
	}

	public static ExtentReports setupExtentReport() throws Exception {

		try {
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
			Date date = new Date();
			String actualDate = format.format(date);

			String reportPath = System.getProperty("user.dir") + "/Reports/ExecutionReport_" + actualDate + ".html";

			ExtentSparkReporter sparkReport = new ExtentSparkReporter(reportPath);

			extent = new ExtentReports();
			extent.attachReporter(sparkReport);

			sparkReport.config().setDocumentTitle("CSR Test Execution Report");
			sparkReport.config().setTheme(Theme.STANDARD);
			sparkReport.config().setReportName("CSR Test Automation");

			extent.setSystemInfo("Executed on Environment: ", PropertiesOperations.getPropertyValueByKey("url"));
			extent.setSystemInfo("Executed on Browser: ", PropertiesOperations.getPropertyValueByKey("browser"));
			extent.setSystemInfo("Executed on OS: ", System.getProperty("os.name"));
			extent.setSystemInfo("Executed by User: ", System.getProperty("user.name"));
		} catch (Exception e) {

			e.printStackTrace();
			// TODO: handle exception
		}

		return extent;
	}
}
