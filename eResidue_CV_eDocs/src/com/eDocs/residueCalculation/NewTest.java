package com.eDocs.residueCalculation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class NewTest {
			
	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException, InterruptedException 
	{
		String CurrenProductName= "P4";
		float LowestoneExpectedL3 = (float) 10.0;
		float Rinsevolume =  10;
		//UniversalSettings();
		//groupingApproach_L0_p11(CurrenProductName);
	}
	
	
 /*static WebDriver driver;
	public static String UniversalSettings() throws IOException, InterruptedException,ClassNotFoundException, SQLException {
		//System.setProperty("webdriver.Chrome.driver","C:\\selenium\\Testing\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver","C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\geckodriver.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();

		capabilities.setCapability("marionette", true);
		
		driver = new FirefoxDriver(capabilities);
		// Open the application
		driver.get("http://192.168.1.111:8090/login");
		Thread.sleep(1000);
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("123456");
		Thread.sleep(3000);
		driver.findElement(By.id("loginsubmit")).click();
		Thread.sleep(1000);
		driver.get("http://192.168.1.111:8090/residue-limit");
		
		//driver.switchTo().alert().accept();
		if (driver.getTitle().equalsIgnoreCase("Report Tracker - eResidue") == false) {
			Thread.sleep(1000);
			String pop = driver.getWindowHandle();
			driver.switchTo().window(pop);
			Thread.sleep(1000);
			driver.findElement(By.xpath(".//*[@id='loginForm']/div[3]/div/input[2]")).click();
		}
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
		Thread.sleep(1000);
		
		// mouse over on Settings menu
		WebElement f = driver.findElement(By.xpath(".//*[@id='settings']"));
		Actions a1 = new Actions(driver);
		Thread.sleep(300);
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
		//System.out.println("defaultmethod option selected---->"+defaultmethod());
		//Thread.sleep(10000);
		//driver.close();
		return defaultmethod();
	}
		
	*/
	
	
public static String defaultmethod() throws SQLException, ClassNotFoundException
		{
				
		//database connection
			Connection connection = Utils.db_connect();
			Statement stmt = (Statement) connection.createStatement();
			Integer defaultLimitOption=0,l1_default_flag=0,l3_default_flag = 0;
			float l1_other_value = 0,l3_other_value=0;
			ResultSet defaultLimit = stmt.executeQuery("SELECT l1_and_l3_option,l1_default_flag,l1_other_value,l3_default_flag,l3_other_value FROM residue_limit");
			while (defaultLimit.next()) 
			{
				 defaultLimitOption = defaultLimit.getInt(1);
				 l1_default_flag = defaultLimit.getInt(2); // use Default value for L1 (option- default or other)
				 l1_other_value = defaultLimit.getFloat(3); // use Default value for L1 value (both default & other value stored)
				 l3_default_flag = defaultLimit.getInt(4); // use Default value for L3 (option- default or other)
				 l3_other_value = defaultLimit.getFloat(5); // use Default value for L3 value (both default & other value stored)
			}
	
	
	
	
		for (int i = 1; i <=defaultLimitOption; i++) 
		{			
				//No Default used. Use calculated value
				if(defaultLimitOption==1)
				{
					return "No_Default";
				}
				
				//Use a Default value for L1
				if(defaultLimitOption==2)
				{					
					//{
						if (l1_default_flag==1) // L1 default value(10ppm)
						{
						 // For unit conversion (ppm to mg/g)
						float de_L1_unitCon = (float) (l1_other_value * 0.001);
						System.out.println("de_L1_unitCon"+de_L1_unitCon);
						return "Default_L1@@"+de_L1_unitCon;
						
						}
						if (l1_default_flag==0) // L1 default other value
						{
						//String de_L1 = driver.findElement(By.id("defautL1OthersValue")).getAttribute("value");
						float de_L1_unitCon = (float) (l1_other_value * 0.001);
						//String de_L1_unitCon = Double.toString(l1_other_value * 0.001); // For unit conversion (ppm to mg/g)
						System.out.println("de_L1_unitCon"+de_L1_unitCon);
						return "Default_L1@@"+de_L1_unitCon;
					
						}											
				}
				
				//Third Radio button is selected
				if(defaultLimitOption==3)
				{					
						if (l3_default_flag==1) // L3 default value 
						{						
							float de_L3 = l3_other_value;
							System.out.println("Use a default value for L3="+de_L3+" mg/sq.cm ");
							return "Default_L3@@"+de_L3;
						}
						if (l3_default_flag==0) // L3 default other value
						{						
							float de_L3 = l3_other_value;
							System.out.println("Use a default value for L3="+de_L3+"mg/sq.cm (other)");
							return "Default_L3@@"+de_L3;
						}					
				}
				
				//Fourth Radio button is selected
				if(defaultLimitOption==4)
				{					
						float de_L1_Conv = (float) (l1_other_value * 0.001); 						
						System.out.println("Use a default value for L1="+de_L1_Conv+"ppm");						
						
						float de_L1_L3 = (float) (l3_other_value ); 						  
						System.out.println("Use a default value for L3="+de_L1_L3+"mg/sq.cm "); // Default L3 value
						return "Default_L1L3@@"+de_L1_Conv +"@@"+de_L1_L3 ;											
				}
			
			} // closing for loop
		connection.close();
		return null;
		}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*//get current product equipment Train
		public static Float getEquipmentTrain(String CurrenProductName,Float LowestoneExpectedL3) throws SQLException, ClassNotFoundException, IOException  
		{
			XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); 
			XSSFSheet sheet = workbook.getSheet("test");
			
	        int currentproductID = 0, currentproductsetcount = 0; float L4cTrain=0;
	       
	        //database connection
	        Connection connection = Utils.db_connect();
	        Statement stmt = connection.createStatement();
	        
	        //current product equipment set
	        // List<Integer> Currentsetcount = new ArrayList<>();
	        ResultSet currentprod = stmt.executeQuery("SELECT * FROM product where name='" + CurrenProductName + "'"); // get product name id
	        while (currentprod.next()) {
	            currentproductID = currentprod.getInt(1);
	           // Currentsetcount.add(currentprod.getInt(33));
	            currentproductsetcount = currentprod.getInt(33); 
	        }
	        
	        
	        List<Object> setlist = new ArrayList<>();
	        List<Float> equipSetTotalSF = new ArrayList<>();
	        List<Object> equipSetNamelist = new ArrayList<>();
	        
	        	int L4Row = 41;
	        
	        for (int i = 1; i <= currentproductsetcount; i++) 
	        { 
	        	List<Integer> currentequipmentID = new ArrayList<>();
	        	 
	 //check if only equipmnet used in the product
	            ResultSet getequipfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_equipments where product_id='" + currentproductID + "' && set_id ='" + i + "'"); // get product name id
	            while (getequipfromset.next()) 
	            {
	                System.out.println("ony equipment selected");
	                currentequipmentID.add(getequipfromset.getInt(4));
	            }
	 //check if only equipment group used in the product -current product
	           
	            List<Integer> eqgroupIDs = new ArrayList<>(); // if equipment  group means - use the below query
	            // List<Integer> equipmentgroup = new ArrayList<>();
	            ResultSet getequipgrpfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + currentproductID + " && set_id =" + i + ""); // get product name id
	            while (getequipgrpfromset.next()) {
	                System.out.println("ony equipment group selected");
	                eqgroupIDs.add(getequipgrpfromset.getInt(4)); // get group ID
	            }
	            for (int id : eqgroupIDs) // iterate group id one by one 
	            {
	                int equipmentusedcount = 0;
	                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + currentproductID + " && group_id=" + id + ""); // get product name id
	                while (geteqcountfromgrpID.next()) 
	                {
	                    equipmentusedcount = geteqcountfromgrpID.getInt(5);
	                }
	                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + id + " order by sorted_id limit " + equipmentusedcount + ""); // get product name id
	                while (geteqfromgrpID.next()) 
	                {
	                    currentequipmentID.add(geteqfromgrpID.getInt(2));
	                }
	                //  currentequipmentID.addAll(equipmentgroup);
	            }
	            
	//end: check if only equipment group used in the product -current product
	//check if only equipment train used in the product -current product
	            int gettrainID = 0;
	            ResultSet getequiptrainIDfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_train where product_id=" + currentproductID + " && set_id =" + i + ""); // get product name id
	            while (getequiptrainIDfromset.next()) {
	                System.out.println("ony equipment train selected");
	                gettrainID = getequiptrainIDfromset.getInt(4);
	            }
	            // if train used only equipmeans used the below query
	            ResultSet eqfromtrain = stmt.executeQuery("SELECT * FROM equipment_train_equipments where train_id=" + gettrainID + ""); // get product name id
	            while (eqfromtrain.next()) {
	                currentequipmentID.add(eqfromtrain.getInt(2));
	            }
	            
	           System.out.println("gettrainID-->"+gettrainID); 
                
	            // if train used group means - use the below query
	            Set<Integer> groupIDs = new HashSet<>();
	            ResultSet eqfromtraingroup = stmt.executeQuery("SELECT group_id FROM equipment_train_group where train_id=" + gettrainID + ""); // get product name id
	            while (eqfromtraingroup.next()) {
	                groupIDs.add(eqfromtraingroup.getInt(1));
	            }

                
	            for (int id : groupIDs) // iterate group id one by one (from train)
	            {
	                //Set<Integer> equipID = new HashSet();
	                int equipmentusedcount = 0;
	                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT equipment_used_count FROM equipment_train_group where group_id=" + id + ""); // get product name id
	                while (geteqcountfromgrpID.next()) {
	                    equipmentusedcount = geteqcountfromgrpID.getInt(1);
	                }
	                System.out.println("Train group count"+equipmentusedcount);
	                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + id + " order by sorted_id limit " + equipmentusedcount + ""); // get product name id
	                while (geteqfromgrpID.next()) 
	                {
	                    currentequipmentID.add(geteqfromgrpID.getInt(2));
	                }
    
	            }
	            
	//end: check if only equipment train used in the product -current product 
	           
	            List<String>  eqnamelist = new ArrayList<>();	          
            	float surfaceArea = 0;
            	float equipmentTotalSF=0;
	            for(Integer eqid:currentequipmentID)
	            {
	            	//------------->if equipment reused in equipment train
	                Integer equipreusedID=0,equipment_used_count=0;   
	                ResultSet equipreused = stmt.executeQuery("SELECT equipment_id,equipment_used_count FROM train_equipment_count where train_id=" + gettrainID + " && equipment_id="+eqid+""); // get product name id
	                if(equipreused!=null)
	                {
	                	while (equipreused.next()) 
	                	{
	                		equipreusedID = equipreused.getInt(1);
	                		equipment_used_count = equipreused.getInt(2);
	                		// currentequipmentID.add(equipreused.getInt(2));
	                	}
	                	System.out.println("equipment_used_count"+equipment_used_count);
	                //get eqiupment surface area (for reused equipment)
	                	
	                	float equipSF=0;
	                	String equipreusedName =null;
	                	ResultSet equipreusedSf = stmt.executeQuery("SELECT surface_area,name FROM equipment where id=" + equipreusedID + ""); // get product name id
	                	while (equipreusedSf.next()) 
	                	{
	                		equipSF = equipreusedSf.getFloat(1);
	                		equipreusedName = equipreusedSf.getString(2);	                	
	                		//equipment_used_count = equipreusedSf.getInt(1);
	                		//currentequipmentID.add(equipreusedSf.getInt(2));
	                	}
	                
	                equipmentTotalSF = equipSF * equipment_used_count;
	                System.out.println(" ------>equipment reused-"+equipmentTotalSF);	
	                } //<------------------ending if equipment reused in equipment train
	            	
	                
	            	ResultSet getequipdetails = stmt.executeQuery("SELECT name,surface_area FROM equipment where id="+eqid+"");
	            	while(getequipdetails.next())
	            	{
	            		if(equipment_used_count==0)
	                	{
	            			eqnamelist.add(getequipdetails.getString(1));
	                	}else 
	                	{
	                		eqnamelist.add(getequipdetails.getString(1)+"("+equipment_used_count+")");	
	                	}
	            		surfaceArea = (surfaceArea + getequipdetails.getFloat(2) + equipmentTotalSF);
	            	}
	            }
	            
	            System.out.println("Equipment Name Setlist-> "+eqnamelist);
	            setlist.add(currentequipmentID);
	            equipSetTotalSF.add(surfaceArea);
	            equipSetNamelist.add(eqnamelist);
	            System.out.println("surfaceArea-->"+surfaceArea);
	            System.out.println("equipSetTotalSF-> "+surfaceArea);
	            
	           
		        // Get Train rinse volume for each set
		        		Integer trainID = null ;
		        	    ResultSet set = stmt.executeQuery("SELECT train_id FROM product_equipment_set_train where product_id=" + currentproductID + " && set_id="+i+"");
			         	while(set.next())
			         	{
			         		trainID = set.getInt(1);       		
			         	}
		      
			        float TrainRinsevolume=0;
		 	        ResultSet eqtrain = stmt.executeQuery("SELECT rinse_volume FROM equipment_train where id="+trainID+"");
		         	while(eqtrain.next())
		         	{
		         		TrainRinsevolume = eqtrain.getFloat(1);       		
		         	}
		      
		         	Calculation getrinsevolume = new Calculation();
		         	getrinsevolume.eqRinseVolume();
		         	
		         	System.out.println("getrinsevolume.eqRinseVolume() "+getrinsevolume.eqRinseVolume());
		         // equipment rinse volume()
					 if(getrinsevolume.eqRinseVolume()==0) //check rinset from univ setting or each equipment
					 {
						 L4cTrain = (LowestoneExpectedL3 *  surfaceArea) / (TrainRinsevolume * 1000) ;
						 Cell equipRinse = sheet.getRow(L4Row).getCell(32);
						 equipRinse.setCellValue(TrainRinsevolume); 
						 
						 Cell trainL4c = sheet.getRow(L4Row).getCell(33);
						 trainL4c.setCellValue(L4cTrain); 		
						 System.out.println("L4cTrain"+L4cTrain);
					 }else {
						 L4cTrain = (float) ((LowestoneExpectedL3 *  surfaceArea) / (getrinsevolume.eqRinseVolume() * 1000)) ;
						 Cell equipRinse = sheet.getRow(L4Row).getCell(32);
						 equipRinse.setCellValue(getrinsevolume.eqRinseVolume()); 
						 
						 Cell trainL4c = sheet.getRow(L4Row).getCell(33);
						 trainL4c.setCellValue(L4cTrain); 		
						 System.out.println("L4cTrain"+L4cTrain);
					 }
					 
		         	System.out.println("surfaceArea: "+surfaceArea);
		         	System.out.println("TrainRinsevolume: "+TrainRinsevolume);
		 	     //   L4cTrain = (LowestoneExpectedL3 *  surfaceArea) / (TrainRinsevolume * 1000) ;
		 	        System.out.println("L4cTrain "+L4cTrain);      
		 	        
		 	       L4Row++;
		 	       
		 	      // System.out.println("equipSetNamelist"+equipSetNamelist);
	        }
	        L4Row++;
	        
	        System.out.println("Equipment ID Setlist-> "+setlist);  	        
	        System.out.println("Equipment SF Setlist-> "+equipSetTotalSF);	        
	        System.out.println("Equipment Name Setlist-> "+equipSetNamelist);

	               
	        connection.close();
	        writeTooutputFile(workbook); // write output into work sheet
	        return L4cTrain;
	        
	    }*/
		
		
		
		
		
		
		
		// Write output and close workbook
		public static void writeTooutputFile(Workbook workbook) throws IOException {
			try {
				FileOutputStream outFile = new FileOutputStream(new File(Constant.EXCEL_PATH_Result));
				workbook.write(outFile);
				outFile.close();
			} catch (Exception e) {
				throw e;
			}
		}
	
	
	
	
	
}