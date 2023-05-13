/**
 * 
 */
package com.csr.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.PropertiesOperations;
import com.github.javafaker.Faker;

/**
 * @author akaushi3
 *
 */
public class AddCustomerPage extends BasePage {

	BasePage basepage = new BasePage();

	public static String FIRSTNAME = null;
	public static String LASTNAME = null;
	public static String TELEPHONENUMBER = "0456789456";
	Faker faker = new Faker();
	public static String businessCust_Fname = null;
	public static String businessCust_Lname = null;
	public static String businessCust_TelNum = null;
	public static String businessName = null;
	public static String businessType = null;
	public static String abnNumber = "51824753556";

	By addCustomerTitle = By.xpath("//p[contains(text(),'Create a Customer Account')]");
	By create_Button = By.xpath("//button[contains(text(),'Create')]");
	By firstname_textbox = By.id("firstName-input");
	By lastname_textbox = By.id("lastName-input");
	By primaryEmail_textbox = By.name("email1");
	By primaryTelNum_textbox = By.name("phone1");
	By phoneType = By.name("phone1Type");
	By addressType_textbox = By.name("addressType");
	By addressLine_textbox = By.name("address1");
	By suburb_textbox = By.name("city");
	By postalCode_textbox = By.name("zipCode");
	By stateDropdown = By.id("stateprovince");
	By geoCode_button = By.xpath("//button[contains(text(),'Get Geo Code')]");
	By geoCode_textbox = By.name("geocode");
	By successMessage = By.xpath("//*[contains(text(),'Customer has been created successfully!')]");
	By duplicate_emailmessage = By.xpath("//*[contains(text(),'A customer is already registered with this email')]");
	By businessCustomer_checkbox = By.name("isBusinessUser");
	By businessType_dropdown = By.name("businessType");
	By abn_textbox = By.id("abn-input");
	By businessname_textbox = By.id("businessName-input");
	By validation_message = By.xpath("//div[@class='colrs-animate validation-messages']//ul//li");
	By cancel_button = By.xpath("//div[@class='row register-submit']//*[contains(text(),'Cancel')]");
	By day_textbox = By.id("day");
	By month_dropdown = By.id("month");
	By year_textbox = By.id("year");
	By country_dropdown = By.name("country");
	By lastNameMandatory_Message = By.xpath("//div[@class='colrs-animate validation-messages']//ul//li");
	By firstNameMandatory_Message = By.xpath("//div[@class='colrs-animate validation-messages']//ul//li");

	public AddCustomerPage() {

		// waitForTextPresent(addCustomerTitle, "Register A New Customer");

	}

	public void enterBusinessCustomerEssentials() throws Throwable {

		Random ran = new Random();
		waitForElementVisibility(businessCustomer_checkbox, "Business checkbox");
		click_BusnessCheckBox();
		businessName = "E-Commerce Pvt Ltd ";
		sendKeys_custom(businessname_textbox, "Business name", businessName);
		businessType = (selectDropDownByIndex_custom(businessType_dropdown, "Business type", 5));
		sendKeys_custom(abn_textbox, "ABN textbox", "51824753556");
	}

	/**
	 * Get Error Message from Screen
	 * 
	 * @param columnName
	 */
	public List<String> getValidationMessage_CSR() {

		click_custom(create_Button, "Create Button");

		waitForAllElementsPresent(validation_message, "Valudation Messages");
		List<String> actualMsg = new ArrayList<>();
		List<WebElement> getErrmrMsg = DriverFactory.getInstance().getDriver().findElements(validation_message);
		for (int i = 0; i < getErrmrMsg.size(); i++) {

			actualMsg.add(getErrmrMsg.get(i).getText().trim());
		}
		System.out.println("=====>" + actualMsg);
		return actualMsg;

	}

