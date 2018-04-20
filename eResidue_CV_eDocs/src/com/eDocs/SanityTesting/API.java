package com.eDocs.SanityTesting;

import static org.junit.Assert.assertArrayEquals;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

public class API {
  
		
			private RepositoryParser parser;
			private WebDriver driver = Constant.driver;;
			public String password = "123456";
		
			//Datas for create API
			static String ActiveIngredientNameCREATE = "Test API";
			static String ActiveIDCREATE = "Active ID for create";
			static String SolubilityinWaterCREATE = "1";
			static String ChangeControlNumberCREATE  = "Change cntrl create 111";
			static String HBELValueCREATE  = "1.32";
			
			//Datas for Edit API
			static String ActiveIDEDIT = "Active ID for edit";
			static String SolubilityinWaterEDIT = "2";
			static String ChangeControlNumberEDIT  = "Change cntrl edit 222";
			static String HBELValueEDIT  = "1.40";
			static String EditComments = "Edit all the API details";
			
			//Delete Datas for API
			static String changeControlDELETE="Delete single API";
			
			//Multi Delete Data for API
			static String multiDeleteSearchData="Test API";
			
			
			
			/*@BeforeClass
			public void setUp() throws IOException  
			{
				driver = new FirefoxDriver();
				driver.get("http://192.168.1.111:8090");
				parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Product.properties");
			}
		
			@Test(priority=1)
			public void Login() throws InterruptedException
			{
				//Lets see how we can find the first name field
				WebElement username = driver.findElement(By.id("username"));
				WebElement password = driver.findElement(By.id("password"));
				username.sendKeys("admin");
				Thread.sleep(500);
				password.sendKeys("123456");
				Thread.sleep(500);
				driver.findElement(By.id("loginsubmit")).click();
				Thread.sleep(500);
				driver.get("http://192.168.1.111:8090/active-ingredients");
			}
				*/
			
