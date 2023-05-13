/**
 * 
 */
package com.csr.pages;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.Queries;

/**
 * @author akaushi3
 *
 */
public class OrdersPage extends BasePage {

	By header_Orders = By.xpath("//h1[text()='Orders']");
	By refresh_button = By.xpath("//button[contains(text(),'Refresh')]");
	By find_button = By.xpath("//button[contains(text(), 'Find')]");
	By clear_button = By.xpath("//button[contains(text() , 'Clear')]");
	By orderID_textbox = By.id("orderId-input");
	By ordersId_list = By.xpath("//div[@class='searchlist']//table//td[1]");
	By OrderStatus_list = By.xpath("//div[@class='searchlist']//table//td[2]");
	By OrderPrice_list = By.xpath("//div[@class='searchlist']//table//td[6]");
	By OrderSubmittedDate_list = By.xpath("//div[@class='searchlist']//table//td[3]");
	By OrderWebCutOff_list = By.xpath("//div[@class='searchlist']//table//td[5]");
	By OrderServiceType_list = By.xpath("//div[@class='searchlist']//table//td[7]");
	By OrderDeliveryWindow_list = By.xpath("//div[@class='searchlist']//table//td[9]");
	By numberOfRecords = By.xpath("//span[@class='record-total']");
	By nextButton = By.xpath("//button[contains(text(), 'Next >')]");
	By noOfRecordsDropdown = By.xpath("//select[@name='rowCount']");
	By pageTextbox = By.xpath("//input[@type='number']");
	By pageNumDetails = By.xpath("//span[@class='current-page-num']");
	By previousButton = By.xpath("//button[text()='< Prev']");

	BasePage basepage = new BasePage();
	// OrderSummaryPage orderSummary = new OrderSummaryPage();
	FindOrderPage findOrder = new FindOrderPage();

	/**
	 * Refresh Button State
	 * 
	 * @return
	 */
	public boolean getRefreshButtonState() {

		ListenersImplementation.test.get().log(Status.INFO, "Getting the state of Refresh button");
		waitForElementVisibility(refresh_button, "Refresh Button");
		return getElementState_custom(refresh_button, "Refresh Button");
	}

	/**
	 * Clear Button
	 * 
	 * @return
	 */
	public boolean getClearButtonState() {

		ListenersImplementation.test.get().log(Status.INFO, "Getting the state of Clear button");
		return getElementState_custom(clear_button, "Clear button");
	}

	/**
	 * Find Button
	 * 
	 * @return
	 */
	public boolean getFindButtonState() {

		ListenersImplementation.test.get().log(Status.INFO, "Getting the state of Find button");
		return getElementState_custom(find_button, "Find Button");
	}

	/**
	 * Order ID textbox
	 * 
	 * @return
	 */
	public boolean getOrderIdTextBoxState() {

		ListenersImplementation.test.get().log(Status.INFO, "Getting the state of Order ID textbox");
		return getElementState_custom(orderID_textbox, "Order ID textbox");
	}

	/**
	 * Verifying the OrderID
	 * 
	 * @param email
	 * @return
	 */
	public List<String> getActiveOrders() {

		ListenersImplementation.test.get().log(Status.INFO, "Verifying the Active Orders of Customer");

		String numberOfRec = getText_custom(numberOfRecords, "Number Of Records");
		numberOfRec = numberOfRec.replace("records", "").trim();
		List<String> ordersIdDetails = new ArrayList<String>();

		for (int j = 0; j < Integer.parseInt(numberOfRec); j++) {

			List<WebElement> orders = DriverFactory.getInstance().getDriver().findElements(ordersId_list);
			for (int i = 0; i < orders.size(); i++) {
				System.out.println("======================> " + i + " and " + orders.size());
				String orderID = DriverFactory.getInstance().getDriver()
						.findElement(By.xpath("//div[@class='searchlist']//table//tr[" + (i + 3) + "]//td[1]"))
						.getText().trim();
				ordersIdDetails.add(orderID);
				String price = getNumber(DriverFactory.getInstance().getDriver()
						.findElement(By.xpath("//div[@class='searchlist']//table//tr[" + (i + 3) + "]//td[6]"))
						.getText().trim());
				ordersIdDetails.add(price);
				String orderStatus = DriverFactory.getInstance().getDriver()
						.findElement(By.xpath("//div[@class='searchlist']//table//tr[" + (i + 3) + "]//td[2]"))
						.getText().trim();
				ordersIdDetails.add(orderStatus);

				if (i == 9) {
					clickNextButton();
					waitForInvisibilityofElement(loaderIcon, "Loader Icon");
				}
			}

		}

		return ordersIdDetails;
	}

