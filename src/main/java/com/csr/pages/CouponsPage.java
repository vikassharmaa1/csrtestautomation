/**
 * 
 */
package com.csr.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.PropertiesOperations;

/**
 * @author akaushi3
 *
 */
public class CouponsPage extends BasePage {

	public static double couponIssued = 0.0;
	public static double runningBal = 0.0;
	public static double couponRedeemed = 0.0;
	public static double couponAdjusted = 0.0;
	public static String couponAssignReason = null;
	public static String globalCommentReason = null;

	By assign_button = By.id("assignButton");
	By refresh_button = By.id("refreshButton");
	By adjust_buttons = By.id("adjustButton");
	By assignCoupon_form = By.xpath("//form[@name='formAssign']");
	By orderID_textbox = By.xpath("(//input[@id='lastName-input'])[1]");
	By creditCoupon_textbox = By.id("txtRequestedCredit");
	By couponComments_textbox = By.xpath("(//textarea[@id='txtComments'])[1]");
	By adjustcouponComments_textbox = By.xpath("//*[@id='txtComments']");
	By calculate_button = By.xpath("//button[text()='Confirm']");
	By assignCouponReason_dropdown = By.xpath("(//select[@id='reasonCode'])[1]");
	By apply_button = By.xpath("//button[text()='Apply']");
	By assignCouponcancel_button = By.xpath("//button[text()='Apply']//following-sibling::a[text()='Cancel']");
	By couponvalue = By.xpath("//label[contains(.,'Total Credit Coupon Value')]//parent::div//following-sibling::div");
	By adjustCoupon_form = By.xpath("//div[@id='screen1']//div[@class='main-content-column flyout']");
	By delete_buttton = By.xpath("//button[text()='Delete']");
	By adjustCouponcancel_button = By.xpath("//button[text()='Delete']//following-sibling::button[text()='Cancel']");
	By totalRunningBalance = By.xpath("//td[text()='Total Running Balance']//following-sibling::td");
	By totalCreditCouponsIssued = By.xpath("//td[text()='Total Credit coupon issued']//following-sibling::td");
	By totalCouponRedeemed = By.xpath("//td[text()='Total Coupon Redeemed']//following-sibling::td");
	By totalcouponadjusted = By.xpath("//td[text()='Total Coupon Adjusted']//following-sibling::td");
	By firstCoupon = By.xpath("(//table[@class='fixed_header']//tr[1]//td[1]/input)[1]");
	By adjustCouponReasonDropdown = By.xpath("//*[@id='reasonCode']");
	By adjustCoupon_saveButton = By.xpath("//button[text()='Save']");
	By adjustCoupon_cancelButton = By.xpath("(//button[text()='Cancel'])[1]");	
	By reasonCount_adjustCoupon = By.xpath("(//select[@id='reasonCode'])[2]//option");
	By reasonCount_assignCoupon = By.xpath("(//select[@id='reasonCode'])[1]//option");
	By newButton_GlobalComments = By.xpath("//button[contains(text(),'New')]");
	By Comments_textbox = By.xpath("//textarea[@id='txtComment']");
	By globalCommentReason_dropdown = By.xpath("//select[@id='returnReason']");
	By reasonCount_globalComment = By.xpath("(//select[@id='returnReason'])[1]//option");
	By globalComment_save = By.xpath("//button[text()='Save']");
	By globalComment_back = By.xpath("//a[text()='Back']");
	/**
	 * Get Assign Coupon Window
	 * 
	 * @return
	 */
	public WebElement clickAssignButton() {

		click_custom(assign_button, "Assign Coupon");
		return waitForElementVisibility(assignCoupon_form, "Assign Coupon Form");
	}

	/**
	 * Get The Adjust Coupon Form
	 * 
	 * @return
	 */
	public WebElement clickAdjustButton() {

		click_custom(adjust_buttons, "Adjust Coupons");
		return waitForElementVisibility(adjustCoupon_form, "Adjust Coupon Form");

	}

