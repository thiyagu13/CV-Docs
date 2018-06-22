package com.eDocs.microbialCalculation;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;
import com.eDocs.residueCalculation.SurfaceAreaValue;

public class MicrobialCalculation {
	
	//static String currentproductname = "P11";
//	static String nextproductname = "P11";
	/*String productName1 ="P11";
	String productName2 ="P22";
	String productName3 ="P33";
	String productName4 ="P44";*/
	WebDriver driver;
	static String tenant_id= Constant.tenant_id;
	
	
	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{/*
		
		//XSSFWorkbook workbook;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH); 
		XSSFSheet sheet = workbook.getSheet("microbial_calculation_result");
		
		driver = new FirefoxDriver();
		// Open the application
		driver.get("http://192.168.1.111:8090/login");
		Thread.sleep(1000);
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("123456");
		Thread.sleep(3000);
		driver.findElement(By.id("loginsubmit")).click();
		Thread.sleep(1000);
		driver.get("http://192.168.1.111:8090/products");
		Thread.sleep(1000);
		driver.findElement(By.id("searchEquipment")).sendKeys("P11");
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='dLabel']/i")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[9]/div/ul/li[2]/a")).click(); //click calculation window
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='calculateProductForm']/div[2]/div[2]/div/div/label")).click(); // click manual calculation option
		Thread.sleep(1000);
		String title = "Test Calculation";
		driver.findElement(By.name("title")).sendKeys(title);//title of calculation
		Thread.sleep(1000);
		WebElement limitselection = driver.findElement(By.id("limitSelection")); // select residue limit
		Select limit = new Select(limitselection);
		limit.selectByIndex(2);
		Thread.sleep(1000);
		WebElement selectproduct = driver.findElement(By.xpath(".//*[@id='routeAdminDiv']/div/div/span/span[1]/span/ul/li/input"));

		int row =8;
		for(int i = 0;i<=100;i++)
		{
			String getproductname = sheet.getRow(row).getCell(2).getStringCellValue(); //get product name list from excel (for using calculation)
			System.out.println("getproductname"+getproductname);
			if(!StringUtils.isEmpty(getproductname))
			{
				selectproduct.sendKeys(getproductname);
				Thread.sleep(300);
				selectproduct.sendKeys(Keys.ENTER);
				Thread.sleep(300);
				row++;
			}else {
				break;
			}
		}
		driver.findElement(By.id("changeControlNo")).sendKeys("123");
		
		
		
		Select select = new Select(driver.findElement(By.id("productGroups"))); // get seleced products from the dropdown
		List<WebElement> option = select.getAllSelectedOptions();

		Set <String> selectedproducts = new HashSet<>();
		for(WebElement iterateselectedproducts: option)
		{
			 selectedproducts.add(iterateselectedproducts.getText());
		}
		
		System.out.println("selectedproducts-->"+selectedproducts);
		
		driver.findElement(By.id("saveCalculateProduct")).click(); // Click calculation submit button
		
		//get duplication calculation
		String gettitle = driver.findElement(By.xpath("html/body/div[18]/div/span")).getText();
		Thread.sleep(500);
		driver.findElement(By.xpath("html/body/div[18]/div/button")).click();
		
		Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
		for(int k=45;k<1000;k++)
		{
			j.add(k);
		}
		
		List<Integer> iteratecaltitle = new ArrayList<>();
		iteratecaltitle.addAll(j);
		
		System.out.println("iteratecaltitle-->"+iteratecaltitle);
		if(gettitle.equals("Calculation '"+title+"' already exists!"))
		{
			for(Integer i:iteratecaltitle)
			{
			WebElement caltitle1 = driver.findElement(By.name("title"));//title of calculation
			caltitle1.clear();
			caltitle1.sendKeys(title+i);
			driver.findElement(By.id("saveCalculateProduct")).click(); // Click calculation submit button
			Thread.sleep(500);
			try
			{
				if(driver.findElement(By.xpath("html/body/div[18]/div/span"))!=null)
				{
					
					System.out.println(driver.findElement(By.xpath("html/body/div[18]/div/span"))==null);
					String title_duplicate = driver.findElement(By.xpath("html/body/div[18]/div/span")).getText();
					System.out.println("title_duplicate: "+title_duplicate);
					driver.findElement(By.xpath("html/body/div[18]/div/button")).click(); //close the duplicate validation alert
					if(title_duplicate.equals("Calculation '"+title+i+"' already exists!"))
					{
						iteratecaltitle.lastIndexOf(i);
						System.out.println("Remove---->"+iteratecaltitle.lastIndexOf(i));
						System.out.println("iteratecaltitle " +iteratecaltitle);
						System.out.println("duplicate");
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
		
		Thread.sleep(300);
		
		List<String>  currentproductlist = new ArrayList<>(); //store product list
  		currentproductlist.addAll(selectedproducts);
  		
		List<String>  nextproductlist = new ArrayList<>(); //store product list
		nextproductlist.addAll(selectedproducts);*/
		Set<String> selectedproducts = new HashSet<>();
		selectedproducts.add("L3 min batch L");
		selectedproducts.add("L4 min batch L");
		//selectedproducts.add("S3");
		
		List<String>  currentproductlist = new ArrayList<>(); //store product list
  		currentproductlist.addAll(selectedproducts);
  		
		List<String>  nextproductlist = new ArrayList<>(); //store product list
		nextproductlist.addAll(selectedproducts);
		
		microbialcalculation(currentproductlist, nextproductlist);
		
		//writeTooutputFile(workbook); // write output into work sheet
	}
	
	
	
