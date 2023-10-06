package com.ui.common.actions;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.dataprovider.ConfigManager;
import com.ui.utilities.TimeOuts;
import com.ui.utilities.UtilityMethods;

public class Sync implements TimeOuts{
	
	ConfigManager app = new ConfigManager("App");
	private WebDriver driver;
	
	public Sync(WebDriver driver)
	{
		this.driver = driver;
	}
	
	/**
	* Sets implicitWait to ZERO. This helps in making explicitWait work...
	* @param driver (WebDriver) The driver object to be used
	* @return void
	* @throws Exception
	*/
	@SuppressWarnings("deprecation")
	public void nullifyImplicitWait()
	{
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
	}


	/**
	* Set driver's implicitlyWait() time according given waitTime.
	* @param driver (WebDriver) The driver object to be used
	* @param waitTimeInSeconds (int) The time in seconds to specify implicit wait
	* @return void
	* @throws Exception
	*/
	public void setImplicitWait(int waitTimeInSeconds)
	{
		driver.manage().timeouts().implicitlyWait(waitTimeInSeconds, TimeUnit.SECONDS);
	}
	
	
	/**
	 * Waits for an element till the timeout expires
	 * @param driver (WebDriver) The driver object to be used to wait and find the element
	 * @param bylocator (By) locator object of the element to be found
	 * @param waitTime (int) The time in seconds to wait until returning a failure
	 * @return - True (Boolean) if element is located within timeout period else false
	 */
    public boolean isElementPresent(By locator, Duration waitTime)
	{    	
    	boolean bFlag = false;	
    	nullifyImplicitWait();
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator)); 			
			if(driver.findElement(locator).isDisplayed()||driver.findElement(locator).isEnabled())
			{
				bFlag = true;
			}
		}		
		catch (Exception e)
		{
			Assert.fail("Element " + locator + " is not displayed"+UtilityMethods.getStackTrace());
		}
		setImplicitWait(IMPLICITWAIT);
		return bFlag;
	}
    
    public int getWaitTime(int[] optionalWaitArray)
	{
		if(optionalWaitArray.length<=0)
		{
			return MEDIUMWAIT;
		}
		else
		{
			return optionalWaitArray[0];
		}
	}
    
    /**
	 * Method -  Waits for an element till the element is clickable
	 * @param driver (WebDriver) The driver object to be used to wait and find the element
	 * @param bylocator (By) locator object of the element to be found
	 * @param waitTime (int) The time in seconds to wait until returning a failure	 
	 * @return - True (Boolean) if element is located and is clickable within timeout period else false
	 * @throws Exception
	 */
	public boolean waitUntilClickable(By locator, int... optionWaitTime)
	{    	
		boolean bFlag = false;
    	nullifyImplicitWait();
		try
		{
			int waitTime =  getWaitTime(optionWaitTime);
			Duration time = intToDuration(waitTime);
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.elementToBeClickable(locator));		 
			if(driver.findElement((locator)).isDisplayed())
			{
				bFlag = true;
			}
		}
		catch (Exception e)
		{		
			Assert.fail("Element " + locator + " was not clickable" +UtilityMethods.getStackTrace());
		}
		setImplicitWait(IMPLICITWAIT);
		return bFlag;
	}
	
	public Duration intToDuration(int optionWaitTime) {
		Duration time = Duration.ofMinutes(optionWaitTime);
		return time;
	}
	
	/**
	* Waits until page is loaded. Default timeout is 250 seconds. Poll time is every 500 milliseconds.
	*
	* @param driver - The driver object to use to perform this element search
	* @return void
	* @throws Exception
	*/
	
	public void waitForPageToLoad() throws Exception 
	{
		try
		{
			int waitTime = 0;
			boolean isPageLoadComplete = false;
			do 
			{
				isPageLoadComplete = ((String)((JavascriptExecutor)driver).executeScript("return document.readyState")).equals("complete");
				Thread.sleep(500);
				waitTime++;
				if(waitTime > 500)
				{
					break;
				}
			} 
			while(!isPageLoadComplete);
			if(!isPageLoadComplete)
			{	

				Assert.fail("unable to load webpage with in default timeout 250 seconds");
			}
		}
		catch(Exception e)
		{		
			Assert.fail("unable to load webpage"+"\n"+e.getMessage());
		}

	}
	
}
