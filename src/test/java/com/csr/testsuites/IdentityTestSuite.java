/**
 * 
 */
package com.csr.testsuites;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.pages.AddCustomerPage;
import com.csr.pages.BasePage;
import com.csr.pages.FindCustomerPage;
import com.csr.pages.IdentityPage;
import com.csr.pages.LoginPage;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.Queries;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

/**
 * @author akaushi3
 *
 */
public class IdentityTestSuite extends BasePage {

	FindCustomerPage findCustomer = new FindCustomerPage();
	IdentityPage identitypage = new IdentityPage();
	AddCustomerPage addCustomer = new AddCustomerPage();
	BasePage basePage = new BasePage();
	LoginPage loginpage = new LoginPage();
	
	
	@Test(priority = 20, description = "This testcase will verify the Attributes Of Customers Profile")
	//@Description("Verify attributes of Identity page")
	//@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyCustomerProfileAttributes_identityTab() throws InterruptedException {
		refresh();
		findCustomer.navigateToFindCustomerPage();
		/*String emailAddress =  addCustomer.FIRSTNAME.toLowerCase()+ "." +addCustomer.LASTNAME.toLowerCase()+ "@getnada.com";
		findCustomer.enterText_fields(emailAddress, "", "", "");*/
		findCustomer.enterText_fields(FindCustomerTestSuite.emailAddress, "", "", ""); 
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		findCustomer.waitForSearchTableToPresent();
		findCustomer.navigateToCustomerProfile();
		Thread.sleep(2000);
		assertEquals_custom(identitypage.getFirstName(), AddCustomerPage.FIRSTNAME, "First Namme");//
		assertEquals_custom(identitypage.getLastName(), AddCustomerPage.LASTNAME, "Last Name");
		assertEquals_custom(identitypage.getEmailAddress(), FindCustomerTestSuite.emailAddress, "Email");
		assertEquals_custom(identitypage.getTelephoneNumber(), AddCustomerPage.TELEPHONENUMBER, "Telephone Number");
	}
	@Test(priority = 21, description = "This testcase will verify the Attributes Of Customers Profile",enabled=false)
	//@Description("Verify attributes of Identity page")
	//@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyOrganisationAttributes_identityTab() throws InterruptedException {
		refresh();
		/*loginpage.loginIntoApplication();*/
		/*findCustomer.navigateToFindCustomerPage();*/
		checkPageIsReady();
		/*Thread.sleep(3000);*/
		/*findCustomer.enterText_fields("oa_a_admin_googleoaorg@getnada.com", "", "", "");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		Thread.sleep(2000);
		//--Search for search table
		findCustomer.waitForSearchTableToPresent();
		findCustomer.navigateToCustomerProfile();*/
		Thread.sleep(2000);
		String orgName = "";
		String orgUnitName = "";
		System.out.println(identitypage.getOrganisationName() + "--->" + orgName);
		System.out.println(identitypage.getorganisationUnitName() + "--->" + orgUnitName);
		assertEquals(identitypage.getOrganisationName(), orgName);
		assertEquals(identitypage.getorganisationUnitName(), orgUnitName);
	}
	
	@Test(priority = 22, description = "Verify that Identity tab is highlighted")
	//@Description("Verify identity tab and title")
	//@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyIdentityTab_highlighted_identityTab() throws InterruptedException{
		
		refresh();
		checkPageIsReady();
		Thread.sleep(2000);
		assertEquals_custom(getHeader("Identity Page"), "Identity", "Identity Page header");
		verifyTabHighlighted(identityTab, "Identity Tab");
		verifyMandatoryButtons();
	}
	
	@Test(priority = 20, description = "Verify that Secondary Email and Telephone numbers are updated")
	//@Description("Verify secondary email and telephone number updation")
	//@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifySecondaryDetailsUpdated_identityTab() throws Throwable{
		
		refresh();
		Thread.sleep(2000);
		identitypage.enterSecondaryDetails();
		String expectedTelNum = IdentityPage.secondaryTelNum;
		String expectedEmail = IdentityPage.secondaryEmailAddress;
		String primaryEmail = FindCustomerTestSuite.emailAddress;// AddCustomerPage.FIRSTNAME.toLowerCase()+"."+AddCustomerPage.LASTNAME.toLowerCase()+"@getnada.com";
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the updated Secondary Email and Telephone with the database.");
		assertEquals_custom(identitypage.getAddressTable_Details(primaryEmail, "EMAIL2"), expectedEmail, "Secondary email");
		assertEquals_custom(identitypage.getAddressTable_Details(primaryEmail, "PHONE2"), expectedTelNum, "Secondary Telephone Number");
		ListenersImplementation.test.get().log(Status.PASS, "The updated secondary email and telephone number are matching with database.");
	}
	
	@Test(priority = 21, description = "Verify that the Rosie Flag and 2FA preferences values are getting updated in database.")
	//@Description("Verify that Rosie and 2FA choices are getting updaed in database")
	//@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyDBvalue_RosieAndSecurity_identityTab() throws Throwable{
		
		refresh();
		checkPageIsReady();
		Thread.sleep(2000);
		identitypage.updateRosieFlag("OTHER SEE COMMENTS");
		identitypage.updateSecurityPref("Enabled");
		identitypage.updateRecords();
		checkPageIsReady();
		Thread.sleep(2000);
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the updated Rosie Flag and Security Prefences value in database");
		String primaryEmail = FindCustomerTestSuite.emailAddress; // AddCustomerPage.FIRSTNAME.toLowerCase()+"."+AddCustomerPage.LASTNAME.toLowerCase()+"@getnada.com";
		String actualRosieFlagValue = identitypage.getUpdatedValue_DB(primaryEmail, "StringValue", 270);
		assertThat(actualRosieFlagValue, equalToIgnoringCase("110"));
		String actualSecurityValue = identitypage.getUpdatedValue_DB(primaryEmail, "IntegerValue", 840);
		assertThat(actualSecurityValue, equalToIgnoringCase("0"));
	}
	
	@Test(priority = 22, description = "Verify the validation message when user update the blank values")
	//@Description("Verify the various validation messages for blank inputs")
	//@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyValidations_identityTab() throws Throwable{
		
		refresh();
		identitypage.clearInputs();
		checkPageIsReady();
		Thread.sleep(2000);
		verifyValidationMessage("Identity","Blank Inputs Validations");
		refresh();
		checkPageIsReady();
	}
	
	@Test(priority = 23, description = "Verifying the Rosie Flag options with DB")
	//@Description("Verify the Rosie Flag dropdown values from database")
	//@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyRosieFlagOptions_identityTab(){
		
		refresh();
		List<String> rosieFlagOptions_db = getDetails_DB(Queries.getRosieFlagOptions());
		List<String> rosieFlagOptions_CSR = identitypage.getRosieFlagOptons_CSR();
		assertEquals_list(rosieFlagOptions_CSR, rosieFlagOptions_db, "Rosie Flag Options");
	}
}
