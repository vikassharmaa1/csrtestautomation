/**
 * 
 */
package com.csr.pages;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.Queries;

/**
 * @author akaushi3
 *
 */
public class OrderReturn extends BasePage {

	public static String reason = null;
	public static String returnType = null;
	public static String actualAmount = null;
	BasePage basepage = new BasePage();
	public static String rmaID = null;
	String items = null;
	public static String timeSlot = null;
	public String fulfillmentCenterPostCode = null;
	public static String returnMessage = null;

	By globalReason_dropdown = By.xpath("//select[@id='glReturnReason']");
	By productSKU = By.xpath("//div[@class='full-row']//table//tr//td[1]");
	By quantity = By.xpath("//input[@ng-model='qty']");
	By itemReturnreason = By.xpath("//select[@name='glReturnReason']");
	By price = By.xpath("//div[@class='row']//table//tr//td[6]");
	By totalAmount = By.xpath("//div[@class='row']//table//tr//td[9]");
	// By creditAdjustment =
	// By.xpath("//div[@class='full-row']//table//tr//td[6]");
	By returnReason_comment = By.xpath("//select[@id='returnReason2']");
	By comment_textbox = By.xpath("//textarea[@id='txtComment']");
	By returnMode = By.id("ddlAction");
	By saveForLater_button = By.xpath("//button[text()='Save for Later']");
	By submit_button = By.xpath("//button[text()='Submit']");
	By previewSaveButton = By.xpath("//*[contains(text(),'Confirm and Save')]");
	By backToOrderSummary = By.xpath("//span[contains(text(),'Back to Order Summary')]");
	By returnReasons = By.xpath("//select[@id='glReturnReason']//option");
	By returnsTableFirstItem = By.xpath("//div[@class='container mar-top-25']//table//tr[2]//td");
	By resumeSavedReturn_button = By.xpath("//button[text()='Resume Saved Return']");
	By shipping_button = By.xpath("//button[text()='Shipping']");
	By cancel_button = By.xpath("//button[@data-ng-click='OrderReturnCSRVM.openCancelPopup()']");
	By delivery_Type = By.xpath("//select[@id='ddlAction']");
	By delivery_Type_Options = By.xpath("//select[@id='ddlSelectDelType']");
	By selectionWindowText = By.xpath("//h1[text()='Select a window...']");
	By deliveryWindow_SelectButton = By.xpath("//div[@class='center-align']//button[text()='Select']");
	By deliveryWindow_CancelButton = By.xpath("//button[@data-ng-click='OrderReturnCSRVM.closeModalReturn()']");
	By nextSevenDays_button = By.xpath("//button[contains(text(),'Next 7 Days')]");
	By previousSevenDays_button = By.xpath("//button[contains(text(),'Previous 7 Days')]");
	By redelivery_Okay = By.xpath("//div[@id='messagePopup']//button[contains(text(),'Okay')]");
	By reDelivery_message = By.xpath("//div[@aria-label='OrderReturnCSRVM.successMessageInPopup']");
	By creditAdjustment = By.xpath("//input[@data-ng-model='item.creditAdjustment']");
	By fullfillmentCenterDetails = By
			.xpath("//div[contains(@data-ng-if, 'OrderReturnCSRVM.fulfilmentCenterDataHD')]//div[2]//label");
	By okayButton_confirmPopup = By
			.xpath("//div[@id='messagePopup']//button[@aria-label='Yes' and contains(text(), 'Okay')]");
	By confirmMessage = By.xpath("//div[contains(text(),'Are you sure you want to cancel the return?')]");

	/**
	 * Select Global Reason
	 * 
	 * @throws Throwable
	 */
	public void selectRandomReason_global() throws Throwable {
		List<WebElement> ele = DriverFactory.getInstance().getDriver().findElements(returnReasons);
		Random ran = new Random();
		int i = ran.nextInt(ele.size());
		reason = selectDropDownByIndex_custom(globalReason_dropdown, "Global return reason", i);

	}

