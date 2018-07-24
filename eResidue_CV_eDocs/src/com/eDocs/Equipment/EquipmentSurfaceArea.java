package com.eDocs.Equipment;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.NumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import jxl.write.WriteException;

public class EquipmentSurfaceArea {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	
  
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
		numericField.clear();
		numericField.sendKeys("123457");
	}
  	
	/*@Test (priority=5)
	public void  nameBeginingSpaceCheck() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
	{
		
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