		static float L3SurfaceLimit,L3SurfacelimitContactORSwab,L3Rinse,EndoToxinResult;
		public static void microbialcalculation(List<String> currentproduct,List<String> nextproduct) throws SQLException, ClassNotFoundException, IOException
		{
			XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH); 
			XSSFSheet sheet = workbook.getSheet("microbial_calculation_result");
			//database connection
			Connection connection = Utils.db_connect();
			Statement stmt = (Statement) connection.createStatement();
			
			ResultSet microbialoption = stmt.executeQuery("SELECT limits_setup_option,bioburden_limit_option,bioburden_contribution,contact_plate_surface_area,\n" + 
					"var_factor_micro_enum,surface_sample_option,preset_apply_default_limit_for_surface,other_default_limit_value_for_surface_value,other_surface_sample_calculate_compare_default_value,rinse_sample_option,preset_apply_default_limit_for_rinse,other_default_limit_for_rinse_value,rinse_sample_calculate_compare_default,other_rinse_sample_calculate_compare_default_value,default_endotoxin_limit,default_rinse_limit_nmt FROM microbial_limit where tenant_id='"+tenant_id+"'");
		  	int microLimitOption = 0,bioburderLimit = 0,surfacelimitoption=0,bioburdenContribution=0,factorMicroEnumeraion=0,
		  			rinseoption=0,rinsedefaultOption=0,rinsebothdefaultvalueOption = 0,endotoxinDefaultvalueOption = 0;
		  	float rinsedefaultvalueOther=0,surfaceDefaultvalue1 = 0,
		  			surfaceDefaultvalueOther = 0,ContactPlateORSwab=0,surfaceBothDefaultvalue=0,surfaceBothDefaultvalueOther=0,rinsebothdefaultvalueOther=0,endotoxinDefaultvalue=0;
		  	
		  		while (microbialoption.next())  //check microbial option whether bioburden or both
				{
		  			microLimitOption = microbialoption.getInt(1);
		  			System.out.println("==>"+microLimitOption);
		  			bioburderLimit = microbialoption.getInt(2);
					bioburdenContribution = microbialoption.getInt(3);
					ContactPlateORSwab = microbialoption.getFloat(4);
					factorMicroEnumeraion = microbialoption.getInt(5);

					surfacelimitoption = microbialoption.getInt(6);
					surfaceDefaultvalue1 = microbialoption.getFloat(7);
					surfaceDefaultvalueOther = microbialoption.getFloat(8);
					surfaceBothDefaultvalueOther = microbialoption.getFloat(9);
					
					rinseoption = microbialoption.getInt(10);
					rinsedefaultOption = microbialoption.getInt(11);
					rinsedefaultvalueOther = microbialoption.getFloat(12); //rinse other default value
					
					rinsebothdefaultvalueOption = microbialoption.getInt(13); //rinse both option (1 or 2 other)
					rinsebothdefaultvalueOther = microbialoption.getFloat(14);
					
					endotoxinDefaultvalueOption = microbialoption.getInt(15);
					endotoxinDefaultvalue = microbialoption.getFloat(16); //rinse default value 2
				}
			
		  		List<String>  currentproductlist = new ArrayList<>();
		  		currentproductlist.addAll(currentproduct);
		  		
		  		
	  		List<String>  nextproductlist = new ArrayList<>();
	  		nextproductlist.addAll(nextproduct);
	  		
