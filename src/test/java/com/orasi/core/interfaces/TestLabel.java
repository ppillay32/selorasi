package com.orasi.core.interfaces;

import com.orasi.core.interfaces.impl.LabelImpl;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.utils.TestEnvironment;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestLabel extends TestEnvironment{
    
    @BeforeTest(groups ={"regression", "interfaces", "label", "dev"})
    @Parameters({ "runLocation", "browserUnderTest", "browserVersion",
	    "operatingSystem", "environment" })
    public void setup(@Optional String runLocation, String browserUnderTest,
	    String browserVersion, String operatingSystem, String environment) {
	setApplicationUnderTest("Test Site");
	setBrowserUnderTest(browserUnderTest);
	setBrowserVersion(browserVersion);
	setOperatingSystem(operatingSystem);
	setRunLocation(runLocation);
	setTestEnvironment(environment);
	setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/label.html");
	testStart("TestLabel");
    }
    
    @AfterTest(groups ={"regression", "interfaces", "label", "dev"})
    public void close(ITestContext testResults){
	endTest("TestAlert", testResults);
    }

	@Features("Element Interfaces")
	@Stories("Label")
	@Title("constructor")
	@Test(groups ={"regression", "interfaces", "label"})
	public void constructorWithElement(){
		Assert.assertNotNull((new LabelImpl(getDriver().findWebElement((By.xpath("//*[@id='radioForm']/label[1]"))))));
	}

	@Features("Element Interfaces")
	@Stories("Label")
	@Title("getFor")
    @Test(groups ={"regression", "interfaces", "label"})
    public void getFor(){
		Label label= getDriver().findLabel(By.xpath("//*[@id='radioForm']/label[1]"));
		Assert.assertTrue(label.getFor().equals("genderm"));
    }

}
