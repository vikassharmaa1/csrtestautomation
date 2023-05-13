/**
 * 
 */
package com.csr.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.apache.commons.math3.analysis.function.Add;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.utils.DB_Connection;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.Queries;

/**
 * @author akaushi3
 *
 */
public class IdentityPage extends BasePage {

	public static String secondaryTelNum = null;
	public static String secondaryEmailAddress = null;
	public static String secondaryEmailAddress_b2b = null;

	By header_Identity = By.xpath("//h1[text()='Identity']");
	By businessCustomer_checkbox = By.xpath("//input[@name='isBusinessUser']");
	By businessType_dropdown = By.name("businessType");
	By abn_textbox = By.id("abn-input");
	By businessname_textbox = By.id("businessName-input");
	By title_textbox = By.id("title-input");
	By firstName_textbox = By.id("firstName-input");
	By lastname_textbox = By.id("lastName-input");
	By primaryEmail_textbox = By.name("email1");
	By primaryTelNum_textbox = By.name("phone1");
	By phoneType = By.name("phone1Type");
	By suburb_textbox = By.name("city");
	By postalCode_textbox = By.name("zipCode");
	By alertFlag_checkbox = By.name("alertFlag");
	By rosieFlag_dropdown = By.name("rosieFlag");
	By preauth_checkbox = By.name("preAuth");
	By securityPref_dropdown = By.name("securityPreference");
	By secondaryEmail = By.name("email2");
	By secondaryPhone = By.name("phone2");
	By secondaryPhoneType = By.name("phone2Type");
	By update_Button = By.xpath("//button[contains(text(),'Update')]");
	By identityTab = By.xpath("//div[@id='section_button_customer_service']//li[1]"); // By.xpath("//a[text()='Identity']//parent::li");
	By successMessage = By.xpath("//*[contains(text(),'Customer has been updated successfully!')]");
	By organisationName_textbox = By.id("organizationName");
	By organisationUnitName_textbox = By.xpath("//input[@data-ng-model='profileVM.organizationUnitName']");

	/**
	 * get attribute of text oxes
	 * 
	 * @param element
	 * @param fieldName
	 * @return
	 */
	public String verify_CustomerDetails(By element, String fieldName) {

		return getAttribute_value_custom(element, fieldName);
	}

	/**
	 * Get First Name
	 * 
	 * @return
	 */
	public String getFirstName() {

		return verify_CustomerDetails(firstName_textbox, "First Name");
	}

	/**
	 * Get Last Name
	 * 
	 * @return
	 */
	public String getLastName() {

		return verify_CustomerDetails(lastname_textbox, "Last Name");
	}

	/**
	 * Business type
	 * 
	 * @return
	 */
	public String getBusinessType() {

		return getSelectedOption_Dropdown(businessType_dropdown, "Business Type");
	}

	/**
	 * Business name
	 * 
	 * @return
	 */
	public String getBusinessName() {

		return getAttribute_value_custom(businessname_textbox, "value");
	}

	/**
	 * ABN Number
	 * 
	 * @return
	 */
	public String getABN_number() {

		return getAttribute_value_custom(abn_textbox, "ABN textbox");
	}

	/**
	 * Get Email Address
	 * 
	 * @return
	 */
	public String getEmailAddress() {

		return verify_CustomerDetails(primaryEmail_textbox, "Primary Email Address");
	}

	public boolean businessCustomerCheckbox() {

		return DriverFactory.getInstance().getDriver().findElement(businessCustomer_checkbox).isSelected();
	}

	public void verifyB2Bcheckbox_selected() {

		boolean flag = businessCustomerCheckbox();

		if (flag == true) {
			ListenersImplementation.test.get().log(Status.PASS, "Business customer checkbox is checked.");
		} else {
			ListenersImplementation.test.get().log(Status.FAIL, "Business customer checkbox is unchecked.");
		}
	}

	/**
	 * Get Telephone Number
	 * 
	 * @return
	 */
	public String getTelephoneNumber() {

		return verify_CustomerDetails(primaryTelNum_textbox, "Telephone Number");
	}

