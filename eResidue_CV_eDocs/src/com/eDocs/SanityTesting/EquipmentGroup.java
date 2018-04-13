package com.eDocs.SanityTesting;

import java.io.IOException;
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

public class EquipmentGroup {
 
	//RepositoryParser parser = new RepositoryParser("");
				//static Utils WD = new Utils();	
				private RepositoryParser parser;
				private WebDriver driver = Constant.driver;
				public String password = "123456";
				static String EquipmentGroupName = "Test Group";
				static String groupIdentifyEquipment = "Test Equipment";
				
				//Create Group Data's
				static String groupCommentsCREATE = "Create equipment group";
				static String groupChangeControlCREATE = "Create equipment group";
				static String groupNoOfRunsCREATE = "3";
				static String groupProtocolDocCREATE = "equip Group protocol for create";
				static String groupReportIDCREATE = "equip Group report for create";
				
				//Edit Group Data's
				static String groupCommentsEDIT = "edit equipment group";
				static String groupChangeControlEDIT = "Edit equipment group";
				static String groupNoOfRunsEDIT = "4";
				static String groupProtocolDocEDIT = "equip Group protocol for edit";
				static String groupReportIDEDIT = "equip Group report for edit";
				
				@BeforeClass
				public void setUp() throws IOException  
				{
					driver = new FirefoxDriver();
					driver.get("http://192.168.1.111:8090");
					parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
				}
			
