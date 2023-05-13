/**
 * 
 */
package com.csr.testsuites;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.csr.base.ExtentFactory;
import com.csr.pages.AddCustomerPage;
import com.csr.pages.BasePage;
import com.csr.pages.DetailsPage;
import com.csr.pages.FindCustomerPage;
import com.csr.pages.IdentityPage;
import com.csr.pages.LoginPage;
import com.csr.utils.ActionUtils;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.Queries;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

/**
 * @author akaushi3
 *
 */
public class BusinessCustomerRegression extends BasePage {

	LoginPage loginpage = new LoginPage();
	AddCustomerPage addCustomer = new AddCustomerPage();
	FindCustomerPage findCustomer = new FindCustomerPage();
	BasePage basepage = new BasePage();
	DetailsPage details = new DetailsPage();
	IdentityPage identity = new IdentityPage();
	String sheetName = "AddCustomerValidations";

	@Test(priority = 1, description = "Verifying the validation messges for blank inputs")
	// @Description("Verify the various validation messages for blank inputs")
	// @Story("CE-5266- Create a Customer Account")
	public void verifyValidationMessages_blankInputs() throws InterruptedException {
		refresh();
		checkPageIsReady();
		sleep(2000);
		loginpage.loginIntoApplication();
		// --Navigate to Add Customer Page
		findCustomer.navigateToAddCustomer();
		checkPageIsReady();
		// --Verifying that the tab is highlighted
		verifyTabHighlighted(addCustomerTab, "Create Customer");

		// --Verifying the header of the page
		assertEquals_custom(getHeader("Create a Customer Account"), "Create a Customer Account", "Page Header");
		ListenersImplementation.test.get().log(Status.PASS,
				"The title of the page is " + getHeader("Orders page") + "");
		// --Verifying the Validation messages For Blank Inputs
		addCustomer.click_BusnessCheckBox();
		addCustomer.getValidationMessage_CSR();
		assertEquals_list(addCustomer.getValidationMessage_CSR(),
				getValidationMsg_excel(sheetName, "Business Customers-Validations For Blank Input"),
				"Validation message");
	}

	@Test(priority = 2, description = "Creating a business customer") 
	// @Description("CSA should be able to create B2B customer")
	// @Story("CE-5266- Create a Customer Account")
	public void businessCustomerCreation() throws Throwable {
		refresh();
		// --Navigate to add customer page
		findCustomer.navigateToAddCustomer();
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		// --Entering the Business Name, ABN and Business type
		addCustomer.enterBusinessCustomerEssentials();
		// --Enter Unique name and telephone number
		addCustomer.enter_businessCustomerEssentialDetails();
		// --Entering other valid details
		addCustomer.enter_OtherValidDetails();
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		assertEquals_custom("Identity", getHeader("Identity"), "Header Of Page");
		Thread.sleep(10000); // Localization API
	}

