/**
 * 
 */
package com.csr.testsuites;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.text.ParseException;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.csr.pages.BasePage;
import com.csr.pages.CouponsPage;
import com.csr.pages.FindCustomerPage;
import com.csr.pages.LoginPage;
import com.csr.pages.OrderReturn;
import com.csr.pages.OrderSummaryPage;
import com.csr.pages.OrdersPage;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.Queries;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

/**
 * @author akaushi3
 *
 */
public class OrdersTestSuite extends BasePage {

	OrdersPage orders = new OrdersPage();
	FindCustomerPage findCustomer = new FindCustomerPage();
	OrderSummaryPage summary = new OrderSummaryPage();
	OrderReturn returns = new OrderReturn();
	CouponsPage coupons = new CouponsPage();
	LoginPage loginpage = new LoginPage();
	List<String> email = null;

	@DataProvider(name = "getOrderSelivertType")
	public Object[][] getOrderType() {

		return new Object[][] { { "HD" }, { "CC" }, { "RD" } };
	}

	@Test(priority = 34, description = "Verifying that Orders Tab is highlighted header of the page")
	@Description("Verify the tab and title of Orders tab")
	@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyHeaderAndTab_ordersTab() {

		loginpage.loginIntoApplication();
		// --Navigating to find customer page
		findCustomer.navigateToFindCustomerPage();
		email = getDetails_DB(Queries.getEmailId());
		// --Finding a new customer using email id
		findCustomer.enterText_fields(email.get(0), "", "", "");
		// --Waiting for search results
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		findCustomer.waitForSearchTableToPresent();
		// --Navigating to customer profile
		findCustomer.navigateToCustomerProfile();
		// --Navigating to orders tab
		navigateToTab(ordersTab, "Orders");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		checkPageIsReady();
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying that Orders tab should be highlighted and the Header of the Orders Page");
		// --Verify tab is highlighted
		verifyTabHighlighted(ordersTab, "Orders");
		assertEquals_custom(getHeader("Orders Page"), "Orders", "Orders page header");
		// --Verifying that the mandatory button are highlighted
		verifyMandatoryButtons();
	}

	@Test(priority = 35, description = "Verifying the state of controls available in the page")
	@Description("Verify the controls of Orders tab")
	@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyControls_ordersTab() {

		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying the state of the Controls available on the Orders page");
		// --Verifying the state of the controls available on the Orders Page
		assertThat("Verifying the state of Refresh button", orders.getRefreshButtonState(), equalTo(true));
		assertThat("Verifying the state Find button", orders.getFindButtonState(), equalTo(false));
		assertThat("Verifying the state of Clear button", orders.getClearButtonState(), equalTo(false));
		assertThat("Verifying the state of OrderId textbox", orders.getOrderIdTextBoxState(), equalTo(true));
	}

	@Test(priority = 36, description = "Verifying the Order Details of Customers froom CSR with Database")
	@Description("Verify the Orders details of the customer")
	@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyOrderDetails_ordersTab() throws ParseException {

		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying the Customer's order details from CSR with Database");
		// --Getting the userId form the database
		String userID = getUsersID(email.get(0));
		// --Getting the member ID/UserID of the customer
		List<String> getOrderDetails_CSR = orders.getActiveOrders();

		List<String> getOrderDetails_DB = getDetails_DB(Queries.getOrderDetails(userID));
		// --Verifying the Order Details from Database and CSR
		orders.verifyOrderDetails(getOrderDetails_DB, getOrderDetails_CSR, "Order details");
		refresh();
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		orders.getOrderAttributes_CSR();
	}

	// @Test(priority = 37, description = "Verifying the Order Summary Orders")
	@Description("Verify the Order Summary")
	@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyOrderSummary() throws InterruptedException {

		refresh();
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		// --Verifying the Order Summary of the Shipped, Cancelled and Submitted
		// order
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the Order Summary");
		System.out.println("=========> " + email.get(0));
		// summary.verifyOrderSummary(email.get(0), "Cancelled");
		// summary.verifyOrderSummary(email.get(0), "Submitted");
		summary.verifyOrderSummary(email.get(0), "Shipped");
	}

	@Test(dataProvider = "getOrderSelivertType", priority = 38, description = "Verifying the return functionality of a shipped order")
	@Description("Create return from customer - Issue Coupon")
	@Story("CE-5333 - View Edit Customer Account in CSR")
	public void returnShippedOrder_CreditCoupons(String ordertype) throws Throwable {
		
		logOutAs_Customer();
		refresh();
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		findCustomer.navigateToFindCustomerPage();
		List<String> email_id = getDetails_DB(Queries.getCustomer_IssueCoupon(ordertype));
		// --Finding a new customer using email id
		findCustomer.enterText_fields(email_id.get(0), "", "", "");
		// --Waiting for search results
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		findCustomer.waitForSearchTableToPresent();
		// --Navigating to customer profile
		findCustomer.navigateToCustomerProfile();
		// --Navigate to Coupons tab
		navigateToTab(couponsTab, "Coupons Tab");
		// --Wait for page to load
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		checkPageIsReady();
		// --Get the Coupons running balance
		double couponRunningBal = coupons.getRunningBalance();
		// --Navigate to Orders tab
		navigateToTab(ordersTab, "Orders");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		checkPageIsReady();
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying the return functionality of a shipped order");
		// --Navigate to Shipped Order
		System.out.println("=========================> " + email_id.get(0));
		boolean flag = summary.navigateTo_ShippedOrder(email_id.get(0));
		if (!flag) {
			ListenersImplementation.test.get().log(Status.INFO, "No Shipped Order is available");
		} else {
			// --Select random items to return
			summary.selectItemsToReturn();
			// --Select Random reason to return the order
			returns.selectRandomReason_global();
			// --Initiate return process
			returns.initiateReturnProcess("Issue Coupon");
			checkPageIsReady();
			// --Save return
			returns.saveForLater();
			returns.handleIssueCouponPopUp();
			returns.verifyDBdetails_return(OrderSummaryPage.orderID);
			assertContains_custom(OrderReturn.returnMessage, OrderReturn.rmaID, "RMA ID");
			waitForTextPresent(headers_page, "Order Summary");
			// --Navigate to returns page
			refresh();
			waitForInvisibilityofElement(loaderIcon, "Loader Icon");
			navigateToTab(returnsTab, "Returns Tab");
			waitForInvisibilityofElement(loaderIcon, "Loader Icon");
			// --Verify the recently saved return details
			returns.verifySavedReturn();
			// --Navigate to Resume Saved Return page
			returns.navigateToOrderReturn();
			// --Submit the Return
			returns.submitPendingReturn("Issue Coupon");
			returns.handleIssueCouponPopUp();
			ListenersImplementation.test.get().log(Status.INFO,
					"Coupons should be issued when customer returns and order.");
			navigateToTab(couponsTab, "Coupons Tab");
			waitForElementVisibility(loaderIcon, "Loader Icon");
			waitForInvisibilityofElement(loaderIcon, "Loader Icon");
			double newRunningBal = coupons.getRunningBalance();
			double expectedRunningBal = couponRunningBal + Double.valueOf(OrderReturn.actualAmount);
			// --Verify the updated running balance
			assertEquals_custom(String.valueOf(newRunningBal), String.valueOf(expectedRunningBal),
					"Coupon running balance");
			// --Logout as customer
			logOutAs_Customer();
		}
	}
}
