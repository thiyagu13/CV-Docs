package com.eDocs.Equipment;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class Equipment_Name {
	
	public static WebDriver driver = Constant.driver;
	
	
/*	
  @BeforeMethod
  public void geturl() throws InterruptedException 
  {
	  driver.get("http://localhost:8091/login"); //open site url
	
	  		// Login
			driver.findElement(By.id("username")).sendKeys("Admin");
			driver.findElement(By.id("password")).sendKeys("testing");
			Thread.sleep(3000);
			driver.findElement(By.id("loginsubmit")).click();
			
			if (driver.findElement(By.className("top-message")).getText().equalsIgnoreCase("Invalid credentials!")) 
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
			Thread.sleep(500);
  	}
  */
  
  	@Test (priority=1)
  	public void  name_empty() throws IOException, WriteException, InterruptedException
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH);
		XSSFSheet sheet = workbook.getSheetAt(2);
  		driver.get("http://localhost:8091/equipments");
  		//driver.manage().window().maximize();
  		Thread.sleep(500);
  		driver.findElement(By.id("addEquipment")).click(); // Click 'Add' Equipment button
  		Thread.sleep(500);
  		String emptycheck = sheet.getRow(6).getCell(4).getStringCellValue(); // get name from excel
  		WebElement name = driver.findElement(By.id("name"));
  		name.sendKeys(emptycheck);
  		name.clear();
  		Thread.sleep(500);
  		WebElement savebtn = driver.findElement(By.id("saveEquipment"));
  		savebtn.click();
		
		String emptyname_expec = sheet.getRow(6).getCell(5).getStringCellValue(); // get expected value from excel
		String emptyname_actual = driver.findElement(By.xpath("html/body/div[15]/div/span")).getText(); //get actual value from site
		
		XSSFCell emptyname_actual_print = sheet.getRow(6).getCell(6); //Print actual result in the excel cell
		emptyname_actual_print.setCellValue(emptyname_actual);
		
		if(emptyname_expec.equalsIgnoreCase(emptyname_actual))
		{
			XSSFCell emptyname_status = sheet.getRow(6).getCell(7);
			emptyname_status.setCellValue("Pass"); //Print status in excel
			emptyname_status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell emptyname_status = sheet.getRow(6).getCell(7);
			emptyname_status.setCellValue("Fail"); //Print status in excel
			emptyname_status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
		Utils.writeTooutputFile(workbook); // write output to the workbook
		
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
  		}
  	
  	
  	@Test (priority=2)
  	public void  name_duplicate() throws IOException, WriteException, InterruptedException
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheetAt(2);
		
  		String duplicate = sheet.getRow(7).getCell(4).getStringCellValue(); // get name from excel
  		WebElement name = driver.findElement(By.id("name"));
  		Thread.sleep(500);
  		name.clear();
  		Thread.sleep(500);
  		name.sendKeys(duplicate); // send data to input field
  		Thread.sleep(6000);
  		WebElement savebtn = driver.findElement(By.id("saveEquipment"));
  		savebtn.click();
		
		String duplicate_expec = sheet.getRow(7).getCell(5).getStringCellValue(); // get expected value from excel
		String duplicate_actual = driver.findElement(By.xpath("html/body/div[15]/div/span")).getText(); //get actual value from site
		
		XSSFCell duplicate_actual_print = sheet.getRow(7).getCell(6); //Print actual result in the excel cell
		duplicate_actual_print.setCellValue(duplicate_actual);
		
		if(duplicate_expec.equalsIgnoreCase(duplicate_actual))
		{
			XSSFCell duplicate_status = sheet.getRow(7).getCell(7);
			duplicate_status.setCellValue("Pass"); //Print status in excel
			duplicate_status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell duplicate_status = sheet.getRow(7).getCell(7);
			duplicate_status.setCellValue("Fail"); //Print status in excel
			duplicate_status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
		
		Utils.writeTooutputFile(workbook); // write output to the workbook
		
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
  		}
  	
  	
  	@Test (priority=3)
  	public void  name_maxLength() throws IOException, WriteException, InterruptedException
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheetAt(2);
		
  		String maxLength = sheet.getRow(8).getCell(4).getStringCellValue(); // get name from excel
  		WebElement name = driver.findElement(By.id("name"));
  		Thread.sleep(500);
  		name.clear();
  		Thread.sleep(500);
  		name.sendKeys(maxLength); // send data to input field
  		Thread.sleep(2000);
		
		String maxLength_expec =(String.valueOf(sheet.getRow(8).getCell(5).getNumericCellValue())); // get expected value from excel
		
		String maxLength_actual = driver.findElement(By.id("name")).getAttribute("maxlength"); //get actual value from site
		
		XSSFCell maxLength_actual_print = sheet.getRow(8).getCell(6); //Print actual result in the excel cell
		maxLength_actual_print.setCellValue(maxLength_actual);
		
		if(maxLength_expec.equalsIgnoreCase(maxLength_actual))
		{
			XSSFCell maxLength_status = sheet.getRow(8).getCell(7);
			maxLength_status.setCellValue("Pass"); //Print status in excel
			maxLength_status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell maxLength_status = sheet.getRow(8).getCell(7);
			maxLength_status.setCellValue("Fail"); //Print status in excel
			maxLength_status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
	  
		Utils.writeTooutputFile(workbook); // write output to the workbook
		
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
  		}
  	
  	
  	@Test (priority=4)
  	public void  name_mandatory() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheetAt(2);
		//mandatory symbol
		String mandatory_expec =sheet.getRow(9).getCell(5).getStringCellValue(); // get expected value from excel
		
		String mandatory_actual = driver.findElement(By.className("text-red")).getText(); //get actual value from site
		
		XSSFCell mandatory_actual_print = sheet.getRow(9).getCell(6); //Print actual result in the excel cell
		mandatory_actual_print.setCellValue(mandatory_actual);
		
		if(mandatory_expec.equalsIgnoreCase(mandatory_actual))
		{
			XSSFCell mandatory_status = sheet.getRow(9).getCell(7);
			mandatory_status.setCellValue("Pass"); //Print status in excel
			mandatory_status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell mandatory_status = sheet.getRow(9).getCell(7);
			mandatory_status.setCellValue("Fail"); //Print status in excel
			mandatory_status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
		
		//mandatory client side validation
		String mandatory_err = sheet.getRow(10).getCell(4).getStringCellValue(); // get name from excel
  		WebElement name = driver.findElement(By.id("name"));
  		Thread.sleep(500);
  		name.clear();
  		Thread.sleep(500);
  		name.sendKeys(mandatory_err); // send data to input field
  		Thread.sleep(2000);
  		name.clear();
  		Thread.sleep(1000);
  		name.sendKeys(Keys.BACK_SPACE);
  		
		String mandatory_expec_err =sheet.getRow(10).getCell(5).getStringCellValue(); // get expected value from excel
		String mandatory_actual_err = driver.findElement(By.id("name-error")).getText(); //get actual value from site
		
		XSSFCell mandatory_actual_err_print = sheet.getRow(10).getCell(6); //Print actual result in the excel cell
		mandatory_actual_err_print.setCellValue(mandatory_actual_err);
		
		if(mandatory_expec_err.equalsIgnoreCase(mandatory_actual_err))
		{
			XSSFCell mandatory_err_status = sheet.getRow(10).getCell(7);
			mandatory_err_status.setCellValue("Pass"); //Print status in excel
			mandatory_err_status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell mandatory_err_status = sheet.getRow(10).getCell(7);
			mandatory_err_status.setCellValue("Fail"); //Print status in excel
			mandatory_err_status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
		
		Utils.writeTooutputFile(workbook); // write output to the workbook
		
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
		
  		}
  	
  	
// Special char validation
  	
  	@Test (priority=5)
  	public void  name_specialChar() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheetAt(2);
		//mandatory client side validation
		String specialChar = sheet.getRow(11).getCell(4).getStringCellValue(); // get name from excel
  		WebElement name = driver.findElement(By.id("name"));
  		Thread.sleep(500);
  		name.clear();
  		Thread.sleep(500);
  		name.sendKeys(specialChar); // send data to input field
  		Thread.sleep(2000);
  		
		String specialChar_expec =sheet.getRow(11).getCell(5).getStringCellValue(); // get expected value from excel
		
		XSSFCell specialChar_actual_print = sheet.getRow(11).getCell(6); //Print actual result in the excel cell
		specialChar_actual_print.setCellValue(name.getAttribute("value"));
		System.out.println(name.getAttribute("value"));
		if(specialChar_expec.equalsIgnoreCase(name.getAttribute("value")))
		{
			XSSFCell specialChar_status = sheet.getRow(11).getCell(7);
			specialChar_status.setCellValue("Pass"); //Print status in excel
			specialChar_status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell specialChar_status = sheet.getRow(11).getCell(7);
			specialChar_status.setCellValue("Fail"); //Print status in excel
			specialChar_status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
		Utils.writeTooutputFile(workbook); // write output to the workbook
		
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
		
  		}
  	
  	
}

