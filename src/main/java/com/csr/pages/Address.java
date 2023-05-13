/**
 * 
 */
package com.csr.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.utils.DB_Connection;
import com.csr.utils.ListenersImplementation;

/**
 * @author akaushi3
 *
 */
public class Address extends BasePage {

	//static String deliveryAddType = "HOME_DELIVERY";
	public static String deliveryInst = "Testing Instructions";

	By header = By.xpath("//h1[text() = 'Addresses']");
	By addressList = By.xpath("//ul[@class='address-list']//li");
	By addButton = By.xpath("//button[contains(text(),'Add')]");
	By removeButton = By.xpath("//button[contains(text(), 'Remove')]");
	By nickName = By.xpath("//div[@class='container mar-top-25']//input[@name='nickName']");
	By addressLine1 = By.xpath("//div[@class='container mar-top-25']//input[@name='address1']");
	By city = By.xpath("//div[@class='container mar-top-25']//input[@name='city']");
	By country = By.xpath("//div[@class='container mar-top-25']//select[@name='country']");
	By state = By.xpath("//div[@class='container mar-top-25']//select[@id='stateprovince']");
	By postCode = By.xpath("//div[@class='container mar-top-25']//input[@name='zipCode']");
	By geoCode = By.xpath("//div[@class='container mar-top-25']//input[@name='geocode']");
	By getGeoCodebutton = By.xpath("//div[@class='container mar-top-25']//button[contains(text(), 'Get Geo Code')]");
	By updateButton = By.xpath("//button[contains(text(), 'Update')]");
	By submitAddress = By.xpath("//button[contains(text(), 'Submit')]");
	By addNewAddress_label = By.xpath("//h1[text() = 'Add New Address']");
	// --Add New Address Locators
	By firstname_textbox = By.id("fName-input");
	By lastname_textbox = By.id("lName-input");
	By primaryEmail_textbox = By.xpath("//div[@class='content-column-inside']//input[@name='email1']");
	By primaryTelNum_textbox = By.xpath("//div[@class='content-column-inside']//input[@name='phone1']");
	
	By addNewAddressNickName = By.xpath("//div[@class='content-column-inside']//input[@name='nickName']");
	By addNewAddressAddressLine1 = By.xpath("//div[@class='content-column-inside']//input[@name='address1']");
	By addNewAddressCity = By.xpath("//div[@class='content-column-inside']//input[@name='city']");
	By addNewAddressCountry = By.xpath("//div[@class='content-column-inside']//select[@name='country']");
	By addNewAddressState = By.xpath("//div[@class='content-column-inside']//select[@id='stateprovince']");
	By addNewAddressPostCode = By.xpath("//div[@class='content-column-inside']//input[@name='zipCode']");
	By addNewAddressGeoCode = By.xpath("//div[@class='content-column-inside']//input[@id='geocode']");
	By addNewAddressGetGeoCodebutton = By
			.xpath("//div[@class='content-column-inside']//button[contains(text(), 'Get Geo Code')]");
	By deleteConfirmMessage = By.xpath("//div[contains(text(),'Are you sure you want to delete this address?')]");
	By deleteAddress = By.xpath("//button[contains(text(),'Yes')]");
	By shippingInstructions = By.xpath("//form[@name='shippingInstructions']//*[@id='instructions']");

	/**
	 * Get Header
	 * 
	 * @return
	 */
	public String getHeader() {

		return getText_custom(header, "Address Page");
	}

	public void verifyAddressTab() {

		waitForElementVisibility(header, "Address tab header");
	}
	

	/**
	 * Remove Button Status
	 * 
	 * @return
	 */
	public boolean verifyRemoveButtonStatus() {

		ListenersImplementation.test.get().log(Status.INFO, "Getting the state of Remove button");
		return getElementState_custom(removeButton, "Remove Button");
	}

	public boolean verifyAddButtonStatus() {

		ListenersImplementation.test.get().log(Status.INFO, "Getting the state of Add button");
		return getElementState_custom(addButton, "Add Button");
	}

	public boolean verifyNickNameStatus() {

		ListenersImplementation.test.get().log(Status.INFO, "Getting the state of Nickname textbox");
		return getElementState_custom(nickName, "Nick name textbox");
	}

	public boolean verifyAddressLineStatus() {

		ListenersImplementation.test.get().log(Status.INFO, "Getting the state of AddressLine textbox");
		return getElementState_custom(addressLine1, "Address textbox");
	}

