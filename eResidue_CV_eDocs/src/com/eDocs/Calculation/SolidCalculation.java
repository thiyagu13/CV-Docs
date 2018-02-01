package com.eDocs.Calculation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
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
	
	/*//Get value from universal settings
	public double swabArea() throws ClassNotFoundException, SQLException {
		Statement stmt = Utils.db_connect();// Create Statement Object
		ResultSet residueLimit = stmt.executeQuery("SELECT * FROM residue_limit");
		while (residueLimit.next()) {	swabSurfaceArea = residueLimit.getFloat(19); }
		return swabSurfaceArea;
	}//Get value from universal settings
	public double swabAmount() throws ClassNotFoundException, SQLException {
		Statement stmt = Utils.db_connect();// Create Statement Object
		ResultSet residueLimit = stmt.executeQuery("SELECT * FROM residue_limit");
		while (residueLimit.next()) {	swabAmount = residueLimit.getFloat(21); }
		return swabAmount;
	}*/
	/*public double eqRinseVolume() throws ClassNotFoundException, SQLException {//Get value from universal settings
		Statement stmt = Utils.db_connect();// Create Statement Object
		ResultSet residueLimit = stmt.executeQuery("SELECT * FROM residue_limit");
		while (residueLimit.next()) {	rinsevolume = residueLimit.getFloat(24); }
		return rinsevolume;
	}*/
	
	
	double value_L1,value_L2,value_L3,Solid_Total_surface_area,maxDD,minBatch,Solid_Expec_Value_L2,Solid_Expec_Value_L3, Solid_Expec_Value_L4a, Solid_Expec_Value_L4b,Solid_Expec_Value_L1,swabSurfaceArea,swabAmount,rinsevolume
			,L4cEquipment;

	@Test(priority=1) // Product P1 Active1 to P2
	public void P1A1_P2_Solid() throws IOException, InterruptedException, SQLException, ClassNotFoundException 
	{
		defaultValueSet();
		//XSSFWorkbook workbook;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); 
		XSSFSheet sheet = workbook.getSheet("Solid_Calculation");
				Statement stmt = Utils.db_connect();// Create Statement Object
				// Execute the SQL Query. Store results in Result Set // While Loop to iterate through all data and print results
				ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+sheet.getRow(12).getCell(1).getStringCellValue()+"'"); // get next prod name from excel and find out in db
				while (productdata.next()) {	maxDD = productdata.getFloat(8);	minBatch = productdata.getFloat(9);   }
				value_L1 = Calculate_L0_Solid.calculate_P1A1_L0() / maxDD;
				value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
				Solid_Total_surface_area = sheet.getRow(28).getCell(9).getNumericCellValue(); 
				value_L3 = value_L2 / Solid_Total_surface_area; // Calculated L3 value
				System.out.println("Calculated L3: "+value_L3);
				
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
		System.out.println("L0 value: "+Calculate_L0_Solid.calculate_P1A1_L0());
		System.out.println("L1 value: "+Solid_Expec_Value_L1);
		System.out.println("L2 value: "+Solid_Expec_Value_L2);
		System.out.println("L3 value: "+Solid_Expec_Value_L3);
		
		int getprodID;
		float SFArea = 0,rinsevolume=0,swabarea = 0,swabamount=0;
		String eqname = null;
		//Get no of used Equipment in the product
		ResultSet productID = stmt.executeQuery("Select * from product where name ='P11'"); // get product name id
		try {
		while (productID.next()) {	getprodID = productID.getInt(1);
		System.out.println("PRoduct id" +getprodID);
		//equipment set id
		ResultSet productSetEqID = stmt.executeQuery("Select * from product_equipment_set_equipments where product_id='" + getprodID + "'"); // get equipment id
		Set<Integer> set = new HashSet<>(); // store multiple equipment id
		    while (productSetEqID.next()) {
		      set.add(productSetEqID.getInt(4));
		    }
		    int i =94; 
		    for (Integer equipmentID:set) //get id from set
		    {
		    ResultSet EquipID = stmt.executeQuery("Select * from equipment where id= '" + equipmentID + "'"); // get product name id
		    System.out.println("Equipment ID"+equipmentID);// print equipment id
		    while(EquipID.next()) {  // print name and sf value from equipment table
		    	 eqname = EquipID.getString(9); // get name from database
				 SFArea = EquipID.getFloat(13); // get SF value from database
				 rinsevolume = EquipID.getFloat(17); // get SF value from database
				 swabarea = EquipID.getFloat(15); // get SF value from database
				 swabamount = EquipID.getFloat(16);
				 System.out.println("Name: "+ eqname+ " SFA: " + SFArea+" Rinse: " + rinsevolume);
		    	 Solid_Expec_Value_L4a =  Solid_Expec_Value_L3 * swabarea;
		    	 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabamount;
		    	 L4cEquipment = (Solid_Expec_Value_L3 * SFArea) /(rinsevolume * 1000);//Calculate L4c equipment value
		    	 System.out.println("L4c Equipment: " +L4cEquipment);
		    	 // Print Expected L4a, L4b, L4c value
		    	 	Cell equipName = sheet.getRow(i).getCell(1);//cell to print name 
		    		equipName.setCellValue(eqname); // print equipment name(used in the product) in excel
		    		Cell equipSF = sheet.getRow(i).getCell(2);//cell to print equipment surface area 
		    		equipSF.setCellValue(SFArea); // print all the equipment surface area(used in the product) in excel
		    		Cell equipRinse = sheet.getRow(i).getCell(3);//cell to print equipment surface area 
		    		equipRinse.setCellValue(rinsevolume); // print all the equipment surface area(used in the product) in excel
		    		Cell L4aEquip = sheet.getRow(i).getCell(4);//cell to print L4a value 
		    		L4aEquip.setCellValue(Solid_Expec_Value_L4a); // print all the equipment surface area(used in the product) in excel
		    		Cell L4bEquip = sheet.getRow(i).getCell(5);//cell to print L4b value
		    		L4bEquip.setCellValue(Solid_Expec_Value_L4b); // print all the equipment surface area(used in the product) in excel
		    		Cell L4cEquip = sheet.getRow(i).getCell(6);//cell to print L4b value
		    		L4cEquip.setCellValue(L4cEquipment); // print all the equipment surface area(used in the product) in excel
		    		i++;
		    		System.out.println("Print:"+i);
		    } //closing equipmentID while loop
		    } //closing for loop
		    System.out.println("Size: "+set.size());
		    
		    /*int j =94;
		    	 for( int i:set)
		    	for( int i=94:i<equipmentID;i++)
		    	 {
		    		System.out.println("Print ID:" +i);
		    		ResultSet EquipmentID = stmt.executeQuery("Select * from equipment where id= '" + i + "'"); 
		    		
		    		while(EquipmentID.next())
		    		{
		    			j++;
		    		String eqpname = EquipmentID.getString(9); // get name from database
		    		Cell equipName = sheet.getRow(j).getCell(1); 
		    		equipName.setCellValue(eqpname); // print expected L4a result into excel 
		    		System.out.println("for loop eq name: "+eqpname);
		    		}
		    	 }*/
			} // closing for productID while loop 
			} // try close
		
		catch(Exception e)
		{
			System.out.println("Error");
		}
		/*Cell Value_L0 = sheet.getRow(42).getCell(3); 
		Value_L0.setCellValue(Calculate_L0_Solid.calculate_P1A1_L0()); // print expected L0 result into excel*/	
		Cell Value_L1 = sheet.getRow(42).getCell(4); 
		Value_L1.setCellValue(Solid_Expec_Value_L1); // print expected L0 result into excel
		Cell Solid_expec_Value_L2_print = sheet.getRow(42).getCell(5); 
		Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
		Cell Solid_expec_Value_L3_print = sheet.getRow(42).getCell(6); 
		Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
		/*Cell Solid_expec_Value_L4a_print = sheet.getRow(42).getCell(8); 
		Solid_expec_Value_L4a_print.setCellValue(Solid_Expec_Value_L4a); // print expected L4a result into excel
		Cell Solid_expec_Value_L4b_print = sheet.getRow(42).getCell(9); 
		Solid_expec_Value_L4b_print.setCellValue(Solid_Expec_Value_L4b); // print expected L4b result into excel
		*/		
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
	public void no_defaultMethod() throws ClassNotFoundException, SQLException
	{  
		System.out.println("No default true");
		Solid_Expec_Value_L1 = value_L1;
		Solid_Expec_Value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value 
		Solid_Expec_Value_L3 = Solid_Expec_Value_L2 / Solid_Total_surface_area; // Calculated L3 value
	}

	// method for when default limit option: Default L1
	public void defaultL1Method() throws ClassNotFoundException, SQLException
	{
		System.out.println("Defaut L1 Value true");
		if(value_L1<default_l1_val) // Low L1 value and high default l1 value
		{	// Formula for L2 
			Solid_Expec_Value_L1 = value_L1;
			Solid_Expec_Value_L2 = value_L2; // Roundoff
			Solid_Expec_Value_L3 = value_L3; // Roundoff
		}else {	// high L1 value and low default l1 value
			System.out.println("use Default L1 Value: " +default_l1_val);
			Solid_Expec_Value_L1 = default_l1_val;
			Solid_Expec_Value_L2 = default_l1_val * minBatch * 1000 ; // Calculated L2 Value for same product
			Solid_Expec_Value_L3 = Solid_Expec_Value_L2 / Solid_Total_surface_area; // Calculated L3 value
			 }
	}
	// method for when default limit option: Default L3
	public void defaultL3Method() throws ClassNotFoundException, SQLException
	{

		if(value_L3<default_l3_val) // Low L3 value and high default l3 value
		{
			Solid_Expec_Value_L1 = value_L1;
			Solid_Expec_Value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value for same product
			Solid_Expec_Value_L3 = Solid_Expec_Value_L2 / Solid_Total_surface_area; // Calculated L3 value
		}
		else {	// high L3 value and low default l3 value
			Solid_Expec_Value_L1 = value_L1;
			Solid_Expec_Value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value for same product
			Solid_Expec_Value_L3 = default_l3_val; // Calculated L3 value
		}
	}
	
	// method for when default limit option: Default L1 and L3
	public void defaultL1L3Method() throws ClassNotFoundException, SQLException
	{
		if(value_L1>default_l1l3_l1)
			{	// Formula for L2 
				Solid_Expec_Value_L1 = default_l1l3_l1;
				Solid_Expec_Value_L2  = default_l1l3_l1 * minBatch * 1000 ; // Calculated L2 Value for same product
				double valL3 = Solid_Expec_Value_L2 / Solid_Total_surface_area; // Calculated L3 value using default L1 value
				if(Solid_Expec_Value_L3<default_l1l3_l3)
				{
				Solid_Expec_Value_L3 = valL3;
				} else
				{	//double L3 = default_l1l3_l3 / Solid_Total_surface_area;
					Solid_Expec_Value_L3 = default_l1l3_l3; 
				}
			}
		if(value_L1<default_l1l3_l1)
		{		// Formula for L2 
			Solid_Expec_Value_L1 = value_L1;
			Solid_Expec_Value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value for same product
			double valL3 = Solid_Expec_Value_L2 / Solid_Total_surface_area; // Calculated L3 value using default L1 value
			if(Solid_Expec_Value_L3<default_l1l3_l3)
			{   
				Solid_Expec_Value_L3 = valL3;
			} else
			{	//double L3 = default_l1l3_l3 / Solid_Total_surface_area;
				Solid_Expec_Value_L3 = default_l1l3_l3; 
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
