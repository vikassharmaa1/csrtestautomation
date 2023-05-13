/**
 * 
 */
package com.csr.pages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.csr.base.DriverFactory;
import com.csr.base.ExtentFactory;
import com.csr.utils.ListenersImplementation;
import com.csr.utils.PropertiesOperations;

import freemarker.template.SimpleDate;

/**
 * @author akaushi3
 *
 */
public class DSSPage extends BasePage {

	public static String extracted_price = "";
	public static String day_selector = "";
	public static String day_selector2 = "";
	String date_0 = "";
	public static String hd_date = "";
	public static String hd_time = "";

	By dateSelector = By.id("airline-date-selector");
	By currentDaySelector = By.xpath("//a[@class='date-option is-active']");
	By selectedDay = By.xpath("//a[contains(@class,'activeDaySlot')]");
	By loadingText = By.xpath("//*[contains(text(), 'Loading, please wait')]");

	public void selectHD_deliverySlot(String windowType) {

		boolean flag = false;

		outerloop: for (int i = getCurrentDay(); i <= 7; i++) {

			String dayStr = Integer.toString(i);
			String dayXpath = PropertiesOperations.getXpathValueByKey("selectDay").replace("nvalue", dayStr);
			String monthXpath = PropertiesOperations.getXpathValueByKey("selectedMonth").replace("nvalue", dayStr);
			clickByXpath(dayXpath, "Select Day");
			ListenersImplementation.test.get().log(Status.INFO,
					"Checking that any slots are available on selected day");

			String slotPrice = PropertiesOperations.getXpathValueByKey("getSlotPriceDetails").replace("nvalue", dayStr);
			String slotPriceDetail = getText_custom(By.xpath(slotPrice), "Slot Price Details");
			if (!(slotPriceDetail.contains("$")) && !(slotPriceDetail.contains("Free"))) {

				ListenersImplementation.test.get().log(Status.INFO,
						"No slots are available for the selected day, proceed to select next day");
				continue;
			}

			String day = PropertiesOperations.getXpathValueByKey("getDay").replace("nvalue", dayStr);
			String dss_Day = getText_custom(By.xpath(day), "Current day");

			String slot_selector = PropertiesOperations.getXpathValueByKey("hdSlotList").replace("nvalue", dayStr);
			String soldOutSlot = PropertiesOperations.getXpathValueByKey("hdSoldOutSlot").replace("nvalue", dayStr);
			System.out.println("==============> " + dss_Day);
			// --Loop to select slots available
			for (int k = 1; k <= 4; k++) {

				String kStr = Integer.toString(k);
				String slot_selector_FinalXpath = slot_selector.replace("kvalue", kStr);
				String soldOutSlot_finalXpath = soldOutSlot.replace("kvalue", kStr);

				if (isElementPresent(By.xpath(soldOutSlot_finalXpath), "Sold out slots")) {

					clickByXpath(soldOutSlot_finalXpath, "Sold Out Slot");
					ListenersImplementation.test.get().log(Status.INFO, "Slot sold out, move to next slot");
					continue;
				}

				if (isElementPresent(By.xpath(slot_selector_FinalXpath), "Available slots") == false) {

					ListenersImplementation.test.get().log(Status.INFO,
							"No slots are available , moving to next day");
					continue;
				}

				// Count the available slots
				List<WebElement> getSlotCount = DriverFactory.getInstance().getDriver()
						.findElements(By.xpath(slot_selector_FinalXpath));

				if (getSlotCount.size() == 0) {
					ListenersImplementation.test.get().log(Status.INFO,
							"No slot are available , moving to next section of the day");
					continue;
				}

				// --Looping through the available slots
				String hd_slot_price_1 = "";
				String HD_slot_selector = "";
				String HD_slot_time1 = "";
				String hd_slot_header1 = "";
				for (int j = 1; j <= getSlotCount.size(); j++) {

					String jStr = Integer.toString(j);

					String dssDelivery_Type = PropertiesOperations.getXpathValueByKey("dssDeliveryType")
							.replace("nvalue", dayStr).replace("kvalue", kStr).replace("mvalue", jStr);

					String partner_Delivery = PropertiesOperations.getXpathValueByKey("partnerDelivery")
							.replace("nvalue", dayStr).replace("kvalue", kStr).replace("mvalue", jStr);

					String slot_status = PropertiesOperations.getXpathValueByKey("slotStatus").replace("nvalue", dayStr)
							.replace("kvalue", kStr).replace("mvalue", jStr);

					HD_slot_selector = PropertiesOperations.getXpathValueByKey("hdSlotPickerDynamic")
							.replace("nvalue", dayStr).replace("kvalue", kStr).replace("mvalue", jStr);

					hd_slot_price_1 = PropertiesOperations.getXpathValueByKey("hdSlotPrice").replace("nvalue", dayStr)
							.replace("kvalue", kStr).replace("mvalue", jStr);

					HD_slot_time1 = PropertiesOperations.getXpathValueByKey("hdSlotTime").replace("nvalue", dayStr)
							.replace("kvalue", kStr).replace("mvalue", jStr);

					hd_slot_header1 = PropertiesOperations.getXpathValueByKey("hdSlotHeader").replace("nvalue", dayStr)
							.replace("kvalue", kStr).replace("mvalue", jStr);

					boolean deliveryViaYello = isElementPresent(By.xpath(hd_slot_header1), "Delivery Via Yello");
					String yellowindowAttr = getAttribute_custom(By.xpath(HD_slot_selector), "data-time-slots",
							"Yellow window");
					extracted_price = getText_custom(By.xpath(hd_slot_price_1), "Delivery charge");
					String status = getText_custom(By.xpath(slot_status), "Slot status");

					Boolean isPartnerDelevery = isElementPresent(By.xpath(partner_Delivery), "Partner Delivery");
					if (isPartnerDelevery) {
						ListenersImplementation.test.get().log(Status.INFO,
								"This is a Partner Delivery slot. Skiping this slot");
						continue;
					}

					if (status.contains("Expired") || status.contains("Sold out") || status.contains("Closed")
							|| deliveryViaYello || yellowindowAttr.toLowerCase().contains("yello")) {

						ListenersImplementation.test.get().log(Status.INFO,
								"The selected slot is expired/closed/sold out/yellow delivery slot , moving to next slot");
						continue;
					}

					day_selector = DriverFactory.getInstance().getDriver().findElement(By.xpath(dayXpath)).getText()
							.trim();
					day_selector2 = DriverFactory.getInstance().getDriver().findElement(By.xpath(monthXpath)).getText()
							.trim();
					date_0 = day_selector.concat(" ").concat(day_selector2);
					int year = Calendar.getInstance().get(Calendar.YEAR);
					hd_date = date_0.concat("" + year);
					hd_time = getText_custom(By.xpath(HD_slot_time1), "Slot time");
					String DelType = getText_custom(By.xpath(dssDelivery_Type), "Delivery type");
					DelType = DelType.replaceAll("[^a-zA-Z]", "");
					windowType = windowType.toLowerCase();
					windowType = windowType.replaceAll("\\s+", "");
					System.out.println("==========> " + windowType);

					switch (windowType) {
					case "signed":
						if (DelType.equalsIgnoreCase("")) {
							clickByXpath(HD_slot_selector, "Slot");
							sleep(5000);
							clickByXpath(PropertiesOperations.getXpathValueByKey("delivery_Opt_Signed"),
									"Signed Delivery");
							clickByXpath(PropertiesOperations.getXpathValueByKey("dsspopupContinue_Button"),
									"DSS Popup Continue button");
							flag = true;
						} else {
							continue;
						}
						break;
					case "signaturerequired":
						if (DelType.equalsIgnoreCase("Signed")) {
							clickByXpath(HD_slot_selector, "Slot");
							sleep(5000);
							clickByXpath(PropertiesOperations.getXpathValueByKey("dsspopupContinue_Button"),
									"DSS Popup Continue button");
							flag = true;
						} else {
							continue;
						}
						break;

					case "customerchoiceunattended":
						if (DelType.equalsIgnoreCase("")) {
							clickByXpath(HD_slot_selector, "Slot");
							sleep(5000);
							clickByXpath(PropertiesOperations.getXpathValueByKey("customerChoicewindowUnanntedBtn"),
									"Unattanted Radio button");
							clickByXpath(PropertiesOperations.getXpathValueByKey("dsspopupContinue_Button"),
									"DSS Popup Continue button");
							flag = true;
						} else
							continue;

						break;

					case "unattendedonly":
						if (DelType.equalsIgnoreCase("UNATTENDED")) {
							clickByXpath(HD_slot_selector, "Slot");
							waitForInvisibilityofElement(loaderIcon, "Loader Icon");
							sleep(2000);
							clickByXpath(PropertiesOperations.getXpathValueByKey("dsspopupContinue_Button"),
									"DSS Popup Continue button");
							flag = true;
						} else
							continue;
						break;

					default:
						break;
					}
					if (flag)
						break outerloop;
				}
			}
		}

		if (flag == false) {
			ListenersImplementation.test.get().log(Status.FAIL, "Failed to select a slot");
		}

		waitForInvisibilityofElement(loadingText, "Loading Text");
		sleep(10000);
		waitForTextPresent(By.xpath("//h1"), "Confirm your order");
		System.out.println("=================>@@ " + hd_date + " and " + hd_time);
	}

