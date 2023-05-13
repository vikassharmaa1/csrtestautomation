/**
 * 
 */
package com.csr.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.CheckForNull;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.utils.DB_Connection;
import com.csr.utils.ListenersImplementation;

/**
 * @author akaushi3
 *
 */
public class DetailsPage extends BasePage {

	By sapAccount_textbox = By.id("sapAccount-input");
	By minimumSpend_textbox = By.id("minimumSpend-input");
	By substitutions_checkbox = By.name("substitution");
	By unattendedDelivery_checkbox = By.name("unattendedDelivery");
	By redemption_checkbox = By.name("redemptionLimit");
	By flypal_checkbox = By
			.xpath("//label[contains(text(),'FlyPay')]//preceding-sibling::input[@name='paymentMethod']");
	By paypal_checkbox = By
			.xpath("//label[contains(text(),'PayPal')]//preceding-sibling::input[@name='paymentMethod']");
	By creditcard_checkbox = By
			.xpath("//label[contains(text(),'Credit Card')]//preceding-sibling::input[@name='paymentMethod_2']");
	By customerAccount_checkbox = By
			.xpath("//label[contains(text(),'Customer Account')]//preceding-sibling::input[@name='paymentMethod_3']");
	By unattendedHomeAlone_checkbox = By.xpath("//input[@name='unattended']");
	By betterBag_checkbox = By
			.xpath("//label[contains(text(),'Better Bag')]/..//preceding-sibling::div//input[@name='bagging']");
	By bagless_checkbox = By
			.xpath("//label[contains(text(),'Bagless')]/..//preceding-sibling::div//input[@name='bagging']");
	By dayOfBirth_textbox = By.id("day");
	By mohthOfBirth_dropdown = By.id("month");
	By yearOfBirth_textbox = By.id("year");
	By update_button = By.xpath("//button[contains(text(),'Update')]");
	By DetailsTab = By.xpath("//div[@id='section_button_customer_service']//li[2]");
	By details_header = By.xpath("//h1[text()='Details']");
	By successMessage = By.xpath("//*[contains(text(),'Customer has been updated successfully!')]");
	By flyBys_textbox = By.id("flybuy-input");
	By staffDiscountCard = By.id("discountCard-input");
	By errorMessages= By.xpath("//div[@class='colrs-animate error-message']");
	By costCentreErrorMessage=By.xpath("//div[@class='colrs-animate validation-messages']//li[contains(text(),'Please enter')]");
	By costCentre_textbox = By.xpath("//input[@id='costCenter-input']");
	By hubStore_checkbox = By.xpath("//input[@id='isHubStore']");

	/**
	 * Verify Payment Method
	 * 
	 * @param paymentMethod
	 * @param fieldName
	 */
	public void verifyDefaultCheckedOptions(By paymentMethod, String fieldName) {

		ListenersImplementation.test.get().log(Status.INFO, fieldName + " should be selected by default");

		boolean flag = false;
		flag = DriverFactory.getInstance().getDriver().findElement(paymentMethod).isSelected();
		System.out.println("=====> "+flag);
		if (flag) {
			ListenersImplementation.test.get().log(Status.PASS, fieldName + " is selected by default");
		} else {
			ListenersImplementation.test.get().log(Status.FAIL, fieldName + " is not selected by default");
		}
	}

	/**
	 * Payment Method For Customer Account
	 */
	public void verifycustomerAccountPaymentMethod() {

		ListenersImplementation.test.get().log(Status.INFO,
				"Customer Account should be disabled if it is a B2C Customer");

		String value = getAttribute_value_custom(sapAccount_textbox, "value");

		boolean flag = DriverFactory.getInstance().getDriver().findElement(customerAccount_checkbox).isSelected();

		if (value.isEmpty()) {

			ListenersImplementation.test.get().log(Status.INFO,
					"This is a B2C customer and Customer Account payment should be disabled");

			if (flag == false) {
				ListenersImplementation.test.get().log(Status.PASS, "Customer Account payment option is disabled");
			} else {
				ListenersImplementation.test.get().log(Status.FAIL, "Customer Account payment option is enabled");
			}
		}

		else {

			ListenersImplementation.test.get().log(Status.INFO,
					"This is a B2B customer and Customer Account payment should be disabled");
			if (flag) {
				ListenersImplementation.test.get().log(Status.PASS, "Customer Account payment option is disabled");
			} else {
				ListenersImplementation.test.get().log(Status.FAIL, "Customer Account payment option is enabled");
			}
		}
	}

