package com.eDocs.Calculation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
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

	//default limit option
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
	
	
	//Get swab area value from universal settings
	public double swabArea() throws ClassNotFoundException, SQLException {
		Statement stmt = Utils.db_connect();// Create Statement Object
		ResultSet residueLimit = stmt.executeQuery("SELECT * FROM residue_limit");
		while (residueLimit.next()) 
		{	
		int definded_for_each_equip_loc_for_surface_area_sampled_flag = residueLimit.getInt(20);
		if(definded_for_each_equip_loc_for_surface_area_sampled_flag==0)
		{
			swabSurfaceArea = residueLimit.getFloat(19);
		}
		}
		return swabSurfaceArea;
	}
	
	//Get swab amount value from universal settings
	public double swabAmount() throws ClassNotFoundException, SQLException {
		Statement stmt = Utils.db_connect();// Create Statement Object
		ResultSet residueLimit = stmt.executeQuery("SELECT * FROM residue_limit");
		while (residueLimit.next()) 
		{
			int definded_for_each_equip_loc_for_solvent_used_flag = residueLimit.getInt(22);
		if(definded_for_each_equip_loc_for_solvent_used_flag==0)
		{
			swabAmount = residueLimit.getFloat(21);
		}
		}
		return swabAmount;
	}
	String sampling_methodOption;
	int RinseSampling;
	//Get equipment rinse volume from universal settings
	public double eqRinseVolume() throws ClassNotFoundException, SQLException {//Get value from universal settings
		Statement stmt = Utils.db_connect();// Create Statement Object
		ResultSet residueLimit = stmt.executeQuery("SELECT * FROM residue_limit");
		while (residueLimit.next()) 
		{	
			int defined_for_each_equip_or_train_loc_flag = residueLimit.getInt(25);
			sampling_methodOption = residueLimit.getString(16);
			RinseSampling = residueLimit.getInt(23);
			if(defined_for_each_equip_or_train_loc_flag==0)
			{
				rinsevolume = residueLimit.getFloat(24); 
			}
		}
		return rinsevolume;
	}
	
	
	
	
	
	
	
	double value_L1,value_L2,value_L3,Solid_Total_surface_area,maxDD,minBatch,Solid_Expec_Value_L2,Solid_Expec_Value_L3, Solid_Expec_Value_L4a, Solid_Expec_Value_L4b,Solid_Expec_Value_L1,swabSurfaceArea,swabAmount,rinsevolume
			,L4cEquipment;

	@Test(priority=1) // Product P1 Active1 to P2
	public void P1ToP2_P1A1_Solid() throws IOException, InterruptedException, SQLException, ClassNotFoundException 
	{
		defaultValueSet();
		//XSSFWorkbook workbook;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); 
		XSSFSheet sheet = workbook.getSheet("Solid_Calculation");
				Statement stmt = Utils.db_connect();// Create Statement Object
				//next product id // Execute the SQL Query. Store results in Result Set // While Loop to iterate through all data and print results
				ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+sheet.getRow(12).getCell(1).getStringCellValue()+"'"); // get next prod name from excel and find out in db
				while (productdata.next()) {	maxDD = productdata.getFloat(8); minBatch = productdata.getFloat(9);   }
				value_L1 = Calculate_L0_Solid.calculate_P1_active1_L0() / maxDD;
				value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
				Solid_Total_surface_area = sheet.getRow(28).getCell(9).getNumericCellValue(); 
				value_L3 = value_L2 / Solid_Total_surface_area; // Calculated L3 value
				
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
		Cell Solid_expec_Value_L0_print = sheet.getRow(42).getCell(3); 
		Solid_expec_Value_L0_print.setCellValue(Calculate_L0_Solid.calculate_P1_active1_L0()); // print expected L0 result into excel
		Cell Solid_expec_Value_L1_print = sheet.getRow(42).getCell(4); 
		Solid_expec_Value_L1_print.setCellValue(Solid_Expec_Value_L1); // print expected L0 result into excel
		Cell Solid_expec_Value_L2_print = sheet.getRow(42).getCell(5); 
		Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
		Cell Solid_expec_Value_L3_print = sheet.getRow(42).getCell(6); 
		Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
		int getprodID = 0;
		float SFArea = 0,rinsevolume=0,swabarea = 0,swabamount=0;
		String eqname = null;
		//Get no of used Equipment in the product
		ResultSet productID = stmt.executeQuery("Select * from product where name = '" + sheet.getRow(8).getCell(1) + "'"); // get product name id
			while (productID.next()) 
			{
				getprodID = productID.getInt(1);
			} // closing for productID while loop 
			//equipment set id
			ResultSet productSetEqID = stmt.executeQuery("Select * from product_equipment_set_equipments where product_id='" + getprodID + "'"); // get equipment id
			Set<Integer> set = new HashSet<>(); // store multiple equipment id
		    while (productSetEqID.next()) {
		      set.add(productSetEqID.getInt(4));
		    }
		    int i =81; //increment cell to print result
		    for (Integer equipmentID:set) //get id from set
		    {
		    ResultSet EquipID = stmt.executeQuery("Select * from equipment where id= '" + equipmentID + "'"); // get product name id
		   // print
		    while(EquipID.next()) {  // print name and sf value from equipment table
		    	 eqname = EquipID.getString(9); // get name from database
				 SFArea = EquipID.getFloat(13); // get SF value from database
				 rinsevolume = EquipID.getFloat(17); // get SF value from database
				 swabarea = EquipID.getFloat(15); // get SF value from database
				 swabamount = EquipID.getFloat(16);
				 if(swabArea()==0) //check swab area from uni setting or each equipment
				 {
					 Solid_Expec_Value_L4a =  Solid_Expec_Value_L3 * swabarea;
					 Cell equipRinse = sheet.getRow(i).getCell(3);//cell to print swab amount
					 equipRinse.setCellValue(swabarea); 
				 }else {
					 Solid_Expec_Value_L4a =  Solid_Expec_Value_L3 * swabArea();
					 Cell equipRinse = sheet.getRow(i).getCell(3);//cell to print swab amount
					 equipRinse.setCellValue(swabArea()); 
				 }
				 //swab amount
				 if(swabAmount()==0) //check swab amount from univ setting or each equipment
				 {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabamount;
					 Cell equipRinse = sheet.getRow(i).getCell(4);//cell to print swab amount
					 equipRinse.setCellValue(swabamount); 
				 }else {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabAmount();
					 Cell equipRinse = sheet.getRow(i).getCell(4);//cell to print swab amount
					 equipRinse.setCellValue(swabAmount()); 
				 }
				// equipment rinse volume()
				 if(eqRinseVolume()==0) //check rinset from univ setting or each equipment
				 {
					 L4cEquipment = (Solid_Expec_Value_L3 * SFArea) /(rinsevolume * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(5);//cell to print equipment rinse volume
					 equipRinse.setCellValue(rinsevolume); // print all the equipment rinse volume(used in the product) in excel
				 }else {
					 L4cEquipment = (Solid_Expec_Value_L3 * SFArea) /(eqRinseVolume() * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(5);//cell to print equipment rinse volume 
					 equipRinse.setCellValue(eqRinseVolume()); // print all the equipment rinse volume(used in the product) in excel
				 }
		    	 // Print Expected L4a, L4b, L4c value
		    	 	Cell equipName = sheet.getRow(i).getCell(1);//cell to print name 
		    		equipName.setCellValue(eqname); // print equipment name(used in the product) in excel
		    		Cell equipSF = sheet.getRow(i).getCell(2);//cell to print equipment surface area 
		    		equipSF.setCellValue(SFArea); // print all the equipment surface area(used in the product) in excel
		    		Cell L4aEquip = sheet.getRow(i).getCell(6);//cell to print L4a value 
		    		L4aEquip.setCellValue(Solid_Expec_Value_L4a); // print all the equipment surface area(used in the product) in excel
		    		Cell L4bEquip = sheet.getRow(i).getCell(7);//cell to print L4b value
		    		L4bEquip.setCellValue(Solid_Expec_Value_L4b); // print all the equipment surface area(used in the product) in excel
		    		
		    		
		    		
		    		
		    		
		    		
		    		// check whether rinse enabled in universal settings
		    			if(sampling_methodOption.equals("1,2") && RinseSampling==1) // if rinse enabled in sampling
		    			{
		    				Cell L4cEquip = sheet.getRow(i).getCell(8);
		    				L4cEquip.setCellValue(L4cEquipment); 
		    			}
		    			else {
		    				Cell L4cEquip = sheet.getRow(i).getCell(8);
		    				L4cEquip.setCellValue(0); 
		    				}
		    		i++;
		    		} //closing equipmentID while loop
		    		} //closing for loop
		   // Get Limit Result from db
				 int active_id = 0;
				 // get active id
				 ResultSet active = stmt.executeQuery("SELECT * FROM product_active_ingredient where name = '"+ sheet.getRow(8).getCell(2).getStringCellValue() + "'");
				 {
					 while (active.next()) 
					 {
						 active_id = active.getInt(1);
						 String activename = active.getString(2);
						 //print active name to excel
						 Cell NameofActive = sheet.getRow(79).getCell(2); 
						 NameofActive.setCellValue(activename); // print active name into excel
					 }
				 }
				 //get product id
				 System.out.println("Prouct id: "+getprodID +" Active id: "+active_id);
				 //current product id 
				 float ActualL0Result = 0,ActualL1Result = 0,ActualL2Result = 0,ActualL3Result = 0;
				 ResultSet prod_cal = stmt.executeQuery("SELECT * FROM product_calculation where product_id= '" + getprodID + "' && active_ingredient_id='"+  active_id+ "'");
				// While Loop to iterate through all data and print results
						while (prod_cal.next()) {
							 ActualL0Result = prod_cal.getFloat(3); ActualL1Result = prod_cal.getFloat(4); ActualL2Result = prod_cal.getFloat(5); ActualL3Result = prod_cal.getFloat(6);
						}
				// Print Actual result to excel
				Cell print_actual_L0 = sheet.getRow(42).getCell(8); 
				print_actual_L0.setCellValue(ActualL0Result); // print actual L0 result into excel
				Cell print_actual_L1 = sheet.getRow(42).getCell(9); 
				print_actual_L1.setCellValue(ActualL1Result); // print actual L1 result into excel
				Cell print_actual_L2 = sheet.getRow(42).getCell(10); 
				print_actual_L2.setCellValue(ActualL2Result); // print actual L2 result into excel
				Cell print_actual_L3 = sheet.getRow(42).getCell(11); 
				print_actual_L3.setCellValue(ActualL3Result); // print actual L3 result into excel
				System.out.println("L3 result" +ActualL3Result);
				
				//Compare both expected and actual result - applied round off for comparison
				if(toOptimizeDecimalPlacesRoundedOff(Solid_Expec_Value_L3).equals(toOptimizeDecimalPlacesRoundedOff(ActualL3Result)) )
				{
					System.out.println("Expected"+toOptimizeDecimalPlacesRoundedOff(Solid_Expec_Value_L3));
					System.out.println("Actual"+toOptimizeDecimalPlacesRoundedOff(ActualL3Result));
					Cell printL3_result = sheet.getRow(42).getCell(13);
					printL3_result.setCellValue("Pass");
					printL3_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
				}else
				{	Cell printL3_result = sheet.getRow(42).getCell(13);
					printL3_result.setCellValue("Fail");
					printL3_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
				}
			//Get Actual L4a L4b L4c
			int j =81;
			float Ac_L4a = 0,Ac_L4b = 0,Ac_L4c = 0;
			for (Integer equipmentIDList:set) //get id from set
				   {
					System.out.println("equipmentIDList"+equipmentIDList);
					ResultSet ActualequipResult = stmt.executeQuery("SELECT * FROM product_calculation_equipment_results where product_id= '" + getprodID + "' && active_ingredient_id='"+  active_id+ "' && equipment_id='"+equipmentIDList+"'");
					while (ActualequipResult.next()) {
					 Ac_L4a = ActualequipResult.getFloat(5); 
					 Ac_L4b = ActualequipResult.getFloat(6);
					 Ac_L4c = ActualequipResult.getFloat(7);
				Cell L4aEquip = sheet.getRow(j).getCell(9);//cell to print L4a value 
	    		L4aEquip.setCellValue(Ac_L4a); // print all the equipment surface area(used in the product) in excel
	    		Cell L4bEquip = sheet.getRow(j).getCell(10);//cell to print L4b value
	    		L4bEquip.setCellValue(Ac_L4b); // print all the equipment surface area(used in the product) in excel
	    		if(sampling_methodOption.equals("1,2")&& RinseSampling==1) // if rinse enabled in sampling
    			{
    				Cell L4cEquip = sheet.getRow(j).getCell(11);//cell to print L4b value
    	    		L4cEquip.setCellValue(Ac_L4c); // print all the equipment surface area(used in the product) in excel
    			}
    			else {
    				Cell L4cEquip = sheet.getRow(j).getCell(11);//cell to print L4b value
    	    		L4cEquip.setCellValue(0); // print all the equipment surface area(used in the product) in excel 
    				}
					}
					j++;
				 }
			
			// check expected L4a,L4b,L4c and actual L4a,L4b,L4c 
			int k= 81;
			for(Integer s:set)
			{
			double EL4a = sheet.getRow(k).getCell(6).getNumericCellValue();
			double EL4b = sheet.getRow(k).getCell(7).getNumericCellValue();
			double EL4c = sheet.getRow(k).getCell(8).getNumericCellValue();
			double AL4a = sheet.getRow(k).getCell(9).getNumericCellValue();
			double AL4b = sheet.getRow(k).getCell(10).getNumericCellValue();
			double AL4c = sheet.getRow(k).getCell(11).getNumericCellValue();
			if(toOptimizeDecimalPlacesRoundedOff(EL4a).equals(toOptimizeDecimalPlacesRoundedOff(AL4a)) && 
					toOptimizeDecimalPlacesRoundedOff(EL4b).equals(toOptimizeDecimalPlacesRoundedOff(AL4b)) &&
							toOptimizeDecimalPlacesRoundedOff(EL4c).equals(toOptimizeDecimalPlacesRoundedOff(AL4c)))
			{
				Cell printL3_result = sheet.getRow(k).getCell(12);
				printL3_result.setCellValue("Pass");
				printL3_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			}else
			{	
				Cell printL3_result = sheet.getRow(k).getCell(12);
				printL3_result.setCellValue("Fail");
				printL3_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
			}
			k++;
			}
			
		
		writeTooutputFile(workbook); // write output into work sheet
		Thread.sleep(800);
		}// closing P1A1 calculation

	
	
	
	
	
	
	
	
	
	public String toOptimizeDecimalPlacesRoundedOff(double valueDouble) {

        /** The PdfTemplate with the total number of pages. */
        /*
         * Double roundedValue = Math.round(valueDouble * 100.0) / 100.0; return
         * roundedValue.toString();
         */
        if (0.00 >= valueDouble)
            return "";

        BigDecimal value = BigDecimal.valueOf(valueDouble);

        if (value.compareTo(new BigDecimal(100)) >= 0) {
            return value.setScale(3, BigDecimal.ROUND_HALF_UP).toString();
        } else if (value.compareTo(new BigDecimal(10)) >= 0) {
            return value.setScale(3, BigDecimal.ROUND_HALF_UP).toString();
        } else if (value.compareTo(new BigDecimal(1)) >= 0) {
            return value.setScale(3, BigDecimal.ROUND_HALF_UP).toString();
        } else {
            int zeros = 0;
            BigDecimal valueTest = value;
            while (valueTest.compareTo(new BigDecimal(1)) < 0) {
                valueTest = valueTest.multiply(new BigDecimal(10));
                zeros++;
                if (zeros == 10) {
                    break;
                }
            }
            zeros += 2;

            if (value.setScale(zeros, BigDecimal.ROUND_HALF_UP).toString().contains("E")) {
                return value.setScale(zeros, BigDecimal.ROUND_HALF_UP).toString().replace("E", " x 10<sup>") + "</sup>";
            }

            return value.setScale(zeros, BigDecimal.ROUND_HALF_UP).toString();
        }
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
