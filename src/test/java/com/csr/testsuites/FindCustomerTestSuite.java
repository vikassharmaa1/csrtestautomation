/**
 * 
 */
package com.csr.testsuites;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.pages.AddCustomerPage;
import com.csr.pages.BasePage;
import com.csr.pages.FindCustomerPage;
import com.csr.pages.LoginPage;
import com.csr.utils.DataProviderUtils;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.PropertiesOperations;
import com.csr.utils.Queries;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

/**
 * @author akaushi3
 *
 */
public class FindCustomerTestSuite extends BasePage {
	public static String emailAddress = null;
	public String sheetName = "FindCustomer";
	FindCustomerPage findCustomer = new FindCustomerPage();
	AddCustomerPage addCustomer = new AddCustomerPage();
	BasePage basepage = new BasePage();


	@DataProvider
	public Object[][] getData() {

		Object[][] data = DataProviderUtils.getSheetData(sheetName);

		return data;
	}

	@Test(dataProvider = "getData", priority = 9, description = "Verify the validations")
	//@Description("Verify the various validation messages for invalid inputs for Finding a customer")
	//@Story("CE-5267- Find a Customer")
	public void verifyValidations_FindCustomer(String email, String fName, String lName, String telephone, String error) {
		
		//--Navigate to find customer page
		findCustomer.navigateToFindCustomerPage();
		//--Verifying the Find Customer tab is highlighted or not
		assertThat("Verifying that the Find Customer tab is highlighted",
				verifyTabHighlighted(findCustomerTab, "Find Customer"), equalTo(true));
		//--Verifying the error messaged for various invalid inputs
		findCustomer.enterText_fields(email, fName, lName, telephone);
		checkPageIsReady();
		verifyMessage(error);
		//--Click clear button
		findCustomer.click_clear_button();
	}

	@Test(priority = 10, description = "Find existing customer using email address")
	//@Description("Verify a customer using email address")
	//@Story("CE-5267- Find a Customer")
	public void findCustomer_usingEmailAddress() throws InterruptedException {

		findCustomer.click_clear_button();
		findCustomer.navigateToFindCustomerPage();
		emailAddress =  addCustomer.FIRSTNAME.toLowerCase()+ "." +addCustomer.LASTNAME.toLowerCase()+ "@getnada.com";
		System.out.println("===========EMAIL=========== " +emailAddress);
		//--Searching for a customer using Email Address
		findCustomer.enterText_fields(emailAddress, "", "", "");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		Thread.sleep(3000);
		//--Search for search table
		findCustomer.waitForSearchTableToPresent();
		//--Verify the customer data with database
		findCustomer.verifyUI_Data_With_DB(emailAddress);
	}
     // Commented this test script due to performance issue
	//@Test(priority = 11, description = "Find existing customer using firstname")
	//@Description("Verify a customer using first name")
	//@Story("CE-5267- Find a Customer")
	public void findCustomer_usingFirstName() {
		//--Click clear button
		findCustomer.click_clear_button();
		//--Searching the customer by first name
		String fname = addCustomer.FIRSTNAME;
		String lname = addCustomer.LASTNAME;
		findCustomer.enterText_fields("", fname, lname , "");
		//--Waiting for result table
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		findCustomer.waitForSearchTableToPresent();
		//--Getting the count of matching users from DB
		String expectedcount = findCustomer.getCountOfUsersFirstAndLastName("ADDRESS.FirstName", fname.toUpperCase(),"ADDRESS.LastName", lname.toUpperCase());
		//--Getting the count of users from CSR
		String actualCount = findCustomer.getTotalRecords();
		assertContains_custom(actualCount, expectedcount, "Number of records matching from CSR to DB");
	}

