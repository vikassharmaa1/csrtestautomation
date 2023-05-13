/**
 * 
 */
package com.csr.testsuites;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.pages.BasePage;
import com.csr.pages.CompareAccountsPage;
import com.csr.pages.FindCustomerPage;
import com.csr.pages.LoginPage;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.PropertiesOperations;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

/**
 * @author akaushi3
 *
 */
public class CompareAccountsTestSuite extends BasePage {

	BasePage basepage = new BasePage();
	FindCustomerPage findCustomer = new FindCustomerPage();
	CompareAccountsPage compare = new CompareAccountsPage();
	LoginPage login = new LoginPage();
	
	@Test(priority = 47, description = "Verifying the highlighted tab and the header of the page as well")
	@Description("Verify tab and title for Compare accounts")
	@Story("CE-5451 - Fraud Investigations and Duplicate Accounts")
	public void verifyTabAndHeader_compareAccounts() throws InterruptedException {
		//--Logout as the customer
		login.loginIntoApplication();
		checkPageIsReady();
		//Thread.sleep(5000);
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying that the tab is highlighted and header of the page");
		//--Navigate to compare account page
		navigateToTab(compareAccountsTab, "Comopare Accounts");
		checkPageIsReady();
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying that Compare Accounts tab should be highlighted and the Header of the Page");
		//--Verify that the tab is highlighted or not
		verifyTabHighlighted(compareAccountsTab, "Compare Accounts Tab");
		//--Verifying the page header
		assertEquals_custom(getHeader("Compare Accounts"), "Compare Accounts", "Compare accounts page title");
	}

	@Test(priority = 48, description = "Verifying the compare button functionality")
	@Description("Verify compare button functionality")
	@Story("CE-5451 - Fraud Investigations and Duplicate Accounts")
	public void verifyCompareBtnFunctionality_compareAccounts() throws InterruptedException {
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the functionality of Compare Button");
		//--Search for customers to verify the compare button functionality
		findCustomer.enterText_fields(PropertiesOperations.getPropertyValueByKey("compareAccountsCustomer"), "", "");
		Thread.sleep(100000);
		//--Wait for results to appear
		findCustomer.waitForSearchTableToPresent();
		ListenersImplementation.test.get().log(Status.INFO,
				"When no customer profile is selected compare button should be disabled.");
		//--Verify that the condition when Compare button is disabled
		assertThat(compare.verifyState_compareButton(), equalTo(false));
		//--Selecting 5 records
		compare.selectRecords(5);
		ListenersImplementation.test.get().log(Status.INFO,
				"When more than 4 customer profiles are selected, compare button should be disabled.");
		//--Again verifying the compare accounts button status
		assertThat(compare.verifyState_compareButton(), equalTo(false));

	}

	@Test(priority = 49, description = "Verifying the account details")
	@Description("Verify the account details on Compare accounts screen")
	@Story("CE-5451 - Fraud Investigations and Duplicate Accounts")
	public void compareAccountsDetails() throws InterruptedException {
		ListenersImplementation.test.get().log(Status.INFO, "Comparing the customer account details");
		refresh();
		//--Search for customers to compare accounts
		findCustomer.enterText_fields(PropertiesOperations.getPropertyValueByKey("compareAccountsCustomer"), "", "");
		Thread.sleep(100000);
		findCustomer.waitForSearchTableToPresent();
		//--Select accounts for comparsion
		List<String> data = compare.selectaccounts_forcomparasion(2);
		compare.click_CompareAccountsButton();
		Thread.sleep(10000);
		checkPageIsReady();
		//--Verifying the account details  from previous screen
		List<String> data_01 = compare.getCustomersInformation_compareAccountsScreen(2);
		assertThat(data, equalTo(data_01));
		ListenersImplementation.test.get().log(Status.INFO,
				"The data of customers present on Find Customer page and Compare Accounts are same.");
	}
	
	@Test(priority = 50, description = "Verifyng the Enable and Disable functionality")
	@Description("Verify enable and disable functionality from Compare Accounts functionality")
	@Story("CE-5451 - Fraud Investigations and Duplicate Accounts")
	public void verifyDisable_EnableFunctionality() throws InterruptedException{
		
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the enabling and disabling functionality of Customer's account");
		//--Disable / Enable the account as per the current status
		compare.toggle_customerAccountStatus();
	}
	
}
