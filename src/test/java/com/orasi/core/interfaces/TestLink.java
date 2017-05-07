package com.orasi.core.interfaces;

import com.orasi.core.interfaces.impl.LinkImpl;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.utils.TestEnvironment;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;


public class TestLink extends TestEnvironment {
    
        @BeforeTest(groups ={"regression", "interfaces", "link", "dev"})
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
        	setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/link.html");
        	testStart("TestLink");
        }
    
        @AfterTest(groups ={"regression", "interfaces", "link", "dev"})
        public void close(ITestContext testResults){
    		endTest("TestAlert", testResults);
        }

	@Features("Element Interfaces")
	@Stories("Link")
	@Title("constructor")
	@Test(groups ={"regression", "interfaces", "link"})
	public void constructorWithElement(){
		Assert.assertNotNull((new LinkImpl(getDriver().findWebElement(By.xpath("//a[@href='testLinks.html']")))));
		Assert.assertNotNull((new LinkImpl(getDriver().findLink(By.xpath("//a[@href='testLinks.html']")))));
	}

	@Features("Element Interfaces")
	@Stories("Link")
	@Title("click")
	@Test(groups ={"regression", "interfaces", "link"}, dependsOnMethods="constructorWithElement")
	public void click(){
		Link link= getDriver().findLink(By.xpath("//a[@href='testLinks.html']"));
		link.click();
		Assert.assertTrue(getDriver().findElement(By.xpath("//a[@href='link.html']")).syncVisible());
	}

	@Features("Element Interfaces")
	@Stories("Link")
	@Title("clickNegative")
	@Test(groups ={"regression", "interfaces", "link"}, dependsOnMethods="click")
	public void clickNegative(){

		if(driver.getDriverCapability().browserName().contains("explorer") ||getBrowserUnderTest().toLowerCase().equals("html") || getBrowserUnderTest().isEmpty() ) throw new SkipException("Test not valid for Internet Explorer");
		if(getBrowserUnderTest().toLowerCase().equals("html") || getBrowserUnderTest().isEmpty() ) throw new SkipException("Test not valid for HTMLUnitDriver");
		Link link= getDriver().findLink(By.xpath("//a[@href='hiddenLink.html']"));
		boolean valid = false;
		try{
			link.click();
		}catch (RuntimeException rte){
			valid = true;
		}
		Assert.assertTrue(valid);
	}

	@Features("Element Interfaces")
	@Stories("Link")
	@Title("jsClick")
        @Test(groups ={"regression", "interfaces", "link"}, dependsOnMethods="click")
        public void jsClick(){
		Link link= getDriver().findLink(By.xpath("//a[@href='link.html']"));
		link.jsClick();
		Assert.assertTrue(getDriver().findLink(By.xpath("//a[@href='testLinks.html']")).syncVisible());
	}

	@Features("Element Interfaces")
	@Stories("Link")
	@Title("jsClickNegative")
	@Test(groups ={"regression", "interfaces", "link"}, dependsOnMethods="jsClick")
	public void jsClickNegative(){
		if(driver.getDriverCapability().browserName().contains("explorer") ) throw new SkipException("Test not valid for Internet Explorer");
		Link link= getDriver().findLink(By.xpath("//a[@href='hiddenLink.html']"));
		getDriver().findLink(By.xpath("//a[@href='testLinks.html']")).click();
		boolean valid = false;
		try{
			link.jsClick();
		}catch (RuntimeException rte){
			valid = true;
		}
		Assert.assertTrue(valid);
	}

	@Features("Element Interfaces")
	@Stories("Link")
	@Title("getURL")
        @Test(groups ={"regression", "interfaces", "link"}, dependsOnMethods="jsClick")
        public void getURL(){
        	Link link= getDriver().findLink(By.xpath("//a[@href='testLinks.html']"));
        	Assert.assertTrue(link.getURL().contains("testLinks.html"));
        }
}