	// Commented this test script due to performance issue
	//@Test(priority = 12, description = "Find existing customer using lastname")
	//@Description("Verify a customer using last name")
	//@Story("CE-5267- Find a Customer")
	public void findCustomer_usingLastName() {
		findCustomer.click_clear_button();
		String lname = addCustomer.LASTNAME;
		String fname = addCustomer.FIRSTNAME;
		findCustomer.enterText_fields_fname_mandatory("", lname, fname , "");
		//--Search the customer using last name
		//findCustomer.enterText_fields("", "", lname, "");
		//--Waiting for search result to appear
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		findCustomer.waitForSearchTableToPresent();
		//--Getting the count of users in database
		String expectedcount = findCustomer.getCountOfUsersFirstAndLastName("ADDRESS.FirstName", fname.toUpperCase(),"ADDRESS.LastName", lname.toUpperCase());
	    //String expectedcount = findCustomer.getCountOfUsers("ADDRESS.LASTNAME", lname.toUpperCase());
		//--Getting the count of users in CSR
		String actualCount = findCustomer.getTotalRecords();
		assertContains_custom(actualCount, expectedcount, "Number of records matching from CSR to DB");
	}
	@Test(priority = 13, description = "Find existing customer using firstname")
	//@Description("Verify a customer using first name")
	//@Story("CE-5267- Find a Customer")
	public void findCustomer_LastNameMandatory() {
		
	        //--Click clear button
		    sleep(3000);
	        findCustomer.click_clear_button();
	        //--Searching the customer by first name
	        String fname = addCustomer.FIRSTNAME;
	        findCustomer.enterText_fields("", fname, "", "");
	        sleep(2000);
	        String expectedMsg = "Please enter last name";
			assertEquals_custom(addCustomer.getMandatoryLastNameMessage(), expectedMsg,
					"Error message while entering first name");
	        //--Waiting for result table
	        /*waitForInvisibilityofElement(loaderIcon, "Loader Icon");
	        findCustomer.waitForSearchTableToPresent();
	        //--Getting the count of matching users from DB
	        String expectedcount = findCustomer.getCountOfUsers("ADDRESS.FirstName", fname.toUpperCase());
	        //--Getting the count of users from CSR
	        String actualCount = findCustomer.getTotalRecords();
	        assertContains_custom(actualCount, expectedcount, "Number of records matching from CSR to DB");*/
	    }
	@Test(priority = 14, description = "Find existing customer using firstname")
	//@Description("Verify a customer using first name")
	//@Story("CE-5267- Find a Customer")
	public void findCustomer_FirstNameMandatory() {
		
	        //--Click clear button
	        findCustomer.click_clear_button();
	        //--Searching the customer by first name
	        String lname = addCustomer.LASTNAME;
	        findCustomer.enterText_fields("", "", lname , "");
	        sleep(2000);
	        String expectedMsg = "Please enter first name";
			assertEquals_custom(addCustomer.getMandatoryFirstNameMessage(), expectedMsg,
					"Error message while entering lastname name");
	    }

	//@Test(priority = 15, description = "Find existing customer using telephone number")
	@Description("Verify a customer using telephone number")
	@Story("CE-5267- Find a Customer")
	public void findCustomer_usingTelephoneNumber() {
		//--Clearing the previous search criteria
		findCustomer.click_clear_button();
		//--Search for a customer using Telephone number
		findCustomer.enterText_fields("", "", "", addCustomer.TELEPHONENUMBER);
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		//--Getting count of users from DB
		String expectedcount = findCustomer.getCountOfUsers("ADDRESS.Phone1", addCustomer.TELEPHONENUMBER);
		//--Getting count of users from CSR
		String actualCount = findCustomer.getTotalRecords();
		assertContains_custom(actualCount, expectedcount, "Number of records matching from CSR to DB");
	}

	//@Test(priority = 16, description = "Find existing customer using address") //--This test case is obsolete
	public void findCustomer_usingAddress() {
		//--Clearing the previous search criteria
		findCustomer.click_clear_button();
		//--Search for a customer using the address
		//findCustomer.enterText_fields("", "", "", "", PropertiesOperations.getPropertyValueByKey("addressLine"),
			//	PropertiesOperations.getPropertyValueByKey("suburb"),
				//PropertiesOperations.getPropertyValueByKey("postCode"));
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		//--Getting the count of users with the searched address 
		String expectedcount = findCustomer.getCountOfUsers("Address1",
				PropertiesOperations.getPropertyValueByKey("addressLine"));
		String actualCount = findCustomer.getTotalRecords();
		assertContains_custom(actualCount, expectedcount, "Number of records matching from CSR to DB");
	}