	/**
	 * Initiate return process
	 * 
	 * @throws Throwable
	 */
	public void initiateReturnProcess(String returnAction) throws Throwable {

		items = OrderSummaryPage.unitsOfItems;

		if (Double.valueOf(items) > 1) {
			items = "2";
		} else {
			items = "1";
		}
		sendKeys_custom(quantity, "Quantity of items", items);
		checkPageIsReady();
		Thread.sleep(3000);
		String selectedOption = getSelectedOption_Dropdown(itemReturnreason, "Return reason");
		assertEquals_custom(selectedOption, reason, "Return reason");
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the total amount of items");
		double expectedTotal = Double.valueOf(items) * Double.valueOf(getText_custom(price, "Unit Price"));
		DecimalFormat format = new DecimalFormat("#.00");
		String expTotal = format.format(expectedTotal);
		String expAmountTotal = getText_custom(price, "price");
		actualAmount = getText_custom(totalAmount, "Total amount");
		if (String.valueOf(expTotal).contains(actualAmount)) {
			ListenersImplementation.test.get().log(Status.PASS,
					"The total amount value is correct and it is :" + expectedTotal);
			checkPageIsReady();
			/*scrollIntoView(returnReason_comment, "Return reason dropdown");*/		
			sendKeys_custom(comment_textbox, "Comment", "Returning the items because: " + reason);
			Thread.sleep(10000);
			selectDropDownByVisibleText_custom(returnReason_comment, "Return reason", reason);
			checkPageIsReady();
			Thread.sleep(1000);
			checkPageIsReady(); // --Need to remove this
			selectDropDownByOption_custom(returnMode, "Return Type", returnAction);
			returnType = returnAction;
		} else {
			ListenersImplementation.test.get().log(Status.FAIL,
					"The total amount value is incorrect and it is :" + expectedTotal);
			Assert.fail();
		}
	}

	/**
	 * Save For Later
	 */
	public void saveForLater() {
		click_custom(saveForLater_button, "Save for later");
		waitForInvisibilityofElement(basepage.loaderIcon, "Loader Icon");
	}

	/**
	 * 
	 */
	public void navigateToOrderReturn() {
		click_custom(resumeSavedReturn_button, "Resume saved return");
		waitForInvisibilityofElement(basepage.loaderIcon, "Loader icon");
		checkPageIsReady();
		waitForElementVisibility(globalReason_dropdown, "Order return page");
	}

	/**
	 * 
	 * @param returnAction
	 * @throws Throwable
	 */
	public void submitPendingReturn(String returnAction) throws Throwable {

		sendKeys_custom(comment_textbox, "Comment", "Closing the return of the items because: " + reason);
		selectDropDownByOption_custom(returnMode, "Return Type", returnAction); // Need
																				// to
																				// remove
																				// this
																				// as
																				// it
																				// is
																				// due
																				// to
																				// a
																				// bug
		click_custom(submit_button, "Submit returns");
	}

