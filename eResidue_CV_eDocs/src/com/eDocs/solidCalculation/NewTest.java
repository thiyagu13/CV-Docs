package com.eDocs.solidCalculation;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;



public class NewTest {
	
	private RepositoryParser parser ;
	private WebDriver driver;
	
	@BeforeClass
	public void setUp() throws IOException, InterruptedException
	{
		driver = new FirefoxDriver();
		parser = new RepositoryParser(Constant.equipment_Properties_Path);
		//parser = new RepositoryParser(Constant.product_Properties_Path);
		driver.get("http://localhost:8091/calculation/login");
		Thread.sleep(1000);
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("123456");
		Thread.sleep(3000);
		driver.findElement(By.id("loginsubmit")).click();
		Thread.sleep(1000);
		driver.get("http://localhost:8091/calculation/equipments");
	}
	
	
	@Test
	public void equipment() throws InterruptedException
	{
		//Lets see how we can find the first name field
		
		driver.findElement(By.id("addEquipment")).click();
		Thread.sleep(500);
		driver.findElement(parser.getbjectLocator("saveequipment")).click();
		Thread.sleep(500);
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}
	
	
}
