/**
 * 
 */
package com.csr.testsuites;

import java.util.List;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.pages.BasePage;
import com.csr.pages.ColesOnlineComponents;
import com.csr.pages.FindCustomerPage;
import com.csr.pages.SubscriptionPage;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.PropertiesOperations;
import com.csr.utils.Queries;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

/**
 * @author akaushi3
 *
 */
public class SubscriptionTestSuite extends BasePage {

	BasePage basepage = new BasePage();
	SubscriptionPage subscription = new SubscriptionPage();
	FindCustomerPage findCustomer = new FindCustomerPage();
	ColesOnlineComponents coles = new ColesOnlineComponents();

	@Test(priority = 44, description = "Verify the title of the Subscription tab and the tab in the left section should be highlighted")
	//@Description("Verify the tab and title of Subscription tab")
	//@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyTitleAndTab_subscription() {
		refresh();
		checkPageIsReady();
		//--Navigating to subscription tab
		navigateToTab(subscriptionTab, "Subscription");
		//--Verifying that the tab is highlighted
		verifyTabHighlighted(subscriptionTab, "Subscription tab");
		//--Verifying the header of the page
		assertEquals_custom(getHeader("Subscriptions Page"), "Subscriptions", "Subscriptions page header");
		//--Verifying the mandatory buttons on the page
		verifyMandatoryButtons();
	}

	@Test(priority = 45, description = "Verify the status of the active subscriber")
	//@Description("Verify the status of active subscriber")
	//@Story("CE-5268 - View Edit Customer Account in CSR")
	public void avtiveSubscriberVerification_subscription() {
		refresh();
		//--Logging out as a customer
		logOutAs_Customer();
		//--Navigate to find customer page
		findCustomer.navigateToFindCustomerPage();
		//--Searching for customer using email id
		List<String> email = getDetails_DB(Queries.getSuscriptionCustomer("1"));
		findCustomer.enterText_fields(email.get(0), "", "","");
		//--Waiting for search table
		findCustomer.waitForSearchTableToPresent();
		//--Navigating to customer Profile
		findCustomer.navigateToCustomerProfile();
		//--Navigating to subscription tab
		navigateToTab(subscriptionTab, "Subscription");
		ListenersImplementation.test.get().log(Status.INFO,
				"Active subscriber should have cancellation date and reason as null");
		String cancelReason = subscription.subscription_details("Cancellation Reason");
		//--Verifying the cancel reason for active subscriber
		if(cancelReason.isEmpty()){
			ListenersImplementation.test.get().log(Status.PASS, "For active subscriber the cancellation reason should be blank");
		}
		else{
			ListenersImplementation.test.get().log(Status.FAIL, "For active subscriber the cancellation reason should be blank but is is not blank");
		}

		String subscriptionId = subscription.subscription_details("Subscription ID");
		List<String> headers = subscription.getHeaders_subscriptionTable();
		List<String> activeSubscriberDetails = basepage
				.getDetails_DB(Queries.getActiveSubscriberDetails(subscriptionId));
		List<String> subscriptionDetails_CSR = subscription.verifySubscriptionData_activeSubscriber();
		//--Verifying the subscription details from the database
		subscription.assertActiveSubscriberDetails(subscriptionDetails_CSR, activeSubscriberDetails, headers);
		//--Navigating to coles online and login as a customer
		
		//--Commenting below code as flow in Coles Online has changed
		/*coles.navigateToShopForCustomer();
		coles.openProfileSection();
		//--Verifying coles subscription details
		coles.verifyColesPlus_subscription("You are subscribed to Coles Plus");
		coles.navigateToCSR();*/
	}

	@Test(priority = 46, description = "Verify the status of pending cancellation subscriber")
	@Description("Verify the status of pending cancellation subscriber")
	@Story("CE-5268 - View Edit Customer Account in CSR")
	public void pendingCalcellationVerification_subscription() {
		refresh();
		//--Logout as customer
		logOutAs_Customer();
		//--Navigating to Find Customer Page
		findCustomer.navigateToFindCustomerPage();
		//--Finding a customer using email id
		List<String> email_01 = getDetails_DB(Queries.getSuscriptionCustomer("3"));
		
		System.out.println("=====> " +Queries.getSuscriptionCustomer("3"));
		System.out.println("=====> " +email_01.get(0));
		findCustomer.enterText_fields(email_01.get(0), "", "","");
		//--Waiting for search results
		findCustomer.waitForSearchTableToPresent();
		//--Navigating to customer profile
		findCustomer.navigateToCustomerProfile();
		//--Navigating to subscription page
		navigateToTab(subscriptionTab, "Subscription");
		ListenersImplementation.test.get().log(Status.INFO,
				"Active subscriber should have cancellation date and reason as null");
		String subscriptionId = subscription.subscription_details("Subscription ID");
		List<String> headers = subscription.getHeaders_subscriptionTable();
		List<String> activeSubscriberDetails = basepage
				.getDetails_DB(Queries.getCancelledSubscriberDetails(subscriptionId));
		List<String> subscriptionDetails_CSR = subscription.verifySubscriptionData_activeSubscriber();
		//--Verifying the subscription details with DB
		subscription.assertActiveSubscriberDetails(subscriptionDetails_CSR, activeSubscriberDetails, headers);
		//--Navigting to coles online to verify the subscription details in profile section
		
		//--Commenting below code as flow in Coles Online has changed
		/*coles.navigateToShopForCustomer();
		coles.openProfileSection();
		//--Verifying coles subscription details
		coles.verifyColesPlus_subscription("You are subscribed to Coles Plus");
		coles.navigateToCSR();*/
		logOutAs_Customer();
	}

}