	/**
	 * Assign A Coupon Againest An OrderID
	 * 
	 * @param orderID
	 * @param valueOfCoupon
	 * @throws Throwable
	 */
	public void assignCoupon(String orderID, String valueOfCoupon) throws Throwable {

		waitForElementVisibility(assignCoupon_form, "Assign Coupon Form");
		sendKeys_custom(orderID_textbox, "Order ID textbox", orderID);
		checkPageIsReady();
		sendKeys_custom(creditCoupon_textbox, "Coupon Value", valueOfCoupon);
		checkPageIsReady();
		click_custom(calculate_button, "Calculate Coupon Value");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		checkPageIsReady();
		String couponVal = getText_custom(couponvalue, "coupon value calculated");
		assertThat(valueOfCoupon, equalTo(couponVal));
		ListenersImplementation.test.get().log(Status.PASS,
				"The coupon value is exactly same which is calculated.");
		couponAssignReason = selectRandomReason(assignCouponReason_dropdown, reasonCount_assignCoupon);
		sendKeys_custom(couponComments_textbox, "Coupon Comment", couponAssignReason);
		click_custom(apply_button, "Apply Coupon");
		Thread.sleep(5000);
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		checkPageIsReady();
	}

	/**
	 * Assign Coupon without OrderID
	 * 
	 * @param valueOfCoupon
	 * @throws Throwable
	 */
	public void assignCoupon(String valueOfCoupon) throws Throwable {

		waitForElementVisibility(assignCoupon_form, "Assign Coupon Form");
		checkPageIsReady();
		sendKeys_custom(creditCoupon_textbox, "Coupon Value", valueOfCoupon);
		click_custom(calculate_button, "Calculate Coupon Value");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		checkPageIsReady();
		String couponVal = getText_custom(couponvalue, "coupon value calculated");
		assertThat(valueOfCoupon, equalTo(couponVal));
		ListenersImplementation.test.get().log(Status.PASS,
				"The coupon value is exactly same which is calculated.");
		couponAssignReason = selectRandomReason(assignCouponReason_dropdown, reasonCount_assignCoupon);
		sendKeys_custom(couponComments_textbox, "Coupon Comment", couponAssignReason);
		click_custom(apply_button, "Apply Coupon");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
		checkPageIsReady();
	}


	/**
	 * Verify Coupon Values
	 */
	public void verifyCouponIssued() {
		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying that the total credit coupon issued is sum of running balance , coupon redeemed and adjusted.");

		runningBal = Double.valueOf(getText_custom(totalRunningBalance, "Total Running Balance"));
		couponIssued = Double.valueOf(getText_custom(totalCreditCouponsIssued, "Total Coupon Issued"));
		couponRedeemed = Double.valueOf(getText_custom(totalCouponRedeemed, "Total Coupon Redeemed"));
		couponAdjusted = Double.valueOf(getText_custom(totalcouponadjusted, "Total Coupon Adjusted"));

		assertThat(couponIssued, equalTo(runningBal + couponAdjusted + couponRedeemed));
		ListenersImplementation.test.get().log(Status.PASS,
				"The couponIsued value is the sum of running balance, coupon redeemed and coupon adjusted");
	}

	/**
	 * @throws InterruptedException 
	 * 
	 */
	public void verifyCouponIssuedBalance_couponadded(Double issuedCoupon) throws InterruptedException {
		clickRefreshButton();
		
		ListenersImplementation.test.get().log(Status.INFO,
				"Coupon Issued should be increased by value of new coupon added by CSA");

		double latestValue_couponIssued = Double.valueOf(getText_custom(totalCreditCouponsIssued, "Coupon Issued"));

		double sumOfCouponAndIssuedCoupon = issuedCoupon
				+ Double.valueOf(PropertiesOperations.getPropertyValueByKey("couponValue"));

		if (latestValue_couponIssued == sumOfCouponAndIssuedCoupon) {
			ListenersImplementation.test.get().log(Status.PASS,
					"The new coupon value is added to the Total Coupon Issued successfully and the value is :"+sumOfCouponAndIssuedCoupon);
		}

		else {
			ListenersImplementation.test.get().log(Status.FAIL,
					"The new coupon value is not added to the Total Coupon Issued and the value is "+sumOfCouponAndIssuedCoupon );
		}
	}

