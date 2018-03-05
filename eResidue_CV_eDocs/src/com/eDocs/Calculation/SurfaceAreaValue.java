package com.eDocs.Calculation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Workbook;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;

public class SurfaceAreaValue {
	
	public static String productName1 = "P11";
	public static String productName2 = "P22";
	public static String productName3 = "P33";
	public static String productName4 = "P44";

	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException {
	
		String CurrenProductName = productName1;
		 Set<String> productList = new HashSet<>(); 
		 productList.add(productName2);
		 productList.add(productName3);
		 productList.add(productName4);
		//lowestTrainbetween2();
		 for(String s:productList)
		 {
			 System.out.println("s"+s);
			 actualSharedbetween2(CurrenProductName,s);
		 }
		//groupingApproach_L0_p11();
		//limitDetermination();
	}
	
		
		//Current to next   - Actual shared SF between two products
		public static double actualSharedbetween2(String currentproductname,String nextproductname) throws SQLException, ClassNotFoundException
		{
			int currentproductID = 0,nextproductID = 0,currentproductsetcount = 0,nextproductsetcount = 0;
			Statement stmt = Utils.db_connect();// Create Statement Object
			
			//current product equipment set
			List<Integer> Currentsetcount = new ArrayList<>();
			ResultSet currentprod = stmt.executeQuery("SELECT * FROM product where name='" + currentproductname + "'"); // get product name id
		    while (currentprod.next()) {
		    	currentproductID = currentprod.getInt(1);
		    	Currentsetcount.add(currentprod.getInt(33));
		    	currentproductsetcount = currentprod.getInt(33);
		    }
		    
		    //next product equipment set
		    System.out.println("productList"+nextproductname);
		    List<Integer> Nextsetcount = new ArrayList<>();
		    ResultSet nextprod = stmt.executeQuery("SELECT * FROM product where name='" + nextproductname + "'"); // get product name id
		    while (nextprod.next()) {
		    	nextproductID = nextprod.getInt(1);
		    	Nextsetcount.add(nextprod.getInt(33));
		    	nextproductsetcount = nextprod.getInt(33);
		    }
		    System.out.println("currentproductID"+currentproductID);
		    System.out.println("nextproductID"+nextproductID);
		    System.out.println("Currentsetcount"+Currentsetcount);
		    System.out.println("Nextsetcount"+Nextsetcount);
		    
		     //Current product equipment set and total surface area
		    List<Float> TotalactualshreadList = new ArrayList<>();
		    for(int i=1;i<=currentproductsetcount;i++)
		    {
		    	Set<Integer> equipmentID = new HashSet<>();
		    	System.out.println("Current I-->: "+i);
		    	ResultSet getequipfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_equipments where product_id='" + currentproductID + "' && set_id ='" + i + "'"); // get product name id
		 	    while (getequipfromset.next()) {
		 	    	equipmentID.add(getequipfromset.getInt(4));
		 	    	}
		 	   System.out.println("Current equipmentID"+equipmentID);
		 	   
		 	   
		 	    List<Float> actualshread = new ArrayList<>();
		 	   //Next product equipment set and total surface area
		 	   for(int j=1;j<=nextproductsetcount;j++)
		 	   {
		 		   Set<Integer> nextequipmentIDs = new HashSet<>();
		 		   System.out.println("Next I-->: "+i);
		 		   ResultSet getequipIDprodset = stmt.executeQuery("SELECT * FROM product_equipment_set_equipments where product_id='" + nextproductID + "' && set_id ='" + j + "'"); // get product name id
		 		   while (getequipIDprodset.next()) 
		 		   	{
		 			   nextequipmentIDs.add(getequipIDprodset.getInt(4));
		 		   	}
		 		   System.out.println("Next equipmentID"+nextequipmentIDs);
		 		   nextequipmentIDs.retainAll(equipmentID);  // get common id between current and next
		 		   System.out.println("Common: "+nextequipmentIDs);
		 		   
		 		   float equipTotalSharedSF=0;
		 		   for(int sharedID:nextequipmentIDs) //calculate shared total sf
		 		   {
		 			   ResultSet eqSF = stmt.executeQuery("SELECT * FROM equipment where id='" + sharedID + "'"); // get product name id
		 			   while (eqSF.next())
		 			   {
		 				   equipTotalSharedSF = equipTotalSharedSF + eqSF.getFloat(13);
		 			   }
		 		   }
		 		   actualshread.add(equipTotalSharedSF);
		 	   }
		 	   TotalactualshreadList.addAll(actualshread);
		    }
		    System.out.println("Total actualshread list" +TotalactualshreadList);
		    float actualsharedbetween2 = Collections.max(TotalactualshreadList);
		    System.out.println("Maximum shared SF value:" +actualsharedbetween2);
		    return actualsharedbetween2;
		   	}
		
		
		
		
		
		
		
