package com.eDocs.Equipment;

import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.AlphaNumericValidation;
import com.eDocs.CommonValidation.NumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class EquipmentSurfaceArea {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	
	/*
	@BeforeClass
	public void setUp() throws IOException  
	{
		driver.get("http://192.168.1.111:8090");
		parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
	}

	@Test(priority=1)
	public void Login() throws InterruptedException
	{
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
	}*/
	
  
	@Test(priority=6)
	public void NumericEmptyTest() throws InterruptedException, WriteException, IOException
	{
		WebElement Submit = driver.findElement(By.id("saveEquipment"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericEmpty(Submit,15);
	}
	
	@Test(priority=7)
	public void NumericZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("surfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(numericField,16);
	}
	
	@Test(priority=8)
	public void NumericMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("surfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(numericField,17);
	}
	
	@Test(priority=9)
	public void numericFieldAlphabetsCheckTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("surfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(numericField,18);
	}
	
	@Test(priority=10)
	public void numericSpaceCheckTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("surfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(numericField,19);
	}
	
	@Test(priority=11)
	public void numericMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("surfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(numericField,20);
	}
  	
	/*@Test (priority=5)
	public void  nameBeginingSpaceCheck() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
	{
		
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

