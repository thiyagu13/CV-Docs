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
				static String EquipmentName ="Test Equipment";
				
				//Create Equipment Data's
				static String EquipmentModelCREATE ="Eq Create Model1";
				static String EquipmentSerialNoCREATE ="Eq Create 111";
				static String EquipmentManufacturerCREATE ="Eq Create Manu1";
				static String EquipmentSFCREATE ="10000";
				static String EquipmentSFDataSourceCREATE ="Eq Create Data Source";
				static String EquipmentMinBatchCREATE ="10";
				static String EquipmentQuaDocIDCREATE ="Eq Qua ID Create";
				static String EquipmentCleaningInfoCREATE ="Create Euipment";
				static String EquipmentPreferentialTransferCREATE ="Yes";
				static String EquipmentPreferentialTransferSFCREATE ="100";
				static String EquipmentPrimaryPackagingeOptionCREATE ="Yes";
				static String EquipmentSamplingLocationCREATE ="Chennai";
				
				
				

				//Edit Equipment Data's
				static String EquipmentModelEDIT ="Eq Edit Model";
				static String EquipmentSerialNoEDIT ="Eq Edit 222";
				static String EquipmentManufacturerEDIT ="Eq Edit Manu";
				static String EquipmentSFEDIT ="20000";
				static String EquipmentSFDataSourceEDIT ="Eq Edit Data Source";
				static String EquipmentMinBatchEDIT ="15";
				static String EquipmentQuaDocIDEDIT ="Eq Qua ID Edit";
				static String EquipmentCleaningInfoEDIT ="Edit Euipment";
				static String EquipmentPreferentialTransferEDIT ="No";
				
				
				
				
				
			/*	@BeforeClass
				public void setUp() throws IOException  
				{
					//driver = new FirefoxDriver();
					//driver.get("http://192.168.1.111:8090");
					driver.get("http://192.168.1.45:8092");
					parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
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
					//driver.get("http://192.168.1.111:8090/equipment");
					driver.get("http://192.168.1.45:8092/equipment");
					Thread.sleep(500);
					//driver.get("http://192.168.1.45:8091/equipment-group");
				}*/
		
				@Test(priority=9,invocationCount=2)
				public void CreateEquipment() throws InterruptedException, SQLException, ClassNotFoundException, IOException
				{
					parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
					Thread.sleep(5000);
					driver.get("http://192.168.1.45:8092/equipment");
					//driver.navigate().refresh();
					Thread.sleep(2000);
					driver.findElement(parser.getbjectLocator("CreateEquipment")).click(); // Click create equipment button
					Thread.sleep(1000);
					String equipmentName = EquipmentName;
					WebElement eqName = driver.findElement(parser.getbjectLocator("EquipmentName")); //Equipment Name field
					eqName.sendKeys(equipmentName);
					Thread.sleep(500);
					WebElement locationdropdown = driver.findElement(parser.getbjectLocator("EquipmentLocation"));
					Select location = new Select(locationdropdown);
					location.selectByIndex(1);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Model")).sendKeys(EquipmentModelCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).sendKeys(EquipmentSerialNoCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Manufacturer")).sendKeys(EquipmentManufacturerCREATE);
					Thread.sleep(500);
					//driver.findElement(parser.getbjectLocator("SurfaceArea")).sendKeys("10000");
					driver.findElement(By.id("surfaceArea")).sendKeys(EquipmentSFCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys(EquipmentSFDataSourceCREATE);
					Thread.sleep(500);
					
					WebElement preferentialTransfer = driver.findElement(parser.getbjectLocator("CanPreferentialTransferofResidueOccurwiththisEquipment?"));
					Select preferentialTransferType = new Select(preferentialTransfer);
					preferentialTransferType.selectByVisibleText(EquipmentPreferentialTransferCREATE);
					Thread.sleep(500);
					
					driver.findElement(By.id("preferentialTransferSurfaceArea")).sendKeys(EquipmentPreferentialTransferSFCREATE);
					Thread.sleep(500);

					WebElement PrimaryPackaging = driver.findElement(By.id("primaryPackaging"));
					Select SelectPrimaryPackaging = new Select(PrimaryPackaging);
					SelectPrimaryPackaging.selectByVisibleText(EquipmentPrimaryPackagingeOptionCREATE);
					Thread.sleep(500);
					
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(EquipmentMinBatchCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("QualificationDocumentId")).sendKeys(EquipmentQuaDocIDCREATE);
					Thread.sleep(500);
					WebElement SOP = driver.findElement(parser.getbjectLocator("CleaningSOPNo"));
					Select CleaningSOP = new Select(SOP);
					CleaningSOP.selectByIndex(1);
					Thread.sleep(500);
					WebElement processType = driver.findElement(parser.getbjectLocator("CleaningProcessType"));
					Select CleaningProcessType = new Select(processType);
					CleaningProcessType.selectByIndex(1);
					Thread.sleep(500);
					
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).sendKeys(EquipmentCleaningInfoCREATE);
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
					driver.findElement(parser.getbjectLocator("AddLocation")).click();
					Thread.sleep(500);
					driver.findElement(By.id("checkbox1")).click();
					Thread.sleep(500);
					driver.findElement(By.id("checkbox2")).click();
					Thread.sleep(500);
					driver.findElement(By.id("location1")).sendKeys(EquipmentSamplingLocationCREATE);
					Thread.sleep(500);
					driver.findElement(By.id("location2")).sendKeys(EquipmentSamplingLocationCREATE);
					Thread.sleep(500);
					//Enter Locations
			        Thread.sleep(500);
			        driver.findElement(By.id("location1")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
			        //Delete row
			        Thread.sleep(1000);
			        driver.findElement(By.cssSelector(".remove-row-icon")).click();
			        Thread.sleep(1000);
			        driver.findElement(By.cssSelector(".remove-row-icon")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB);
			        Thread.sleep(1000);
					
					driver.findElement(parser.getbjectLocator("Nextbutton")).click(); // submit location assessment
					
					
					//Do you want to upload images?
					WebElement uploadsamplingimage = driver.findElement(parser.getbjectLocator("Doyouwanttouploadimages?"));
					Select YesorNo = new Select(uploadsamplingimage);
					YesorNo.selectByIndex(2); // select Yes option
					Thread.sleep(1000);
					//upload image
					//driver.findElement(By.xpath(".//*[@id='upload-images']/div/div/input")).sendKeys("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\Test Data\\equipTrain.jpg");
					//Thread.sleep(1000);
					//add location
					//driver.findElement(By.xpath(".//*[@id='addPinName']")).click();
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
					
					//save image
					//driver.findElement(By.xpath(".//*[@id='preview-image']/div/div[2]/img")).click(); //click to save uploaded image
					//Thread.sleep(1000);
					Thread.sleep(1000);
					WebElement samplingbutton = driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")); //submitEquipmentSamplingDetails
					if(samplingbutton.getText().equalsIgnoreCase("Submit"))
					{
						System.out.println("No Custom loop");
						driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")).click();
						Thread.sleep(2000);
						
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
								Thread.sleep(2000);
						
					}
					String createEquipment = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("createEquipment "+createEquipment);
					//String createEquipment = driver.findElement(By.className("notify-msg")).getText();
					Assert.assertEquals(createEquipment,"Equipment saved successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						//String createEquipment = driver.findElement(By.className("notify-msg")).getText();
						//System.out.println("createEquipment "+createEquipment);
						//String createEquipment = driver.findElement(By.className("notify-msg")).getText();
						//Assert.assertEquals(createEquipment,"Equipment saved successfully");
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(500);
					
				} // closing Create Equipment method
			
				
				@Test(priority=10)
				public void EditEquipment() throws InterruptedException, SQLException, ClassNotFoundException
				{
					//Thread.sleep(31000);
					Thread.sleep(6000);
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
					
					String getEqmodel = driver.findElement(parser.getbjectLocator("Model")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getEqmodel,EquipmentModelCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Model")).clear();
					driver.findElement(parser.getbjectLocator("Model")).sendKeys(EquipmentModelEDIT);
					Thread.sleep(500);
					
					String getSerialNo = driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getSerialNo,EquipmentSerialNoCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).clear();
					driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).sendKeys(EquipmentSerialNoEDIT);
					Thread.sleep(300);
					
					String getmanufacturing = driver.findElement(parser.getbjectLocator("Manufacturer")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getmanufacturing,EquipmentManufacturerCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Manufacturer")).clear();
					driver.findElement(parser.getbjectLocator("Manufacturer")).sendKeys(EquipmentManufacturerEDIT);
					Thread.sleep(500);
					
					String geteqSF = driver.findElement(By.id("surfaceArea")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(geteqSF,EquipmentSFCREATE);
					Thread.sleep(500);
					driver.findElement(By.id("surfaceArea")).clear();
					driver.findElement(By.id("surfaceArea")).sendKeys(EquipmentSFEDIT);
					Thread.sleep(500);
					
					String getDASF = driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getDASF,EquipmentSFDataSourceCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).clear();
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys(EquipmentSFDataSourceEDIT);
					Thread.sleep(500);
					
					WebElement preferentialTransfer = driver.findElement(parser.getbjectLocator("CanPreferentialTransferofResidueOccurwiththisEquipment?"));
					Select preferentialTransferType = new Select(preferentialTransfer);
					WebElement option = preferentialTransferType.getFirstSelectedOption(); 
					String getpreTransfer = option.getText();
					Assert.assertEquals(getpreTransfer,EquipmentPreferentialTransferCREATE);
					preferentialTransferType.selectByVisibleText(EquipmentPreferentialTransferEDIT);
					Thread.sleep(500);
					
					String getMinBatch = driver.findElement(parser.getbjectLocator("MinimumBatchSize")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getMinBatch,EquipmentMinBatchCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).clear();
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(EquipmentMinBatchEDIT);
					Thread.sleep(500);
					
					String getQAID = driver.findElement(parser.getbjectLocator("QualificationDocumentId")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getQAID,EquipmentQuaDocIDCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("QualificationDocumentId")).clear();
					driver.findElement(parser.getbjectLocator("QualificationDocumentId")).sendKeys(EquipmentQuaDocIDCREATE);
					Thread.sleep(500);
					
					WebElement SOP = driver.findElement(parser.getbjectLocator("CleaningSOPNo"));
					Select CleaningSOP = new Select(SOP);
					CleaningSOP.selectByIndex(1);
					Thread.sleep(500);
					WebElement processType = driver.findElement(parser.getbjectLocator("CleaningProcessType"));
					Select CleaningProcessType = new Select(processType);
					CleaningProcessType.selectByIndex(3);
					Thread.sleep(500);
					
					String getothercleaningInfo = driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getothercleaningInfo,EquipmentCleaningInfoCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).clear();
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).sendKeys(EquipmentCleaningInfoEDIT);
					Thread.sleep(1000);
					
					//driver.findElement(parser.getbjectLocator("NextButton")).click();
					driver.findElement(By.id("saveEquipment")).click();
					Thread.sleep(500);
					
					
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Nextbutton")).click(); // submit location assessment
					
					Thread.sleep(3000);
					//Do you want to upload images?
					WebElement uploadsamplingimage = driver.findElement(parser.getbjectLocator("Doyouwanttouploadimages?"));
					Select YesorNo = new Select(uploadsamplingimage);
					YesorNo.selectByIndex(2); // select Yes option
					Thread.sleep(1000);
					
					
					
					Thread.sleep(1000);
					WebElement samplingbutton = driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")); //submitEquipmentSamplingDetails
					
					if(samplingbutton.getText().equals("Submit"))
					{
						System.out.println("No Custom loop");
						driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")).click();
						Thread.sleep(2000);
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
					Thread.sleep(2000);
					String EditEquipment = driver.findElement(By.className("notify-msg")).getText(); 
					Assert.assertEquals(EditEquipment,"Equipment updated successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(500);
				} // closing Edit Equipment method
				
				
				
				
				@Test(priority=11)
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
				
				@Test(priority=12)
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
				
				
				
				
				@Test(priority=13)
				public void CreateEquipmentforGroup() throws InterruptedException, SQLException, ClassNotFoundException
				{
					Thread.sleep(5000);
					driver.findElement(By.id("example-select-all")).click(); // un check mutli check box
					Thread.sleep(1000);
					driver.findElement(parser.getbjectLocator("CreateEquipment")).click(); // Click create equipment button
					Thread.sleep(1000);
					String equipmentName = EquipmentName;
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
					Thread.sleep(2000);
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
				
				
				
				
				
				@Test(priority=14,invocationCount=6)
				public void CopyEquipment() throws InterruptedException, SQLException, ClassNotFoundException
				{
					//driver.navigate().refresh();
					Thread.sleep(6000);
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
					
					Thread.sleep(500);
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
