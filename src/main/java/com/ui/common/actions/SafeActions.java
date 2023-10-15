package com.ui.common.actions;

import java.time.Duration;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.ui.utilities.UtilityMethods;

public class SafeActions extends Sync{

	WebDriver driver;

	public SafeActions(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
	}
	
	/**
	 * Method - Safe Method for User Click, waits until the element is loaded and then performs a click action
	 * @param locator
	 * @param waitTime
	 */
	public void safeClick(By locator, int... optionWaitTime)
	{
		try
		{
			Duration time = intToDuration(SHORTWAIT);
			waitUntilClickable(locator, optionWaitTime);
			if(isElementPresent(locator,time))
			{
				WebElement element = driver.findElement(locator);
				element.click();		
			}
			else
			{
				Assert.fail("Unable to click the element " + locator+UtilityMethods.getStackTrace());
			}
		}
		catch(Exception e)
		{	
			Assert.fail("Element " + locator + " was not clickable" +UtilityMethods.getStackTrace());
		}
	}
	
	/**
	 * Method - Safe Method for User Type, waits until the element is loaded and then enters some text
	 * @param locator
	 * @param sText
	 * @param waitTime
	 */
	public void safeType(By locator, String text, int... optionWaitTime)
	{
		try
		{
			Duration time = intToDuration(SHORTWAIT);
			if(isElementPresent(locator, time))
			{
				WebElement element=driver.findElement(locator);
				element.sendKeys(text);
			}
			else
			{
				Assert.fail("Unable to enter " + text + " in field " + locator+UtilityMethods.getStackTrace());
			}
		}
		catch(Exception e)
		{
			Assert.fail("Unable to enter '" + text + "' text in field with locator -"+ locator+UtilityMethods.getStackTrace());
		}
	}
	
	/**
	 * 
	 * scroll method to scroll the page down until expected element is visible	 *
	 * @param locator - locator value by which an element is located
	 * @param waitTime - Time to wait for an element
	 * @return - returns the text value from element
	 */
	public void scrollIntoElementView(By locator, int...optionWaitTime)
	{
		try
		{
			int waitTime =  getWaitTime(optionWaitTime);
			Duration time = intToDuration(waitTime);
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator)); 
			WebElement element = driver.findElement(locator);
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",element);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail("Unable to scroll the page to find "+ locator+ "\n"+UtilityMethods.getStackTrace());			
		}
	}
	
	
	/**
	 * Method - Method to hover on an element based on locator using Actions,it waits until the element is loaded and then hovers on the element
	 * @param locator
	 * @param waitTime
	 * @throws Exception
	 */
	public void mouseHover(By locator,int... optionWaitTime)
	{
		try
		{
			int waitTime =  getWaitTime(optionWaitTime);
			Duration time = intToDuration(waitTime);
			WebDriverWait wait = new WebDriverWait(driver, time);
			if(isElementPresent(locator, time))
			{
				Actions builder = new Actions(driver);
				WebElement HoverElement = driver.findElement(locator);
				builder.moveToElement(HoverElement).build().perform();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
			}
			else
			{	    
				Assert.fail("Element was not visible to hover "+"\n"+ UtilityMethods.getStackTrace());
			}
		}
		catch(Exception e)
		{	
			Assert.fail("Unable to hover the cursor on " + locator + "\n" + UtilityMethods.getStackTrace());
		}
	}
	
	/**
	 * 
	 * TODO JavaScript method for clicking on an element
	 *
	 * @param locator - locator value by which element is recognized
	 * @param waitTime - Time to wait for an element
	 * @return
	 * @throws Exception
	 */
	public void safeJavaScriptClick(By locator, int waitTime) throws Exception
	{
		try
		{
			if(waitUntilClickable(locator, waitTime))
			{
				setImplicitWait(waitTime);
				scrollIntoElementView(locator);
				WebElement element = driver.findElement(locator);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				nullifyImplicitWait();
			}
			else
			{
				Assert.fail("Unable to click on element " + locator +UtilityMethods.getStackTrace());
			}
		}
		catch (Exception e)
		{
			nullifyImplicitWait();
			Assert.fail("Unable to click on element " + locator +UtilityMethods.getStackTrace());
		}
	}
	
	/**
	 * 
	 * This method is used to return locator found
	 *
	 * @param Locator
	 * @return , returns number of locators
	 */
	public WebElement safeFindElement(By Locator)
	{
		WebElement ele = null;
		try
		{
			ele = driver.findElement(Locator);
		}
		catch(Exception e)
		{
			Assert.fail("Some exception occured while retriving Locator count, exception message: "+e.getMessage());
		}
		return ele;
	}
	
	public String safeGetText(By locator,int... optionWaitTime) {
		String text= null;
		try
		{
			Duration time = intToDuration(SHORTWAIT);
			waitUntilClickable(locator, optionWaitTime);
			if(isElementPresent(locator,time))
			{
				WebElement element = driver.findElement(locator);
				text =  element.getText();		
			}
			else
			{
				Assert.fail("Unable to click the element " + locator+UtilityMethods.getStackTrace());
			}
		}
		catch(Exception e)
		{	
			Assert.fail("Element " + locator + " was not clickable" +UtilityMethods.getStackTrace());
		}
		return text;
	}
	
	/**
	 * 
	 * @param xpathlocator
	 * @param toReplace
	 * @param replaceWith
	 * @return
	 */
	public void safeMouseoverByreplaceXpath(String xpathlocator,String toReplace, String replaceWith) {
		String newlocator = null;
		try
		{
			 newlocator = xpathlocator.replace(toReplace, replaceWith);
			 Actions builder = new Actions(driver);
			 WebElement HoverElement = driver.findElement(By.xpath(newlocator));
			 builder.moveToElement(HoverElement).build().perform();
		}
		catch(Exception e)
		{
			Assert.fail("Some exception occured while retriving Locator count, exception message: "+e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param xpathlocator
	 * @param toReplace
	 * @param replaceWith
	 */
	public void safeClickByreplaceXpath(By xpathlocator,String toReplace, String replaceWith) {
		String newlocator = null;
		try
		{
			 newlocator = xpathlocator.toString().replace(toReplace,replaceWith);
			 String[] loc = newlocator.split("By.xpath: ");
			 safeClick(By.xpath(loc[1].trim()), SHORTWAIT);
		}
		catch(Exception e)
		{
			Assert.fail("Some exception occured while retriving Locator count, exception message: "+e.getMessage());
		}
	}
	
	public String getTitle() {
		return driver.getCurrentUrl();
	}
	
	public void zoomto() {
		((JavascriptExecutor)driver).executeScript("document.body.style.transform='scale(0.8)';");
	}
	
	public void safeSwitchFrame(String name) {
		driver.switchTo().frame(name);
	}
	
	public void safeSwitcToMainWindow() {
		driver.switchTo().defaultContent();
	}
	
}
