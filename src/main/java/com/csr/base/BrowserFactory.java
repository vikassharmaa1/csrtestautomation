/**
 * 
 */
package com.csr.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author akaushi3
 *
 */
public class BrowserFactory {

	public WebDriver createInstance(String browserName) {

		WebDriver driver = null;

		if (browserName.toLowerCase().contains("firefox")) {

			 WebDriverManager.firefoxdriver().setup();
			//System.setProperty("webdriver.gecko.driver",
					//System.getProperty("user.dir") + "//src//test//resources//drivers//geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			driver = new FirefoxDriver(options);

			return driver;

		}

		if (browserName.toLowerCase().contains("internet")) {

			WebDriverManager.iedriver().setup();
			//System.setProperty("webdriver.ie.driver",
				//	System.getProperty("user.dir") + "//src//test//resources//drivers//IEDriverserver.exe");
			driver = new InternetExplorerDriver();

			return driver;

		}

		if (browserName.toLowerCase().contains("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "//src//test//resources//drivers//chromedriver.exe");
			//WebDriverManager.chromedriver().setup();
			ChromeOptions opt=new ChromeOptions();
			opt.addArguments("--ignore-certificate-errors"); 
			opt.setAcceptInsecureCerts(true);
			//opt.setExperimentalOption("debuggerAddress","localhost:9222");
			driver = new ChromeDriver(opt);
			
			return driver;

		}

		return driver;

	}
}