		  		/*List<String>  currentproductlist = new ArrayList<>();
		  		currentproductlist.add(productName1);
		  		currentproductlist.add(productName2);
		  		currentproductlist.add(productName3);
		  		currentproductlist.add(productName4);
		  		
	  		List<String>  nextproductlist = new ArrayList<>();
	  		nextproductlist.add(productName1);
	  		nextproductlist.add(productName2);
	  		nextproductlist.add(productName3);
	  		nextproductlist.add(productName4);*/
		  		
	  		int row=8,surface=6,surfaceontact=9,printrinse=12,printCurrentpname=4,printNextpname=5;
		  		for(String currentproductname:currentproductlist)
		  		{
		  			System.out.println("Current Product--->"+currentproductname);
		  			Cell currentprodname = sheet.getRow(row).getCell(printCurrentpname);
			  		currentprodname.setCellValue(currentproductname); 
			  		
		  			for(String nextproductname:nextproductlist)
		  			{
		  				
		  		//if only Surface Limit Selected Selected
		  				System.out.println("-->"+microLimitOption);
		  		if(microLimitOption==1 || microLimitOption==3) //bioburden(1) or endotoxin(2) or both(3)
		  		{
		  			System.out.println(bioburderLimit);
		  			if(bioburderLimit==1 || bioburderLimit==3)  //Surface(1) or rinse(2) or both(3)
		  			{
		  				if(surfacelimitoption==1) //Surface-no default(1)
		  				{
		  					L3SurfaceLimit= BioburdensurfaceLimit(currentproductname,nextproductname);
		  					L3SurfacelimitContactORSwab = surfaceLimitContactPlateORSwab(currentproductname,nextproductname);
		  					System.out.println("L3SurfaceLimit--->: "+L3SurfaceLimit);
		  				}
		  				if(surfacelimitoption==2) //Surface- default(2) 
		  				{
		  					if(surfaceDefaultvalueOther==0)
		  					{
		  						L3SurfaceLimit = surfaceDefaultvalue1;
	  						L3SurfacelimitContactORSwab = surfaceDefaultvalue1 * ContactPlateORSwab;
		  					}else
		  					{
		  						L3SurfaceLimit = surfaceDefaultvalueOther;
	  						L3SurfacelimitContactORSwab = surfaceDefaultvalueOther * ContactPlateORSwab;
		  					}
		  				}
		  				if(surfacelimitoption==3) //Surface- Both default & no default (3) 
		  				{
		  					if(surfaceBothDefaultvalueOther==0) // surface both - if other value not set
		  					{
		  						if(BioburdensurfaceLimit(currentproductname,nextproductname)<1)  // surface both - if other value not set
		  						{
		  							L3SurfaceLimit = BioburdensurfaceLimit(currentproductname,nextproductname);
		  							L3SurfacelimitContactORSwab = L3SurfaceLimit * ContactPlateORSwab;
		  						}else
		  						{
		  							L3SurfaceLimit = 1;
		  							L3SurfacelimitContactORSwab = L3SurfaceLimit * ContactPlateORSwab;
		  							System.out.println("============>"+L3SurfaceLimit);
		  						}
		  				
		  					}if(surfaceBothDefaultvalueOther!=0) // surface both - if other value not set
		  					{
		  						if(BioburdensurfaceLimit(currentproductname,nextproductname)<surfaceBothDefaultvalueOther)  // surface both - if other value not set
	  						{
	  							L3SurfaceLimit = BioburdensurfaceLimit(currentproductname,nextproductname);
	  							L3SurfacelimitContactORSwab = L3SurfaceLimit * ContactPlateORSwab;
	  						}else
	  						{
	  							L3SurfaceLimit = surfaceBothDefaultvalueOther;
  							L3SurfacelimitContactORSwab = L3SurfaceLimit * ContactPlateORSwab;
	  						}
		  					}
		  				}
		  				
		  			}
		  		}
		  		
		  	//if only rinse Limit Selected Selected
		  		if(microLimitOption==1 || microLimitOption==3) //bioburden(1) or endotoxin(2) or both(3)
		  		{
		  			if(bioburderLimit==2 || bioburderLimit==3)  //Surface(1) or rinse(2) or both(3)
		  			{
		  				if(rinseoption==1) //Rinse-no default(1) 
		  				{
		  					L3Rinse= rinseLimit(currentproductname,nextproductname, L3SurfaceLimit);
		  				}
		  				
		  				if(rinseoption==2) //Rinse - default(2) 
		  				{
		  					if(rinsedefaultOption==1) //rinse default value 1
		  					{
		  						L3Rinse = 1; //(NMT 1 CFU/10mL)
		  					}
		  					if(rinsedefaultOption==2) //rinse default value 2
	  					{
		  						L3Rinse = 100;
	  					}
		  					if(rinsedefaultOption==3) //rinse other default value 
		  					{
		  						L3Rinse = rinsedefaultvalueOther;
		  					}	
		  				}
		  				
		  			if(rinseoption==3) //Rinse - Compare default & no default(3) 
	  				{
		  					if(rinsebothdefaultvalueOption==1) //compare default 1 value with calculated value
		  					{
		  						if(rinseLimit(currentproductname,nextproductname,L3SurfaceLimit)<0.1)
		  						{
		  							L3Rinse = rinseLimit(currentproductname,nextproductname,L3SurfaceLimit);
		  						}else
		  						{
		  							L3Rinse = (float)0.1;
		  						}
		  					}
		  					if(rinsebothdefaultvalueOption==2) //compare default 100 value with calculated value
		  					{
		  						if(rinseLimit(currentproductname,nextproductname,L3SurfaceLimit)<100)
		  						{
		  							L3Rinse = rinseLimit(currentproductname,nextproductname,L3SurfaceLimit);
		  						}else
		  						{
		  							L3Rinse = 100;
		  						}
		  					}
		  					if(rinsebothdefaultvalueOption==3) //compare default other value with calculated value
		  					{
		  						if(rinseLimit(currentproductname,nextproductname,L3SurfaceLimit)<rinsebothdefaultvalueOther)
		  						{
		  							L3Rinse = rinseLimit(currentproductname,nextproductname,L3SurfaceLimit);
		  						}else
		  						{
		  							L3Rinse = rinsebothdefaultvalueOther;
		  						}
		  					}
		  					
	  				}
		  		}
		  	}
		  		
		  		
		  		//if endotoxin result
		  		if(microLimitOption==2 || microLimitOption==3) //bioburden(1) or endotoxin(2) or both(3)
		  		{
		  			if(endotoxinDefaultvalueOption==1)
		  			{
		  				EndoToxinResult = (float) 0.25;
		  			}
		  			if(endotoxinDefaultvalueOption==2)
	  			{
	  				EndoToxinResult = endotoxinDefaultvalue;
	  			}
		  		}
		  		
		  	System.out.println("currentproductname-->"+currentproductname);	
		  	System.out.println("nextproductname-->"+nextproductname);
		  	System.out.println("L3SurfaceLimit: "+L3SurfaceLimit);
	  		System.out.println("L3SurfacelimitContactORSwab: "+L3SurfacelimitContactORSwab);
	  		System.out.println("L3Rinse: "+L3Rinse);
	  		System.out.println("EndoToxinResult: "+EndoToxinResult);
	  		
	  		
	  		Cell nextprodname = sheet.getRow(row).getCell(printNextpname); 
	  		nextprodname.setCellValue(nextproductname); 
	  		
	  		//Print expected result
	  		Cell SurfaceLimit = sheet.getRow(row).getCell(surface); 
	  		SurfaceLimit.setCellValue(L3SurfaceLimit); 
    		Cell SurfaceLimitContorswab = sheet.getRow(row).getCell(surfaceontact);
    		SurfaceLimitContorswab.setCellValue(L3SurfacelimitContactORSwab); 
    		Cell rinse = sheet.getRow(row).getCell(printrinse);
    		rinse.setCellValue(L3Rinse); 
    		
    		
    		//print actual result
    		int currentproductID=0,nextproductID = 0;
    		//get current product id
    		ResultSet getcurrentProductID = stmt.executeQuery("SELECT * FROM product where name ='"+currentproductname+"' && tenant_id='"+tenant_id+"'");
			while (getcurrentProductID.next())  //check surface or both
			{	
				currentproductID = getcurrentProductID.getInt(1);
			}
			//get next product id
    		ResultSet getnextProductID = stmt.executeQuery("SELECT * FROM product where name ='"+nextproductname+"' && tenant_id='"+tenant_id+"'");
			while (getnextProductID.next())  //check surface or both
			{	
				nextproductID = getnextProductID.getInt(1);
			}
    		
			float actualSurfaceLimit=0,actualSurfaceContactPlateSwab=0,actulaRinse=0;
    		ResultSet bioburdensurfaceResult = stmt.executeQuery("SELECT * FROM microbial_bioburden_results where product_id="+currentproductID+" and next_product_ids="+nextproductID+" and rinse_or_surface="+1+" && tenant_id='"+tenant_id+"'");
			while (bioburdensurfaceResult.next())  //check surface or both
			{	
				actualSurfaceLimit = bioburdensurfaceResult.getFloat(2);
				actualSurfaceContactPlateSwab = bioburdensurfaceResult.getFloat(4);
			}
			ResultSet bioburdenrinseResult = stmt.executeQuery("SELECT * FROM microbial_bioburden_results where product_id="+currentproductID+" and next_product_ids="+nextproductID+" and rinse_or_surface="+2+" && tenant_id='"+tenant_id+"'");
			while (bioburdenrinseResult.next())  //check surface or both
			{	
				actulaRinse = bioburdenrinseResult.getFloat(3);
			}
    		
			int actualsurfacelimitColumn =7,actualsurfacelimitContantPlateColumn=10,actualrinselimitColumn=13;
			
			if(actualSurfaceLimit!=0) //print actual surface limit value in excel
			{
			Cell actualSurfaceLimitresult = sheet.getRow(row).getCell(actualsurfacelimitColumn); 
			actualSurfaceLimitresult.setCellValue(actualSurfaceLimit);
			}else
			{
				Cell actualSurfaceLimitresult = sheet.getRow(row).getCell(actualsurfacelimitColumn); 
				actualSurfaceLimitresult.setCellValue("");
			}
			
			if(actualSurfaceContactPlateSwab!=0) //print actual surface limit contact plate/swab value in excel
			{
				Cell actulasurfaceLimitContorswabResult = sheet.getRow(row).getCell(actualsurfacelimitContantPlateColumn);
	    		actulasurfaceLimitContorswabResult.setCellValue(actualSurfaceContactPlateSwab);
			}else
			{
				Cell actulasurfaceLimitContorswabResult = sheet.getRow(row).getCell(actualsurfacelimitContantPlateColumn);
	    		actulasurfaceLimitContorswabResult.setCellValue("");
			}
    		
    		if(actulaRinse!=0) //print actual rinse value in excel
    		{
    		Cell actualrinseresult = sheet.getRow(row).getCell(actualrinselimitColumn);
    		actualrinseresult.setCellValue(actulaRinse);
    		}else
    		{
    			Cell actualrinseresult = sheet.getRow(row).getCell(actualrinselimitColumn);
        		actualrinseresult.setCellValue("");
    		}
    		
    		
    		int surfacestatus=8;
    		if(actualSurfaceLimit!=0)// if actual surface value not zero
    		{
    			if(Utils.toOptimizeDecimalPlacesRoundedOff(L3SurfaceLimit).equals(Utils.toOptimizeDecimalPlacesRoundedOff(actualSurfaceLimit))) //verify expected surface and actual surface limit value
    			{
    			Cell surfacestatusresult = sheet.getRow(row).getCell(surfacestatus);
    			surfacestatusresult.setCellValue("Pass");
    			surfacestatusresult.setCellStyle(Utils.style(workbook, "Pass"));
    			}else {
    			Cell surfacestatusresult = sheet.getRow(row).getCell(surfacestatus);
    			surfacestatusresult.setCellValue("Fail");
    			surfacestatusresult.setCellStyle(Utils.style(workbook, "Fail"));
    			}
    			
    		}
    		int surfacecontactstatus=11;
    		if(actualSurfaceContactPlateSwab!=0)//if actual surface contact value not zero
    		{
    			if(Utils.toOptimizeDecimalPlacesRoundedOff(L3SurfacelimitContactORSwab).equals(Utils.toOptimizeDecimalPlacesRoundedOff(actualSurfaceContactPlateSwab))) //verify expected surface and actual surface limit value
    			{
    			Cell surfacestatusresult = sheet.getRow(row).getCell(surfacecontactstatus);
    			surfacestatusresult.setCellValue("Pass");
    			surfacestatusresult.setCellStyle(Utils.style(workbook, "Pass"));
    			}else {
    			Cell surfacestatusresult = sheet.getRow(row).getCell(surfacecontactstatus);
    			surfacestatusresult.setCellValue("Fail");
    			surfacestatusresult.setCellStyle(Utils.style(workbook, "Fail"));
    			}
    			
    		}
    		
    		int rinsestatus=14;
    		if(actulaRinse!=0)//if actual surface contact value not zero
    		{
    			if(Utils.toOptimizeDecimalPlacesRoundedOff(L3Rinse).equals(Utils.toOptimizeDecimalPlacesRoundedOff(actulaRinse))) //verify expected surface and actual surface limit value
    			{
    			Cell surfacestatusresult = sheet.getRow(row).getCell(rinsestatus);
    			surfacestatusresult.setCellValue("Pass");
    			surfacestatusresult.setCellStyle(Utils.style(workbook, "Pass"));
    			}else {
    			Cell surfacestatusresult = sheet.getRow(row).getCell(rinsestatus);
    			surfacestatusresult.setCellValue("Fail");
    			surfacestatusresult.setCellStyle(Utils.style(workbook, "Fail"));
    			}
    		}
    		row++;
    		
		  		} // closing next product list for loop
		  			
		  		} // closing current product list for loop
		  		
