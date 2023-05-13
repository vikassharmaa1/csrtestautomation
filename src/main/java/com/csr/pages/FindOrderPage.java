/**
 * 
 */
package com.csr.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.Queries;
import com.mifmif.common.regex.Main;

/**
 * @author akaushi3
 *
 */
public class FindOrderPage extends BasePage {

	BasePage basepage = new BasePage();

	By orderId_textbox = By.id("orderId");
	By searchOrderId_button = By.xpath("//div[@class='col-30 ']//button");
	By day_textbox = By.id("fromDate");
	By month_dropdown = By.id("fromDate");
	By year_textbox = By.id("fromDate");
	By address_textbox = By.xpath("//*[@id='address1-input']");
	By suburb_textbox = By.xpath("//*[@id='city-input']");
	By zipcode_textbox = By.xpath("//*[@id='zipCode-input']");
	By search_buttonByDate = By.xpath("//div[@class='col-30 wd-auto']//button[contains(text(), 'Search')]");
	By clear_button = By.xpath("//div[@class='col-30 wd-auto']//button[contains(text(), 'Clear')]");
	By searchTable = By.xpath("//div[@class='searchlist']//table//tr//td");
	By header = By.className("main-header");
	By actionGear = By.xpath("//div[@class='searchlist']//table//td//img");
	By orderStatus = By.xpath("//div[@class='searchlist']//table//tr//td[2]");
	By cancelOrder = By.xpath("//a[@class='actionTitle']//span[text()='Cancel Order']");
	By cancelOrder_msg = By.xpath("//h1[text()='Cancel this order']");
	By reasonDropDown = By.xpath("//select[@id='reason']");
	By reasonDropDownOptions = By.xpath("//select[@id='reason']//option");
	By comment_textbox = By.id("comments");
	By cancelOrder_button = By.xpath("//button[@aria-label='Cancel Order']");
	By okay_button = By.xpath("//button[@aria-label='Okay']");
	By successMessage = By.xpath("//div[@class='colrs-animate success-message']");
	By orderSummaryOption = By.xpath("//a[text()='View Order Summary']");

	/**
	 * Wait For Search Table To Be Visible
	 */
	public void waitForSeatchTable() {

		waitForAllElementsPresent(searchTable, "Records table");
	}

	/**
	 * Header
	 * 
	 * @return
	 */
	public String getHeader() {

		return getText_custom(header, "Find An Order");
	}

	/**
	 * Get Order Attributes
	 * 
	 * @param orderId
	 * @return
	 * @throws InterruptedException 
	 */
	public void verifyOrderAttributes(String orderId) throws InterruptedException {
		String query = Queries.getOrderAttributes(orderId);
		List<String> orderAtt_db = basepage.getDetails_DB(query);
		List<WebElement> ele = DriverFactory.getInstance().getDriver().findElements(searchTable);
		List<String> orderAtt_csr = new ArrayList<String>();
		for (int i = 0; i < orderAtt_db.size(); i++) {
			if (i == 2) {
				String text = modifyDate(ele.get(i).getText().trim());
				orderAtt_csr.add(text);
			} else {
				orderAtt_csr.add(ele.get(i).getText().trim());
			}

		}
		for (int k = 0; k < orderAtt_db.size(); k++) 
		{			
			System.out.println("=============> Order Attributes " + orderAtt_csr.get(k) + " and " + orderAtt_db.get(k));
			ListenersImplementation.test.get().log(Status.PASS, "The value from database is " + orderAtt_db.get(k)
					+ " and value from CSR is " + orderAtt_csr.get(k) + "");
			Thread.sleep(1000);
				String dbValue=orderAtt_db.get(k).trim().replaceAll(" ", "");
				String csrValue=orderAtt_csr.get(k).trim().replaceAll(" ", "");
				assertContains_custom(dbValue, csrValue, "Order attributes");
		}
			//assertContains_custom(orderAtt_csr.get(k).trim(), orderAtt_db.get(k).trim(), "Order attributes");
	}
	
