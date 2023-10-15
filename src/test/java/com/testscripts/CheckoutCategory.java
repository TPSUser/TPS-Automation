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
		System.out.println("data : " +isCookieExists+ " "+category+" "+deliverType+" "+deliveryOption+" "+paymentType );
		if(isCookieExists=="yes") {
			launchApp(env);
		}else {
			launchApp("https://www.theperfumeshop.com/my-account");
		}
		hp.allowCookies(isCookieExists);	
		lp.loginToApp(isCookieExists,userId,pwd);	
		try{
			lp.chooseCategoryToBuy(category);
			lp.selectFirstProductAndAddToCart("wrapyes11S");
			lp.checkoutCartItems();
			lp.selectDeliveryTypeAndProcessPayment(deliverType,deliveryOption,paymentType);
		}catch(Exception e) {
			System.out.println("Execution failed! Proceeding execution with new dataset. Check logs!");
			launchApp("https://www.theperfumeshop.com/my-account");
		}
	}


	@DataProvider(name="E2EPurchaseFlow")
	public Object[][] testData(){
		return new Object[][] 
				{
			{ "yes","Offers", "Deliver","Standard Delivery","Credit Card" },
			{ "no","Men", "Deliver","Standard Delivery","Credit Card" },
			{ "no","Women", "Deliver","Standard Delivery","Credit Card" }
				};

	}

}
