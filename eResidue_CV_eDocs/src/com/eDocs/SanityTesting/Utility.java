package com.eDocs.SanityTesting;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
import org.sqlite.util.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

public class Utility {
  
	
	
			
			private RepositoryParser parser;
			//private WebDriver driver = Constant.driver;
			private WebDriver driver;
			public String password = "123456";
		
			//Datas for create User
			static String utilityNameCREATE = "SeleniumUtility";
			static int locationNameCREATE = 1;
			static String sopNumberCREATE = "CIP";
			static String qualificationDocCREATE  = "CIP";
			static String dateofLastQuaCREATE  = "04/04/1989";
			static String changeControlCREATE  = "CreateUtil 111";
			
			//Datas for Edit User
			static int locationNameEDIT = 1;
			static String sopNumberEDIT = "COP";
			static String qualificationDocEDIT  = "COP";
			static String dateofLastQuaEDIT  = "05/04/1989";
			
			//Multi Delete Data for user
			static String multiDeleteSearchData="SeleniumUtility";
			
			
			
			@BeforeClass
			public void setUp() throws IOException  
			{
				
				//Create FireFox Profile object
				FirefoxProfile profile = new FirefoxProfile();
		 
				//Set Location to store files after downloading.
				profile.setPreference("browser.download.dir", "D:\\WebDriverDownloads");
				profile.setPreference("browser.download.folderList", 2);
		 
				//Set Preference to not show file download confirmation dialogue using MIME types Of different file extension types.
				profile.setPreference("browser.helperApps.neverAsk.saveToDisk", 
				    "application/pdf;"); 
		 
				profile.setPreference( "browser.download.manager.showWhenStarting", false );
				profile.setPreference( "pdfjs.disabled", true );
		 
				//Pass FProfile parameter In webdriver to use preferences to download file.
				 FirefoxOptions options = new FirefoxOptions();
				    options.setProfile(profile);
				driver = new FirefoxDriver(options);  
				driver.get("http://192.168.1.45:8092");
				parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\utility.properties");
			}
		
