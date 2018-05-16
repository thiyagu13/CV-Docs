package com.eDocs.Equipment;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class Equipment_Name {
	
	public static WebDriver driver = Constant.driver;
	
	
	
  @BeforeClass
  public void geturl() throws InterruptedException 
  {
	  driver.get("http://localhost:8092/login"); //open site url
	
	  		//Login
			Thread.sleep(1000);
			driver.findElement(By.id("username")).sendKeys("thiyagu1");
			driver.findElement(By.id("password")).sendKeys("123456");
			Thread.sleep(500);
			driver.findElement(By.id("loginsubmit")).click();
			
			/*if (driver.findElement(By.className("top-message")).getText().equalsIgnoreCase("Invalid credentials!")) 
			{
				System.out.println(driver.findElement(By.className("top-message")).getText());
			} 
			else {
					// Force Login
					if (driver.getTitle().equalsIgnoreCase("Report Tracker - eResidue") == false) 
					{
					Thread.sleep(1000);
					String pop = driver.getWindowHandle();
					driver.switchTo().window(pop);
					Thread.sleep(1000);
					driver.findElement(By.id("forcelogin")).click();
					}
				 }
			Thread.sleep(1000);
			//go to equipment module
			WebElement fields = driver.findElement(By.xpath("html/body/div[6]/div/div[2]/nav/ul/li[3]/a")); // fileds menu link
			Thread.sleep(500);
			Actions fieldsub =new Actions(driver);
			fieldsub.moveToElement(fields).perform();
			driver.findElement(By.linkText("Equipment")).click(); // submenu link (Equipment)
*/			Thread.sleep(500);
  	}
  
  
  	@Test (priority=1)
  	public void  nameEmpty() throws IOException, WriteException, InterruptedException
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
  		driver.get("http://localhost:8092/equipment");
  		//driver.manage().window().maximize();
  		Thread.sleep(1000);
  		driver.findElement(By.id("addEquipment")).click(); // Click 'Add' Equipment button
  		Thread.sleep(500);
  		driver.findElement(By.name("name")).sendKeys("");
  		Thread.sleep(500);
  		WebElement savebtn = driver.findElement(By.id("saveEquipment"));
  		savebtn.click();
		String expectedMSG = sheet.getRow(6).getCell(5).getStringCellValue(); // get expected value from excel
		String actualMSG = null;
		Thread.sleep(1000);
		if(driver.findElements(By.className("notify-msg")).size()!=0)
		{
			actualMSG = driver.findElement(By.className("notify-msg")).getText();
			String className = this.getClass().getName(); // get current class name - for screenshot
			String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
			Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
			driver.findElement(By.className("custom-notify-close")).click();
		}
		XSSFCell actualMSGprint = sheet.getRow(6).getCell(6); //Print actual result in the excel cell
		actualMSGprint.setCellValue(actualMSG);
		
		if(expectedMSG.equalsIgnoreCase(actualMSG))
		{
			XSSFCell status = sheet.getRow(6).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell status = sheet.getRow(6).getCell(7);
			status.setCellValue("Fail"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
		Utils.writeTooutputFile(workbook); // write output to the workbook
		}
  	
  	
  	@Test (priority=2)
  	public void  nameDuplicate() throws IOException, WriteException, InterruptedException
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet =  workbook.getSheet("EquipmentTC");
		
  		String duplicate = sheet.getRow(7).getCell(4).getStringCellValue(); // get name from excel
  		WebElement name = driver.findElement(By.name("name"));
  		Thread.sleep(500);
  		name.clear();
  		Thread.sleep(500);
  		name.sendKeys(duplicate); // send data to input field
  		Thread.sleep(1000);
  		
  		//Data for other fields
  		driver.findElement(By.id("surfaceArea")).sendKeys("10000");
  		driver.findElement(By.id("minBatch")).sendKeys("100");
  		Select preferential = new Select(driver.findElement(By.id("preferentialTransferOption")));
  		preferential.selectByValue("2");
  		Select cleaningSOPNo = new Select(driver.findElement(By.id("cleaningSOPNo")));
  		cleaningSOPNo.selectByValue("1");
  		Select cleaningProcessType = new Select(driver.findElement(By.id("cleaningProcessType")));
  		cleaningProcessType.selectByValue("2");
  		//
  		
  		
  		WebElement savebtn = driver.findElement(By.id("saveEquipment"));
  		savebtn.click();
		
  		String expectedMSG = sheet.getRow(7).getCell(5).getStringCellValue(); // get expected value from excel
		String actualMSG = null;
		Thread.sleep(1000);
			if(driver.findElements(By.className("notify-msg")).size()!=0)
			{
				actualMSG = driver.findElement(By.className("notify-msg")).getText();
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				driver.findElement(By.className("custom-notify-close")).click();
			}
			XSSFCell actualMSGprint = sheet.getRow(7).getCell(6); //Print actual result in the excel cell
			actualMSGprint.setCellValue(actualMSG);
		
			if(expectedMSG.equalsIgnoreCase(actualMSG))
			{
				XSSFCell status = sheet.getRow(7).getCell(7);
				status.setCellValue("Pass"); //Print status in excel
				status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			}else
			{
				XSSFCell status = sheet.getRow(7).getCell(7);
				status.setCellValue("Fail"); //Print status in excel
				status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
			}
		Utils.writeTooutputFile(workbook); // write output to the workbook
		}
  	
  	
  	@Test (priority=3)
  	public void  nameMaxFieldLength() throws IOException, WriteException, InterruptedException
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
		
  		String maxLength = sheet.getRow(8).getCell(4).getStringCellValue(); // get name from excel
  		WebElement name = driver.findElement(By.name("name"));
  		Thread.sleep(500);
  		name.clear();
  		Thread.sleep(500);
  		name.sendKeys(maxLength); // send data to input field
  		Thread.sleep(2000);
		
		double expectedMSG = sheet.getRow(8).getCell(5).getNumericCellValue(); // get expected value from excel
		String actualMSG = driver.findElement(By.name("name")).getAttribute("value"); //get actual value from site
		double length = actualMSG.length();
		
		XSSFCell maxLength_actual_print = sheet.getRow(8).getCell(6); //Print actual result in the excel cell
		maxLength_actual_print.setCellValue(length);
		
		if(expectedMSG == length)
		{
			XSSFCell status = sheet.getRow(8).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell status = sheet.getRow(8).getCell(7);
			status.setCellValue("Fail"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
	  
		Utils.writeTooutputFile(workbook); // write output to the workbook
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
  		}
  	
  	
// Special char validation
  	
  	@Test (priority=4)
  	public void  nameSpecialChar() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
		
  		String maxLength = sheet.getRow(9).getCell(4).getStringCellValue(); // get name from excel
  		WebElement name = driver.findElement(By.name("name"));
  		Thread.sleep(500);
  		name.clear();
  		Thread.sleep(500);
  		name.sendKeys(maxLength); // send data to input field
  		Thread.sleep(2000);
		
		String expectedMSG =sheet.getRow(9).getCell(5).getStringCellValue(); // get expected value from excel
		String actualMSG = driver.findElement(By.name("name")).getAttribute("value");//get actual value from site
		
		System.out.println("Spl Char"+actualMSG);
		XSSFCell maxLength_actual_print = sheet.getRow(9).getCell(6); //Print actual result in the excel cell
		maxLength_actual_print.setCellValue(actualMSG);
		
		if(expectedMSG.equalsIgnoreCase(actualMSG))
		{
			XSSFCell status = sheet.getRow(9).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell status = sheet.getRow(9).getCell(7);
			status.setCellValue("Fail"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
		Utils.writeTooutputFile(workbook); // write output to the workbook
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
  		}
  	
  	
	@Test (priority=5)
  	public void  nameBeginingSpaceCheck() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
		
  		String data = sheet.getRow(10).getCell(4).getStringCellValue(); // get name from excel
  		WebElement name = driver.findElement(By.name("name"));
  		Thread.sleep(500);
  		name.clear();
  		Thread.sleep(500);
  		name.sendKeys(data); // send data to input field
  		Thread.sleep(2000);
		
		String expectedMSG =sheet.getRow(10).getCell(4).getStringCellValue(); // get expected value from excel
		Integer ExpectedResult = expectedMSG.length();
		String actualMSG = driver.findElement(By.name("name")).getAttribute("value"); //get actual value from site
		Integer ActualResult = actualMSG.length();
		
		System.out.println(ExpectedResult);
		System.out.println(ActualResult);
		if(!ExpectedResult.equals(ActualResult))
		{
			XSSFCell maxLength_actual_print = sheet.getRow(10).getCell(6); //Print actual result in the excel cell
			maxLength_actual_print.setCellValue("Space Not Accepted");
			XSSFCell status = sheet.getRow(10).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell maxLength_actual_print = sheet.getRow(10).getCell(6); //Print actual result in the excel cell
			maxLength_actual_print.setCellValue("Space Accepted");
			XSSFCell status = sheet.getRow(10).getCell(7);
			status.setCellValue("Fail"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
		Utils.writeTooutputFile(workbook); // write output to the workbook
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
  		}
	
	
	
}

