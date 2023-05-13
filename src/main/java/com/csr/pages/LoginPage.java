/**
 * 
 */
package com.csr.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.csr.base.DriverFactory;
import com.csr.base.TestBase;
import com.csr.utils.PropertiesOperations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * @author akaushi3
 *
 */
public class LoginPage extends BasePage {

	By getUserName_textbox = By.id("login-email-input");
	By getPassword_textbox = By.id("login-password-input");
	By getLogin_button = By.xpath("//button[contains(text(),'Log In')]");
	By getLoginErrorMessage = By.xpath("//p[contains(text(),'There was a problem logging you in')]");
	By getTryAgainButton_message = By.xpath("//button[contains(text(),'Try Again')]");
	By validationMsg = By.xpath("//div[contains(@class,'validation-messages')]//ul//li");
	By loginHearderInfo = By.xpath("//p[text()='To log on as CSR, type the User Name and Password.']");
	By waitLoader = By.xpath("//*[contains(text(), 'Logging in...')]");

	public FindCustomerPage doLogin(String username, String password) {

		// waitForTextPresent(loginHearderInfo, "Login in as CSR");
		sendKeys_custom(getUserName_textbox, "User name textbox", username);
		sendKeys_custom(getPassword_textbox, "Password textbox", password);
		click_custom(getLogin_button, "Login button");
		waitForInvisibilityofElement(waitLoader, "Loader icon");
		return new FindCustomerPage();
	}

	public void login_invlaidCredintials(String username, String password) {

		// waitForTextPresent(loginHearderInfo, "Login in as CSR");
		sendKeys_custom(getUserName_textbox, "User name textbox", username);
		sendKeys_custom(getPassword_textbox, "Password textbox", password);
		click_custom(getLogin_button, "Login button");

	}

	public void verifyLoginValidation_messages(String errorMsg) {

		System.out.println("====>" + errorMsg);
		List<WebElement> validations = DriverFactory.getInstance().getDriver().findElements(validationMsg);
		List<String> validationMsg = new ArrayList<String>();
		for (int i = 0; i < validations.size(); i++) {

			validationMsg.add(validations.get(i).getText().trim());
		}

		assertThat("Verifying the validation messages", validationMsg, contains(errorMsg));
	}

	public void loginIntoApplication() {
		navigateToLogin();
		doLogin(PropertiesOperations.getPropertyValueByKey("username"),
				PropertiesOperations.getPropertyValueByKey("password"));
		checkPageIsReady();
		DriverFactory.getInstance().getDriver().get(PropertiesOperations.getPropertyValueByKey("findCustomerPageUrl"));
	}
}