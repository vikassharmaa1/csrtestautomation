/**
 * 
 */
package com.csr.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.Queries;

/**
 * @author akaushi3
 *
 */
public class BulkCancellationPage extends BasePage {

	FindOrderPage findOrder = new FindOrderPage();

	By orderId_textbox = By.id("orderId");
	By search_buton = By.xpath("//button[contains(text(),'Search')]");
	By refresh_button = By.xpath("//button[contains(text(),'Refresh')]");
	By noRecord = By.xpath("//div[contains(text(),'No Record Found!!')]");
	By orderTable = By.xpath("//div[@class='searchlist']");
	By selectAllOrders = By.xpath("//input[@name='smsPreference']");
	By cancelSelectedOrder_button = By.xpath("//button[contains(text(),'Cancel Selected Order')]");
	By noteMsg = By.xpath("//*[@id='password_help_msg']");
	
	/**
	 * Pending Order Search
	 * 
	 * @param OrderType
	 */
	public void searchPendingOrder(String OrderType) {

		List<String> orderId = getDetails_DB(Queries.getOrderByServiceType(OrderType, "P"));
		ListenersImplementation.test.get().log(Status.INFO,
				OrderType + " Pending order should not be looked up in Bulk Cancellation");
		if (orderId.size() > 0) {
			String orderNo = orderId.get(0);

			sendKeys_custom(orderId_textbox, "Order Id", orderNo);
			click_custom(search_buton, "Search button");
			boolean flag = waitForTextPresent(noRecord, "No Record Found!!");
			if (flag) {

				ListenersImplementation.test.get().log(Status.PASS,
						OrderType + " Pending Orders cannot be looked up from Bulk Cancellations.");
			} else {
				ListenersImplementation.test.get().log(Status.FAIL,
						OrderType + " Pending Orders can be looked up from Bulk Cancellations.");
			}
		}

		else {
			ListenersImplementation.test.get().log(Status.FAIL, "No data is available in database");
		}
	}
	
	public void message_verification() throws InterruptedException
	{		
		
		checkPageIsReady();
		Thread.sleep(2000);
		boolean flag = isElementPresent(noteMsg, "Bulk Cancellation Note Message");
		if (flag) {
			ListenersImplementation.test.get().log(Status.PASS, "Bulk Cancellation Note Message can be seen as expected");
		} else {
			ListenersImplementation.test.get().log(Status.FAIL,
					"Unable to see bulk cancellation Note message");
		}		
	}

	/**
	 * Cancel Order Search
	 * 
	 * @param orderType
	 */
	public void cancelOrderSearch(String orderType) {

		ListenersImplementation.test.get().log(Status.INFO,
				"Cancelled orders should be looked up from Bulk Cancellation");
		System.out.println("query is "+Queries.getOrderByServiceType(orderType, "X"));
		List<String> orderId = getDetails_DB(Queries.getOrderByServiceType(orderType, "X"));
		if (orderId.size() > 0) {
			String orderNo = orderId.get(0);

			sendKeys_custom(orderId_textbox, "Order Id", orderNo);
			click_custom(search_buton, "Search button");
			waitForInvisibilityofElement(loaderIcon, "Loader icon");
			waitForElementVisibility(orderTable, "Order table");
			select_checkBox_Custom(selectAllOrders, "Select all orders");
			boolean flag = getElementState_custom(cancelSelectedOrder_button, "Cancel Order button");
			if (!flag) {
				ListenersImplementation.test.get().log(Status.PASS, "Cancelled orders can not be cancelled again");
			} else {
				ListenersImplementation.test.get().log(Status.FAIL,
						"Cancel selected Order button should be disabled");
			}
		}
	}

	/**
	 * Search Submitted and Shipped Orders
	 * 
	 * @param orderType
	 * @param type
	 */
	public void searchSubmittedAndShippedOrders(String orderType, String type) {

		ListenersImplementation.test.get().log(Status.INFO, "Search the Submitted and Shipped Orders");
		List<String> orderId = getDetails_DB(Queries.getOrderByServiceType(orderType, type));
		if (orderId.size() > 0) {
			String orderNo = orderId.get(0);

			sendKeys_custom(orderId_textbox, "Order Id", orderNo);
			click_custom(search_buton, "Search button");
			waitForInvisibilityofElement(loaderIcon, "Loader icon");
			waitForElementVisibility(orderTable, "Order table");
			select_checkBox_Custom(selectAllOrders, "Select all orders");
			boolean flag = getElementState_custom(cancelSelectedOrder_button, "Cancel Order button");
			if (flag) {
				ListenersImplementation.test.get().log(Status.PASS,
						orderType + " for " + type + " can be cancelled from Bulk Cancellation");
			} else {
				ListenersImplementation.test.get().log(Status.FAIL,
						"Cancel selected Order button should be enabled");
			}
		}
	}

	public void cancelOrders_bulkCancellation() {

		ListenersImplementation.test.get().log(Status.INFO,
				"Finding orders to verofy the bulk cancellation functionality");
		List<String> hdorderId = getDetails_DB(Queries.getOrderByServiceType("HD", "S"));
		List<String> ccorderId = getDetails_DB(Queries.getOrderByServiceType("CC", "S"));
		//List<String> rdorderId = getDetails_DB(Queries.getOrderByServiceType("RD", "S"));

		if (hdorderId.size() > 0 && ccorderId.size() > 0) {
			sendKeys_custom(orderId_textbox, "Order Id",
					hdorderId.get(0) + "," + ccorderId.get(0));
			click_custom(search_buton, "Search button");
			waitForInvisibilityofElement(loaderIcon, "Loader icon");
			waitForElementVisibility(orderTable, "Order table");
			select_checkBox_Custom(selectAllOrders, "Select all orders");

			List<WebElement> checkbox = DriverFactory.getInstance().getDriver()
					.findElements(By.xpath("//input[@type='checkbox']"));

			for (int i = 0; i > checkbox.size(); i++) {

				boolean flag = checkbox.get(i).isSelected();
				if (flag) {
					ListenersImplementation.test.get().log(Status.PASS, "Checkbox is checked");
				} else {
					ListenersImplementation.test.get().log(Status.PASS,
							"Select all orders checkbox is not working fine.");
				}
			}

			boolean flag = getElementState_custom(cancelSelectedOrder_button, "Cancel Order button");
			if (flag) {
				ListenersImplementation.test.get().log(Status.PASS, "Cancel Selected Order button is enabled");
			} else {
				ListenersImplementation.test.get().log(Status.FAIL, "Cancel selected Order button is disabled");
			}

			click_custom(cancelSelectedOrder_button, "Cancel selected orders");
			try {
				findOrder.bulkCancellation("All orders have been cancelled successfully.");
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else {
			ListenersImplementation.test.get().log(Status.FAIL, "No orders are available in the database");
		}
	}

	public void verifyCancelStatusOfOrders() {

		ListenersImplementation.test.get().log(Status.INFO, "Status Of All Orders should be cancelled");

		List<WebElement> status = DriverFactory.getInstance().getDriver()
				.findElements(By.xpath("//div[@class='searchlist']//td[3]"));
		
		for(int i = 0; i<status.size();i++){
			
			String str = status.get(i).getText().trim();
			assertEquals_custom(str, "Cancelled", "Order Status");
		}
	}
}
