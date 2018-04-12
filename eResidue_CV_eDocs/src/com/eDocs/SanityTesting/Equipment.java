package com.eDocs.SanityTesting;

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

public class Equipment {
  
				private RepositoryParser parser;
				private WebDriver driver = Constant.driver;
				public String password = "123456";
			
				@BeforeClass
				public void setUp() throws IOException  
				{
					//driver = new FirefoxDriver();
					//driver.get("http://192.168.1.111:8090");
					driver.get("http://192.168.1.45:8091");
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
					//driver.get("http://192.168.1.111:8090/equipment");
					driver.get("http://192.168.1.45:8091/equipment");
					Thread.sleep(500);
					//driver.get("http://192.168.1.45:8091/equipment-group");
				}
		
				/*@Test(priority=2,invocationCount=2)
				public void CreateEquipment() throws InterruptedException, SQLException, ClassNotFoundException
				{
					//driver.navigate().refresh();
					Thread.sleep(1000);
					driver.findElement(parser.getbjectLocator("CreateEquipment")).click(); // Click create equipment button
					Thread.sleep(1000);
					String equipmentName = "Test Equipment";
					WebElement eqName = driver.findElement(parser.getbjectLocator("EquipmentName")); //Equipment Name field
					eqName.sendKeys(equipmentName);
					Thread.sleep(500);
					WebElement locationdropdown = driver.findElement(parser.getbjectLocator("EquipmentLocation"));
					Select location = new Select(locationdropdown);
					location.selectByIndex(1);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Model")).sendKeys("Test equipment Model");
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).sendKeys("Test Equipment Serial No");
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Manufacturer")).sendKeys("Test Equipment manufacturer");
					Thread.sleep(500);
					//driver.findElement(parser.getbjectLocator("SurfaceArea")).sendKeys("10000");
					driver.findElement(By.id("surfaceArea")).sendKeys("10000");
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys("Test Surface Data Source");
					Thread.sleep(500);
					
					WebElement preferentialTransfer = driver.findElement(parser.getbjectLocator("CanPreferentialTransferofResidueOccurwiththisEquipment?"));
					Select preferentialTransferType = new Select(preferentialTransfer);
					preferentialTransferType.selectByIndex(2);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys("50");
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("QualificationDocumentId")).sendKeys("QD 111");
					Thread.sleep(500);
					WebElement SOP = driver.findElement(parser.getbjectLocator("CleaningSOPNo"));
					Select CleaningSOP = new Select(SOP);
					CleaningSOP.selectByIndex(1);
					Thread.sleep(500);
					WebElement processType = driver.findElement(parser.getbjectLocator("CleaningProcessType"));
					Select CleaningProcessType = new Select(processType);
					CleaningProcessType.selectByIndex(1);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).sendKeys("Test Data");
					Thread.sleep(1000);
					//driver.findElement(parser.getbjectLocator("NextButton")).click();
					driver.findElement(By.id("saveEquipment")).click();
					Thread.sleep(500);
					
					
					if(driver.findElements(By.className("notify-msg")).size()!=0)
					{
						String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
						driver.findElement(By.className("custom-notify-close")).click();
					
					Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
					for(int k=25;k<1000;k++)
					{
						j.add(k);
					}
					
					Thread.sleep(500);
					if(getduplicatename.equals("Equipment '"+equipmentName+"' already exists!"))
					{
						for(Integer i:j)
						{
						eqName.clear();
						eqName.sendKeys(equipmentName+i);
						Thread.sleep(500);
						driver.findElement(By.id("saveEquipment")).click(); // Click calculation submit button
						Thread.sleep(500);
						try
						{
							//if(driver.findElement(By.xpath("html/body/div[18]/div/span"))!=null)
							//System.out.println("Size----->"+driver.findElements(By.id("saveEquipment")).size());
							if(driver.findElements(By.id("saveEquipment")).size()!=0)
							{
								String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
								System.out.println("Name duplicate: "+nameduplicate);
								driver.findElement(By.className("custom-notify-close")).click();
								if(nameduplicate.equals("Equipment '"+equipmentName+i+"' already exists!"))
								{
									continue;
								}
							}
						}
						
							catch(Exception e)
							{
									System.out.println("Not duplicate so break the loop");
									break;
							}
						
							}
						}
					}	
					
					//Location Assessment
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AddLocation")).click();
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("No")).click();
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Name")).sendKeys("Location1");
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Nextbutton")).click(); // submit location assessment
					
					
					//Do you want to upload images?
					WebElement uploadsamplingimage = driver.findElement(parser.getbjectLocator("Doyouwanttouploadimages?"));
					Select YesorNo = new Select(uploadsamplingimage);
					YesorNo.selectByIndex(2); // select No option
					
					//click add location pin
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AddLocationButton")).click(); // submit location assessment
					//MOC Selection
					WebElement MOCSelection = driver.findElement(parser.getbjectLocator("Moc"));
					Select SelectMOC = new Select(MOCSelection);
					SelectMOC.selectByIndex(1); 
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='marker-num1']/td[4]/span/span[1]/span/ul")).click();
					//Thread.sleep(1500);
					//driver.findElement(By.xpath(".//*[@id='select2-mocy1-result-6k82-2']")).click();
					
					//Sampling Selection
					//WebElement SamplingSelection = driver.findElement(By.xpath(".//*[@id='marker-num1']/td[4]/span/span[1]/span/ul"));
					//Select Selectsampling = new Select(SamplingSelection);
					//Selectsampling.selectByVisibleText("Rinse");
					
					Thread.sleep(1000);
					WebElement samplingbutton = driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")); //submitEquipmentSamplingDetails
					if(samplingbutton.getText().equals("Submit"))
					{
						System.out.println("No Custom loop");
						driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")).click();
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
					Thread.sleep(500);
					String createEquipment = driver.findElement(By.className("notify-msg")).getText();
					Assert.assertEquals(createEquipment,"Equipment saved successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(500);
					
				} // closing Create Equipment method
			
				
				
				
				@Test(priority=3)
				public void EditEquipment() throws InterruptedException, SQLException, ClassNotFoundException
				{
					//Thread.sleep(31000);
					Thread.sleep(1000);
					//driver.findElement(parser.getbjectLocator("EquipmentAction")).click(); // Click action icon
					driver.findElement(By.id("dLabel")).click();
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[10]/div/ul/li[3]/a")).click();
					driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
					Thread.sleep(500);
					WebElement locationdropdown = driver.findElement(parser.getbjectLocator("EquipmentLocation"));
					Select location = new Select(locationdropdown);
					location.selectByIndex(1);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Model")).clear();
					driver.findElement(parser.getbjectLocator("Model")).sendKeys("Edit Test equipment Model");
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).clear();
					driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).sendKeys("Edit Test Equipment Serial No");
					Thread.sleep(300);
					driver.findElement(parser.getbjectLocator("Manufacturer")).clear();
					driver.findElement(parser.getbjectLocator("Manufacturer")).sendKeys("Edit Test Equipment manufacturer");
					Thread.sleep(500);
					//driver.findElement(parser.getbjectLocator("SurfaceArea")).sendKeys("10000");
					driver.findElement(By.id("surfaceArea")).clear();
					driver.findElement(By.id("surfaceArea")).sendKeys("10000");
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).clear();
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys("Edit Test Surface Data Source");
					Thread.sleep(500);
					
					WebElement preferentialTransfer = driver.findElement(parser.getbjectLocator("CanPreferentialTransferofResidueOccurwiththisEquipment?"));
					Select preferentialTransferType = new Select(preferentialTransfer);
					preferentialTransferType.selectByIndex(2);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).clear();
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys("30");
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("QualificationDocumentId")).clear();
					driver.findElement(parser.getbjectLocator("QualificationDocumentId")).sendKeys("Edit QD 111");
					Thread.sleep(500);
					WebElement SOP = driver.findElement(parser.getbjectLocator("CleaningSOPNo"));
					Select CleaningSOP = new Select(SOP);
					CleaningSOP.selectByIndex(1);
					Thread.sleep(500);
					WebElement processType = driver.findElement(parser.getbjectLocator("CleaningProcessType"));
					Select CleaningProcessType = new Select(processType);
					CleaningProcessType.selectByIndex(3);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).clear();
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).sendKeys("Edit Test Data");
					Thread.sleep(1000);
					//driver.findElement(parser.getbjectLocator("NextButton")).click();
					driver.findElement(By.id("saveEquipment")).click();
					Thread.sleep(500);
					
					
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Nextbutton")).click(); // submit location assessment
					
					
					Thread.sleep(1000);
					WebElement samplingbutton = driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")); //submitEquipmentSamplingDetails
					
					if(samplingbutton.getText().equals("Submit"))
					{
						System.out.println("No Custom loop");
						driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")).click();
						Thread.sleep(500);
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
								Thread.sleep(500);
								driver.findElement(By.id("comments")).click();
								Thread.sleep(500);
								driver.findElement(By.id("comments")).sendKeys(Keys.SHIFT,Keys.TAB,password);
								Thread.sleep(500);
								driver.findElement(By.id("ackSubmit")).click();
						
					}
					
					String EditEquipment = driver.findElement(By.className("notify-msg")).getText(); 
					Assert.assertEquals(EditEquipment,"Equipment saved successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(500);
				} // closing Create Equipment method
				
				
				
				
				@Test(priority=4)
				public void SingleDeleteEquipment() throws InterruptedException, SQLException, ClassNotFoundException
				{
					Thread.sleep(1000);
					//driver.findElement(parser.getbjectLocator("EquipmentAction")).click(); // Click action icon
					driver.findElement(By.id("dLabel")).click();
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[10]/div/ul/li[4]/a")).click(); // click delete button
					driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
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
					Assert.assertEquals(deletemsg,"Equipment deleted successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(500);
				}
				
				@Test(priority=5)
				public void MultiDeleteEquipment() throws Exception
				{
					Thread.sleep(500);
					driver.findElement(By.id("searchEquipment")).sendKeys("test Equipment");
					Thread.sleep(500);
					driver.findElement(By.id("example-select-all")).click();
					Thread.sleep(500);
					driver.findElement(By.id("deleteSelectedEquipment")).click(); // multi delete
					Thread.sleep(500);
					driver.findElement(By.id("deleteSelectedEquipment")).sendKeys(Keys.ENTER);
					driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
					Thread.sleep(500);
					driver.findElement(By.id("ackChangeControlNo")).sendKeys("111");
					Thread.sleep(500);
					driver.findElement(By.id("ackChangeControlNo")).sendKeys(Keys.TAB +password);
					Thread.sleep(500);
					driver.findElement(By.id("comments")).sendKeys("Delete Multi equipment");
					Thread.sleep(500);
					driver.findElement(By.id("ackSubmit")).click();
					Thread.sleep(1000);
					String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
					Assert.assertEquals(deletemsg,"Equipment deleted successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(500);
				}
				
				
				
				
				@Test(priority=6)
				public void CreateEquipment1() throws InterruptedException, SQLException, ClassNotFoundException
				{
					Thread.sleep(1000);
					driver.findElement(parser.getbjectLocator("CreateEquipment")).click(); // Click create equipment button
					Thread.sleep(1000);
					String equipmentName = "Test Equipment";
					WebElement eqName = driver.findElement(parser.getbjectLocator("EquipmentName")); //Equipment Name field
					eqName.sendKeys(equipmentName);
					Thread.sleep(500);
					WebElement locationdropdown = driver.findElement(parser.getbjectLocator("EquipmentLocation"));
					Select location = new Select(locationdropdown);
					location.selectByIndex(1);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Model")).sendKeys("Test equipment Model");
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).sendKeys("Test Equipment Serial No");
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Manufacturer")).sendKeys("Test Equipment manufacturer");
					Thread.sleep(500);
					//driver.findElement(parser.getbjectLocator("SurfaceArea")).sendKeys("10000");
					driver.findElement(By.id("surfaceArea")).sendKeys("10000");
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys("Test Surface Data Source");
					Thread.sleep(500);
					
					WebElement preferentialTransfer = driver.findElement(parser.getbjectLocator("CanPreferentialTransferofResidueOccurwiththisEquipment?"));
					Select preferentialTransferType = new Select(preferentialTransfer);
					preferentialTransferType.selectByIndex(2);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys("50");
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("QualificationDocumentId")).sendKeys("QD 111");
					Thread.sleep(500);
					WebElement SOP = driver.findElement(parser.getbjectLocator("CleaningSOPNo"));
					Select CleaningSOP = new Select(SOP);
					CleaningSOP.selectByIndex(1);
					Thread.sleep(500);
					WebElement processType = driver.findElement(parser.getbjectLocator("CleaningProcessType"));
					Select CleaningProcessType = new Select(processType);
					CleaningProcessType.selectByIndex(1);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).sendKeys("Test Data");
					Thread.sleep(1000);
					//driver.findElement(parser.getbjectLocator("NextButton")).click();
					driver.findElement(By.id("saveEquipment")).click();
					Thread.sleep(500);
					
					
					if(driver.findElements(By.className("notify-msg")).size()!=0)
					{
						String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
						driver.findElement(By.className("custom-notify-close")).click();
					
					Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
					for(int k=25;k<1000;k++)
					{
						j.add(k);
					}
					
					Thread.sleep(500);
					if(getduplicatename.equals("Equipment '"+equipmentName+"' already exists!"))
					{
						for(Integer i:j)
						{
						eqName.clear();
						eqName.sendKeys(equipmentName+i);
						Thread.sleep(500);
						driver.findElement(By.id("saveEquipment")).click(); // Click calculation submit button
						Thread.sleep(500);
						try
						{
							//if(driver.findElement(By.xpath("html/body/div[18]/div/span"))!=null)
							//System.out.println("Size----->"+driver.findElements(By.id("saveEquipment")).size());
							if(driver.findElements(By.id("saveEquipment")).size()!=0)
							{
								String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
								System.out.println("Name duplicate: "+nameduplicate);
								driver.findElement(By.className("custom-notify-close")).click();
								if(nameduplicate.equals("Equipment '"+equipmentName+i+"' already exists!"))
								{
									continue;
								}
							}
						}
						
							catch(Exception e)
							{
									System.out.println("Not duplicate so break the loop");
									break;
							}
						
							}
						}
					}	
					
					//Location Assessment
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AddLocation")).click();
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("No")).click();
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Name")).sendKeys("Location1");
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Nextbutton")).click(); // submit location assessment
					
					
					//Do you want to upload images?
					WebElement uploadsamplingimage = driver.findElement(parser.getbjectLocator("Doyouwanttouploadimages?"));
					Select YesorNo = new Select(uploadsamplingimage);
					YesorNo.selectByIndex(2); // select No option
					
					//click add location pin
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AddLocationButton")).click(); // submit location assessment
					//MOC Selection
					WebElement MOCSelection = driver.findElement(parser.getbjectLocator("Moc"));
					Select SelectMOC = new Select(MOCSelection);
					SelectMOC.selectByIndex(1); 
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='marker-num1']/td[4]/span/span[1]/span/ul")).click();
					//Thread.sleep(1500);
					//driver.findElement(By.xpath(".//*[@id='select2-mocy1-result-6k82-2']")).click();
					
					//Sampling Selection
					//WebElement SamplingSelection = driver.findElement(By.xpath(".//*[@id='marker-num1']/td[4]/span/span[1]/span/ul"));
					//Select Selectsampling = new Select(SamplingSelection);
					//Selectsampling.selectByVisibleText("Rinse");
					
					Thread.sleep(1000);
					WebElement samplingbutton = driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")); //submitEquipmentSamplingDetails
					if(samplingbutton.getText().equals("Submit"))
					{
						System.out.println("No Custom loop");
						driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")).click();
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
					Thread.sleep(500);
					String createEquipment = driver.findElement(By.className("notify-msg")).getText();
					Assert.assertEquals(createEquipment,"Equipment saved successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(500);
				} // closing Create Equipment method
				*/
				
				
				
				
				@Test(priority=7,invocationCount=6)
				public void CopyEquipment() throws InterruptedException, SQLException, ClassNotFoundException
				{
					//driver.navigate().refresh();
					Thread.sleep(1000);
					//driver.findElement(parser.getbjectLocator("EquipmentAction")).click(); // Click action icon
					driver.findElement(By.id("dLabel")).click();
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[10]/div/ul/li[2]/a")).click(); // click delete button
					driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
					Thread.sleep(500);
					
					String equipmentName = "Test Equipment";
					WebElement eqName = driver.findElement(By.id("nameForCopy")); //Equipment copy Name field
					eqName.sendKeys(equipmentName);
					
					Thread.sleep(500);
					driver.findElement(By.id("copySubmit")).click();
					Thread.sleep(500);
					
					
					if(driver.findElements(By.id("copySubmit")).size()!=0)
					{
						String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
						driver.findElement(By.className("custom-notify-close")).click();
					
					Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
					for(int k=25;k<1000;k++)
					{
						j.add(k);
					}
					
					Thread.sleep(500);
					if(getduplicatename.equals("Equipment '"+equipmentName+"' already exists!"))
					{
						System.out.println("loop");
						for(Integer i:j)
						{
							driver.findElement(By.id("nameForCopy")).clear();
							driver.findElement(By.id("nameForCopy")).sendKeys(equipmentName+i);
							Thread.sleep(500);
							driver.findElement(By.id("copySubmit")).click();
							Thread.sleep(500);
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+i+"' already exists!"))
							{
								String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
								System.out.println("Name duplicate: "+nameduplicate);
								driver.findElement(By.className("custom-notify-close")).click();
								if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+i+"' already exists!"))
								{
									continue;
								}
							}
									System.out.println("Not duplicate so break the loop");
									break;
							}
						}
					} 
					
					
					String createEquipmentTrain = driver.findElement(By.className("notify-msg")).getText();
					System.out.println(createEquipmentTrain);
					Assert.assertEquals(createEquipmentTrain,"Equipment copied successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(500);
				}
				
				/*private static String downloadPath = "D:\\seleniumdownloads";
				@Test(priority=6)
				public void MultiDeleteEquipment() throws InterruptedException, SQLException, ClassNotFoundException
				{
					Thread.sleep(500);
					driver.findElement(By.id("export-file")).click();
					Thread.sleep(2000);
					String exportMSG = driver.findElement(By.xpath("html/body/div[15]/div/span")).getText();
					Assert.assertEquals(exportMSG,"PDF downloaded successfully");
				}
				*/
				
				
				
				
				
				
				
				
				
				
				/*@AfterClass
				public void tearDown()
				{
					driver.quit();
				}*/
				
	
	
	
}
