package com.eResidue.Calculation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import com.eResidue.Utils.Constant;
import com.eResidue.Utils.Utils;

public class SolidCalculation {
	
	public boolean no_default = false;
	public double default_l1_val;
	public double default_l1l3_l1;
	public double default_l1l3_l3;
	public double default_l3_val,default_l1_val1;
	public boolean default_l3 = false;
	public boolean default_l1 = false;
	public boolean default_l1_l3 = false;

	
	public void defaultValueSet()	{
		if(Default_Limit.defaultmethod().equalsIgnoreCase("No_Default")) {
			no_default = true;
		}else if(Default_Limit.defaultmethod().contains("Default_L1@@")) {
			String[] a = Default_Limit.defaultmethod().split("@@");
			default_l1_val = Double.parseDouble(a[1]);
			default_l1 = true;
			System.out.println("default_l1_val---->"+default_l1_val+"---->default_l1---->"+default_l1);
			
		}else if(Default_Limit.defaultmethod().contains("Default_L3@@")) {
			String[] a = Default_Limit.defaultmethod().split("@@");
			default_l3_val = Double.parseDouble(a[1]);
			default_l3 = true;
			System.out.println("default_l3_val---->"+default_l3_val+"---->default_l3---->"+default_l3);
			
		}else if(Default_Limit.defaultmethod().contains("Default_L1L3@@")) {
			System.out.println("Entry 4");
			String[] a = Default_Limit.defaultmethod().split("@@");
			default_l1l3_l1 = Double.parseDouble(a[1]);
			default_l1l3_l3 = Double.parseDouble(a[2]);
			default_l1_l3 = true;
			System.out.println("4: default_l1_val---->"+default_l1l3_l1+"---->default_l3---->"+default_l1l3_l3 +"Default L1 and L3 option: "+default_l1_l3);
		}
	}
	
	
	double value_L1,value_L2,value_L3,Solid_Total_surface_area,Solid_swab_area=40,Solid_desorption_solvent=20,maxDD,minBatch,Solid_Expec_Value_L2,Solid_Expec_Value_L3, Solid_Expec_Value_L4a, Solid_Expec_Value_L4b;

	@Test(priority=1) // Product P1 Active1 to P2
	public void P1A1_P2_Solid() throws IOException, InterruptedException, SQLException, ClassNotFoundException 
	{
		defaultValueSet();
		//XSSFWorkbook workbook;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); 
		XSSFSheet sheet = workbook.getSheetAt(1);
				Statement stmt = Utils.db_connect();// Create Statement Object
				// Execute the SQL Query. Store results in Result Set // While Loop to iterate through all data and print results
				ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+sheet.getRow(12).getCell(1).getStringCellValue()+"'"); // get next prod name from excel and find out in db
				while (productdata.next()) {	maxDD = productdata.getFloat(8);	minBatch = productdata.getFloat(9);   }
				value_L1 = Calculate_L0_Solid.calculate_P1A1_L0() / maxDD;
				value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
				Solid_Total_surface_area = sheet.getRow(28).getCell(9).getNumericCellValue(); 
				value_L3 = value_L2 / Solid_Total_surface_area; // Calculated L3 value
				System.out.println("Calculated L3: "+value_L3);
				System.out.println("Test");
				System.out.println("Test");
		if(no_default) // No Default limit
		{	
			no_defaultMethod();
		}
		if(default_l1) // Default limit for L1 value
		{
			defaultL1Method();
		}
		if(default_l3) // Default limit for L3 value
		{
			defaultL3Method();
		}
		if(default_l1_l3)// Default limit for L1 and L3
		{	
			defaultL1L3Method();
		}  
		System.out.println("L2 value: "+Solid_Expec_Value_L2);
		System.out.println("L3 value: "+Solid_Expec_Value_L3);
		System.out.println("L4a value: "+Solid_Expec_Value_L4a);
		System.out.println("L4b value: "+Solid_Expec_Value_L4b);
		Cell Solid_expec_Value_L2_print = sheet.getRow(42).getCell(5); 
		Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
		Cell Solid_expec_Value_L3_print = sheet.getRow(42).getCell(6); 
		Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
		Cell Solid_expec_Value_L4a_print = sheet.getRow(42).getCell(8); 
		Solid_expec_Value_L4a_print.setCellValue(Solid_Expec_Value_L4a); // print expected L4a result into excel
		Cell Solid_expec_Value_L4b_print = sheet.getRow(42).getCell(9); 
		Solid_expec_Value_L4b_print.setCellValue(Solid_Expec_Value_L4b); // print expected L4b result into excel
		