	/**
	 * Verify Order Details
	 * 
	 * @param dbList
	 * @param csrList
	 * @param text
	 */
	public void verifyOrderDetails(List<String> dbList, List<String> csrList, String text) {

		int dbItems = dbList.size();
		System.out.println(dbItems + "======" + csrList.size());

		for (int i = 0; i < dbItems; i++) {

			assertEquals_custom(csrList.get(i), dbList.get(i), text);
		}
	}

	/**
	 * Get Number in Decimal Format
	 * 
	 * @param num
	 * @return
	 */
	public String getNumber(String num) {

		BigDecimal a = new BigDecimal(num);
		BigDecimal roundOff = a.setScale(5, BigDecimal.ROUND_HALF_EVEN);
		String str = roundOff.toString();
		return str;
	}

	/**
	 * Get Order Attributes From CSR
	 * 
	 * @return
	 * @throws ParseException
	 */
	public void getOrderAttributes_CSR() throws ParseException {

		ListenersImplementation.test.get().log(Status.INFO,
				"Getting the Order Submitted Date, Web Cutoff, Service Type, Delivery Window");
		List<WebElement> orders = DriverFactory.getInstance().getDriver().findElements(ordersId_list);
		List<WebElement> orderstatus = DriverFactory.getInstance().getDriver().findElements(OrderStatus_list);
		List<WebElement> getSubmittedDate = DriverFactory.getInstance().getDriver()
				.findElements(OrderSubmittedDate_list);
		List<WebElement> getWebCutoff = DriverFactory.getInstance().getDriver().findElements(OrderWebCutOff_list);
		List<WebElement> getDeliveryWindow = DriverFactory.getInstance().getDriver()
				.findElements(OrderDeliveryWindow_list);

		for (int j = 0; j < getSubmittedDate.size(); j++) {

			if (j == 10) {
				clickNextButton();
				waitForInvisibilityofElement(basepage.loaderIcon, "Loader Icon");
			}

			if (!orderstatus.get(j).getText().trim().equals("Pending")) {
				verifyDateFormats(getSubmittedDate.get(j).getText().trim(),
						"Sumbitted date of OrderID: " + orders.get(j).getText().trim());
				verifyDateFormats(getWebCutoff.get(j).getText().trim(),
						"Web CutOff date of OrderID: " + orders.get(j).getText().trim());
				verifyDeliveryWindowSlot(getDeliveryWindow.get(j).getText().trim());
			}

		}
	}

	/**
	 * Verify the date formats
	 * 
	 * @param date
	 * @param field
	 */
	public void verifyDateFormats(String date, String field) {

		String regexDate = "^[0-3][0-9]/[0-3][0-9]/(?:[0-9][0-9])?[0-9][0-9] [0-9]{2}:[0-9]{2} [A-z][A-z]$";

		Pattern pattern = Pattern.compile(regexDate);

		Matcher matcher = pattern.matcher(date);
		if (matcher.matches() == true) {

			ListenersImplementation.test.get().log(Status.PASS, "The " + field + " is coming in allowed format");
		}

		else {
			ListenersImplementation.test.get().log(Status.FAIL,
					"The " + field + " is not coming in allowed format");
		}
		System.out.println(date + " : " + matcher.matches());
	}