			@Test(priority=23,invocationCount=2)
			public void CreateAPI() throws InterruptedException, SQLException, ClassNotFoundException, IOException
			{
				Thread.sleep(2000);
				driver.get("http://192.168.1.45:8092/active-ingredients");
				parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Product.properties");
				Thread.sleep(1000);
				driver.findElement(By.id("addApi")).click();
				Thread.sleep(1000);
				String Name = ActiveIngredientNameCREATE;
				WebElement APIName = driver.findElement(parser.getbjectLocator("ActiveIngredientName"));
				APIName.sendKeys(Name);
				Thread.sleep(500);
				
				driver.findElement(parser.getbjectLocator("ActiveID")).sendKeys(ActiveIDCREATE);
				Thread.sleep(500);
				WebElement HBEL = driver.findElement(parser.getbjectLocator("HBELTerm")); // Select ADE
				Select SelectHBEL = new Select(HBEL);
				SelectHBEL.selectByIndex(1);
				Thread.sleep(500);
				
				
				
				driver.findElement(By.id("RouteAdmin")).click();
				Thread.sleep(500);
				driver.findElement(By.id("RouteAdmin")).sendKeys(Keys.ENTER);
				Thread.sleep(500);
				driver.findElement(By.id("RouteAdmin")).click();
				Thread.sleep(500);
				driver.findElement(By.id("hbelValue1")).sendKeys(HBELValueCREATE); 
				Thread.sleep(500);
				
				
				
				driver.findElement(parser.getbjectLocator("SolubilityinWater")).sendKeys(SolubilityinWaterCREATE);
				Thread.sleep(500);
				
				driver.findElement(parser.getbjectLocator("APIChangeControlNumber")).sendKeys(ChangeControlNumberCREATE);
				Thread.sleep(500);
				
				WebElement submit = driver.findElement(parser.getbjectLocator("APIsubmit"));
				submit.click();
				Thread.sleep(500);
				
				
				
				//if duplicate equipment name
				if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+Name+"' already exists!"))
				{
					String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.className("custom-notify-close")).click();
				
				Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
				for(int k=5;k<1000;k++)
				{
					j.add(k);
				}
				
				Thread.sleep(500);
				if(getduplicatename.equals("Active Ingredient '"+Name+"' already exists!"))
				{
					for(Integer i:j)
					{
						driver.findElement(parser.getbjectLocator("ActiveIngredientName")).clear();
						driver.findElement(parser.getbjectLocator("ActiveIngredientName")).sendKeys(Name+i);
						Thread.sleep(500);
						driver.findElement(parser.getbjectLocator("APIsubmit")).click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+Name+i+"' already exists!"))
						{
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							System.out.println("Name duplicate: "+nameduplicate);
							driver.findElement(By.className("custom-notify-close")).click();
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+Name+i+"' already exists!"))
							{
								continue;
							}
						}
								System.out.println("Not duplicate so break the loop");
								break;
						}
					}
				}// closing if loop duplicate equipment
				
				
				Thread.sleep(1000);
				String createAPI = driver.findElement(By.className("notify-msg")).getText();
				Assert.assertEquals(createAPI,"Active Ingredient saved successfully");
				
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(500);
			} // closing create API method
			
			
			
			
			
			@Test(priority=24)
			public void EditAPI() throws InterruptedException, SQLException, ClassNotFoundException
			{
				Thread.sleep(1000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
				Thread.sleep(300);
				String getactiverID = driver.findElement(parser.getbjectLocator("ActiveID")).getAttribute("value"); //verify text presented in the edit
				Assert.assertEquals(getactiverID, ActiveIDCREATE);
				Thread.sleep(300);
				driver.findElement(parser.getbjectLocator("ActiveID")).clear();
				Thread.sleep(300);
				driver.findElement(parser.getbjectLocator("ActiveID")).sendKeys(ActiveIDEDIT);
				Thread.sleep(500);
				
				String getHBELvalue = driver.findElement(By.id("hbelValue1")).getAttribute("value");
				Thread.sleep(300);
				Assert.assertEquals(getHBELvalue, HBELValueCREATE); // verify HBEL value
				
				WebElement HBEL = driver.findElement(parser.getbjectLocator("HBELTerm")); // select PDE
				Select SelectHBEL = new Select(HBEL);
				SelectHBEL.selectByIndex(2);
				Thread.sleep(500);
				
				driver.findElement(By.id("RouteAdmin")).click();
				Thread.sleep(500);
				driver.findElement(By.id("RouteAdmin")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
				Thread.sleep(500);
				driver.findElement(By.id("RouteAdmin")).click();
				Thread.sleep(200);
				
				driver.findElement(By.id("hbelValue1")).sendKeys(HBELValueEDIT);
				Thread.sleep(500);
				
				String getsolubility = driver.findElement(parser.getbjectLocator("SolubilityinWater")).getAttribute("value");
				Thread.sleep(300);
				Assert.assertEquals(getsolubility, SolubilityinWaterCREATE);
				driver.findElement(parser.getbjectLocator("SolubilityinWater")).clear();
				Thread.sleep(300);
				driver.findElement(parser.getbjectLocator("SolubilityinWater")).sendKeys(SolubilityinWaterEDIT);
				Thread.sleep(500);
				
				String getchControl = driver.findElement(parser.getbjectLocator("APIChangeControlNumber")).getAttribute("value");
				Thread.sleep(300);
				Assert.assertEquals(getchControl, ChangeControlNumberCREATE);
				
				driver.findElement(parser.getbjectLocator("APIChangeControlNumber")).clear();
				Thread.sleep(500);
				driver.findElement(parser.getbjectLocator("APIChangeControlNumber")).sendKeys(ChangeControlNumberEDIT);
				Thread.sleep(500);
				
				WebElement submit = driver.findElement(parser.getbjectLocator("APIsubmit"));
				submit.click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("comments")).sendKeys(EditComments);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys(Keys.SHIFT,Keys.TAB,password);
				Thread.sleep(500);
				driver.findElement(By.id("ackSubmit")).click();
				
				Thread.sleep(1000);
				String EditAPI = driver.findElement(By.className("notify-msg")).getText();
				System.out.println(EditAPI);
				Assert.assertEquals(EditAPI,"Active Ingredient updated successfully");
				
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(500);
			}
			
			
			
			
			
			@Test(priority=25)
			public void SingleDeleteAPI() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				driver.findElement(By.xpath(".//*[@id='dropdownactionDoc']/li[3]/a")).click(); // Click edit equipment button
				Thread.sleep(1000);
				driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys(changeControlDELETE);
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete single equipment");
				Thread.sleep(500);
				driver.findElement(By.id("ackSubmit")).click();
				Thread.sleep(1000);
				String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
				Assert.assertEquals(deletemsg,"Active deleted successfully");
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			
			
			
			
			@Test(priority=26)
			public void MultiDeleteAPI() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("searchEquipment")).sendKeys(multiDeleteSearchData);
				Thread.sleep(1000);
				driver.findElement(By.id("example-select-all")).click();
				Thread.sleep(1000);
				driver.findElement(By.id("deleteSelectedActive")).click(); // multi delete
				Thread.sleep(500);
				driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys(changeControlDELETE);
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete single equipment");
				Thread.sleep(500);
				driver.findElement(By.id("ackSubmit")).click();
				Thread.sleep(1000);
				String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
				Assert.assertEquals(deletemsg,"Active deleted successfully");
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			
			
			@Test(priority=27)
			public void CreateAPIforProduct() throws InterruptedException, SQLException, ClassNotFoundException, IOException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("addApi")).click();
				Thread.sleep(1000);
				String Name = ActiveIngredientNameCREATE;
				WebElement APIName = driver.findElement(parser.getbjectLocator("ActiveIngredientName"));
				APIName.sendKeys(Name);
				Thread.sleep(500);
				
				driver.findElement(parser.getbjectLocator("ActiveID")).sendKeys(ActiveIDCREATE);
				Thread.sleep(500);
				WebElement HBEL = driver.findElement(parser.getbjectLocator("HBELTerm")); // Select ADE
				Select SelectHBEL = new Select(HBEL);
				SelectHBEL.selectByIndex(1);
				Thread.sleep(500);
				
				
				
				driver.findElement(By.id("RouteAdmin")).click();
				Thread.sleep(500);
				driver.findElement(By.id("RouteAdmin")).sendKeys(Keys.ENTER);
				Thread.sleep(500);
				driver.findElement(By.id("RouteAdmin")).click();
				Thread.sleep(500);
				driver.findElement(By.id("hbelValue1")).sendKeys(HBELValueCREATE); 
				Thread.sleep(500);
				
				
				
				driver.findElement(parser.getbjectLocator("SolubilityinWater")).sendKeys(SolubilityinWaterCREATE);
				Thread.sleep(500);
				
				driver.findElement(parser.getbjectLocator("APIChangeControlNumber")).sendKeys(ChangeControlNumberCREATE);
				Thread.sleep(500);
				
				WebElement submit = driver.findElement(parser.getbjectLocator("APIsubmit"));
				submit.click();
				Thread.sleep(500);
				
				
				
				//if duplicate equipment name
				if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+Name+"' already exists!"))
				{
					String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.className("custom-notify-close")).click();
				
				Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
				for(int k=5;k<1000;k++)
				{
					j.add(k);
				}
				
				Thread.sleep(500);
				if(getduplicatename.equals("Active Ingredient '"+Name+"' already exists!"))
				{
					for(Integer i:j)
					{
						driver.findElement(parser.getbjectLocator("ActiveIngredientName")).clear();
						driver.findElement(parser.getbjectLocator("ActiveIngredientName")).sendKeys(Name+i);
						Thread.sleep(500);
						driver.findElement(parser.getbjectLocator("APIsubmit")).click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+Name+i+"' already exists!"))
						{
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							System.out.println("Name duplicate: "+nameduplicate);
							driver.findElement(By.className("custom-notify-close")).click();
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+Name+i+"' already exists!"))
							{
								continue;
							}
						}
								System.out.println("Not duplicate so break the loop");
								break;
						}
					}
				}// closing if loop duplicate equipment
				
				
				Thread.sleep(1000);
				String createAPI = driver.findElement(By.className("notify-msg")).getText();
				Assert.assertEquals(createAPI,"Active Ingredient saved successfully");
				
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(500);
			} // closing create API method
			
			
			
				
			
			
			/*@Test(priority=6)
			public void ExportAPI() throws Exception
			{
				Utils.ExportPDF(driver);
			}*/
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
}

