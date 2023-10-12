package com.base;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import com.dataprovider.ConfigManager;

import io.github.bonigarcia.wdm.WebDriverManager;


/**
 * This class defines all methods required to initialize ChromeDriver
 * So far only one method is written to initialize with default settings
 */
public class ChromeBrowser 
{
	static ConfigManager sys = new ConfigManager();
	private static Logger log = LogManager.getLogger("ChromeBrowser");
	private static WebDriver driver;
	static String fileSeperator = System.getProperty("file.separator");

	/**
	 * 
	 * This method initiates Chrome browser and returns the driver object
	 *
	 * @return , returns the driver object after initiating Chrome browser
	 */
	public static WebDriver init()
	{		
		ChromeOptions option = new ChromeOptions();
		WebDriverManager.chromedriver().setup();
		Map<String, Object> prefs = new HashMap<String, Object>();
		Map<String,Integer> settings = new HashMap<String,Integer>();
		Map<String,Object> profile = new HashMap<String,Object>();
		
		option.addArguments("--disable-blink-features=AutomationControlled");
		
		settings.put("notifications", 1);
		profile.put("managed_default_content_settings", settings);
		
		prefs.put("profile", profile);
		
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled",false);
	
		option.setExperimentalOption("useAutomationExtension", false);
		option.setExperimentalOption("excludeSwitches", new String[]{"enable-automation","--disable-local-storage","disable-popup-blocking"});
		option.setExperimentalOption("prefs", prefs);
		WebDriver driver = new ChromeDriver(option);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	public static WebDriver init1()
	{		
		ChromeOptions option = new ChromeOptions();
		WebDriverManager.chromedriver().setup();
		option.addArguments("--disable-blink-features=AutomationControlled");
		option.addArguments("--disable-extensions");
		option.addArguments("disable-infobars");
		//option.addArguments("profile.password_manager_enabled");
		option.addArguments("--disable-save-password-bubble");
		//option.put("notifications", 1);
		option.setExperimentalOption("useAutomationExtension", false);
		option.setExperimentalOption("excludeSwitches", new String[]{"enable-automation","--disable-local-storage","disable-popup-blocking"});
		WebDriver driver = new ChromeDriver(option);
		return driver;
	}
	
	

	/**
	 * 
	 * This method initiates Chrome browser with default profile and returns the driver object
	 *
	 * @return , returns the driver object after initiating Chrome browser
	 */
	static WebDriver initChromeDriver() 
	{
		log.info("Launching google chrome with new profile..");
		//System.setProperty("webdriver.chrome.driver", getDriverPath());
		Map<String,Integer> settings = new HashMap<String,Integer>();
		Map<String,Object> profile = new HashMap<String,Object>();
		Map<String, Object> prefs = new HashMap<String, Object>();
		
		ChromeOptions options = new ChromeOptions();
		//settings.put(fileSeperator, null)
		settings.put("notifications", 1);
		profile.put("managed_default_content_settings", settings);
		
		profile.put("--disable-blink-features=AutomationControlled",true);
		profile.put("credentials_enable_service", false);
		profile.put("profile.password_manager_enabled",false);
		profile.put("excludeSwitches", new String[]{"enable-automation"});
		profile.put("--disable-extensions", true);
		profile.put("disable-infobars",true);		
		//profile.put("credentials_enable_service", false);
//		profile.put("profile.password_manager_enabled", false);
		prefs.put("profile", profile);
		
		options.addArguments("--disable-extensions");
		options.addArguments("disable-infobars");
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("useAutomationExtension", false);
		options.setExperimentalOption("prefs", prefs);
		System.out.println("===== ");
		
		
	
		WebDriverManager.chromedriver().setup();
		log.info("chrome driver initialized..");
		return new ChromeDriver(options);
	}

	/**
	 * 
	 * This method initiates chrome browser with specified profile
	 *
	 * @param UserDataPath, Need to pass the chrome user directory path
	 * @param ProfileName , Need to the chrome profile name
	 * @return , returns the driver object after initiating Chrome browser with specified profile
	 */
	private static WebDriver initChromeDriver(String UserDataPath, String ProfileName)
	{
		log.info("Launching google chrome with specified profile - "+ ProfileName);
		System.setProperty("webdriver.chrome.driver", getDriverPath());
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", getDownloadLocation());
		prefs.put("download.prompt_for_download", false);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		if(isProfileDirPresent())
		{
			log.info("Running with specified chrome profile");
			options.addArguments("user-data-dir=" + UserDataPath);
			options.addArguments("--profile-directory=" + ProfileName);
			log.info("Init chrome driver with custom profile is completed..");
		}
		else
		{
			log.info("Specified chrome profile does not exists in 'User Data' folder");
			log.info("Hence Chrome Browser is launched with a new profile..");	
		}
		options.setExperimentalOption("prefs", prefs);
		return new ChromeDriver(options);
	}

	/**
	 * 
	 * This method returns the location of chromedriver.exe file
	 *
	 * @return, returns chromedriver.exe file path
	 */
	public static String getDriverPath()
	{
		return System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe";
	}

	/**
	 * Method to retrieve the Chrome 'User Data' path given in Properties file
	 * @return - returns chrome user data path
	 * @throws Exception
	 */
	public static String getUserDataPath()
	{
		return sys.getProperty("ChromeUserDirectoryPath");
	}

	/**
	 * Method to retrieve the Chrome 'User Data' path given in Properties file
	 * @return - returns chrome user data path
	 * @throws Exception
	 */
	public static String getProfileName()
	{
		return sys.getProperty("ChromeProfileFoldername");
	}

	/**
	 * Method to retrieve the Chrome 'User Data' path given in Properties file
	 * @return - returns chrome user data path
	 * @throws Exception
	 */
	public static boolean isUserDataDirPresent()
	{
		String sUserData= getUserDataPath();
		try
		{
			if(!sUserData.isEmpty())
			{
				File UserDataFolder = new File(sUserData);
				return UserDataFolder.exists();
			}
			else
			{
				return false;
			}
		}
		catch(NullPointerException e)
		{
			log.error("folder does not exists"+sUserData);
			//Assert.fail("folder does not exists"+sUserData);
			return false;
		}
	}

	/**
	 * Method to retrieve the Chrome 'User Data' path given in Properties file
	 * @return - returns chrome user data path
	 * @throws Exception
	 */
	public static boolean isProfileDirPresent()
	{
		String profilePath = getUserDataPath()+"/"+getProfileName();
		try
		{
			if(!profilePath.isEmpty())
			{
				File profileFolder = new File(profilePath);
				return profileFolder.exists();
			}
			else
			{
				return false;
			}
		}
		catch(NullPointerException e)
		{
			log.error("Profile does not exists"+getProfileName());
			Assert.fail("Profile does not exists"+getProfileName());
			return false;
		}
	}

	/**
	 * Method to get file download path location
	 * @return - returns file download path
	 * @throws IOException
	 */	
	public static String getDownloadLocation()
	{
		String DownloadPath= System.getProperty("user.dir")+fileSeperator+"Downloaded Files";
		return DownloadPath;
	}
}
