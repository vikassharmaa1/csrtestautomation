/**
 * 
 */
package com.csr.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.security.Identity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.utils.DB_Connection;
import com.csr.utils.ListenersImplementation;
import com.ibm.db2.jcc.am.DatabaseManagerNonTransientException;

/**
 * @author akaushi3
 *
 */
public class FindCustomerPage extends BasePage {

	BasePage basepage = new BasePage();
	OrdersPage orders = new OrdersPage();

	public static String sheetName = "FindCustomerValidations";

	By addCustomer = By.xpath("//a[text()='Create Customer']");
	By getFindCustomer_label = By.xpath("//h1[text()='Find a Customer']");
	By getEmail_textbox = By.name("email1");
	By firstName_textbox = By.id("firstName-input");
	By lastName_textbox = By.id("lastName-input");
	By telephoneNum = By.id("telephone-input");
	By address_textbox = By.id("address1-input");
	By suburb_textbox = By.id("city-input");
	By postalcode_textbox = By.id("zipCode-input");
	By submit_btn = By.xpath("//button[contains(text(), 'Search')]");
	By clear_btn = By.xpath("//button[contains(text(), 'Clear')]");
	By findCustomer_link = By.id("findRegisteredCustomer");
	By validation_messages = By.xpath("//div[@class='colrs-animate validation-messages']//ul//li");
	By rowCount = By.name("rowCount");
	By searchTable = By.xpath("//div[@class='searchlist']//table//tr//td");
	By searchResultTable = By.xpath("//div[@class='searchlist']");
	By searchTableByRow = By.xpath("//div[@class='searchlist']//table//tr");
	By accessCustomerAccount = By.xpath("//td[@class='text-center']//ul//li//span[contains(text(),'Access')]");
	By viewOrders = By.xpath("//span[contains(text(),'View Orders')]");
	By totalRecords = By.xpath("//span[contains(text(), 'records')]");
	By disableCustomer = By.xpath("//span[contains(text(),'Disable Customer')]");
	By enableCustomer = By.xpath("//span[contains(text(),'Enable Customer')]");
	By enableDisableForm = By.xpath("//form[@name='customerAccountModal']");
	By comment_textbox = By.id("txtComment");
	By reason_dropdown = By.id("returnReason");
	By saveStatus = By.xpath("//button[text()='Save']");
	By popUp_message = By.xpath("//span[@class='modal-heading text-left']");
	By disableStatus = By
			.xpath("//table//tr//th[text()='Account Status']//parent::tr/following-sibling::tr//*[contains(text(),'Disabled')]");
	By enableStatus = By
			.xpath("//table//tr//th[text()='Account Status']//parent::tr/following-sibling::tr//*[contains(text(),'Enable')]");
	By disabledReason = By
			.xpath("//table//tr//th[text()='Reason for Disablement']//parent::tr/following-sibling::tr/td[6]");
	By gearButtonOptions = By.xpath("//ul[@class='actionDropdown actionDropdown_csr']//li");

	public FindCustomerPage() {

		// waitForTextPresent(getFindCustomer_label, "Find a Customer");
	}

	/**
	 * Navigate To Find Customer Page
	 */
	public void navigateToFindCustomerPage() {
        
		click_custom(findCustomer_link, "Find a Customer");
		waitForTextPresent(getFindCustomer_label, "Find a Customer");
		checkPageIsReady();
		sleep(5000);
	}

	public AddCustomerPage navigateToAddCustomer() {

		click_custom(addCustomer, "Create Customer");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		return new AddCustomerPage();
	}

	public void click_clear_button() {

		click_custom(clear_btn, "Clear button");
	}

	public void verifyBlankInputs_validationMessage() {

		click_custom(submit_btn, "Submit_Button");
		waitForElementVisibility(validation_messages, "Validation messages");
		String errorMsg = getText_custom(validation_messages, "Validation Message");
		assertThat("Verifying the validation messages", errorMsg,
				equalTo("Please enter a search criteria to continue"));
	}