	/**
	 * Provide Invalid Inputs
	 * 
	 * @throws Throwable
	 */
	public void invalidinputDetails_addCustomer() throws Throwable {
		click_custom(businessCustomer_checkbox, "Business Customer Checkbox");

		sendKeys_custom(businessname_textbox, "Business Name", "!@#123");
		sendKeys_custom(abn_textbox, "ABN", "123");
		sendKeys_custom(firstname_textbox, "First Name", "!@#123");
		sendKeys_custom(lastname_textbox, "Last Name", "!@#123");
		sendKeys_custom(day_textbox, "Day of birth", "29");
		selectDropDownByVisibleText_custom(month_dropdown, "Month of Birth", "March");
		sendKeys_custom(year_textbox, "Year of birth", "2030");
		sendKeys_custom(primaryEmail_textbox, "Primary email id", "test.com");
		sendKeys_custom(primaryTelNum_textbox, "Telephone No", "0123456789");
		selectDropDownByVisibleText_custom(phoneType, "Phone Type", "Mobile Number");
		sendKeys_custom(addressType_textbox, "Address Nick Name", "!@#123");
		sendKeys_custom(addressLine_textbox, "Address Line", "!@#123");
		sendKeys_custom(suburb_textbox, "Suburb", "!@#123");
		selectDropDownByVisibleText_custom(stateDropdown, "State", "VIC");
		sendKeys_custom(postalCode_textbox, "Post Code", "!@#1");
		click_custom(geoCode_button, "Get Geo Code");
		Thread.sleep(2000);
		click_custom(geoCode_button, "Get Geo Code");
		Thread.sleep(2000);
		click_custom(geoCode_button, "Get Geo Code");
		click_custom(create_Button, "Create button");
	}

	/**
	 * Generate random mobile number
	 * 
	 * @return
	 */
	public static String generateRandomPhoneNumber() {
		Random ran = new Random();
		int n = ran.nextInt(99999999);
		String number = String.format("%08d", n);
		number = "04" + number;
		return number;
	}

	/**
	 * Method To Input First Name , LastName, Email And Telephone Number
	 * 
	 * @throws Throwable
	 */
	public void enter_fName_lName_telNum() throws Throwable {

		FIRSTNAME = faker.name().firstName();
		LASTNAME = faker.name().lastName();
		TELEPHONENUMBER = generateRandomPhoneNumber();
		Thread.sleep(2000);
		sendKeys_custom(firstname_textbox, "First Name", FIRSTNAME);
		sendKeys_custom(lastname_textbox, "Last Name", LASTNAME);
		sendKeys_custom(primaryEmail_textbox, "Email Id",
				FIRSTNAME.toLowerCase() + "." + LASTNAME.toLowerCase() + "@getnada.com");
		sendKeys_custom(primaryTelNum_textbox, "Telephone Number", TELEPHONENUMBER);
		selectDropDownByVisibleText_custom(phoneType, "Phone Type", "Mobile Number");
	}

	public void enter_businessCustomerEssentialDetails() throws Throwable {
		Faker fake = new Faker();
		businessCust_Fname = fake.name().firstName();
		businessCust_Lname = fake.name().lastName();
		businessCust_TelNum = generateRandomPhoneNumber();
		sendKeys_custom(firstname_textbox, "First Name", businessCust_Fname);
		sendKeys_custom(lastname_textbox, "Last Name", businessCust_Lname);
		sendKeys_custom(primaryEmail_textbox, "Email Id",
				businessCust_Fname.toLowerCase() + "." + businessCust_Lname.toLowerCase() + "@getnada.com");
		sendKeys_custom(primaryTelNum_textbox, "Telephone Number", businessCust_TelNum);
		selectDropDownByVisibleText_custom(phoneType, "Phone Type", "Mobile Number");
	}

	/**
	 * Entering duplicate details for verifying duplicate email message
	 * 
	 * @throws Throwable
	 */
	public void enterDuplicateEmail() throws Throwable {
		waitForElementVisibility(firstname_textbox, "First Name");
		sendKeys_custom(firstname_textbox, "First Name", FIRSTNAME);
		sendKeys_custom(lastname_textbox, "Last Name", LASTNAME);
		sendKeys_custom(primaryEmail_textbox, "Email Id",
				FIRSTNAME.toLowerCase() + "." + LASTNAME.toLowerCase() + "@getnada.com");
		sendKeys_custom(primaryTelNum_textbox, "Telephone Number", TELEPHONENUMBER);
		selectDropDownByVisibleText_custom(phoneType, "Phone Type", "Mobile Number");

	}

	/**
	 * 
	 * @throws Throwable
	 */
	public void enter_OtherValidDetails() throws Throwable {
		sendKeys_custom(day_textbox, "Day of birth", "29");
		selectDropDownByVisibleText_custom(month_dropdown, "Month of Birth", "March");
		sendKeys_custom(year_textbox, "Year of birth", "1990");
		sendKeys_custom(addressType_textbox, "Address Nick Name",
		PropertiesOperations.getPropertyValueByKey("addresstype"));
		sendKeys_custom(addressLine_textbox, "Address Line", PropertiesOperations.getPropertyValueByKey("addressLine"));
		sendKeys_custom(suburb_textbox, "Suburb", PropertiesOperations.getPropertyValueByKey("suburb"));
		selectDropDownByVisibleText_custom(stateDropdown, "State", PropertiesOperations.getPropertyValueByKey("state"));
		sendKeys_custom(postalCode_textbox, "Post Code", PropertiesOperations.getPropertyValueByKey("postCode"));
		sendKeys_custom(geoCode_textbox, "Geo Code", "-31.12345,121.12345"); // --Due to app or browser cache issue
		// click_custom(geoCode_button, "Get Geo Code");
		Thread.sleep(2000);
		click_custom(create_Button, "Create button");
		waitForInvisibilityofElement(basepage.loaderIcon, "Loader Icon");
	}