			@Test(priority=1)
			public void Login() throws InterruptedException
			{
				//Lets see how we can find the first name field
				WebElement username = driver.findElement(By.id("username"));
				WebElement password = driver.findElement(By.id("password"));
				username.sendKeys("thiyagu1");
				Thread.sleep(500);
				password.sendKeys("123456");
				Thread.sleep(500);
				driver.findElement(By.id("loginsubmit")).click();
				Thread.sleep(500);
				driver.get("http://192.168.1.45:8092/utility");
			}
				
			
			
			
			@Test(priority=2,invocationCount=2)
			public void CreateUtility() throws InterruptedException, SQLException, ClassNotFoundException, IOException
			{
				Thread.sleep(2000);
				//driver.get("http://192.168.1.45:8092/utility");
				///parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\utility.properties");
				Thread.sleep(1000);
				driver.findElement(By.id("addUtility")).click();
				Thread.sleep(1000);
				String Name = utilityNameCREATE;
				WebElement userName = driver.findElement(parser.getbjectLocator("UtilityName"));
				userName.sendKeys(Name);
				Thread.sleep(500);
				
				//Location Name
				WebElement UtilityLocationName  = driver.findElement(parser.getbjectLocator("UtilityLocationName"));
				Select SelecttoUtilityLocationName  = new Select(UtilityLocationName);
				SelecttoUtilityLocationName.selectByIndex(locationNameCREATE);
				Thread.sleep(500);
				
				//SOP Number 
				WebElement UtilitySOPNumber  = driver.findElement(parser.getbjectLocator("UtilitySOPNumber"));
				Select SelectUtilitySOPNumber  = new Select(UtilitySOPNumber);
				SelectUtilitySOPNumber.selectByVisibleText(sopNumberCREATE);
				Thread.sleep(500);
				
				//Qualification Documents 	 
				WebElement QualificationDocuments  = driver.findElement(parser.getbjectLocator("QualificationDocuments"));
				Select SelectQualificationDocuments  = new Select(QualificationDocuments);
				SelectQualificationDocuments.selectByVisibleText(qualificationDocCREATE);
				Thread.sleep(500);
				
				driver.findElement(parser.getbjectLocator("DateofLastQualification")).sendKeys(dateofLastQuaCREATE);
				Thread.sleep(500);
				
				WebElement submit = driver.findElement(parser.getbjectLocator("UtilitySubmit"));
				submit.click();
				Thread.sleep(1000);
				
				
				//if duplicate equipment name
				if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Utility '"+Name+"' already exists!"))
				{
					String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.className("custom-notify-close")).click();
				
				Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
				for(int k=1;k<1000;k++)
				{
					j.add(k);
				}
				
				Thread.sleep(500);
				if(getduplicatename.equalsIgnoreCase("Utility '"+Name+"' already exists!"))
				{
					for(Integer i:j)
					{
						driver.findElement(parser.getbjectLocator("UtilityName")).clear();
						driver.findElement(parser.getbjectLocator("UtilityName")).sendKeys(Name+i);
						Thread.sleep(500);
						driver.findElement(parser.getbjectLocator("UtilitySubmit")).click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Utility '"+Name+i+"' already exists!"))
						{
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							System.out.println("Name duplicate: "+nameduplicate);
							driver.findElement(By.className("custom-notify-close")).click();
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Utility '"+Name+i+"' already exists!"))
							{
								continue;
							}
						}
								System.out.println("Not duplicate so break the loop");
								break;
						}
					}
				}// closing if loop duplicate equipment
				
				
				/*//custom loop
				WebElement samplingbutton = driver.findElement(parser.getbjectLocator("UtilitySubmit")); //submitEquipmentSamplingDetails
				Thread.sleep(500);
				if(samplingbutton.getText().equalsIgnoreCase("Submit"))
				{
					System.out.println("No Custom loop");
					driver.findElement(parser.getbjectLocator("TrainSubmitbutton")).click();
					Thread.sleep(500);
					
				}else
				{
					samplingbutton.click();
					System.out.println("Custom loop");
					Thread.sleep(1000);
					for(int i=0;i<6;i++)
					{
						System.out.println("i--->"+i);
						String custom ="customFieldInput_";
						Thread.sleep(500);
						if(driver.findElements(By.id(custom+i)).size()!=0)
						{
							Thread.sleep(1000);
							if(driver.findElement(By.id(custom+i)).getAttribute("type").equals("text"))
							{
								System.out.println("Text bx");
								Thread.sleep(500);
								driver.findElement(By.id(custom+i)).sendKeys("Test");
							}
							if(driver.findElement(By.id(custom+i)).getAttribute("type").equals("select-one"))
							{
								System.out.println("DropDown");
								Thread.sleep(500);
								WebElement select = driver.findElement(By.id(custom+i));
								Select selectcustom = new Select(select);
								selectcustom.selectByIndex(1); 
							}
						}
					}
							//click save button in custom fields
							driver.findElement(By.id("saveCustomDetails")).click();
							
				}*/
				
