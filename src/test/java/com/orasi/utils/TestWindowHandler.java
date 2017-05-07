package com.orasi.utils;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestWindowHandler extends TestEnvironment{

    @BeforeTest(groups ={"regression", "utils", "dev"})
    @Parameters({ "runLocation", "browserUnderTest", "browserVersion",
	    "operatingSystem", "environment" })
    public void setup(@Optional String runLocation, String browserUnderTest, String browserVersion,
			String operatingSystem, String environment) {
		setApplicationUnderTest("Test Site");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setTestEnvironment(environment);
		
		setPageURL("http://google.com");
		testStart("TestWindowHandler");
    }

    @AfterTest(groups ={"regression","utils", "dev"})
    public void close(ITestContext testResults){
	endTest("TestWindowHandler", testResults);
    }

    @Features("Utilities")
    @Stories("WindowHandler")
    @Title("waitUntilNumberOfWindowsAre")
    @Test(groups={"regression", "smoke"})
    public void waitUntilNumberOfWindowsAre(){
		driver.executeJavaScript("window.open('http://bluesourcestaging.herokuapp.com', 'BLAH', 'height=800,width=800');");
		Assert.assertTrue(WindowHandler.waitUntilNumberOfWindowsAre(driver, 2));
    }
    
    @Features("Utilities")
    @Stories("WindowHandler")
    @Title("waitUntilWindowExistsWithTitle")
    @Test(groups={"regression", "smoke"},
    		dependsOnMethods="waitUntilNumberOfWindowsAre")
    public void waitUntilWindowExistsWithTitle(){
		Assert.assertTrue(WindowHandler.waitUntilWindowExistsWithTitle(driver, "BlueSource"));
    }
    
    @Features("Utilities")
    @Stories("WindowHandler")
    @Title("waitUntilWindowExistsTitleContains")
    @Test(groups={"regression", "smoke"},
    		dependsOnMethods="waitUntilNumberOfWindowsAre")
    public void waitUntilWindowExistsTitleContains(){
			Assert.assertTrue(WindowHandler.waitUntilWindowExistsTitleContains(driver, "Blue"));
    }
    
    @Features("Utilities")
    @Stories("WindowHandler")
    @Title("waitUntilWindowExistsTitleMatches")
    @Test(groups={"regression", "smoke"},
    		dependsOnMethods="waitUntilNumberOfWindowsAre")
    public void waitUntilWindowExistsTitleMatches(){
		Assert.assertTrue(WindowHandler.waitUntilWindowExistsTitleMatches(driver, "(?i:.*source)"));
    }
    
}
