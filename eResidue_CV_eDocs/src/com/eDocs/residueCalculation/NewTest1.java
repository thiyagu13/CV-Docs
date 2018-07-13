package com.eDocs.residueCalculation;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class NewTest1 {
	static String tenant_id=Constant.tenant_id;
			
	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException, InterruptedException 
	{
		String CurrenProductName= "Liquid B";
		String nextproductname= "Test Product7";
		groupingApproach_L0forPatch(CurrenProductName);
	}
	
	public static double groupingApproach_L0forPatch(String CurrenProductName) throws IOException, ClassNotFoundException, SQLException  
	{
		float L0 = 0, Safety_Factor = 0, Active_Concen = 0, percentageAbsorbtion = 0, minNoOFPatchesWornatTime = 0, minDailyDoseperPatch = 0,doseL0=0,healthL0 = 0,min_daily_dose=0;
		int Basislimitoption = 0,frequency = 0;
		
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,percentage_absorption FROM product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id from product table
		int prodname_id = 0, lowestsolubilityID = 0,lowestADEID=0;
		//Get product id 
		while (getprodname_id.next()) {
			prodname_id = getprodname_id.getInt(1); // get name id from product table
			//frequency = getprodname_id.getInt(2); // get frequency from product table
			percentageAbsorbtion = getprodname_id.getFloat(2); //// get product dose from product table
			}
			System.out.println("name id: " + prodname_id);
			//get active id
			ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "' && tenant_id='"+tenant_id+"'");
				List<Integer> active = new ArrayList<>(); // store multiple equipment id
		    	while (getactiveID.next()) 
		    	{
		    	active.add(getactiveID.getInt(2)); // get health based value
		    	}
		    
		    //get lowest solubility within api from product
		    List<Float> Solubilities = new ArrayList<>(); // store multiple equipment id
		    	for(int activeID:active)
		    	{
		    		ResultSet getallActive = stmt.executeQuery("SELECT solubility_in_water FROM product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			Solubilities.add((float) getallActive.getFloat(1)); // get health based value
		    			System.out.println("solubilityinWater" +Solubilities + "Active:"+activeID);
		    		}
		    	}
		    	float minsolubility = Collections.min(Solubilities); // get minimum value from awithin active
		    	
		    
		    // find minimum solubility active id
		    for(int listofactiveID:active)
		    {
		    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+listofactiveID+ "' and solubility_in_water='"+minsolubility+ "' or solubility_in_water LIKE '"+minsolubility+ "' && tenant_id='"+tenant_id+"'");
		    while(getActive.next())
		    {
		    	lowestsolubilityID =getActive.getInt(1); // get health based value
		    	System.out.println("Lowest solubility active id: "+lowestsolubilityID);
		    }
		    } // end - get lowest solubility within api from product
		    
		    
		  //get lowest ADE within api from product
		    
		    List<Float> ade = new ArrayList<>(); // store multiple equipment id
		    	for(int activeID:active)
		    	{
		    		Integer HealthTerm = 0;
				    float repiratoryVolume = 0;
		    		ResultSet getallActive = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			if(HealthTerm==4)
		    			{
		    				ade.add((float) getallActive.getFloat(1) * repiratoryVolume); // get health based value
		    			}else
		    			{
		    				ade.add((float) getallActive.getFloat(1)); // get health based value
		    			}
		    		}
		    	}
		    	float minade = Collections.min(ade); // get minimum value from awithin active
		    	System.out.println("Min ADE" +minade);
		    	
		    
		    // find minimum solubility active id
		    for(int listofactiveID:active)
		    {
		    	System.out.println("listofactiveID "+listofactiveID);
		    	ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+listofactiveID+ "' && lowest_route_of_admin_value="+minade+" or lowest_route_of_admin_value LIKE "+minade+" && tenant_id='"+tenant_id+"'");
		    	while(getActive.next())
		    	{
		    		lowestADEID = getActive.getInt(1); // get health based value
		    		System.out.println("Lowest ADE active id: "+lowestADEID);
		    	}
		    } // end - get lowest solubility within api from product
		    
		    //Integer basisofcalID=0;
		    Set<Integer> basisofcalID = new HashSet<>();
		    ResultSet basisID = stmt.executeQuery("SELECT basis_of_calc_id FROM product_basis_of_calculation_relation where product_id = '"+prodname_id+ "' && tenant_id='"+tenant_id+"'");
		    while(basisID.next())
		    {
		    	basisofcalID.add(basisID.getInt(1)); // get health based value
		    }
		    
		    
		    
		    // get values using lowest active id
		   
		    for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
		    {
		    	ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_daily_dose_per_patch,min_no_of_patches_worn_at_one_time,min_daily_dose FROM product_basis_of_calculation where id="+basID+" && active_ingredient_id="+lowestsolubilityID+" && tenant_id='"+tenant_id+"'");
				while (basisOfcalc.next()) 
				{
					//dose_based_flag = basisOfcalc.getInt(5);
					//health_based_flag = basisOfcalc.getInt(11); 
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					minDailyDoseperPatch = basisOfcalc.getFloat(3);
					minNoOFPatchesWornatTime = basisOfcalc.getFloat(4);
					min_daily_dose = basisOfcalc.getFloat(5);
				} 
		    }	
		    
				ResultSet residuelimit = stmt.executeQuery("SELECT l0_option FROM residue_limit where tenant_id='"+tenant_id+"'");
			    while (residuelimit.next()) 
				{
			    Basislimitoption = residuelimit.getInt(1);
				}
			    //Basis of limit option if dose or lowest between two
					if (Basislimitoption==1 || Basislimitoption==3) 
					{
							// get dose based information
							/*ResultSet dosebaseddata = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where id="+basisofcalID+" && active_ingredient_id ='" + lowestsolubilityID + "'");
							//System.out.println("activelist.get(0)" +activelist.get(0));
							System.out.println("lowestsolubilityID" +lowestsolubilityID);
							// While Loop to iterate through all data and print results
							while (dosebaseddata.next())
							{
								Safety_Factor = dosebaseddata.getFloat(10);
								Active_Concen = dosebaseddata.getFloat(6);
								Dose_of_active = dosebaseddata.getFloat(7);
								min_no_of_dose = dosebaseddata.getFloat(8);
							}*/
							
							doseL0 =  (float) (Safety_Factor * Active_Concen * (percentageAbsorbtion /100)* minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001);
							System.out.println("Print Dose based L0" +doseL0);
							if(doseL0==0)
							{
								doseL0 = (float) min_daily_dose * Safety_Factor * (percentageAbsorbtion /100);
							}
					} // closing for loop
					
					//Basis of limit option if health or lowest between two
					if (Basislimitoption==2 || Basislimitoption==3) 
					{
						if(lowestADEID == lowestsolubilityID)
						{
							System.out.println(" same");
						// get health based L0 from database
							  Integer HealthTerm = 0;
							  float repiratoryVolume = 0;
							ResultSet Active = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM product_active_ingredient where id = '"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
							while (Active.next()) 
							{
								float health = Active.getFloat(1);
								HealthTerm = Active.getInt(2);
								repiratoryVolume = Active.getFloat(3);
								if(HealthTerm==4)
								{
									healthL0 = health *repiratoryVolume;
								}
								else
								{
									healthL0 = health;
								}
							}
						}else
						{
							System.out.println("Not same");
							float lowestADEDose = 0,lowestsolubilityDose = 0;
							for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
						    {
								ResultSet LowestPDEactive = stmt.executeQuery("SELECT min_daily_dose_per_patch FROM product_basis_of_calculation where id ="+basID+" && active_ingredient_id='"+lowestADEID+ "' && tenant_id='"+tenant_id+"'");
								//TO DO
								while(LowestPDEactive.next())
								{
									
									lowestADEDose = LowestPDEactive.getFloat(1);
									System.out.println("lowestADEDose"+lowestADEDose);
								}
						    
								ResultSet Lowestsolubilityactive = stmt.executeQuery("SELECT min_daily_dose_per_patch FROM product_basis_of_calculation where id ="+basID+" && active_ingredient_id='"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
								while(Lowestsolubilityactive.next())
								{
									lowestsolubilityDose = Lowestsolubilityactive.getFloat(1);
									System.out.println("lowestsolubilityDose"+lowestsolubilityDose);
								}
						    }
							healthL0 = ((minade/lowestADEDose) *lowestsolubilityDose);
						}
					}
					
					// get final L0 value
					if(doseL0==0)
					{
						L0 = healthL0;
					}
					if(healthL0==0)
					{
						L0 = doseL0;
					}
					if(healthL0!=0 && doseL0!=0)
					{
						L0 = Math.min(doseL0,healthL0);
					}
					System.out.println("Print dose L0: "+doseL0);
					System.out.println("Print health L0: "+healthL0);
					System.out.println("Print  L0: "+L0);
					connection.close();
		return L0; // return that L0 in this method
	} 
	

	 
		
	
}