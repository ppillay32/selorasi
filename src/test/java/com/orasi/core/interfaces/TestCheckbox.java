package com.orasi.core.interfaces;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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


public class TestCheckbox extends TestEnvironment{
    WebDriver driver = null;
    
    @BeforeTest(groups ={"regression", "interfaces", "checkbox", "dev"})
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
	setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/checkbox.html");
	testStart("TestCheckbox");
    }
    
    @AfterTest(groups ={"regression", "interfaces", "checkbox", "dev"})
    public void close(ITestContext testResults){
	endTest("TestAlert", testResults);
    }

	@Features("Element Interfaces")
	@Stories("Checkbox")
	@Title("check")
    @Test(groups ={"regression", "interfaces", "checkbox"}, dependsOnMethods="isChecked")
    public void check(){
	Checkbox checkbox= getDriver().findCheckbox(By.name("checkbox"));
	checkbox.check();
	Assert.assertTrue(checkbox.isChecked());
    }

	@Features("Element Interfaces")
	@Stories("Checkbox")
	@Title("isCheck")
	@Test(groups ={"regression", "interfaces", "checkbox"})
    public void isChecked(){
	Checkbox checkbox= getDriver().findCheckbox(By.name("checkbox"));
	Assert.assertFalse(checkbox.isChecked());
    }

	@Features("Element Interfaces")
	@Stories("Checkbox")
	@Title("toggle")
	@Test(groups ={"regression", "interfaces", "checkbox"}, dependsOnMethods="jsToggle")
    public void toggle(){
	Checkbox checkbox= getDriver().findCheckbox(By.name("checkbox"));
	checkbox.toggle();
	Assert.assertFalse(checkbox.isChecked());
    }

	@Features("Element Interfaces")
	@Stories("Checkbox")
	@Title("uncheck")
	@Test(groups ={"regression", "interfaces", "checkbox"}, dependsOnMethods="check")
    public void uncheck(){
	Checkbox checkbox= getDriver().findCheckbox(By.name("checkbox"));
	checkbox.uncheck();
	Assert.assertFalse(checkbox.isChecked());
    }

	@Features("Element Interfaces")
	@Stories("Checkbox")
	@Title("jsToggle")
	@Test(groups ={"regression", "interfaces", "checkbox"}, dependsOnMethods="uncheck")
    public void jsToggle(){
	Checkbox checkbox= getDriver().findCheckbox(By.name("checkbox"));
	checkbox.jsToggle();
	Assert.assertTrue(checkbox.isChecked());
    }
}