	/**
	 * Verify Delivery Window Slot
	 * 
	 * @param dateTime
	 */
	public void verifyDeliveryWindowSlot(String dateTime) {

		String regexDate = "^[A-z]{3} ([0-2][0-9]|(3)[0-1])(/)(((0)[0-9])|((1)[0-2]))(/)[0-9]{4} [0-9][0-9]:[0-9][0-9]-[0-9][0-9]:[0-9][0-9]$";
		Pattern pattern = Pattern.compile(regexDate);

		Matcher matcher = pattern.matcher(dateTime);
		if (matcher.matches() == true) {

			ListenersImplementation.test.get().log(Status.PASS, "The Delivery slot is coming in allowed format");
		}

		else {
			ListenersImplementation.test.get().log(Status.FAIL,
					"The Delivery slot is not coming in allowed format");
		}
		System.out.println(dateTime + " : " + matcher.matches());

	}

	/**
	 * Get Number Of Orders
	 * 
	 * @return
	 */
	public String getTotalNumberOrders() {

		String str = getText_custom(numberOfRecords, "Number Of Records");
		str = str.replace("records", "").trim();
		return str;
	}

	/**
	 * Number Of Records Per page
	 * 
	 * @return
	 */
	public String getNumberOfRecordsPerPage() {

		checkPageIsReady();
		List<WebElement> orders = DriverFactory.getInstance().getDriver().findElements(ordersId_list);
		return String.valueOf(orders.size());
	}

	/**
	 * 
	 * @return
	 */
	public List<WebElement> ordersList_Orders() {
		checkPageIsReady();
		return DriverFactory.getInstance().getDriver().findElements(ordersId_list);
	}