	//Current to next  -lowest train SF between two products
	public static double lowestTrainbetween2(String currentproductname,String nextproductname) throws SQLException, ClassNotFoundException
	{
		int currentproductID = 0,nextproductID = 0,currentproductsetcount = 0,nextproductsetcount = 0;
		Statement stmt = Utils.db_connect();// Create Statement Object
		
		//current product equipment set
		List<Integer> Currentsetcount = new ArrayList<>();
		ResultSet currentprod = stmt.executeQuery("SELECT * FROM product where name='" + currentproductname + "'"); // get product name id
	    while (currentprod.next()) {
	    	currentproductID = currentprod.getInt(1);
	    	Currentsetcount.add(currentprod.getInt(33));
	    	currentproductsetcount = currentprod.getInt(33);
	    }
	    
	    //next product equipment set
	    List<Integer> Nextsetcount = new ArrayList<>();
	    ResultSet nextprod = stmt.executeQuery("SELECT * FROM product where name='" + nextproductname + "'"); // get product name id
	    while (nextprod.next()) {
	    	nextproductID = nextprod.getInt(1);
	    	Nextsetcount.add(nextprod.getInt(33));
	    	nextproductsetcount = nextprod.getInt(33);
	    }
	    System.out.println("currentproductID"+currentproductID);
	    System.out.println("nextproductID"+nextproductID);
	    System.out.println("Currentsetcount"+Currentsetcount);
	    System.out.println("Nextsetcount"+Nextsetcount);
	   
	     //Current product equipment set and total surface area
	    List<Float> currnetProdeqSettotalSF = new ArrayList<>();
	    for(int i=1;i<=currentproductsetcount;i++)
	    {
	    	Set<Integer> equipments = new HashSet<>();
	    	System.out.println("I-->: "+i);
	    	ResultSet getequipfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_equipments where product_id='" + currentproductID + "' && set_id ='" + i + "'"); // get product name id
	 	    while (getequipfromset.next()) {
	 	    	equipments.add(getequipfromset.getInt(4));
	 	    	}
	 	   
	 	    int equiptotalSF = 0;
	 	    for(int geteqID:equipments) //get equipment surface area
	 	    {
	 	    	ResultSet eqSF = stmt.executeQuery("SELECT * FROM equipment where id='" + geteqID +"'"); // get product name id
	 	    	while (eqSF.next()) {
	 	    		equiptotalSF = equiptotalSF + eqSF.getInt(13);
	 	    		}
	 	    }
	 	   currnetProdeqSettotalSF.add((float) equiptotalSF);
	    }
	   System.out.println("CurrnetProdeqSettotalSF" +currnetProdeqSettotalSF);
	  
	   System.out.println("Next product----->");
	    
	    
	   List<Float> nextProdeqSettotalSF = new ArrayList<>();
	    //Next product equipment set and total surface area
	    for(int i=1;i<=nextproductsetcount;i++)
	    {
	    	Set<Integer> equipments = new HashSet<>();
	    	System.out.println("I-->: "+i);
	    	ResultSet getequipfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_equipments where product_id='" + nextproductID + "' && set_id ='" + i + "'"); // get product name id
	 	    while (getequipfromset.next()) {
	 	    	equipments.add(getequipfromset.getInt(4));
	 	    	}
	 	   
	 	    int equiptotalSF = 0;
	 	    for(int geteqID:equipments) //get equipment surface area
	 	    {
	 	    	ResultSet eqSF = stmt.executeQuery("SELECT * FROM equipment where id='" + geteqID +"'"); // get product name id
	 	    	while (eqSF.next()) {
	 	    		equiptotalSF = equiptotalSF + eqSF.getInt(13);
	 	    		}
	 	    }
	 	   nextProdeqSettotalSF.add((float) equiptotalSF);
	    }
	    System.out.println("nextProdeqSettotalSF"+nextProdeqSettotalSF);
	    
	    
	    ArrayList<Float> Lowestvalue= new ArrayList<>(); //storing comparison output
	    for(float currentTest:currnetProdeqSettotalSF)
	    {
	    	for(float nextTest:nextProdeqSettotalSF)
	    	{
	    		Lowestvalue.add(Float.compare(currentTest,nextTest) < 0  ?  currentTest : nextTest); 
	    	}
	    }
	    
	    float lowestTrainbetween2 = Collections.max(Lowestvalue);
	    System.out.println("Lowest value is : "+Lowestvalue);
	    System.out.println("Largest value is : "+lowestTrainbetween2);
	    
	    return lowestTrainbetween2;
	   	}
	
    
	    
