package com.orasi.core.interfaces;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.exception.automation.ElementAttributeValueNotMatchingException;
import com.orasi.exception.automation.ElementCssValueNotMatchingException;
import com.orasi.exception.automation.ElementNotDisabledException;
import com.orasi.exception.automation.ElementNotEnabledException;
import com.orasi.exception.automation.ElementNotHiddenException;
import com.orasi.exception.automation.ElementNotVisibleException;
import com.orasi.exception.automation.TextInElementNotPresentException;
import com.orasi.utils.TestEnvironment;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestElement extends TestEnvironment {
	@BeforeTest(groups = { "regression", "interfaces", "element", "dev" })
	@Parameters({ "runLocation", "browserUnderTest", "browserVersion", "operatingSystem", "environment" })
	public void setup(@Optional String runLocation, String browserUnderTest, String browserVersion,
			String operatingSystem, String environment) {
		setApplicationUnderTest("Test Site");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setTestEnvironment(environment);
		setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/element.html");
		testStart("TestElement");
		getDriver().findWebElement(By.id("text1")).sendKeys("blah");
	}

	@AfterTest(groups = { "regression", "interfaces", "element", "dev" })
	public void close(ITestContext testResults) {
		endTest("TestAlert", testResults);
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("reload")
	@Test(groups = { "regression", "interfaces", "element" })
	public void reload() {
		Element element = getDriver().findElement(By.id("text1"));
		getDriver().get(pageUrl);
		Assert.assertTrue(element.isEnabled());
	}
	
	@Features("Element Interfaces")
	@Stories("Element")
	@Title("clear")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void clear() {
		Element element = getDriver().findElement(By.id("text1"));
		element.clear();
		Assert.assertTrue(element.getAttribute("value").equals(""));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("click")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "clear", alwaysRun=true)
	public void click() {
		Element element = getDriver().findElement(By.id("buttonForText1"));
		if(getBrowserUnderTest().toLowerCase().contains("edge")) element.jsClick();
		else element.click();
		//temporary fix for edge click
		Assert.assertTrue(getDriver().findElement(By.id("text1")).getAttribute("value").equals("Clicked button"));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("elementWired")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "click", alwaysRun=true)
	public void elementWired() {
		Element element = getDriver().findElement(By.id("text1"));
		Assert.assertTrue(element.elementWired());
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("getAttribute")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void getAttribute() {
		Element element = getDriver().findElement(By.xpath("//input[@value='female']"));
		Assert.assertTrue(element.getAttribute("type").equals("radio"));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("getCoordinates")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void getCoordinates() {
		Element element = getDriver().findElement(By.id("text1"));
		try {
			Assert.assertTrue(element.getCoordinates().onPage().getX() > 0);
			Assert.assertTrue(element.getCoordinates().onPage().getY() > 0);
		} catch (WebDriverException wde) {
			if (getBrowserUnderTest().toLowerCase().contains("edge"))
				throw new AssertionError("getCoordinates not supported by EdgeDriver");
		}
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("getCssValue")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void getCssValue() {
		Element element = getDriver().findElement(By.id("buttonForText1"));
		if (getBrowserUnderTest().equalsIgnoreCase("html"))
			Assert.assertTrue(element.getCssValue("font-family").equals("verdana"));
		else
			Assert.assertTrue(!element.getCssValue("font-family").isEmpty());
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("getElementIdentifier")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void getElementIdentifier() {
		Element element = getDriver().findElement(By.id("text1"));
		Assert.assertTrue(element.getElementIdentifier().equals("text1"));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("getElementLocator")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void getElementLocator() {
		Element element = getDriver().findElement(By.id("text1"));
		Assert.assertNotNull(element.getElementLocator());
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("getElementLocatorInfo")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void getElementLocatorInfo() {
		Element element = getDriver().findElement(By.id("text1"));
		Assert.assertNotNull(element.getElementLocatorInfo());
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("getLocation")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void getLocation() {
		Element element = getDriver().findElement(By.id("text1"));
		try {
			Assert.assertTrue(element.getLocation().getX() > 0);
			Assert.assertTrue(element.getLocation().getY() > 0);
		} catch (WebDriverException wde) {
			if (getBrowserUnderTest().toLowerCase().contains("edge"))
				throw new AssertionError("getLocation not supported by EdgeDriver");
		}
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("getSize")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void getSize() {
		Element element = getDriver().findElement(By.id("text1"));
		Assert.assertTrue(element.getSize().getHeight() > 0);
		Assert.assertTrue(element.getSize().getWidth() > 0);
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("getTagName")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void getTagName() {
		Element element = getDriver().findElement(By.id("text1"));
		Assert.assertTrue(element.getTagName().equals("input"));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("getText")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void getText() {
		Element element = getDriver().findElement(By.id("pageheader"));
		Assert.assertTrue(element.getText().equals("Element test page"));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("highlight")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "getAttribute", alwaysRun=true)
	public void highlight() {
		Element element = getDriver().findElement(By.id("buttonForText1"));
		element.highlight();
		Assert.assertTrue(element.getAttribute("style").toLowerCase().contains("red"));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("isDisplayed")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void isDisplayed() {
		Element element = getDriver().findElement(By.id("text1"));
		Assert.assertTrue(element.isDisplayed());
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("isEnabled")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void isEnabled() {
		Element element = getDriver().findElement(By.id("text1"));
		Assert.assertTrue(element.isEnabled());
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("isSelected")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void isSelected() {
		Element element = getDriver().findElement(By.xpath("//*[@id='radiogroup']/input[1]"));
		Assert.assertTrue(element.isSelected());
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("isSelectedNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void isSelectedNegative() {
		Element element = getDriver().findElement(By.xpath("//*[@id='radiogroup']/input[2]"));
		Assert.assertFalse(element.isSelected());
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("jsClick")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "click", alwaysRun=true)
	public void jsClick() {
		getDriver().findElement(By.id("text1")).sendKeys("blah");
		Element element = getDriver().findElement(By.id("buttonForText1"));
		element.jsClick();
		Assert.assertTrue(getDriver().findElement(By.id("text1")).getAttribute("value").equals("Clicked button"));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("sendKeys")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "jsClick", alwaysRun=true)
	public void sendKeys() {
		Element element = getDriver().findElement(By.id("text1"));
		element.sendKeys("testing");
		Assert.assertTrue(element.getAttribute("value").equals("Clicked buttontesting"));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncDisabledBasic")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncDisabledBasic() {
		Element element = getDriver().findElement(By.id("disable"));
		Assert.assertTrue(element.syncDisabled());
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncDisabledBasicNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = { "syncDisabledBasic" }, alwaysRun=true, expectedExceptions=ElementNotDisabledException.class)
	public void syncDisabledBasicNegative() {
		Element element = getDriver().findElement(By.id("text1"));
		element.syncDisabled();
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncDisabledAdvanced")
	@Test(groups = { "regression", "element" }, dependsOnMethods = { "syncDisabledBasic" }, alwaysRun=true)
	public void syncDisabledAdvanced() {
		Element element = getDriver().findElement(By.id("disable"));
		Assert.assertTrue(element.syncDisabled(5, false));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncDisabledAdvancedNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = { "syncDisabledBasic" }, alwaysRun=true)
	public void syncDisabledAdvancedNegative() {
		Element element = getDriver().findElement(By.id("text1"));
		Assert.assertFalse(element.syncDisabled(1, false));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncHiddenBasic")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void syncHiddenBasic() {
		Element element = getDriver().findElement(By.id("hidden"));
		Assert.assertTrue(element.syncHidden());
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncHiddenBasicNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "elementWired", alwaysRun=true, expectedExceptions=ElementNotHiddenException.class)
	public void syncHiddenBasicNegative() {
		Element element = getDriver().findElement(By.id("text1"));
		element.syncHidden();
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncHiddenAdvanced")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void syncHiddenAdvanced() {
		Element element = getDriver().findElement(By.id("hidden"));
		Assert.assertTrue(element.syncHidden(5, false));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncHiddenAdvancedNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void syncHiddenAdvancedNegative() {
		Element element = getDriver().findElement(By.id("text1"));
		Assert.assertFalse(element.syncHidden(1, false));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncVisibleBasic")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void syncVisibleBasic() {
		Textbox element = getDriver().findTextbox(By.id("text1"));
		Assert.assertTrue(element.syncVisible());
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncVisibleBasicNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "elementWired", alwaysRun=true, expectedExceptions=ElementNotVisibleException.class)
	public void syncVisibleBasicNegative() {
		Element element = getDriver().findElement(By.id("hidden"));
		element.syncVisible();
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncVisibleAdvanced")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void syncVisibleAdvanced() {
		Element element = getDriver().findElement(By.id("text1"));
		Assert.assertTrue(element.syncVisible(5, false));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncVisibleAdvancedNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void syncVisibleAdvancedNegative() {
		Element element = getDriver().findElement(By.id("hidden"));
		Assert.assertFalse(element.syncVisible(1, false));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncEnabledBasic")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void syncEnabledBasic() {
		Element element = getDriver().findElement(By.id("text1"));
		Assert.assertTrue(element.syncEnabled());
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncEnabledBasicNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true, expectedExceptions=ElementNotEnabledException.class)
	public void syncEnabledBasicNegative() {
	    Element element = getDriver().findElement(By.id("disable"));
	    element.syncEnabled();
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncEnabledAdvanced")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "elementWired", alwaysRun=true)
	public void syncEnabledAdvanced() {
		Element element = getDriver().findElement(By.id("text1"));
		Assert.assertTrue(element.syncEnabled(5, false));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncEnabledAdvancedNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncEnabledAdvancedNegative() {
		Element element = getDriver().findElement(By.id("disable"));
		Assert.assertFalse(element.syncEnabled(1, false));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncTextInElementBasic")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncTextInElementBasic() {
		Element element = getDriver().findElement(By.id("pageheader"));
		Assert.assertTrue(element.syncTextInElement("Element test page"));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncTextInElementBasicNegative")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun=true, expectedExceptions=TextInElementNotPresentException.class)
	public void syncTextInElementBasicNegative() {
		Element element = getDriver().findElement(By.id("pageheader"));
		element.syncTextInElement("Loading");
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncTextInElementAdvanced")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncTextInElementAdvanced() {
		Element element = getDriver().findElement(By.id("pageheader"));
		Assert.assertTrue(element.syncTextInElement("Element test page", 5, false));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncTextInElementAdvancedNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncTextInElementAdvancedNegative() {
		Element element = getDriver().findElement(By.id("pageheader"));
		Assert.assertFalse(element.syncTextInElement("negative", 2, false));
	}
	

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncTextMatchesInElementBasic")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncTextMatchesInElementBasic() {
		Element element = getDriver().findElement(By.id("pageheader"));
		Assert.assertTrue(element.syncTextMatchesInElement("(.*test page)"));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncTextMatchesInElementBasicNegative")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun=true, expectedExceptions=TextInElementNotPresentException.class)
	public void syncTextMatchesInElementBasicNegative() {
		Element element = getDriver().findElement(By.id("pageheader"));
		element.syncTextMatchesInElement("(.*tst pge)");
	}

	
	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncTextMatchesInElementAdvanced")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncTextMatchesInElementAdvanced() {
		Element element = getDriver().findElement(By.id("pageheader"));
		Assert.assertTrue(element.syncTextMatchesInElement("(.*test page)", 5, false));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncTextMatchesInElementAdvancedNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncTextMatchesInElementAdvancedNegative() {
		Element element = getDriver().findElement(By.id("pageheader"));
		Assert.assertFalse(element.syncTextMatchesInElement("(.*tst pge)", 2, false));
	}
	
	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncAttributeContainsValueBasic")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncAttributeContainsValueBasic() {
		Element element = getDriver().findElement(By.xpath("//input[@value='female']"));
		Assert.assertTrue(element.syncAttributeContainsValue("type","radio"));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncAttributeContainsValueBasicNegative")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun=true, expectedExceptions=ElementAttributeValueNotMatchingException.class)
	public void syncAttributeContainsValueBasicNegative() {
		Element element = getDriver().findElement(By.xpath("//input[@value='female']"));
		element.syncAttributeContainsValue("type","Radio");
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncAttributeContainsValueAdvanced")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncAttributeContainsValueAdvanced() {
		Element element = getDriver().findElement(By.xpath("//input[@value='female']"));
		Assert.assertTrue(element.syncAttributeContainsValue("type","radio", 5, false));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncAttributeContainsValueAdvancedNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncAttributeContainsValueAdvancedNegative() {
		Element element = getDriver().findElement(By.xpath("//input[@value='female']"));
		Assert.assertFalse(element.syncAttributeContainsValue("type","Rao", 2, false));
	}
	
	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncAttributeMatchesValueBasic")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncAttributeMatchesValueBasic() {
		Element element = getDriver().findElement(By.xpath("//input[@value='female']"));
		Assert.assertTrue(element.syncAttributeMatchesValue("type","(.*adi.*)"));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncAttributeMatchesValueBasicNegative")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun=true, expectedExceptions=ElementAttributeValueNotMatchingException.class)
	public void syncAttributeMatchesValueBasicNegative() {
		Element element = getDriver().findElement(By.xpath("//input[@value='female']"));
		element.syncAttributeMatchesValue("type","(.*adi)");
	}

	
	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncAttributeMatchesValueAdvanced")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncAttributeMatchesValueAdvanced() {
		Element element = getDriver().findElement(By.xpath("//input[@value='female']"));
		Assert.assertTrue(element.syncAttributeMatchesValue("type","(.*adi.*)"));
	}
	
	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncAttributeMatchesValueAdvancedNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncAttributeMatchesValueAdvancedNegative() {
		Element element = getDriver().findElement(By.xpath("//input[@value='female']"));
		Assert.assertFalse(element.syncAttributeMatchesValue("type","(.*adi)", 2, false));
	}
	
	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncCssPropertyContainsValueBasic")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncCssPropertyContainsValueBasic() {
		Element element = getDriver().findElement(By.id("buttonForText1"));
		Assert.assertTrue(element.syncCssPropertyContainsValue("display","inline"));

	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncCssPropertyContainsValueBasicNegative")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun=true, expectedExceptions=ElementCssValueNotMatchingException.class)
	public void syncCssPropertyContainsValueBasicNegative() {
		Element element = getDriver().findElement(By.id("buttonForText1"));
	    element.syncCssPropertyContainsValue("display","Inline");
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncCssPropertyContainsValueAdvanced")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncCssPropertyContainsValueAdvanced() {
		Element element = getDriver().findElement(By.id("buttonForText1"));
	    Assert.assertTrue(element.syncCssPropertyContainsValue("display","inline", 5, false));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncCssPropertyContainsValueAdvancedNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncCssPropertyContainsValueAdvancedNegative() {
		Element element = getDriver().findElement(By.id("buttonForText1"));
		if (getBrowserUnderTest().equalsIgnoreCase("html")){
		    Assert.assertFalse(element.syncCssPropertyContainsValue("type","transasdfpar", 2, false));
		}else{
		    Assert.assertFalse(element.syncCssPropertyContainsValue("display","Inline", 2, false));
		}
	}
	
	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncCssPropertyMatchesValueBasic")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncCssPropertyMatchesValueBasic() {
		Element element = getDriver().findElement(By.id("buttonForText1"));
	    Assert.assertTrue(element.syncCssPropertyMatchesValue("display","(.*inline.*)"));
	}

	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncCssPropertyMatchesValueBasicNegative")
	@Test(groups = { "regression", "interfaces", "element" }, dependsOnMethods = "reload", alwaysRun=true, expectedExceptions=ElementCssValueNotMatchingException.class)
	public void syncCssPropertyMatchesValueBasicNegative() {
		Element element = getDriver().findElement(By.id("buttonForText1"));
	    element.syncCssPropertyMatchesValue("display","(.*Inline.*)");
	}

	
	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncCssPropertyMatchesValueAdvanced")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncCssPropertyMatchesValueAdvanced() {
		Element element = getDriver().findElement(By.id("buttonForText1"));
	    Assert.assertTrue(element.syncCssPropertyMatchesValue("display","(.*inline.*)", 2, false));
	}
	
	@Features("Element Interfaces")
	@Stories("Element")
	@Title("syncCssPropertyMatchesValueAdvancedNegative")
	@Test(groups = { "regression", "element" }, dependsOnMethods = "reload", alwaysRun=true)
	public void syncCssPropertyMatchesValueAdvancedNegative() {
		Element element = getDriver().findElement(By.id("buttonForText1"));
	    Assert.assertFalse(element.syncCssPropertyMatchesValue("display","(.*Inline-Block.*)", 2, false));
	}
	
	
}
