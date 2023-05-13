/**
 * 
 */
package com.csr.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.csr.utils.ActionUtils;
import com.csr.utils.PropertiesOperations;

/**
 * @author akaushi3
 *
 */
public class TestBase extends ActionUtils {

	BrowserFactory bf = new BrowserFactory();
	public WebDriver driver = null;
	
	
	//@BeforeSuite
	@BeforeTest
	public void launchApplication() {

		DriverFactory.getInstance()
				.setWebDriver(bf.createInstance(PropertiesOperations.getPropertyValueByKey("browser")));

		driver = DriverFactory.getInstance().getDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(PropertiesOperations.getPropertyValueByKey("url"));
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void refresh() {

		DriverFactory.getInstance().getDriver().navigate().refresh();
	}

	
	//@AfterSuite(alwaysRun = true)
	@AfterTest(alwaysRun = true)
	public void tearDown() {

		DriverFactory.getInstance().closeBrowser();
	}

}
