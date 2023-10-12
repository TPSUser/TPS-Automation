package com.ui.common.pages;

import org.openqa.selenium.WebDriver;

import com.ui.common.actions.SafeActions;
import com.ui.common.locators.Locators;

public class HomePage extends SafeActions implements Locators{

	WebDriver driver;
	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public void allowCookies(String isCookieExists) {
		System.out.println("Cookie value is :: "+isCookieExists);
		if(isCookieExists=="true") {
		zoomto();
		safeClick(BTN_ALLOWCOOKIES,5);
		}else {
			System.out.println("@@@@@@@@@@@@@");
		}
//		try {
//		safeClick(BTN_ALLOWCOOKIES,5);
//		}catch(Exception e) {
//			System.out.println("no cookies..");
//		}
	}
	
	public void navigateToLogin() {
		//mouseHover(BTN_MYACCOUNT);
		//safeClick(BTN_MYACC_LOGIN,5);
	}
}
