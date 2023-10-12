package com.testscripts;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.base.BaseSetup;
import com.dataprovider.ConfigManager;
import com.ui.common.pages.HomePage;
import com.ui.common.pages.LoginPage;

public class CheckoutCategory extends BaseSetup{

	ConfigManager envProps;
	String env, userId, pwd;
	HomePage hp;
	LoginPage lp;
	boolean firstLaunch;

	@BeforeClass
	public void init() {
		envProps = new ConfigManager("environment");
		env = envProps.getProperty("envToLaunch");
		userId = envProps.getProperty("login_user");
		pwd = envProps.getProperty("login_password");
		hp = new HomePage(getDriver());
		lp = new LoginPage(getDriver());
		firstLaunch = true;
	}

	@Test(dataProvider="E2EPurchaseFlow")
	public void orderItem(String isCookieExists,String category, String deliverType, String deliveryOption, String paymentType) throws Exception {
		System.out.println("data : "+category+" "+deliverType+" "+deliveryOption+" "+paymentType );
		launchApp(env);
		hp.allowCookies(isCookieExists);	
		lp.loginToApp(userId,pwd);	
		try{
			lp.chooseCategoryToBuy(category);
			lp.selectFirstProductAndAddToCart();
			lp.checkoutCartItems();
			lp.selectDeliveryTypeAndProcessPayment(deliverType,deliveryOption,paymentType);
			lp.logOut();
		}catch(Exception e) {
			lp.logOut();
			System.out.println("Executing next run with new data. previous run failed. Check logs!");
			launchApp(env);
		}
	}


	@DataProvider(name="E2EPurchaseFlow")
	public Object[][] testData(){
		return new Object[][] 
				{
			{ "true","Offers", "Deliver","Standard Delivery","Credit Card" },
			{ "false","Men", "Deliver","Standard Delivery","Credit Card" },
			{ "false","Women", "Deliver","Standard Delivery","Credit Card" }
				};

	}

}
