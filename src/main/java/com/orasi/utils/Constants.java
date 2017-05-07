package com.orasi.utils;

import java.io.File;
import java.util.Calendar;


public class Constants {

	final static public int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    final static public int CURRENT_MONTH = Calendar.getInstance().get(Calendar.MONTH);
    final static public int CURRENT_DAY = Calendar.getInstance().get(Calendar.DATE);
	
    /** Location of the environment URLs properties file */
    final static public String ENVIRONMENT_URL_PATH = "EnvironmentURLs";
   		
    
    /** Location of the user credentials properties file */
    final static public String USER_CREDENTIALS_PATH = "UserCredentials";
    
    final static public String SANDBOX_PATH = "/sandbox/";
    
    /** Location of drivers in project */
    final static public String DRIVERS_PATH_LOCAL = "/drivers/";
    final static public String DRIVERS_PATH_REMOTE = "C:\\Selenium\\WebDrivers\\";
    
    /** Location of tnsnames in project */
    final static public String TNSNAMES_PATH = "/database/";
    		
    /** An alias for File.separator */
    final static public String DIR_SEPARATOR = File.separator;
    
    /** The current path of the project */ 
    final static public String CURRENT_DIR = determineCurrentPath();

    /** The location screenshots are to be stored */
    final static public String SCREENSHOT_FOLDER = CURRENT_DIR + "selenium-reports" + DIR_SEPARATOR + "html" + DIR_SEPARATOR + "screenshots"  ;
    
    /** The global system property line.separator */
    final static public String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    
    /** An alias for the global system property line.separator */
    final static public String NEW_LINE = LINE_SEPARATOR;
    
    /** The default timeout in seconds, should be a generous default time */
    final static public int DEFAULT_GLOBAL_DRIVER_TIMEOUT = 10;

    /** The timeout (seconds) for finding web elements on a page, shouldn't be too long */
    static public int ELEMENT_TIMEOUT = 3;
    
    /** The timeout (seconds) for page/DOM/transitions, should also be a generous */
    static public int PAGE_TIMEOUT = 10;
   
	public static boolean defaultSyncHandler = true;
	public static int millisecondsToPollForElement = 250;

	/**
	 * Set on how to handle element sync failures. True will cause the sync to throw an exception  while false will just have the element sync return a boolean
	 * 
	 * @param syncHandler True/False 
	 * @version 1/14/2016
	 * @author Justin Phlegar
	 */
	public static void setSyncToFailTest(boolean syncHandler) {
		defaultSyncHandler = syncHandler;
	}
	/**
     * Defaults to "./" if there's an exception of any sort.
     * @warning Exceptions are swallowed.
     * @return Constants.DIR_SEPARATOR
     */
    final private static String determineCurrentPath() {
        try {
            return (new File(".").getCanonicalPath()) + Constants.DIR_SEPARATOR; 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "." + Constants.DIR_SEPARATOR;
    }



}

