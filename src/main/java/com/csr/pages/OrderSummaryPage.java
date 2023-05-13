/**
 * 
 */
package com.csr.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.Queries;
import com.graphbuilder.math.func.RandFunction;

/**
 * @author akaushi3
 *
 */
public class OrderSummaryPage extends BasePage {

	OrdersPage orderPage = new OrdersPage();
	public static String productSKUNumber = null;
	public static String unitsOfItems = null;
	public static String orderID = null;

	By summaryOfOrder = By.xpath("//div[@class='order-summery-first-description']//div[@class='full-row']//div[2]");
	By summaryOfCharges = By.xpath(
			"//div[@class='order-summery-second-description']//div[@class='full-row']//div[2][contains(@class, 'col-50')]");
	By shipTo = By.xpath("//div[@class='order-summery-second-description']//div[@class='col-25'][2]");
	By findProduct_textbox = By.id("find-item-input");
	By find_button = By.xpath("//button[text()='Find']");
	By clear_button = By.xpath("//button[text()='Clear']");
	By createReturn_button = By.xpath("//button[contains(text(), 'Return From Order')]");
	By resumeSavedReturn_button = By.xpath("//button[contains(text(), 'Resume Saved Return')]");
	By cancel_button = By.xpath("(//button[contains(text(), 'Cancel')])[2]");
	By orderNumber = By.xpath("//div[@class='col-25']/h1");
	By actionGrid = By.xpath("//div[@class='actionDropdown actionDropdown_csr' and @style='display: block;']");
	By viewOrderSummary = By.xpath(
			"//div[@class='actionDropdown actionDropdown_csr' and @style='display: block;']//a[text()='View Order Summary']");
	By actionGridItems = By.xpath(
			"//ul[@class='actionDropdown actionDropdown_csr' and @style='display: block;']//li[@class='actionItem']//a");
	By numberOfItems = By.xpath("//div[@class='full-row']//table//tr[2]//td[1]");
	By productSKU = By.xpath("//div[@class='full-row']//table//tr//td[2]");
	By quantityOfItems = By.xpath("//div[@class='full-row']//table//tr//td[7]");
	By numberOfRecords = By.xpath("//span[@class='record-total']");
	By productSKUheader = By.xpath("//th[text()='Product SKU']");
	By orderTable = By.xpath("//div[@class='searchlist']");

	/**
	 * 
	 * @throws InterruptedException
	 */
	public void verifyOrderSummary(String email, String orderType) throws InterruptedException {

		String totalOrders = getText_custom(numberOfRecords, "Count of orders");
		List<WebElement> orderStatus = orderPage.getorderStatus();
		totalOrders = totalOrders.replace("records", "").trim();
		ListenersImplementation.test.get().log(Status.INFO, "Total Orders available are: " + totalOrders);

		List<String> orderCount = getDetails_DB(Queries.countOfOrders(email));
		int countOfOrders = Integer.valueOf(orderCount.get(0));
		List<WebElement> orderList = null;
		for (int k = 0; k < countOfOrders; k++) {

			System.out.println("=====> 1");
			orderList = orderPage.ordersList_Orders();

			for (int i = 0; i < orderList.size(); i++) {
				System.out.println("=====> 2");
				String orderId = orderList.get(i).getText().trim();
				System.out.println(">>>>>>>>>>>>>>> " + orderId);

				if (orderStatus.get(i).getText().trim().equalsIgnoreCase(orderType)) {
					ListenersImplementation.test.get().log(Status.INFO, "Navigating to Order summary of "
							+ orderType + " Order: " + orderList.get(i).getText().trim());
					orderList.get(i).click();
					System.out.println("=====> 3");
					waitForInvisibilityofElement(loaderIcon, "Loader Icon");
					checkPageIsReady();
					System.out.println("=====> 4");
					waitForAllElementsPresent(summaryOfOrder, "Summary of order");
					assertThat(getElementState_custom(createReturn_button, "Create return from order button"),
							equalTo(false));
					ListenersImplementation.test.get().log(Status.PASS,
							"By default Create Return button is disabled for " + orderType + " Order");
				}

				else {
					System.out.println("=====> 5");
					if (i == 9) {
						orderPage.clickNextButton();
						waitForInvisibilityofElement(loaderIcon, "Loader Icon");
					}
					continue;
				}

				verifyTheOrderDetails(orderId);
				// verifySummaryOfCharges(orderId); //Depreciated
				click_custom(cancel_button, "Cancel Button");
				waitForInvisibilityofElement(loaderIcon, "Loader Icon");

				break;
			}
		}

	}

