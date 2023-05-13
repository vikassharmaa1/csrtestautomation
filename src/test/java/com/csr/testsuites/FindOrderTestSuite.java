/**
 * 
 */
package com.csr.testsuites;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.pages.BasePage;
import com.csr.pages.FindOrderPage;
import com.csr.pages.LoginPage;
import com.csr.pages.OrderReturn;
import com.csr.pages.OrderSummaryPage;
import com.csr.utils.DataProviderUtils;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.Queries;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

/**
 * @author akaushi3
 *
 */
public class FindOrderTestSuite extends BasePage {

	FindOrderPage findOrder = new FindOrderPage();
	OrderSummaryPage summary = new OrderSummaryPage();
	OrderReturn returns = new OrderReturn();
	LoginPage login = new LoginPage();
	String sheetname = "FindAnOrder";

	@DataProvider()
	public Object[][] getData() {

		Object[][] data = DataProviderUtils.getSheetData(sheetname);
		return data;
	}

	@DataProvider()
	public Object[][] getOrderData() {

		Object[][] data = DataProviderUtils.getSheetData("FindAnOrderData");
		return data;
	}

	@DataProvider()
	public Object[][] getPendingOrderData() {

		Object[][] data = DataProviderUtils.getSheetData("PendingOrder");
		return data;
	}

	@Test(priority = 51, description = "Verifying the header and highlighted tab in Customer Services section")
	//@Description("Verify the find an order tab and title")
	//@Story("CE-5418 - Find an Order")
	public void verify_headerAndTab_findanOrder() {
		
		login.loginIntoApplication();
		//logOutAs_Customer();
		checkPageIsReady();
		refresh();
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying that the Header of page and tab is highlighted");
		navigateToTab(findAnOrderTab, "Find An Order");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		checkPageIsReady();
		findOrder.verifyFindOrderPage();
		verifyTabHighlighted(findAnOrderTab, "Find an Order");
		assertEquals_custom(getHeader("Find Order Page"), "Find an Order", "Find Order page header");
	}

	@Test(dataProvider = "getData", priority = 52, description = "Verifying the various validation messages for Addresses combinations.")
	//@Description("Verify the various validation messages for invalid inputs of Address combinations")
	//@Story("CE-5418 - Find an Order")
	public void verifyValidationMessages_findanOrder(String day, String month, String year, String address,
			String suburb, String postCode, String errorMessage) throws Throwable {

		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying the various validation messages for Address and Date combinations.");
		findOrder.enterAddressDetails(day, month, year, address, suburb, postCode);
		findOrder.searchOrderId_AddressCombination();
		waitForValidations();
		verifyMessage(errorMessage);
	}

	@Test(priority = 53, description = "Verifying the validation messages for OrderID")
	//@Description("Verify the validation message for invalid orderid")
	//@Story("CE-5418 - Find an Order")
	public void verifyOrderIdValidationMessages_findanOrder() {

		refresh();
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the Order validations messages");

		findOrder.enterOrderId("");
		findOrder.searchOrders_ByOrdersId();
		waitForValidations();
		verifyMessage("Please enter a search criteria to continue");
		refresh();
		findOrder.enterOrderId("!@#123");
		findOrder.searchOrders_ByOrdersId();
		verifyMessage("Order Id must not contain alphabets and special characters"); // Correct
	}

	@Test(dataProvider = "getOrderData", priority = 54, description ="Verifying the Order Attributes")
	//@Description("Verify the attributes of various orders like HD, CC and RD")
	//@Story("CE-5418 - Find an Order")
	public void verifyOrderAttributes_findanOrder(String orderType, String orderCategory, String orderTypeDB,
			String orderCategoryDB) throws InterruptedException {
		refresh();
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the Order details using OrderID search");
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying the " + orderType + " Order details for " + orderCategory + "");
		System.out.println("=====>" +Queries.getOrderByServiceType(orderTypeDB, orderCategoryDB));
		List<String> orderId = getDetails_DB(Queries.getOrderByServiceType(orderTypeDB, orderCategoryDB));
		navigateToTab(findAnOrderTab, "Find An Order");
		findOrder.enterOrderId(orderId.get(0));
		findOrder.searchOrders_ByOrdersId();
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		findOrder.waitForOrderList();
		checkPageIsReady();
		if (orderType.equals("HD")) {
			findOrder.verifyOrderAttributes(orderId.get(0));
		} else {
			findOrder.verifyNonHDOrderAttributes(orderId.get(0));
		}
		Thread.sleep(5000);
		findOrder.clearRecords();
	}

