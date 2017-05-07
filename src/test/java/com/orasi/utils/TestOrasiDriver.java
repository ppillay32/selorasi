package com.orasi.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.core.by.angular.ByNG;
import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Checkbox;
import com.orasi.core.interfaces.Element;
import com.orasi.core.interfaces.Label;
import com.orasi.core.interfaces.Link;
import com.orasi.core.interfaces.Listbox;
import com.orasi.core.interfaces.RadioGroup;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.Webtable;
import com.orasi.exception.automation.KeyExistsException;
import com.orasi.exception.automation.NoKeyFoundException;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.saucerest.SauceREST;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestOrasiDriver extends TestEnvironment {

	protected ResourceBundle appURLRepository = ResourceBundle.getBundle(Constants.ENVIRONMENT_URL_PATH);
	protected SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(
			Base64Coder.decodeString(appURLRepository.getString("SAUCELABS_USERNAME")),
			Base64Coder.decodeString(appURLRepository.getString("SAUCELABS_KEY")));
	DesiredCapabilities caps = null;
	OrasiDriver driver = null;
	File file = null;
	String runLocation = "";
	String browserUnderTest = "";
	String browserVersion = "";
	String operatingSystem = "";
	String environment = "";

	@BeforeTest(groups = { "regression", "utils", "orasidriver" })
	@Parameters({ "runLocation", "browserUnderTest", "browserVersion", "operatingSystem", "environment" })
	public void setup(@Optional String runLocation, String browserUnderTest, String browserVersion,
			String operatingSystem, String environment) {
		// setReportToMustard(false);

		if (browserUnderTest.equalsIgnoreCase("jenkinsParameter")) {
			this.browserUnderTest = System.getProperty("jenkinsBrowser").trim();
		} else {
			this.browserUnderTest = browserUnderTest;
		}

		if (browserVersion.equalsIgnoreCase("jenkinsParameter")) {
			browserVersion = System.getProperty("jenkinsBrowserVersion").trim();
		} else {
			this.browserVersion = browserVersion;
		}

		if (operatingSystem.equalsIgnoreCase("jenkinsParameter")) {
			operatingSystem = System.getProperty("jenkinsOperatingSystem").trim();
		} else {
			this.operatingSystem = operatingSystem;
		}

		if (runLocation.equalsIgnoreCase("jenkinsParameter")) {
			runLocation = System.getProperty("jenkinsRunLocation").trim();
		} else {
			this.runLocation = runLocation;
		}

		this.environment = environment;
		caps = new DesiredCapabilities();
		caps.setCapability("ignoreZoomSetting", true);
		caps.setCapability(CapabilityType.BROWSER_NAME, this.browserUnderTest);
		caps.setCapability(CapabilityType.VERSION, browserVersion);
		caps.setCapability(CapabilityType.PLATFORM, operatingSystem);
		caps.setCapability("enablePersistentHover", false);
		caps.setCapability("name", "TestOrasiDriver");
		setRunLocation(runLocation);
		if (runLocation.toLowerCase().equals("local")) {
			if (browserUnderTest.equalsIgnoreCase("IE")
					|| browserUnderTest.replace(" ", "").equalsIgnoreCase("internetexplorer")) {
				file = new File(
						this.getClass().getResource(Constants.DRIVERS_PATH_LOCAL + "IEDriverServer.exe").getPath());
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			} else if (browserUnderTest.equalsIgnoreCase("Chrome")) {
				file = new File(
						this.getClass().getResource(Constants.DRIVERS_PATH_LOCAL + "ChromeDriver.exe").getPath());
				System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			} else if (browserUnderTest.replace(" ", "").equalsIgnoreCase("microsoftedge")) {
				file = new File(
						this.getClass().getResource(Constants.DRIVERS_PATH_LOCAL + "MicrosoftWebDriver.exe").getPath());
				System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
			}
			driver = new OrasiDriver(caps);
		} else {
			TestEnvironment te = new TestEnvironment("", this.browserUnderTest, browserVersion, operatingSystem,
					runLocation, environment);
			try {
				driver = new OrasiDriver(caps, new URL(te.getRemoteURL()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		setDriver(driver);
		driver.get("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/testsite.html");

		// testStart("TestOrasiDriver");
	}

	private void endSauceTest(int result) {
		Map<String, Object> updates = new HashMap<String, Object>();
		updates.put("name", "TestOrasiDriver");

		if (result == ITestResult.FAILURE) {
			updates.put("passed", false);
		} else {
			updates.put("passed", true);
		}

		SauceREST client = new SauceREST(authentication.getUsername(), authentication.getAccessKey());
		client.updateJobInfo(driver.getSessionId().toString(), updates);

	}

	@AfterTest(groups = { "regression", "utils", "orasidriver" })
	public void close(ITestContext testResults) {
		if (runLocation.equalsIgnoreCase("sauce")) {
			if (testResults.getFailedTests().size() == 0) {
				endSauceTest(ITestResult.SUCCESS);
			} else {
				endSauceTest(ITestResult.FAILURE);
			}
		}
		driver.quit();
	}
	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("setOrasiDriver")
	@Test(groups = { "regression", "utils", "orasidriver" })
	public void setOrasiDriver() {
		OrasiDriver orasiDriver = new OrasiDriver();
		orasiDriver.setDriver(orasiDriver);
	}
	
	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("getPageTimeout")
	@Test(groups = { "regression", "utils", "orasidriver" })
	public void getPageTimeout() {
		Assert.assertTrue(driver.getPageTimeout() == Constants.PAGE_TIMEOUT);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("setPageTimeout")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "getPageTimeout")
	public void setPageTimeout() {
		if (this.browserUnderTest.toLowerCase().contains("safari") || driver.toString().contains("safari"))
			throw new SkipException("Test not valid for SafariDriver");
		driver.setPageTimeout(15);
		Assert.assertTrue(driver.getPageTimeout() == 15);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("getElementTimeout")
	@Test(groups = { "regression", "utils", "orasidriver" })
	public void getElementTimeout() {
		Assert.assertTrue(driver.getElementTimeout() == Constants.ELEMENT_TIMEOUT);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("setElementTimeout")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "getElementTimeout")
	public void setElementTimeout() {
		driver.setElementTimeout(15);
		Assert.assertTrue(driver.getElementTimeout() == 15);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("getScriptTimeout")
	@Test(groups = { "regression", "utils", "orasidriver" })
	public void getScriptTimeout() {
		Assert.assertTrue(driver.getScriptTimeout() == Constants.DEFAULT_GLOBAL_DRIVER_TIMEOUT);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("setScriptTimeout")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "getScriptTimeout")
	public void setScriptTimeout() {
		driver.setScriptTimeout(15);
		Assert.assertTrue(driver.getScriptTimeout() == 15);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("getDriver")
	@Test(groups = { "regression", "utils", "orasidriver" })
	public void testGetDriver() {
		Assert.assertNotNull(driver.getWebDriver());
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("executeJavaScript")
	@Test(groups = { "regression", "utils", "orasidriver" })
	public void executeJavaScript() {
		driver.executeJavaScript("var result = document.readyState; return (result == 'complete');");

		WebElement element = (WebElement) driver.executeJavaScript("return document.getElementById('FirstName')");
		Assert.assertNotNull(element);
	}
	

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findElements")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "testGetDriver")
	public void findElements() {
		List<WebElement> elements = driver.findElements(By.tagName("input"));
		Assert.assertTrue(elements.size() > 0);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findWebElements")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "findElements")
	public void findWebElements() {
		List<WebElement> elements = driver.findElements(By.tagName("input"));
		Assert.assertTrue(elements.size() > 0);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findButton")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "testGetDriver")
	public void findButton() {
		Button button = driver.findButton(By.id("Add"));
		Assert.assertNotNull(button);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findElement")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "findWebElements")
	public void findElement() {
		Element element = driver.findElement(By.id("FirstName"));
		Assert.assertNotNull(element);
	}
	
	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findListbox")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "findElement")
	public void findListbox() {
		driver.setElementTimeout(3);
		Listbox listbox = driver.findListbox(By.id("Category"));
		Assert.assertNotNull(listbox);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findTextbox")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "findListbox")
	public void findTextbox() {
		Textbox textbox = driver.findTextbox(By.id("FirstName"));
		Assert.assertNotNull(textbox);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findWebtable")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "findTextbox")
	public void findWebtable() {
		Webtable webtable = driver.findWebtable(By.id("VIPs"));
		Assert.assertNotNull(webtable);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findRadioGroup")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "findWebtable")
	public void findRadioGroup() {
		driver.get("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/radioGroup.html");
		RadioGroup radioGroup = driver.findRadioGroup(By.id("Content"));
		Assert.assertNotNull(radioGroup);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findCheckbox")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "findRadioGroup")
	public void findCheckbox() {
		driver.get("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/checkbox.html");
		Checkbox checkbox = driver.findCheckbox(By.name("checkbox"));
		Assert.assertNotNull(checkbox);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findLabel")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "findCheckbox")
	public void findLabel() {
		driver.get("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/label.html");
		Label label = driver.findLabel(By.xpath("//*[@id='radioForm']/label[1]"));
		Assert.assertNotNull(label);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findLink")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "findLabel")
	public void findLink() {
		driver.get("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/link.html");
		Link link = driver.findLink(By.xpath("//a[@href='testLinks.html']"));
		Assert.assertNotNull(link);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("executeAsyncJavaScript")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "findLink")
	public void executeAsyncJavaScript() {
		if (browserUnderTest.toLowerCase().equals("html") || browserUnderTest.isEmpty())
			throw new SkipException("Test not valid for HTMLUnitDriver");
		driver.get("http://cafetownsend-angular-rails.herokuapp.com/login");
		driver.executeAsyncJavaScript(
				"var callback = arguments[arguments.length - 1];angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);");
	}
	
	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("getCurrentUrl")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "executeAsyncJavaScript")
	public void getCurrentUrl() {
		Assert.assertTrue(driver.getCurrentUrl().contains("cafetownsend-angular-rails.herokuapp.com"));
	}
	
	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("getTitle")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "executeAsyncJavaScript")
	public void getTitle() {
		Assert.assertTrue(driver.getTitle().contains("CafeTownsend-AngularJS-Rails"));
	}
	
	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findNGModel")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "executeAsyncJavaScript")
	public void findNGModel() {
		Assert.assertNotNull(driver.findTextbox(ByNG.model("user.name")));
		driver.findTextbox(ByNG.model("user.name")).set("Luke");
		driver.findTextbox(ByNG.model("user.password")).set("Skywalker");
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findNGButtonText")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "findNGModel")
	public void findNGButtonText() {

		PageLoaded.isAngularComplete(driver);
		Assert.assertNotNull(driver.findButton(ByNG.buttonText("Login")));
		driver.findButton(ByNG.buttonText("Login")).jsClick();
		PageLoaded.isAngularComplete(driver);
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findNGController")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "findNGButtonText")
	public void findNGController() {
		Assert.assertNotNull(driver.findElement(ByNG.controller("HeaderController")));
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("findNGRepeater")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "findNGController")
	public void findNGRepeater() {
		Sleeper.sleep(2000);
		Assert.assertNotNull(driver.findElement(ByNG.repeater("employee in employees")));
	}
	
	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("getDataWarehouse")
	@Test(groups = { "regression", "utils", "orasidriver" })
	public void getDataWarehouse() {
		Assert.assertNotNull(driver.data());
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("addData")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "getDataWarehouse")
	public void addData() {
		driver.data().add("username", "Admin");
		Assert.assertNotNull(driver.data().get("username"));
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("getValidData")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "addData")
	public void getValidData() {
		Assert.assertNotNull(driver.data().get("username"));
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("getInvalidData")
	@Test(groups = { "regression", "utils", "orasidriver" }, expectedExceptions = NoKeyFoundException.class)
	public void getInvalidData() {
		driver.data().get("password");
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("addToExistingKey")
	@Test(groups = { "regression", "utils",
	"orasidriver" }, dependsOnMethods = "addData", expectedExceptions = KeyExistsException.class)
	public void addToExistingKey() {
		driver.data().add("username", "Employee");
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("updateExistingKey")
	@Test(groups = { "regression", "utils", "orasidriver" }, dependsOnMethods = "addData")
	public void updateExistingKey() {
		driver.data().update("username", "Manager");
		Assert.assertEquals("Manager", driver.data().get("username"));
	}

	@Features("Utilities")
	@Stories("OrasiDriver")
	@Title("updateNoKeyFound")
	@Test(groups = { "regression", "utils", "orasidriver" }, expectedExceptions = NoKeyFoundException.class)
	public void updateNoKeyFound() {
		driver.data().update("password", "Orasi123");
	}
}
