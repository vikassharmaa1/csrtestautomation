/**
 * 
 */
package com.csr.testsuites;

import java.util.List;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.pages.BasePage;
import com.csr.pages.FindCustomerPage;
import com.csr.pages.FindOrderPage;
import com.csr.pages.LoginPage;
import com.csr.pages.OrdersPage;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.Queries;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

/**
 * @author akaushi3
 *
 */
public class CancleOrderTestSuite extends BasePage{

	FindOrderPage findOrder = new FindOrderPage();
	FindCustomerPage findCustomer = new FindCustomerPage();
	OrdersPage orders = new OrdersPage();
	LoginPage loginpage = new LoginPage();
	
	@Test(priority = 59, description = "Cancel Order Functionality from Find an Order")
	//@Description("Verify Cancle Order functionality from Find an Order")
	//@Story("CE-5332 - Cancel Order")
	public void cancelOrder_findOrder() throws Throwable {
		loginpage.loginIntoApplication();
		navigateToTab(findAnOrderTab, "Find An Order");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		checkPageIsReady();
		ListenersImplementation.test.get().log(Status.INFO, "Getting a Shipped order from database");
		String orderId = getOrderId_database("S");
		findOrder.enterOrderId(orderId);
		findOrder.searchOrders_ByOrdersId();
		findOrder.waitForOrderList();
		checkPageIsReady();
		findOrder.cancelOrder_findOrderPage();
		refresh();
		ListenersImplementation.test.get().log(Status.INFO, "Getting a Submitted order from database");
		orderId = getOrderId_database("M");
		findOrder.enterOrderId(orderId);
		findOrder.searchOrders_ByOrdersId();
		findOrder.waitForOrderList();
		checkPageIsReady();
		findOrder.cancelOrder_findOrderPage();
		refresh();
		ListenersImplementation.test.get().log(Status.INFO, "Getting a Cancelled order from database");
		orderId = getOrderId_database("X");
		findOrder.enterOrderId(orderId);
		findOrder.searchOrders_ByOrdersId();
		findOrder.waitForOrderList();
		checkPageIsReady();
		findOrder.cancelOrder_findOrderPage();
		refresh();
	}
	
	@Test(priority = 60, description = "Cancel Order functionality from Find Customer page")
	//@Description("Verify Cancle Order functionality from View Orders")
	//@Story("CE-5332 - Cancel Order")
	public void cancelOrder_findcustomer() throws Throwable{
		refresh();
		navigateToTab(findCustomerTab, "Find Customer");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		ListenersImplementation.test.get().log(Status.INFO, "Getting a customer with Submitted Order from database.");
		List<String> getEmailId = getDetails_DB(Queries.getEmailId_Order("M"));
        findCustomer.enterText_fields(getEmailId.get(0), "", "", "");
        Thread.sleep(1000);
		findCustomer.enterText_fields(getEmailId.get(0), "", "", "");
		Thread.sleep(1000);
		findCustomer.waitForSearchTableToPresent();
		findCustomer.navigateToViewOrders();
		orders.cancelOrder(getEmailId.get(0), "Submitted");		
		Thread.sleep(10000);
		navigateToTab(findCustomerTab, "Find Customer");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		ListenersImplementation.test.get().log(Status.INFO, "Getting a customer with Submitted Order from database.");
		List<String> getEmailId_01 = getDetails_DB(Queries.getEmailId_Order("S"));
		findCustomer.enterText_fields(getEmailId_01.get(0), "", "", "");
		Thread.sleep(1000);
		findCustomer.waitForSearchTableToPresent();
		findCustomer.navigateToViewOrders();
		orders.cancelOrder(getEmailId_01.get(0), "Shipped");
	}
		
	@Test(priority = 61, description = "Cancel Order functionality from Find Customer page")
	// @Description("Verify Cancle Order functionality from View Orders")
	// @Story("CE-5332 - Cancel Order")
	public void cancelOrder_AfterAccessCustomer() throws Throwable {
		refresh();
		navigateToTab(findCustomerTab, "Find Customer");
		Thread.sleep(1000);
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		ListenersImplementation.test.get().log(Status.INFO, "Getting a customer with Shipped Order from database.");
		List<String> getEmailId_01 = getDetails_DB(Queries.getEmailId_Order("M"));
		findCustomer.enterText_fields(getEmailId_01.get(0), "", "", "");
		findCustomer.waitForSearchTableToPresent();
		// --Navigating to customer profile
		findCustomer.navigateToCustomerProfile();
		// --Navigating to orders tab
		navigateToTab(ordersTab, "Orders");
		orders.cancelOrder_AfterAccessCustomer_Orders(getEmailId_01.get(0), "Submitted");
		logOutAs_Customer();
		refresh();
		Thread.sleep(5000);
		navigateToTab(findCustomerTab, "Find Customer");
		Thread.sleep(1000);
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		ListenersImplementation.test.get().log(Status.INFO, "Getting a customer with Submitted Order from database.");
		List<String> getEmailId_02 = getDetails_DB(Queries.getEmailId_Order("S"));
		findCustomer.enterText_fields(getEmailId_02.get(0), "", "", "");
		findCustomer.waitForSearchTableToPresent();
		findCustomer.navigateToViewOrders();
		orders.cancelOrder(getEmailId_02.get(0), "Shipped");
	}
}