	@Test(dataProvider = "getPendingOrderData", priority = 55, description = "Verifying the Order Attributes")
	//@Description("Verify the pending order attributes")
	//@Story("CE-5418 - Find an Order")
	public void verifyPendingOrderAttributes_findanOrder(String orderType, String orderCategory, String orderTypeDB,
			String orderCategoryDB) throws InterruptedException {

		refresh();
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the Order details using OrderID search");
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying the " + orderType + " Order details for " + orderCategory + "");
		List<String> orderId = getDetails_DB(Queries.getOrderByServiceType(orderTypeDB, orderCategoryDB));
		findOrder.enterOrderId(orderId.get(0));
		findOrder.searchOrders_ByOrdersId();
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		findOrder.waitForOrderList();
		checkPageIsReady();
		findOrder.clearRecords();
	}

	@Test(priority = 56, description = "Verify the Clear button functionality")
	//@Description("Verify the clear button functionality")
	//@Story("CE-5418 - Find an Order")
	public void verifyClearButtonFunctionality_findanOrder() throws Throwable {
		System.out.println("Step --------> 0001");
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the Clear button functionality");
		findOrder.enterOrderId("123");
		findOrder.enterAddressDetails("12", "March", "2020", "TestAddress", "TestAddress", "3125");
		findOrder.clearRecords();
		ListenersImplementation.test.get().log(Status.INFO,
				"Clear button should reset all the values entered in the textboxes");
		assertThat(findOrder.verifyOrderId_Value(), equalTo(true));
		ListenersImplementation.test.get().log(Status.PASS, "OrderId field value has been reset successfully");
		/*assertThat(findOrder.verifyDate_Value(), equalTo(true));
		ListenersImplementation.test.get().log(Status.PASS, "Date field value has been reset successfully");
		assertThat(findOrder.verifyYear_Value(), equalTo(true));
		ListenersImplementation.test.get().log(Status.PASS, "Year field value has been reset successfully");*/
		assertThat(findOrder.verifyAddress_Value(), equalTo(true));
		ListenersImplementation.test.get().log(Status.PASS, "Address field value has been reset successfully");
		assertThat(findOrder.verifySuburb_Value(), equalTo(true));
		ListenersImplementation.test.get().log(Status.PASS, "Suburb field value has been reset successfully");
		assertThat(findOrder.verifyPostCode_Value(), equalTo(true));
		ListenersImplementation.test.get().log(Status.PASS, "Post Code field value has been reset successfully");
		System.out.println("Step --------> Clear 001");
	}

	@Test(priority = 57, description = "Verify The Redelivery functionality of return and order")
	//@Description("Verify the Re Delivery functionality of an order")
	//@Story("CE-5333 - Create Returns from Orders")
	public void redeliveryOfOrder() throws Throwable {
		System.out.println("Step --------> Return 001");
		refresh();
		System.out.println(Queries.getOrders_Redelivery("HD", "S"));
		List<String> orderId = getDetails_DB(Queries.getOrders_Redelivery("HD", "S"));
		System.out.println("Step -------->" + orderId.size());
		for (int i = 0; i < orderId.size(); i++) {
			if (orderId.size() > 0) {
				findOrder.enterOrderId(orderId.get(i));
				findOrder.searchOrders_ByOrdersId();
				waitForInvisibilityofElement(loaderIcon, "Loader Icon");
				findOrder.waitForOrderList();
				checkPageIsReady();
				findOrder.verifyOrderAttributes(orderId.get(i));
				findOrder.navigateToOrderSummaryPage();
				Thread.sleep(5000);
				summary.selectItemsToReturn();
				returns.selectRandomReason_global();
				returns.initiateReturnProcess("Re-delivery");
				waitForInvisibilityofElement(loaderIcon, "Loader Icon");
				returns.selectDeliveryType("Home Delivery");
				Thread.sleep(5000);
				waitForInvisibilityofElement(loaderIcon, "Loader Icon");
				returns.verifyDeliveryTypeOptions(orderId.get(i));
				returns.verifyButtonsState();
				returns.verifyDBdetails_return(orderId.get(i));
				boolean flag = returns.selectDeliverySlot();
				if (flag) {

					returns.selectDeliveryAndBaggingPref();
					returns.handleRedeliveryPopUp(OrderReturn.rmaID);
					returns.verifyDBentries_return(OrderReturn.rmaID, "Re-delivery");
					returns.verifyXorderAttrData(orderId.get(i));
					break;
				} else {
					returns.navigateToFindOrder();
					continue;
				}
			} else {
				ListenersImplementation.test.get().log(Status.FAIL, "No orders are fetched from database.");
			}
		}

	}
}