	public void enter_OtherValidDetailsForDuplicateEmail() throws Throwable {
		sendKeys_custom(day_textbox, "Day of birth", "29");
		selectDropDownByVisibleText_custom(month_dropdown, "Month of Birth", "March");
		sendKeys_custom(year_textbox, "Year of birth", "1990");
		sendKeys_custom(addressType_textbox, "Address Nick Name",
				PropertiesOperations.getPropertyValueByKey("addresstype"));
		sendKeys_custom(addressLine_textbox, "Address Line", PropertiesOperations.getPropertyValueByKey("addressLine"));
		sendKeys_custom(suburb_textbox, "Suburb", PropertiesOperations.getPropertyValueByKey("suburb"));
		selectDropDownByVisibleText_custom(stateDropdown, "State", PropertiesOperations.getPropertyValueByKey("state"));
		sendKeys_custom(postalCode_textbox, "Post Code", PropertiesOperations.getPropertyValueByKey("postCode"));
		sendKeys_custom(geoCode_textbox, "Geo Code", "-31.12345,121.12345"); // --Due to app or browser cache issue
		// click_custom(geoCode_button, "Get Geo Code");
		Thread.sleep(2000);
		click_custom(create_Button, "Create button");

	}

	/**
	 * Get Validation Message For Duplicate Email
	 * 
	 * @return
	 */
	public String getDuplicateEmailMessage() {
		waitForElementVisibility(duplicate_emailmessage, "Duplicate email error message");
		return getText_custom(duplicate_emailmessage, "Duplicate Email Validation");
	}

	public String getMandatoryLastNameMessage() {
		waitForElementVisibility(lastNameMandatory_Message, "Last name mandatory message");
		return getText_custom(lastNameMandatory_Message, "Last name mandatory message");
	}

	public String getMandatoryFirstNameMessage() {
		waitForElementVisibility(firstNameMandatory_Message, "First name mandatory message");
		return getText_custom(firstNameMandatory_Message, "First name mandatory message");
	}

	/**
	 * Get State Of ANB
	 * 
	 * @return
	 */
	public boolean getABN_state() {
		ListenersImplementation.test.get().log(Status.INFO,
				MarkupHelper.createLabel("Getting the state of ABN field", ExtentColor.BLUE));
		return getElementState_custom(abn_textbox, "ABN");
	}

	/**
	 * Get state of Business Type
	 * 
	 * @return
	 */
	public boolean getBusinessType_state() {
		ListenersImplementation.test.get().log(Status.INFO,
				MarkupHelper.createLabel("Getting the state of Business Type field", ExtentColor.BLUE));
		return getElementState_custom(businessType_dropdown, "Business Type");
	}

	/**
	 * Get State Of Business Name
	 * 
	 * @return
	 */
	public boolean getBusinessName_state() {
		ListenersImplementation.test.get().log(Status.INFO,
				MarkupHelper.createLabel("Getting the state of Business Name field", ExtentColor.BLUE));
		return getElementState_custom(businessname_textbox, "Business Name");
	}

	/**
	 * Select Biusiness Customer Checkbox
	 */
	public void click_BusnessCheckBox() {

		boolean flag = false;

		flag = DriverFactory.getInstance().getDriver().findElement(businessCustomer_checkbox).isSelected();
		if (!flag) {
			click_custom(businessCustomer_checkbox, "Business Customer Checkbox");
		}
	}

	public String getSuccessMessage_createCustomer() {

		waitForElementVisibility(successMessage, "Customer creation success message");
		return getText_custom(successMessage, "Success Message");
	}

	/**
	 * Get Selected Option
	 * 
	 * @return
	 */
	public String verifyDefaultSelectedCountry() {

		return new Select(DriverFactory.getInstance().getDriver().findElement(country_dropdown))
				.getFirstSelectedOption().getText().trim();
	}

	public void click_cancelBtn() {

		click_custom(cancel_button, "Cancel Button");
	}

}
