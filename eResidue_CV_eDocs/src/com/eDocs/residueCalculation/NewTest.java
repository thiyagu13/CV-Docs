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
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class NewTest {
	
	static String tenant_id= Constant.tenant_id;
			
	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException, InterruptedException 
	{
		String CurrenProductName= "Topical Train1";
		Integer activeID= 3167;
		L0forTOPICALoption2(activeID,CurrenProductName);
	}
	
	public static double L0forTOPICALoption2(Integer activeID,String CurrenProductName) throws SQLException, ClassNotFoundException, IOException {
		float L0 = 0,doseL0=0;
		float Safety_Factor = 0, Active_Concen = 0, minAmountApplied=0,
				minApplnFrequency=0,minBodySF=0,min_daily_dose=0;
		int Basislimitoption=0;
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = connection.createStatement();
		// get current product name id from product table // for finding dose based and health flag
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,percentage_absorption FROM product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id from product table
		int prodname_id = 0;
		while (getprodname_id.next()) 
			{ 
			prodname_id = getprodname_id.getInt(1); // get name id from product table
			}
			System.out.println("name id: " + prodname_id);
			System.out.println("activeID id: " + activeID);
			ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='" + prodname_id + "' && tenant_id='"+tenant_id+"'");
				//get active multiple basis of calculation ID
				List<Integer> basislist = new ArrayList<>(); // get active list from above query
				while (prod_basis_relation_id.next()) 
				{
					basislist.add(prod_basis_relation_id.getInt(2));
				}
				System.out.println("basislist id: " + basislist);
				for(Integer basislistID:basislist)
				{
				ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_amount_applied,min_daily_application_frequency,min_body_surface_area,min_daily_dose FROM product_basis_of_calculation where id =" + basislistID + " && active_ingredient_id="+activeID+" && tenant_id='"+tenant_id+"'");
				while (basisOfcalc.next()) 
					{
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					minAmountApplied= basisOfcalc.getFloat(3);
					minApplnFrequency =basisOfcalc.getFloat(4);
					minBodySF = basisOfcalc.getFloat(5);
					min_daily_dose = basisOfcalc.getFloat(6);
					System.out.println(Safety_Factor);
					System.out.println(Active_Concen);
					System.out.println(minAmountApplied);
					System.out.println(minApplnFrequency);
					System.out.println(minBodySF);
					}
				}
					/*//get active id for getting health value
					ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
					List<Integer> active = new ArrayList<>(); // store multiple equipment id
				    while (getactiveID.next()) 
				    {
				    	active.add(getactiveID.getInt(2)); // get health based value
				    }*/
					
				    ResultSet residuelimit = stmt.executeQuery("SELECT l0_option FROM residue_limit where tenant_id='"+tenant_id+"'");
				    while (residuelimit.next()) 
					{
				    Basislimitoption = residuelimit.getInt(1);
					}
				    
				    //find health value for each active
				    float health = 0;
				    //get health based L0 from database
					ResultSet Active = stmt.executeQuery("SELECT lowest_route_of_admin_value FROM product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
					while (Active.next()) 
					{
						health = Active.getFloat(1);
						L0 = health;
					}
					System.out.println("Health LO: "+health);
				    // When dose and health flag is true in basis of calculation table
					if (Basislimitoption== 3) {
									System.out.println("Both enabled");
									doseL0 = (float) (Safety_Factor * Active_Concen * minAmountApplied * minApplnFrequency * minBodySF * 0.001);
								
									if(doseL0==0)
									{
										doseL0 = (float) (min_daily_dose * 0.001);
									}
									System.out.println("Min Daily Dose: "+doseL0);
									
										if (health <= doseL0) // compare both dose and health
										{
											L0 = health;
										}else
										{
											L0=doseL0;
										}
										
									System.out.println("Print lowest b/w health & dose L0: "+L0);
									return L0; // getting lowest L0 b/w 2
					} // for finding dose based and health flag
					
					if (Basislimitoption == 1) 
					{
						System.out.println("Dose enabled and health disabled");
							
						L0 = (float) (Safety_Factor * Active_Concen * minAmountApplied * minApplnFrequency * minBodySF * 0.001);
						if(L0==0)
						{
							L0 = (float) (min_daily_dose * 0.001);
						}
						System.out.println("Min Daily Dose: "+L0);
						System.out.println("Print Dose based L0: " +L0);
						return L0; // getting does L0 
					} 
					
					if (Basislimitoption== 2) 
					{
						System.out.println("Dose disabled and health enabled");
						// get health based L0 from database
							L0 = health;
						System.out.println("Print health L0: "+L0);
						return L0;
					}
					connection.close();
		return L0; // return that L0 in this method
	} //closing topical L0
			
	
		
	
}