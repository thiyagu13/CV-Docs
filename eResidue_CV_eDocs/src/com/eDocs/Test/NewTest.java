/*package com.eDocs.Test;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.eDocs.solidCalculation.SurfaceAreaValue;
import com.mysql.jdbc.Connection;

public class NewTest {
			
	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		List<String>  currentproductlist = new ArrayList<>(); //store product list
  		currentproductlist.add("P1");
  		currentproductlist.add("P2");
  		currentproductlist.add("P3");
  		currentproductlist.add("P4");
  		
		List<String>  nextproductlist = new ArrayList<>(); //store product list
		nextproductlist.add("P1");
		nextproductlist.add("P2");
		nextproductlist.add("P3");
		nextproductlist.add("P4");
		
		
		System.out.println("currentproductlist   "+currentproductlist);
		System.out.println("nextproductlist   "+nextproductlist);
		P1_Active1_Solid(currentproductlist, nextproductlist);
		
		//writeTooutputFile(workbook); // write output into work sheet
	}
	
	
	public void P1_Active1_Solid(List<String> CurrenProduct,List<String> Nextprod) throws IOException, InterruptedException, SQLException, ClassNotFoundException 
	{
		//String CurrenProduct = CurrenProductName; // current product name
		defaultValueSet(CurrenProduct);
		//XSSFWorkbook workbook;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); 
		XSSFSheet sheet = workbook.getSheet("test");
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
				//next product id // Execute the SQL Query. Store results in Result Set // While Loop to iterate through all data and print results
				int nextProdID=0;
				
				 Set<String> c = new HashSet<>(); 
				 c.add("P1");
				 c.add("P2");
				 c.add("P3");
				 c.add("P4");
				
				//Current product list
				List<String>  currentproductlist = new ArrayList<>();
		  		currentproductlist.addAll(CurrenProduct);
		  		//Next product list
		  		List<String>  nextproductlist = new ArrayList<>();
		  		nextproductlist.addAll(Nextprod);
	  		
				List<Float> LowestExpectL3 = new ArrayList<>();
				int row=41,column=9; //excel row and column
				
				for(String CurrenProductName : currentproductlist)
				{
				System.out.println("CurrenProductName-->"+CurrenProductName);
				for(String NextprodName : nextproductlist)
				{
				String nprodname = null;	
				System.out.println("prodName"+NextprodName);
				ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodName+"' "); // get next prod name from excel and find out in db
				while (productdata.next()) {	nextProdID = productdata.getInt(1); nprodname = productdata.getString(2); maxDD = productdata.getFloat(8); minBatch = productdata.getFloat(9);   }

				if(limitDetermination()==2)
				{
				System.out.println("Lowest Limit");
				value_L1 = L0.calculate_P1_active1_L0(CurrenProductName) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0.calculate_P1_active1_L0(CurrenProductName)); // print expected L0 result into excel
				}
				if(limitDetermination()==1)
				{
				System.out.println("grouping approach");
				value_L1 = L0.groupingApproach_L0_p11(CurrenProductName) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0.groupingApproach_L0_p11(CurrenProductName)); // print expected L0 result into excel
				}
				value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
				
				//find surface area option in residue limit whether shared or lowest
				int sharedORLowest=0;
				ResultSet surfaceAreaOption = stmt.executeQuery("Select * from residue_limit"); // get equipment id
			    while (surfaceAreaOption.next()) {
			      sharedORLowest =surfaceAreaOption.getInt(15);
			    	}
			    if(sharedORLowest==0)
			    {
			    	System.out.println("SF shread");
			    	Solid_Total_surface_area =  SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName); // Calculated L3 for actual shared
			    	Cell printsurfaceareaUsed = sheet.getRow(28).getCell(column); 
			    	printsurfaceareaUsed.setCellValue(SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName)); // print surface area
			    }else
			    {
			    	System.out.println("SF lowest");
			    	Solid_Total_surface_area =  SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName); // Calculated L3 for lowest train between two
			    	Cell printsurfaceareaUsed = sheet.getRow(28).getCell(column); 
			    	printsurfaceareaUsed.setCellValue(SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName));
			    }
			    
			    
			    value_L3 = value_L2 / Solid_Total_surface_area;
			    
				
		if(no_default) // No Default limit
		{	
			System.out.println("no default inside");
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
		Cell nextprodname = sheet.getRow(row).getCell(2); 
		nextprodname.setCellValue(nprodname ); // print next product name
		Cell Solid_expec_Value_L1_print = sheet.getRow(row).getCell(4); 
		Solid_expec_Value_L1_print.setCellValue(Solid_Expec_Value_L1 ); // print expected L0 result into excel
		Cell Solid_expec_Value_L2_print = sheet.getRow(row).getCell(5); 
		Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
		Cell Solid_expec_Value_L3_print = sheet.getRow(row).getCell(6); 
		Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
		System.out.println("Expected L1: "+Solid_Expec_Value_L1);
		System.out.println("Expected L2: "+Solid_Expec_Value_L2);
		System.out.println("Expected L3: "+Solid_Expec_Value_L3);
		LowestExpectL3.add((float) Solid_Expec_Value_L3);
				
		System.out.println("LowestExpectL3"+LowestExpectL3);
		float LowestoneExpectedL3 = Collections.min(LowestExpectL3);
		System.out.println("Min" +LowestoneExpectedL3);
		Cell printlowestL3 = sheet.getRow(row).getCell(7); //print lowest L3 from iteration
		printlowestL3.setCellValue(LowestoneExpectedL3); // print expected L3 result into excel
		int getprodID = 0;
		String prodname = null;
		//Get no of used Equipment in the Current product
				ResultSet productID = stmt.executeQuery("Select * from product where name = '" + CurrenProductName + "'"); // get product name id
					while (productID.next()) 
					{
						getprodID = productID.getInt(1);
						prodname = productID.getString(2); // get name id from product table
					} // closing for productID while loop 
					
		 // Get Actual Limit Result from db
	    System.out.println("Get current product ID: "+getprodID);
	    ResultSet prod_active_relation = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + getprodID + "'");
		//get active multiple active id
		List<Integer> activelist = new ArrayList<>(); // get active list from above query
		while (prod_active_relation.next()) 
		{
		activelist.add(prod_active_relation.getInt(2));
		}
			 // get active name ,prod name and print in excel
			 ResultSet active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+ activelist.get(0) + "'");
				 String activename = null;
				 while (active.next()) 
				 {
					 activename = active.getString(2);
				 }
					 if(limitDetermination()==2)
					 {
					 System.out.println("Äctive name ------->"+activename);
					 Cell ActiveName = sheet.getRow(row).getCell(1);
					 ActiveName.setCellValue(activename); // print active name into excel
					 }else {
						 System.out.println("prodname name ------->"+prodname);
						 Cell prodName1 = sheet.getRow(row).getCell(1);
						 prodName1.setCellValue(prodname); // print active name into excel
					 }
					 row++;	
					 column++;
					 System.out.println("Row--->"+row);
					 System.out.println("column-->"+column);
		}
		}
				
 
		
		writeTooutputFile(workbook); // write output into work sheet
		connection.close();
		Thread.sleep(800);
		
		}// closing P1A1 calculation
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
*/