	public void clickRefreshButton() throws InterruptedException {
		Thread.sleep(1000);

		click_custom(refresh_button, "Refresh Button");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
	}

	/**
	 * 
	 */
	public void verifyAvailableCoupanBalance() {

		List<WebElement> couponVal = DriverFactory.getInstance().getDriver()
				.findElements(By.xpath("//table[@class='fixed_header']//tr//td[3]"));
		double sumOfAvailableCoupons = 0.0;
		double couponValue = 0.0;
		for (int i = 0; i < couponVal.size(); i++) {
			String val = couponVal.get(i).getText().trim();
			val = val.replace("$", "").trim();
			couponValue = Double.valueOf(val);
			sumOfAvailableCoupons = couponValue + sumOfAvailableCoupons;
		}
		ListenersImplementation.test.get().log(Status.INFO,
				"All available coupons should be equal to total running balance");

		if (getRunningBalance() == sumOfAvailableCoupons) {

			ListenersImplementation.test.get().log(Status.PASS,
					"Total available coupons are equal to total running balance running balance is " + getRunningBalance()
							+ " and total availavble coupons are " + sumOfAvailableCoupons + "");
		}

		else {
			ListenersImplementation.test.get().log(Status.FAIL,
					"Total available coupons are not equal to total running balance, running balance is " + runningBal
							+ " and total availavble coupons are " + sumOfAvailableCoupons + "  ");
		}
	}

	public void verifyRunningBal_DeleteCoupon() throws Throwable {

		select_checkBox_Custom(firstCoupon, "First available coupon");
		click_custom(delete_buttton, "Delete button");
		checkPageIsReady();
		couponAssignReason = selectRandomReason(adjustCouponReasonDropdown, reasonCount_assignCoupon);
		sendKeys_custom(adjustcouponComments_textbox, "Coupon Comment", couponAssignReason);
		checkPageIsReady();
		click_custom(adjustCoupon_saveButton, "Save Button");
		checkPageIsReady();
		click_custom(adjustCoupon_cancelButton, "Cancel Button");
		
	}

	/**
	 * Get Running Balance
	 * 
	 * @return
	 */
	public double getRunningBalance() {

		return Double.valueOf(getText_custom(totalRunningBalance, "Total Running Balance"));
	}
	
	/**
	 * Get Issued Coupon
	 * @return
	 */
	public double getTotalIssuedCoupon(){
		waitForElementVisibility(totalCreditCouponsIssued, "Coupon Issued");
		return Double.valueOf(getText_custom(totalCreditCouponsIssued, "Coupon Issued"));
	}
	
	public void globalComentsFunctionality() throws Throwable
	{
		click_custom(globalComments_button, "Global Comments Button");
		click_custom(newButton_GlobalComments, "New Button");			
		globalCommentReason = selectRandomReason(globalCommentReason_dropdown, reasonCount_globalComment);
		sendKeys_custom(Comments_textbox, "Global Comment", globalCommentReason);
		checkPageIsReady();
		boolean flag = isElementPresent(globalComment_save, "Global Comment Save Button");
		if (flag) {
			ListenersImplementation.test.get().log(Status.PASS, "Global Comment Save Button can be seen as expected");
		} else {
			ListenersImplementation.test.get().log(Status.FAIL,
					"Unable to see Global Comment Save Button");
		}	
		boolean checkFlag = isElementPresent(globalComment_back, "Global Comment Back Button");
		if (checkFlag) {
			ListenersImplementation.test.get().log(Status.PASS, "Global Comment Back Button can be seen as expected");
		} else {
			ListenersImplementation.test.get().log(Status.FAIL,
					"Unable to see Global Comment Back Button");
		}					
		click_custom(globalComment_save, "Save Comment");
		
		
	}
}