				@Test(priority=2)
				public void Login() throws InterruptedException, IOException
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
					Thread.sleep(500);
					driver.get("http://192.168.1.111:8090/equipment-group");
					Thread.sleep(500);
				}
	
	
				@Test(priority=8,invocationCount=2)
				public void CreareEquipmentGroup() throws InterruptedException, IOException
				{
					parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
					
					Thread.sleep(500);
					driver.get("http://192.168.1.111:8090/equipment-group");
					//driver.navigate().refresh();
					Thread.sleep(1000);
					driver.findElement(By.xpath(".//*[@id='addGroup']/span")).click(); // Click create equipment button
					Thread.sleep(500);
					String GroupName = EquipmentGroupName;
					WebElement eqGRPName = driver.findElement(parser.getbjectLocator("EquipmentGroupName")); //Equipment Name field
					eqGRPName.sendKeys(GroupName);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("GroupingCriteriaSimilar")).click();
					Thread.sleep(500);
					WebElement selectcriteria= driver.findElement(parser.getbjectLocator("Feature"));
					Select SelectGroupCriteria = new Select(selectcriteria);
					SelectGroupCriteria.selectByIndex(1);
					Thread.sleep(500);
					
					driver.findElement(parser.getbjectLocator("GroupComments")).sendKeys(groupCommentsCREATE);
					Thread.sleep(500);
					
					
					WebElement IdentifuEquip = driver.findElement(By.className("select2-search__field"));
					IdentifuEquip.sendKeys(groupIdentifyEquipment);
					Thread.sleep(200);
					IdentifuEquip.sendKeys(Keys.ENTER);
					Thread.sleep(200);
					IdentifuEquip.sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
			        Thread.sleep(500);
			       
					List<String> equipments = new ArrayList<>();
					equipments.add(driver.findElement(By.cssSelector("ul.select2-selection__rendered>li:nth-child(1)")).getText().substring(1));
					equipments.add(driver.findElement(By.cssSelector("ul.select2-selection__rendered>li:nth-child(2)")).getText().substring(1));
					
					System.out.println("-->equipments "+ equipments);
					for(String equipment:equipments)
					{
						Thread.sleep(500);
						WebElement selectEquipment = driver.findElement(parser.getbjectLocator("IdentifyEquipment"));
						selectEquipment.sendKeys(equipment);
						Thread.sleep(500);
						selectEquipment.sendKeys(Keys.ENTER);
						Thread.sleep(500);
						selectEquipment.click();
						Thread.sleep(500);
					}
					
					
					WebElement next = driver.findElement(parser.getbjectLocator("WorstcaseNextButton"));
					next.click();
					Thread.sleep(500);
					
	//Check name duplicate
					if(driver.findElements(By.className("notify-msg")).size()!=0)
					{
						String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
						driver.findElement(By.className("custom-notify-close")).click();
					
					Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
					for(int k=5;k<1000;k++)
					{
						j.add(k);
					}
					
					Thread.sleep(500);
					if(getduplicatename.equals("Group '"+GroupName+"' already exists!"))
					{
						System.out.println("loop");
						for(Integer i:j)
						{
							eqGRPName.clear();
							eqGRPName.sendKeys(GroupName+i);
							Thread.sleep(500);
							next.click(); 
							Thread.sleep(500);
						try
						{
							if(driver.findElements(parser.getbjectLocator("WorstcaseNextButton")).size()!=0)
							{
								String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
								System.out.println("Name duplicate: "+nameduplicate);
								driver.findElement(By.className("custom-notify-close")).click();
								if(nameduplicate.equals("Equipment '"+GroupName+i+"' already exists!"))
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
					
					//worst case equipment selection
					Thread.sleep(500);
					WebElement worstcase = driver.findElement(parser.getbjectLocator("WorstCaseDetermination"));
					worstcase.click();
					worstcase.sendKeys(equipments.get(0));
					worstcase.sendKeys(Keys.ENTER);
					worstcase.click();
					Thread.sleep(1000);	
					//no of runs
					WebElement noofruns = driver.findElement(parser.getbjectLocator("No.ofRuns"));
					Select noofRunsforWorstcaseeq = new Select(noofruns);
					noofRunsforWorstcaseeq.selectByVisibleText(groupNoOfRunsCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("ProtocolDocID")).sendKeys(groupProtocolDocCREATE);//protocol id
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("ReportID")).sendKeys(groupReportIDCREATE);
					Thread.sleep(500);
					//Worstcase equipment
					WebElement worstcaseEQ = driver.findElement(parser.getbjectLocator("WorstCaseEquipment"));
					Select worstquipment = new Select(worstcaseEQ);
					worstquipment.selectByIndex(1);
					Thread.sleep(500);
					
					
					WebElement worstcasesubmit = driver.findElement(By.id("saveEquipmentGroup")); //submitEquipmentSamplingDetails
					
					if(worstcasesubmit.getText().equals("Submit"))
					{
						Thread.sleep(1000);
						worstcasesubmit.click();
					}else
					{
						worstcasesubmit.click();
						System.out.println("Custom loop");
						Thread.sleep(500);
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
						Thread.sleep(500);
								//click save button in custom fields
								driver.findElement(By.id("saveCustomDetails")).click();
					}
					Thread.sleep(1000);
					String createGroup = driver.findElement(By.className("notify-msg")).getText(); 
					Assert.assertEquals(createGroup,"Equipment group saved successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(600);
				} // closing create equipment group
				
	
				
				@Test(priority=9)
				public void EditEquipmentGroup() throws InterruptedException, IOException
				{
					Thread.sleep(1000);
					driver.findElement(By.id("dLabel")).click();
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[8]/div/ul/li[2]/a")).click(); // Click edit equipment button
					driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
					Thread.sleep(1000);
					driver.findElement(parser.getbjectLocator("GroupingCriteriaSimilar")).click();
					Thread.sleep(500);
					//WebElement selectcriteria= driver.findElement(parser.getbjectLocator("Feature"));
					//Select SelectGroupCriteria = new Select(selectcriteria);
					//SelectGroupCriteria.selectByIndex(2);
					//Thread.sleep(500);
					
					String getgroupComments = driver.findElement(parser.getbjectLocator("GroupComments")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getgroupComments,groupCommentsCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("GroupComments")).clear();
					driver.findElement(parser.getbjectLocator("GroupComments")).sendKeys(groupCommentsEDIT);
					Thread.sleep(500);
					
					
					WebElement next = driver.findElement(parser.getbjectLocator("WorstcaseNextButton"));
					next.click();
					Thread.sleep(500);
					
					
					System.out.println(driver.findElements(parser.getbjectLocator("No.ofRuns")).size());
					if(driver.findElements(parser.getbjectLocator("No.ofRuns")).size()!=0)
					{
					//no of runs
						
					WebElement noofruns = driver.findElement(parser.getbjectLocator("No.ofRuns"));
					Select noofRunsforWorstcaseeq = new Select(noofruns);
					WebElement option = noofRunsforWorstcaseeq.getFirstSelectedOption(); 
					String getNoofRuns = option.getText();
					Assert.assertEquals(getNoofRuns,groupNoOfRunsCREATE);
					noofRunsforWorstcaseeq.selectByVisibleText(groupNoOfRunsEDIT);
					Thread.sleep(500);
					
					String getProtocolID = driver.findElement(parser.getbjectLocator("ProtocolDocID")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getProtocolID,groupProtocolDocCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("ProtocolDocID")).clear();//protocol id
					driver.findElement(parser.getbjectLocator("ProtocolDocID")).sendKeys(groupProtocolDocEDIT);//protocol id
					Thread.sleep(500);
					
					String getReportID = driver.findElement(parser.getbjectLocator("ReportID")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getReportID,groupReportIDCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("ReportID")).clear();
					driver.findElement(parser.getbjectLocator("ReportID")).sendKeys(groupReportIDEDIT);
					Thread.sleep(500);
					//Worstcase equipment
					WebElement worstcaseEQ = driver.findElement(parser.getbjectLocator("WorstCaseEquipment"));
					Select worstquipment = new Select(worstcaseEQ);
					worstquipment.selectByIndex(1);
					Thread.sleep(500);
					}
					
					
					Thread.sleep(1000);
					WebElement worstcasesubmit = driver.findElement(By.id("saveEquipmentGroup")); //submitEquipmentSamplingDetails
					
					
					if(worstcasesubmit.getText().equals("Submit"))
					{
						worstcasesubmit.click();
						Thread.sleep(1000);
						driver.findElement(By.id("comments")).click();
						Thread.sleep(500);
						driver.findElement(By.id("comments")).sendKeys(Keys.SHIFT,Keys.TAB,password);
						Thread.sleep(500);
						driver.findElement(By.id("ackSubmit")).click();
						
						
					}else
					{
						worstcasesubmit.click();
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
					String EditGroup = driver.findElement(By.className("notify-msg")).getText();
					Assert.assertEquals(EditGroup,"Equipment group updated successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(600);
				} // closing Edit equipment group
				
				
				
				
				@Test(priority=10)
				public void SingleDeleteEquipmentGroup() throws InterruptedException, IOException
				{
					Thread.sleep(2000);
					driver.findElement(By.id("dLabel")).click();
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[8]/div/ul/li[3]/a")).click(); // Click edit equipment button
					driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
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
					String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
					Assert.assertEquals(deletemsg,"Equipment Group deleted successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(600);
				}
				

			/*	@Test(priority=11)
				public void MultiDeleteEquipmentGroup() throws InterruptedException, IOException
				{
					Thread.sleep(2000);
					driver.findElement(By.id("searchEquipment")).sendKeys("Test Equipment");
					Thread.sleep(1000);
					driver.findElement(By.id("example-select-all")).click();
					Thread.sleep(1000);
					driver.findElement(By.id("deleteSelectedEquipmentGroup")).click(); // multi delete
					driver.findElement(By.id("deleteSelectedEquipmentGroup")).sendKeys(Keys.ENTER); // multi delete
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
					String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
					Assert.assertEquals(deletemsg,"Equipment Group deleted successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(600);
					
				}
				*/
				
				
				
				
				
				
				
				
	
}
