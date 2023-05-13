/**
 * 
 */
package com.csr.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.CheckForNull;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.PropertiesOperations;

/**
 * @author akaushi3
 *
 */
public class ColesOnlineComponents extends BasePage {

	ArrayList<String> tabs = null;
	public static String trollyTotal = "";

	By profileButton = By.xpath("//a[@class='draw-link-account']//span[@data-replacement-text='Your Account']");
	By logoutColesOnline_button = By.className("logout");
	By colesPlusSubscription_button = By.xpath("//span[contains(text(),'Coles Plus subscription')]");
	By subscriptionMessage = By.xpath("//span[@class='tick']//following-sibling::h1");
	By signUp_button = By.xpath("//button[text()='Sign up']");
	By trollyAmount = By
			.xpath("//a[@class='draw-link-cart colrs-animate']//span[contains(@data-ng-bind,'formattedTotalPrice')]");
	By openTrolly = By.xpath("//span[contains(text(),'Trolley and checkout')]");
	By emptyTrollyMessage = By.xpath("//p[@id='empty-trolley-warning-title']");
	By emptyTrollyButton = By.xpath("//button[contains(text(),'Remove all')]");
	By removeEveryThingButton = By.xpath("//button[contains(text(),'Yes, remove everything')]");
	By firstItemTemporarilyUnavailable = By
			.xpath("//div[@id='results-everything']//div[@data-tile-seq-no='1']//div[@class='product-flag']");
	By liquorWarning = By.xpath(
			"//div[@id='results-everything']//div[@data-tile-seq-no='1']//div[@id='contentRecommendationWidget_RS_LiquorWarningTile_Content']//a");
	By tobaccoWarningContent = By.xpath(
			"//div[@id='results-everything']//div[@data-tile-seq-no='1']//div[@id='contentRecommendationWidget_RS_TobaccoWarningTile_Content']");
	By addItemForFirstProduct = By
			.xpath("//div[@id='results-everything']//div[@data-tile-seq-no='1']//button[@type='submit']");
	By searchItemsTextbox = By.id("searchTerm");
	By searchItemButton = By.id("btnSearch");
	By clearSearch = By.id("clear-search");
	By addProductsPopup = By.xpath("//ul[@role='presentation']");
	By enterQuantity = By.xpath("//input[contains(@id,'inputCustomQty')]");
	By updateBtn = By.xpath("//span[text()='Update']");
	By proceedToCheckOutButton = By.xpath("//button[contains(text(),'Proceed to check out')]");
	By selectDeliveryTimeMessage = By.xpath("//span[@class='warning-title']");
	By viewDeliveryTimeButton = By.xpath("//button[contains(text(), 'View delivery options')]");
	By selectHDAddress = By.xpath("//div[contains(@data-ng-if,'dssVM.addressManager.hdAddresses.length')]//p//strong[text()='HD']");
	By selectFirstCCAdress = By.xpath("(//div[@class='cc-locations']//button[contains(@data-ng-repeat, 'ccLocation')])[1]");

	public void openProfileSection() {

		waitForElementVisibility(profileButton, "Profile button");
		moveToElement_custom(profileButton, "Profile section");
		waitForElementVisibility(logoutColesOnline_button, "Profile section");
	}

	public void verifyColesPlus_subscription(String text) {

		click_custom(colesPlusSubscription_button, "Coles Plus subscription button");
		waitForTextPresent(subscriptionMessage, text);
		String expectedMsg = getText_custom(subscriptionMessage, "Subscription message");
		assertEquals_custom(text, expectedMsg, "Coles Plus subscription message");
	}

	public void navigateToColesOnline() {
		ListenersImplementation.test.get().log(Status.INFO, "Navigating to coles online in a new tab.");
		((JavascriptExecutor) DriverFactory.getInstance().getDriver()).executeScript(
				"window.open('" + PropertiesOperations.getPropertyValueByKey("colesOnlineURL") + "','_blank');");
		tabs = new ArrayList<>(DriverFactory.getInstance().getDriver().getWindowHandles());
		DriverFactory.getInstance().getDriver().switchTo().window(tabs.get(1));
		waitForElementVisibility(profileButton, "Profile Button");
	}

	/**
	 * 
	 */
	public void navigateToShopForCustomer() {
		ListenersImplementation.test.get().log(Status.INFO, "Navigating to Shop For Customer");
		click_custom(shopForCustomer_button, "Shop For Customer");
		checkPageIsReady();
		DriverFactory.getInstance().getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

	}

	public void navigateToCSR() {
		ListenersImplementation.test.get().log(Status.INFO, "Navigating to CSR.");
		DriverFactory.getInstance().getDriver().close();
		DriverFactory.getInstance().getDriver().switchTo().window(tabs.get(0));
	}

	public void verifyColesPlus_Cancelledsubscription() {
		ListenersImplementation.test.get().log(Status.INFO,
				"Sign up for Coles Plus button should be present for cancelled subscription.");
		click_custom(colesPlusSubscription_button, "Coles Plu subscription button");
		waitForElementVisibility(signUp_button, "SignUp button");
		ListenersImplementation.test.get().log(Status.PASS, "Sign up for Coles Plus button is present on screen.");

	}

