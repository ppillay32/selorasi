package com.orasi.utils;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;


public class TestTestEnvironment extends TestEnvironment {
	private String application = "application";
	private String browserUnderTest = "html";
	private String browserVersion = "1";
	private String operatingSystem = "windows";
	private String runLocation = "local";
	private String testingEnvironment = "stage";
	private String testingName = "TestEnvironment";
	private String pageURL = "http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/element.html";
	
	@BeforeTest
	public void setup() {
		setReportToMustard(false);
	}
	
	@Features("Utilities")
	@Stories("TestEnvironment")
	@Title("testEnviroment")
	@Test(groups = "regression")
	public void testEnviroment() {
		TestEnvironment te = new TestEnvironment();
		Assert.assertNotNull(te);
	}
	
	@Features("Utilities")
	@Stories("TestEnvironment")
	@Title("testEnviromentWithStringConstructor")
	@Test(groups = "regression")
	public void testEnviromentWithStringConstructor() {
		TestEnvironment te = new TestEnvironment(application, browserUnderTest, browserVersion, operatingSystem,
				runLocation, testingEnvironment);
		Assert.assertNotNull(te);
	}

	@Features("Utilities")
	@Stories("TestEnvironment")
	@Title("applicationUnderTest")
	@Test(groups = "regression")
	public void applicationUnderTest() {
		TestEnvironment te = new TestEnvironment();
		te.setApplicationUnderTest(application);
		Assert.assertTrue(te.getApplicationUnderTest().equals(application));
	}

	@Features("Utilities")
	@Stories("TestEnvironment")
	@Title("browserUnderTest")
	@Test(groups = "regression")
	public void browserUnderTest() {
		TestEnvironment te = new TestEnvironment();
		te.setBrowserUnderTest(browserUnderTest);
		Assert.assertTrue(te.getBrowserUnderTest().equals(browserUnderTest));
	}

	@Features("Utilities")
	@Stories("TestEnvironment")
	@Title("browserVersion")
	@Test(groups = "regression")
	public void browserVersion() {
		TestEnvironment te = new TestEnvironment();
		te.setBrowserVersion(browserVersion);
		Assert.assertTrue(te.getBrowserVersion().equals(browserVersion));
	}

	@Features("Utilities")
	@Stories("TestEnvironment")
	@Title("operatingSystem")
	@Test(groups = "regression")
	public void operatingSystem() {
		TestEnvironment te = new TestEnvironment();
		te.setOperatingSystem(operatingSystem);
		Assert.assertTrue(te.getOperatingSystem().equals(operatingSystem));
	}

	@Features("Utilities")
	@Stories("TestEnvironment")
	@Title("runLocation")
	@Test(groups = "regression")
	public void runLocation() {
		TestEnvironment te = new TestEnvironment();
		te.setRunLocation(runLocation);
		Assert.assertTrue(te.getRunLocation().equals(runLocation));
	}

	@Features("Utilities")
	@Stories("TestEnvironment")
	@Title("testEnvironment")
	@Test(groups = "regression")
	public void testEnvironment() {
		TestEnvironment te = new TestEnvironment();
		te.setTestEnvironment(testingEnvironment);
		Assert.assertTrue(te.getTestEnvironment().equals(testingEnvironment));
	}

	@Features("Utilities")
	@Stories("TestEnvironment")
	@Title("testName")
	@Test(groups = "regression")
	public void testName() {
		TestEnvironment te = new TestEnvironment();
		te.setTestName(testingName);
		Assert.assertTrue(te.getTestName().equals(testingName));
	}

	@Features("Utilities")
	@Stories("TestEnvironment")
	@Title("pageURL")
	@Test(groups = "regression")
	public void pageURL() {
		TestEnvironment te = new TestEnvironment();
		te.setPageURL(pageURL);
		Assert.assertTrue(te.getPageURL().equals(pageURL));
	}

	@Features("Utilities")
	@Stories("TestEnvironment")
	@Title("testStart")
	@Test(groups = "regression")
	public void testTestStart() {
		TestEnvironment te = new TestEnvironment(application, browserUnderTest, browserVersion, operatingSystem,
				runLocation, testingEnvironment);
		te.setPageURL(pageURL);
		te.testStart(testingName);
		Assert.assertTrue(te.getDriver().getTitle().equals("Unit test site"));
		

	}


}