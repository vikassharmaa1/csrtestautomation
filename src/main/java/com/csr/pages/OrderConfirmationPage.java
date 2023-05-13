/**
 * 
 */
package com.csr.pages;

import org.openqa.selenium.By;

import com.aventstack.extentreports.Status;
import com.csr.base.ExtentFactory;
import com.csr.utils.ListenersImplementation;

/**
 * @author akaushi3
 *
 */
public class OrderConfirmationPage extends BasePage {

	By trollyPrice = By.xpath("//span[@class='cost-value bold']");
	By deliveryPrice = By.xpath("(//span[@class='cost-value'])[1]");
	By creditCardOption = By.xpath("//button[@data-payment-name='MPGSCARD']");
	By viewCoupons = By.id("viewCoupons");
	By addPromoCode = By.id("addPromoCode");
	By deliveryTypeInfo = By.xpath("//div[@class='row dt-service-type-bar']//span[@class='line-feed']");
	By dssSlotDate = By
			.xpath("//*[@id='main-content-inside']//div[@class='row dt-date-time-bar']//span[@class='line-feed']");
	By dssTimeSlot = By.xpath("//span[@class='timeslot']");
	By addNewCreditCard = By
			.xpath("//div[@class='change-link-wrpr']//a[@class='edit-link' and @data-payment-name='MPGSCARD']");
	By payUsingCreditCard = By
			.xpath("//*[@id='main-content-inside']/..//li[@class='payment-credit-card']/div/div/button");
	By checkagreeMent = By.id("acceptAgreement");
	By completeOrder = By.xpath("//button[text()='Complete order']");
	By addComment = By.xpath("//*[@id='comments']");
	By reasonDropdown = By.xpath("//select[@id='reason']");
	By reasonDropdownOptions = By.xpath("//select[@id='reason']//option");
	By submitComment = By.xpath("//*[@aria-label='submit comment']");

	public void verifyDeliveryPrice() {

		String deliveryCharge = getText_custom(deliveryPrice, "Delivery Price");

		assertContains_custom(deliveryCharge, DSSPage.extracted_price, "Delivery Price");
	}

	public void verifyTrollyPrice() {

		String trollyAmount = getText_custom(trollyPrice, "Trolly amount");
		assertContains_custom(trollyAmount, ColesOnlineComponents.trollyTotal, "Trolly amount");
	}

	public void verifyMandatoryChecks() {

		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying payment option, promo code and coupons option should be present on order confirmation page");

		isElementPresent(addPromoCode, "Add Promo Code");
		isElementPresent(viewCoupons, "View Coupons");
		isElementPresent(creditCardOption, "Credit Card Payment Option");
	}

	public String verifyDeliveryInfo() {

		return getText_custom(deliveryTypeInfo, "Delivery Info");
	}

	public String getDSSTimeSlot() {
		
		String text = getText_custom(dssTimeSlot, "Time slot");;
		if(text.contains("AM")){
			text = text.replace("AM", " AM");
		}
		
		if(text.contains("PM")){
			
			text = text.replace("PM", " PM");
		}
		return text;
	}

	public void navigateToaddCreditCardPage() {

		click_custom(payUsingCreditCard, "Pay Using CC");
		click_custom(addNewCreditCard, "Add New CC");
		sleep(5000);
	}
	
	public void placeOrder() throws Throwable{
		
		select_checkBox_Custom(checkagreeMent, "Order agreement");
		click_custom(completeOrder, "Complete Order");
		waitForElementVisibility(addComment, "Add comment");
		String comment = selectRandomReason(reasonDropdown, reasonDropdownOptions);
		sendKeys_custom(addComment, "Comment", comment);
		click_custom(submitComment, "Submit comment");
	}
}