	/**
	 * Identity Tab Is Selected
	 * 
	 * @return
	 */
	public boolean identityTabSelected() {

		waitForElementVisibility(identityTab, "Identity tab header");
		String classAtt = DriverFactory.getInstance().getDriver().findElement(identityTab).getAttribute("class");
		if (classAtt.equals("isactive")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get The Data From Address Table
	 * 
	 * @param email
	 * @return
	 */
	public String getAddressTable_Details(String email, String databaseFieldName) {
		System.out.println(">>>>>>>>>>>>>>>>>" + email + " >>>>>" + databaseFieldName);
		List<String> details_DB = getDetails_DB(Queries.getSecondaryDetails(email, databaseFieldName));
		System.out.println(Queries.getSecondaryDetails(email, databaseFieldName));
		String emailId = null;
		for (int i = 0; i < details_DB.size(); i++) {
			System.out.println("SIZE ===========> " + details_DB.size());
			emailId = details_DB.get(i);
			System.out.println("THE EMAIL IS >>>>>>>>>>>>>>>>>>>>>" + emailId);
		}

		return emailId;
	}

	public String getUpdatedValue_DB(String email, String databaseColumn, int value) {
		String query = "select Users_ID from users where Field1='" + email + "'";

		List<String[]> data = DB_Connection.getDB_Data(query);
		String[] arr = data.get(0);

		query = "select " + databaseColumn + " from mbrattrval where member_id=" + arr[0] + " and mbrattr_id = " + value
				+ "";
		List<String[]> data_01 = DB_Connection.getDB_Data(query);
		String[] updatedValue = data_01.get(0);
		return updatedValue[0];

	}

	public void enterSecondaryDetails() throws Throwable {
		// secondaryEmailAddress = AddCustomerPage.FIRSTNAME.toLowerCase() +
		// AddCustomerPage.LASTNAME.toLowerCase()
		// + "@getnada.com";
		secondaryEmailAddress = "testanyday@getnada.com";
		// secondaryTelNum = AddCustomerPage.generateRandomPhoneNumber();
		secondaryTelNum = "0412345678";
		sendKeys_custom(secondaryEmail, "Secondary Email", secondaryEmailAddress);
		sendKeys_custom(secondaryPhone, "Secondary Telephone Number", secondaryTelNum);
		selectDropDownByVisibleText_custom(secondaryPhoneType, "Secondary Telephone Type", "Mobile Number");
		Thread.sleep(5000);
		click_custom(update_Button, "Update Button");
		waitForElementVisibility(successMessage, "Customer records update message");
		// checkPageIsReady();
		// updateSecurityPref("Enabled");
		// Thread.sleep(5000); //added due to wait issue
		// waitForElementVisibility(successMessage, "Customer records update message");
	}

	public void enterSecondaryDetails_businessCustomer() throws Throwable {
		secondaryEmailAddress_b2b = AddCustomerPage.businessCust_Fname.toLowerCase()
				+ AddCustomerPage.businessCust_Lname.toLowerCase() + "@getnada.com";
		secondaryTelNum = AddCustomerPage.generateRandomPhoneNumber();
		sleep(10000);
		sendKeys_custom(secondaryEmail, "Secondary Email", secondaryEmailAddress_b2b);
		sendKeys_custom(secondaryPhone, "Secondary Telephone Number", secondaryTelNum);
		selectDropDownByVisibleText_custom(secondaryPhoneType, "Secondary Telephone Type", "Mobile Number");
		click_custom(update_Button, "Update Button");
		waitForElementVisibility(successMessage, "Customer records update message");
		checkPageIsReady();
	}

	public void updateRosieFlag(String value) throws Throwable {

		selectDropDownByVisibleText_custom(rosieFlag_dropdown, "Rosie Flag", value);
	}

	public void updateSecurityPref(String value) throws Throwable {

		Thread.sleep(2000);
		selectDropDownByVisibleText_custom(securityPref_dropdown, "2FA", value);
	}

	public void updateRecords() throws InterruptedException {
		click_custom(update_Button, "Update button");
		checkPageIsReady();
		waitForElementVisibility(successMessage, "Customer records update message");
	}

	public void clearInputs() throws Throwable {

		sendKeys_custom(firstName_textbox, "First Name", " ");
		sendKeys_custom(lastname_textbox, "Last Name", " ");
		sendKeys_custom(primaryEmail_textbox, "Primary Email", " ");
		sendKeys_custom(primaryTelNum_textbox, "Primary Telephone", " ");
		selectDropDownByVisibleText_custom(phoneType, "Primary Phine type", "Select");
		sendKeys_custom(suburb_textbox, "Suburb", " ");
		sendKeys_custom(postalCode_textbox, "Post Code", " ");
		click_custom(update_Button, "Update Button");
		checkPageIsReady();
	}

	/**
	 * Get Rosie Flag Options CSR
	 * 
	 * @return
	 */
	public List<String> getRosieFlagOptons_CSR() {

		List<WebElement> options = new Select(DriverFactory.getInstance().getDriver().findElement(rosieFlag_dropdown))
				.getOptions();
		List<String> opt = new ArrayList<String>();

		for (int i = 1; i < options.size(); i++) {

			opt.add(options.get(i).getText().trim());
			ListenersImplementation.test.get().log(Status.INFO,
					"Value fetched from CSR Identity tab is: " + options.get(i).getText().trim());
		}
		return opt;

	}

	public String getOrganisationName() {
		System.out.println(organisationName_textbox);
		return verify_CustomerDetails(organisationName_textbox, "Organisation Name");
	}

	public String getorganisationUnitName() {

		return verify_CustomerDetails(organisationUnitName_textbox, "Organisation Unit Name");
	}

	// Here we are getting Organisation Name
	public String getOrganisationNameFromEmailId(String emailId) {
		// if (!Objects.isNull(Queries.getDistinguishName(emailId))) {
		// DN = getDetails_DB(Queries.getDistinguishName(emailId)).get(0); //getting
		// data from 1st position into db
		String DN = !getDetails_DB(Queries.getDistinguishName(emailId)).isEmpty()
				? getDetails_DB(Queries.getDistinguishName(emailId)).get(0)
				: "";
		String skipStandaloneCustomerDN[] = DN.split(",", 2); // o=vishalsub,o=vsihal,o=coles,0=cgl
		String organisationName = "";
		if (skipStandaloneCustomerDN[1].contains("o=coles b2b buyer organization")
				&& !skipStandaloneCustomerDN[1].startsWith("o=coles b2b buyer organization")) {

			if (skipStandaloneCustomerDN[1].split(",").length > 3) {
				String str[] = skipStandaloneCustomerDN[1].split(",", 2);
				System.out.println(skipStandaloneCustomerDN[1] + "line no 309---->" + str[1]);
				organisationName = getDetails_DB(Queries.getOrgNameFromDB(str[1].toString())).get(0);

			} else {
				String str[] = skipStandaloneCustomerDN[1].split(",", 2);
				System.out.println(skipStandaloneCustomerDN[1] + "----->>>>");
				organisationName = getDetails_DB(Queries.getOrgNameFromDB(skipStandaloneCustomerDN[1])).get(0);
			}

		}
		return organisationName.toLowerCase();
	}

	// here we are getting OrganisationUnit Name
	public String getOrganisationUnitNameFromEmailId(String emailId) {
		String DN = "";
		if (!Objects.isNull(Queries.getDistinguishName(emailId))) {
			DN = getDetails_DB(Queries.getDistinguishName(emailId)).get(0);
		}

		String resultOrgUnitName = "";
		if (DN.split(",").length > 4) {
			String str[] = DN.split(",", 2);
			System.out.println("str[1] org Unit value is--->" + str[1] + "----------------");
			System.out.println(getDetails_DB(Queries.getOrgNameFromDB(str[1])) + "---->orgunit");
			resultOrgUnitName = getDetails_DB(Queries.getOrgNameFromDB(str[1].trim())).get(0);
		}
		return resultOrgUnitName.toLowerCase();

	}

	public String getEmailIdForB2B() {
		return getDetails_DB(Queries.getEmailIdForB2BFromDB()).get(0);
	}

}
