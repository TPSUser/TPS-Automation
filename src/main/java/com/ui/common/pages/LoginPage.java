package com.ui.common.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.dataprovider.ConfigManager;
import com.ui.common.actions.SafeActions;
import com.ui.common.locators.Locators;

public class LoginPage extends SafeActions implements Locators{

	WebDriver driver;
	String url;
	ConfigManager envProps;

	Logger log =LogManager.getLogger("LoginPage");

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		envProps = new ConfigManager("environment");
	}

	/***************************************************************************************************
	 * This method login to the application
	 * @param username
	 * @param password
	 ***************************************************************************************************/
	public void loginToApp(String isCookieExists,String username,String password)  {
		if(isCookieExists=="yes") {
			safeType(INPUT_LOGIN_EMAIL,username);
			safeClick(BTN_LOGIN_ADDEMAIL);;
			safeType(INPUT_LOGIN_PWD, password);
			safeClick(BTN_LOGIN, 5);
			Assert.assertTrue(safeFindElement(TXT_PROFILENAME).isDisplayed());
			log.info("Logged into App");
		}else {
			log.info("User already logged in..");
		}
	}


	/***************************************************************************************************
	 * This method will move mouse to menu category and selects the first sub-menu category from the dropdown list
	 * @param menu
	 * @throws Exception
	 ***************************************************************************************************/
	public void chooseCategoryToBuy(String menu) throws Exception{
		menu=menu.toUpperCase();
		Thread.sleep(5000);
		safeMouseoverByreplaceXpath(MENU_MAIN, "%", menu);	
		//safeClickByreplaceXpath(MENU_MAIN_HEADER, "%", menu);
		safeClick(By.xpath("//nav[@aria-label='main navigation']/ul//a[starts-with(text(),'"+menu+"')]/following-sibling::div//ul/li[2]/a"));
		waitForPageToLoad();
		log.info("Category selected!");
	}


	/***************************************************************************************************
	 * This method selects the First item from the listing page and add to cart
	 * @throws Exception 
	 ***************************************************************************************************/
	public void selectFirstProductAndAddToCart(/* String product */String wrapYesNo) throws Exception {
		safeClick(MENU_CATEGORY_FIRSTITEM,8);
		if(wrapYesNo.equalsIgnoreCase("wrapyes")) {
			safeClick(BTN_WRAP);
			Thread.sleep(3000);
			safeClick(BTN_WRAP_GIFTWRAP);
			Thread.sleep(3000);
			safeClick(BTN_SAVE_WRAP);
			Thread.sleep(3000);
			Assert.assertEquals(safeGetText(TXT_ITSWRAPPED).toUpperCase(), "IT'S WRAPPED");
		}else {
			log.info("Gift wrap not selected!");
		}
		safeClick(BTN_ADDTOCART,8);	
		log.info("Item added to cart!");
		waitForPageToLoad();
	}


	/***************************************************************************************************
	 * This method secure checkout the product from minicart and continue to checkout
	 * @throws Exception
	 ***************************************************************************************************/
	public void checkoutCartItems() throws Exception{
		waitUntilClickable(BTN_MINICART, 5);
		safeClick(BTN_MINICART, 5);		
		setImplicitWait(MEDIUMWAIT);
		safeJavaScriptClick(BTN_CHECKOUTCONFIRMATION,5);
		safeClick(BTN_CONTINUETOCHECKOUT);
		log.info("Checked Out Cart items");
	}


	/***************************************************************************************************
	 * This method selects the delivery type and process the payment
	 * @param deliveryType
	 * @param payType
	 * @throws Exception
	 ***************************************************************************************************/
	public void selectDeliveryTypeAndProcessPayment(String deliveryType,String deliveryOption, String payType) throws Exception{
		waitForPageToLoad();
		safeClickByreplaceXpath(SELECT_DELIVERYMETHOD_DYMC, "%", deliveryType.toLowerCase());
		log.info("Selected Delivery Method :: "+deliveryType);
		Thread.sleep(8000);
		safeClickByreplaceXpath(SELECT_DELIVERYOPTION_DYNMC, "%", deliveryOption);
		log.info("Selected Delivery Option :: "+deliveryOption);
		safeClickByreplaceXpath(SELECT_PAYMENTTYPE_DYMC, "%", payType);
		log.info("Payment type selected :: "+payType);
		safeClick(BTN_PROCEEDTOPAYMENT);
		setImplicitWait(MEDIUMWAIT);
		if(payType.equalsIgnoreCase("credit card")) {
			safeSwitchFrame("wp-cl-wp-iframe-iframe");
			addDefaultCardDetails();
			safeSwitcToMainWindow();
		}
	}


	/***************************************************************************************************
	 * This method add default card details
	 * @throws Exception
	 ***************************************************************************************************/
	public void addDefaultCardDetails() throws Exception{
		log.info("Addding card details!");
		safeType(By.id("cardNumber"), "4111111111111111",5);
		safeType(By.id("expiryMonth"), "10",5);
		safeType(By.id("expiryYear"), "30",5);
		safeType(By.id("securityCode"), "454");
		//safeClick(By.xpath("//*[@value='Make Payment']"));
	}
	/***************************************************************************************************
	 * This method login to the application
	 ***************************************************************************************************/
	public void logOut() {
		safeClick(BTN_PROFILE_LOGGED);
		safeClick(BTN_LOGOUT);
		log.info("Logged out!!");
	}

}