	/**
	 * 
	 */
	public void verifySavedReturn() {
		List<WebElement> returnDetails = null;
		List<String> returnItemDetails = null;
		try {
			returnDetails = DriverFactory.getInstance().getDriver().findElements(returnsTableFirstItem);
			returnItemDetails = new ArrayList<String>();
			for (int i = 0; i < returnDetails.size(); i++) {
				returnItemDetails.add(returnDetails.get(i).getText().trim());
			}

			ListenersImplementation.test.get().log(Status.INFO,
					"Verifying the order number and OrderId and return status and other details");

			for (int j = 0; j < returnItemDetails.size(); j++) {

				String detail = returnItemDetails.get(j);
				if (detail.equals(OrderSummaryPage.orderID)) {

					ListenersImplementation.test.get().log(Status.PASS,
							"OrderID in the return details is correct and it is :" + OrderSummaryPage.orderID);
				}

				else if (detail.equals("Pending")) {
					ListenersImplementation.test.get().log(Status.PASS,
							"Return status in the return details is correct.");
				}

				else if (detail.equals("Credit") || detail.equals("Re-Delivery") || detail.equals("Refund")) {
					ListenersImplementation.test.get().log(Status.PASS,
							"Return type in the return details is correct and it is: " + returnType);
				}

				else if (detail.equals(reason)) {
					ListenersImplementation.test.get().log(Status.PASS,
							"Return reason in the return details is correct and it is: " + reason);
				}

				else if (detail.contains("Returning the items because: " + reason)) {
					ListenersImplementation.test.get().log(Status.PASS,
							"Comment for saving the return is correct in return details");
				}

			}

			ListenersImplementation.test.get().log(Status.INFO, "Navigating to Order Return page.");
			returnDetails.get(1).click();
			waitForInvisibilityofElement(basepage.loaderIcon, "Loader Icon");
			waitForElementVisibility(resumeSavedReturn_button, "Resume Saved Return");

		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL, "Return details are not available " + e);
			Assert.fail();
		}

	}

	/**
	 * Select delivery type
	 * 
	 * @param deliveryType
	 */
	public void selectDeliveryType(String deliveryType) {

		ListenersImplementation.test.get().log(Status.INFO, "Selecting delivery type as " + delivery_Type_Options + "");
		try {
			selectDropDownByVisibleText_custom(delivery_Type_Options, "Delivery Type", deliveryType);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Verify Delivery type
	 * 
	 * @param orderID
	 */
	public void verifyDeliveryTypeOptions(String orderID) {

		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying the delivery types available for the order");
		List<String> getDeliveryType = getDetails_DB(Queries.getOrderDeliveryType(orderID));
		if (getDeliveryType.size() > 0) {

			if (getDeliveryType.get(0).equals("HD")) {

				List<WebElement> opt = new Select(DriverFactory.getInstance().getDriver().findElement(delivery_Type_Options))
						.getOptions();
				List<String> expOpt = new ArrayList<>();
				expOpt.add("Home Delivery");
				expOpt.add("Click & Collect");
				for (int i = 1; i < opt.size(); i++) {

					assertEquals_custom(opt.get(i).getText().trim(), expOpt.get(i - 1), "Delivery type for HD Orders");
				}
			} else if (getDeliveryType.get(0).equals("CC")) {
				List<WebElement> opt = new Select(DriverFactory.getInstance().getDriver().findElement(delivery_Type_Options))
						.getOptions();
				List<String> expOpt = new ArrayList<>();
				expOpt.add("Click & Collect");
				for (int i = 1; i < opt.size(); i++) {

					assertEquals_custom(opt.get(i).getText().trim(), expOpt.get(i - 1), "Delivery type for CC Orders");
				}
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getFulfilmentCenter() {

		return getText_custom(fullfillmentCenterDetails, "Fulfillment center");
	}

	/**
	 * Verifying the button States
	 */
	public void verifyButtonsState() {

		boolean flag = false;
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the state of the buttons");

		flag = getElementState_custom(submit_button, "Submit button");
		if (flag) {
			ListenersImplementation.test.get().log(Status.FAIL, "Submit button should be disabled by default");
		}

		flag = getElementState_custom(shipping_button, "Shipping button");
		if (!flag) {
			ListenersImplementation.test.get().log(Status.FAIL, "Shiping button should be enable by default");
		}
		flag = getElementState_custom(cancel_button, "Cancel button");
		if (!flag) {
			ListenersImplementation.test.get().log(Status.FAIL, "Cancel button should be enable by default");
		}
	}

	/**
	 * Verifying the db details
	 * 
	 * @param orderID
	 */
	public void verifyDBdetails_return(String orderID) {

		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying that RMA table should have entry for the return as Pending");

		List<String> rmaTable = getDetails_DB(Queries.getRmaComments(orderID));

		if (rmaTable.size() > 0) {
			rmaID = rmaTable.get(0);
			assertContains_custom(rmaTable.get(1), actualAmount, "Verifying the total amount");
			assertEquals_custom(rmaTable.get(2), "EDT", "Status of the return return");
		}

	}

	/**
	 * Delivery Slot window
	 */
	public boolean selectDeliverySlot() {
		boolean selectSlot = false;
		ListenersImplementation.test.get().log(Status.INFO, "Selecting a delivery slot");
		click_custom(shipping_button, "Shipping");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		waitForTextPresent(selectionWindowText, "Select a window...");
		boolean flag = false;
		flag = DriverFactory.getInstance().getDriver().findElement(deliveryWindow_SelectButton).isEnabled();
		if (!flag) {
			ListenersImplementation.test.get().log(Status.PASS,
					"Select button should be diabled when no slot is selected");
		} else {
			ListenersImplementation.test.get().log(Status.FAIL,
					"Select button should not be diabled when no slot is selected");
		}

		ListenersImplementation.test.get().log(Status.INFO, "Click Next 7 button should take CSA to next page");
		click_custom(nextSevenDays_button, "Next 7 days slot");
		checkPageIsReady();
		WebElement ele = waitForElementClickable(previousSevenDays_button, "Previous 7 days");
		if (ele.isDisplayed()) {
			click_custom(previousSevenDays_button, "Previous 7 days");
			WebElement element = waitForElementClickable(nextSevenDays_button, "Next seven days");
			if (element.isDisplayed()) {
				ListenersImplementation.test.get().log(Status.PASS, "Page naviation is working fine");
			} else {
				ListenersImplementation.test.get().log(Status.FAIL,
						"Page navigation is not working as per expectation");
			}
		} else {
			ListenersImplementation.test.get().log(Status.FAIL,
					"Page navigation is not working as per expectation");
		}

		try {
			List<WebElement> slots = DriverFactory.getInstance().getDriver().findElements(
					By.xpath("//div[@class='radio-button-container bag-option-1']//input[@type='radio']"));

			if (slots.size() > 0) {
				System.out.println("===============> " + slots.size());
				int i;
				if (slots.size() > 1) {
					i = getRandomNo(slots.size());
				} else {
					i = 0;
				}
				System.out.println("i ======> " + i);
				DriverFactory.getInstance().getDriver().findElement(By.xpath(
						"(//div[@class='radio-button-container bag-option-1']//input[@type='radio'])[" + (i + 1) + "]"))
						.click();
				// slots.get(i).click();
				String xpath_Timeslot = "(//div[@class='radio-button-container bag-option-1']//input[@type='radio'])["
						+ (i + 1) + "]//ancestor::tr//td[1]";
				By time_Slot = By.xpath(xpath_Timeslot);
				timeSlot = getText_custom(time_Slot, "Delivery time slot");
				click_custom(deliveryWindow_SelectButton, "Select button");
				ListenersImplementation.test.get().log(Status.PASS, "Slot has been selected successfully");
				fulfillmentCenterPostCode = getFulfilmentCenter();
				return selectSlot = true;
			} else {
				return selectSlot = false;
			}
		} catch (Exception e) {
			ListenersImplementation.test.get().log(Status.FAIL, e.toString());
			return selectSlot = false;
		}
	}

	public void selectDeliveryAndBaggingPref() throws InterruptedException {

		ListenersImplementation.test.get().log(Status.INFO, "Selecting the delivery and bagging preference");

		List<WebElement> radioButtons = DriverFactory.getInstance().getDriver().findElements(By.xpath(
				"//label[contains(text(), 'Delivery Preference:')]//parent::div//div[@class='radio-button-container bag-option-1']//input"));

		if (radioButtons.size() > 0) {
			radioButtons.get(0).click();
			ListenersImplementation.test.get().log(Status.PASS, "Selected delivery preference successfully");
		} else {
			ListenersImplementation.test.get().log(Status.FAIL,
					"No delivery choices are available in the selected slot");
		}

		List<WebElement> baggingChoice = DriverFactory.getInstance().getDriver().findElements(By.xpath(
				"//label[contains(text(), 'Bagging Preference:')]//parent::div//div[@class='radio-button-container bag-option-1']//input"));
		if (baggingChoice.size() > 0) {

			if (!baggingChoice.get(0).isSelected()) {
				baggingChoice.get(0).click();
			}
			ListenersImplementation.test.get().log(Status.PASS, "Selected bagging preference successfully");
		}

		click_custom(submit_button, "Submit return");
		Thread.sleep(1000);
		click_custom(previewSaveButton, "Preview Page-Confirm and Save Button");		
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
	}

	/**
	 * Handle Popup
	 * 
	 * @param rmaid
	 */
	public void handleIssueCouponPopUp() {

		ListenersImplementation.test.get().log(Status.INFO, "Verifying the RMAID");

		waitForElementVisibility(redelivery_Okay, "Popup");
		returnMessage = getText_custom(reDelivery_message, "Success message");
		click_custom(redelivery_Okay, "Okay button");
	}

	/**
	 * Handle Popup
	 * 
	 * @param rmaid
	 */
	public void handleRedeliveryPopUp(String rmaid) {

		ListenersImplementation.test.get().log(Status.INFO, "Verifying the RMAID");

		waitForElementVisibility(redelivery_Okay, "Popup");
		assertContains_custom(getText_custom(reDelivery_message, "Success message"), rmaid, "RMA Id");
		click_custom(redelivery_Okay, "Okay button");
	}

	/**
	 * DB entries for return
	 * 
	 * @param rmaID
	 * @param returnMethod
	 * @throws InterruptedException 
	 */
	public void verifyDBentries_return(String rmaID, String returnMethod) throws InterruptedException {

		ListenersImplementation.test.get().log(Status.INFO,
				"Verifing the entries in RMA, RMAItems and Xram tables");

		List<String> getRMAdata = getDetails_DB(Queries.getRMATableData(rmaID));

		if (getRMAdata.size() > 0) {

			assertContains_custom(getRMAdata.get(0), actualAmount, "Total amount");
			assertContains_custom(getRMAdata.get(1), "Approved", "Status");
		}

		else {

			ListenersImplementation.test.get().log(Status.FAIL, "Failed to fetch data from DB");
		}
		
		Thread.sleep(10000);

		List<String> rmaItemsData = getDetails_DB(Queries.getRMAItemsData(rmaID));

		/*String creditAdjustmentVal = getAttribute_value_custom(creditAdjustment, "Credit Adjustment");
		if (creditAdjustmentVal.isEmpty()) {
			creditAdjustmentVal = "0";
		}*/

		if (rmaItemsData.size() > 0) {

			assertContains_custom(rmaItemsData.get(0), actualAmount, "Total Credit amount");
			assertContains_custom(rmaItemsData.get(1), items, "Quantity of items");
			//assertContains_custom(rmaItemsData.get(2), creditAdjustmentVal, "Credit adjustment");
			assertContains_custom(rmaItemsData.get(3), "Approved", "Status of item return");
		} else {
			ListenersImplementation.test.get().log(Status.FAIL, "Failed to fetch data from DB");
		}

		List<String> xrmaData = getDetails_DB(Queries.getXrmaData(rmaID));

		if (xrmaData.size() > 0) {

			assertContains_custom(xrmaData.get(0), returnMethod, "Return method");
			if (returnMethod.equals("Re-Delivery")) {

				String orders_id = xrmaData.get(1);
				if (orders_id == null) {

					ListenersImplementation.test.get().log(Status.FAIL,
							"For redelivery the order id should not be null");
				} else {
					ListenersImplementation.test.get().log(Status.FAIL,
							"For redelivery the order id is " + orders_id);
				}
			}
		}
	}

	/**
	 * Verify Data
	 * 
	 * @param orderID
	 */
	public void verifyXorderAttrData(String orderID) {

		String deliverySlot = null;
		ListenersImplementation.test.get().log(Status.INFO, "Verifying the Delivery Slot details");

		List<String> slotStartTime = getDetails_DB(Queries.getTimeFromXorderattr(orderID, "DMDeliveryWindowStartTime"));
		List<String> slotEndTime = getDetails_DB(Queries.getTimeFromXorderattr(orderID, "DMDeliveryWindowEndTime"));

		if (slotStartTime.size() > 0 && slotEndTime.size() > 0) {
			deliverySlot = slotStartTime.get(0) + "-" + slotEndTime.get(0);
			//assertEquals_custom(deliverySlot, timeSlot, "Delivery window");
		} else {
			ListenersImplementation.test.get().log(Status.PASS, "Unable to fetch data form database");
		}

		String postCode = getPostCode(fulfillmentCenterPostCode);

		List<String> locationID = getDetails_DB(Queries.getxOrderAttrData(orderID, "DMLocationZoneId"));

		if (locationID.size() > 0) {

			//assertEquals_custom(locationID.get(0), postCode, "Fulfillment center postcode");
		}
	}

	public void navigateToFindOrder() {

		click_custom(deliveryWindow_CancelButton, "Delivery window cancel button");
		click_custom(cancel_button, "Cancel return");
		waitForTextPresent(confirmMessage, "Are you sure you want to cancel the return?");
		click_custom(okayButton_confirmPopup, "Okay button");
		click_custom(redelivery_Okay, "Okay button");
		navigateToTab(findAnOrderTab, "Find an Order");
	}

	public String getPostCode(String str) {

		String string = null;

		char[] arr = str.toCharArray();

		for (int i = 0; i < 5; i++) {

			string = string + arr[i];
		}
		return string;
	}
}