	/**
	 * 
	 * @param orderId
	 */
	public void verifyNonHDOrderAttributes(String orderId) {
		String query = Queries.getOrderAttributes(orderId);
		List<String> orderAtt_db = basepage.getDetails_DB(query);
		List<WebElement> ele = DriverFactory.getInstance().getDriver().findElements(searchTable);
		List<String> orderAtt_csr = new ArrayList<String>();
		for (int i = 0; i < orderAtt_db.size(); i++) {
			if (i == 2) {
				String text = modifyDate(ele.get(i).getText().trim());
				orderAtt_csr.add(text);
			} else {
				orderAtt_csr.add(ele.get(i).getText().trim());
			}

		}
		for (int k = 0; k < orderAtt_db.size()-1; k++) {
			System.out.println("=============> Order Attributes " + orderAtt_csr.get(k) + " and " + orderAtt_db.get(k));
			ListenersImplementation.test.get().log(Status.PASS, "The value from database is " + orderAtt_db.get(k)
					+ " and value from CSR is " + orderAtt_csr.get(k) + "");				
				String dbValue=orderAtt_db.get(k).trim().replaceAll(" ", "");
				String csrValue=orderAtt_csr.get(k).trim().replaceAll(" ", "");				
				assertContains_custom(dbValue, csrValue, "Order attributes");
		}
	}

	public void verifyPendingOrderAttributes(String orderId) {
		String query = Queries.getOrderAttributes(orderId);
		List<String> orderAtt_db = basepage.getDetails_DB(query);
		List<WebElement> ele = DriverFactory.getInstance().getDriver().findElements(searchTable);
		List<String> orderAtt_csr = new ArrayList<String>();
		for (int i = 0; i < orderAtt_db.size(); i++) {
			if (i == 2) {
				String text =  "null";
				orderAtt_csr.add(text);
			} else {
				orderAtt_csr.add(ele.get(i).getText().trim());
			}

		}
		for (int k = 0; k < orderAtt_db.size(); k++) {
			System.out.println("=============> Order Attributes " + orderAtt_csr.get(k) + " and " + orderAtt_db.get(k));
			ListenersImplementation.test.get().log(Status.PASS, "The value from database is " + orderAtt_db.get(k)
					+ " and value from CSR is " + orderAtt_csr.get(k) + "");
			assertThat(orderAtt_db.get(k), equalTo(orderAtt_csr.get(k)));
			//assertContains_custom(orderAtt_csr.get(k).trim(), orderAtt_db.get(k).trim(), "Order attributes");
		}
	}
	
	/**
	 * Verify Page Is Loaded
	 */
	public void verifyFindOrderPage() {

		waitForTextPresent(header, "Find an Order");
	}

	/**
	 * Enter OrderId
	 * 
	 * @param name
	 */
	public void enterOrderId(String name) {

		waitForElementVisibility(orderId_textbox, "OrderID textbox");
		sendKeys_custom(orderId_textbox, "Order ID textbox", name);
	}

	public void enterAddressDetails(String day, String month, String year, String address, String suburb,
			String zipCode) throws Throwable {

		ListenersImplementation.test.get().log(Status.INFO,
				"Entering the date and address details to fetch the orders");

		waitForElementVisibility(day_textbox, "Day textbox");
		sendKeys_custom(day_textbox, "Date", "23/01/2023");
		/*selectDropDownByVisibleText_custom(month_dropdown, "Month", month);
		sendKeys_custom(year_textbox, "Year", year);*/
		sendKeys_custom(address_textbox, "Address", address);
		sendKeys_custom(suburb_textbox, "Suburb", suburb);
		sendKeys_custom(zipcode_textbox, "Post Code", zipCode);
	}

	/**
	 * Search Orders By OrdersId
	 */
	public void searchOrders_ByOrdersId() {

		click_custom(searchOrderId_button, "Search Orders");
	}

	/**
	 * Search Orders By Address Combinations
	 */
	public void searchOrderId_AddressCombination() {

		click_custom(search_buttonByDate, "Search Orders");
	}