	@Test(priority = 17, description = "Verify the functioanlity of Crearbutton")
	//@Description("Verify Clear button functionality")
	//@Story("CE-5267- Find a Customer")
	public void clearButtonFunctionality_FindCustomer() {
		//--Clearing the previous search criteria
		sleep(5000);
		findCustomer.click_clear_button();
		//--Entering random invalid inputs to search customer
		findCustomer.enterText_fields("email", "testname", "testname", "041122334455");
		//--Click clear button
		findCustomer.click_clear_button();
		ListenersImplementation.test.get().log(Status.INFO,
				MarkupHelper.createLabel("All the text boxes should be cleared", ExtentColor.BLUE));
		//--All text fields should become empty
		assertThat(findCustomer.verify_emailTextBox_Empty(), equalTo(true));
		assertThat(findCustomer.verify_FirstNameTextBox_Empty(), equalTo(true));
		assertThat(findCustomer.verify_LastNameTextBox_Empty(), equalTo(true));
		assertThat(findCustomer.verify_TelephoneTextBox_Empty(), equalTo(true));
		ListenersImplementation.test.get().log(Status.PASS,
				MarkupHelper.createLabel("All the text boxes are cleared", ExtentColor.BLUE));
	}

	@Test(priority = 19, description = "Verify the disable functionality")
	//@Description("Verify a Disable Customer Account Functionality")
	//@Story("CE-5269 - Enable Disable Customer")
	public void enableUser_disableCustomer() throws Throwable {
		refresh();
		ListenersImplementation.test.get().log(Status.INFO, "Getting a user from database and verifying the disable functionality");
		//--Navigate to Find customer page
		findCustomer.navigateToFindCustomerPage();
		//--Get an enable customer from DB
		List<String> getEnableCustomer = getDetails_DB(Queries.getEnableCustomerEmail());
		//--Search for the same customer
		System.out.println("========================================>" +getEnableCustomer);
		findCustomer.enterText_fields(getEnableCustomer.get(0), "", "", "");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		Thread.sleep(2000);
		//--Wait for search table to appear
		findCustomer.waitForSearchTableToPresent();
		//--Open the gear button
		findCustomer.openGearButtonOptions();
		//--Disable the user
		String status = findCustomer.getTheStatusOfCustomer();
		if (status.contains("Disable")) {
			findCustomer.disable_Customer();
		} else {
			findCustomer.enable_Customer();
		}

	}

	@Test(priority = 18, description = "Verify the Enable Customer functionality")
	//@Description("Verify a Enable Customer Account Functionality")
	//@Story("CE-5269 - Enable Disable Customer")
	public void disableUser_enableCustomer() throws Throwable {
		refresh();
		ListenersImplementation.test.get().log(Status.INFO, "Getting a customer from database andvVerifying the enable customer's account functionality");
		//--Navigate to find customer page
		sleep(2000);
		/*findCustomer.navigateToFindCustomerPage();*/
	     checkPageIsReady();
		//--Get an disable customer from DB
		List<String> getdisableCustomer = getDetails_DB(Queries.getDisableCustomerEmail());
		//--Search the same customer fetched from DB
		System.out.println("========================================>" +getdisableCustomer.get(0));
		findCustomer.enterText_fields(getdisableCustomer.get(0), "", "", "");
		//--Wait for result table to appear
		findCustomer.waitForSearchTableToPresent();
		//--Open the gear button
		findCustomer.openGearButtonOptions();
		//--Enable the customer
		String status = findCustomer.getTheStatusOfCustomer();
		if (status.contains("Enable")) {
			findCustomer.enable_Customer();
		} else {
			findCustomer.disable_Customer();
		}

	}
}