	public boolean verifySuburbState() {

		ListenersImplementation.test.get().log(Status.INFO, "Getting the state of Suburb textbox");
		return getElementState_custom(city, "Suburb textbox");
	}

	public boolean verifyCountryState() {
		ListenersImplementation.test.get().log(Status.INFO, "Getting the state of Country textbox");
		return getElementState_custom(country, "Country textbox");
	}

	public boolean verifyStateProvienceStatus() {

		ListenersImplementation.test.get().log(Status.INFO, "Getting the state of State dropdown");
		return getElementState_custom(state, "State dropdown");
	}

	public boolean verifyZipCodeState() {

		ListenersImplementation.test.get().log(Status.INFO, "Getting the state of Zip code textbox");
		return getElementState_custom(postCode, "Zip code textbox");
	}

	public boolean getgeoCodeState() {

		ListenersImplementation.test.get().log(Status.INFO, "Getting the state of Get Geo Code button");
		return getElementState_custom(getGeoCodebutton, "Get Geo Code button");
	}

	public int countOfAddress() {

		List<WebElement> address = DriverFactory.getInstance().getDriver().findElements(addressList);
		return address.size();
	}

	/**
	 * Get BD value
	 * 
	 * @param email
	 * @param query
	 * @return
	 */
	public int getAddressDetails(String email) {

		String query_01 = "select USERS_ID from users where Field1='" + email + "'";

		List<String[]> data = DB_Connection.getDB_Data(query_01);
		String[] arr = data.get(0);

		query_01 = "select Count(*) from address where member_id=" + arr[0] + " and Status = 'P' and IsPrimary = 0";
		List<String[]> data_01 = DB_Connection.getDB_Data(query_01);
		String[] array = data_01.get(0);
		int i = Integer.valueOf(array[0]);

		return i;
	}

	/**
	 * Get Address Details From DataBase
	 * 
	 * @param email
	 * @return
	 */
	public List<String> getAddressAttributes_DB(String email) {

		String query_01 = "select USERS_ID from users where Field1='" + email + "'";

		List<String[]> data = DB_Connection.getDB_Data(query_01);
		String[] arr = data.get(0);

		query_01 = "select NickName, Address1, City, Country, State, Zipcode from address where member_id=" + arr[0]
				+ " and Status = 'P' and IsPrimary = '0' Order By NickName";
		List<String[]> data_01 = DB_Connection.getDB_Data(query_01);
		String[] array = null;
		List<String> addressDetails = new ArrayList<String>();
		for (int i = 0; i < data_01.size(); i++) {
			System.out.println("======> "+data_01.get(i));
			array = data_01.get(i);
			for (int j = 0; j < array.length; j++) {
				addressDetails.add(array[j]);
			}
		}

		return addressDetails;
	}

	/**
	 * Get Address Details From UI
	 * 
	 * @return
	 */
	public List<String> getAddressFromProfile() {

		List<WebElement> address = DriverFactory.getInstance().getDriver().findElements(addressList);
		List<String> addressDetails = new ArrayList<>();

		for (int i = 0; i < address.size(); i++) {

			address.get(i).click();
			checkPageIsReady();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			addressDetails.add(getAttribute_value_custom(nickName, "NickName"));
			addressDetails.add(getAttribute_value_custom(addressLine1, "Address1"));
			addressDetails.add(getAttribute_value_custom(city, "Suburb"));
			String countryName = getSelectedOption_Dropdown(country, "Country");
			if (countryName.equals("Australia")) {
				countryName = "AU";
			}
			addressDetails.add(countryName);
			addressDetails.add(getSelectedOption_Dropdown(state, "State"));
			addressDetails.add(getAttribute_value_custom(postCode, "Zipcode"));
			checkPageIsReady();
		}

		return addressDetails;
	}