	public void verifyValidationMessage(String columnName, String sheetname) {

		click_custom(submit_btn, "Submit Button");

		waitForAllElementsPresent(validation_messages, "Validation Messages");
		List<String> actualMsg = new ArrayList<>();
		List<WebElement> getErrmrMsg = DriverFactory.getInstance().getDriver().findElements(validation_messages);
		for (int i = 0; i < getErrmrMsg.size(); i++) {

			actualMsg.add(getErrmrMsg.get(i).getText().trim());
			System.out.println("=====>" + actualMsg);
		}

		assertThat("Verifying the error messages", actualMsg,
				equalTo(basepage.getValidationMsg_excel(sheetname, columnName)));
	}


	/**
	 * Enter The Details
	 * 
	 * @param email
	 * @param fName
	 * @param lName
	 * @param telePhone
	 * @param address
	 * @param suburb
	 * @param postcode
	 */
	public void enterText_fields(String email, String fName, String lName, String telePhone) {
		//Will update email validation script in the next sprint
		waitForElementVisibility(getEmail_textbox, "Email Textbox");
		sendKeys_custom(getEmail_textbox, "Email", email);
		sendKeys_custom(firstName_textbox, "First Name", fName);
		sendKeys_custom(lastName_textbox, "Last Name", lName);
		sendKeys_custom(telephoneNum, "Telephone Number", telePhone);
		//sendKeys_custom(address_textbox, "Address", address); //--Address is removed from Search Criteria
		//sendKeys_custom(suburb_textbox, "Suburb", suburb);
		//sendKeys_custom(postalcode_textbox, "PostCode", postcode);
		sleep(2000);
		click_custom(submit_btn, "Submit Button");
		checkPageIsReady();
	}

	public void enterText_fields_fname_mandatory(String email, String lName, String fName, String telePhone) {
		//email="test@test.com"; //Will update email validation script in the next sprint
		waitForElementVisibility(getEmail_textbox, "Email Textbox");
		sendKeys_custom(getEmail_textbox, "Email", email);
		sendKeys_custom(lastName_textbox, "Last Name", lName);
		sendKeys_custom(firstName_textbox, "First Name", fName);
		sendKeys_custom(telephoneNum, "Telephone Number", telePhone);
		//sendKeys_custom(address_textbox, "Address", address); //--Address is removed from Search Criteria
		//sendKeys_custom(suburb_textbox, "Suburb", suburb);
		//sendKeys_custom(postalcode_textbox, "PostCode", postcode);
		sleep(2000);
		click_custom(submit_btn, "Submit Button");
		checkPageIsReady();
	}
	public void enterText_fields(String fName, String lName, String telePhone) {
		waitForElementVisibility(firstName_textbox, "First Name Textbox");
		sendKeys_custom(firstName_textbox, "First Name", fName);
		sendKeys_custom(lastName_textbox, "Last Name", lName);
		sendKeys_custom(telephoneNum, "Telephone Number", telePhone);
		//sendKeys_custom(address_textbox, "Address", address);
		//sendKeys_custom(suburb_textbox, "Suburb", suburb);
		//sendKeys_custom(postalcode_textbox, "PostCode", postcode);
		click_custom(submit_btn, "Submit Button");
		checkPageIsReady();
	}

	/**
	 * Get
	 * 
	 * @return
	 */
	public String getLabel_FindCustomer() {

		waitForElementVisibility(getFindCustomer_label, "Find Customer Label");
		return getText_custom(getFindCustomer_label, "Label");
	}

	/**
	 * 
	 */
	public void waitForSearchTableToPresent() {
		
		waitForElementVisibility(searchResultTable, "Record table");
	}

	/**
	 * Get UserID
	 * 
	 * @param email
	 * @return
	 */
	public String getUserID(String email) {

		String query = "select USERS_ID  from users where field1='" + email + "'";
		String userID = null;

		List<String[]> uIDList = DB_Connection.getDB_Data(query);
		for (int i = 0; i < uIDList.size(); i++) {
			String[] arr = uIDList.get(i);

			for (int j = 0; j < arr.length; j++) {
				userID = arr[j];
				System.out.println(userID);
			}
		}
		return userID;

	}

