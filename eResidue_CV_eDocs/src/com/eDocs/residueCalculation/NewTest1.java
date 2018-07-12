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
		groupingApproach_L0forSolid(CurrenProductName);
	}
	
	public static double groupingApproach_L0forSolid(String CurrenProductName) throws IOException, ClassNotFoundException, SQLException 
	{
		double L0 = 0, Safety_Factor = 0, Active_Concen = 0, Dose_of_active = 0, Product_Dose = 0, min_no_of_dose = 0,doseL0=0,healthL0 = 0,min_daily_dose=0;
		int Basislimitoption = 0,frequency = 0;
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,dosage_interval,product_dose FROM product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id from product table
		int prodname_id = 0, /*lowestsolubilityID = 0,*/lowestADEID=0;
		//Get product id 
		while (getprodname_id.next()) {
			prodname_id = getprodname_id.getInt(1); // get name id from product table
			frequency = getprodname_id.getInt(2); // get frequency from product table
			Product_Dose = getprodname_id.getFloat(3); //// get product dose from product table
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
		    Map<Integer,Float> solubi = new HashMap<Integer,Float>(); 	
		    for(int activeID:active)
		    	{
		    		ResultSet getallActive = stmt.executeQuery("SELECT solubility_in_water FROM product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			Solubilities.add((float) getallActive.getFloat(1)); 
		    			System.out.println("solubilityinWater" +Solubilities + "Active:"+activeID);
		    			solubi.put(activeID, getallActive.getFloat(1));
		    		}
		    	}
		    float minsolubility = Collections.min(Solubilities); // get minimum value from awithin active
		    
			//get lowest active if solublity same
		    	List<Integer> duplicateSolubility  = new ArrayList<>();
		    		for (Map.Entry<Integer, Float> map : solubi.entrySet()) {
						if (map.getValue().equals(minsolubility)) {
							duplicateSolubility.add(map.getKey());
						}
					}
		    	System.out.println("duplicateSolubility: "+duplicateSolubility);
		    	
		    	List<Float> lowestvalueforSolubility  = new ArrayList<>();
		    	for(Integer Solubility: duplicateSolubility)
		    	{
		    		 ResultSet getActive = stmt.executeQuery("SELECT lowest_route_of_admin_value FROM product_active_ingredient where id= '"+Solubility+ "' && tenant_id='"+tenant_id+"'");
		    		 while(getActive.next())
		    		 {
		    			 lowestvalueforSolubility.add(getActive.getFloat(1)); 
		    		 }
		    	}
		    	System.out.println("lowestvalueforSolubility:"+lowestvalueforSolubility);
		    	float lowestsolubilityvalue = Collections.min(lowestvalueforSolubility);
		    	System.out.println("lowestsolubility:"+lowestsolubilityvalue);
		    	
		    	// find whther dose or health or both
		    	ResultSet residuelimit = stmt.executeQuery("SELECT l0_option FROM residue_limit where tenant_id='"+tenant_id+"'");
			    while (residuelimit.next()) 
				{
			    Basislimitoption = residuelimit.getInt(1);
				}
			    System.out.println("Basislimitoption"+Basislimitoption);
			    
		    	Integer lowestsolubilityID = null;
		    	
		    	if(Basislimitoption==2 || Basislimitoption==3)
		    	{
		    		for(Integer activelst:active)
		    		{
		    			ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id= '"+activelst+ "' and lowest_route_of_admin_value='"+lowestsolubilityvalue+ "' or lowest_route_of_admin_value LIKE '"+lowestsolubilityvalue+"' && tenant_id='"+tenant_id+"'");
		    			while(getActive.next())
		    			{
		    				lowestsolubilityID = getActive.getInt(1); 
		    			}
		    		}
		    	}
		    	System.out.println("lowestsolubility ID:"+lowestsolubilityID);
		    	
		    // find minimum solubility active id
		  /*  for(int listofactiveID:active)
		    {
		    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+listofactiveID+ "' and solubility_in_water='"+minsolubility+ "' or solubility_in_water LIKE '"+minsolubility+"' && tenant_id='"+tenant_id+"'");
		    while(getActive.next())
		    {
		    	lowestsolubilityID =getActive.getInt(1); 
		    	System.out.println("Lowest solubility active id: "+lowestsolubilityID);
		    }
		    }*/ // end - get lowest solubility within api from product
		    
		  //get lowest ADE within api from product
		    List<Float> ade = new ArrayList<>(); 
		    	for(int activeID:active)
		    	{
		    		Integer HealthTerm = 0;
				    float repiratoryVolume = 0;
		    		ResultSet getallActive = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			if(HealthTerm==4) {
		    			ade.add((float) getallActive.getFloat(1) * repiratoryVolume); // get health based value
		    			System.out.println("ADE" +ade + "Active:"+activeID);
		    			}
		    			else {
		    				ade.add((float) getallActive.getFloat(1)); // get health based value
			    			System.out.println("ADE" +ade + "Active:"+activeID);
		    				}
		    		}
		    	}
		    	float minade = Collections.min(ade); // get minimum value from awithin active
		    	System.out.println("Min ADE" +minade);
		    	
		    
		    // find minimum solubility active id
		    for(int listofactiveID:active)
		    {
		    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+listofactiveID+ "' and lowest_route_of_admin_value LIKE '"+minade+ "' or lowest_route_of_admin_value='"+minade+"' && tenant_id='"+tenant_id+"'");
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
		    
		    
		    //get all dose value
		   
		    Map<Integer,Float> getallDosevalue = new HashMap<Integer,Float>();
		    List<Float> allDosevalue = new ArrayList<Float>();
		    
		    Map<Integer,Float> solubilityactiveID = new HashMap<Integer,Float>();
		    List<Float> lowestsolubilities = new ArrayList<Float>();
		    
		    for(Integer actlist:active)
	    	{
		    	ResultSet getallActive = stmt.executeQuery("SELECT solubility_in_water FROM product_active_ingredient where id = '"+actlist+ "' && tenant_id='"+tenant_id+"'");
	    		while(getallActive.next())
	    		{
	    			lowestsolubilities.add((float) getallActive.getFloat(1)); 
	    			solubilityactiveID.put(actlist, getallActive.getFloat(1));
	    		}
	    	}
		    float lowestsolubilityvalueDose = Collections.min(lowestsolubilities);
			  //get lowest active if solublity same
		    	List<Integer> lowestSolubility  = new ArrayList<>();
		    		for (Map.Entry<Integer, Float> map : solubilityactiveID.entrySet()) {
						if (map.getValue().equals(lowestsolubilityvalueDose)) {
							lowestSolubility.add(map.getKey());
						}
					}
		    	System.out.println("lowestSolubility IDS: "+lowestSolubility);
		    	
		    	
		    for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
		    {
		    	for(Integer actlist:lowestSolubility)
		    	{
		    		float dosevalue;
		    		ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,dose_of_active,min_num_of_dose,min_daily_dose FROM product_basis_of_calculation where id="+basID+" && active_ingredient_id="+actlist+" && tenant_id='"+tenant_id+"'");
		    		while (basisOfcalc.next()) 
		    		{
		    			//dose_based_flag = basisOfcalc.getInt(5);
		    			//health_based_flag = basisOfcalc.getInt(11); 
		    			float Safety_Factor1 = basisOfcalc.getFloat(1);
		    			float Active_Concen1 = basisOfcalc.getFloat(2);
		    			float Dose_of_active1 = basisOfcalc.getFloat(3);
		    			float min_no_of_dose1 = basisOfcalc.getFloat(4);
		    			float min_daily_dose1 = basisOfcalc.getFloat(5);
		    			if (Dose_of_active1 == 0) 
		    			{ 
		    				dosevalue = (float) (Safety_Factor1 * Active_Concen1 * Product_Dose* (min_no_of_dose1 / frequency));
						} else { // if dose of active not null
							dosevalue = (float) (Safety_Factor1 * Dose_of_active1 * (min_no_of_dose1/ frequency));
						}
						if(dosevalue==0)
						{
							dosevalue = (float) (min_daily_dose1 * 0.001);
						}
						getallDosevalue.put(actlist, dosevalue);
						allDosevalue.add(dosevalue);
		    		}
		    	}
		    }
		    float mindosevalue = Collections.min(allDosevalue);
		    
		    
		    Integer LowestSolubilityActiveIDDose=0;
    		for (Map.Entry<Integer, Float> map : getallDosevalue.entrySet()) {
				if (map.getValue().equals(mindosevalue)) {
					LowestSolubilityActiveIDDose = map.getKey();
				}
			}
		    
    		
    		
		    for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
		    {
		    	ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,dose_of_active,min_num_of_dose,min_daily_dose FROM product_basis_of_calculation where id="+basID+" && active_ingredient_id="+LowestSolubilityActiveIDDose+" && tenant_id='"+tenant_id+"'");
				while (basisOfcalc.next()) 
				{
					//dose_based_flag = basisOfcalc.getInt(5);
					//health_based_flag = basisOfcalc.getInt(11); 
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					Dose_of_active = basisOfcalc.getFloat(3);
					min_no_of_dose = basisOfcalc.getFloat(4);
					min_daily_dose = basisOfcalc.getFloat(5);
					System.out.println("Dose_of_active"+Dose_of_active);
				} 
		    }	
		    
			    //Basis of limit option if dose or lowest between two
					if (Basislimitoption==1 || Basislimitoption==3) {
							System.out.println("Dose enabled and health disabled");
							// get dose based information
									if (Dose_of_active == 0) { // if dose of active is null
										doseL0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
									} else { // if dose of active not null
										doseL0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
									}
									if(doseL0==0)
									{
										doseL0 = min_daily_dose * 0.001;
									}
									System.out.println("Min Daily Dose: "+doseL0);
								System.out.println("Print Dose based L0" +doseL0);
					} // closing for loop
					
					//Basis of limit option if health or lowest between two
					if (Basislimitoption==2 || Basislimitoption==3) 
					{
						 	Integer HealthTerm = 0;
						    float repiratoryVolume = 0;
						if(lowestADEID == lowestsolubilityID)
						{
							System.out.println(" same");
							// get health based L0 from database
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
								System.out.println("basID: "+basID);
								System.out.println("lowestADEID: "+lowestADEID);
								ResultSet LowestPDEactive = stmt.executeQuery("SELECT dose_of_active FROM product_basis_of_calculation where id ="+basID+" && active_ingredient_id='"+lowestADEID+ "' && tenant_id='"+tenant_id+"'");
								//TO DO
								while(LowestPDEactive.next())
								{
									lowestADEDose = LowestPDEactive.getFloat(1);
									System.out.println("lowestADEDose"+lowestADEDose);
								}
						    
								ResultSet Lowestsolubilityactive = stmt.executeQuery("SELECT dose_of_active FROM product_basis_of_calculation where id ="+basID+" && active_ingredient_id='"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
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