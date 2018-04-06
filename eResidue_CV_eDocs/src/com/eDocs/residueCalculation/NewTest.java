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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class NewTest {
			
	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException, InterruptedException 
	{
		String CurrenProductName= "Test P4";
		float LowestoneExpectedL3 = (float) 10.0;
		Integer activeID=7;
		float Rinsevolume =  10;
		L0forSOLID(2,CurrenProductName);
		//UniversalSettings();
		//groupingApproach_L0forPatch(CurrenProductName);
	}
	
	
	public static double L0forSOLID(Integer activeID,String CurrenProductName) throws SQLException, ClassNotFoundException, IOException {
		double L0 = 0, Safety_Factor = 0, Active_Concen = 0, Dose_of_active = 0, Product_Dose = 0, min_no_of_dose = 0,frequency = 0;
		int Basislimitoption=0;
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		// get current product name id from product table // for finding dose based and health flag
		System.out.println("");
		ResultSet getprodname_id = stmt.executeQuery("SELECT * FROM product where name ='"+CurrenProductName+"'");// Execute the SQL Query to find prod id from product table
		int prodname_id = 0;
		while (getprodname_id.next()) 
			{
			prodname_id = getprodname_id.getInt(1); // get name id from product table
			frequency = getprodname_id.getFloat(6); // get frequency from product table
			Product_Dose = getprodname_id.getFloat(5); //// get product dose from product table
			}
			System.out.println("name id: " + prodname_id);
			ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='" + prodname_id + "'");
				//get active multiple active id
				List<Integer> BasisID = new ArrayList<>(); // get active list from above query
				while (prod_basis_relation_id.next()) 
				{
					BasisID.add(prod_basis_relation_id.getInt(2));
				}
			  	//System.out.println("First Active:" +activelist.get(0));// get 1st id
				System.out.println("BasisID"+BasisID);
				System.out.println("activeID"+activeID);
				for(Integer basis:BasisID) {
				ResultSet basisOfcalc = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where id ='"+basis+"' && active_ingredient_id='"+activeID+"'");
				while (basisOfcalc.next()) 
					{
					Safety_Factor = basisOfcalc.getFloat(10);
					Active_Concen = basisOfcalc.getFloat(6);
					Dose_of_active = basisOfcalc.getFloat(7);
					min_no_of_dose = basisOfcalc.getFloat(8);
					System.out.println(Safety_Factor);
					System.out.println(Dose_of_active);
					}
				}
					//get active id for getting health value
					/*ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
					List<Integer> active = new ArrayList<>(); // store multiple equipment id
				    while (getactiveID.next()) 
				    {
				    	active.add(getactiveID.getInt(2)); // get health based value
				    }*/
					
				    ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit");
				    while (residuelimit.next()) 
					{
				    Basislimitoption = residuelimit.getInt(2);
					}
				    
				    // When dose and health flag is true in basis of calculation table
					if (Basislimitoption== 3) {
									System.out.println("Both enabled");
									if (Dose_of_active == 0) { // if dose of active is null
										L0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
									} else { // if dose of active not null
										L0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
										System.out.println(Safety_Factor);
										System.out.println(Dose_of_active);
									}
									System.out.println("LO"+L0);
									// get health based L0 from database
									ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+activeID+ "'");
									while (Active.next()) 
									{
										float health = Active.getFloat(12);
										if (health <= L0) // compare both dose and health
										{
											L0 = health;
										}
									} // closing 1st while
									System.out.println("Print lowest b/w health & dose L0: "+L0);
									return L0; // getting lowest L0 b/w 2
					} // for finding dose based and health flag
					
					if (Basislimitoption == 1) {
						System.out.println("Dose enabled and health disabled");
							// get dose based information
							/*ResultSet dosebaseddata = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where id ='" + BasisID + "' && active_ingredient_id="+activeID+"");
							// While Loop to iterate through all data and print results
							while (dosebaseddata.next())
							{
								Safety_Factor = dosebaseddata.getFloat(10);
								Active_Concen = dosebaseddata.getFloat(6);
								Dose_of_active = dosebaseddata.getFloat(7);
								min_no_of_dose = dosebaseddata.getFloat(8);
							}*/
									if (Dose_of_active == 0) { // if dose of active is null
										L0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
									} else { // if dose of active not null
										L0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
									}
								System.out.println("Print Dose based L0" +L0);
								return L0; // getting lowest L0 b/w 2
					} // closing 4th while - health based L0
					
					if (Basislimitoption== 2) 
					{
						System.out.println("Dose disabled and health enabled");
						// get health based L0 from database
						ResultSet Active = stmt.executeQuery("SELECT lowest_route_of_admin_value FROM product_active_ingredient where id = '"+activeID+ "'");
						while (Active.next()) 
						{
							float health = Active.getFloat(1);
							L0 = health;
						}
						System.out.println("Print health L0: "+L0);
						return L0;
					}
					connection.close();
		//writeTooutputFile(workbook); // write output into work sheet
		return L0; // return that L0 in this method
	} //closing calculate_P1_active1_L0
	 
		
	
}