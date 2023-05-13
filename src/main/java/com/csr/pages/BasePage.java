/**
 * 
 */
package com.csr.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.utils.DB_Connection;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.Queries;
import com.csr.utils.Xls_Reader;

/**
 * @author akaushi3
 *
 */
public class BasePage extends TestBase {

	protected By addCustomerTab = By.xpath("//div[@id='section_list_customer_service']//li[3]");
	protected By findAnOrderTab = By.xpath("//div[@id='section_list_customer_service']//li[2]");
	protected By findCustomerTab = By.xpath("//div[@id='section_list_customer_service']//li[1]");
	protected By compareAccountsTab = By.xpath("//div[@id='section_list_customer_service']//li[5]");
	protected By bulkCancellationTab = By.xpath("//div[@id='section_list_customer_service']//li[4]");
	protected By coles_onlineicon_deliverTo = By.xpath("//span[contains(text(),'Deliver to')]");
	protected By getSearch_TextBox = By.id("searchTerm");
	protected By getSearchIcon = By.id("btnSearch");
	protected By getLoginLink = By.xpath("//span[contains(text(),'Log in / Signup')]");
	protected By validation_message = By.xpath("//div[@class='colrs-animate validation-messages']//ul//li");
	protected By identityTab = By.xpath("//div[@id='section_button_customer_service']//li[1]");
	protected By Detailstab = By.xpath("//div[@id='section_button_customer_service']//li[2]");
	protected By addressTab = By.xpath("//div[@id='section_button_customer_service']//li[3]");
	protected By ordersTab = By.xpath("//div[@id='section_button_customer_service']//li[4]");
	protected By couponsTab = By.xpath("//div[@id='section_button_customer_service']//li[6]");
	protected By commentsTab = By.xpath("//div[@id='section_button_customer_service']//li[7]");
	protected By subscriptionTab = By.xpath("//div[@id='section_button_customer_service']//li[8]");
	protected By logOutAsCustomer = By.xpath("//a[text()='Logout From Customer']");
	protected By customerService_list = By.id("section_list_customer_service");
	protected By validation_messages_email = By.xpath("//div[@class='colrs-animate error-message']");
	protected By validation_messages = By.xpath("//div[@class='colrs-animate validation-messages']//ul//li");
	protected By headers_page = By.className("main-header");
	protected By resetCustomerPassword_button = By.xpath("//button[contains(text(),'Reset Customer')]");
	protected By shopForCustomer_button = By.xpath("//button[contains(text(),'Shop for Customer')]");
	protected By globalComments_button = By.xpath("//button[contains(text(),'Global Comments')]");
	protected By loaderIcon = By.className("preloaderContainer colrs-animate large is-active");
	protected By returnsTab = By.xpath("//div[@id='section_button_customer_service']//li[5]");
	protected By geatIcon = By.xpath("//img[contains(@src , 'gear')]");
	protected By logoutFromCustomerPopup = By.xpath("");
	protected By okayBtn_logoutPopup = By.xpath("(//button[contains(text(),'Okay')])"); 
	//protected By okayBtn_logoutPopup = By.xpath("(//button[contains(text(),'Okay')])[2]");
	protected By orderAlertPopupHeading = By.xpath("//span[text()='Order Alert']");
	protected By orderAlertOkButton = By.id("orderLockConfirmYes");
	// By viewOrderSummary

