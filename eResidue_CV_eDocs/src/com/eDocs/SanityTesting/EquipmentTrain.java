package com.eDocs.SanityTesting;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

public class EquipmentTrain {
  
	
			private RepositoryParser parser;
			private WebDriver driver = Constant.driver;
			public String password = "123456";
		
			@BeforeClass
			public void setUp() throws IOException  
			{
				driver = new FirefoxDriver();
				driver.get("http://192.168.1.111:8090");
				parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
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
				driver.get("http://192.168.1.111:8090/equipment-train");
			}
				
			
			@Test(priority=12,invocationCount=2)
			public void CreateEquipmentTrain() throws InterruptedException, SQLException, ClassNotFoundException, IOException
			{
				parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
				Thread.sleep(6500);
				driver.get("http://192.168.1.111:8090/equipment-train");
				Thread.sleep(1000);
				driver.findElement(parser.getbjectLocator("createTrain")).click(); // Click create equipment button
				Thread.sleep(1000);
				String trainName = "Test Equipment Train";
				driver.findElement(parser.getbjectLocator("EquipmentTrainName")).sendKeys(trainName);; //Equipment Name field
				//Name.sendKeys(trainName);
				Thread.sleep(500);
				
				
				WebElement IdentifuEquip = driver.findElement(By.className("select2-search__field"));
				IdentifuEquip.sendKeys("Test Equipment");
				Thread.sleep(300);
				IdentifuEquip.sendKeys(Keys.ENTER);
				Thread.sleep(300);
				String getselectedEquipName = driver.findElement(By.className("select2-selection__rendered")).getText().substring(1);
				IdentifuEquip.sendKeys("Test Equipment");
				Thread.sleep(500);
				IdentifuEquip.sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
		        Thread.sleep(500);
		        //driver.findElement(By.className("select2-selection__rendered")).getText().substring(1); //get total selectd equipments
		        
		       
				List<String> equipments = new ArrayList<>();
				equipments.add(getselectedEquipName);
				//equipments.add(driver.findElement(By.className("select2-selection__rendered")).getText().substring(1));
				//equipments.add(driver.findElement(By.cssSelector("ul.select2-selection__rendered>li:nth-child(1)")).getText().substring(1));
			
				System.out.println("equipments"+equipments);
				Thread.sleep(500);
				driver.findElement(parser.getbjectLocator("TrainChangeControlNo")).sendKeys("234");
				
				Thread.sleep(500);
				driver.findElement(By.id("saveEquipmentTrainDetails")).click();
				Thread.sleep(1000);
				
				//if duplicate equipment name
				if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Train '"+trainName+"' already exists!"))
				{
					String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.className("custom-notify-close")).click();
				
				Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
				for(int k=5;k<1000;k++)
				{
					j.add(k);
				}
				
				Thread.sleep(500);
				if(getduplicatename.equals("Train '"+trainName+"' already exists!"))
				{
					System.out.println("loop");
					for(Integer i:j)
					{
						driver.findElement(parser.getbjectLocator("EquipmentTrainName")).clear();
						driver.findElement(parser.getbjectLocator("EquipmentTrainName")).sendKeys(trainName+i);
						Thread.sleep(500);
						driver.findElement(By.id("saveEquipmentTrainDetails")).click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Train '"+trainName+i+"' already exists!"))
						{
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							System.out.println("Name duplicate: "+nameduplicate);
							driver.findElement(By.className("custom-notify-close")).click();
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Train '"+trainName+i+"' already exists!"))
							{
								continue;
							}
						}
								System.out.println("Not duplicate so break the loop");
								break;
						}
					}
				}// closing if loop duplicate equipment
				
				Thread.sleep(500);
				
				
				//if duplicate equipment set
				if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Duplicate equipment set") )
				{
						driver.findElement(By.className("custom-notify-close")).click();
						WebElement IdentifuEquipment = driver.findElement(By.className("select2-search__field"));
						IdentifuEquipment.sendKeys("Test Equipment");
						Thread.sleep(500);
						IdentifuEquipment.sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
						Thread.sleep(500);
						driver.findElement(By.id("saveEquipmentTrainDetails")).click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Duplicate equipment set"))
						{
							driver.findElement(By.className("custom-notify-close")).click();
							WebElement IdentifuDupliEquipment = driver.findElement(By.className("select2-search__field"));
							IdentifuDupliEquipment.sendKeys("Test Equipment");
							Thread.sleep(500);
							IdentifuDupliEquipment.sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
							Thread.sleep(500);
							driver.findElement(By.id("saveEquipmentTrainDetails")).click();
							Thread.sleep(500);
						}
					
				}	// closing if loop duplicate equipment set
				
				
				
				
				
				WebElement uploadimage = driver.findElement(parser.getbjectLocator("TrainDoyouwanttouploadimages?"));
				Select YesorNo = new Select(uploadimage);
				YesorNo.selectByIndex(2);
				
