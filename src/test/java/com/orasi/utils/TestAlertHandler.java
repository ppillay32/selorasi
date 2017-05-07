package com.orasi.utils;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestAlertHandler extends TestEnvironment {
	@BeforeTest(groups = { "regression", "utils", "dev", "framehandler" })
	@Parameters({ "runLocation", "browserUnderTest", "browserVersion", "operatingSystem", "environment" })
	public void setup(@Optional String runLocation, String browserUnderTest, String browserVersion,
			String operatingSystem, String environment) {
		setApplicationUnderTest("Test Site");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setTestEnvironment(environment);
		setReportToMustard(false);
		if(getBrowserUnderTest().toLowerCase().equals("html") || getBrowserUnderTest().isEmpty() ) throw new SkipException("Test not valid for HTMLUnitDriver");
		if(getBrowserUnderTest().toLowerCase().equals("safari") ||getBrowserUnderTest().toLowerCase().equals("html") || getBrowserUnderTest().isEmpty() ) throw new SkipException("Test not valid for SafariDriver");
		setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/utils/alertHandler.html");
		testStart("TestAlert");
	}

	@AfterTest(groups = { "regression", "utils", "dev" })
	public void close(ITestContext testResults) {
		endTest("TestAlert", testResults);
	}


	@Features("Utilities")
	@Stories("AlertHandler")
	@Title("constructor")
	@Test(groups ={"regression", "interfaces", "textbox"})
	public void constructorWithElement(){
		Assert.assertNotNull((new AlertHandler()));
	}

	@Features("Utilities")
	@Stories("AlertHandler")
	@Title("isAlertPresent")
	@Test(groups = { "regression", "utils", "dev", "alertHandler" })
	public void isAlertPresent() {
		Assert.assertTrue("Alert was not present", AlertHandler.isAlertPresent(driver,1));
	}

	@Features("Utilities")
	@Stories("AlertHandler")
	@Title("handleAllAlerts")
	@Test(groups = { "regression", "utils", "dev", "alertHandler" },
		  dependsOnMethods = "isAlertPresent")
	public void handleAllAlerts() {
		AlertHandler.handleAllAlerts(driver,1);
		Assert.assertFalse("Alert was not handled", AlertHandler.isAlertPresent(driver,1));
	}

	@Features("Utilities")
	@Stories("AlertHandler")
	@Title("handleAlert")
	@Test(groups = { "regression", "utils", "dev", "alertHandler" },
			dependsOnMethods = "handleAllAlerts")
	public void handleAlert() {
		driver.findElement(By.id("button")).click();
		AlertHandler.handleAlert(driver,1);
		Assert.assertFalse("Alert was not handled", AlertHandler.isAlertPresent(driver,1));
	}


}
