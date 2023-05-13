/**
 * 
 */
package com.csr.testsuites;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.pages.AddCustomerPage;
import com.csr.pages.BasePage;
import com.csr.pages.FindCustomerPage;
import com.csr.pages.LoginPage;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.PropertiesOperations;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import javax.management.DescriptorKey;

/**
 * @author akaushi3
 *
 */
public class AddCustomerTestSuite extends BasePage {

	LoginPage loginpage = new LoginPage();
	AddCustomerPage addCustomer = new AddCustomerPage();
	FindCustomerPage findCustomer = new FindCustomerPage();
	String sheetName = "AddCustomerValidations";

	@Test(priority = 3, description = "Verifying the validation messges for blank inputs")
	// @Description("Verify the various validation messages for blank inputs")
	// @Story("CE-5266- Create a Customer Account")
	public void verifyValidationMessages_blankInputs() throws InterruptedException {

		loginpage.loginIntoApplication();
		// --Navigate to Add Customer Page
		findCustomer.navigateToAddCustomer();
		checkPageIsReady();
		Thread.sleep(3000);
		// --Verifying that the tab is highlighted
		verifyTabHighlighted(addCustomerTab, "Create Customer");
		// --Verifying the header of the page
		assertEquals_custom(getHeader("Create a Customer Account"), "Create a Customer Account", "Page Header");
		ListenersImplementation.test.get().log(Status.PASS,
				"The title of the page is " + getHeader("Orders page") + "");
		// --Verifying the Validation messages For Blank Inputs
		sleep(3000);
		assertEquals_list(addCustomer.getValidationMessage_CSR(),
				getValidationMsg_excel(sheetName, "Validations For Blank Input"), "Validation message");
	}

	@Test(priority = 4, description = "Verifying the validations for invalid inputs")
	// @Description("Verify the various validation messages for invalid inputs")
	// @Story("CE-5266- Create a Customer Account")
	public void verifyValidationMessages_invalidInputs() throws Throwable {
		refresh();
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		// Try to add customer with invalid inputs
		addCustomer.invalidinputDetails_addCustomer();
		// --Verify the validation messages for invalid inputs
		assertEquals_list(addCustomer.getValidationMessage_CSR(),
				getValidationMsg_excel(sheetName, "Validations For Invalid Inputs"), "Validation message");
	}

	@Test(priority = 5, description = "Verify the Business Type, ABN and Business Name fields should be disabled by default")
	// @Description("Verify the states of ABN, Business Type and Business name
	// fields state")
	// @Story("CE-5266- Create a Customer Account")
	public void verifyElementState() {
		refresh();
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		// --Verifying the button state for business customer
		assertThat("Business Name field should be disabled", addCustomer.getBusinessName_state(), equalTo(false));
		assertThat("ABN field should be disabled", addCustomer.getABN_state(), equalTo(false));
		assertThat("Business Type field should be disabled", addCustomer.getBusinessType_state(), equalTo(false));
		addCustomer.click_BusnessCheckBox();
		assertThat("Business Name field should be enabled", addCustomer.getBusinessName_state(), equalTo(true));
		assertThat("ABN field should be enabled", addCustomer.getABN_state(), equalTo(true));
		assertThat("Business Type field should be enabled", addCustomer.getBusinessType_state(), equalTo(true));
	}

	@Test(priority = 6, description = "Verify the functionality of Cancel button and Default selected Country")
	// @Description("Verify the functionality of Cancel button")
	// @Story("CE-5266- Create a Customer Account")
	public void verify_CancelBtn() {

		refresh();
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		// --Navigate to add customer page
		findCustomer.navigateToAddCustomer();
		// --Verifying the bydefault selected country
		assertEquals_custom(addCustomer.verifyDefaultSelectedCountry(), "Australia", "By default selected country");
		ListenersImplementation.test.get().log(Status.INFO,
				"On clicking cancel button CSA should be navigated to Find Customer Page");
		// --Verifying the navigation by clicking the cancel button
		addCustomer.click_cancelBtn();
		assertEquals_custom(findCustomer.getLabel_FindCustomer(), "Find a Customer", "Find Customer page header");
	}

	@Test(priority = 7, description = "Verify that CSR is able to create B2C customer")
	// @Description("Verify Create a customer account functionality")
	// @Story("CE-5266- Create a Customer Account")
	public void createCustomer() throws Throwable {
		refresh();
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		// --Navigate to add customer page
		findCustomer.navigateToAddCustomer();
		// --Enter Unique name and telephone number
		addCustomer.enter_fName_lName_telNum();
		// --Entering other valid details
		addCustomer.enter_OtherValidDetails();
		// assertEquals_custom(addCustomer.getSuccessMessage_createCustomer(),
		// "Customer has been created successfully!",
		// "Success Message");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		Thread.sleep(1000);
		assertEquals_custom("Identity", getHeader("Identity"), "Header Of Page");
		Thread.sleep(1000); // Localization API
		logOutAs_Customer();
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
	}

	@Test(priority = 8, description = "Verifying the validation message when CSA provides existing email id while creating customer")
	// @Description("Verify the validation for using existing customer email for
	// new account creation")
	// @Story("CE-5266- Create a Customer Account")
	public void verifyValidation_duplicateEmailId() throws Throwable {
		// --Navigate to customer's profile
		findCustomer.navigateToAddCustomer();
		// --Enter already used email id
		addCustomer.enterDuplicateEmail();
		// --Enter other valid details
		addCustomer.enter_OtherValidDetailsForDuplicateEmail();
		String expectedMsg = "A customer is already registered with this email";
		assertEquals_custom(addCustomer.getDuplicateEmailMessage(), expectedMsg,
				"Error message while creating new customer with existing email");
	}

}
