/**
 * 
 */
package com.csr.testsuites;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.csr.base.ExtentFactory;
import com.csr.pages.BasePage;
import com.csr.pages.BulkCancellationPage;
import com.csr.pages.LoginPage;
import com.csr.utils.ListenersImplementation;

/**
 * @author akaushi3
 *
 */
public class BulkCancellationTestSuite extends BasePage {

	BulkCancellationPage bulkCancellation = new BulkCancellationPage();
	LoginPage loginpage = new LoginPage();

	@Test(priority = 61, description = "Verify the Bulk Cancellation page attributes")
	public void verifyBulkCancellationPage() throws InterruptedException {	
		loginpage.loginIntoApplication();
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying that the tab is highlighted and header of the page");
		// --Navigate to compare account page
		navigateToTab(bulkCancellationTab, "Bulk Cancellation");
		checkPageIsReady();
		Thread.sleep(5000);
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying that Bulk Cancellation tab should be highlighted and the Header of the Page");
		// --Verify that the tab is highlighted or not
		verifyTabHighlighted(bulkCancellationTab, "Bulk Cancellation Tab");
		// --Verifying the page header
		assertEquals_custom(getHeader("Bulk Cancellation"), "Bulk Cancellation", "Bulk Cancellation page title");
	}
	
	
	@Test(priority = 62, description = "Verify CSA can not search Pending Orders on Bulk Cancellation Page")
	public void verify_message_bulkCancellation() throws InterruptedException {
		refresh();
		bulkCancellation.message_verification();
	}


	@Test(priority = 63, description = "Verify CSA can not search Pending Orders on Bulk Cancellation Page")
	public void searchPendingOrder_bulkCancellation() {
		refresh();
		bulkCancellation.searchPendingOrder("HD");
		bulkCancellation.searchPendingOrder("CC");
		bulkCancellation.searchPendingOrder("RD");
	}

	@Test(priority = 64, description = "Verify CSA can search cancelled order but can not cancel it again")
	public void verify_cancelOrderSearch_bulkCancellation() {
		
		refresh();
		bulkCancellation.cancelOrderSearch("HD");
		bulkCancellation.cancelOrderSearch("CC");
		bulkCancellation.cancelOrderSearch("RD");
	}

	@Test(priority = 65, description = "Verify CSA can search cancelled order but can not cancel it again")
	public void verify_SubmittedAndShippedOrders_bulkCancellation() {
		
		refresh();
		bulkCancellation.searchSubmittedAndShippedOrders("HD", "S");
		bulkCancellation.searchSubmittedAndShippedOrders("HD", "M");
		bulkCancellation.searchSubmittedAndShippedOrders("CC", "S");
		bulkCancellation.searchSubmittedAndShippedOrders("CC", "M");
		bulkCancellation.searchSubmittedAndShippedOrders("RD", "S");
		bulkCancellation.searchSubmittedAndShippedOrders("RD", "M");
	}
	
	@Test(priority=66, description = "Verify the bulk cancellation functionality")
	public void verify_bulkCancellationFunctionality(){
		
		refresh();
		bulkCancellation.cancelOrders_bulkCancellation();
		bulkCancellation.verifyCancelStatusOfOrders();
	}

}
