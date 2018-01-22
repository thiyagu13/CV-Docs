package com.eResidue.Calculation;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class Default_Limit {
	public static WebDriver driver;
	
	@Test(priority=1)
	public void UniversalSettings() throws IOException, InterruptedException,ClassNotFoundException {
		//System.setProperty("webdriver.Chrome.driver","C:\\selenium\\Testing\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver","C:\\selenium\\Testing\\geckodriver.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();

		capabilities.setCapability("marionette", true);
		
		driver = new FirefoxDriver(capabilities);
		// Open the application
		driver.get("http://192.168.1.111:8081/login");
		Thread.sleep(1000);
		driver.switchTo().alert().accept();
		// Login
		driver.findElement(By.id("username")).sendKeys("eresiduetest1");
		driver.findElement(By.id("password")).sendKeys("quascenta");
		Thread.sleep(3000);
		driver.findElement(By.id("submitbtn")).click();
		Thread.sleep(1000);
		driver.switchTo().alert().accept();
		// ForceLogin
		if (driver.findElement(By.className("top-message")).getText()
				.equalsIgnoreCase("Invalid credentials!")) {
			System.out.println(driver.findElement(By.className("top-message"))
					.getText());
		} else {
			// Force Login
			if (driver.getTitle().equalsIgnoreCase("Report Tracker - eResidue") == false) {
				Thread.sleep(1000);
				String pop = driver.getWindowHandle();
				driver.switchTo().window(pop);
				Thread.sleep(1000);
				driver.findElement(By.id("forcelogin")).click();
			}
		}
		Thread.sleep(2000);
		
		
		// mouse over on Settings menu
		WebElement f = driver.findElement(By.xpath(".//*[@id='settings']"));
		Actions a1 = new Actions(driver);
		a1.moveToElement(f).perform();
		Thread.sleep(1000);
		// select Universal Settings option
		driver.findElement(By.linkText("Universal Settings")).click();
		Thread.sleep(500);
		System.out.println(driver.getTitle());
		Thread.sleep(500);
		// select Limit Definition Tab
		driver.findElement(By.linkText("Limit Definition")).click();
	
		defaultmethod();
	//	System.out.println("defaultmethod option selected---->"+defaultmethod());
	}
		public static String defaultmethod()
		{
			
		for (int i = 1; i <=4; i++) 
		{
			WebElement radiobutton = driver.findElement(By.id("default_value_"+i));
			String str2;
			if (radiobutton.isSelected()) 
			{
				str2 = radiobutton.getAttribute("Value"); 
			//	System.out.println(str2+" - radio button is Selected");
				
				//First Radio button is selected
				if(str2.equals("1")){
										//System.out.println("'No default used. Use calculated value' is selected =---->");
					return "No_Default";
				}
				
				//Second Radio button is selected
				if(str2.equals("2"))
				{
					//System.out.println("'Use a default value for L1' is selected =---->");
					WebElement radiobutton2 = driver.findElement(By.id("default_value_l1"));
					{
						if (radiobutton2.isSelected()) 
						{
						String de_L1 = radiobutton2.getAttribute("Value"); 
						String de_L1_unitCon = Double.toString(Double.parseDouble(de_L1) * 0.001); // For unit conversion (ppm to mg/g)
						//System.out.println("Use a default value for L1="+de_L1_unitCon+"ppm");
						return "Default_L1@@"+de_L1_unitCon;
						}
					else{
						String de_L1 = driver.findElement(By.id("default_L1_other")).getAttribute("value");
						String de_L1_unitCon = Double.toString(Double.parseDouble(de_L1) * 0.001); // For unit conversion (ppm to mg/g)
						//System.out.println("Use a default value for L1="+de_L1+"ppm (other)");
						return "Default_L1@@"+de_L1_unitCon;
						}
					}
					
				}
				
				//Third Radio button is selected
				if(str2.equals("3"))
				{
					//System.out.println("'Use a default value for L3' is selected =---->");
					WebElement radiobutton3 = driver.findElement(By.id("default_value_l3"));
					{
						if (radiobutton3.isSelected()) 
						{
						String de_L3 = radiobutton3.getAttribute("Value"); 
						System.out.println("Use a default value for L3="+de_L3+" mg/sq.cm ");
						return "Default_L3@@"+de_L3;
						}
					else{
						String de_L3 = driver.findElement(By.id("default_L3_other")).getAttribute("value");
						System.out.println("Use a default value for L3="+de_L3+"mg/sq.cm (other)");
						return "Default_L3@@"+de_L3;
						}
					}
				}
				
				//Fourth Radio button is selected
				if(str2.equals("4"))
				{
					//System.out.println("'Use a default value for both L1 and L3' is selected=---->");
					WebElement defaultL1 = driver.findElement(By.id("default_value_l1_2"));
					WebElement defaultL3 = driver.findElement(By.id("default_value_l3_2"));
					WebElement defaultL1_other_option = driver.findElement(By.id("default_value_l1_2_other"));
					WebElement defaultL1_other_value = driver.findElement(By.id("default_L1_other1"));
					WebElement defaultL3_other_option = driver.findElement(By.id("default_value_l3_2_other"));
					WebElement defaultL3_other_value = driver.findElement(By.id("default_L3_other1"));
					{
						if (defaultL1.isSelected() &&defaultL3.isSelected()) // default L1=10 ppm and Default L3 = 0.004 mg/sq.cm
						{
						String de_L1 = defaultL1.getAttribute("Value"); 
						System.out.println("Use a default value for L1="+de_L1+"ppm");
						String de_L1_Conv = Double.toString(Double.parseDouble(de_L1) * 0.001); // For unit conversion (ppm to mg/g)
						
						String de_L1_L3 = defaultL3.getAttribute("Value"); 
						System.out.println("Use a default value for L3="+de_L1_L3+"mg/sq.cm "); // Default L3 value
						return "Default_L1L3@@"+de_L1_Conv +"@@"+de_L1_L3 ;
						
						}
						if (defaultL1.isSelected() &&defaultL3_other_option.isSelected() ) // default L1=10 ppm and Default other L3 value
						{
						String de_L1 = defaultL1.getAttribute("Value"); 
						System.out.println("Use a default value for L1="+de_L1+"ppm");
						String de_L1_Conv = Double.toString(Double.parseDouble(de_L1) * 0.001); // For unit conversion (ppm to mg/g)
						
						String de_L1_L3 = defaultL3_other_value.getAttribute("Value"); 
						System.out.println("Use a default value for L3="+de_L1_L3+"mg/sq.cm "); // Default L3 value
						return "Default_L1L3@@"+de_L1_Conv +"@@"+de_L1_L3 ;
						}
						if (defaultL1_other_option.isSelected() &&defaultL3_other_option.isSelected() ) // default other L1 value and Default other L3 value
						{
						String de_L1 = defaultL1_other_value.getAttribute("Value"); 
						System.out.println("Use a default value for L1="+de_L1+"ppm");
						String de_L1_Conv = Double.toString(Double.parseDouble(de_L1) * 0.001); // For unit conversion (ppm to mg/g)
						
						String de_L1_L3 = defaultL3_other_value.getAttribute("Value"); 
						System.out.println("Use a default value for L3="+de_L1_L3+"mg/sq.cm "); // Default L3 value
						return "Default_L1L3@@"+de_L1_Conv +"@@"+de_L1_L3 ;
						}
						if (defaultL1_other_option.isSelected() &&defaultL3.isSelected() ) // default other L1 Value and Default other L3 = 0.004 mg/sq.cm
						{
						String de_L1 = defaultL1_other_value.getAttribute("Value"); 
						System.out.println("Use a default value for L1="+de_L1+"ppm");
						String de_L1_Conv = Double.toString(Double.parseDouble(de_L1) * 0.001); // For unit conversion (ppm to mg/g)
						
						String de_L1_L3 = defaultL3.getAttribute("Value"); 
						System.out.println("Use a default value for L3="+de_L1_L3+"mg/sq.cm "); // Default L3 value
						return "Default_L1L3@@"+de_L1_Conv +"@@"+de_L1_L3 ;
						}
					}
				}
			}
			} 

		return null;
		}
		
		
	}

