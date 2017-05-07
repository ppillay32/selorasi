package com.orasi.utils;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestFrameHandler extends TestEnvironment {
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
		setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/utils/frameHandler.html");
		testStart("TestFrame");
	}

	@AfterTest(groups = { "regression", "utils", "dev" })
	public void close(ITestContext testResults) {
		endTest("TestFrame", testResults);
	}

	@Features("Utilities")
	@Stories("FrameHandler")
	@Title("findAndSwitchToFrameFromOutsideFrame")
	@SuppressWarnings("deprecation")
	@Test(groups = { "regression", "utils", "dev", "framehandler" })
	public void findAndSwitchToFrameFromOutsideFrame() {
		FrameHandler.findAndSwitchToFrame(getDriver(), "menu_page");
		Assert.assertTrue("Link was not found in 'menu_page'",
				getDriver().findElement(By.id("googleLink")).isDisplayed());
	}

	@Features("Utilities")
	@Stories("FrameHandler")
	@Title("testGetCurrentFrameName")
	@Test(groups = { "regression", "utils", "dev",
			"framehandler" }, dependsOnMethods = "findAndSwitchToFrameFromOutsideFrame")
	public void testGetCurrentFrameName() {
		Assert.assertTrue("Frame name was not 'menu_page' ",
				FrameHandler.getCurrentFrameName(getDriver()).equals("menu_page"));
	}

	@Features("Utilities")
	@Stories("FrameHandler")
	@Title("testGetDefaultContent")
	@Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testGetCurrentFrameName")
	public void testGetDefaultContent() {
		FrameHandler.moveToDefaultContext(getDriver());
		Assert.assertNull("Failed to move to default Content", FrameHandler.getCurrentFrameName(getDriver()));
	}

	@Features("Utilities")
	@Stories("FrameHandler")
	@Title("testMoveToChildFrameWithName")
	@Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testGetDefaultContent")
	public void testMoveToChildFrameWithName() {
		FrameHandler.moveToChildFrame(getDriver(), "main_page");
		Assert.assertTrue("Button was not found in frame 'main_page'",
				FrameHandler.getCurrentFrameName(getDriver()).equals("main_page"));
	}

	@Features("Utilities")
	@Stories("FrameHandler")
	@Title("testMoveToChildFrameWithLocator")
	@Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testMoveToChildFrameWithName")
	public void testMoveToChildFrameWithLocator() {
		By locator = By.name("main_frame1");
		FrameHandler.moveToChildFrame(getDriver(), locator);
		Assert.assertTrue("Button was not found in frame 'main_frame1'",
				FrameHandler.getCurrentFrameName(getDriver()).equals("main_frame1"));
	}

	@Features("Utilities")
	@Stories("FrameHandler")
	@Title("testMoveToParentFrame")
	@Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testMoveToChildFrameWithLocator")
	public void testMoveToParentFrame() {
		if (this.browserUnderTest.toLowerCase().contains("safari") || driver.toString().contains("safari"))
			throw new SkipException("Test not valid for SafariDriver");
		FrameHandler.moveToParentFrame(getDriver());
		Assert.assertTrue("Button was not found in frame 'main_page'",
				FrameHandler.getCurrentFrameName(getDriver()).equals("main_page"));
	}

	@Features("Utilities")
	@Stories("FrameHandler")
	@Title("testMoveToSiblingFrameWithName")
	@Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testMoveToParentFrame")
	public void testMoveToSiblingFrameWithName() {
		if (this.browserUnderTest.toLowerCase().contains("safari") || driver.toString().contains("safari"))
			throw new SkipException("Test not valid for SafariDriver");
		FrameHandler.moveToChildFrame(getDriver(), "main_frame1");
		FrameHandler.moveToSiblingFrame(getDriver(), "main_frame2");
		Assert.assertTrue("Button was not found in frame 'main_page'",
				FrameHandler.getCurrentFrameName(getDriver()).equals("main_frame2"));
	}

	@Features("Utilities")
	@Stories("FrameHandler")
	@Title("testMoveToSiblingFrameWithLocator")
	@Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testMoveToSiblingFrameWithName")
	public void testMoveToSiblingFrameWithLocator() {
		if (this.browserUnderTest.toLowerCase().contains("safari") || driver.toString().contains("safari"))
			throw new SkipException("Test not valid for SafariDriver");
		By locator = By.name("main_frame1");
		FrameHandler.moveToSiblingFrame(getDriver(), locator);
		Assert.assertTrue("Button was not found in frame 'main_frame1'",
				FrameHandler.getCurrentFrameName(getDriver()).equals("main_frame1"));
	}

	@Features("Utilities")
	@Stories("FrameHandler")
	@Title("testMoveToMultiChildFrameWithName")
	@Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testMoveToSiblingFrameWithLocator")
	public void testMoveToMultiChildFrameWithName() {
		FrameHandler.moveToDefaultContext(getDriver());
		FrameHandler.moveToChildFrame(getDriver(), new String[] { "main_page", "main_frame1" });
		Assert.assertTrue("Button was not found in frame 'main_frame1'",
				FrameHandler.getCurrentFrameName(getDriver()).equals("main_frame1"));
	}

	@Features("Utilities")
	@Stories("FrameHandler")
	@Title("testMoveToMultiChildFrameWithLocator")
	@Test(groups = { "regression", "utils", "dev" }, dependsOnMethods = "testMoveToMultiChildFrameWithName")
	public void testMoveToMultiChildFrameWithLocator() {
		By locatorParentFrame = By.name("main_page");
		By locatorChildFrame = By.name("main_frame1");
		FrameHandler.moveToDefaultContext(getDriver());
		FrameHandler.moveToChildFrame(getDriver(), new By[] { locatorParentFrame, locatorChildFrame });
		Assert.assertTrue("Button was not found in frame 'main_frame1'",
				FrameHandler.getCurrentFrameName(getDriver()).equals("main_frame1"));
	}

}