		// end - Default L1 and L3 limit
		// Get Limit Result from db
		/* ResultSet result = stmt.executeQuery("select * from calculation where name = '"+sheet.getRow(5).getCell(2)+"'");
		// While Loop to iterate through all data and print results
				while (rs.next()) {
					float Solid_actual_Value_L2 = result.getFloat(2);
					float Solid_actual_Value_L3 = result.getFloat(3);
					float Solid_actual_Value_L4a = result.getFloat(4);
					float Solid_actual_Value_L4b = result.getFloat(5);
					
					System.out.println("P1:act L2 - " + Solid_actual_Value_L2 + "\nP1:act L3 - " + Solid_actual_Value_L3 + "\nP1:act L4a - " +Solid_actual_Value_L4a + "\nP1:act L4b - "+ Solid_actual_Value_L4b);
		// Print Actual result to excel
		Cell Solid_actual_Value_L2_print = sheet.getRow(25).getCell(9); 
		Solid_actual_Value_L2_print.setCellValue(Solid_actual_Value_L2); // print expected L2 result into excel
		Cell Solid_actual_Value_L3_print = sheet.getRow(25).getCell(10); 
		Solid_actual_Value_L3_print.setCellValue(Solid_actual_Value_L3); // print expected L3 result into excel
		Cell Solid_actual_Value_L4a_print = sheet.getRow(25).getCell(12); 
		Solid_actual_Value_L4a_print.setCellValue(Solid_actual_Value_L4a); // print expected L4a result into excel
		Cell Solid_actual_Value_L4b_print = sheet.getRow(25).getCell(13); 
		Solid_actual_Value_L4b_print.setCellValue(Solid_actual_Value_L4b); // print expected L4b result into excel
		
		//Get printed expected value from excel
		double Solid_Expec_Value_L3 = sheet.getRow(25).getCell(4).getNumericCellValue();
		System.out.println("Expected L3: "+Solid_Expec_Value_L3);
		System.out.println("Actual L3: "+Solid_actual_Value_L3);
		//Compare both expected and actual result
		if(Solid_Expec_Value_L3 == Solid_actual_Value_L3)
		{	Cell val_L2_result = sheet.getRow(25).getCell(15);
			val_L2_result.setCellValue("Pass");
			System.out.println("Pass");
		}else
		{	Cell val_L2_result = sheet.getRow(25).getCell(15);
			val_L2_result.setCellValue("Fail");
			System.out.println("Fail");
		}
		}*/				
		writeTooutputFile(workbook); // write output into work sheet
		Thread.sleep(800);
		}
	

	// method for when default limit option: no default option
	public void no_defaultMethod()
	{  
		System.out.println("No default true");
		Solid_Expec_Value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value 
		Solid_Expec_Value_L3 = Solid_Expec_Value_L2 / Solid_Total_surface_area; // Calculated L3 value
		Solid_Expec_Value_L4a = Solid_Expec_Value_L3 *Solid_swab_area ; // Calculated L4a value
		Solid_Expec_Value_L4b = Solid_Expec_Value_L4a / Solid_desorption_solvent; // Calculated L4b value
	}

	// method for when default limit option: Default L1
	public void defaultL1Method()
	{
		System.out.println("Defaut L1 Value true");
		if(value_L1<default_l1_val) // Low L1 value and high default l1 value
		{	// Formula for L2 
			Solid_Expec_Value_L2 = value_L2; // Roundoff
			Solid_Expec_Value_L3 = value_L3; // Roundoff
			Solid_Expec_Value_L4a = Solid_Expec_Value_L3 *Solid_swab_area ; // Calculated L4a value
			Solid_Expec_Value_L4b = Solid_Expec_Value_L4a / Solid_desorption_solvent; // Calculated L4b value
		}else {	// high L1 value and low default l1 value
			System.out.println("use Default L1 Value: " +default_l1_val);
			Solid_Expec_Value_L2 = default_l1_val * minBatch * 1000 ; // Calculated L2 Value for same product
			Solid_Expec_Value_L3 = Solid_Expec_Value_L2 / Solid_Total_surface_area; // Calculated L3 value
			Solid_Expec_Value_L4a = Solid_Expec_Value_L3 * Solid_swab_area ; // Calculated L4a value
			Solid_Expec_Value_L4b = Solid_Expec_Value_L4a / Solid_desorption_solvent; // Calculated L4b value
			 }
	}
	// method for when default limit option: Default L3
	public void defaultL3Method()
	{

		if(value_L3<default_l3_val) // Low L3 value and high default l3 value
		{
			Solid_Expec_Value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value for same product
			Solid_Expec_Value_L3 = Solid_Expec_Value_L2 / Solid_Total_surface_area; // Calculated L3 value
			Solid_Expec_Value_L4a = Solid_Expec_Value_L3 *Solid_swab_area ; // Calculated L4a value
			Solid_Expec_Value_L4b = Solid_Expec_Value_L4a / Solid_desorption_solvent; // Calculated L4b value
		}
		else {	// high L3 value and low default l3 value
			Solid_Expec_Value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value for same product
			Solid_Expec_Value_L3 = default_l3_val; // Calculated L3 value
			Solid_Expec_Value_L4a = Solid_Expec_Value_L3 * Solid_swab_area ; // Calculated L4a value
			Solid_Expec_Value_L4b = Solid_Expec_Value_L4a / Solid_desorption_solvent; // Calculated L4b value
		}
	}
	
	// method for when default limit option: Default L1 and L3
	public void defaultL1L3Method()
	{
		if(value_L1>default_l1l3_l1)
			{	// Formula for L2 
				Solid_Expec_Value_L2  = default_l1l3_l1 * minBatch * 1000 ; // Calculated L2 Value for same product
				Solid_Expec_Value_L3 = Solid_Expec_Value_L2 / Solid_Total_surface_area; // Calculated L3 value using default L1 value
				if(Solid_Expec_Value_L3<default_l1l3_l3)
				{
				Solid_Expec_Value_L4a = Solid_Expec_Value_L3 *Solid_swab_area ; // Calculated L4a value
				Solid_Expec_Value_L4b = Solid_Expec_Value_L4a / Solid_desorption_solvent; // Calculated L4b value
				} else
				{	//double L3 = default_l1l3_l3 / Solid_Total_surface_area;
					Solid_Expec_Value_L3 = default_l1l3_l3; 
					Solid_Expec_Value_L4a = default_l1l3_l3 *Solid_swab_area ; // Calculated L4a value
					Solid_Expec_Value_L4b = Solid_Expec_Value_L4a / Solid_desorption_solvent; // Calculated L4b value
				}
			}
		if(value_L1<default_l1l3_l1)
		{		// Formula for L2 
			Solid_Expec_Value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value for same product
			Solid_Expec_Value_L3 = Solid_Expec_Value_L2 / Solid_Total_surface_area; // Calculated L3 value using default L1 value
			if(Solid_Expec_Value_L3<default_l1l3_l3)
			{   
				Solid_Expec_Value_L4a = Solid_Expec_Value_L3 *Solid_swab_area ; // Calculated L4a value
				Solid_Expec_Value_L4b = Solid_Expec_Value_L4a / Solid_desorption_solvent; // Calculated L4b value
			} else
			{	//double L3 = default_l1l3_l3 / Solid_Total_surface_area;
				Solid_Expec_Value_L3 = default_l1l3_l3; 
				Solid_Expec_Value_L4a = default_l1l3_l3 *Solid_swab_area ; // Calculated L4a value
				Solid_Expec_Value_L4b = Solid_Expec_Value_L4a / Solid_desorption_solvent; // Calculated L4b value
			}
		}		
	}
	
	
	
	
	
	
	// Write output and close workbook
	public void writeTooutputFile(Workbook workbook) throws IOException {
		try {
			FileOutputStream outFile = new FileOutputStream(new File(Constant.EXCEL_PATH_Result));
			workbook.write(outFile);
			outFile.close();
		} catch (Exception e) {
			throw e;
		}
	}
	
	// Method for getting the lowest (minimum) value
	  public static double getMin(double[] inputArray){ 
	    double minValue = inputArray[0]; 
	    for(int i=1;i<inputArray.length;i++){ 
	      if(inputArray[i] < minValue){ 
	        minValue = inputArray[i]; 
	      } 
	    } 
	    return minValue; 
	  } 
	
}
