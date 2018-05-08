package com.eDocs.residueCalculation;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.experimental.max.MaxCore;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class NewTest {
	
	static String tenant_id= Constant.tenant_id;
			
	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException, InterruptedException 
	{
		String CurrenProductName= "CA";
		CleaningAgentCalculation(CurrenProductName);
	}
	
	public static void CleaningAgentCalculation(String CurrenProduct) throws IOException, ClassNotFoundException, SQLException 
	{/*
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH); 
		XSSFSheet sheet = workbook.getSheet("ResidueCalculation");
		 Connection connection = Utils.db_connect();
	     Statement stmt = connection.createStatement();
		
		List<String> currentproductlist = new ArrayList<>();
		currentproductlist.add("CA");
		currentproductlist.add("S1");
		currentproductlist.add("S2");
		
		List<String> nextproductlist = new ArrayList<>();
		nextproductlist.add("CA");
		nextproductlist.add("S1");
		nextproductlist.add("S2");
		
		
		Set<String> CAName =new HashSet<>(); // store all the product type
		for(String CurrenProductName : currentproductlist) // Current product list
		{
			int CurrentproductType=0;
			//get current product type
			ResultSet getproductType = stmt.executeQuery("Select product_type from product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'"); // get product name id
			while (getproductType.next()) 
			{
				CurrentproductType = getproductType.getInt(1);
			}
			
				// Check Cleaning agent included or not in the product list
				
				boolean CheckProductType =false;
				for(String Namelist:currentproductlist)
				{
					ResultSet checkDiluent = stmt.executeQuery("Select name,product_type from product where name = '" + Namelist + "' && tenant_id='"+tenant_id+"'"); // get product name id
					Integer Type = 0; // store all the product type
					while (checkDiluent.next()) 
					{
						Type = checkDiluent.getInt(2);
					}
					if(Type==1) 
					{
						CheckProductType =true;	
						ResultSet getCAName = stmt.executeQuery("Select name from product where name = '" + Namelist + "' && product_type ='" + Type + "' && tenant_id='"+tenant_id+"'"); // get product name id
						while (getCAName.next()) 
						{
							CAName.add(getCAName.getString(1));
						}
					} 	
				} //end- Check Cleaning agent included or not in the product list
			}
				System.out.println("CA Name"+CAName);
				
				
				//Iterate Current product cleaning agent
				for(String CurrenProductName:CAName)
				{
					for(String NextprodName : nextproductlist)
					{
						if(CurrenProductName.equals(NextprodName))
						{
							
						}
						else
						{
						String LimitcalculationType = sheet.getRow(39).getCell(0).getStringCellValue();
						String nprodname = null;	
						Integer nextProdID;
						float maxDD,minBatch,percentage_absorption;
						ResultSet productdata = stmt.executeQuery("Select id,name,max_daily_dose,min_batch_size,percentage_absorption from product where name ='"+NextprodName+"' && tenant_id='"+tenant_id+"' "); // get next prod name from excel and find out in db
						while (productdata.next()) {	nextProdID = productdata.getInt(1); nprodname = productdata.getString(2); maxDD = productdata.getFloat(3); minBatch = productdata.getFloat(4);  percentage_absorption =  productdata.getFloat(5);}
						
						String productType = sheet.getRow(39).getCell(1).getStringCellValue();
						System.out.println("productType--->"+productType);
						if(productType.equals("Solid")|| productType.equals("Liquid")||productType.equals("Inhalant"))
						{
							value_L1 = L0.L0forCleaningAgent(CurrenProductName) / maxDD;
							Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(5); 
							Solid_expec_Value_L0_print.setCellValue(L0.L0forCleaningAgent(CurrenProductName));
						}
						//if product is Transdermal Patch
						if(productType.equals("Patch"))
						{
							value_L1 = (L0.L0forCleaningAgent(CurrenProductName)*1000) / (maxDD *(percentage_absorption/100));
							Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(5); 
							Solid_expec_Value_L0_print.setCellValue(L0.L0forCleaningAgent(CurrenProductName));
						}
						//if product is Topical - Option2
						if(productType.equals("Topical"))
						{
							value_L1 = (L0.L0forCleaningAgent(CurrenProductName)*1000) / maxDD;
							Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(5); 
							Solid_expec_Value_L0_print.setCellValue(L0.L0forCleaningAgent(CurrenProductName));
						}
						
						Cell nextprodname = sheet.getRow(row).getCell(4); 
						nextprodname.setCellValue(nprodname); // print next product name
						
						value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
						
						//find surface area option in residue limit whether shared or lowest
						int sharedORLowest=0;
						ResultSet surfaceAreaOption = stmt.executeQuery("Select l3_surface_area_option from residue_limit where tenant_id='"+tenant_id+"'"); // get equipment id
					    while (surfaceAreaOption.next()) 
					    {
					      sharedORLowest =surfaceAreaOption.getInt(1);
					    }
					    // Check same product
					if(CurrenProductName.equals(NextprodName) && LimitcalculationType.equalsIgnoreCase("Campaign"))
					{
						Solid_Total_surface_area =  SurfaceAreaValue.sameProductSF(CurrenProductName);
					}
					else
					{
					    if(sharedORLowest==0)
					    {
					    	System.out.println("SF shread------------->" +SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName));
					    	System.out.println("CurrenProductName"+CurrenProductName);
					    	System.out.println("CurrenProductName"+CurrenProductName);
					    	Solid_Total_surface_area =  SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName); // Calculated L3 for actual shared
					    }else
					    {
					    	System.out.println("SF lowest");
					       	Solid_Total_surface_area =  SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName); // Calculated L3 for lowest train between two
					    }
					    System.out.println("Surface Area: ----------------------->"+Solid_Total_surface_area);
					}
					
					    value_L3 = value_L2 / Solid_Total_surface_area;
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
				
				Cell Solid_expec_Value_L1_print = sheet.getRow(row).getCell(6); 
				Solid_expec_Value_L1_print.setCellValue(Solid_Expec_Value_L1 ); // print expected L0 result into excel
				Cell Solid_expec_Value_L2_print = sheet.getRow(row).getCell(7); 
				Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
				Cell Solid_expec_Value_L3_print = sheet.getRow(row).getCell(8); 
				Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
				System.out.println("Expected L1: "+Solid_Expec_Value_L1);
				System.out.println("Expected L2: "+Solid_Expec_Value_L2);
				System.out.println("Expected L3: "+Solid_Expec_Value_L3);
				LowestExpectL3.add((float) Solid_Expec_Value_L3); // get all Expected L3
						
				// Get Actual Result from DB
					 System.out.println("Prouct id: "+getprodID +" Active id: "+activeID); // current product id and active id
					 int actualnextProdID = 0;
					 int actualresultrow = 41; 
						 float ActualL0Result = 0,ActualL1Result = 0,ActualL2Result = 0,ActualL3Result = 0;
						 ResultSet nextproductdata = stmt.executeQuery("Select * from product where name ='"+NextprodName+"' && tenant_id='"+tenant_id+"' "); // get next prod name from excel and find out in db
							while (nextproductdata.next()) {	actualnextProdID = nextproductdata.getInt(1);    }
							
							System.out.println("actualnextProdID"+actualnextProdID);
							ResultSet prod_cal = stmt.executeQuery("SELECT l0,l1,l2,l3 FROM product_calculation where product_id= '" + getprodID + "'&& next_prod_ids='"+  actualnextProdID+ "' && active_ingredient_id='"+activeID+ "' && tenant_id='"+tenant_id+"'");
							//While Loop to iterate through all data and print results
							while (prod_cal.next()) 
							{
								 ActualL0Result = prod_cal.getFloat(1); ActualL1Result = prod_cal.getFloat(2); ActualL2Result = prod_cal.getFloat(3); ActualL3Result = prod_cal.getFloat(4);
							}
							// Print Actual result to excel
							if(ActualL0Result==0)
							{
									Cell print_actual_L0 = sheet.getRow(row).getCell(9); 
									print_actual_L0.setCellValue("NA"); // print actual L0 result into excel
							}else {
									Cell print_actual_L0 = sheet.getRow(row).getCell(9); 
									print_actual_L0.setCellValue(ActualL0Result); // print actual L0 result into excel
							}			
							if(ActualL1Result==0)
							{
								Cell print_actual_L1 = sheet.getRow(row).getCell(10); 
								print_actual_L1.setCellValue("NA"); // print actual L1 result into excel
							}else {
								Cell print_actual_L1 = sheet.getRow(row).getCell(10); 
								print_actual_L1.setCellValue(ActualL1Result); // print actual L1 result into excel
							}		
							if(ActualL2Result==0)
							{
								Cell print_actual_L2 = sheet.getRow(row).getCell(11); 
								print_actual_L2.setCellValue("NA"); // print actual L2 result into excel
							}else {
								Cell print_actual_L2 = sheet.getRow(row).getCell(11); 
								print_actual_L2.setCellValue(ActualL2Result); // print actual L2 result into excel
							}
							if(ActualL3Result==0)
							{
								System.out.println("Zero");
								Cell print_actual_L3 = sheet.getRow(row).getCell(12); 
								print_actual_L3.setCellValue("NA"); // print actual L3 result into excel
							}else {
								System.out.println("Not Zero");
								Cell print_actual_L3 = sheet.getRow(row).getCell(12); 
								print_actual_L3.setCellValue(ActualL3Result); // print actual L3 result into excel
							}
							if(ActualL3Result!=0) {
							LowestActualL3.add((float) ActualL3Result);	
							}
						
						if(ActualL3Result==0) // this condition for if actual result not lowest(if zero)
						{
							System.out.println("No Result");
						}
						else 
						{
							System.out.println("Expected: "+Solid_Expec_Value_L3);
							System.out.println("Actual: "+ActualL3Result);
							if(Utils.toOptimizeDecimalPlacesRoundedOff(Solid_Expec_Value_L3).equals(Utils.toOptimizeDecimalPlacesRoundedOff(ActualL3Result)))
							{
								Cell printlowestL3 = sheet.getRow(row).getCell(13);
								printlowestL3.setCellValue("Pass");
								printlowestL3.setCellStyle(Utils.style(workbook, "Pass")); // for print green font				
							}else
							{
								Cell printlowestL3 = sheet.getRow(row).getCell(13);
								printlowestL3.setCellValue("Fail");
								printlowestL3.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
							}
						}
								
				row++;	
				column++;
				}
				}
				}
	*/}
			
	
		
	
}