				Thread.sleep(2000);
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,"Utility saved successfully");
				
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(500);
			} // closing create User method
			
	
			/*
			@Test(priority=3)
			public void EditUtility() throws InterruptedException, SQLException, ClassNotFoundException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
				driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
				//Thread.sleep(300);
				
				//Location Name
				Thread.sleep(500);
				WebElement getlocationName = driver.findElement(parser.getbjectLocator("UtilityLocationName"));
				Select Selectlocation  = new Select(getlocationName);
				WebElement locationoption = Selectlocation.getFirstSelectedOption();
				String selectedlocation = locationoption.getText();
				Assert.assertEquals(selectedlocation, locationNameCREATE);
				Selectlocation.selectByIndex(locationNameEDIT);
				Thread.sleep(500);
				
				//SOP Number
				WebElement SOPNo = driver.findElement(parser.getbjectLocator("UtilitySOPNumber"));
				Select Selectsop  = new Select(SOPNo);
				WebElement sopoption = Selectsop.getFirstSelectedOption();
				String selectedSOP = sopoption.getText();
				Assert.assertEquals(selectedSOP, sopNumberCREATE);
				Selectsop.selectByVisibleText(sopNumberEDIT);
				Thread.sleep(500);
				
				//Qualification Documents 
				WebElement QaDoc = driver.findElement(parser.getbjectLocator("QualificationDocuments"));
				Select SelectQaDoc  = new Select(QaDoc);
				WebElement QaDocoption = SelectQaDoc.getFirstSelectedOption();
				String selectedQADoc = QaDocoption.getText();
				Assert.assertEquals(selectedQADoc, qualificationDocCREATE);
				SelectQaDoc.selectByVisibleText(qualificationDocEDIT);
				Thread.sleep(500);
				
				String getDate = driver.findElement(parser.getbjectLocator("DateofLastQualification")).getAttribute("value");
				Assert.assertEquals(getDate, dateofLastQuaCREATE);
				driver.findElement(parser.getbjectLocator("DateofLastQualification")).clear();
				driver.findElement(parser.getbjectLocator("DateofLastQualification")).sendKeys(dateofLastQuaEDIT);
				Thread.sleep(500);
				
				WebElement submit = driver.findElement(parser.getbjectLocator("UtilitySubmit"));
				submit.click();
				Thread.sleep(1000);
				
				driver.findElement(By.name("comments")).sendKeys("Edit utility");
				Thread.sleep(500);
				driver.findElement(By.name("comments")).sendKeys(Keys.SHIFT,Keys.TAB,password);
				Thread.sleep(500);
				driver.findElement(By.id("ackSubmit")).click();
				
				Thread.sleep(2000);
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,"Utility updated successfully");
				
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(500);
			}
			
			
	
			
			
			
			@Test(priority=4)
			public void SingleDeleteUser() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
				driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
				
				Thread.sleep(1000);
				//driver.findElement(By.xpath(".//*[@id='dropdownactionDoc']/li[3]/a")).click(); // Click edit equipment button
				//Thread.sleep(1000);
				driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys("111");
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete single user");
				Thread.sleep(500);
				driver.findElement(By.id("ackSubmit")).click();
				Thread.sleep(2000);
				
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,"Utility deleted successfully");
				
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			
			
			
			
			@Test(priority=5)
			public void MultiDeleteUser() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("searchEquipment")).sendKeys(multiDeleteSearchData);
				Thread.sleep(1000);
				driver.findElement(By.id("example-select-all")).click();
				Thread.sleep(2000);
				driver.findElement(By.id("deleteSelectedUtility")).click(); // multi delete
				Thread.sleep(500);
				driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys("222");
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete multple user");
				Thread.sleep(500);
				driver.findElement(By.id("ackSubmit")).click();
				Thread.sleep(2000);
				
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,"Utility deleted successfully");
				
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			
			
	
			
			
			
			@Test(priority=6)
			public void ExportUtility() throws Exception
			{
				Utils.ExportPDF(driver);
			}
			
			
			
			@Test(priority=7)
			public void viewUtility() throws Exception
			{
				Thread.sleep(1000);
				driver.findElement(By.className("eqpNameTag")).click();
				Thread.sleep(1000);
				String name = driver.findElement(parser.getbjectLocator("UtilityName")).getText();
				System.out.println("Name: "+name);
				if(org.apache.commons.lang3.StringUtils.isEmpty(name)) {
					throw new Exception("Utility Name is Empty");
				}
				//Location Name
				Thread.sleep(500);
				WebElement getlocationName = driver.findElement(parser.getbjectLocator("UtilityLocationName"));
				Select Selectlocation  = new Select(getlocationName);
				WebElement locationoption = Selectlocation.getFirstSelectedOption();
				String selectedlocation = locationoption.getText();
				System.out.println("Location Name: "+selectedlocation);
				Thread.sleep(500);
				
				//SOP Number
				WebElement SOPNo = driver.findElement(parser.getbjectLocator("UtilitySOPNumber"));
				Select Selectsop  = new Select(SOPNo);
				WebElement sopoption = Selectsop.getFirstSelectedOption();
				String selectedSOP = sopoption.getText();
				System.out.println("SOP Number: "+selectedSOP);
				Assert.assertEquals(selectedSOP,sopNumberCREATE);
				Thread.sleep(500);
				
				//Qualification Documents 
				WebElement QaDoc = driver.findElement(parser.getbjectLocator("QualificationDocuments"));
				Select SelectQaDoc  = new Select(QaDoc);
				WebElement QaDocoption = SelectQaDoc.getFirstSelectedOption();
				String selectedQADoc = QaDocoption.getText();
				System.out.println("Qualification Documents : "+selectedQADoc);
				Assert.assertEquals(selectedQADoc,qualificationDocCREATE);
				Thread.sleep(500);
				
				String LastQuaDate = driver.findElement(parser.getbjectLocator("DateofLastQualification")).getText();
				System.out.println("Date of Last Qualification : "+LastQuaDate);
				if(org.apache.commons.lang3.StringUtils.isEmpty(LastQuaDate)) {
					throw new Exception("Date of Last Qualification field is Empty");
				}
				Assert.assertEquals(LastQuaDate,dateofLastQuaCREATE);
				Thread.sleep(500);
				driver.findElement(By.className("cancel-btn")).click();
			}
			
			*/
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
	
}
