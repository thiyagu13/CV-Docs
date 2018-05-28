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

public class EquipmentMinimumBatch {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	
  
	@Test(priority=21)
	public void NumericEmptyTest() throws InterruptedException, WriteException, IOException
	{
		// For min BAtch Empty check - Pref transer to be filled (Bcz previous field)
		//Select prefTransfer =  new Select(driver.findElement(By.id("preferentialTransferOption")));
		//prefTransfer.selectByValue("2"); // select no
		NumericValidation getnumericField = new NumericValidation();
		WebElement Submit = driver.findElement(By.id("saveEquipment"));
		getnumericField.NumericEmpty(Submit,31);
	}
	
	@Test(priority=22)
	public void NumericZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("minBatch"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(numericField,32);
	}
	
	@Test(priority=23)
	public void NumericMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("minBatch"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(numericField,33);
	}
	
	@Test(priority=24)
	public void numericFieldAlphabetsCheckTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("minBatch"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(numericField,34);
	}
	
	@Test(priority=25)
	public void numericSpaceCheckTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("minBatch"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(numericField,35);
	}
	
	@Test(priority=26)
	public void numericMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("minBatch"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(numericField,36);
	}
	
	
	
	
	
}