	public void navigateToLogin() {
		waitForTextPresent(coles_onlineicon_deliverTo, "Deliver to");
		click_custom(getLoginLink, "Login Link To CSR");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get a Column Data
	 * 
	 * @param columnName
	 * @return
	 */
	public List<String> getValidationMsg_excel(String sheetName, String columnName) {
		Xls_Reader reader = new Xls_Reader(System.getProperty("user.dir") + "/src/test/resources/Data/TestData.xlsx");
		List<String> errorMsg = new ArrayList<>();
		int rowCount = reader.getRowCount(sheetName);
		for (int i = 1; i < rowCount; i++) {
			errorMsg.add(reader.getCellData(sheetName, columnName, i + 1));
		}

		return errorMsg;
	}

	/**
	 * 
	 * @param columnName
	 * @param sheetName
	 */
	public void verifyValidationMessage(String columnName, String sheetName) {
		waitForAllElementsPresent(validation_message, "Validation message");
		List<String> actualMsg = new ArrayList<>();
		List<WebElement> getErrmrMsg = DriverFactory.getInstance().getDriver().findElements(validation_message);
		List<String> expectedMsg = getValidationMsg_excel(columnName, sheetName);
		for (int i = 0; i < getErrmrMsg.size(); i++) {

			actualMsg.add(getErrmrMsg.get(i).getText().trim());
		}

		for (int j = 0; j < actualMsg.size(); j++) {
			System.out.println("Expected essage ========> " + actualMsg.get(j));
			assertEquals_custom(actualMsg.get(j), expectedMsg.get(j), "Validation message");
		}
	}

	/**
	 * Navigate to tab
	 * 
	 * @param element
	 * @param field
	 */
	public void navigateToTab(By element, String field) {
		ListenersImplementation.test.get().log(Status.INFO, "Navigating to " + field + " tab");

		click_custom(element, field);
		checkPageIsReady();
		try {
			Thread.sleep(3000); // --As Suggested, localization api takes time
								// to load properly.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Verify tab is highlighted
	 * 
	 * @return
	 */
	public boolean verifyTabHighlighted(By element, String fieldName) {

		waitForElementVisibility(element, fieldName);
		String classAtt = DriverFactory.getInstance().getDriver().findElement(element).getAttribute("class");

		System.out.println("==========>" + classAtt);
		if (classAtt.equals("isactive")) {
			ListenersImplementation.test.get().log(Status.PASS, "The " + fieldName + " tab is highlighted");
			return true;
		} else {

			ListenersImplementation.test.get().log(Status.FAIL, "The " + fieldName + " tab is not highlighted");
			return false;
		}
	}

	/**
	 * Get User Id of Customer
	 * 
	 * @param email
	 * @return
	 */
	public String getUsersID(String email) {

		String query = "select USERS_ID from users where Field1='" + email + "'";
		List<String[]> data = DB_Connection.getDB_Data(query);
		String[] arr = data.get(0);
		return arr[0];
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public List<String> getDetails_DB(String query_01) {
		List<String[]> data_01 = DB_Connection.getDB_Data(query_01);
		List<String> details = new ArrayList<String>();
		for (int i = 0; i < data_01.size(); i++) {

			String[] array = data_01.get(i);
			for (int j = 0; j < array.length; j++) {
				details.add(array[j]);
			}
		}

		for (int k = 0; k < details.size(); k++) {
			ListenersImplementation.test.get().log(Status.INFO, "Value fetched from database: " + details.get(k));
		}
		return details;
	}

	/**
	 * Logout As Customer
	 */
	public void logOutAs_Customer() {
		if (DriverFactory.getInstance().getDriver().findElement(logOutAsCustomer).isDisplayed()) {
			ListenersImplementation.test.get().log(Status.INFO, "Logging out as a Customer");
			click_custom(logOutAsCustomer, "Logout As Customer");
			waitForElementClickable(okayBtn_logoutPopup, "Okay button of Logout from Customer");
			click_custom(okayBtn_logoutPopup, "Okay button");
			waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		}
	}

	/**
	 * Verify The Error Messages From Excel To UI
	 * 
	 * @param errorMsg
	 */
	public void verifyMessage(String errorMsg) {

		ArrayList<String> msg = null;
		String expected = null;
		String actual = null;

		List<String> actualMsg = getValidation_messages();
		for (int i = 0; i < actualMsg.size(); i++) {
			actual = actualMsg.get(i);
			System.out.println("Actual: " + actual);
		}

		if (errorMsg.contains("\n")) {
			System.out.println("Err" + errorMsg);
			msg = new ArrayList<>(Arrays.asList(errorMsg.split("\n")));
			for (int i = 0; i < msg.size(); i++) {
				expected = msg.get(i).trim();
				System.out.println("Expected: " + expected);
			}
		} else {
			msg = new ArrayList<>(Arrays.asList(errorMsg));
			for (int i = 0; i < msg.size(); i++) {
				expected = msg.get(i).trim();
				System.out.println("Expected: " + expected);
			}
		}
		ListenersImplementation.test.get().log(Status.INFO,
				"Actual Message is : " + actual + " and expected message is " + expected + "");
		assertThat("Verifying the error message", actual, equalTo(expected));
	}

	/**
	 * Get Messages
	 * 
	 * @return
	 */
	public List<String> getValidation_messages() {
		waitForAllElementsPresent(validation_messages, "Validation messages");
		List<WebElement> str = DriverFactory.getInstance().getDriver().findElements(validation_messages);
		List<String> msg = new ArrayList<>();

		for (int i = 0; i < str.size(); i++) {
			msg.add(str.get(i).getText().trim());
		}
		return msg;
	}

	/**
	 * Header OF The Page
	 * 
	 * @param pageName
	 * @return
	 */
	public String getHeader(String pageName) {

		return getText_custom(headers_page, pageName);
	}

	/**
	 * verify that the mandatory buttons are visible
	 */
	public void verifyMandatoryButtons() {

		ListenersImplementation.test.get().log(Status.INFO,
				"Global Comments,The Reset Password and Shop for Customer button should be visible");
		boolean visibleflag = DriverFactory.getInstance().getDriver().findElement(resetCustomerPassword_button)
				.isDisplayed();
		boolean enableflag = DriverFactory.getInstance().getDriver().findElement(resetCustomerPassword_button)
				.isEnabled();
		assertThat(visibleflag, equalTo(true));
		assertThat(enableflag, equalTo(true));
		ListenersImplementation.test.get().log(Status.PASS, "The Reset Password button is visible");
		visibleflag = DriverFactory.getInstance().getDriver().findElement(shopForCustomer_button).isDisplayed();
		enableflag = DriverFactory.getInstance().getDriver().findElement(shopForCustomer_button).isEnabled();
		assertThat(visibleflag, equalTo(true));
		assertThat(enableflag, equalTo(true));
		ListenersImplementation.test.get().log(Status.PASS, "The Shop For Customer button is visible");
		visibleflag = DriverFactory.getInstance().getDriver().findElement(globalComments_button).isDisplayed();
		enableflag = DriverFactory.getInstance().getDriver().findElement(globalComments_button).isEnabled();
		assertThat(visibleflag, equalTo(true));
		assertThat(enableflag, equalTo(true));
		ListenersImplementation.test.get().log(Status.PASS, "Global Comments button is visible");
	}

	/**
	 * 
	 */
	public void waitForValidations() {

		waitForAllElementsPresent(validation_messages, "Validation messages");
	}

	public void closePopUp() throws InterruptedException, AWTException {
		Thread.sleep(3000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(5000);
	}

	/**
	 * Customer For Coupons
	 * 
	 * @return
	 */
	public String findCustomer_coupons() {

		List<String> data = getDetails_DB(Queries.getCustomerForCoupons());
		if (data.size() > 0) {
			ListenersImplementation.test.get().log(Status.PASS, "The customer to search is :" + data.get(0));
			return data.get(0);
		} else {
			ListenersImplementation.test.get().log(Status.FAIL, "Unable to fetch customer from database.");
			return null;
		}
	}

	public String getShippedOrder_ID(String email) {

		List<String> data = getDetails_DB(Queries.getOrdersId_shipped(email));

		if (data.size() > 0) {
			ListenersImplementation.test.get().log(Status.PASS, "The Orderid to search is :" + data.get(0));
			return data.get(0);
		} else {
			ListenersImplementation.test.get().log(Status.FAIL, "Unable to fetch the orderId from database");
			return null;
		}
	}

	/**
	 * Get OrderId From Database
	 * 
	 * @param status
	 * @return
	 */
	public String getOrderId_database(String status) {

		String orderId = "null";

		List<String> order = getDetails_DB(Queries.getOrdersId(status));
		if (order.size() == 1) {
			orderId = order.get(0);
			ListenersImplementation.test.get().log(Status.PASS, "The Orderid to search is :" + orderId);
			return orderId;
		} else {
			ListenersImplementation.test.get().log(Status.FAIL, "Unable to fetch the orderId from database");
			return null;
		}
	}

	public void nvigateToShopForCustomer() {

		click_custom(shopForCustomer_button, "Shop for Customer");
		waitForTextPresent(orderAlertPopupHeading, "Order Alert");
		click_custom(orderAlertOkButton, "Order alert okay button ");
		try {
			// API takes time to load the customer profile
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		checkPageIsReady();
	}

	public int getRandomNo(int bound) {

		Random rand = new Random();

		if (bound == 0) {
			return 0;
		}

		else {

			return rand.nextInt(bound - 1);

		}
	}

	public void sleep(long time) {

		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Select A Random Reason For Coupon
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String selectRandomReason(By element, By reasonCount) throws Throwable {

		Random rand = new Random();

		List<WebElement> list = DriverFactory.getInstance().getDriver().findElements(reasonCount);
		int num = rand.nextInt(list.size());
		String selectedOption = selectDropDownByIndex_custom(element, "Reason Dropdown", num);
		return selectedOption;
	}

}
