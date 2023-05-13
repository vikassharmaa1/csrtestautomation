/**
 * 
 */
package com.csr.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.utils.ListenersImplementation;

/**
 * @author akaushi3
 *
 */
public class CompareAccountsPage extends BasePage {

	By compare_button = By.xpath("//button[text()='Compare']");
	By checkBox_list = By.xpath("//div[@class='searchlist']//table//td//input");
	By firstName = By.xpath("//div[@class='searchlist']//td[2]");
	By compareAccounts_page = By.xpath("//p[text()='Compare Customer']");
	By accountStatus = By.xpath("//div[@class='searchlist']//tr[6]//td");
	By buttons_enableDisable = By.xpath("//div[@class='searchlist']//tr[8]//td//button");
	By firstName_compareTables = By.xpath("//div[@class='searchlist']//tr[2]//td");
	By enableDisableForm = By.xpath("//form[@name='customerAccountModal']");
	By comment_textbox = By.id("txtComment");
	By reason_dropdown = By.xpath("//*[@id='returnReason']");
	By saveStatus = By.xpath("//button[text()='Save']");
	By popUp_message = By.xpath("//h1[@class='modal-heading']");

	/**
	 * 
	 * @return
	 */
	public boolean verifyState_compareButton() {
		ListenersImplementation.test.get().log(Status.INFO,
				MarkupHelper.createLabel("Getting the state of Compare accounts button", ExtentColor.BLUE));
		boolean flag = getElementState_custom(compare_button, "Compare accounts button");

		return flag;
	}

	/**
	 * 
	 */
	public void selectRecords(int number) {

		List<WebElement> customers = DriverFactory.getInstance().getDriver().findElements(checkBox_list);
		List<WebElement> fName = DriverFactory.getInstance().getDriver().findElements(firstName);

		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying that on selecting more than 4 customers Compare buttons gets disables");

		if (customers.size() >= 4) {

			for (int i = 0; i < number; i++) {
				String name = fName.get(i).getText().trim();

				if (!customers.get(i).isSelected()) {
					customers.get(i).click();
					checkPageIsReady();
				}
				ListenersImplementation.test.get().log(Status.INFO, "Selected " + name + " customer");
			}

		} else {

			ListenersImplementation.test.get().log(Status.FAIL,
					"Number of records are less than 4 search a new customer.");
		}
	}

	/**
	 * Compare Data
	 * 
	 * @param number
	 */
	public List<String> selectaccounts_forcomparasion(int number) {

		ListenersImplementation.test.get().log(Status.INFO, "Selecting records to compare the records");
		List<WebElement> fName = DriverFactory.getInstance().getDriver().findElements(firstName);
		List<WebElement> customers = DriverFactory.getInstance().getDriver().findElements(checkBox_list);
		List<String> customerDetails = new ArrayList<String>();

		for (int i = 0; i < number; i++) {
			String name = fName.get(i).getText().trim();
			System.out.println("=====> "+name);
			if (!customers.get(i).isSelected()) {
				customers.get(i).click();
				checkPageIsReady();
			}
			ListenersImplementation.test.get().log(Status.INFO, "Selected " + name + " customer");

			for (int j = 0; j < 6; j++) {
				System.out.println("Failed 1");
				String customerData = DriverFactory.getInstance().getDriver()
						.findElement(
								By.xpath("//div[@class='searchlist']//table//tr[" + (i + 2) + "]//td[" + (j + 2) + "]"))
						.getText().trim();
				System.out.println("Failed 2");
				customerDetails.add(customerData);
			}
		}
		return customerDetails;
	}

	/**
	 * 
	 */
	public void click_CompareAccountsButton() {

		click_custom(compare_button, "Compare Accounts");
		
	}

	/**
	 * Get Customer Information From Compare Accounts Table
	 * 
	 * @param number
	 * @return
	 */
	public List<String> getCustomersInformation_compareAccountsScreen(int number) {

		ListenersImplementation.test.get().log(Status.INFO,
				"Getting the customer's information from Compare Accounts table");

		List<WebElement> rowCount = DriverFactory.getInstance().getDriver().findElements(firstName);
		List<String> custInfo = new ArrayList<String>();
		List<WebElement> customerEle = null;

		for (int i = 0; i < number; i++) {

			customerEle = DriverFactory.getInstance().getDriver()
					.findElements(By.xpath("//div[@class='searchlist']//td[" + (i + 2) + "]"));

			System.out.println("=================================================" + customerEle.size());

			for (int j = 0; j < customerEle.size(); j++) {

				if (j == 6) {
					break;
				}
				custInfo.add(customerEle.get(j).getText().trim());
			}
		}

		return custInfo;
	}

	/**
	 * Toggle Customer Account status
	 * @throws InterruptedException 
	 */
	public void toggle_customerAccountStatus() throws InterruptedException {

		List<WebElement> status = DriverFactory.getInstance().getDriver().findElements(accountStatus);
		List<WebElement> buttons = DriverFactory.getInstance().getDriver().findElements(buttons_enableDisable);
		List<WebElement> firstName_customer = DriverFactory.getInstance().getDriver()
				.findElements(firstName_compareTables);
		String buttonStatus = null;

		for (int i = 1; i < status.size(); i++) {

			buttonStatus = status.get(i).getText().trim();
			System.out.println("=========> " + buttonStatus);
			if (buttonStatus.equals("Enabled")) {
				System.out.println();
				ListenersImplementation.test.get().log(Status.INFO,
						"The account for customer " + firstName_customer.get(i).getText().trim()
								+ " is enabled, so CSA is disabling the account.");
				Thread.sleep(10000);
				buttons.get(i-1).click();
				checkPageIsReady();
				waitForElementVisibility(popUp_message, "PopUp");
				try {
					switch_enableDisableAccount("Disabled");
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (buttonStatus.equals("Disabled")) {
				ListenersImplementation.test.get().log(Status.INFO,
						"The account for customer " + firstName_customer.get(i).getText().trim()
								+ " is disabled, so CSA is enabling the account.");
				Thread.sleep(10000);
				buttons.get(i-1).click();
				Thread.sleep(10000);
				waitForElementVisibility(popUp_message, "PopUp");
				try {
					switch_enableDisableAccount("Enabled");
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void switch_enableDisableAccount(String status) throws Throwable {

		if (status.equals("Enabled")) {
			assertThat(getElementState_custom(reason_dropdown, "Reason dropdown"), equalTo(false));			
			sendKeys_custom(comment_textbox, "Comment Textbox", "Enabling for testing reasons");
			checkPageIsReady();
			click_custom(saveStatus, "Save button");
		}

		else if (status.equals("Disabled")) {
			sendKeys_custom(comment_textbox, "Comment TextBox", "Testing Resons");
			waitForElementClickable(reason_dropdown, "Reason Dropdown");			
			Select s = new Select(DriverFactory.getInstance().getDriver().findElement(reason_dropdown));			
			s.selectByIndex(3);
			//selectDropDownByOption_custom(reason_dropdown, "Reason For Disablement", "5");
			checkPageIsReady();
			Thread.sleep(10000);
			click_custom(saveStatus, "Save button");
		}
	}
}
