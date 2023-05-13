/**
 * 
 */
package com.csr.pages;

import org.openqa.selenium.By;

import com.csr.utils.PropertiesOperations;

/**
 * @author akaushi3
 *
 */
public class AddCreditCardPage extends BasePage {

	By mpgsCvv = By.xpath("//*[@id='securityCode']");
	By mpgsExpiryearDropdown = By.xpath("//*[@id='expiryYear']");
	By mpgsExpiryMonthDropdown = By.xpath("//*[@id='expiryMonth']");
	By mpgsCardNumber = By.xpath("//*[@id='number']");
	By mpgsCardNumberIframe = By.xpath("//iframe[@class='gw-proxy-number']");
	By mpgsExpiryMonthIframe = By.xpath("//iframe[@class='gw-proxy-expiryMonth']");
	By mpgsExpiryYearIframe = By.xpath("//iframe[@class='gw-proxy-expiryYear']");
	By mpgsCvvIframe = By.xpath("//iframe[@class='gw-proxy-securityCode']");
	By continueButton = By.id("Continue");
	
	public void addNewCard() throws Throwable{
		
		switchToFrame(mpgsCardNumberIframe);
		sendKeys_custom(mpgsCardNumber, "CC number", PropertiesOperations.getPropertyValueByKey("CerditCardNumber"));
		switchToDefaultContent();
		switchToFrame(mpgsExpiryYearIframe);
		selectDropDownByVisibleText_custom(mpgsExpiryearDropdown, "Expiry Year", PropertiesOperations.getPropertyValueByKey("Year"));
		switchToDefaultContent();
		switchToFrame(mpgsExpiryMonthIframe);
		selectDropDownByVisibleText_custom(mpgsExpiryMonthDropdown, "Year", PropertiesOperations.getPropertyValueByKey("Month"));
		switchToDefaultContent();
		switchToFrame(mpgsCvvIframe);
		sendKeys_custom(mpgsCvv, "CVV", PropertiesOperations.getPropertyValueByKey("CVV"));
		switchToDefaultContent();
		click_custom(continueButton, "Continue button");
		waitForInvisibilityofElement(loaderIcon, "Loader Icon");
	}
	
}
