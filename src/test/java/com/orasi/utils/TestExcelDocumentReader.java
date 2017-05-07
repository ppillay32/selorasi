package com.orasi.utils;

import org.junit.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.orasi.exception.AutomationException;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestExcelDocumentReader extends TestEnvironment{
	@BeforeTest
	public void setup(){
		setReportToMustard(false);
	}
	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataFilePathConstructorAllRows")
	@Test(groups="regression")
	public void readDataFilePathConstructorAllRows(){
		Object[][] data = null;
		String filepath = getClass().getResource("/excelsheets/TestData.xlsx").getPath();
		data = new ExcelDocumentReader(filepath).readData("Data");

		Assert.assertNotNull(data);
		Assert.assertTrue(data.length == 3);
		Assert.assertTrue(data[0].length == 5);
		Assert.assertTrue(data[0][0].toString().equals("DataSet1"));

	}

	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataFilePathConstructorSingleRow")
	@Test(groups="regression")
	public void readDataFilePathConstructorSingleRow(){
		Object[][] data = null;
		String filepath = getClass().getResource("/excelsheets/TestData.xlsx").getPath();
		data = new ExcelDocumentReader(filepath).readData("Data", 1);

		Assert.assertNotNull(data);
		Assert.assertTrue(data.length == 1);
		Assert.assertTrue(data[0].length == 5);
		Assert.assertTrue(data[0][0].toString().equals("DataSet1"));

	}


	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataFilePathConstructorSingleRow")
	@Test(groups="regression")
	public void readDataFilePathConstructorSheetNameSingleRow(){
		Object[][] data = null;
		String filepath = getClass().getResource("/excelsheets/TestData.xlsx").getPath();
		data = new ExcelDocumentReader(filepath).readData("Data", 1);

		Assert.assertNotNull(data);
		Assert.assertTrue(data.length == 1);
		Assert.assertTrue(data[0].length == 5);
		Assert.assertTrue(data[0][0].toString().equals("DataSet1"));

	}

	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataDefaultConstructorAllRows")
	@Test(groups="regression")
	public void readDataDefaultConstructorAllRows(){
		Object[][] data = null;
		String filepath = getClass().getResource("/excelsheets/TestData.xlsx").getPath();
		data = new ExcelDocumentReader().readData(filepath, "Data");

		Assert.assertNotNull(data);
		Assert.assertTrue(data.length == 3);
		Assert.assertTrue(data[0].length == 5);
		Assert.assertTrue(data[0][0].toString().equals("DataSet1"));

	}
	
	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataDefaultConstructorAllRows")
	@Test(groups="regression")
	public void readDataDefaultConstructorAllRowsIncludeColumns(){
		Object[][] data = null;
		String filepath = getClass().getResource("/excelsheets/TestData.xlsx").getPath();
		data = new ExcelDocumentReader(filepath).readData("Data", -1,0);

		Assert.assertNotNull(data);
		Assert.assertTrue(data.length == 4);
		Assert.assertTrue(data[0].length == 5);
		Assert.assertTrue(data[0][0].toString().equals("Column1"));
	}
	
	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataDefaultConstructorAllRows")
	@Test(groups="regression")
	public void readDataDefaultConstructorAllRowsStartRow(){
		Object[][] data = null;
		String filepath = getClass().getResource("/excelsheets/TestData.xlsx").getPath();
		data = new ExcelDocumentReader(filepath).readData("Data", -1,2);

		Assert.assertNotNull(data);
		Assert.assertTrue(data.length == 2);
		Assert.assertTrue(data[0].length == 5);
		Assert.assertTrue(data[0][0].toString().equals("DataSet2"));

	}
	
	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataDefaultConstructorSingleRow")
	@Test(groups="regression")
	public void readDataDefaultConstructorSingleRow(){
		Object[][] data = null;
		String filepath = getClass().getResource("/excelsheets/TestData.xlsx").getPath();
		data = new ExcelDocumentReader().readData(filepath, "Data", 1);

		Assert.assertNotNull(data);
		Assert.assertTrue(data.length == 1);
		Assert.assertTrue(data[0].length == 5);
		Assert.assertTrue(data[0][0].toString().equals("DataSet1"));

	}
	
	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataXlsFile")
	@Test(groups="regression")
	public void readDataXlsFile(){
		Object[][] data = null;
		String filepath = getClass().getResource("/excelsheets/TestData_xls.xls").getPath();
		data = new ExcelDocumentReader().readData(filepath, "Data", 1);

		Assert.assertNotNull(data);
		Assert.assertTrue(data.length == 1);
		Assert.assertTrue(data[0].length == 5);
		Assert.assertTrue(data[0][0].toString().equals("DataSet1"));

	}


	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataMissingXlsFile")
	@Test(groups="regression", expectedExceptions={AutomationException.class})
	public void readDataMissingXlsFile(){
		Object[][] data = null;
		data = new ExcelDocumentReader().readData("/excelsheets/TestData_blah.xls", "Data", 1);
	}

	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataInvalidXlsFile")
	@Test(groups="regression", expectedExceptions={AutomationException.class})
	public void readDataInvalidXlsFile(){
		Object[][] data = null;
		String filepath = getClass().getResource("/excelsheets/TestData_csv.csv").getPath();
		data = new ExcelDocumentReader().readData(filepath, "Data", 1);
	}
	
	
	@Features("Utilities")
	@Stories("ExcelDocumentReader")
	@Title("readDataSheetAsIndex")
	@Test(groups="regression")
	public void readDataSheetAsIndex(){
		Object[][] data = null;
		String filepath = getClass().getResource("/excelsheets/TestData.xlsx").getPath();
		data = new ExcelDocumentReader().readData(filepath, "0");

		Assert.assertNotNull(data);
		Assert.assertTrue(data.length == 3);
		Assert.assertTrue(data[0].length == 5);
		Assert.assertTrue(data[0][0].toString().equals("DataSet1"));

	}
}