	/**
	 * Credit Card
	 */
	public void creditCard_PaymentMethod() {

		verifyDefaultCheckedOptions(creditcard_checkbox, "Credit Card Payment Option");
	}

	/**
	 * Pay Pal
	 */
	public void payPal_paymentMethod() {

		verifyDefaultCheckedOptions(paypal_checkbox, "Pay Pal Payment Option");
	}

	/**
	 * Fly Pay
	 */
	public void flyPay_paymentMethod() {

		verifyDefaultCheckedOptions(flypal_checkbox, "Fly Pay Payment Method");
	}

	/**
	 * 
	 */
	public void substitutionsCheckbox() {

		verifyDefaultCheckedOptions(substitutions_checkbox, "Substitutions Checkbox");
	}

	/**
	 * 
	 */
	public void redemptionLimitCheckboxStatus() {

		verifyDefaultCheckedOptions(redemption_checkbox, "Redemption Limit Checkbox");
	}

	/**
	 * 
	 */
	public void unattendedDeliveryStatus() {

		verifyDefaultCheckedOptions(unattendedDelivery_checkbox, "Unattended Delivery Checkbox");
	}

	public void clickDetailsTab() {

		click_custom(DetailsTab, "Details Tab");
		checkPageIsReady();
		waitForElementVisibility(details_header, "Details tab header");
		checkPageIsReady();
	}

