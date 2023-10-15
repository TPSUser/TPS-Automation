package com.ui.common.locators;

import org.openqa.selenium.By;

public interface Locators {

	public static final By BTN_ALLOWCOOKIES = By.id("onetrust-accept-btn-handler");
	
	public static final By BTN_MYACCOUNT = By.xpath("//a[@class='header-link']");
	public static final By BTN_MYACC_SIGNUP = By.xpath("//a[contains(text(),'SIGN UP')]");
	public static final By BTN_MYACC_LOGIN = By.xpath("//a[contains(text(),'LOG IN')]");
	public static final By INPUT_LOGIN_EMAIL = By.name("uid");
	public static final By BTN_LOGIN_ADDEMAIL = By.xpath("//h3[contains(text(),'Log in to your account')]/following-sibling::div[@class='step__actions']/*[@classnames='step__submit']/button");
	public static final By LOGIN_EMAILCORRECT = By.xpath("//input[@name='uid' and contains(@class,'ng-valid')]");
	public static final By INPUT_LOGIN_PWD = By.name("password");
	public static final By BTN_LOGIN = By.xpath("//button[@class='account-form__login']");
	
	
	public static final By TXT_PROFILENAME = By.xpath("//span[@class='header-link__text-greeting']");
	public static final String MENU_MAIN = "//nav[@aria-label='main navigation']/ul//a[starts-with(text(),'%')]/parent::li";
	public static final By MENU_MAIN_HEADER = By.xpath("//nav[@aria-label='main navigation']/ul//a[starts-with(text(),'%')]/parent::li");
	public static final By TOOLTIP = By.xpath("//div[text()='Your email address']/../span");
	public static final By TXT_RETURNINGCUST = By.xpath("//section[@class='tab-switcher']//span[text()[normalize-space()='returning customer' or normalize-space()='Sign In']]");
	public static final By MENU_CATEGORY_FIRSTITEM = By.xpath("//div[@class='product-grid__products-list']/e2-product-tile[1]//a[@class='product-list-item__details-wrapper']");
	public static final By BTN_WRAP = By.xpath("//span[contains(text(),'Wrap me')]");
	public static final By BTN_WRAP_GIFTWRAP = By.xpath("//span[text()[normalize-space()='Gift Wrap']]/../following-sibling::label");
	public static final By BTN_SAVE_WRAP = By.xpath("//*[@class='step__submit-wrapper']/button");
	public static final By TXT_ITSWRAPPED = By.xpath("//*[contains(@class,'add-gift-wrap__button--abrand')]");
	
	public static final By BTN_ADDTOCART = By.xpath("//div[contains(@class,'product-add-to-cart__placeholder')]//form//e2core-button//span");
	public static final By BTN_MINICART = By.className("minicart-anchor");
	public static final By BTN_SECURECHECKOUT = By.xpath("//a[text()[normalize-space()='Secure Checkout']]");
	public static final By BTN_CHECKOUTCONFIRMATION = By.className("checkout-button");
	public static final By BTN_CONTINUETOCHECKOUT = By.xpath("//a[text()[normalize-space()='CONTINUE TO CHECKOUT']]");
	
	public static final By SELECT_DELIVERYMETHOD_DYMC = By.xpath("//ul[@class='delivery-methods__list']//div[contains(@class,'group--%')]/p");
	public static final By SELECT_DELIVERYOPTION_DYNMC = By.xpath("//div[@class='delivery-options__name']//div[contains(text(),'%')]");
	public static final By SELECT_PAYMENTTYPE_DYMC = By.xpath("//div[text()='%']/preceding-sibling::input");
	public static final By SELECT_DELIVERYMETHOD_DELIVER = By.xpath("//ul[@class='delivery-methods__list']//div[contains(@class,'group--deliver')]");
	public static final By SELECT_DELIVERYMETHOD_COLLECT = By.xpath("//ul[@class='delivery-methods__list']//div[contains(@class,'group--collect')]");
	public static final By BTN_PROCEEDTOPAYMENT = By.xpath("//button[contains(text(),'Proceed to payment')]");
	
	public static final By BTN_PROFILE_LOGGED = By.xpath("//a[@class='header-link header-link--logged']");
	public static final By BTN_LOGOUT = By.xpath("//span[contains(text(),'Log Out')]");
}