		  	connection.close();	  		
		  	writeTooutputFile(workbook); // write output into work sheet
		  	
			//return 0;
		}
		
	
	
	
	
	
	
	
  // Get adjusted Bsp value for each current product
  public static float adjustedBSP(String currentproductname) throws SQLException, ClassNotFoundException 
  {
	//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
	  ResultSet microbialoption = stmt.executeQuery("SELECT limits_setup_option FROM microbial_limit where tenant_id='"+tenant_id+"'");
	  	int microLimitOption = 0,bioburderLimit = 0,surfacelimitoption=0,bioburdenContribution=0,
	  			factorMicroEnumeraion=0,rinseoption=0,endotoxinDefaultvalueOption=0;
	  	float adjusted_BSP = 0,productBSP=0;
		while (microbialoption.next())  //check microbial option whether bioburder or both
		{	
		microLimitOption = microbialoption.getInt(1);
		}
			if(microLimitOption==1 || microLimitOption==3)
			{
				ResultSet bioburderLimitOption = stmt.executeQuery("SELECT bioburden_contribution,var_factor_micro_enum,bioburden_limit_option,surface_sample_option,rinse_sample_option,default_endotoxin_limit FROM microbial_limit where tenant_id='"+tenant_id+"'");
				while (bioburderLimitOption.next())  //check surface or both
				{	
					bioburdenContribution = bioburderLimitOption.getInt(1);
					factorMicroEnumeraion = bioburderLimitOption.getInt(2);
					bioburderLimit = bioburderLimitOption.getInt(3);
					surfacelimitoption = bioburderLimitOption.getInt(4);
					rinseoption = bioburderLimitOption.getInt(5);
					endotoxinDefaultvalueOption = bioburderLimitOption.getInt(6);
				}
				//get basp value rom product
				ResultSet bspfromProduct = stmt.executeQuery("SELECT * FROM product where name='"+currentproductname+"' && tenant_id='"+tenant_id+"'");
				while (bspfromProduct.next())  //check surface or both
				{	
					productBSP = bspfromProduct.getFloat(11);
				}
				if(bioburderLimit == 1 || bioburderLimit==2 || bioburderLimit ==3  ) // check surface or rinse or both
				{
					if(surfacelimitoption ==1 || surfacelimitoption==3 || rinseoption ==1 || rinseoption==3) //check surface limit whther default or not default or both
					{
					float bioburden = 100-bioburdenContribution;
					adjusted_BSP = productBSP * ((bioburden/100) / factorMicroEnumeraion);
					//Adjusted_BSP = BSP x (100-Bioburden contribution)% / factor
					
					}
				}
			}
			connection.close();
			return adjusted_BSP;
	}
  
  
  		/*//get Next Product total surface area value for L3 Surface bioburden
  		public static float NextproductTotalSF(String nextproductname) throws ClassNotFoundException, SQLException
  		{
  			Float nextProductTotalSurfaceArea;
			int nextproductID = 0,nextproductsetcount = 0;
			Connection connection = Utils.db_connect();
			Statement stmt = (Statement) connection.createStatement();// Create Statement Object
			
		    //next product equipment set
		    List<Integer> Nextsetcount = new ArrayList<>();
		    ResultSet nextprod = stmt.executeQuery("SELECT * FROM product where name='" + nextproductname + "' && tenant_id='"+tenant_id+"'"); // get product name id
		    while (nextprod.next()) {
		    	nextproductID = nextprod.getInt(1);
		    	Nextsetcount.add(nextprod.getInt(33));
		    	nextproductsetcount = nextprod.getInt(33);
		    }
		   
		   List<Float> nextProdeqSettotalSF = new ArrayList<>();
		   List<Integer> equipmentgroupNextProd = new ArrayList<>();
//Next product equipment set and total surface area
		    for(int i=1;i<=nextproductsetcount;i++)
		    {
		    	Set<Integer> equipments = new HashSet<>();
		    	ResultSet getequipfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_equipments where product_id='" + nextproductID + "' && set_id ='" + i + "' && tenant_id='"+tenant_id+"'"); // get product name id
		 	    while (getequipfromset.next()) 
		 	    {
		 	    	equipments.add(getequipfromset.getInt(4));
		 	    }
		 	    
		 	    
//check if only equipment group used in the product -Next product
		 	// if train used group means - use the below query
			 	   List<Integer> eqgroupIDs = new ArrayList<>();
			 	   ResultSet getequipgrpfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + nextproductID + " && set_id =" + i + " && tenant_id='"+tenant_id+"'"); // get product name id
			 	   while (getequipgrpfromset.next())
			 	   {
			 		  		eqgroupIDs.add(getequipgrpfromset.getInt(4));
			 	   }
			 	   for(int id:eqgroupIDs) // iterate group id one by one (from train)
			 	   {
			 		   int equipmentusedcount = 0;
			 		   ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + nextproductID + " && group_id=" + id +" && tenant_id='"+tenant_id+"'"); // get product name id
				 	   while (geteqcountfromgrpID.next()) 
				 	   {
				 		 equipmentusedcount = geteqcountfromgrpID.getInt(5);
				 	   }
				 	   ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + id +" && tenant_id='"+tenant_id+"' order by sorted_id limit "+equipmentusedcount+""); // get product name id
				 	   while (geteqfromgrpID.next()) 
				 	   {
				 		  equipmentgroupNextProd.add(geteqfromgrpID.getInt(2));
				 	   }
				 	   equipments.addAll(equipmentgroupNextProd);
			 	   }
			      
//End: check if only equipment group used in the product -Next product
			 	   

//check if only equipment train used in the product -current product
			       int nextprodtrainID = 0;
			 	   ResultSet nextgetequiptrainIDfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_train where product_id=" + nextproductID + " && set_id =" + i + " && tenant_id='"+tenant_id+"'"); // get product name id
			 	   while (nextgetequiptrainIDfromset.next())
			 	   {
			 		  		nextprodtrainID = nextgetequiptrainIDfromset.getInt(4);
			 		  		
			 	   }
			 	   // if train used only equip means used the below query
			 	   ResultSet eqfromtrainnextprod = stmt.executeQuery("SELECT * FROM equipment_train_equipments where train_id=" + nextprodtrainID +" && tenant_id='"+tenant_id+"'"); // get product name id
			 	   while (eqfromtrainnextprod.next()) 
			 	   {
			 		  equipments.add(eqfromtrainnextprod.getInt(2));
			 	   }
			 	  // if train used group means - use the below query
			 	   Set<Integer> nextprodgroupIDs = new HashSet<>();
			 	   ResultSet eqfromtraingroupNextProd = stmt.executeQuery("SELECT * FROM equipment_train_group where train_id=" + nextprodtrainID +" && tenant_id='"+tenant_id+"'"); // get product name id
			 	   while (eqfromtraingroupNextProd.next()) 
			 	   {
			 		  nextprodgroupIDs.add(eqfromtraingroupNextProd.getInt(2));
			 	   }
			 	   
			 	   for(int ids:nextprodgroupIDs) // iterate group id one by one (from train)
			 	   {
			 		   //Set<Integer> equipID = new HashSet();
			 		   int equipmentusedcount = 0;
			 		   ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT * FROM equipment_train_group where group_id=" + ids +" && tenant_id='"+tenant_id+"'"); // get product name id
				 	   while (geteqcountfromgrpID.next()) 
				 	   {
				 		 equipmentusedcount = geteqcountfromgrpID.getInt(4);
				 	   }
				 	   ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + ids +" && tenant_id='"+tenant_id+"' order by sorted_id limit "+equipmentusedcount+""); // get product name id
				 	   while (geteqfromgrpID.next()) 
				 	   {
				 		  equipments.add(geteqfromgrpID.getInt(2));
				 	   }
			 	   }
//end: check if only equipment train used in the product -current product			 	    
		 	    
		 	    int equiptotalSF = 0;
		 	    for(int geteqID:equipments) //get equipment surface area
		 	    {
		 	    	ResultSet eqSF = stmt.executeQuery("SELECT * FROM equipment where id='" + geteqID +"' && tenant_id='"+tenant_id+"'"); // get product name id
		 	    	while (eqSF.next()) {
		 	    		equiptotalSF = equiptotalSF + eqSF.getInt(13);
		 	    		}
		 	    }
		 	   nextProdeqSettotalSF.add((float) equiptotalSF);
		    }
		    float largestTotalSF = Collections.max(nextProdeqSettotalSF);
		    connection.close();
		    return largestTotalSF;
  		}
  		*/
  		
  		

  		//find L3 bioburden surface limit value
  		public static float BioburdensurfaceLimit(String currentproductname,String nextproductname) throws ClassNotFoundException, SQLException
  		{
  			float L3SurfaceBioburden = 0;
  			
  			Connection connection = Utils.db_connect();
  			Statement stmt = (Statement) connection.createStatement();// Create Statement ObjectStatement stmt = Utils.db_connect();// Create Statement Object
  			
  			ResultSet Product = stmt.executeQuery("SELECT * FROM product where name='"+nextproductname+"' && tenant_id='"+tenant_id+"'");
  			float minBatchofNextprod=0;
			while (Product.next())  //check surface or both
			{	
				minBatchofNextprod = Product.getFloat(9);
			}
			System.out.println("nextproductname: "+nextproductname);
			System.out.println("adjustedBSP(currentproductname): "+adjustedBSP(currentproductname));
			System.out.println("minBatchofNextprod: "+minBatchofNextprod);
			System.out.println("SurfaceAreaValue.sameProductSF(nextproductname): "+SurfaceAreaValue.sameProductSF(nextproductname));
			L3SurfaceBioburden = (adjustedBSP(currentproductname) * minBatchofNextprod * 1000) / SurfaceAreaValue.sameProductSF(nextproductname);
			connection.close();
			return L3SurfaceBioburden;
  		}
  
  		
  
  	//To get L3surfacelimit - Contact plate or swab
  		public static float surfaceLimitContactPlateORSwab(String currentproductname,String nextproductname) throws ClassNotFoundException, SQLException
  		{
  			Connection connection = Utils.db_connect();
  			Statement stmt = (Statement) connection.createStatement();// Create Statement Object
  			
  			 ResultSet SwabSurfaceLimit = stmt.executeQuery("SELECT contact_plate_surface_area,bioburden_limit_option FROM microbial_limit where tenant_id='"+tenant_id+"'");
  			 float contactPlateORSwab = 0,SurfaceLimitContactORSwab = 0;
  			 int bioburdenoption = 0;
  				while (SwabSurfaceLimit.next())  //check microbial option whether bioburder or both
  				{	
  				contactPlateORSwab = SwabSurfaceLimit.getInt(1);
  				bioburdenoption = SwabSurfaceLimit.getInt(2);
  				}
  				if(bioburdenoption == 1 || bioburdenoption ==3  ) // check surface or rinse or both
				{
  					SurfaceLimitContactORSwab = BioburdensurfaceLimit(currentproductname,nextproductname) * contactPlateORSwab;
				}
  				connection.close();
			return SurfaceLimitContactORSwab;
  		}
  
  
  	//To get L3surfacelimit - Contact plate or swab
  		public static float rinseLimit(String currentproductname, String nextproductname,float L3SurfaceLimit)   throws ClassNotFoundException, SQLException
  		{
  			float L3RinseBioburden;
  			Connection connection = Utils.db_connect();
  			Statement stmt = (Statement) connection.createStatement();// Create Statement Object
  			 ResultSet product = stmt.executeQuery("SELECT total_rinse_volume FROM product where name='"+currentproductname+"' && tenant_id='"+tenant_id+"'");
 			 float rinsevolumeofcurrentproduct = 0;
 				while (product.next())  //check microbial option whether bioburder or both
 				{	
 				rinsevolumeofcurrentproduct = product.getFloat(1);
 				}
 				//get minimum batch of next product
 				ResultSet Product = stmt.executeQuery("SELECT min_batch_size FROM product where name='"+nextproductname+"' && tenant_id='"+tenant_id+"'");
 	  			float minBatchofNextprod=0;
 				while (Product.next())  //check surface or both
 				{	
 					minBatchofNextprod = Product.getFloat(1);
 				}
 				
 				if(L3SurfaceLimit!=0)
 					{
 					L3RinseBioburden  = (L3SurfaceLimit * SurfaceAreaValue.sameProductSF(nextproductname)) / (rinsevolumeofcurrentproduct * 1000);
 					}else
 					{
 						L3RinseBioburden  = (adjustedBSP(currentproductname) * minBatchofNextprod * 1000)/ (rinsevolumeofcurrentproduct * 1000);
 					}
 				connection.close();
				return L3RinseBioburden;
				
  		}
  		
  		
  		
  		
  		
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
  
  
  
  