	/**
	 * Details Tab
	 * 
	 * @return
	 */
	public boolean detailsTab_Highlighted() {

		waitForElementVisibility(DetailsTab , "Details tab header");

		String classAtt = DriverFactory.getInstance().getDriver().findElement(DetailsTab).getAttribute("class");

		if (classAtt.equals("isactive")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Page Header
	 * 
	 * @return
	 */
	public String getHeader() {

		return getText_custom(details_header, "Details Tab Header");
	}

	/**
	 * 
	 * @return
	 * @throws ParseException
	 */
	public String getDateOfBirth() throws ParseException {

		ListenersImplementation.test.get().log(Status.INFO,
				"Getting the date of birth of customer from details tab");
		String dob = null;

		String date = getAttribute_value_custom(dayOfBirth_textbox, "Day of birth");
		String year = getAttribute_value_custom(yearOfBirth_textbox, "Year");
		String month = new Select(DriverFactory.getInstance().getDriver().findElement(mohthOfBirth_dropdown))
				.getFirstSelectedOption().getText().trim();
		System.out.println("=====>" + month);

		DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ENGLISH);
		TemporalAccessor accessor = parser.parse(month);
		int i = accessor.get(ChronoField.MONTH_OF_YEAR);
		String m;
		if(i < 10) 
		{
			m="0" + i;
		}
		else
		{
			m=String.valueOf(i);
		}
		dob = year + "-" + m + "-" + date;
		ListenersImplementation.test.get().log(Status.PASS,
				"Date of birth of customer available on details tab is +" + dob);
		return dob;
	}

	/**
	 * Get DB Updated Value
	 * 
	 * @param email
	 * @param databaseColumn
	 * @return
	 */
	public String getUpdatedValue_DB(String email, String databaseColumn) {

		ListenersImplementation.test.get().log(Status.INFO,
				"Getting the " + databaseColumn + " of customer from database");
		String query = "select USERS_ID from users where Field1='" + email + "'";

		List<String[]> data = DB_Connection.getDB_Data(query);
		String[] arr = data.get(0);

		query = "select " + databaseColumn + " from userdemo where users_id=" + arr[0] + "";

		data.clear();
		data = DB_Connection.getDB_Data(query);
		String[] updatedValue = data.get(0);
		ListenersImplementation.test.get().log(Status.PASS,
				"The " + databaseColumn + " of customer is " + updatedValue[0]);

		return updatedValue[0];

	}

	/**
	 * Update Delivery and Substitutions Options
	 */
	public void updateDeliveryChoices() {

		ListenersImplementation.test.get().log(Status.INFO,
				"Unchecking Substitutions Unattended delivery and Redemption Checkbox");

		uncheck_checkBox_Custom(substitutions_checkbox, "Substitution Option");
		uncheck_checkBox_Custom(unattendedDelivery_checkbox, "Unattended Delivery Option");
		uncheck_checkBox_Custom(redemption_checkbox, "Redemption Limit");
		select_checkBox_Custom(unattendedHomeAlone_checkbox, "Unattended Delivery Home Alone");
		//scrollIntoView(customerAccount_checkbox, "Customer Account");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//click_custom(bagless_checkbox, "Bagless");
		sleep(2000);
		click_custom(update_button, "Update button");
		waitForElementVisibility(successMessage , "Success messages");
		checkPageIsReady();
	}

	/**
	 * Delivery Value Updated In DB
	 * @param email
	 * @param value
	 * @return
	 */
	public String getDeliveryUpdateValues_DB(String email, String value) {

		String query = "select USERS_ID from users where Field1='" + email + "'";

		List<String[]> data = DB_Connection.getDB_Data(query);
		String[] arr = data.get(0);

		query = "select IntegerValue from mbrattrval where member_id=" + arr[0] + " and mbrattr_id = " + value + "";
		System.out.println("=====>" + query);
		data.clear();
		data = DB_Connection.getDB_Data(query);
		String[] updatedValue = data.get(0);
		return updatedValue[0];
	}
	
	/**
	 * Minimum Spend can not be Zero
	 * @throws InterruptedException 
	 */
	public void verifyMinimumSpendLimit() throws InterruptedException{
		
		ListenersImplementation.test.get().log(Status.INFO, "Minimum spend should not be set to 0");
		
		sendKeys_custom(minimumSpend_textbox, "Minimum Spend", "0");
		click_custom(update_button, "Update Button");
		Thread.sleep(500);
		waitForTextPresent(validation_message, "Minimum Spend Limit must contain value greater than zero. Number should not start with 0");
		
		refresh();
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		sendKeys_custom(minimumSpend_textbox, "Minimum Spend", "1");
		click_custom(update_button, "Update button");
		sleep(4000);
		waitForTextPresent(successMessage, "Customer has been updated successfully!");
		String actualValue = getAttribute_value_custom(minimumSpend_textbox, "value");
		assertEquals_custom(actualValue, "1", "Minimum Spend");
	}
	
	public void verifySapNumber_functionality(){
		
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the SAP Number validations");
		sendKeys_custom(sapAccount_textbox, "Sap account", "123321");
		click_custom(update_button, "Update button");
		waitForTextPresent(validation_message, "Customer Account must be 10 digit");
		refresh();
		checkPageIsReady();
		sendKeys_custom(sapAccount_textbox, "Sap account", "1234567890");
		click_custom(update_button, "Update button");
		waitForTextPresent(successMessage, "Customer has been updated successfully!");
		checkPageIsReady();
		scrollIntoView(customerAccount_checkbox, "Customer Account");
		select_checkBox_Custom(customerAccount_checkbox, "Customer Account");
		boolean flag = DriverFactory.getInstance().getDriver().findElement(customerAccount_checkbox).isSelected();
		if(flag){
			ListenersImplementation.test.get().log(Status.PASS, "Customer account checkbox is linked with SAP Number");
		}
		else {
			ListenersImplementation.test.get().log(Status.FAIL, "Customer account checkbox is not linked with SAP Number");
		}
	}
	
	public void updateFlyBysNumber(){
		
		ListenersImplementation.test.get().log(Status.INFO, "Verify Flybys and Staff Discount Card validation messages");
		
		sendKeys_custom(flyBys_textbox, "Flybys number", "12312312");
		click_custom(update_button, "Update button");
		sleep(5000);
		waitForTextPresent(errorMessages, "Error Flybuy Validation");
		refresh();
		checkPageIsReady();
		sendKeys_custom(staffDiscountCard, "Staff Discount Card", "1231232");
		click_custom(update_button, "Update button");
		waitForTextPresent(errorMessages, "Error Team Discount Number Validation");
	}
	
	public void updateCostCentreNumber() {

		ListenersImplementation.test.get().log(Status.INFO,
				"Verify Cost Centre validation messages");
		sendKeys_custom(costCentre_textbox, "Cost Centre", "12@12");
		click_custom(update_button, "Update button");
		sleep(2000);
		waitForTextPresent(costCentreErrorMessage, "Error Cost Centre Validation");
		refresh();
		checkPageIsReady();
		sendKeys_custom(costCentre_textbox, "Cost Centre", "1231232");
		click_custom(update_button, "Update button");
		sleep(2000);
		waitForTextPresent(successMessage, "Customer has been updated successfully!");
	
	}
	
	public void hubStoreCheckBox() 
	{
		ListenersImplementation.test.get().log(Status.INFO, "Verify hubStoreCheckBox ");
		 sleep(5000);
		 select_checkBox_Custom(hubStore_checkbox, "Customer Account");
		try {
		 Thread.sleep(1000);
		 } catch (InterruptedException e) {
		
		e.printStackTrace();
		}
		click_custom(update_button, "Update button");
		 waitForElementVisibility(successMessage, "Success messages");
		 checkPageIsReady();
		
	}
}