		/*
		 ResultSet Setid = stmt.executeQuery("SELECT * FROM product_equipment_set_train where product_id='" + currentproductID + "'"); // get product name id
		 Set<Integer> trainset = new HashSet<>(); // store multiple equipment id
	    while (Setid.next()) {
	    	trainset.add(Setid.getInt(4));
	    }
		System.out.println("trainset"+trainset);
	    for(int trainid:trainset)
	    {
	    	 ResultSet eqfromtrainID = stmt.executeQuery("SELECT * FROM equipment_train_equipments where train_id='" + trainid + "'"); // get product name id
	    	Set<Integer> ListOfeqID = new HashSet<>(); // store multiple equipment id
 		    while (eqfromtrainID.next()) {
 		    	ListOfeqID.add(eqfromtrainID.getInt(1));
 		    	System.out.println("ListOfeqID"+ListOfeqID);
 		    }
	    
 		    for(int eqfromtrain:ListOfeqID)
 		    {
 		    	ResultSet eqSFfromtrain = stmt.executeQuery("SELECT * FROM equipment where id='" + eqfromtrain + "'"); // get product name id
 		    	Set<Double> ListOfeqSF = new HashSet<>(); // store multiple equipment id
	 		    while (eqSFfromtrain.next()) {
	 		    	ListOfeqSF.add((double) eqSFfromtrain.getInt(13));
	 		    }
	 		    System.out.println("ListOfeqSF" +ListOfeqSF);
 		  //  return ListOfeqSF;
 		    ArrayList<Double> myAList = new ArrayList<>(Arrays.asList(1.1,2.1));
 		    double doubleSum = myAList.stream().mapToDouble(Double::doubleValue).sum();
 		    System.out.println("Add all" +doubleSum);
 		    }
 		  
	    
	    }*/
	   
	
	

	
	
	
	
	
	
