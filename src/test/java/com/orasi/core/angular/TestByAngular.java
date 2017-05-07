package com.orasi.core.angular;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.core.by.angular.ByNG;
import com.orasi.core.by.angular.WaitForAngularRequestsToFinish;
import com.orasi.core.by.angular.internal.ByAngular;
import com.orasi.core.by.angular.internal.ByAngularButtonText;
import com.orasi.core.by.angular.internal.ByAngularController;
import com.orasi.core.by.angular.internal.ByAngularModel;
import com.orasi.core.by.angular.internal.ByAngularRepeater;
import com.orasi.utils.TestEnvironment;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestByAngular extends TestEnvironment{
	@BeforeTest(groups ={"regression", "interfaces", "ByAngularModel", "dev"})
    @Parameters({ "runLocation", "browserUnderTest", "browserVersion",
	    "operatingSystem", "environment" })
    public void setup(@Optional String runLocation, String browserUnderTest,
	    String browserVersion, String operatingSystem, String environment) {
	    
	    if(getBrowserUnderTest().toLowerCase().equals("html")) setReportToMustard(false);
		setApplicationUnderTest("Test App");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		if(getBrowserUnderTest().toLowerCase().equals("html") || getBrowserUnderTest().isEmpty() ) throw new SkipException("Test not valid for HTMLUnitDriver");
		
		setRunLocation(runLocation);
		setPageURL("http://cafetownsend-angular-rails.herokuapp.com/login");	
		setTestEnvironment(environment);
		testStart("TestByAngularModel");
	}
    
    @AfterTest(groups ={"regression", "interfaces", "ByAngularModel", "dev"})
    public void close(ITestContext testResults){
	endTest("TestAlert", testResults);
    }


	@Features("Element Interfaces")
	@Stories("Angular")
	@Title("constructor")
    @Test(groups ={"regression", "interfaces", "ByAngularModel"})
    public void byAngular(){
    	ByAngular angular = new ByAngular(driver);
    	Assert.assertNotNull(angular);
    }

	@Features("Element Interfaces")
	@Stories("Angular")
	@Title("Model")
    @Test(groups ={"regression", "interfaces", "ByAngularModel"})
    public void byAngularModel(){
    	ByAngularModel model = new ByAngularModel((JavascriptExecutor)driver.getWebDriver(), "user.name");
    	Assert.assertNotNull(model);
    }


	@Features("Element Interfaces")
	@Stories("Angular")
	@Title("Model")
    @Test(groups ={"regression", "interfaces", "ByAngularModel"})
    public void byAngularButtonText(){
    	ByAngularButtonText buttonText = new ByAngularButtonText((JavascriptExecutor)driver.getWebDriver(), "Login");
    	Assert.assertNotNull(buttonText);
    	driver.findTextbox(ByNG.model("user.name")).set("Luke");
    	driver.findTextbox(ByNG.model("user.password")).set("Skywalker");
    	driver.findTextbox(ByNG.buttonText("Login")).click();
    	
    }


	@Features("Element Interfaces")
	@Stories("Angular")
	@Title("waitForAgnularRequestsToFinish")
    @Test(groups={"regression", "interfaces", "WaitForAngularRequestsToFinish"}, dependsOnMethods="byAngularButtonText")
    public void waitForAngularRequestsToFinish(){
    	WaitForAngularRequestsToFinish.waitForAngularRequestsToFinish(driver);
    }

	@Features("Element Interfaces")
	@Stories("Angular")
	@Title("controller")
    @Test(groups ={"regression", "interfaces", "byAngularController"}, dependsOnMethods="byAngularButtonText")
    public void byAngularController(){
    	ByAngularController controller = new ByAngularController((JavascriptExecutor)driver.getWebDriver(), "HeaderController");
    	Assert.assertNotNull(controller);
    }


	@Features("Element Interfaces")
	@Stories("Angular")
	@Title("repeater")
    @Test(groups ={"regression", "interfaces", "byAngularController"}, dependsOnMethods="byAngularButtonText")
    public void byAngularRepeater(){
    	ByAngularRepeater repeat = new ByAngularRepeater((JavascriptExecutor)driver.getWebDriver(), "employee in employees");
    	Assert.assertNotNull(repeat);
    }
    
    
}
