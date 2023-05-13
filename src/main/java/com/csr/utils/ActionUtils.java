/**
 * 
 */
package com.csr.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;

/**
 * @author akaushi3
 *
 */
public class ActionUtils {

	WebDriverWait wait = null;

	/**
	 * 
	 * @param element
	 * @param fieldName
	 * @param valueToSet
	 */
	public void sendKeys_custom(By element, String fieldName, String valueToSet) {

		try {
			waitForElementVisibility(element, fieldName).clear();
			DriverFactory.getInstance().getDriver().findElement(element).clear();
			DriverFactory.getInstance().getDriver().findElement(element).sendKeys(valueToSet);
			ListenersImplementation.test.get().log(Status.PASS, fieldName + " entered value as " + valueToSet);

		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL,
					"Value enter in field: " + fieldName + " is failed due to exception: " + e);
			Assert.fail();
		}
	}

	/**
	 * 
	 * @param element
	 * @param fieldName
	 */
	public void click_custom(By element, String fieldName) {

		try {
			// waitForElementVisibility(element);
			waitForElementVisibility(element, fieldName);
			Thread.sleep(2000);
			DriverFactory.getInstance().getDriver().findElement(element).click();
			ListenersImplementation.test.get().log(Status.PASS, fieldName + " Clicked");
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL, fieldName + " Unable to click due to " + e);
			Assert.fail();
		}
	}

	/**
	 * 
	 * @param xpath
	 * @param fieldName
	 */
	public void clickByXpath(String xpath, String fieldName) {

		try {
			// waitForElementVisibility(element);
			wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 60);
			wait.until(ExpectedConditions
					.visibilityOf(DriverFactory.getInstance().getDriver().findElement(By.xpath(xpath)))).click();
			ListenersImplementation.test.get().log(Status.PASS, fieldName + " Clicked");
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL, fieldName + " Unable to click due to " + e);
			Assert.fail();
		}
	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	public WebElement waitForElementVisibility(By element, String fieldName) {
		WebElement ele = null;
		try {
			wait = new WebDriverWait(DriverFactory.getInstance().getDriver(),40); //from 500 to 40
			ele = wait.until(ExpectedConditions.visibilityOfElementLocated(element));
			ListenersImplementation.test.get().log(Status.PASS, fieldName + " is visible on page");
			return ele;

		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL, fieldName + " is not present on page due to " + e);
			Assert.fail();
			return null;
		}

	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	public WebElement waitForElementClickable(By element, String fieldName) {
		WebElement ele = null;
		try {
			wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 30);

			ele = wait.until(ExpectedConditions.elementToBeClickable(element));
			ListenersImplementation.test.get().log(Status.PASS, fieldName + " is clickable");
			return ele;

		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL,
					fieldName + " is not clickable on page due to " + e);
			Assert.fail();
			return null;
		}

	}

	public void waitForAllElementsPresent(By element, String fieldName) {

		try {
			wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 50);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
			ListenersImplementation.test.get().log(Status.PASS, fieldName + " present on page");
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL, fieldName + " not present on webpage due to " + e);
			Assert.fail();
		}
	}

	/**
	 * 
	 * @param element
	 * @param text
	 * @return
	 */
	public Boolean waitForTextPresent(By element, String text) {
		boolean flag = false;
		try {
			wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 40);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(element, text));
			ListenersImplementation.test.get().log(Status.PASS, text + " is present on page");
			return flag = true;
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL, text + " is not present on page due to " + e);
			return flag = false;
		}

	}

	/**
	 * 
	 * @param element
	 * @param fieldName
	 * @param ddVisibleText
	 * @throws Throwable
	 */
	public void selectDropDownByVisibleText_custom(By element, String fieldName, String ddVisibleText)
			throws Throwable {
		try {
			waitForElementVisibility(element, fieldName);
			WebElement ele = DriverFactory.getInstance().getDriver().findElement(element);
			Select s = new Select(ele);
			s.selectByVisibleText(ddVisibleText);
			ListenersImplementation.test.get().log(Status.PASS,
					fieldName + "==> Dropdown Value Selected by visible text: " + ddVisibleText);
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL,
					"Dropdown value not selected for field: " + fieldName + "  due to exception: " + e);
			Assert.fail();
		}
	}

	/**
	 * 
	 * @param element
	 * @param fieldName
	 * @param ddVisibleText
	 * @throws Throwable
	 */
	public void selectDropDownByOption_custom(By element, String fieldName, String ddVisibleText) throws Throwable {
		try {
			waitForElementVisibility(element, fieldName);
			WebElement ele = DriverFactory.getInstance().getDriver().findElement(element);
			Select s = new Select(ele);
			s.selectByValue(ddVisibleText);
			ListenersImplementation.test.get().log(Status.PASS,
					fieldName + "==> Dropdown Value Selected by visible text: " + ddVisibleText);
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL,
					"Dropdown value not selected for field: " + fieldName + "  due to exception: " + e);
			Assert.fail();
		}
	}

	/**
	 * 
	 * @param element
	 * @param fieldName
	 * @param ddVisibleText
	 * @throws Throwable
	 */
	public String selectDropDownByIndex_custom(By element, String fieldName, int index) throws Throwable {
		String selectedoption = null;
		try {
			waitForElementVisibility(element, fieldName);
			WebElement ele = DriverFactory.getInstance().getDriver().findElement(element);
			Select s = new Select(ele);
			s.selectByIndex(index);
			selectedoption = s.getFirstSelectedOption().getText();
			ListenersImplementation.test.get().log(Status.PASS,
					fieldName + "==> Dropdown Value Selected by index is : " + selectedoption);
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL,
					"Dropdown value not selected for field: " + fieldName + "  due to exception: " + e);
			Assert.fail();
		}
		return selectedoption;
	}

	public String getText_custom(By element, String fieldName) {
		String text = "";
		try {

			text = DriverFactory.getInstance().getDriver().findElement(element).getText().trim();
			ListenersImplementation.test.get().log(Status.PASS, fieldName + "==> Text retrived is: " + text);
			return text;
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.INFO,
					fieldName + "==> Text not retrived due to exception: " + e);
			Assert.fail();
		}
		return text;
	}

	/**
	 * Get Attribute Of Text Boxes
	 * 
	 * @param element
	 * @param fieldName
	 * @return
	 */
	public String getAttribute_value_custom(By element, String fieldName) {

		String text = "";
		try {

			text = DriverFactory.getInstance().getDriver().findElement(element).getAttribute("value");
			ListenersImplementation.test.get().log(Status.PASS, fieldName + "==> Text retrived is: " + text);
			return text;
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL,
					fieldName + "==> Text not retrived due to exception: " + e);
			Assert.fail();
		}
		return text;
	}
	
	/**
	 * Get Attribute Of Text Boxes
	 * 
	 * @param element
	 * @param fieldName
	 * @return
	 */
	public String getAttribute_custom(By element,String attribute ,String fieldName) {

		String text = "";
		try {

			text = DriverFactory.getInstance().getDriver().findElement(element).getAttribute(attribute);
			ListenersImplementation.test.get().log(Status.PASS, fieldName + "==> Text retrived is: " + text);
			return text;
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL,
					fieldName + "==> Text not retrived due to exception: " + e);
			Assert.fail();
		}
		return text;
	}

	/**
	 * To check the state of Element
	 * 
	 * @param element
	 * @param fieldName
	 * @return
	 */
	public boolean getElementState_custom(By element, String fieldName) {
		boolean flag = false;
		try {

			flag = DriverFactory.getInstance().getDriver().findElement(element).isEnabled();
			if (flag) {
				ListenersImplementation.test.get().log(Status.PASS, fieldName + " is enabled.");
				return flag;
			} else {
				ListenersImplementation.test.get().log(Status.PASS, fieldName + " is disabled.");
				return flag;
			}

		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL,
					fieldName + "==> Text not retrived due to exception: " + e);

		}
		return flag;
	}

	public void waitForInvisibilityofElement(By element, String fieldname) {

		try {
			wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
			Thread.sleep(1000);
			ListenersImplementation.test.get().log(Status.PASS,
					fieldname + " was present on page and page has been refreshed.");
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL,
					fieldname + " was not present on page due to " + e);
			Assert.fail();
		}

	}

	/**
	 * 
	 * @param element
	 * @param fieldName
	 */
	public void moveToElement_custom(By element, String fieldName) {
		try {

			WebElement ele = DriverFactory.getInstance().getDriver().findElement(element);
			JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getInstance().getDriver();
			executor.executeScript("arguments[0].scrollIntoView(true);", ele);
			Actions actions = new Actions(DriverFactory.getInstance().getDriver());
			actions.moveToElement(ele).click().build().perform();
			ListenersImplementation.test.get().log(Status.PASS, fieldName + "==> Mouse hovered Successfully! ");
			Thread.sleep(1000);
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL,
					"Unable to hover mouse on field: " + fieldName + " due to exception: " + e);
			Assert.fail();
		}
	}

	/**
	 * 
	 * @param element
	 * @param fieldName
	 */
	public void scrollIntoView(By element, String fieldName) {
		WebElement ele = DriverFactory.getInstance().getDriver().findElement(element);
		JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getInstance().getDriver();
		executor.executeScript("arguments[0].scrollIntoView(true);", ele);
	}

	/**
	 * Check Checkbox
	 * 
	 * @param element
	 * @param fieldName
	 */
	public void select_checkBox_Custom(By element, String fieldName) {

		boolean flag = false;

		try {

			flag = DriverFactory.getInstance().getDriver().findElement(element).isSelected();

			if (flag == false) {

				DriverFactory.getInstance().getDriver().findElement(element).click();
				ListenersImplementation.test.get().log(Status.PASS, fieldName + " checkbox is checked");
			}

		} catch (Exception e) {

			ListenersImplementation.test.get().log(Status.PASS,
					fieldName + " checkbox is not checked due to " + e);
		}

	}

	/**
	 * Uncheck Checkbox
	 * 
	 * @param element
	 * @param fieldName
	 */
	public void uncheck_checkBox_Custom(By element, String fieldName) {

		boolean flag = false;

		try {

			flag = DriverFactory.getInstance().getDriver().findElement(element).isSelected();

			if (flag) {

				DriverFactory.getInstance().getDriver().findElement(element).click();
				ListenersImplementation.test.get().log(Status.PASS, fieldName + " checkbox is unchecked");
			}

		} catch (Exception e) {

			ListenersImplementation.test.get().log(Status.PASS,
					fieldName + " checkbox is not unchecked due to " + e);
		}

	}

	/**
	 * Wait method for page to load
	 */
	public void checkPageIsReady() {

		JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getInstance().getDriver();

		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			System.out.println("Page Is loaded.");
			return;
		}
		for (int i = 0; i < 15; i++) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}

			// To check page ready state.
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}
	}

	/**
	 * 
	 * @param element
	 * @param field
	 */
	public String getSelectedOption_Dropdown(By element, String field) {
		String text = "";

		try {
			text = new Select(DriverFactory.getInstance().getDriver().findElement(element)).getFirstSelectedOption()
					.getText().trim();
			ListenersImplementation.test.get().log(Status.PASS, field + "==> Text retrived is: " + text);
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.PASS, field + "==> Text not retrived due to " + e);
		}
		return text;

	}

	/**
	 * Custom Assert Function
	 * 
	 * @param actualVal
	 * @param expectedVal
	 * @param fieldName
	 */
	public void assertEquals_custom(String actualVal, String expectedVal, String fieldName) {

		try {

			if (actualVal.equalsIgnoreCase(expectedVal)) {

				ListenersImplementation.test.get().log(Status.PASS, "Verification is pass for " + fieldName
						+ " expected value is :" + expectedVal + " actual value is " + actualVal + "");
			}

			else {
				ListenersImplementation.test.get().log(Status.FAIL, "Verification is fail for " + fieldName
						+ " expected value is :" + expectedVal + " actual value is " + actualVal + "");
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL,
					"Verification is fail on " + fieldName + " due to " + e);
			Assert.assertTrue(false, e.toString());
		}
	}

	/**
	 * Contains Assertion
	 * 
	 * @param actualVal
	 * @param expectedVal
	 * @param fieldName
	 */
	public void assertContains_custom(String actualVal, String expectedVal, String fieldName) {

		try {

			if (actualVal.contains(expectedVal)) {

				ListenersImplementation.test.get().log(Status.PASS, "Verification is pass for " + fieldName
						+ " expected value is :" + expectedVal + " actual value is " + actualVal + "");
			}

			else {
				ListenersImplementation.test.get().log(Status.FAIL, "Verification is fail for " + fieldName
						+ " expected value is :" + expectedVal + " actual value is " + actualVal + "");
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL,
					"Verification is fail on " + fieldName + " due to " + e);
			Assert.assertTrue(false, e.toString());
		}
	}

	/**
	 * 
	 * @param actualMsg
	 * @param expectedMsg
	 * @param fieldName
	 */
	public void assertEquals_list(List<String> actualMsg, List<String> expectedMsg, String fieldName) {

		try {

			if (actualMsg.size() == expectedMsg.size()) {

				for (int i = 0; i < actualMsg.size(); i++) {

					if (actualMsg.get(i).equals(expectedMsg.get(i))) {
						ListenersImplementation.test.get().log(Status.PASS,
								"Verification is pass for " + fieldName + " expected value is :" + expectedMsg.get(i)
										+ " actual value is " + actualMsg.get(i) + "");
					} else {
						ListenersImplementation.test.get().log(Status.FAIL,
								"Verification is fail for " + fieldName + " expected value is :" + expectedMsg.get(i)
										+ " actual value is " + actualMsg.get(i) + "");
						Assert.assertTrue(false);
					}
				}
			}

			else {

				ListenersImplementation.test.get().log(Status.FAIL,
						"The verificatoin can not be done as number of messages are different");

			}
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL,
					"Verification is fail on " + fieldName + " due to " + e);
			Assert.assertTrue(false, e.toString());
		}
	}
	
	public void switchToFrame(By element){
		
		try {
			wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 60);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));	
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL,"Unable to locate element " +e);
			Assert.fail();
		}
		
	}
	
	public void switchToDefaultContent(){
		
		DriverFactory.getInstance().getDriver().switchTo().defaultContent();
	}

	public boolean isElementPresent(By ele, String fieldName) {

		try {
			WebElement element = DriverFactory.getInstance().getDriver().findElement(ele);
			waitForElementVisibility(ele, fieldName);
			if (element.isDisplayed()) {

				ListenersImplementation.test.get().log(Status.PASS, fieldName + " is visible on screen");
				return true;
			} else {
				ListenersImplementation.test.get().log(Status.INFO, fieldName + " is not visible on screen");
				return false;
			}
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.INFO, fieldName + " is not visible on screen");
			return false;
		}

	}
}
