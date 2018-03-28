package com.eDocs.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class NewTest {
			
	@Test
	public void test() throws ClassNotFoundException, SQLException 
	{
		String CurrenProductName= "P7";
		
		getEquipmentTrain(CurrenProductName);
		
		
	}
	
	//get current product equipment ID
		public static List<Integer> getEquipmentTrain(String CurrenProductName) throws SQLException, ClassNotFoundException  
		{
	        int currentproductID = 0, currentproductsetcount = 0;
	        //database connection
	        Connection connection = Utils.db_connect();
	        Statement stmt = connection.createStatement();
	        
	        //current product equipment set
	        // List<Integer> Currentsetcount = new ArrayList<>();
	        ResultSet currentprod = stmt.executeQuery("SELECT * FROM product where name='" + CurrenProductName + "'"); // get product name id
	        while (currentprod.next()) {
	            currentproductID = currentprod.getInt(1);
	           // Currentsetcount.add(currentprod.getInt(33));
	            currentproductsetcount = currentprod.getInt(33);
	        }
	        
	        
	        List setlist = new ArrayList<>();
	        List totalDF = new ArrayList<>();
	        List namelist = new ArrayList<>();
	        for (int i = 1; i <= currentproductsetcount; i++) 
	        { 
	        	List<Integer> currentequipmentID = new ArrayList<>();
	        	 
	 //check if only equipmnet used in the product
	            ResultSet getequipfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_equipments where product_id='" + currentproductID + "' && set_id ='" + i + "'"); // get product name id
	            while (getequipfromset.next()) 
	            {
	                System.out.println("ony equipment selected");
	                currentequipmentID.add(getequipfromset.getInt(4));
	            }
	 //check if only equipment group used in the product -current product
	           
	            List<Integer> eqgroupIDs = new ArrayList<>(); // if equipment  group means - use the below query
	            // List<Integer> equipmentgroup = new ArrayList<>();
	            ResultSet getequipgrpfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + currentproductID + " && set_id =" + i + ""); // get product name id
	            while (getequipgrpfromset.next()) {
	                System.out.println("ony equipment group selected");
	                eqgroupIDs.add(getequipgrpfromset.getInt(4)); // get group ID
	            }
	            for (int id : eqgroupIDs) // iterate group id one by one 
	            {
	                int equipmentusedcount = 0;
	                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + currentproductID + " && group_id=" + id + ""); // get product name id
	                while (geteqcountfromgrpID.next()) 
	                {
	                    equipmentusedcount = geteqcountfromgrpID.getInt(5);
	                }
	                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + id + " order by sorted_id limit " + equipmentusedcount + ""); // get product name id
	                while (geteqfromgrpID.next()) 
	                {
	                    currentequipmentID.add(geteqfromgrpID.getInt(2));
	                }
	                //  currentequipmentID.addAll(equipmentgroup);
	            }
	            
	//end: check if only equipment group used in the product -current product
	//check if only equipment train used in the product -current product
	            int gettrainID = 0;
	            ResultSet getequiptrainIDfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_train where product_id=" + currentproductID + " && set_id =" + i + ""); // get product name id
	            while (getequiptrainIDfromset.next()) {
	                System.out.println("ony equipment train selected");
	                gettrainID = getequiptrainIDfromset.getInt(4);
	            }
	            // if train used only equipmeans used the below query
	            ResultSet eqfromtrain = stmt.executeQuery("SELECT * FROM equipment_train_equipments where train_id=" + gettrainID + ""); // get product name id
	            while (eqfromtrain.next()) {
	                currentequipmentID.add(eqfromtrain.getInt(2));
	            }
	            // if train used group means - use the below query
	            Set<Integer> groupIDs = new HashSet<>();
	            ResultSet eqfromtraingroup = stmt.executeQuery("SELECT group_id FROM equipment_train_group where train_id=" + gettrainID + ""); // get product name id
	            while (eqfromtraingroup.next()) {
	                groupIDs.add(eqfromtraingroup.getInt(1));
	            }
	            for (int id : groupIDs) // iterate group id one by one (from train)
	            {
	                //Set<Integer> equipID = new HashSet();
	                int equipmentusedcount = 0;
	                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT equipment_used_count FROM equipment_train_group where group_id=" + id + ""); // get product name id
	                while (geteqcountfromgrpID.next()) {
	                    equipmentusedcount = geteqcountfromgrpID.getInt(1);
	                }
	                System.out.println("Train group count"+equipmentusedcount);
	                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + id + " order by sorted_id limit " + equipmentusedcount + ""); // get product name id
	                while (geteqfromgrpID.next()) {
	                    currentequipmentID.add(geteqfromgrpID.getInt(2));
	                }
	            }
	            
	//end: check if only equipment train used in the product -current product 
	           
	            
	            String equipmentName =null;
            	float surfaceArea = 0;
	            for(Integer eqid:currentequipmentID)
	            {
	            	
	            	ResultSet getequipdetails = stmt.executeQuery("SELECT name,surface_area FROM equipment where id="+eqid+"");
	            	while(getequipdetails.next())
	            	{
	            		equipmentName =  getequipdetails.getString(1);
	            		//surfaceArea = getequipdetails.getInt(2);
	            		surfaceArea = surfaceArea + getequipdetails.getFloat(2);
	            	}
	            	
	            }
	            
	            setlist.add(currentequipmentID);
	            totalDF.add(surfaceArea);
	            namelist.add(equipmentName);
	       
	        }
	        System.out.println("Equipment ID Setlist-> "+setlist);  
	        
	        System.out.println("Equipment SF Setlist-> "+totalDF);
	        
	        System.out.println("Equipment Name Setlist-> "+namelist);
	        
	        
	        
	        
	        
	        
	        
	        
	        connection.close();
	        return setlist;
	        
	    }
	
	
	
	
	
}