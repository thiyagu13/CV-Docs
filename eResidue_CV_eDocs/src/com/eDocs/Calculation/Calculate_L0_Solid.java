package com.eDocs.Calculation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;

public class Calculate_L0_Solid {
	
	// this is only for when dose based and health check box enabled - both option
		public static double calculate_P1A1_L0() throws SQLException, ClassNotFoundException, IOException {
			double L0 = 0, Safety_Factor = 0, Active_Concen = 0, Dose_of_active = 0, Product_Dose = 0, min_no_of_dose = 0,frequency = 0;
			int basis_of_calc_id = 0, dose_based_flag, health_based_flag;
			XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); // Create workbook
			XSSFSheet sheet = workbook.getSheet("Solid_Calculation");
			Statement stmt = Utils.db_connect();// Create Statement Object (Database Connection)
			// get product name id from product table // for finding dose based and health flag
			ResultSet getprodname_id = stmt.executeQuery("SELECT * FROM product where name = '" + sheet.getRow(8).getCell(1) + "'");// Execute the SQL Query to find prod id from product table
			while (getprodname_id.next()) {
				int prodname_id = getprodname_id.getInt(1); // get name id from product table
				System.out.println("name id: " + prodname_id);
				ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='" + prodname_id + "'");
				//get active multiple active id
				List<Integer> activelist = new ArrayList<>();
				while (prod_basis_relation_id.next()) {
					activelist.add(prod_basis_relation_id.getInt(2));
				    }
				/*//for get all the actives available in the list
				for (Integer activeID:activelist) //get id from set
			    {*/
			    
				/*while (prod_basis_relation_id.next()) {
					int prodbasis_of_calc_id = prod_basis_relation_id.getInt(2); // get basis of calc id from basis of calc relation table
				 */	
				  	System.out.println("Second Active:" +activelist.get(0));// get 1st id
					ResultSet basisOfcalc = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where id ='" + activelist.get(0) + "'");
					while (basisOfcalc.next()) {
						dose_based_flag = basisOfcalc.getInt(5);
						health_based_flag = basisOfcalc.getInt(11); // finding dose and health flag in basis of calculation table
						
						if (dose_based_flag == 1 && health_based_flag == 1) {
							ResultSet getname_id = stmt.executeQuery("SELECT * FROM product where name = '" + sheet.getRow(8).getCell(1) + "'");// Execute
							while (getname_id.next()) {
								int name_id = getname_id.getInt(1); // get name id from product table
								System.out.println("name id: " + name_id);
								// get basis of calculation table relation id from basis of calc relation table
								/*ResultSet basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='"+ name_id + "'");
								while (basis_relation_id.next()) {
									basis_of_calc_id = basis_relation_id.getInt(2); // get basis of calc id from basis of calc relation table
									System.out.println("Basis of calc id: " + basis_of_calc_id);
								}*/
								// get dose based information
								ResultSet dosebaseddata = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where id ='" + activelist.get(0) + "'");
								// While Loop to iterate through all data and print results
								while (dosebaseddata.next()) {
									Safety_Factor = dosebaseddata.getFloat(10);
									Active_Concen = dosebaseddata.getFloat(6);
									Dose_of_active = dosebaseddata.getFloat(7);
									min_no_of_dose = dosebaseddata.getFloat(8);
									System.out.println("active dose: " + Dose_of_active);
									// get product information from product table
									ResultSet productdata = stmt.executeQuery("SELECT * FROM product where name = '"+ sheet.getRow(8).getCell(1).getStringCellValue() + "'");
									while (productdata.next()) {
										frequency = productdata.getFloat(6);
										Product_Dose = productdata.getFloat(5);
										if (Dose_of_active == 0) { // if dose of active is null
											L0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
											System.out.println("L0 with product dose: " + L0);
										} else { // if dose of active not null
											L0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
											System.out.println("dose of active L0: " + L0);
										}
										System.out.println("Dose L0:" + L0); // getting dose L0 here
										// get health based L0 from database
										ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where name = '"+ sheet.getRow(8).getCell(2).getStringCellValue() + "'");
										while (Active.next()) {
											float health = Active.getFloat(12);
											if (health <= L0) // compare both dose and health
											{
												L0 = health;
												System.out.println("Health L0:" + L0);
											}
											System.out.println("Print health & dose L0: "+L0);
											return L0; // getting lowest L0 b/w 2
										} // closing 1st while
									} // closing 2st while
								} // closing 3st while
							} // closing 4th while - health based L0
						} // for finding dose based and health flag
						if (dose_based_flag == 1 && health_based_flag == 0) {
							System.out.println("Dose enabled and health disabled");
							ResultSet getname_id = stmt.executeQuery("SELECT * FROM product where name = '" + sheet.getRow(8).getCell(1) + "'");// Execute
							while (getname_id.next()) {
								int name_id = getname_id.getInt(1); // get name id from product table
								System.out.println("name id: " + name_id);
								// get basis of calculation table relation id from basis of calc relation table
								ResultSet basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='"
												+ name_id + "'");
								/*while (basis_relation_id.next()) {
									basis_of_calc_id = basis_relation_id.getInt(2); // get basis of calc id from basis of calc relation table
									System.out.println("Basis of calc id: " + basis_of_calc_id);
								}*/
								// get dose based information
								ResultSet dosebaseddata = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where id ='" + activelist.get(0) + "'");
								// While Loop to iterate through all data and print results
								while (dosebaseddata.next()) {
									Safety_Factor = dosebaseddata.getFloat(10);
									Active_Concen = dosebaseddata.getFloat(6);
									Dose_of_active = dosebaseddata.getFloat(7);
									min_no_of_dose = dosebaseddata.getFloat(8);
									// get product information from product table
									ResultSet productdata = stmt.executeQuery("SELECT * FROM product where name = '"+ sheet.getRow(8).getCell(1).getStringCellValue() + "'");
									while (productdata.next()) {
										frequency = productdata.getFloat(6);
										Product_Dose = productdata.getFloat(5);
										if (Dose_of_active == 0) { // if dose of active is null
											L0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
											System.out.println("L0 with product dose: " + L0);
										} else { // if dose of active not null
											L0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
											System.out.println("dose of active L0: " + L0);
										}
										System.out.println("Print Dose L0");
										return L0; // getting lowest L0 b/w 2
									} // closing 1st while
								} // closing 2st while
							} // closing 3st while
						} // closing 4th while - health based L0
						if (dose_based_flag == 0 && health_based_flag == 1) {
							System.out.println("Dose disabled and health enabled");
							// get health based L0 from database
							ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where name = '"+ sheet.getRow(8).getCell(2).getStringCellValue() + "'");
							while (Active.next()) {
								float health = Active.getFloat(12);
								L0 = health;
								System.out.println("Health L0:" + L0);
							}
						}
						System.out.println("Print health L0");
						return L0;
						
					} // for finding dose based and health flag
				} // for finding dose based and health flag
			/*}*/
		//workbook.getSheet("Solid_Calculation").getRow(42).getCell(3).setCellValue(L0);
			writeTooutputFile(workbook); // write output into work sheet
			return L0; // return that L0 in this method
		}

	/*// this is only for when dose based and health check box enabled - both option
	public static double calculate_P1A1_L0() throws SQLException, ClassNotFoundException, IOException {
		double L0 = 0, Safety_Factor = 0, Active_Concen = 0, Dose_of_active = 0, Product_Dose = 0, min_no_of_dose = 0,frequency = 0;
		int basis_of_calc_id = 0, dose_based_flag, health_based_flag;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); // Create workbook
		XSSFSheet sheet = workbook.getSheet("Solid_Calculation");
		Statement stmt = Utils.db_connect();// Create Statement Object (Database Connection)
		// get product name id from product table // for finding dose based and health flag
		ResultSet getprodname_id = stmt.executeQuery("SELECT * FROM product where name = '" + sheet.getRow(8).getCell(1) + "'");// Execute the SQL Query to find prod id from product table
		while (getprodname_id.next()) {
			int prodname_id = getprodname_id.getInt(1); // get name id from product table
			System.out.println("name id: " + prodname_id);
			ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='" + prodname_id + "'");
			while (prod_basis_relation_id.next()) {
				int prodbasis_of_calc_id = prod_basis_relation_id.getInt(2); // get basis of calc id from basis of calc relation table
				System.out.println("Product Basis of calc id: " + prodbasis_of_calc_id);
				ResultSet basisOfcalc = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where id ='" + prodbasis_of_calc_id + "'");
				while (basisOfcalc.next()) {
					dose_based_flag = basisOfcalc.getInt(5);
					health_based_flag = basisOfcalc.getInt(11); // finding dose and health flag in basis of calculation table
					
					if (dose_based_flag == 1 && health_based_flag == 1) {
						ResultSet getname_id = stmt.executeQuery("SELECT * FROM product where name = '" + sheet.getRow(8).getCell(1) + "'");// Execute
						while (getname_id.next()) {
							int name_id = getname_id.getInt(1); // get name id from product table
							System.out.println("name id: " + name_id);
							// get basis of calculation table relation id from basis of calc relation table
							ResultSet basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='"+ name_id + "'");
							while (basis_relation_id.next()) {
								basis_of_calc_id = basis_relation_id.getInt(2); // get basis of calc id from basis of calc relation table
								System.out.println("Basis of calc id: " + basis_of_calc_id);
							}
							// get dose based information
							ResultSet dosebaseddata = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where id ='" + basis_of_calc_id + "'");
							// While Loop to iterate through all data and print results
							while (dosebaseddata.next()) {
								Safety_Factor = dosebaseddata.getFloat(10);
								Active_Concen = dosebaseddata.getFloat(6);
								Dose_of_active = dosebaseddata.getFloat(7);
								min_no_of_dose = dosebaseddata.getFloat(8);
								System.out.println("active dose: " + Dose_of_active);
								// get product information from product table
								ResultSet productdata = stmt.executeQuery("SELECT * FROM product where name = '"+ sheet.getRow(8).getCell(1).getStringCellValue() + "'");
								while (productdata.next()) {
									frequency = productdata.getFloat(6);
									Product_Dose = productdata.getFloat(5);
									if (Dose_of_active == 0) { // if dose of active is null
										L0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
										System.out.println("L0 with product dose: " + L0);
									} else { // if dose of active not null
										L0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
										System.out.println("dose of active L0: " + L0);
									}
									System.out.println("Dose L0:" + L0); // getting dose L0 here
									// get health based L0 from database
									ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where name = '"+ sheet.getRow(8).getCell(2).getStringCellValue() + "'");
									while (Active.next()) {
										float health = Active.getFloat(12);
										if (health <= L0) // compare both dose and health
										{
											L0 = health;
											System.out.println("Health L0:" + L0);
										}
										System.out.println("Print health & dose L0: "+L0);
										return L0; // getting lowest L0 b/w 2
									} // closing 1st while
								} // closing 2st while
							} // closing 3st while
						} // closing 4th while - health based L0
					} // for finding dose based and health flag
					if (dose_based_flag == 1 && health_based_flag == 0) {
						System.out.println("Dose enabled and health disabled");
						ResultSet getname_id = stmt.executeQuery("SELECT * FROM product where name = '" + sheet.getRow(8).getCell(1) + "'");// Execute
						while (getname_id.next()) {
							int name_id = getname_id.getInt(1); // get name id from product table
							System.out.println("name id: " + name_id);
							// get basis of calculation table relation id from basis of calc relation table
							ResultSet basis_relation_id = stmt.executeQuery("SELECT * FROM edocs.product_basis_of_calculation_relation where product_id='"
											+ name_id + "'");
							while (basis_relation_id.next()) {
								basis_of_calc_id = basis_relation_id.getInt(2); // get basis of calc id from basis of calc relation table
								System.out.println("Basis of calc id: " + basis_of_calc_id);
							}
							// get dose based information
							ResultSet dosebaseddata = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where id ='" + basis_of_calc_id + "'");
							// While Loop to iterate through all data and print results
							while (dosebaseddata.next()) {
								Safety_Factor = dosebaseddata.getFloat(10);
								Active_Concen = dosebaseddata.getFloat(6);
								Dose_of_active = dosebaseddata.getFloat(7);
								min_no_of_dose = dosebaseddata.getFloat(8);
								// get product information from product table
								ResultSet productdata = stmt.executeQuery("SELECT * FROM product where name = '"+ sheet.getRow(8).getCell(1).getStringCellValue() + "'");
								while (productdata.next()) {
									frequency = productdata.getFloat(6);
									Product_Dose = productdata.getFloat(5);
									if (Dose_of_active == 0) { // if dose of active is null
										L0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
										System.out.println("L0 with product dose: " + L0);
									} else { // if dose of active not null
										L0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
										System.out.println("dose of active L0: " + L0);
									}
									System.out.println("Print Dose L0");
									return L0; // getting lowest L0 b/w 2
								} // closing 1st while
							} // closing 2st while
						} // closing 3st while
					} // closing 4th while - health based L0
					if (dose_based_flag == 0 && health_based_flag == 1) {
						System.out.println("Dose disabled and health enabled");
						// get health based L0 from database
						ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where name = '"+ sheet.getRow(8).getCell(2).getStringCellValue() + "'");
						while (Active.next()) {
							float health = Active.getFloat(12);
							L0 = health;
							System.out.println("Health L0:" + L0);
						}
					}
					System.out.println("Print health L0");
					return L0;
					
				} // for finding dose based and health flag
			} // for finding dose based and health flag
			
		}
	//	workbook.getSheet("Solid_Calculation").getRow(42).getCell(3).setCellValue(L0);
		writeTooutputFile(workbook); // write output into work sheet
		return L0; // return that L0 in this method
	}*/

	/*
	 * public static double Solid_P2_L0() throws IOException {
	 * 
	 * XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
	 * XSSFSheet sheet = workbook.getSheetAt(0); double Solid_f_Safety_Factor =
	 * sheet.getRow(6).getCell(12).getNumericCellValue(); // SF for former product
	 * double Solid_f_Active_Concen =
	 * sheet.getRow(6).getCell(4).getNumericCellValue(); // Active Concen. for
	 * former product double Solid_f_Product_Dose =
	 * sheet.getRow(6).getCell(6).getNumericCellValue(); // Product Dose for former
	 * product double Solid__f_min_daily_dose =
	 * sheet.getRow(6).getCell(8).getNumericCellValue(); // Min Dose for former
	 * product
	 * 
	 * double Solid_f_frequency = sheet.getRow(6).getCell(8).getNumericCellValue();
	 * // Frequency for later product
	 * 
	 * //Formula for L1 to other product double L0 = Solid_f_Safety_Factor *
	 * Solid_f_Active_Concen * Solid_f_Product_Dose *
	 * (Solid__f_min_daily_dose/Solid_f_frequency) ;
	 * 
	 * writeTooutputFile(workbook); // write output into work sheet return L0; }
	 * 
	 * public static double Solid_P3_L0() throws IOException {
	 * 
	 * XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
	 * XSSFSheet sheet = workbook.getSheetAt(0); double Solid_f_Safety_Factor =
	 * sheet.getRow(7).getCell(12).getNumericCellValue(); // SF for former product
	 * double Solid_f_Active_Concen =
	 * sheet.getRow(7).getCell(4).getNumericCellValue(); // Active Concen. for
	 * former product double Solid_f_Product_Dose =
	 * sheet.getRow(7).getCell(6).getNumericCellValue(); // Product Dose for former
	 * product double Solid__f_min_daily_dose =
	 * sheet.getRow(5).getCell(8).getNumericCellValue(); // Min Dose for former
	 * product
	 * 
	 * double Solid_f_frequency = sheet.getRow(7).getCell(8).getNumericCellValue();
	 * // Frequency for later product
	 * 
	 * //Formula for L1 to other product double L0 = Solid_f_Safety_Factor *
	 * Solid_f_Active_Concen * Solid_f_Product_Dose *
	 * (Solid__f_min_daily_dose/Solid_f_frequency) ;
	 * 
	 * writeTooutputFile(workbook); // write output into work sheet return L0; }
	 * 
	 * public static double Solid_P4_L0() throws IOException {
	 * 
	 * XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
	 * XSSFSheet sheet = workbook.getSheetAt(0); double Solid_f_Safety_Factor =
	 * sheet.getRow(8).getCell(12).getNumericCellValue(); // SF for former product
	 * double Solid_f_Active_Concen =
	 * sheet.getRow(8).getCell(4).getNumericCellValue(); // Active Concen. for
	 * former product double Solid_f_Product_Dose =
	 * sheet.getRow(8).getCell(6).getNumericCellValue(); // Product Dose for former
	 * product double Solid__f_min_daily_dose =
	 * sheet.getRow(8).getCell(8).getNumericCellValue(); // Min Dose for former
	 * product double Solid_f_frequency =
	 * sheet.getRow(8).getCell(8).getNumericCellValue(); // Frequency for later
	 * product
	 * 
	 * //Formula for L1 to other product double L0 = Solid_f_Safety_Factor *
	 * Solid_f_Active_Concen * Solid_f_Product_Dose *
	 * (Solid__f_min_daily_dose/Solid_f_frequency) ;
	 * 
	 * writeTooutputFile(workbook); // write output into work sheet return L0; }
	 * 
	 * public static double Solid_P5_L0() throws IOException {
	 * 
	 * XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
	 * XSSFSheet sheet = workbook.getSheetAt(0); double Solid_f_Safety_Factor =
	 * sheet.getRow(9).getCell(12).getNumericCellValue(); // SF for former product
	 * double Solid_f_Active_Concen =
	 * sheet.getRow(9).getCell(4).getNumericCellValue(); // Active Concen. for
	 * former product double Solid_f_Product_Dose =
	 * sheet.getRow(9).getCell(6).getNumericCellValue(); // Product Dose for former
	 * product double Solid__f_min_daily_dose =
	 * sheet.getRow(9).getCell(8).getNumericCellValue(); // Min Dose for former
	 * product
	 * 
	 * double Solid_f_frequency = sheet.getRow(9).getCell(8).getNumericCellValue();
	 * // Frequency for later product
	 * 
	 * //Formula for L1 to other product double L0 = Solid_f_Safety_Factor *
	 * Solid_f_Active_Concen * Solid_f_Product_Dose *
	 * (Solid__f_min_daily_dose/Solid_f_frequency) ;
	 * 
	 * writeTooutputFile(workbook); // write output into work sheet return L0; }
	 * 
	 */

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
