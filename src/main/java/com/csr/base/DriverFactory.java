/**
 * 
 */
package com.csr.base;

import org.openqa.selenium.WebDriver;

/**
 * @author akaushi3
 *
 */
public class DriverFactory {

	private DriverFactory() {

	}

	private static DriverFactory instance = new DriverFactory();

	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

	public static DriverFactory getInstance() {

		return instance;

	}

	public WebDriver getDriver() {

		return webDriver.get();
	}

	public void setWebDriver(WebDriver driver) {

		webDriver.set(driver);

	}

	public void closeBrowser() {
		webDriver.get().quit();
		webDriver.remove();
	}

}