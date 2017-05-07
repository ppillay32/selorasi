package com.orasi.core.interfaces;

import java.util.List;

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

public class TestRadioButton extends TestEnvironment{
//    private String xpath = "//form/fieldset[1]";
    @BeforeTest(groups ={"regression", "interfaces", "radiogroup", "dev"})
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
	setPageURL("http://orasi.github.io/Chameleon/sites/unitTests/orasi/core/interfaces/radioGroup.html");
	testStart("TestRadiogroup");
    }

    
    @AfterTest(groups ={"regression", "interfaces", "radiogroup", "dev"})
    public void close(ITestContext testResults){
	endTest("TestAlert", testResults);
    }


/*	@Features("Element Interfaces")
	@Stories("RadioGroup")
	@Title("constructor")
	@Test(groups ={"regression", "interfaces", "textbox"})
	public void constructorWithElement(){
		Assert.assertNotNull((new RadioGroupImpl(getDriver().findWebElement((By.id("radioForm"))),driver)));
	}*/

	@Features("Element Interfaces")
	@Stories("RadioGroup")
	@Title("getNumberOfOptions")
    @Test(groups ={"regression", "interfaces", "radiogroup"})
    public void getNumberOfOptions(){

	RadioGroup radiogroup = getDriver().findRadioGroup(By.id("radioForm"));
	Assert.assertTrue(radiogroup.getNumberOfOptions() == 3 );
    }
    
   /* @Test(groups ={"regression", "interfaces", "radiogroup"})
    public void getNumberOfRadioButtons(){
	RadioGroup radiogroup = getDriver().findRadioGroup(By.id("radioForm"));
	Assert.assertTrue(radiogroup.getNumberOfRadioButtons() == 2 );
    }
*/
   @Features("Element Interfaces")
   @Stories("RadioGroup")
   @Title("getSelectedIndex")
    @Test(groups ={"regression", "interfaces", "radiogroup"})
    public void getSelectedIndex(){
	RadioGroup radiogroup = getDriver().findRadioGroup(By.id("radioForm"));
	Assert.assertTrue(radiogroup.getSelectedIndex() == 1 );
    }

	@Features("Element Interfaces")
	@Stories("RadioGroup")
	@Title("getSelectedOption")
    @Test(groups ={"regression", "interfaces", "radiogroup"})
    public void getSelectedOption(){
	RadioGroup radiogroup = getDriver().findRadioGroup(By.id("radioForm"));
	Assert.assertTrue(radiogroup.getSelectedOption().equals("female") );
    }

	@Features("Element Interfaces")
	@Stories("RadioGroup")
	@Title("selectByIndex")
    @Test(groups ={"regression", "interfaces", "radiogroup"}, dependsOnMethods="getSelectedIndex")
	public void selectByIndex(){
		RadioGroup radiogroup = getDriver().findRadioGroup(By.id("radioForm"));
		radiogroup.selectByIndex(1);
		Assert.assertTrue(radiogroup.getSelectedIndex() == 1 );
	}

	@Features("Element Interfaces")
	@Stories("RadioGroup")
	@Title("selectIndexOutOfBounds")
	@Test(groups ={"regression", "interfaces", "radiogroup"}, dependsOnMethods="selectByIndex")
	public void selectByIndexOutOfBounds(){
		RadioGroup radiogroup = getDriver().findRadioGroup(By.id("radioForm"));
		boolean valid = false;
		try{
			radiogroup.selectByIndex(3);
		}catch (RuntimeException rte){
			valid = true;
		}
		Assert.assertTrue(valid);
	}

	@Features("Element Interfaces")
	@Stories("RadioGroup")
	@Title("selectByOption")
    @Test(groups ={"regression", "interfaces", "radiogroup"}, dependsOnMethods="selectByIndex")
    public void selectByOption(){
		RadioGroup radiogroup = getDriver().findRadioGroup(By.id("radioForm"));
		radiogroup.selectByOption("male");
		Assert.assertTrue(radiogroup.getSelectedIndex() == 0 );
    }

	@Features("Element Interfaces")
	@Stories("RadioGroup")
	@Title("selectByOptionNoText")
	@Test(groups ={"regression", "interfaces", "radiogroup"}, dependsOnMethods="selectByOption")
	public void selectByOptionNoText(){
		if(getBrowserUnderTest().toLowerCase().equals("html") || getBrowserUnderTest().isEmpty() ) throw new SkipException("Test not valid for HTMLUnitDriver");
		if(driver.getDriverCapability().browserName().contains("explorer")) throw new SkipException("Test not valid for Internet Explorer");
		RadioGroup radiogroup = getDriver().findRadioGroup(By.id("radioForm"));
		radiogroup.selectByOption("");
		Assert.assertTrue(radiogroup.getSelectedIndex() == 0);
	}

	@Features("Element Interfaces")
	@Stories("RadioGroup")
	@Title("selectByOptionNegative")
	@Test(groups ={"regression", "interfaces", "radiogroup"}, dependsOnMethods="selectByOption")
	public void selectByOptionNegative(){
		if(driver.getDriverCapability().browserName().contains("explorer")) throw new SkipException("Test not valid for Internet Explorer");
		if(getBrowserUnderTest().toLowerCase().equals("html") || getBrowserUnderTest().isEmpty() ) throw new SkipException("Test not valid for HTMLUnitDriver");
		if(getBrowserUnderTest().toLowerCase().equals("html") || getBrowserUnderTest().isEmpty() ) throw new SkipException("Test not valid for HTMLUnitDriver");
		RadioGroup radiogroup = getDriver().findRadioGroup(By.id("radioForm"));
		boolean valid = false;
		try{
			radiogroup.selectByOption("disabled");
		}catch (RuntimeException rte){
			valid = true;
		}
		Assert.assertTrue(valid);
	}

	@Features("Element Interfaces")
	@Stories("RadioGroup")
	@Title("getAllOptions")
    @Test(groups ={"regression", "interfaces", "radiogroup"})
    public void getAllOptions(){
	RadioGroup radiogroup = getDriver().findRadioGroup(By.id("radioForm"));
	List<String> group = radiogroup.getAllOptions();
	Assert.assertTrue(group.get(0).equals("male"));
	Assert.assertTrue(group.get(1).equals("female"));
    }
}