	@Test(priority = 3, description = "This testcase will verify the Attributes Of Customers Profile")
	// @Description("Verify attributes of Identity page")
	// @Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyOrganisationNameForStandaloneCustomer_identityTab() throws InterruptedException {
		refresh();
		String orgNameDB = identity.getOrganisationNameFromEmailId(identity.getEmailAddress());
		String orgUnitNameDB = identity.getOrganisationUnitNameFromEmailId((identity.getEmailAddress()));
		assertEquals(identity.getOrganisationName().toLowerCase(), orgNameDB);
		assertEquals(identity.getorganisationUnitName().toLowerCase(), orgUnitNameDB);
	}

	@Test(priority = 9, description = "This testcase will verify the Org and OrgUnit field's value Of Customers Profile")
	// @Description("Verify Organisation of Identity page")
	// @Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyOrgNameAndOrgUnitNameValue_identityTab() throws InterruptedException {
		refresh();
		sleep(3000);
		/*checkPageIsReady();
		loginpage.loginIntoApplication();*/
		findCustomer.navigateToFindCustomerPage();
		findCustomer.enterText_fields(identity.getEmailIdForB2B(), "", "", "");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		Thread.sleep(2000);
		// --Search for search table
		findCustomer.waitForSearchTableToPresent();
		Thread.sleep(2000);
		findCustomer.navigateToCustomerProfile();
		String orgNameDB = identity.getOrganisationNameFromEmailId(identity.getEmailIdForB2B().toLowerCase());
		String orgUnitNameDB = identity.getOrganisationUnitNameFromEmailId(identity.getEmailIdForB2B());
		assertEquals(identity.getOrganisationName().toLowerCase(), orgNameDB);
		assertEquals(identity.getorganisationUnitName().toLowerCase(), orgUnitNameDB);
	}

	@Test(priority = 3, description = "Business customer details should be available on Identity page")
	@Description("B2B customer business details should be present on Identity Page")
	@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyB2BDetails_identityTab() {
		refresh();
		ListenersImplementation.test.get().log(Status.INFO,
				"Business customer checkbox, business name, type and abn should be available on Identity page.");
		identity.verifyB2Bcheckbox_selected();
		System.out.println(
				"Actual Type--->" + identity.getBusinessType() + "Expected Type--->" + AddCustomerPage.businessType);
		sleep(5000);
		assertEquals_custom(identity.getBusinessType(), AddCustomerPage.businessType, "Business Type");
		assertEquals_custom(identity.getABN_number(), AddCustomerPage.abnNumber, "ABN Number");
		assertEquals_custom(identity.getBusinessName(), AddCustomerPage.businessName.trim(), "Business Name");
	}

	@Test(priority = 5, description = "Verify CSA is able to update the customer information on details page")
	@Description("Verify secondary email and telephone number updation for B2B customer")
	@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyDetailsUpdation() throws Throwable {

		refresh();
		identity.enterSecondaryDetails_businessCustomer();
		String expectedTelNum = IdentityPage.secondaryTelNum;
		String expectedEmail = IdentityPage.secondaryEmailAddress_b2b;
		String primaryEmail = AddCustomerPage.businessCust_Fname.toLowerCase() + "."
				+ AddCustomerPage.businessCust_Lname.toLowerCase() + "@getnada.com";
		System.out.println("-------------------" + primaryEmail + "==========" + expectedEmail);
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying the updated Secondary Email and Telephone with the database.");
		assertEquals_custom(identity.getAddressTable_Details(primaryEmail, "EMAIL2"), expectedEmail, "Secondary email");
		assertEquals_custom(identity.getAddressTable_Details(primaryEmail, "PHONE2"), expectedTelNum,
				"Secondary Telephone Number");
		ListenersImplementation.test.get().log(Status.PASS,
				"The updated secondary email and telephone number are matching with database.");
	}

	@Test(priority = 4, description = "Verify that CSA can update Rosie and 2FA details for B2B customer")
	@Description("Verify 2FA and Rosie updated details")
	@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verify_RosieAndSecurityPref() throws Throwable {

		/*
		 * String primaryEmail = AddCustomerPage.businessCust_Fname.toLowerCase() + "."
		 * + AddCustomerPage.businessCust_Lname.toLowerCase() + "@getnada.com";
		 */

		refresh();
		checkPageIsReady();
		identity.updateRosieFlag("OTHER SEE COMMENTS");
		identity.updateSecurityPref("Enabled");
		identity.updateRecords();
		checkPageIsReady();
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying the updated Rosie Flag and Security Prefences value in database");
		sleep(5000);
		String actualRosieFlagValue = identity.getUpdatedValue_DB(identity.getEmailAddress(), "StringValue", 270);
		assertContains_custom(actualRosieFlagValue, "110", "Rosie Flag");
		// assertThat(actualRosieFlagValue, equalToIgnoringCase("110"));
		String actualSecurityValue = identity.getUpdatedValue_DB(identity.getEmailAddress(), "IntegerValue", 840);
		assertContains_custom(actualSecurityValue, "0", "Security Pref.");
		// assertThat(actualSecurityValue, equalToIgnoringCase("0"));
	}

	@Test(priority = 6, description = "Verify that Details tab is highlighted") // 24
	// @Description("Verifying the details tab and title")
	// @Story("CE-5268 - View Edit Customer Account in CSR")
	public void detailsTabHighlighted_detailsTab() throws InterruptedException {

		refresh();
		checkPageIsReady();
		Thread.sleep(2000);
		details.clickDetailsTab();
		verifyTabHighlighted(Detailstab, "Details tab");
		assertEquals_custom(getHeader("Details Page"), "Details", "Details page header");
		verifyMandatoryButtons();
	}

	@Test(priority = 7, description = "Verify CostCentre Field") // 28
	// @Description("Verifying the details tab and title")
	// @Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyCostCentre_detailsTab() throws InterruptedException {

		refresh();
		checkPageIsReady();
		details.updateCostCentreNumber();
	}
	@Test(priority = 8, description = "CSR can set the hub store flag to 'YES'")
    public void setHubStoreFlagToYes_DetailsTab(){
		
		refresh();
		checkPageIsReady();
		details.hubStoreCheckBox();
		logOutAs_Customer();
        
    }
}