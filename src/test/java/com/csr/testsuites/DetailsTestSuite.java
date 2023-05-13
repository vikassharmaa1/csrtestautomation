/**
 * 
 */
package com.csr.testsuites;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import java.text.ParseException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.pages.AddCustomerPage;
import com.csr.pages.BasePage;
import com.csr.pages.DetailsPage;
import com.csr.utils.ListenersImplementation;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

/**
 * @author akaushi3
 *
 */
public class DetailsTestSuite extends BasePage {

	static String email = null;
	DetailsPage details = new DetailsPage();
	BasePage basepage = new BasePage();

	@Test(priority = 24, description = "Verify that Details tab is highlighted")
	@Description("Verifying the details tab and title")
	@Story("CE-5268 - View Edit Customer Account in CSR")
	public void detailsTabHighlighted_detailsTab() throws InterruptedException {

		refresh();
		checkPageIsReady();
		Thread.sleep(2000);
		details.clickDetailsTab();
		verifyTabHighlighted(Detailstab, "Details tab");
		assertEquals_custom(getHeader("Details Page"), "Details", "Details page header");
		verifyMandatoryButtons();
	}

	@Test(priority = 25, description = "Verify the by default selected payment methods")
	@Description("Verify the by default checked check boxes on details page")
	@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyBydefaultCheckedOptions_detailsTab() {

		refresh();
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		details.flyPay_paymentMethod();
		//details.payPal_paymentMethod(); //Now it is removed From The CSR and SC.
		details.creditCard_PaymentMethod();
		details.substitutionsCheckbox();
		details.unattendedDeliveryStatus();
		details.redemptionLimitCheckboxStatus();
		details.verifycustomerAccountPaymentMethod();
	}

	@Test(priority = 26, description = "Verify that date of birth of the customer")
	@Description("Verify the data of birth of customer")
	@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyDateOfBirth_detailsTab() throws ParseException {

		refresh();
		checkPageIsReady();
		ListenersImplementation.test.get().log(Status.INFO,
				"Date of birth of customer visibile on UI should match with database record");
		String dob = details.getDateOfBirth();
		email = FindCustomerTestSuite.emailAddress;
		String dob_database = details.getUpdatedValue_DB(email, "DateOfBirth");
		assertThat(dob, containsString(dob_database));
		ListenersImplementation.test.get().log(Status.PASS,
				"Date of birth of customer visible on Details tab is matching with database records.");
	}

	@Test(priority = 27, description = "Verifying that delivery and substitions changes are getting updated in database as well")
	@Description("Verify the delivery value updates in database")
	@Story("CE-5268 - View Edit Customer Account in CSR")
	public void verifyDeliveryUpdates_DB_detailsTab() throws InterruptedException {

		refresh();
		checkPageIsReady();
		details.updateDeliveryChoices();
		checkPageIsReady();
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying the updated values in database for unattended delivery , redemption limit.");
		String unattendedDeliveryValue = details.getDeliveryUpdateValues_DB(email, "272");
		assertContains_custom(unattendedDeliveryValue, "1", "Unattended Delivery Vaue");
		//assertThat(unattendedDeliveryValue, containsString("1"));
		String redemptionLimitValue = details.getDeliveryUpdateValues_DB(email, "260");
		assertContains_custom(redemptionLimitValue, "1", "Redemption Limit");
		//assertThat(redemptionLimitValue, containsString("0"));
		ListenersImplementation.test.get().log(Status.PASS,
				"The unattended delivery value is updated in database successfully to " + unattendedDeliveryValue
						+ ".");
		String unattendedDelivery_homeAlone = details.getDeliveryUpdateValues_DB(email, "731");
		ListenersImplementation.test.get().log(Status.PASS,
				"The redemption limit value is updated in database successfully to " + redemptionLimitValue + ".");
		assertContains_custom(unattendedDelivery_homeAlone, "1", "Unattended Delivery When Home Alone");
		//assertThat(unattendedDelivery_homeAlone, containsString("1"));
		ListenersImplementation.test.get().log(Status.PASS,
				"The unattended delivery home alone value is updated in database successfully to "
						+ unattendedDelivery_homeAlone + ".");
		//String bagLessOption = details.getDeliveryUpdateValues_DB(email, "730");
		//assertThat(bagLessOption, equals("1"));
		//ListenersImplementation.test.get().log(Status.PASS,
				//"The Bagless option value is updated in database successfully to " + bagLessOption + ".");
	}	
	
	@Test(priority = 28, description = "Verify minimum Spend values and SAP Number")
	public void verifySAPNumberAndMinimumSpent_detailsTab() throws InterruptedException{
		
		refresh();
		checkPageIsReady();
		details.verifyMinimumSpendLimit();
		details.verifySapNumber_functionality();
		details.updateFlyBysNumber();
	}

}