				//add location
				driver.findElement(parser.getbjectLocator("TrainAddLocationButton")).click();
				Thread.sleep(500);
				driver.findElement(parser.getbjectLocator("TrainLocationName")).sendKeys("location1");
				Thread.sleep(500);
				WebElement MOC = driver.findElement(parser.getbjectLocator("TrainMoc")); //MOC
				Select selectMOC = new Select(MOC);
				selectMOC.selectByIndex(1);
				Thread.sleep(500);
				driver.findElement(parser.getbjectLocator("TrainRationale")).sendKeys("Rationale");
				Thread.sleep(500);
				
				WebElement samplingbutton = driver.findElement(parser.getbjectLocator("TrainSubmitbutton")); //submitEquipmentSamplingDetails
				Thread.sleep(200);
				if(samplingbutton.getText().equals("Submit"))
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
							
				}
				Thread.sleep(1000);
				String createEquipmentTrain = driver.findElement(By.className("notify-msg")).getText();
				System.out.println(createEquipmentTrain);
				Assert.assertEquals(createEquipmentTrain,"Equipment train saved successfully");
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(600);
			} // closing Create Equipment Train method
		
	
			
			@Test(priority=13)
			public void EditEquipmentTrain() throws InterruptedException, SQLException, ClassNotFoundException, IOException
			{
				Thread.sleep(1000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[8]/div/ul/li[2]/a")).click(); // Click edit equipment button
				driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
				Thread.sleep(500);
				//driver.findElement(parser.getbjectLocator("TrainChangeControlNo")).clear();
				//driver.findElement(parser.getbjectLocator("TrainChangeControlNo")).sendKeys("234456");
				
				Thread.sleep(500);
				driver.findElement(By.id("saveEquipmentTrainDetails")).click();
				Thread.sleep(500);
				
				
				//WebElement uploadimage = driver.findElement(parser.getbjectLocator("TrainDoyouwanttouploadimages?"));
				//Select YesorNo = new Select(uploadimage);
				//YesorNo.selectByIndex(2);
				
				//add location
				Thread.sleep(500);
				driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input")).clear();
				driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input")).sendKeys("location1 edit");
				Thread.sleep(500);
				driver.findElement(By.xpath(".//*[@id='marker-num1']/td[4]/input")).clear();
				driver.findElement(By.xpath(".//*[@id='marker-num1']/td[4]/input")).sendKeys("Edit Rationale");
				Thread.sleep(500);
				
				WebElement samplingbutton = driver.findElement(parser.getbjectLocator("TrainSubmitbutton")); //submitEquipmentSamplingDetails
				Thread.sleep(200);
				if(samplingbutton.getText().equals("Submit"))
				{
					System.out.println("No Custom loop");
					driver.findElement(parser.getbjectLocator("TrainSubmitbutton")).click();
					Thread.sleep(1000);
					driver.findElement(By.id("comments")).click();
					Thread.sleep(500);
					driver.findElement(By.id("comments")).sendKeys(Keys.SHIFT,Keys.TAB,password);
					Thread.sleep(500);
					driver.findElement(By.id("ackSubmit")).click();
					
					
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
							Thread.sleep(1000);
							
							driver.findElement(By.id("comments")).click();
							Thread.sleep(500);
							driver.findElement(By.id("comments")).sendKeys(Keys.SHIFT,Keys.TAB,password);
							Thread.sleep(500);
							driver.findElement(By.id("ackSubmit")).click();
							
				}
				Thread.sleep(1000);
				String EditEquipmentTrain = driver.findElement(By.className("notify-msg")).getText();
				System.out.println(EditEquipmentTrain);
				Assert.assertEquals(EditEquipmentTrain,"Equipment train updated successfully");
				
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(600);
			} // closing Edit Equipment Train method
			
			
			
			
			@Test(priority=14)
			public void SingleDeleteEquipmentTrain() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.findElement(By.xpath(".//*[@id='dLabel']/i")).click();
				Thread.sleep(500);
				driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[8]/div/ul/li[3]/a")).click(); // Click edit equipment button
				Thread.sleep(1000);
				driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys("111");
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete single equipment");
				Thread.sleep(500);
				driver.findElement(By.id("ackSubmit")).click();
				Thread.sleep(1000);
				String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
				Assert.assertEquals(deletemsg,"Equipment train deleted Successfully!");
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			

			@Test(priority=15)
			public void MultiDeleteEquipmentTrain() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("searchEquipment")).sendKeys("Test Equipment");
				Thread.sleep(1000);
				driver.findElement(By.id("example-select-all")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath(".//*[@id='deleteSelectedEquipmentTrain']")).click(); // multi delete
				//driver.findElement(By.id("deleteSelectedEquipmentGroup")).sendKeys(Keys.ENTER); // multi delete
				Thread.sleep(500);
				driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys("111");
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete single equipment");
				Thread.sleep(500);
				driver.findElement(By.id("ackSubmit")).click();
				Thread.sleep(1000);
				String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
				Assert.assertEquals(deletemsg,"Equipment Train deleted successfully");
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
	
	
}