	/**
	 * 
	 * @param orderId
	 */
	public void verifyTheOrderDetails(String orderId) {
		System.out.println(".......> 11");
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the Order details");
		waitForAllElementsPresent(shipTo, "Summary of order");
		System.out.println(".......> 12");
		List<String> orderSummary = new ArrayList<String>();
		String orderSum = getText_custom(shipTo, "Shipping Information");

		String[] orderssummaryInfo = orderSum.split("\n");
		for (int k = 0; k < orderssummaryInfo.length; k++) {
			orderSummary.add(orderssummaryInfo[k].trim());
		}
		for (String str : orderSummary) {
			System.out.println("=======> " + str);
		}
		List<String> dbData = getDetails_DB(Queries.getOrderSummary(orderId));
		// for (int z = 0; z < dbData.size(); z++) {
		assertThat("Verifying the shipping information", orderSummary, Matchers.containsInAnyOrder(dbData.toArray()));
		// }
	}

	/**
	 * Removed From The UI
	 * 
	 * @param orderID
	 */
	public void verifySummaryOfCharges(String orderID) {

		ListenersImplementation.test.get().log(Status.INFO, "Verifying the Summary Of Charges");

		waitForAllElementsPresent(summaryOfCharges, "Summary of Charges");
		List<String> orderSummary = new ArrayList<String>();
		List<WebElement> orderSum = DriverFactory.getInstance().getDriver().findElements(summaryOfCharges);

		for (int k = 0; k < orderSum.size(); k++) {
			String amount = orderSum.get(k).getText().trim();
			amount = amount.replace("$", "").trim();
			amount = orderPage.getNumber(amount);
			if (amount.equals("0.00000")) {
				amount = ".00000";
			}
			amount = "$ " + amount;
			orderSummary.add(amount);
		}
		List<String> dbData = getDetails_DB(Queries.getOrderCharges(orderID));

		for (int z = 0; z < dbData.size(); z++) {

			assertContains_custom(dbData.get(z), orderSummary.get(z), "Order summary details");
		}
	}

	/**
	 * Verify the Action Grid Items
	 * 
	 * @param orderType
	 */
	public void verifyActionGridItems_customerProfile(String orderType) {

		List<WebElement> actionGridOpt = DriverFactory.getInstance().getDriver().findElements(actionGridItems);
		List<String> options = new ArrayList<>();
		List<String> expectedItems = new ArrayList<>();
		for (int j = 0; j < actionGridOpt.size(); j++) {
			options.add(actionGridOpt.get(j).getText().trim());
		}

		if (orderType.equalsIgnoreCase("Shipped")) {
			expectedItems.clear();
			expectedItems.add("View Order Summary");
			expectedItems.add("View Return Details");
			assertEquals_list(options, expectedItems, "Action Grid Options for shipped order");
		}

		else if (orderType.equalsIgnoreCase("Submitted")) {
			expectedItems.clear();
			expectedItems.add("View Order Summary");
			assertEquals_list(options, expectedItems, "Action Grid Options for submitted order");
		}

		else if (orderType.equalsIgnoreCase("Cancelled")) {
			expectedItems.clear();
			expectedItems.add("View Order Summary");
			assertEquals_list(options, expectedItems, "Action Grid Options for cancelled order");
		}

	}

	/**
	 * Verify the Action Grid Items
	 * 
	 * @param orderType
	 */
	public void verifyActionGridItems(String orderType) {

		List<WebElement> actionGridOpt = DriverFactory.getInstance().getDriver().findElements(actionGridItems);
		actionGridOpt.removeAll(Arrays.asList("", null));
		List<String> options = new ArrayList<>();
		List<String> expectedItems = new ArrayList<>();
		for (int j = 0; j < actionGridOpt.size(); j++) {
			options.add(actionGridOpt.get(j).getText().trim());
		}

		if (orderType.equalsIgnoreCase("Cancelled")) {
			expectedItems.clear();
			expectedItems.add("View Order Summary");
			expectedItems.add("Invoice");
			assertEquals_list(options, expectedItems, "Action Grid Options for cancelled order");
		}

		else if (orderType.equalsIgnoreCase("Submitted")) {
			expectedItems.clear();
			expectedItems.add("View Order Summary");
			expectedItems.add("Cancel Order");
			assertEquals_list(options, expectedItems, "Action Grid Options for submitted order");
		}

		else if (orderType.equalsIgnoreCase("Shipped")) {
			expectedItems.clear();			
			expectedItems.add("View Order Summary");
			//expectedItems.add("Invoice");
			expectedItems.add("Cancel Order");
			expectedItems.add("View Return Details");
			assertEquals_list(options, expectedItems, "Action Grid Options for shipped order");
		}

	}

