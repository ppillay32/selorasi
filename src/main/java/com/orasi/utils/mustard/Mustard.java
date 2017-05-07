package com.orasi.utils.mustard;

import java.util.ResourceBundle;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.testng.ITestResult;

import com.orasi.api.restServices.RestService;
import com.orasi.api.restServices.Headers.HeaderType;
import com.orasi.core.Beta;
import com.orasi.utils.Base64Coder;
import com.orasi.utils.Constants;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.TestReporter;
import com.saucelabs.common.SauceOnDemandAuthentication;


@Beta
public class Mustard {
	private static String mustardURL = "https://api.mustard.orasi.com/results";
    	private static String mustardKey = "facb655bcd4526fb18580c09f38a88d7"; //dev key da8f8779749cfb27bbba1fb9f136c1cf
	protected static ResourceBundle appURLRepository = ResourceBundle.getBundle(Constants.ENVIRONMENT_URL_PATH);
	protected static SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(
			Base64Coder.decodeString(appURLRepository.getString("SAUCELABS_USERNAME")),
			Base64Coder.decodeString(appURLRepository.getString("SAUCELABS_KEY")));
	

    @Beta
	public static void postResultsToMustard(OrasiDriver driver, ITestResult result, String runLocation){
		
		RestService request = new RestService();
		
		String device_id = driver.getDriverCapability().platform().name()  + "_" +driver.getDriverCapability().browserName() + "_" + driver.getDriverCapability().browserVersion().replace(".", "_");
		String test_name = result.getTestClass().getName();
		test_name = test_name.substring(test_name.lastIndexOf(".") + 1, test_name.length())+ "_" +result.getMethod().getMethodName() ;
		String status = "";
		
		if (result.getStatus() == ITestResult.SUCCESS) status = "pass";
		else if (result.getStatus() == ITestResult.SKIP) status = "skip";
		else status = "fail";
		String sauceURL = "";
		MustardResult mustardResult = new MustardResult();
		
		mustardResult.getResult().setProjectId(mustardKey);
		mustardResult.getResult().setResultType("automated");
		mustardResult.getResult().setEnvironmentId(device_id);
		mustardResult.getResult().setTestcaseId(test_name);
		mustardResult.getResult().setStatus(status);
		mustardResult.getResult().setDisplayName(test_name);
		
		
		if(runLocation.toLowerCase().equals("sauce")){
		    sauceURL = "https://saucelabs.com/beta/tests/" + driver.getSessionId().toString();
		    mustardResult.getResult().setLink(sauceURL);
		}
		
		if(status.equals("fail")) {
		    mustardResult.getResult().setComment(result.getThrowable().getMessage());
		    mustardResult.getResult().setStacktrace(ExceptionUtils.getFullStackTrace(result.getThrowable()));
		}
			
		TestReporter.setDebugLevel(TestReporter.TRACE);
		request.sendPostRequest(mustardURL , HeaderType.JSON, RestService.getJsonFromObject(mustardResult));
		
	}
}
