/**
 * 
 */
package com.csr.testsuites;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.csr.base.DriverFactory;
import com.csr.base.TestBase;
import com.csr.pages.BasePage;
import com.csr.pages.LoginPage;
import com.csr.utils.DataProviderUtils;
import com.csr.utils.PropertiesOperations;

import io.qameta.allure.Description;
import io.qameta.allure.Story;

/**
 * @author akaushi3
 *
 */
public class LoginTestSuite extends TestBase {

	BasePage basepage = new BasePage();
	LoginPage loginpage = new LoginPage();

	@DataProvider
	public Object[][] getData() {

		Object[][] testData = DataProviderUtils.getSheetData(DataProviderUtils.LoginTestData);

		return testData;
	}

	@Test(dataProvider = "getData", priority = 1, description = "Verifying the Validation messages when CSR provides invalid credentials")
	@Description("Verify validation messages for invalid inputs")
	@Story("CE-5225 - Login to CSR")
	public void verifyValidationMessages(String username, String password, String errorMsg) {

		// System.out.println(username +" "+password+" "+ errorMsg);
		basepage.navigateToLogin();
		loginpage.login_invlaidCredintials(username, password);
		loginpage.verifyLoginValidation_messages(errorMsg);
		basepage.navigateToLogin();
	}

	@Test(priority = 2, description = "Successful Login To CSR Application")
	@Description("Verify Successful login to CSR")
	@Story("CE-5225 - Login to CSR")
	public void loginTest() {

		//refresh();
		basepage.navigateToLogin();
		loginpage.doLogin(PropertiesOperations.getPropertyValueByKey("username"),
				PropertiesOperations.getPropertyValueByKey("password"));
	}
}