	/**
	 * @return
	 */
	private int getCurrentDay() {

		int winStart = 0;

		if (isElementPresent(dateSelector, "Date Selector")) {

			String windowId = getAttribute_custom(currentDaySelector, "id", "current day");
			System.out.println("=====> " + windowId);
			winStart = Integer.valueOf(windowId.substring((windowId.lastIndexOf("-") + 1), windowId.length()));
			System.out.println("=====> " + winStart);
		} else {
			String windowId = getAttribute_custom(selectedDay, "id", "current day");
			winStart = Integer.valueOf(windowId.replaceAll("[^0-9]", "").trim());
		}

		return winStart;
	}

	public void selectCCWindowSlot() {

		try {
			boolean stflag = true;
			boolean changeslot = false;
			int win_start = 0;
			wait(10000);

			win_start = getCurrentDay();
			outerloop: for (int i = win_start; i <= 5; i++) {
				String dayStr = Integer.toString(i);
				String dayXpath = PropertiesOperations.getXpathValueByKey("selectDay").replace("nvalue", dayStr);
				String monthXpath = PropertiesOperations.getXpathValueByKey("selectedMonth").replace("nvalue", dayStr);
				clickByXpath(dayXpath, "Select Day");
				ListenersImplementation.test.get().log(Status.INFO,
						"Checking that any slots are available on selected day");
				String slotPrice = PropertiesOperations.getXpathValueByKey("getSlotPriceDetails").replace("nvalue",
						dayStr);
				String slotPriceDetail = getText_custom(By.xpath(slotPrice), "Slot Price Details");
				if (!(slotPriceDetail.contains("$")) && !(slotPriceDetail.contains("Free"))) {

					ListenersImplementation.test.get().log(Status.INFO,
							"No slots are available for the selected day, proceed to select next day");
					continue;
				}
				
				String day = PropertiesOperations.getXpathValueByKey("getDay").replace("nvalue", dayStr);
				String dss_Day = getText_custom(By.xpath(day), "Current day");

				String slot_selector = PropertiesOperations.getXpathValueByKey("ccslotList").replace("nvalue", dayStr);
				String soldOutSlot = PropertiesOperations.getXpathValueByKey("ccSlotsSoldOut").replace("nvalue", dayStr);
				System.out.println("==============> " + dss_Day);
				
				for(int k = 1; k<=3; k++){
					

					String kStr = Integer.toString(k);
					String slot_selector_FinalXpath = slot_selector.replace("kvalue", kStr);
					String soldOutSlot_finalXpath = soldOutSlot.replace("kvalue", kStr);

					if (isElementPresent(By.xpath(soldOutSlot_finalXpath), "Sold out slots")) {

						clickByXpath(soldOutSlot_finalXpath, "Sold Out Slot");
						ListenersImplementation.test.get().log(Status.INFO, "Slot sold out, move to next slot");
						continue;
					}

					if (isElementPresent(By.xpath(slot_selector_FinalXpath), "Available slots") == false) {

						ListenersImplementation.test.get().log(Status.INFO,
								"No slots are available , moving to next day");
						continue;
					}
					
					// Count the available slots
					List<WebElement> getSlotCount = DriverFactory.getInstance().getDriver()
							.findElements(By.xpath(slot_selector_FinalXpath));

					if (getSlotCount.size() == 0) {
						ListenersImplementation.test.get().log(Status.INFO,
								"No slot are available , moving to next section of the day");
						continue;
					}
					
					String cc_slot_selector = "";
					String cc_slot_price_selector = "";
					String cc_slot_already_selected = "";
					String slotStatus = PropertiesOperations.getXpathValueByKey("ccSlotStatus");
					
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getDaySlots(String day) {

		Date date = new Date();
		Calendar c = Calendar.getInstance();
		String dayVal;
		if (day.equals("Today")) {
			c.setTime(date);
			dayVal = new SimpleDateFormat("EEEE").format(date);
			ListenersImplementation.test.get().log(Status.INFO, "Today the day is :" + dayVal);
		} else if (day.equals("Tomorrow")) {
			c.setTime(date);
			c.add(Calendar.DATE, 1);
			dayVal = new SimpleDateFormat("EEEE").format(c.getTime());
			ListenersImplementation.test.get().log(Status.INFO, "Tomorrow the day is :" + dayVal);
		} else {
			dayVal = day;
		}
		return dayVal;
	}

}
