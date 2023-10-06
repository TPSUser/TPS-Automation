package com.ui.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dataprovider.ConfigManager;

public class UtilityMethods {

	public static Logger log = LogManager.getLogger("UtilityMethods");
	static ConfigManager sys = new ConfigManager();


 	/**
 	 * TODO Typecasts the wait time values from String to integer
 	 *
 	 * @param WaitTime
 	 * @return returns value of wait time in integer
 	 * @throws Exception
 	 */
 	public static int getWaitTime(String WaitType)
 	{
 		int iSecondsToWait = 15;
 		String wait;
 		if(WaitType!=null&&!WaitType.equalsIgnoreCase(""))
 		{
 			wait = sys.getProperty(WaitType);
 		}
 		else
 		{
 			log.error("WaitType cannot be empty...defaulting to MEDIUM WAIT");
 			wait = sys.getProperty("WAIT.MEDIUM");
 		}
 		try
 		{
 			iSecondsToWait = Integer.parseInt(wait);
 		}
 		catch(NumberFormatException e)
 		{
 			log.error("Please check the config file. Wait values must be a number...defaulting to 15 seconds");
 		} 		
 		return iSecondsToWait;		
 	}
 	
 	
 	/**
     * TODO To get the entire exception stack trace
     * 
     * @return returns the stack trace
     */
    public static String getStackTrace()
    {
		String trace = "";
		int i;
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for(i=2;i<stackTrace.length;i++)
		{
			trace = trace+"\n"+stackTrace[i];
		}
		return trace;
    }


	
	/**
	 * 
	 * TODO Gives the name of operating system your are currently working on
	 * 
	 * @return returns the OS name
	 */
	public static String getOSName()
	{
		return System.getProperty("os.name");
	
	}	
	



}
