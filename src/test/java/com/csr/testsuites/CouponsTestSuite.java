/**
 * 
 */
package com.csr.testsuites;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.csr.base.ExtentFactory;
import com.csr.base.ExtentReport;
import com.csr.base.TestBase;
import com.csr.pages.BasePage;
import com.csr.pages.CommentsPage;
import com.csr.pages.CouponsPage;
import com.csr.pages.FindCustomerPage;
import com.csr.pages.LoginPage;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.PropertiesOperations;
import com.csr.utils.Queries;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

/**
 * @author akaushi3
 *
 */
public class CouponsTestSuite extends BasePage {

	BasePage basepage = new BasePage();
	CouponsPage coupons = new CouponsPage();
	CommentsPage comments = new CommentsPage();
	FindCustomerPage findCustomer = new FindCustomerPage();
	String customerEmail = null;
	String userId = null;
	LoginPage login = new LoginPage();
	
	@Test(priority = 39, description = "Verifying the highlighted tab and header of the Coupons tab")
	@Description("Verify the coupon tab and title")
	@Story("CE-5284 - Assign and Adjust Coupons")
	public void verifyTabAndHeaders_coupons() {
		
		login.loginIntoApplication();
		// --Navigating to findcustomer page
		findCustomer.navigateToFindCustomerPage();
		// --Finding a new customer using email id
		customerEmail = findCustomer_coupons();
		findCustomer.enterText_fields(customerEmail, "", "", "");
		// --Waiting for search results
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		findCustomer.waitForSearchTableToPresent();
		// --Navigating to customer profile
		findCustomer.navigateToCustomerProfile();
		checkPageIsReady();
		navigateToTab(couponsTab, "Coupons");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		checkPageIsReady();
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying that Coupons tab should be highlighted and the Header of the Coupons Page");
		
				verifyTabHighlighted(couponsTab, "Coupons");
		assertThat("Verifying the header of Coupons page", getHeader("Coupons"), containsString("Coupons"));
		ListenersImplementation.test.get().log(Status.PASS,
				"The title of the page is " + getHeader("Coupons") + "");
		verifyMandatoryButtons();
	}

	@Test(priority = 40, description = "Verifying that the coupon issued value is correct")
	@Description("Verifying coupon values")
	@Story("CE-5284 - Assign and Adjust Coupons")
	public void verifyCoupanIssuedValue_coupons() {

		coupons.verifyCouponIssued();
	}

	@Test(priority = 41, description = "Verifying the OrderID when CSA assigns a coupon")
	@Description("Verifying the assign coupon functionality")
	@Story("CE-5284 - Assign and Adjust Coupons")
	public void verifyOrdersID_coupons() throws Throwable {

		ListenersImplementation.test.get().log(Status.INFO, "Verify the OrdersID when CSA assigns a coupon");
		double couponVal = coupons.getTotalIssuedCoupon();
		coupons.clickAssignButton();
		checkPageIsReady();
		coupons.assignCoupon(PropertiesOperations.getPropertyValueByKey("couponValue"));
		ListenersImplementation.test.get().log(Status.INFO,
				"The Order Id should be null when CSA assign coupon without providing orderID.");
		userId = getUsersID(customerEmail);
		System.out.println(userId);
		List<String> orderID = getDetails_DB(Queries.getOrdersId_coupons(userId));
		assertThat(orderID.get(0), equalTo(null));
		ListenersImplementation.test.get().log(Status.PASS, "Order ID should be null when CSA assigns a coupon with out order ID and it is correct.");
		coupons.verifyCouponIssuedBalance_couponadded(couponVal);
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the comments of Coupons");
		System.out.println("THe Order Id is "+orderID.get(0)+" and reason is : "+CouponsPage.couponAssignReason);
		comments.verifyAssignCouponComment(userId, CouponsPage.couponAssignReason);
		Thread.sleep(5000);
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the Order ID when CSA assigns the coupon");
		couponVal = coupons.getTotalIssuedCoupon();
		coupons.clickAssignButton();
		checkPageIsReady();
		coupons.assignCoupon(getShippedOrder_ID(customerEmail),
				PropertiesOperations.getPropertyValueByKey("couponValue"));
		ListenersImplementation.test.get().log(Status.INFO,
				"The Order Id should be null when CSA assign coupon without providing orderID.");
		userId = getUsersID(customerEmail);
		System.out.println(userId);
		//List<String> orderID_01 = getDetails_DB(Queries.getOrdersId_coupons(userId));
		//assertThat(orderID_01.get(0), equalTo(PropertiesOperations.getPropertyValueByKey("orderId")));
		ListenersImplementation.test.get().log(Status.PASS, "The Order Id is " + orderID.get(0));
		coupons.verifyCouponIssuedBalance_couponadded(couponVal);
		comments.verifyAssignCouponComment(userId, CouponsPage.couponAssignReason);
	}

	@Test(priority = 42, description = "Verifying the refresh button functionality")
	@Description("Verify the refresh button functionality")
	@Story("CE-5284 - Assign and Adjust Coupons")
	public void verifyRefreshButtonFunctionality_coupons() throws InterruptedException {

		refresh();
		coupons.clickRefreshButton();
	}
	
	@Test(priority = 43, description = "Verifying the Adjust coupons functionality")
	@Description("Verify the adjust coupon functionality")
	@Story("CE-5284 - Assign and Adjust Coupons")
	public void verifyAdjustCoupons_coupons() throws Throwable{
		
		coupons.clickAdjustButton();
		coupons.verifyAvailableCoupanBalance();
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the running balance after deleting first coupon");
		coupons.verifyRunningBal_DeleteCoupon();
		comments.verifyAdjustCouponComment(userId, CouponsPage.couponAssignReason);
	}
	
	@Test(priority = 44, description = "Verifying Global Comments functionality")
	@Description("Verify the Global Comment functionality")
	@Story("ST-6514-CSA can able to see the 'Comment' and 'Reason' field alongwith save and back button after click in the  'New' button under 'Global Comments' section.")
	public void verifyGlobalComments() throws Throwable
	{
		coupons.globalComentsFunctionality();
	}
}