	public static void writeTooutputFile(Workbook workbook) throws IOException {
		try {
			FileOutputStream outFile = new FileOutputStream(new File(Constant.EXCEL_PATH_Result));
			workbook.write(outFile);
			outFile.close();
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	
		/*//if grouping approach
				public static double groupingApproach_L0_p11() throws IOException, ClassNotFoundException, SQLException 
				{
					double L0 = 0, Safety_Factor = 0, Active_Concen = 0, Dose_of_active = 0, Product_Dose = 0, min_no_of_dose = 0,frequency = 0,doseL0=0,healthL0 = 0;
					int Basislimitoption = 0;
					Statement stmt = Utils.db_connect();// Create Statement Object (Database Connection)
					ResultSet getprodname_id = stmt.executeQuery("SELECT * FROM product where name = '" + productName1 + "'");// Execute the SQL Query to find prod id from product table
					int prodname_id = 0, lowestsolubilityID = 0,lowestADEID=0;
					//Get product id 
					while (getprodname_id.next()) {
						prodname_id = getprodname_id.getInt(1); // get name id from product table
						frequency = getprodname_id.getFloat(6); // get frequency from product table
						Product_Dose = getprodname_id.getFloat(5); //// get product dose from product table
						}
						System.out.println("name id: " + prodname_id);
						//get active id
						ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
							List<Integer> active = new ArrayList<>(); // store multiple equipment id
					    	while (getactiveID.next()) 
					    	{
					    	active.add(getactiveID.getInt(2)); // get health based value
					    	}
					    
					    //get lowest solubility within api from product
					    List<Float> Solubilities = new ArrayList<>(); // store multiple equipment id
					    	for(int activeID:active)
					    	{
					    		ResultSet getallActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+activeID+ "'");
					    		while(getallActive.next())
					    		{
					    			Solubilities.add((float) getallActive.getFloat(9)); // get health based value
					    			System.out.println("solubilityinWater" +Solubilities + "Active:"+activeID);
					    		}
					    	}
					    	float minsolubility = Collections.min(Solubilities); // get minimum value from awithin active
					    	
					    
					    // find minimum solubility active id
					    for(int listofactiveID:active)
					    {
					    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+listofactiveID+ "' and solubility_in_water= '"+minsolubility+ "'");
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
					    		ResultSet getallActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+activeID+ "'");
					    		while(getallActive.next())
					    		{
					    			ade.add((float) getallActive.getFloat(12)); // get health based value
					    			System.out.println("ADE" +ade + "Active:"+activeID);
					    		}
					    	}
					    	float minade = Collections.min(ade); // get minimum value from awithin active
					    	System.out.println("Min ADE" +minade);
					    	
					    
					    // find minimum solubility active id
					    for(int listofactiveID:active)
					    {
					    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+listofactiveID+ "' and lowest_route_of_admin_value LIKE '"+minade+ "'");
					    while(getActive.next())
					    {
					    	lowestADEID =getActive.getInt(1); // get health based value
					    	System.out.println("Lowest ADE active id: "+lowestADEID);
					    }
					    } // end - get lowest solubility within api from product
					    
					    
					    // get values using lowest active id
						ResultSet basisOfcalc = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id ='" + lowestsolubilityID + "'");
							while (basisOfcalc.next()) 
							{
								//dose_based_flag = basisOfcalc.getInt(5);
								//health_based_flag = basisOfcalc.getInt(11); 
								Safety_Factor = basisOfcalc.getFloat(10);
								Active_Concen = basisOfcalc.getFloat(6);
								Dose_of_active = basisOfcalc.getFloat(7);
								min_no_of_dose = basisOfcalc.getFloat(8);
							} 
							
							ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit");
						    while (residuelimit.next()) 
							{
						    Basislimitoption = residuelimit.getInt(2);
							}
						    System.out.println("Basislimitoption"+Basislimitoption);
						    
						    //Basis of limit option if dose or lowest between two
								if (Basislimitoption==1 || Basislimitoption==3) {
										System.out.println("Dose enabled and health disabled");
										// get dose based information
										ResultSet dosebaseddata = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id ='" + lowestsolubilityID + "'");
										//System.out.println("activelist.get(0)" +activelist.get(0));
										System.out.println("lowestsolubilityID" +lowestsolubilityID);
										// While Loop to iterate through all data and print results
										while (dosebaseddata.next())
										{
											Safety_Factor = dosebaseddata.getFloat(10);
											Active_Concen = dosebaseddata.getFloat(6);
											Dose_of_active = dosebaseddata.getFloat(7);
											min_no_of_dose = dosebaseddata.getFloat(8);
										}
												if (Dose_of_active == 0) { // if dose of active is null
													doseL0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
												} else { // if dose of active not null
													doseL0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
												}
											System.out.println("Print Dose based L0" +doseL0);
								} // closing for loop
								
								//Basis of limit option if health or lowest between two
								if (Basislimitoption==2 || Basislimitoption==3) 
								{
									System.out.println("Dose disabled and health enabled");
									System.out.println("lowestsolubilityID"+lowestsolubilityID);
									System.out.println("lowestADEID"+lowestADEID);
									if(lowestADEID == lowestsolubilityID)
									{
										System.out.println(" same");
									// get health based L0 from database
									ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+lowestsolubilityID+ "'");
									while (Active.next()) 
									{
										float health = Active.getFloat(12);
										healthL0 = health;
									}
									}else
									{
										System.out.println("Not same");
										float lowestADEDose = 0,lowestsolubilityDose = 0;
										ResultSet LowestPDEactive = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id LIKE '"+lowestADEID+ "'");
										//TO DO
										while(LowestPDEactive.next())
										{
											lowestADEDose = LowestPDEactive.getFloat(7);
											System.out.println("lowestADEDose"+lowestADEDose);
										}
										ResultSet Lowestsolubilityactive = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id LIKE '"+lowestsolubilityID+ "'");
										while(Lowestsolubilityactive.next())
										{
											lowestsolubilityDose = Lowestsolubilityactive.getFloat(7);
											System.out.println("lowestsolubilityDose"+lowestsolubilityDose);
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
					return L0; // return that L0 in this method
				} 
				
				
				int LimitDeterminationOption;
				public int limitDetermination() throws ClassNotFoundException, SQLException
				{
					Statement stmt = Utils.db_connect();// Create Statement Object
					ResultSet LimitDetermination = stmt.executeQuery("SELECT * FROM residue_limit");
					while (LimitDetermination.next()) 
					{
						LimitDeterminationOption = LimitDetermination.getInt(10);
					}
					System.out.println("LimitDeterminationOption"+LimitDeterminationOption);
					return LimitDeterminationOption;
				}*/	
		
		
		
		
}
