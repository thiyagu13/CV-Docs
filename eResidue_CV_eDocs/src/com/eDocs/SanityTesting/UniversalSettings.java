package com.eDocs.SanityTesting;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

public class UniversalSettings {
	
	
			private RepositoryParser parser;
			private WebDriver driver = Constant.driver;
			public String GneralPreference = "http://192.168.1.45:8092/general-preferences";
			public String SiteMap = "http://192.168.1.45:8092/site-map";
			public String LimitTerminology = "http://192.168.1.45:8092/limit-terminology";
			public String SamplingLocation = "http://192.168.1.45:8092/sampling-locations";
			public String ResidueLimit = "http://192.168.1.45:8092/residue-limit";
			public String microbilaLimit = "http://192.168.1.45:8092/microbial-limit";
			public String productgrouping = "http://192.168.1.45:8092/product-grouping";
			
				@BeforeClass
				public void setUp() throws IOException  
				{
					//driver = new FirefoxDriver();
					//driver.get("http://192.168.1.111:8090");
					driver.get("http://192.168.1.45:8092/login");
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
					//driver.get("http://192.168.1.45:8091/residue-limit");
					Thread.sleep(500);
					//driver.get("http://192.168.1.45:8091/equipment-group");
				}

				
				
				@Test(priority=2)
				public void ResidueLimitSAVE() throws InterruptedException
				{
					driver.get(ResidueLimit);
					Thread.sleep(500);
					//Basis Limit
					System.out.println(driver.findElement(By.id("basis3")).isSelected());
					if(driver.findElement(By.id("basis3")).isSelected()==false)
					{
						Thread.sleep(500);
						driver.findElement(By.id("basis3")).click();
					}
					Thread.sleep(500);
					//DefaultLimit
					if(driver.findElement(By.id("default1")).isSelected()==false)
					{
						Thread.sleep(500);
						driver.findElement(By.id("default1")).click();
					}
					Thread.sleep(500);
					//Basis of Limit Determination
					if(driver.findElement(By.id("limitBasedonLowestL0")).isSelected()==false)
					{
						Thread.sleep(500);
						driver.findElement(By.id("limitBasedonLowestL0")).click();
					}
					Thread.sleep(500);
					//Surface Area to be used in Limit per unit area determination 
					driver.findElement(By.id("defaultsa1")).click();
					Thread.sleep(1000);
					
					//Qualification Run Sampling Method
					if(driver.findElement(By.xpath(".//*[@id='residueLimitDetails']/div/div[7]/table/tbody/tr/td[1]/span/span[1]/span/ul")).getText().equalsIgnoreCase("×Swab"))
					{
						Thread.sleep(500);
						WebElement QualificationRun = driver.findElement(By.xpath(".//*[@id='residueLimitDetails']/div/div[7]/table/tbody/tr/td[1]/span/span[1]/span/ul"));
						Thread.sleep(500);
						QualificationRun.click();
						Thread.sleep(500);
						QualificationRun.sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
						Thread.sleep(500);
						System.out.println("QualificationRun "+QualificationRun.getText());
					}
					
					Thread.sleep(500);
					//sampling Type
					WebElement samplingType =driver.findElement(By.id("samplingMethodCategory"));
					Select SelectSamplingType = new Select(samplingType);
					SelectSamplingType.selectByVisibleText("Individual");
					Thread.sleep(500);
					
					// Routine Monitoring Sampling Method
					if(driver.findElement(By.xpath(".//*[@id='residueLimitDetails']/div/div[8]/table/tbody/tr/td/span/span[1]/span/ul")).getText().equalsIgnoreCase("×Swab"))
					{
						WebElement QualificationRun = driver.findElement(By.xpath(".//*[@id='residueLimitDetails']/div/div[8]/table/tbody/tr/td/span/span[1]/span/ul"));
						Thread.sleep(500);
						QualificationRun.click();
						Thread.sleep(500);
						QualificationRun.sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
						Thread.sleep(500);
						System.out.println("QualificationRun "+QualificationRun.getText());
					}
					
					
					Thread.sleep(500);
					WebElement Swabsample =driver.findElement(By.id("swabLimitsDetermination"));
					Select SelectSwabsample = new Select(Swabsample);
					SelectSwabsample.selectByVisibleText("Both");
					Thread.sleep(500);
					
					Thread.sleep(500);
					WebElement RinseSmple =driver.findElement(By.id("rinseSamplingOption"));
					Select SelectRinseSmple = new Select(RinseSmple);
					SelectRinseSmple.selectByValue("1");
					Thread.sleep(500);
					
					
					
					
					//Swab Area Option
					driver.findElement(By.id("surfaceAreaSampled1")).click();
					Thread.sleep(500);
					driver.findElement(By.id("valueForSameProductsForSurfaceAreaSampled1")).clear();
					driver.findElement(By.id("valueForSameProductsForSurfaceAreaSampled1")).sendKeys("60");
					//Swab Amount Option
					driver.findElement(By.id("desorption1")).click();
					Thread.sleep(500);
					driver.findElement(By.id("valueForSameProductsForSolventUsed1")).clear();
					driver.findElement(By.id("valueForSameProductsForSolventUsed1")).sendKeys("40");
					//Rinse Volume
					driver.findElement(By.id("rinseVolume1")).click();
					Thread.sleep(500);
					driver.findElement(By.id("sameForAllEquipValueForRinseVolume1")).clear();
					driver.findElement(By.id("sameForAllEquipValueForRinseVolume1")).sendKeys("20");
					Thread.sleep(500);
					
					driver.findElement(By.id("saveResidueLimitDetails")).click();
					Thread.sleep(500);	
					
					String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
					Assert.assertEquals(SuccessMessage,"Residue Limit updated successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(500);
				  }
	
				
				@Test(priority=3)
				public void GeneralSettings() throws InterruptedException
				{
					driver.get(GneralPreference);
					Thread.sleep(500);
					//enforceComments
					WebElement EnforceComments = driver.findElement(By.id("enforceComments"));
					Select comments = new Select(EnforceComments);
					comments.selectByVisibleText("No");
					Thread.sleep(500);
					
					//enforceChangeControl
					WebElement enforceChangeControl = driver.findElement(By.id("enforceChangeControl"));
					Select ChangeControl = new Select(enforceChangeControl);
					ChangeControl.selectByVisibleText("No");
					Thread.sleep(200);
					
					driver.findElement(By.id("saveGeneralOption")).click();
					Thread.sleep(500);
					
					String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
					Assert.assertEquals(SuccessMessage,"General Preferences saved successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(500);
				  }
				
				
				
				@Test(priority=4)
				public void SiteMap() throws InterruptedException
				{
					driver.get(SiteMap);
					Thread.sleep(500);
					//enforceChangeControl
					WebElement EnforceComments = driver.findElement(By.id("uploadSiteImages"));
					Select comments = new Select(EnforceComments);
					comments.selectByVisibleText("No");
					Thread.sleep(100);
					
					System.out.println(driver.findElements(By.className("no")).size());
					
						if(driver.findElements(By.className("no")).size()!=0)
						{
							if(driver.findElement(By.className("no")).getText().equalsIgnoreCase("1."))
							{
								Thread.sleep(500);
								System.out.println("--->:"+driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input[2]")).getAttribute("value"));
								if(driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input[2]")).getAttribute("value").equalsIgnoreCase(""))
								{
									driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input[2]")).sendKeys("Chennai");
								}
							}
						}
							else
							{
								Thread.sleep(500);
								driver.findElement(By.cssSelector(".waves-effect")).click();
								Thread.sleep(500);
								if(driver.findElement(By.className("no")).getText().equalsIgnoreCase("1."))
								{
									Thread.sleep(500);
									System.out.println("=-------");
									System.out.println(driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input[2]")).getText());
									if(driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input[2]")).getText().equalsIgnoreCase(""))
									{
										driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input[2]")).sendKeys("Chennai");
									}
								}
								
							}
							Thread.sleep(500);
							driver.findElement(By.id("saveLocation")).click();
							Thread.sleep(500);
							String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
							Assert.assertEquals(SuccessMessage,"Location details saved successfully");
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
				public void LimitTerminology() throws InterruptedException
				{
					driver.get(LimitTerminology);
					Thread.sleep(1000);
					driver.findElement(By.xpath(".//*[@id='limitTerminologyDefinition']/div/div[8]/button[1]")).click();
					Thread.sleep(1000);
					String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
					Assert.assertEquals(SuccessMessage,"Limit Definition saved successfully");
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
				public void SampleLocation() throws InterruptedException
				{
					
					driver.get(SamplingLocation);
					Thread.sleep(1000);
					driver.findElement(By.id("listMOC")).click();
					Thread.sleep(500);
					System.out.println(driver.findElements(By.className("no")).size());
				if(driver.findElements(By.className("no")).size()!=0)
				{
					if(driver.findElement(By.className("no")).getText().equalsIgnoreCase("1."))
					{
						Thread.sleep(500);
						System.out.println(driver.findElement(By.id("locationName1")).getAttribute("value"));
						if(driver.findElement(By.id("locationName1")).getAttribute("value").equalsIgnoreCase(""))
						{
							driver.findElement(By.id("locationName1")).clear();
							driver.findElement(By.id("locationName1")).sendKeys("Chennai");
						}
						
						Thread.sleep(500);
						driver.findElement(By.id("saveMoc")).click();
						Thread.sleep(1000);
						
						String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
						Assert.assertEquals(SuccessMessage,"MOC has been saved successfully");
						String className = this.getClass().getName(); // get current class name - for screenshot
						String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
						Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
						if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
						{
							driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
						}
						Thread.sleep(1000);
						
					}
				}
					else
					{
						Thread.sleep(500);
						driver.findElement(By.xpath(".//*[@id='addMOC']")).click();
						Thread.sleep(500);
						//if(driver.findElement(By.className("no")).getText().equalsIgnoreCase("1."))
						//{
						//	if(driver.findElement(By.id("marker-num1")).getText().equalsIgnoreCase(""))
							//{
								driver.findElement(By.id("locationName1")).clear();
								driver.findElement(By.id("locationName1")).sendKeys("Chennai");
							//}
						//}
								Thread.sleep(500);
								driver.findElement(By.id("saveMoc")).click();
								Thread.sleep(1000);
								String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
								Assert.assertEquals(SuccessMessage,"MOC has been saved successfully");
								String className = this.getClass().getName(); // get current class name - for screenshot
								String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
								Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
								if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
								{
									driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
								}
								Thread.sleep(1000);
						
					}
					
					WebElement Risk = driver.findElement(By.id("samplingType"));
					Select RiskAssement = new Select(Risk);
					RiskAssement.selectByVisibleText("Based on Risk Assessment");
					Thread.sleep(1000);
					
					System.out.println(driver.findElement(By.xpath(".//*[@id='user_role']/div[1]/span/span[1]/span")).getText());
					if(driver.findElement(By.xpath(".//*[@id='user_role']/div[1]/span/span[1]/span")).getText().equalsIgnoreCase(""))
					{
						driver.findElement(By.id("saveSamplingLocation")).click();
					
					if(driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Please select Criteria"))
					{
						if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
						{
							driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
						}
						WebElement sampling = driver.findElement(By.xpath(".//*[@id='user_role']/div[1]/span/span[1]/span"));
						sampling.click();
						Thread.sleep(500);
						sampling.sendKeys(Keys.ENTER);
						Thread.sleep(500);
						sampling.sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
						Thread.sleep(1000);
						}
					}
					Thread.sleep(1000);
					WebElement rating = driver.findElement(By.id("rating"));
					Select SelectRating = new Select(rating);
					WebElement option = SelectRating.getFirstSelectedOption();
					String getvalue = option.getText();
					System.out.println("getpreTransfer"+getvalue);
					if(getvalue.equalsIgnoreCase("Select"))
					{
						SelectRating.selectByValue("1");
						Thread.sleep(1000);
					}
					Thread.sleep(500);
					driver.findElement(By.id("saveSamplingLocation")).click();
					Thread.sleep(2000);
					
					String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
					Assert.assertEquals(SuccessMessage,"SamplingLocationInfo has been saved successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(500);
				}
				
				
				
				@Test(priority=7)
				public void productgrouping() throws InterruptedException 
				{
					Thread.sleep(500);
					driver.get(productgrouping);
					Thread.sleep(1000);
					
					System.out.println(driver.findElement(By.xpath(".//*[@id='groupingForm']/div[1]/div[1]/span/span[1]/span")).getText());
					
				if(driver.findElement(By.xpath(".//*[@id='groupingForm']/div[1]/div[1]/span/span[1]/span")).getText().equalsIgnoreCase(""))
				{
					Thread.sleep(500);
					driver.findElement(By.id("saveProductGroupingCriteria")).click();
					Thread.sleep(1000);
					
					System.out.println(driver.findElement(By.className("notify-msg")).getText());
					if(driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Please select grouping criteria"))
					{
						if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
						{
							driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
						}
						Thread.sleep(500);
						System.out.println("------");
						WebElement criteria = driver.findElement(By.xpath(".//*[@id='groupingForm']/div[1]/div[1]/span/span[1]/span"));
						criteria.click();
						Thread.sleep(1000);
						criteria.sendKeys(Keys.ENTER);
						Thread.sleep(1000);
						criteria.click();
						Thread.sleep(1000);
						criteria.sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
						Thread.sleep(500);
					}
				}
					
					
					WebElement worstcase = driver.findElement(By.id("worstCaseSelection"));
					Select worstcaseSelection = new Select(worstcase);
					WebElement option = worstcaseSelection.getFirstSelectedOption(); 
					String getOption = option.getText();
					System.out.println("getOption "+getOption);
					Thread.sleep(500);
					
					if(getOption.equalsIgnoreCase("Stratified Selection Approach"))
					{
						Thread.sleep(500);
						driver.findElement(By.id("saveProductGroupingCriteria")).click();
						Thread.sleep(500);
					
					}	
					if(getOption.equalsIgnoreCase("Point System Approach"))
					{
						System.out.println(driver.findElement(By.id("value1")).isSelected());
						System.out.println(driver.findElement(By.id("value2")).isSelected());
						if(driver.findElement(By.id("value1")).isSelected()==false && driver.findElement(By.id("value2")).isSelected()==false)
						{
							Thread.sleep(500);
							driver.findElement(By.id("value1")).click();
							Thread.sleep(500);
							driver.findElement(By.id("saveProductGroupingCriteria")).click();
							Thread.sleep(2000);
						}
						if(driver.findElement(By.id("value1")).isSelected()==true || driver.findElement(By.id("value2")).isSelected()==true)
						{
							Thread.sleep(500);
							driver.findElement(By.id("saveProductGroupingCriteria")).click();
							Thread.sleep(2000);
						}
					}	
					
					String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
					Assert.assertEquals(SuccessMessage,"Grouping saved successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(500);
					
				}
					
				
				
				
				
				
				
				
				@Test(priority=8)
				public void microbialLimit() throws InterruptedException 
				{
					Thread.sleep(500);
					driver.get(microbilaLimit);
					Thread.sleep(1000);
					//microbial limitset up
					WebElement limit = driver.findElement(By.id("limitsSetupOption"));
					Select SelectLimit = new Select(limit);
					SelectLimit.selectByVisibleText("Both");
					Thread.sleep(500);
					
					//Bioburden Limit Setup
					WebElement Biolimit = driver.findElement(By.id("bioburdenLimitOption"));
					Select SelectBioLimit = new Select(Biolimit);
					SelectBioLimit.selectByVisibleText("Direct Surface and Rinse Sample");
					Thread.sleep(500);
					
					System.out.println(driver.findElement(By.id("surfaceSample1")).isSelected());
					System.out.println(driver.findElement(By.id("surfaceSample2")).isSelected());
					Thread.sleep(500);
					if(driver.findElement(By.id("surfaceSample1")).isSelected()==false)
					{
						driver.findElement(By.id("surfaceSample1")).click();
					}
					Thread.sleep(500);
					if(driver.findElement(By.id("rinseSample1")).isSelected()==false)
					{
						driver.findElement(By.id("rinseSample1")).click();
					}
					
					WebElement QualificationRunSurface = driver.findElement(By.id("samplingMethodOption"));
					Select SelectQualificationRunSurface = new Select(QualificationRunSurface);
					SelectQualificationRunSurface.selectByValue("2");
					
					Thread.sleep(500);
					driver.findElement(By.id("swabSurfaceArea")).clear();
					driver.findElement(By.id("swabSurfaceArea")).sendKeys("30");
					Thread.sleep(500);
					
					WebElement routeSamplingMethodOption = driver.findElement(By.id("routeSamplingMethodOption"));
					Select SelectrouteSamplingMethodOption = new Select(routeSamplingMethodOption);
					SelectrouteSamplingMethodOption.selectByValue("2");
					
					Thread.sleep(500);
					driver.findElement(By.id("routeSwabSurfaceAreaUnit")).clear();
					driver.findElement(By.id("routeSwabSurfaceAreaUnit")).sendKeys("30");
					
					Thread.sleep(500);
					driver.findElement(By.id("bioburdenContribution")).clear();
					driver.findElement(By.id("bioburdenContribution")).sendKeys("30");
					Thread.sleep(500);
					
					driver.findElement(By.id("varFactorMicroEnum")).clear();
					driver.findElement(By.id("varFactorMicroEnum")).sendKeys("25");
					Thread.sleep(500);
					
					WebElement MicrobialLimitSetup  = driver.findElement(By.id("limitsSetupOption"));
					Select SelectMicrobialLimitSetup = new Select(MicrobialLimitSetup);
					WebElement option = SelectMicrobialLimitSetup.getFirstSelectedOption(); 
					String getMicrobialLimitSetup = option.getText();
					Thread.sleep(500);
					System.out.println(getMicrobialLimitSetup);
					if(getMicrobialLimitSetup.equalsIgnoreCase("Endotoxin Default Limit") || getMicrobialLimitSetup.equalsIgnoreCase("Both"))
					{
						if(driver.findElement(By.id("endotoxinLimitSetup1")).isSelected()==false)
						{
							driver.findElement(By.id("endotoxinLimitSetup1")).click();
						}
					}
					
					
					Thread.sleep(1000);
					driver.findElement(By.id("saveMicrobialLimit")).click();
					Thread.sleep(1000);
					String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
					Assert.assertEquals(SuccessMessage,"Microbial limit saved successfully");
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					}
					Thread.sleep(500);
				}
				
				
				
				
				
	
}
