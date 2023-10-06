package com.ui.testng;


import org.testng.Assert;
 
public class CustomAssert
{
	
	public static void fail(String message) 
	{
		 Assert.fail(message+"</br>");
	}
	
    public static void assertTrue(boolean condition) 
    {
    	Assert.assertTrue(condition);
    }
    
    public static void assertTrue(boolean condition, String message) 
    {
    	Assert.assertTrue(condition, message);
    }
    
    public static void assertFalse(boolean condition) 
    {
    	Assert.assertFalse(condition);
    }
    
    public static void assertFalse(boolean condition, String message) 
    {
    	Assert.assertFalse(condition, message);
    }
    
    public static void assertEquals(boolean actual, boolean expected) 
    {
    	Assert.assertEquals(actual, expected);
    }
    
    public static void assertEquals(boolean actual, boolean expected, String message) 
    {
    	Assert.assertEquals(actual, expected, message);
    }
    
    public static void assertEquals(Object actual, Object expected) 
    {
    	Assert.assertEquals(actual, expected);
    }
    
    public static void assertEquals(Object actual, Object expected, String message) 
    {
    	Assert.assertEquals(actual, expected, message);
    }
    
    public static void assertEquals(Object[] actual, Object[] expected) 
    {
    	Assert.assertEquals(actual, expected);
    }
    
    public static void assertEquals(Object[] actual, Object[] expected, String message) 
    {
    	Assert.assertEquals(actual, expected, message);
    }
 
}