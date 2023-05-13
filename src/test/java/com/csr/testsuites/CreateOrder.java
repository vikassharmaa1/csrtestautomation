/**
 * 
 */
package com.csr.testsuites;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.csr.base.ExtentFactory;
import com.csr.pages.AddCreditCardPage;
import com.csr.pages.AddCustomerPage;
import com.csr.pages.Address;
import com.csr.pages.BasePage;
import com.csr.pages.ColesOnlineComponents;
import com.csr.pages.DSSPage;
import com.csr.pages.FindCustomerPage;
import com.csr.pages.OrderConfirmationPage;
import com.csr.utils.ListenersImplementation;

/**
 * @author akaushi3
 *
 */
public class CreateOrder extends BasePage {

	FindCustomerPage findCustomer = new FindCustomerPage();
	Address address = new Address();
	ColesOnlineComponents colesOnline = new ColesOnlineComponents();
	DSSPage dss = new DSSPage();
	OrderConfirmationPage orderConfirmation = new OrderConfirmationPage();
	AddCreditCardPage addCC = new AddCreditCardPage();

	String items = "202672P,9770517P,198393P,9760091P,8464796P";

	@Test(priority = 70, description = "Creating a Home Delivery Order From Shop From Customer")
	public void createHDOrder() throws Throwable {

		String windowType = "signed";
		ListenersImplementation.test.get().log(Status.INFO, "Searching a new customer created through CSR");
		refresh();
		findCustomer.navigateToFindCustomerPage();
		// String email = AddCustomerPage.FIRSTNAME.toLowerCase() + "." +
		// AddCustomerPage.LASTNAME.toLowerCase()
		// + "@getnada.com";

		findCustomer.enterText_fields("test_orders@getnada.com", "", "", "");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		sleep(20000);
		findCustomer.waitForSearchTableToPresent();
		findCustomer.navigateToCustomerProfile();
		ListenersImplementation.test.get().log(Status.INFO, "Navigating to address tab");
		navigateToTab(addressTab, "Address tab");
		// address.addNewAddress("HD");
		ListenersImplementation.test.get().log(Status.INFO, "Navigating to Shop For Customer");
		nvigateToShopForCustomer();
		colesOnline.verifyCustomerProfile();
		if (Double.parseDouble(colesOnline.getTrollyAmount()) < 50.00) {
			colesOnline.addItemsInTrolly(items);
		}
		colesOnline.navigateToDSSPage("HD");
		dss.selectHD_deliverySlot(windowType);
		orderConfirmation.verifyMandatoryChecks();
		orderConfirmation.verifyDeliveryPrice();
		orderConfirmation.verifyTrollyPrice();
		assertContains_custom(orderConfirmation.verifyDeliveryInfo().toLowerCase(), windowType.toLowerCase(),
				"Delivery info");
		assertContains_custom(orderConfirmation.getDSSTimeSlot(), DSSPage.hd_time, "Time Slot");
		orderConfirmation.navigateToaddCreditCardPage();
		addCC.addNewCard();
		orderConfirmation.placeOrder();
	}

	@Test(priority = 71, description = "Creating a Click to Collect Order from Shop For Customer")
	public void createCCOrder() throws InterruptedException {
		ListenersImplementation.test.get().log(Status.INFO, "Searching a new customer created through CSR");
		refresh();
		findCustomer.navigateToFindCustomerPage();
		// String email = AddCustomerPage.FIRSTNAME.toLowerCase() + "." +
		// AddCustomerPage.LASTNAME.toLowerCase()
		// + "@getnada.com";

		findCustomer.enterText_fields("test_orders@getnada.com", "", "", "");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		sleep(20000);
		findCustomer.waitForSearchTableToPresent();
		findCustomer.navigateToCustomerProfile();
		ListenersImplementation.test.get().log(Status.INFO, "Navigating to address tab");
		navigateToTab(addressTab, "Address tab");
		// address.addNewAddress("HD");
		ListenersImplementation.test.get().log(Status.INFO, "Navigating to Shop For Customer");
		nvigateToShopForCustomer();
		colesOnline.verifyCustomerProfile();
		if (Double.parseDouble(colesOnline.getTrollyAmount()) < 50.00) {
			colesOnline.addItemsInTrolly(items);
		}
		colesOnline.navigateToDSSPage("CC");
		dss.selectCCWindowSlot();
	}

}