	/**
	 * Clear Records
	 */
	public void clearRecords() {

		click_custom(clear_button, "Clear Button");
		checkPageIsReady();
	}

	/**
	 * 
	 */
	public void waitForOrderList() {

		waitForAllElementsPresent(searchTable, "Records table");
	}

	/**
	 * 
	 * @return
	 */
	public boolean verifyOrderId_Value() {

		String value = getAttribute_value_custom(orderId_textbox, "OrderId");
		if (value.isEmpty()) {

			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean verifySuburb_Value() {

		String value = getAttribute_value_custom(suburb_textbox, "Suburb Textbox");
		if (value.isEmpty()) {

			return true;
		} else {
			return false;
		}
	}

	/**
	 * ZipCode Textbox value
	 * 
	 * @return
	 */
	public boolean verifyPostCode_Value() {

		String value = getAttribute_value_custom(zipcode_textbox, "Suburb Textbox");
		if (value.isEmpty()) {

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Address Textbox
	 * 
	 * @return
	 */
	public boolean verifyAddress_Value() {

		String value = getAttribute_value_custom(address_textbox, "Address Textbox");
		if (value.isEmpty()) {

			return true;
		} else {
			return false;
		}
	}

	public boolean verifyDate_Value() {

		String value = getAttribute_value_custom(day_textbox, "Date Textbox");
		if (value.isEmpty()) {

			return true;
		} else {
			return false;
		}
	}

	public boolean verifyYear_Value() {

		String value = getAttribute_value_custom(year_textbox, "Year Textbox");
		if (value.isEmpty()) {

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Return Orders
	 */
	public List<String> getOrderDetails_addressDateConbination() {

		String address = getAttribute_value_custom(address_textbox, "Address");
		String suburb = getAttribute_value_custom(suburb_textbox, "Suburb");
		String postCode = getAttribute_value_custom(zipcode_textbox, "Post Code");
		String date_order = getAttribute_value_custom(day_textbox, "Date");
		String year_order = getAttribute_value_custom(year_textbox, "Year");
		String month_order = getSelectedOption_Dropdown(month_dropdown, "Month");
		String orderDate = month_order + " " + date_order + ", " + year_order;
		boolean flag = false;

		System.out.println("=====>>>>>" + orderDate);
		List<String> data = basepage.getDetails_DB(Queries.getAddressId(address, suburb, postCode));
		System.out.println("--->" + data.size());
		List<String> getOrders = new ArrayList<String>();
		for (int i = 0; i < data.size(); i++) {

			List<String> date = basepage.getDetails_DB(Queries.getOrderDate(data.get(i)));
			System.out.println("---------------> " + data.get(i));
			flag = date.get(i).equals(orderDate);

			if (flag) {

				getOrders = basepage.getDetails_DB(Queries.getOrdersFromAddressID(data.get(i)));
			}
		}
		return getOrders;
	}

	/**
	 * 
	 */
	public void verifyOrders_addressDateCombiation() {

		List<String> getOrders = getOrderDetails_addressDateConbination();

		ListenersImplementation.test.get().log(Status.INFO, "Getting the Order Details from CSR application");

		List<String> orderDetails_csr = new ArrayList<>();
		List<WebElement> ele = DriverFactory.getInstance().getDriver().findElements(searchTable);

		for (int i = 0; i < ele.size() - 1; i++) {

			orderDetails_csr.add(ele.get(i).getText().trim());
		}

		for (int k = 0; k < getOrders.size(); k++) {

			System.out.println("=============> " + orderDetails_csr.get(k) + " " + getOrders.get(k));
			assertEquals_custom(orderDetails_csr.get(k).trim(), getOrders.get(k), "Order details");
		}

	}

	/**
	 * Cancel Order
	 * 
	 * @throws Throwable
	 */
	public void cancelOrder_findOrderPage() throws Throwable {

		String order = getText_custom(orderStatus, "Order Status");
		//assertEquals_custom(order, "Submitted", "Order Status");
		OrderSummaryPage orderSummary = new OrderSummaryPage();
		if (order.equals("Submitted")) {

			moveToElement_custom(actionGear, "Action geat icon");
			orderSummary.verifyActionGridItems("Submitted");
			moveToElement_custom(cancelOrder, "Cancel Order");
			waitForTextPresent(cancelOrder_msg, "Cancel this order");
			cancelTheOrder("Order has been cancelled successfully!");
			order = getText_custom(orderStatus, "Order Status");
			assertEquals_custom(order, "Cancelled", "Order Status");
			ListenersImplementation.test.get().log(Status.INFO, "Order has been cancelled successfully");
		}

		else if (order.equals("Shipped")) {
			moveToElement_custom(actionGear, "Action geat icon");
			orderSummary.verifyActionGridItems("Shipped");
			moveToElement_custom(cancelOrder, "Cancel Order");
			waitForTextPresent(cancelOrder_msg, "Cancel this order");
			cancelTheOrder("Order has been cancelled successfully!");
			order = getText_custom(orderStatus, "Order Status");
			assertEquals_custom(order, "Cancelled", "Order Status");
			ListenersImplementation.test.get().log(Status.INFO, "Order has been cancelled successfully");
		}

		else if (order.equals("Canceled")) {
			moveToElement_custom(actionGear, "Action geat icon");
			orderSummary.verifyActionGridItems("Cancelled");
			ListenersImplementation.test.get().log(Status.INFO, "Order is already in cancelled state");
		}

	}

	/**
	 * Cancel The Order
	 * 
	 * @throws Throwable
	 */
	public void cancelTheOrder(String text) throws Throwable {

		ListenersImplementation.test.get().log(Status.INFO, "Selecting a random reason to cancel the order");
		List<WebElement> orderReasons = DriverFactory.getInstance().getDriver().findElements(reasonDropDownOptions);

		Random ran = new Random();
		int index = ran.nextInt(orderReasons.size());
		System.out.println("=====> " +index);
		if(index==0){
			index = 2;
		}
		String selectedReason = selectDropDownByIndex_custom(reasonDropDown, "Cancel Order Reason", index);
		System.out.println("======<><> "+selectedReason);
		checkPageIsReady();
		sendKeys_custom(comment_textbox, "Comment textbox", selectedReason);
		boolean flag = isElementPresent(cancelOrder_button, "yes cancel order button");
		if(flag)
			click_custom(cancelOrder_button, "Cancel Order button");
		else
			click_custom(okay_button, "Okay button");
		
		waitForTextPresent(successMessage, text);
	}
	
	public void bulkCancellation(String text) throws Throwable {
		Thread.sleep(1000);

		ListenersImplementation.test.get().log(Status.INFO, "Selecting a random reason to cancel the order");
		List<WebElement> orderReasons = DriverFactory.getInstance().getDriver().findElements(reasonDropDownOptions);

		Random ran = new Random();
		int index = ran.nextInt(orderReasons.size());
		System.out.println("=====> " +index);
		if(index==0){
			index = 2;
		}
		String selectedReason = selectDropDownByIndex_custom(reasonDropDown, "Cancel Order Reason", index);
		System.out.println("======<><> "+selectedReason);
		checkPageIsReady();
		sendKeys_custom(comment_textbox, "Comment textbox", selectedReason);
		click_custom(okay_button, "Okay button");
		waitForTextPresent(successMessage, text);
	}

	public String modifyDate(String text) {

		String date = text;

		String[] d = date.split(",");

		String[] d2 = d[0].split(" ");

		String d3 = null;

		if (d2[1].length() == 1) {
			d3 = "0" + d2[1];
			System.out.println(d3);
		} else {
			d3 = d2[1];
		}

		return d2[0] + " " + d3 + "," + d[1];
	}
	
	/**
	 * Navigate To Order Summary Page
	 */
	public void navigateToOrderSummaryPage(){
		
		moveToElement_custom(actionGear, "Action geat icon");
		moveToElement_custom(orderSummaryOption, "View Order Summary");
		waitForTextPresent(header, "Order Summary");
	}
}
