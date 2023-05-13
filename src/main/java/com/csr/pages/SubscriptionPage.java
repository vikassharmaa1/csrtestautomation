/**
 * 
 */
package com.csr.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.base.TestBase;
import com.csr.utils.ListenersImplementation;

/**
 * @author akaushi3
 *
 */
public class SubscriptionPage extends TestBase {

	By subscriptionTable = By.xpath("//div[@class='container mar-top-25']//table//tr");

	public LinkedHashMap<String, String> getTableData() {

		List<WebElement> getHeaders = DriverFactory.getInstance().getDriver()
				.findElements(By.xpath("//div[@class='container mar-top-25']//table//tr//th"));
		List<String> headers = new ArrayList<String>();
		for (int i = 0; i < getHeaders.size(); i++) {
			headers.add(getHeaders.get(i).getText().trim());
		}
		LinkedHashMap<String, String> subscriptionData = new LinkedHashMap<>();
		List<WebElement> rows = DriverFactory.getInstance().getDriver().findElements(subscriptionTable);
		List<WebElement> data = null;
		for (int j = 2; j <= rows.size(); j++) {
			data = DriverFactory.getInstance().getDriver()
					.findElements(By.xpath("//div[@class='container mar-top-25']//table//tr[" + j + "]//td"));

			for (int k = 0; k < data.size(); k++) {

				subscriptionData.put(headers.get(k), data.get(k).getText().trim());
			}
		}

		return subscriptionData;
	}

	/**
	 * Get Subscription ID
	 * 
	 * @return
	 */
	public String subscription_details(String key) {

		LinkedHashMap<String, String> map = getTableData();

		ListenersImplementation.test.get().log(Status.INFO, "The value for " + key + " is " + map.get(key));
		return map.get(key);
	}

	/**
	 * get Subscription Table Data
	 * 
	 * @return
	 */
	public List<String> verifySubscriptionData_activeSubscriber() {

		ListenersImplementation.test.get().log(Status.INFO,
				"getting the Subscription table data for an active user");

		List<String> tableData = new ArrayList<>();
		
		for (Map.Entry<String, String> data : getTableData().entrySet()) {

			tableData.add(data.getValue());
		}
		return tableData;

	}
	
	public List<String> getHeaders_subscriptionTable() {

		ListenersImplementation.test.get().log(Status.INFO,
				"getting the Subscription table header");

		List<String> tableData = new ArrayList<>();
		;
		for (Map.Entry<String, String> data : getTableData().entrySet()) {

			tableData.add(data.getKey());
		}
		return tableData;

	}

	public List<String> getColumnDetails(String column) {

		ListenersImplementation.test.get().log(Status.INFO,
				"getting the "+column+" values");

		List<String> tableData = new ArrayList<>();
		;
		for (Map.Entry<String, String> data : getTableData().entrySet()) {

			tableData.add(data.getValue());
		}
		
		for(String str:tableData){
		}
		return tableData;

	}
	
	/**
	 * Verify the subscription table details
	 * @param actualList
	 * @param expectedList
	 * @param keys
	 */
	public void assertActiveSubscriberDetails(List<String> actualList, List<String> expectedList, List<String> keys) {

		ListenersImplementation.test.get().log(Status.INFO,
				"Verifying the subscription details for active suscriber");

		for (int i = 0; i < expectedList.size(); i++) {

			if (actualList.get(i).contains(expectedList.get(i))) {

				ListenersImplementation.test.get().log(Status.PASS,
						"Verification for " + keys.get(i) + " is pass, expected value is: " + expectedList.get(i)
								+ " and actual value is :" + actualList.get(i));
			}
			
			else{
				ListenersImplementation.test.get().log(Status.FAIL,
						"Verification for " + keys.get(i) + " is fail, expected value is: " + expectedList.get(i)
								+ " and actual value is :" + actualList.get(i));
			}
		}

	}

}