	public void verifyCustomerProfile() throws InterruptedException {
		Thread.sleep(10000);
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying the name of the customer in coles profile loaded by CSR");
		String name = getText_custom(profileButton, "Profile button");
		//assertEquals_custom(name.toLowerCase(),
			//	AddCustomerPage.FIRSTNAME.toLowerCase() + " " + AddCustomerPage.LASTNAME.toLowerCase(),
				//"Name Of the Customer");
	}

	public String getTrollyAmount() {

		ListenersImplementation.test.get().log(Status.INFO, "Getting the trolly amount");
		String getAmount = getText_custom(trollyAmount, "Trolly amount");
		getAmount = getAmount.replace("$", "");

		if (Double.parseDouble(getAmount) < 50.00) {
			ListenersImplementation.test.get().log(Status.INFO,
					"Trolly total is less than $50, so we need to add the items in the trolly");
		} else {
			ListenersImplementation.test.get().log(Status.INFO,
					"Trolly total is more than $50, we can proceed to select the delivery slots");
		}

		return getAmount;
	}

	public void addItemsInTrolly(String items) throws InterruptedException {
		click_custom(openTrolly, "Open trully");
		checkPageIsReady();
		Thread.sleep(2000);
		boolean flag = isElementPresent(emptyTrollyMessage, "Empty trolly message");
		if (flag) {

			ListenersImplementation.test.get().log(Status.INFO, "Trolly is empty");
			click_custom(openTrolly, "Open trully");
			Thread.sleep(3000);
		}

		else {

			waitForElementVisibility(emptyTrollyButton, "Remove all button").click();
			waitForElementVisibility(removeEveryThingButton, "Remove everything button");
			click_custom(removeEveryThingButton, "Remove everything");
			waitForTextPresent(emptyTrollyMessage, "Your trolley is empty");
		}

		String productsToSearch = items;
		String[] itemToSearch = productsToSearch.split(",");
		String productType = null;
		for (int i = 0; i < itemToSearch.length; i++) {

			sendKeys_custom(searchItemsTextbox, "Search item textbox", itemToSearch[i]);
			click_custom(searchItemButton, "Search items");
			waitForElementVisibility(clearSearch, "Clear search link");

			productType = getProductType();
			ListenersImplementation.test.get().log(Status.INFO, "Searched item is of " + productType + "");

			if (productType.equals("Temporarily unavailable Product")) {
				continue;
			} else if (productType.equals("Tobacco Product")) {
				System.out.println("========> " + productType);
			}

			else if (productType.equals("Liquor Product")) {
				System.out.println("========> " + productType);
				continue;
			}

			else {
				addnormalProduct(itemToSearch[i]);
				break;
			}
		}

	}

	public String getProductType() {

		String producttype = null;

		ListenersImplementation.test.get().log(Status.INFO, "Getting the type of product");

		if (isElementPresent(firstItemTemporarilyUnavailable, "Product Temporarily unavailable")) {

			return producttype = "Temporarily unavailable Product";
		}

		else if (isElementPresent(liquorWarning, "Liquor warning content")) {

			return producttype = "Liquor Product";
		}

		else if (isElementPresent(tobaccoWarningContent, "Tobacco Warning content")) {

			return producttype = "Tobacco Product";
		}

		else {
			if (isElementPresent(addItemForFirstProduct, "Available item")) {
				return producttype = "Normal available product";
			}
		}

		return producttype;
	}

	public void addnormalProduct(String item) {
		ListenersImplementation.test.get().log(Status.INFO, "Adding the " + item + "");
		for (int j = 0; j < 10; j++) {

			if (j == 0) {
				click_custom(addItemForFirstProduct, "Add item");
			}

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 trollyTotal = getTrollyAmount();
			if (Double.parseDouble(trollyTotal) < 50.0) {
				click_custom(addItemForFirstProduct, "Add item");
				waitForElementVisibility(addProductsPopup, "Add Product Popup");
				By listItems = By.xpath("//ul[@role='presentation']//li");
				List<WebElement> ele = DriverFactory.getInstance().getDriver().findElements(listItems);
				String items = String.valueOf(((j+1) * 10) / 2);
				if (ele.size() > 0) {

					ele.get(ele.size() - 1).click();
					waitForElementVisibility(enterQuantity, "Enter Quantity");
					sendKeys_custom(enterQuantity, "Enter quantity", items);
					click_custom(updateBtn, "Update Button");
					continue;
				} else {
					ListenersImplementation.test.get().log(Status.INFO, "Can not add items");
					break;
				}

			} else {
				break;
			}
		}
	}
	
	public void navigateToDSSPage(String deliveryType){
		
		ListenersImplementation.test.get().log(Status.INFO,"Navigating to DSS Page to select the delivery slots");
		click_custom(openTrolly, "Expanding trolly");
		click_custom(proceedToCheckOutButton, "Proceed to checkout button");
		waitForTextPresent(selectDeliveryTimeMessage, "To check out, you need to choose a delivery time.");
		click_custom(viewDeliveryTimeButton, "View Delivery Option button");
		if(deliveryType.equalsIgnoreCase("HD")){
			waitForElementVisibility(selectHDAddress, "Select HD Address");
			click_custom(selectHDAddress, "Select HD address");
		}
		
		else if(deliveryType.equalsIgnoreCase("CC")){
			waitForElementVisibility(selectFirstCCAdress, "Select CC Address");
			click_custom(selectFirstCCAdress, "Select CC address");
		}
		
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
	}
}
