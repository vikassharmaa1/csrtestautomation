/**
 * 
 */
package com.csr.pages;

import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;

import org.openqa.selenium.By;

import com.csr.base.ExtentFactory;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.Queries;

/**
 * @author akaushi3
 *
 */
public class CommentsPage extends BasePage {

	By refresh_button = By.id("btnCommentRefresh");
	By showMore = By.xpath("(//a[text()='Show More'])[1]");
	By getTimeStamp = By.xpath("//div[@class='container mar-top-25']//table//tr[2]//td[2]");
	By getCancelReason = By.xpath("//div[@class='container mar-top-25']//table//tr[2]//td[3]");
	By commentDetail = By.xpath("//div[@class='container mar-top-25']//table//tr[2]//td[4]/span");
	By commentShowMore = By.xpath("//div[@class='container mar-top-25']//table//tr[2]//td[4]/a");
	By commentPopup = By.xpath("//h1[text()='Comment']");
	By okay_button = By.xpath("//a[text()='Ok']");
	By commentText = By.xpath("//*[text()='Comment']/parent::div//following-sibling::div/div[1]");

	/**
	 * Verify Coupon Details
	 * @param orderId
	 * @throws InterruptedException 
	 */
	public void verifyAssignCouponComment(String userID , String couponAssignReason) throws InterruptedException {
		navigateToTab(commentsTab, "Comments Tab");
		List<String> getCouponComments = getDetails_DB(Queries.getCouponComments(userID));
		String getTime = getText_custom(getTimeStamp, "Coupon creation time");
		String reasonCode = getText_custom(getCancelReason, "Cancel reason");
		click_custom(showMore, "Show More Button");
		waitForTextPresent(commentPopup, "Comment");
		Thread.sleep(1000);
		String commentdetailText = getText_custom(commentText, "Comment detail");
		click_custom(okay_button, "Okay button");
		checkPageIsReady();

		if (getCouponComments.size() > 0) {
			assertEquals_custom(getTime, getCouponComments.get(0), "Comment creation time");
			assertEquals_custom(commentdetailText, getCouponComments.get(1), "Comment detail");
			assertEquals_custom(reasonCode, couponAssignReason, "Cancel reason");
			navigateToTab(couponsTab, "Coupons Tab");
		}
		else{
			ListenersImplementation.test.get().log(com.aventstack.extentreports.Status.FAIL,"No data retrived from database");
		}

	}
	
	public void verifyAdjustCouponComment(String userID , String couponAssignReason) throws InterruptedException{
		navigateToTab(commentsTab, "Comments Tab");
		List<String> getCouponComments = getDetails_DB(Queries.getCouponComments(userID));
		String getTime = getText_custom(getTimeStamp, "Coupon creation time");
		String reasonCode = getText_custom(getCancelReason, "Cancel reason");
		click_custom(showMore, "Show More Button");
		waitForTextPresent(commentPopup, "Comment");
		Thread.sleep(1000);
		String commentdetailText = getText_custom(commentText, "Comment detail");
		click_custom(okay_button, "Okay button");
		//checkPageIsReady();
		//click_custom(commentShowMore, "Show More Button");
		//String commentdetailText = getText_custom(commentDetail, "Comment detail");
		//checkPageIsReady();

		
		if (getCouponComments.size() > 0) {
			assertEquals_custom(getTime, getCouponComments.get(0), "Comment creation time");
			if(getCouponComments.get(1).contains("%20")){
				String comment = getCouponComments.get(1);
				comment = comment.replace("%20", " ");
				assertEquals_custom(commentdetailText, comment, "Comment detail");
			}
			else{
				assertEquals_custom(commentdetailText, getCouponComments.get(1), "Comment detail");	
			}
			
			assertEquals_custom(reasonCode, couponAssignReason, "Cancel reason");
			navigateToTab(couponsTab, "Coupons Tab");
		}
		else{
			ListenersImplementation.test.get().log(com.aventstack.extentreports.Status.FAIL,"No data retrived from database");
		}
	}

}