	/**
	 * Navigate To Shipped Order
	 * 
	 * @throws InterruptedException
	 */
	public boolean navigateTo_ShippedOrder(String email) throws InterruptedException {
		
		waitForElementVisibility(orderTable, "Orders");
		boolean flag = false;
		List<WebElement> shippedOrder = null;
		List<String> countOfOrders = getDetails_DB(Queries.countOfOrders(email));
		List<WebElement> orderList = null;
		
		for (int j = 0; j < Integer.parseInt(countOfOrders.get(0)); j++) {
			
			
			orderList = DriverFactory.getInstance().getDriver().findElements(By.xpath("//div[@class='searchlist']//table//tr//td[1]"));
			System.out.println("02=======> "+orderList.size());
		
			for (int i = 0; i < orderList.size(); i++) {
				shippedOrder = DriverFactory.getInstance().getDriver().findElements(By.xpath("//div[@class='searchlist']//table//tr//td[2]"));	
				System.out.println("==========>03 "+shippedOrder.get(i).getText().trim());
				if (shippedOrder.get(i).getText().trim().equalsIgnoreCase("Shipped")) {
					try {
						orderID = orderList.get(i).getText().trim();
						Actions action = new Actions(DriverFactory.getInstance().getDriver());
						WebElement element = DriverFactory.getInstance().getDriver().findElement(
								By.xpath("//div[@class='searchlist']//table//tr[" + (i + 3) + "]//td[10]"));
						action.moveToElement(element).click().build().perform();
						checkPageIsReady();
						waitForElementVisibility(actionGrid, "Action Grid");
						verifyActionGridItems_customerProfile("Shipped");
						checkPageIsReady();
						click_custom(viewOrderSummary, "View Order Summary");
						waitForInvisibilityofElement(loaderIcon, "Loader Icon");
						checkPageIsReady();
						flag = true;
						break;
					} catch (Exception e) {
						ListenersImplementation.test.get().log(Status.FAIL,
								"Can not navigate to Shipped order " + e.toString());
					}
				}

				else {
					
					if (i == 9) {
						orderPage.clickNextButton();
						waitForInvisibilityofElement(loaderIcon, "Loader Icon");
						checkPageIsReady();
					}
					continue;
				}
			}
			
			if(flag == true){
				break;
			}
		}
		return flag;
	}

	/**
	 * Selecting the random items to return
	 */
	public void selectItemsToReturn() {

		List<WebElement> element = DriverFactory.getInstance().getDriver().findElements(numberOfItems);
		List<WebElement> sku = DriverFactory.getInstance().getDriver().findElements(productSKU);
		List<WebElement> units = DriverFactory.getInstance().getDriver().findElements(quantityOfItems);
		if (element.size() > 0) {

			//scrollIntoView(productSKUheader, "Product table");
			Random ran = new Random();
			int i = ran.nextInt(element.size());
			element.get(i).click();
			checkPageIsReady();
			productSKUNumber = sku.get(i).getText().trim();
			unitsOfItems = units.get(i).getText().trim();
			System.out.println("ITEMSUNITS=================>" + unitsOfItems);
			boolean flag = getElementState_custom(createReturn_button, "Create return button");
			if (flag == true) {
				ListenersImplementation.test.get().log(Status.PASS,
						"Create return from order button should be enable on selecting any item");
				click_custom(createReturn_button, "Create return");
				waitForInvisibilityofElement(loaderIcon, "Loader Icon");
			} else {
				ListenersImplementation.test.get().log(Status.FAIL,
						"Create return from order button should be enable on selecting any item but it is disabled");
				Assert.fail();
			}
		} else {
			ListenersImplementation.test.get().log(Status.INFO, "No items are available to return");
		}
	}

}