	public void verifyUI_Data_With_DB(String emailAddress) {

		String userid = getUserID(emailAddress);
		String query_01 = "select address.FirstName, address.LastName, address.Email1, address.Phone1 , Case When Userreg.Status=1 then 'Enabled' When Userreg.Status=0 then 'Disabled' else 'No Status' end from address "
						+" Inner Join Userreg on "
						+" address.Member_id=Userreg.Users_id "
						+" where address.Member_ID="+userid+"  AND address.Status = 'P' and address.IsPrimary = 0";
		System.out.println(query_01);
		List<String[]> getDB_data = DB_Connection.getDB_Data(query_01);

		// Getting Data From UI Table

		List<WebElement> uiTable = DriverFactory.getInstance().getDriver().findElements(searchTable);

		for (int i = 0; i < getDB_data.size(); i++) {

			String[] arr = getDB_data.get(i);

			for (int j = 0; j < arr.length-2; j++) {
				System.out.println("UI Data " + uiTable.get(j).getText());
				System.out.println("DB Data " + arr[j]);
				assertEquals_custom(arr[j], uiTable.get(j).getText().trim(), "Verifying the Customer's details");
			}
		}
	}

	/**
	 * Get Total Records From Database
	 * 
	 * @param dataBaseColumn
	 * @param attributeName
	 * @return
	 */
	public String getCountOfUsers(String dataBaseColumn, String attributeName) {

		String count = null;
		String[] arr = null;
		String query = "select Count(Distinct Member_ID) from ADDRESS " + 
				"Inner Join users on users.users_id = address.member_id" + 
				" where users.profiletype='C' and UPPER("+dataBaseColumn+")='"+attributeName+"'";
		System.out.println("Record---->"+ query );
		List<String[]> data = DB_Connection.getDB_Data(query);
		arr = data.get(0);
		count = arr[0];
		ListenersImplementation.test.get().log(Status.PASS, "The total records in database are: " + count);
		return count;
	}
	public String getCountOfUsersFirstAndLastName(String dataBaseColumn1, String attributeName1,String dataBaseColumn2, String attributeName2)
	{

		String count = null;
		String[] arr = null;
		String query = "select Count(Distinct Member_ID) from ADDRESS " + 
				"Inner Join users on users.users_id = address.member_id" + 
				" where users.profiletype='C' and UPPER("+dataBaseColumn1+")='"+attributeName1+"' and UPPER("+dataBaseColumn2+")='"+attributeName2+"'";
		System.out.println("Record---->"+ query );
		List<String[]> data = DB_Connection.getDB_Data(query);
		arr = data.get(0);
		count = arr[0];
		ListenersImplementation.test.get().log(Status.PASS, "The total records in database are: " + count);
		return count;
	}

	/**
	 * Get The Total Records
	 * 
	 * @return
	 */
	public String getTotalRecords() {

		return getText_custom(totalRecords, "Total Records");
	}

	public boolean verifyTextboxEmpty(By element, String elementName) {

		boolean flag = false;
		ListenersImplementation.test.get().log(Status.INFO,
				MarkupHelper.createLabel("Getting the value of " + elementName + "", ExtentColor.BLUE));
		String attrubite = DriverFactory.getInstance().getDriver().findElement(element).getAttribute("value");

		flag = attrubite.isEmpty();
		return flag;
	}

	public boolean verify_emailTextBox_Empty() {

		return verifyTextboxEmpty(getEmail_textbox, "Email Textbox");
	}

	public boolean verify_FirstNameTextBox_Empty() {

		return verifyTextboxEmpty(firstName_textbox, "First Name Textbox");
	}

	public boolean verify_LastNameTextBox_Empty() {

		return verifyTextboxEmpty(lastName_textbox, "Last name Textbox");
	}

	public boolean verify_TelephoneTextBox_Empty() {

		return verifyTextboxEmpty(telephoneNum, "Telephone Number Textbox");
	}