	/**
	 * @throws Throwable
	 * 
	 */
	public void addNewAddress(String addressType) throws Throwable {
		ListenersImplementation.test.get().log(Status.INFO, "Adding a new address");
		click_custom(addButton, "Add button");
		waitForElementVisibility(addNewAddress_label, "Address tab label");
		sendKeys_custom(addNewAddressNickName, "NickName", addressType);
		
		sendKeys_custom(firstname_textbox, "First Name", AddCustomerPage.FIRSTNAME);
		sendKeys_custom(lastname_textbox, "Last Name", AddCustomerPage.LASTNAME);
		sendKeys_custom(primaryEmail_textbox, "Email Id", AddCustomerPage.FIRSTNAME + "." + AddCustomerPage.LASTNAME + "@getnada.com");
		sendKeys_custom(primaryTelNum_textbox, "Telephone Number", AddCustomerPage.TELEPHONENUMBER);
		
		sendKeys_custom(addNewAddressAddressLine1, "Address Line", "35 Tea Tree Lane");
		sendKeys_custom(addNewAddressCity, "Suburb", "Burwood");
		selectDropDownByVisibleText_custom(addNewAddressState, "State", "VIC");
		sendKeys_custom(addNewAddressPostCode, "Post Code", "3125");
		click_custom(addNewAddressGetGeoCodebutton, "Geo Code Button");
		Thread.sleep(5000);
		//click_custom(addNewAddressGetGeoCodebutton, "Geo Code Button"); //Browser cache
		sendKeys_custom(By.name("geocode"), "Geo code", "-12.1234,121.1234");
		String zipCode = getAttribute_value_custom(addNewAddressPostCode, "Post Code");
		assertThat(zipCode.isEmpty(), equalTo(false));
		click_custom(submitAddress, "Submit new address");
		checkPageIsReady();
		ListenersImplementation.test.get().log(Status.PASS, "New address has been added successfully");
	}

	/**
	 * Verify the Remove Functionality
	 * 
	 * @throws InterruptedException
	 */
	public void verifyRemoveFunctionality(String addName) throws InterruptedException {

		ListenersImplementation.test.get().log(Status.INFO, "Removing the latest added address");

		click_custom(By.xpath("//li[text()='"+addName+"']"), "HOME DELIVERY Address");
		checkPageIsReady();
		Thread.sleep(4000);
		click_custom(removeButton, "Remove Button");
		checkPageIsReady();
		waitForElementVisibility(deleteConfirmMessage, "Delete address confirmation popup");
		click_custom(deleteAddress, "Delete address");
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying that the HOME DELIVERY address has been removed");
		List<WebElement> addElement = DriverFactory.getInstance().getDriver().findElements(addressList);
		List<String> addresses = new ArrayList<>();
		for (int i = 0; i < addElement.size(); i++) {
			addresses.add(addElement.get(i).getText().trim());
		}

		if (addresses.contains("HOME DELIVERY")) {
			ListenersImplementation.test.get().log(Status.PASS,
					"HOME DELIVERY address type has been removed from address list.");
		}

		else {
			ListenersImplementation.test.get().log(Status.PASS,
					"HOME DELIVERY address type has been removed from address list.");
		}

		// for(int j = 0; j<addresses.size();j++){
		// if(!addresses.get(j).trim().equals("HOME DELIVERY")){
		// ListenersImplementation.test.get().log(Status.PASS,
		// "HOME DELIVERY address type has been removed from address list.");
		// }
		//
		// else{
		// ListenersImplementation.test.get().log(Status.FAIL,
		// "HOME DELIVERY address type is not removed from address list.");
		// }
		// }

	}

	/**
	 * Single Address Remove Functionality
	 */
	public void verifySingleAddress_RemoveFunctionality() {

		int addressCount = countOfAddress();

		if (addressCount == 1) {

			ListenersImplementation.test.get().log(Status.INFO,
					"When customer has only one address than remove button should be disabled");

			boolean flag = getElementState_custom(removeButton, "Remove Button");

			assertThat(flag, equalTo(false));
		}
	}

	public void updateTheAddress() throws InterruptedException {

		int count = countOfAddress();

		Random ran = new Random();
		int i = ran.nextInt(1000);

		if (count >= 1) {

			List<WebElement> address = DriverFactory.getInstance().getDriver().findElements(addressList);
			address.get(0).click();
			checkPageIsReady();
			Thread.sleep(4000);
			sendKeys_custom(addressLine1, "Address Line", i + " Railway Avenue");
			checkPageIsReady();
			String geoCodeValue = getAttribute_value_custom(geoCode, "Geo Code");
			if(geoCodeValue.equals("undefined,undefined") || geoCodeValue.isEmpty()){
				
				ListenersImplementation.test.get().log(Status.FAIL, "Geo code is not available");
				Assert.assertTrue(false);
			}
			Thread.sleep(3000);
			sendKeys_custom(shippingInstructions, "Shipping Instructions", deliveryInst);
			click_custom(updateButton, "Update address");
			checkPageIsReady();
		} else {

			ListenersImplementation.test.get().log(Status.FAIL, "No address is available");
		}
	}
	
	public String getDeliveryInst(){
		
		return getAttribute_value_custom(shippingInstructions, "value");
	}
}
