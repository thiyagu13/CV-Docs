package com.eDocs.CommonValidation;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class AlphaNumericValidation {
 
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	

	@BeforeClass
	public void setUp() throws IOException  
	{
		//driver = new FirefoxDriver();
		//driver.get("http://192.168.1.111:8090");
		driver.get("http://192.168.1.111:8090");
		parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
	}

	@Test(priority=1)
	public void Login() throws InterruptedException
	{
		//Lets see how we can find the first name field
		Thread.sleep(1000);
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		username.sendKeys("thiyagu1");
		Thread.sleep(500);
		password.sendKeys("123456");
		Thread.sleep(500);
		driver.findElement(By.id("loginsubmit")).click();
		Thread.sleep(1000);
  		if (driver.findElements(By.id("forcelogin")).size()!=0)
  		{
  			// Force Login
  			Thread.sleep(1000);
  			driver.findElement(By.id("forcelogin")).click();
  		}
  		Thread.sleep(1500);
		driver.get("http://192.168.1.111:8090/equipment");
		Thread.sleep(1500);
		driver.findElement(By.id("addEquipment")).click(); 
  		Thread.sleep(1500);
	}
	
	
		@Test(priority=2)
		public void AlphaNumericEmptyTest() throws InterruptedException, WriteException, IOException
		{
			WebElement alphanumericField = driver.findElement(By.name("name"));
			WebElement Submit = driver.findElement(By.name("saveEquipment"));
			AlphaNumericEmpty(Submit,alphanumericField,6);
		}
		
		@Test(priority=3)
		public void MaxLengthCheckTest() throws InterruptedException, WriteException, IOException
		{
			WebElement alphanumericField = driver.findElement(By.name("name"));
			MaxLengthCheck(alphanumericField,8);
		}
		
		
		@Test(priority=4)
		public void nameSpecialCharCheckTest() throws InterruptedException, WriteException, IOException
		{
			WebElement alphanumericField = driver.findElement(By.name("name"));
			nameSpecialCharCheck(alphanumericField,9);
		}
		
		@Test(priority=5)
		public void nameBeginingSpaceCheckTest() throws InterruptedException, WriteException, IOException
		{
			WebElement alphanumericField = driver.findElement(By.name("name"));
			nameBeginingSpaceCheck(alphanumericField,10);
		}
		
		
		
	
  	public void  AlphaNumericEmpty(WebElement Submit,WebElement alphanumericField,int row) throws IOException, WriteException, InterruptedException
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
  		Thread.sleep(1000);
  		alphanumericField.clear();
  		Thread.sleep(500);
  		Submit.click();
		String expectedMSG = sheet.getRow(row).getCell(5).getStringCellValue(); // get expected value from excel
		String actualMSG = null;
		Thread.sleep(1000);
		if(driver.findElements(By.className("notify-msg")).size()!=0)
		{
			actualMSG = driver.findElement(By.className("notify-msg")).getText();
			String className = this.getClass().getName(); // get current class name - for screenshot
			String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
			Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		}
		XSSFCell actualMSGprint = sheet.getRow(row).getCell(6); //Print actual result in the excel cell
		actualMSGprint.setCellValue(actualMSG);
		
		if(expectedMSG.equalsIgnoreCase(actualMSG))
		{
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Fail"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
		Utils.writeTooutputFile(workbook); // write output to the workbook
		}
  	
  	
  	
  	public void  MaxLengthCheck(WebElement alphanumericField,int row) throws IOException, WriteException, InterruptedException
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
		
  		String maxLength = sheet.getRow(row).getCell(4).getStringCellValue(); // get name from excel
  		//WebElement alphanumericField = driver.findElement(By.name("name"));
  		Thread.sleep(500);
  		alphanumericField.clear();
  		Thread.sleep(500);
  		alphanumericField.sendKeys(maxLength); // send data to input field
  		Thread.sleep(2000);
		
		double expectedMSG = sheet.getRow(row).getCell(5).getNumericCellValue(); // get expected value from excel
		String actualMSG = driver.findElement(By.name("name")).getAttribute("value"); //get actual value from site
		double length = actualMSG.length();
		
		XSSFCell maxLength_actual_print = sheet.getRow(8).getCell(6); //Print actual result in the excel cell
		maxLength_actual_print.setCellValue(length);
		
		if(expectedMSG == length)
		{
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Fail"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
	  
		Utils.writeTooutputFile(workbook); // write output to the workbook
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
  		}
  	
  	
  	
  	
  	public void  nameSpecialCharCheck(WebElement alphanumericField, int row) throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
		
  		String maxLength = sheet.getRow(row).getCell(4).getStringCellValue(); // get name from excel
  		//WebElement alphanumericField = driver.findElement(By.name("name"));
  		Thread.sleep(500);
  		alphanumericField.clear();
  		Thread.sleep(500);
  		alphanumericField.sendKeys(maxLength); // send data to input field
  		Thread.sleep(2000);
		
		String expectedMSG =sheet.getRow(row).getCell(5).getStringCellValue(); // get expected value from excel
		String actualMSG = alphanumericField.getAttribute("value");//get actual value from site
		
		System.out.println("Spl Char"+actualMSG);
		XSSFCell maxLength_actual_print = sheet.getRow(row).getCell(6); //Print actual result in the excel cell
		maxLength_actual_print.setCellValue(actualMSG);
		
		if(expectedMSG.equalsIgnoreCase(actualMSG))
		{
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Fail"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
		Utils.writeTooutputFile(workbook); // write output to the workbook
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
  		}
	
  	
  	
  	
	public void  nameBeginingSpaceCheck(WebElement alphanumericField, int row) throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
		
  		String data = sheet.getRow(row).getCell(4).getStringCellValue(); // get name from excel
  		//WebElement alphanumericField = driver.findElement(By.name("name"));
  		Thread.sleep(500);
  		alphanumericField.clear();
  		Thread.sleep(500);
  		alphanumericField.sendKeys(data); // send data to input field
  		Thread.sleep(2000);
		
		String expectedMSG =sheet.getRow(row).getCell(4).getStringCellValue(); // get expected value from excel
		Integer ExpectedResult = expectedMSG.length();
		String actualMSG = alphanumericField.getAttribute("value"); //get actual value from site
		Integer ActualResult = actualMSG.length();
		
		if(!ExpectedResult.equals(ActualResult))
		{
			XSSFCell maxLength_actual_print = sheet.getRow(10).getCell(6); //Print actual result in the excel cell
			maxLength_actual_print.setCellValue("Space Not Accepted");
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell maxLength_actual_print = sheet.getRow(10).getCell(6); //Print actual result in the excel cell
			maxLength_actual_print.setCellValue("Space Accepted");
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Fail"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
		Utils.writeTooutputFile(workbook); // write output to the workbook
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
  		}
	
	
	
}