	/**
	 * Select Number Of Records To Show Per Page
	 * 
	 * @param text
	 */
	public void selectNumberOfRecordsToShow(String text) {

		try {
			selectDropDownByVisibleText_custom(noOfRecordsDropdown, "Number of records to show", text);
			checkPageIsReady();
			// --Need To Remove Once We have loader
			Thread.sleep(5000);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Click Next Button
	 */
	public void clickNextButton() {

		click_custom(nextButton, "Next button");
		checkPageIsReady();
	}

	/**
	 * Get Page Number
	 * 
	 * @return
	 */
	public String getAttValue_pageNum() {

		return getAttribute_value_custom(pageTextbox, "Page Number");
	}

	/**
	 * Verify Next Button Functionality
	 * 
	 * @throws InterruptedException
	 */
	public void navigateToLastPageOfPagination() throws InterruptedException {

		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying that CSR is able to navigate to last page of pagination using Next button");

		String totalPage = getText_custom(pageNumDetails, "Page Number Details");
		totalPage = totalPage.replace("Page of", "");
		ListenersImplementation.test.get().log(Status.INFO, "Total " + totalPage.trim() + " pages are there.");
		for (int i = 1; i <= Integer.valueOf(totalPage.trim()); i++) {

			click_custom(nextButton, "Next Button");
			checkPageIsReady();
			Thread.sleep(3000);
			waitForElementVisibility(ordersId_list, "Orders Id list");
			ListenersImplementation.test.get().log(Status.PASS, "User Is navigated to page number " + i + 1 + "");
		}
	}

	/**
	 * Get Order Status List
	 * 
	 * @return
	 */
	public List<WebElement> getorderStatus() {

		return DriverFactory.getInstance().getDriver().findElements(OrderStatus_list);
	}

	public List<WebElement> getOrderNumbers() {

		return DriverFactory.getInstance().getDriver().findElements(ordersId_list);
	}

	/**
	 * @throws Throwable
	 * 
	 */
	public void cancelOrder(String email, String orderType) throws Throwable {

		// List<WebElement> orderNo = getOrderNumbers();
		
		boolean flag = false;
		
		List<String> orderCount = basepage.getDetails_DB(Queries.countOfOrders(email));

		int countOfOrders = Integer.valueOf(orderCount.get(0));
		
		System.out.println("Count of DB Orders ===============>"+countOfOrders);
		for (int k = 0; k < countOfOrders; k++) {

			List<WebElement> orderNo = DriverFactory.getInstance().getDriver()
					.findElements(By.xpath("//div[@class='searchlist']//table//tr//td[1]"));

			System.out.println("UI Order Count is =====> "+orderNo.size());
			
			for (int i = 0; i < orderNo.size(); i++) {

				String orderStatus = DriverFactory.getInstance().getDriver()
						.findElement(By.xpath("//div[@class='searchlist']//table//tr[" + (i + 2) + "]//td[2]"))
						.getText().trim().toString();
				System.out.println("UI Order Status is :=======> " + orderStatus);

				if (orderStatus.equals(orderType)) {
					ListenersImplementation.test.get().log(Status.INFO,
							"Verifying the action grid options and cancelling the order");
					Actions action = new Actions(DriverFactory.getInstance().getDriver());
					action.moveToElement(DriverFactory.getInstance().getDriver()
							.findElement(By.xpath("//div[@class='searchlist']//table//tr[" + (i + 2) + "]//td[8]")))
							.click().build().perform();
					// orderSummary.verifyActionGridItems(orderType);
					DriverFactory.getInstance().getDriver()
							.findElement(By.xpath("//div[@class='actionDropdown actionDropdown_csr hidden' and @style='display: block;']/div//div//span[text()='Cancel Order']"))
							.click();
					findOrder.cancelTheOrder("Order has been cancelled successfully!");
					flag = true;
					break;

				}

				else {
					if (i == 24) {
						System.out.println("CLICKED========");
						click_custom(nextButton, "next button");
						waitForInvisibilityofElement(basepage.loaderIcon, "Loader Icon");
					}
					//continue;
				}
			}
			
			if(flag == true){
				break;
			}
		}
	}
	public void cancelOrder_AfterAccessCustomer_Orders(String email, String orderType) throws Throwable {

		// List<WebElement> orderNo = getOrderNumbers();
		
		boolean flag = false;
		
		List<String> orderCount = basepage.getDetails_DB(Queries.countOfOrders(email));

		int countOfOrders = Integer.valueOf(orderCount.get(0));
		
		System.out.println("Count of DB Orders ===============>"+countOfOrders);
		for (int k = 0; k < countOfOrders; k++) {

			List<WebElement> orderNo = DriverFactory.getInstance().getDriver()
					.findElements(By.xpath("//div[@class='searchlist']//table//tr//td[1]"));

			System.out.println("UI Order Count is =====> "+orderNo.size());
			
			for (int i = 0; i < orderNo.size(); i++) {

				String orderStatus = DriverFactory.getInstance().getDriver()
						.findElement(By.xpath("//div[@class='searchlist']//table//tr[" + (i + 3) + "]//td[2]"))
						.getText().trim().toString();
				System.out.println("UI Order Status is :=======> " + orderStatus);

				if (orderStatus.equals(orderType)) {
					ListenersImplementation.test.get().log(Status.INFO,
							"Verifying the action grid options and cancelling the order");
					Actions action = new Actions(DriverFactory.getInstance().getDriver());
					action.moveToElement(DriverFactory.getInstance().getDriver()
							.findElement(By.xpath("//div[@class='searchlist']//table//tr[" + (i + 3) + "]//td[11]")))
							.click().build().perform();
					// orderSummary.verifyActionGridItems(orderType);
					DriverFactory.getInstance().getDriver()
							.findElement(By.xpath("//div[@class='actionDropdown actionDropdown_csr' and @style='display: block;']/div//div//span[text()='Cancel Order']"))
							.click();
					findOrder.cancelTheOrder("Order has been cancelled successfully!");
					/*//waitForInvisibilityofElement(loaderIcon, "Loader Icon");
					Thread.sleep(5000);
					refresh();
					Thread.sleep(10000);
					waitForInvisibilityofElement(loaderIcon, "Loader Icon");
					System.out.println("xpath is //div[@class='searchlist']//table//tr[" + (i + 2) + "]//td[2]");
					String newStatus = DriverFactory.getInstance().getDriver()
							.findElement(By.xpath("//div[@class='searchlist']//table//tr[" + (i + 2) + "]//td[2]"))
							.getText().toString();
					assertEquals_custom(newStatus, "Cancelled", "Updated order status for "+orderNo.get(i).getText().trim()+"");*/
					flag = true;
					break;

				}

				else {
					if (i == 24) {
						System.out.println("CLICKED========");
						click_custom(nextButton, "next button");
						waitForInvisibilityofElement(basepage.loaderIcon, "Loader Icon");
					}
					//continue;
				}
			}
			
			if(flag == true){
				break;
			}
		}
	}
}
