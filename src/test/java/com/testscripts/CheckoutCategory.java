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

	@BeforeClass
	public void init() {
		envProps = new ConfigManager("environment");
		env = envProps.getProperty("envToLaunch");
		userId = envProps.getProperty("login_user");
		pwd = envProps.getProperty("login_password");
		hp = new HomePage(getDriver());
		lp = new LoginPage(getDriver());
	}

	@Test(dataProvider="E2EPurchaseFlow")
	public void orderItem(String category, String deliverType, String deliveryOption, String paymentType) throws Exception {
		launchApp(env);
		hp.allowCookies();	
		lp.loginToApp(userId,pwd);	
		lp.chooseCategoryToBuy(category);
		lp.selectFirstProductAndAddToCart();
		lp.checkoutCartItems();
		lp.selectDeliveryTypeAndProcessPayment(deliverType,deliveryOption,paymentType);
		lp.logOut();
	}
	
	
	@DataProvider(name="E2EPurchaseFlow")
    public Object[][] testData(){
    return new Object[][] 
    	{
            { "Offers", "Deliver","Standard Delivery","Credit Card" },
//            { "Men", "Standard Delivery","Credit Card" },
//            { "Women", "Standard Delivery","Credit Card" }
        };

    }

}