	public boolean verify_suburbTextBox_Empty() {

		return verifyTextboxEmpty(suburb_textbox, "Suburb Textbox");
	}

	public boolean verify_AddressTextBox_Empty() {

		return verifyTextboxEmpty(address_textbox, "Address Textbox");
	}

	public boolean verify_postCodeTextBox_Empty() {

		return verifyTextboxEmpty(postalcode_textbox, "Zip Code Textbox");
	}

	/**
	 * Navigate To Customer Account
	 */
	public void navigateToCustomerProfile() {

		IdentityPage identity = new IdentityPage();
		
		/*waitForAllElementsPresent(gearButtonOptions, "Gear buttons Options");*/
		moveToElement_custom(geatIcon, "Gear Icon");
		moveToElement_custom(accessCustomerAccount, "Access customer's profile");
		try {
			Thread.sleep(1000); //--Localization api takes time to load 20000
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		waitForTextPresent(identity.header_Identity, "Identity");
	}

	/**
	 * Navigate To View Orders
	 */
	public void navigateToViewOrders() {

		moveToElement_custom(geatIcon, "Gear Icon");
		moveToElement_custom(viewOrders, "View Orders");
		waitForTextPresent(orders.header_Orders, "Orders");
	}
	
	public void openGearButtonOptions(){
		
		moveToElement_custom(geatIcon, "Gear Icon");
		waitForAllElementsPresent(gearButtonOptions, "Gear buttons Options");
	}
	
	public void enable_Customer(){
		
		moveToElement_custom(enableCustomer, "Enable customer's account");
		waitForElementVisibility(enableDisableForm, "Enable form");
		sendKeys_custom(comment_textbox, "Commentbox", "Customer Request");
		click_custom(saveStatus, "Save button");
		waitForInvisibilityofElement(enableDisableForm, "disable user form");
		checkPageIsReady();
		scrollIntoView(searchResultTable, "Search Table");
		waitForSearchTableToPresent();
		assertEquals_custom(getText_custom(enableStatus, "Account status"), "Enabled",
				"Enabled status of customer profile");
	}
	
	public void disable_Customer() throws Throwable {

		moveToElement_custom(disableCustomer, "Disable customer's account");
		waitForElementVisibility(enableDisableForm, "Disable form");
		sleep(3000);
		selectDropDownByOption_custom(reason_dropdown, "Reason For Disablement", "209");
		String reason = getSelectedOption_Dropdown(reason_dropdown, "Reason");
		sendKeys_custom(comment_textbox, "Commentbox", reason);
		click_custom(saveStatus, "Save button");
		waitForInvisibilityofElement(enableDisableForm, "disable user form");
		checkPageIsReady();
		waitForSearchTableToPresent();
		scrollIntoView(searchResultTable, "Search Table");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>> "+reason);
		assertEquals_custom(getText_custom(disabledReason, "Disable reason"), reason,
				"Disabled status of customer profile");
		assertEquals_custom(getText_custom(disableStatus, "Account status"), "Disabled",
				"Disabled status of customer profile");
	}

	public String getTheStatusOfCustomer(){
		waitForAllElementsPresent(gearButtonOptions, "Gear button options");
		List<WebElement> options = DriverFactory.getInstance().getDriver().findElements(gearButtonOptions);
		List<String> opt = new ArrayList<>();
		String status = "";
		for(int i = 0; i<options.size();i++){
			System.out.println("======================>" +options.get(i).getText().trim());
			if((options.get(i).getText().trim().contains("Enable")||(options.get(i).getText().trim().contains("Disable")))){
				
				status = options.get(i).getText().trim();
			}
		}
		return status;
		
		
	}
	/**
	 * 
	 */
	public void verifyTabOrder() {
		for (int i = 0; i < 20; i++) {
			WebElement ele = DriverFactory.getInstance().getDriver().switchTo().activeElement();
			String str = ele.getText().trim();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ele.sendKeys(Keys.TAB);
		}
	}
}
