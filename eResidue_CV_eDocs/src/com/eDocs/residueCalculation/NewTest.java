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
		String CurrenProductName= "Topical1";
		groupingApproach_L1forTOPICALSameProduct(CurrenProductName);
	}
	
	public static double groupingApproach_L1forTOPICALSameProduct(String CurrenProductName) throws IOException, ClassNotFoundException, SQLException 
	{
		float L0 = 0, doseL0=0,healthL0 = 0,Safety_Factor = 0, Active_Concen = 0,minAmountApplied=0,min_daily_dose=0,
				minApplnFrequency=0,minBodySF=1,max_amount_appled=0,max_ap_freq=0,max_Body_Sf=1;
		int Basislimitoption = 0,grouping_criteria_option=0;
		
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,max_amount_applied,max_daily_application_frequency,max_body_surface_area,grouping_criteria_option FROM product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id from product table
		int prodname_id = 0, lowestsolubilityID = 0,lowestADEID=0;
		//Get product id 
		while (getprodname_id.next()) {
			prodname_id = getprodname_id.getInt(1); // get name id from product table
			max_amount_appled = getprodname_id.getInt(2); // get name id from product table
			max_ap_freq = getprodname_id.getInt(3); // get name id from product table
			max_Body_Sf = getprodname_id.getInt(4); // get name id from product table
			grouping_criteria_option =  getprodname_id.getInt(5); 
			
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
		    		ResultSet getallActive = stmt.executeQuery("SELECT lowest_route_of_admin_value FROM product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			ade.add((float) getallActive.getFloat(1)); // get health based value
		    			System.out.println("ADE" +ade + "Active:"+activeID);
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
		    	ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_amount_applied,min_daily_application_frequency,min_body_surface_area,min_daily_dose FROM product_basis_of_calculation where id="+basID+" && active_ingredient_id="+lowestsolubilityID+" && tenant_id='"+tenant_id+"'");
				while (basisOfcalc.next()) 
				{
					//dose_based_flag = basisOfcalc.getInt(5);
					//health_based_flag = basisOfcalc.getInt(11); 
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					minAmountApplied= basisOfcalc.getFloat(3);
					minApplnFrequency =basisOfcalc.getFloat(4);
					minBodySF = basisOfcalc.getFloat(5);
					min_daily_dose = basisOfcalc.getFloat(6);
				} 
		    }	
		    
				ResultSet residuelimit = stmt.executeQuery("SELECT l0_option FROM residue_limit where tenant_id='"+tenant_id+"'");
			    while (residuelimit.next()) 
				{
			    Basislimitoption = residuelimit.getInt(1);
				}
			    System.out.println("Basislimitoption"+Basislimitoption);
			    
			    //Basis of limit option if dose or lowest between two
					if (Basislimitoption==1 || Basislimitoption==3) 
					{
							System.out.println("Dose enabled and health disabled");
							// get dose based information
							
							doseL0 = (float) (Safety_Factor * Active_Concen * minAmountApplied * minApplnFrequency * minBodySF * 0.001);
							System.out.println("Print Dose based L0" +doseL0);
							if(doseL0==0)
							{
								doseL0 = (float) (min_daily_dose * 0.001);
							}
							System.out.println("Min Daily Dose: "+doseL0);
							
					} // closing for loop
					
					//Basis of limit option if health or lowest between two
					if (Basislimitoption==2 || Basislimitoption==3) 
					{
						System.out.println("Dose disabled and health enabled");
						System.out.println("lowestsolubilityID"+lowestsolubilityID);
						System.out.println("lowestADEID: "+lowestADEID);
						if(lowestADEID == lowestsolubilityID)
						{
							System.out.println(" same");
						// get health based L0 from database
						ResultSet Active = stmt.executeQuery("SELECT lowest_route_of_admin_value FROM product_active_ingredient where id = '"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
							while (Active.next()) 
							{
								float health = Active.getFloat(1);
								healthL0 = health;
							}
						}else
						{
							System.out.println("Not same");
							float lowestADEDose=0,lowestADEminDose = 0,lowestADEminBodySF = 0,lowestsolubilityDose = 0,lowestsolubilityminAmount=0,lowestsolubilityminBodySF=0;
							for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
						    {
								ResultSet LowestPDEactive = stmt.executeQuery("SELECT min_amount_applied,min_body_surface_area FROM product_basis_of_calculation where id ="+basID+" && active_ingredient_id='"+lowestADEID+ "' && tenant_id='"+tenant_id+"'");
								//TO DO
								while(LowestPDEactive.next())
								{
									
									lowestADEminDose = LowestPDEactive.getFloat(1);
									lowestADEminBodySF = LowestPDEactive.getFloat(2);
									if(grouping_criteria_option==2)
									{
										lowestADEDose = lowestADEminDose + lowestADEminBodySF;	
									}else
									{
										lowestADEDose = lowestADEminDose;
									}
									
									System.out.println("lowestADEDose"+lowestADEDose);
								}
						    
								ResultSet Lowestsolubilityactive = stmt.executeQuery("SELECT min_amount_applied,min_body_surface_area FROM product_basis_of_calculation where id ="+basID+" && active_ingredient_id='"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
								while(Lowestsolubilityactive.next())
								{
									lowestsolubilityminAmount = Lowestsolubilityactive.getFloat(1);
									lowestsolubilityminBodySF = Lowestsolubilityactive.getFloat(2);
									if(grouping_criteria_option==2)
									{
										lowestsolubilityDose = lowestsolubilityminAmount + lowestsolubilityminBodySF;	
									}else
									{
										lowestsolubilityDose = lowestsolubilityminAmount;
									}
									
									System.out.println("lowestsolubilityDose"+lowestsolubilityDose);
								}
						    }
							healthL0 = ((minade/lowestADEDose) *lowestsolubilityDose);
						}
					}
					float L1=0;
					// get final L0 value
					if(doseL0==0)
					{
						L1 = (healthL0*1000)/(max_amount_appled * max_ap_freq * max_Body_Sf);
					}
					if(healthL0==0)
					{
						L1 = Safety_Factor * Active_Concen;
					}
					if(healthL0!=0 && doseL0!=0)
					{
						if(doseL0<healthL0)
						{
							L1 = Safety_Factor * Active_Concen;
						}
						else if(doseL0>healthL0)
						{
							L1 = (healthL0*1000)/(max_amount_appled * max_ap_freq * max_Body_Sf);
						}
						
					}
					System.out.println("Print dose L0: "+doseL0);
					System.out.println("Print health L0: "+healthL0);
					System.out.println("Print  L1: "+L1);
					connection.close();
		return L1; // return that L0 in this method
	}
			
	
		
	
}