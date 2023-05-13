/**
 * 
 */
package com.csr.testsuites;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import static org.hamcrest.Matchers.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.pages.Address;
import com.csr.pages.BasePage;
import com.csr.pages.FindCustomerPage;
import com.csr.pages.LoginPage;
import com.csr.utils.ListenersImplementation;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

/**
 * @author akaushi3
 *
 */
public class AddressTestSuite extends BasePage {

	Address address = new Address();
	BasePage basepage = new BasePage();

	@Test(priority = 29, description = "Verifying that the CSA is on Address page and Address tab is highlighted")
	// @Description("Verify the address tab and title")
	// @Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyHeaderAndTab_addressTab() {
		navigateToTab(addressTab, "Address Tab");
		checkPageIsReady();
		address.verifyAddressTab();
		verifyTabHighlighted(addressTab, "Address");
		assertEquals_custom(getHeader("Address Page"), "Addresses", "Address page header");
		verifyMandatoryButtons();
	}

	@Test(priority = 30, description = "Verify the various state of the buttons and textboxes")
	// @Description("Verify the field state of address tab")
	// @Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyFieldsStatus_addressTab() {

		refresh();
		address.verifyAddressTab();
		ListenersImplementation.test.get().log(Status.INFO,
				"Except Add button all other fields should be disabled by default");
		assertThat("Verifying the state of Add Button", address.verifyAddButtonStatus(), equalTo(true));
		assertThat("Verifying the state of Remove Button", address.verifyRemoveButtonStatus(), equalTo(false));
		assertThat("Verifying the state of Nickname textbox", address.verifyNickNameStatus(), equalTo(false));
		assertThat("Verifying the state of Address line textbox", address.verifyAddressLineStatus(), equalTo(false));
		assertThat("Verifying the state of State dropdown", address.verifyStateProvienceStatus(), equalTo(false));
		assertThat("Verifying the state of Country dropdown", address.verifyCountryState(), equalTo(false));
		assertThat("Verifying the state of Suburb textbox", address.verifySuburbState(), equalTo(false));
		assertThat("Verifying the state of Post Code textbox", address.verifyZipCodeState(), equalTo(false));
		assertThat("Verifying the state of Get Geo Code Button", address.getgeoCodeState(), equalTo(false));
	}

	@Test(priority = 31, description = "Verifying the address present in the Customer's profile and in database")
	// @Description("Verify the address present in CSR with database")
	// @Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyAddresses_addressTab() throws InterruptedException {

		refresh();
		checkPageIsReady();
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying the count of address for a customer present in CSR and database.");
		Thread.sleep(1000);
		int addressCount = address.countOfAddress();
		int dbAddressCount = address.getAddressDetails((FindCustomerTestSuite.emailAddress));
		System.out.println(addressCount + "=====" + dbAddressCount);
		assertEquals_custom(String.valueOf(addressCount), String.valueOf(dbAddressCount), "Address count");
		// assertThat(addressCount, equalTo(dbAddressCount));
		ListenersImplementation.test.get().log(Status.PASS,
				"The address present in CSR profile " + addressCount + " and in database " + dbAddressCount + "");
		List<String> uiAddressDetails = address.getAddressFromProfile();
		List<String> dbAddressDetails = address.getAddressAttributes_DB(FindCustomerTestSuite.emailAddress);
		//assertThat(uiAddressDetails, equalTo(dbAddressDetails));
		assertEquals_list(uiAddressDetails, dbAddressDetails, "The adress from UI and Db");
		ListenersImplementation.test.get().log(Status.PASS, "The address present in CSR and in database are matching");
		String deliveryInst = address.getDeliveryInst();
		if (deliveryInst.isEmpty()) {
			ListenersImplementation.test.get().log(Status.PASS,
					"Delivery Inst should be emply as nothing was added while registering the customer");
		} else {
			ListenersImplementation.test.get().log(Status.PASS,
					"Delivery address should be empty but is is " + deliveryInst);
		}
	}

	@Test(priority = 32, description = "Verify the add and removal functionality of addresses")
	// @Description("Verify add and removal address functionality")
	// @Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyAddRemoveAddresses_addressTab() throws Throwable {
		refresh();
		checkPageIsReady();
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying the count of address for a customer present in CSR and database.");
		String addName = RandomStringUtils.randomAlphabetic(6);
		address.addNewAddress(addName);
		int addressCount = address.countOfAddress();
		int dbAddressCount = address.getAddressDetails(FindCustomerTestSuite.emailAddress);
		assertThat(addressCount, equalTo(dbAddressCount));
		ListenersImplementation.test.get().log(Status.PASS,
				"The number of address available in CSR and database are same");
		address.verifyRemoveFunctionality(addName);
		address.verifySingleAddress_RemoveFunctionality();
	}

	@Test(priority = 33, description = "Verifying the update address functionality")
	// @Description("Verify address updation functionality")
	// @Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyUpdateAddress_addressTab() throws InterruptedException {

		refresh();
		checkPageIsReady();
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying that CSA is able to update the address for a customer");
		address.updateTheAddress();
		refresh();
		checkPageIsReady();
		int addressCount = address.countOfAddress();
		int dbAddressCount = address.getAddressDetails(FindCustomerTestSuite.emailAddress);
		assertThat(addressCount, equalTo(dbAddressCount));
		List<String> uiAddressDetails = address.getAddressFromProfile();
		List<String> dbAddressDetails = address.getAddressAttributes_DB(FindCustomerTestSuite.emailAddress);
		assertThat(uiAddressDetails, equalTo(dbAddressDetails));
		String deliveryInstructions = address.getDeliveryInst();
		assertEquals_custom(deliveryInstructions, Address.deliveryInst, "Delivery Instructions");
		ListenersImplementation.test.get().log(Status.PASS, "Address has been successfully updated and verified");
		// --Logout as customer
		logOutAs_Customer();
	}
}
