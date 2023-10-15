package com.base;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.dataprovider.ConfigManager;
import com.ui.utilities.TimeOuts;
import com.ui.utilities.UtilityMethods;

/**************************************** PURPOSE **********************************

	 - This class contains the code related to Basic setup of TestSuites such as Instantiating Browser,
	 - Launching Browser from selected Configuration, perform Clean Up etc

	USAGE
	 - Inherit this BaseClass for any TestSuite class. You don't have to write any @Beforeclass and @AfterClass
	 - actions in your TestSuite Classes
	 
	 - Example: 
	 --import Com.Base
	 --- public class <TestSuiteClassName> extends BaseSetup
	*/

	public class BaseSetup implements TimeOuts
	{
		private WebDriver driver;
		private boolean isReportFolderCreated = true;
		private Logger log = LogManager.getLogger("BaseClass");	
		ConfigManager sys = new ConfigManager();
		ConfigManager envProps = new ConfigManager("environment");

		
		/**
		 * Getter method for WebDriver
		 * @return driver
		*/
	    public WebDriver getDriver() 
	    {
	        return driver;
	    }

	    /**
	     * 
	     * Setter method for WebDriver
	     * @param driver
	     */
	    public void setDriver(WebDriver driver) 
	    {
	        this.driver = driver;
	    }
	    
	    /**
	     * 
	     * Creates folder structure to store the automation reports
	     * @throws Exception
	     */
	    @BeforeSuite
	    public void beforeSuite() throws Exception
	    {
			if (isReportFolderCreated)
			{			
				//ReportSetup.createFolderStructure();
				isReportFolderCreated = false;
			}
			
	    }
	    
	    /**
	     * Method initialize() is declared as part of @BeforeClass
	     * If BaseClass.java is inherited from any TestSuite class, the initialization will happen automatically
	     * The initialization() process includes read the Browser name parameter from "Config.Properties" file and launch the selected browser and navigate to the given URL
	     * @throws Exception
	     */
	    @Parameters("browserType")
	    @BeforeClass (groups = { "regression" })
		public void initializeBaseSetup(@Optional("chrome") String browserType)
		{
	    	try
	    	{
		    	initiateDriver(browserType);
		    	System.out.println("initiated driver...");
		 		driver.manage().window().maximize();
				setPageLoadTimeOut(SHORTWAIT);	
	    	}
	    	catch (Exception e)
	    	{		    		
	       		log.error(e.getMessage() +"---"+UtilityMethods.getStackTrace());
	    	}
		}



	    /**
	     * Purpose - to initiate driver based on the browser
	     * @return - driver
	     */
		public void initiateDriver(String browserType) 
		{		
			log.info("Browser name present in config file :" + browserType);		   				
			if(sys.getProperty("ModeOfExecution").equalsIgnoreCase("remote"))
			{
				//code for remote exec
			}
			else if(sys.getProperty("ModeOfExecution").equalsIgnoreCase("local"))
			{
				log.info("-----------------STARTED RUNNING SELENIUM TESTS ON LOCAL MACHINE------------------");
				setDriver(browserType);				
			}				
			else
			{
				log.error("Enter valid Execution Type i.e. Linear/Remote");
				log.info("Running tests in Linear Mode");
				log.info("-----------------STARTED RUNNING SELENIUM TESTS ON LOCAL MACHINE------------------");
				setDriver(browserType);	
			}
		}


		/**
		 * 
		 * This method sets the driver object based on browser name. If invalid browser name is passed, by default it'll set forefox browser
		 *
		 * @param browserType , Need to pass the browser type
		 */
		String browerType="";
		private void setDriver(String browserType)
		{
			switch(browserType)
			{
				case "chrome":
					driver = ChromeBrowser.init();
					break;
				case "firefox":
					driver = FirefoxBrowser.init();
					break;
				default:
					log.error("browser : "+browserType+" is invalid, Launching Firefox as browser of choice..");
					driver = ChromeBrowser.init();		
			}
		}
		
		public void launchApp(String env) {
			env = env.toUpperCase();
			switch(env) {
			case "QA":
				driver.get(envProps.getProperty("test_url"));
				log.info("Launched QA");
				break;
			case "PROD":
				driver.get(envProps.getProperty("prod_url"));
				log.info("Launched Prod");
				break;
			default:
				driver.get(env);;		
			}
		}
		
		/**
		 * 
		 * This method sets page load timeout.
		 *
		 * @param timeOut , Need to pass the time in seconds  
		 */
	    public void setPageLoadTimeOut(int timeOut)
	    {
				driver.manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);					
	    }
	    
	    
	    /**
	     * This method since added in "AfterClass" group and when this class is inherited from a TestSuite class, it will be called automatically
	     * @throws Exception
	     */
	    @AfterClass (groups = { "regression" },alwaysRun=true)
		public void CloseBrowser() throws Exception
		{       
	    	if(driver != null)
	    	{
	    		driver.quit();
	    	}
		}

	}

