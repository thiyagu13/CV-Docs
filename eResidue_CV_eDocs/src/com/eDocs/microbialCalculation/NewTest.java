package com.eDocs.solidCalculation;

import java.io.IOException;
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
	public void test() throws ClassNotFoundException, SQLException, IOException 
	{
		String currentproductname= "P1";
		String nextproductname= "P2";
		actualSharedbetween2(currentproductname, nextproductname);
		
		
	}
	
	
	 public static double actualSharedbetween2(String currentproductname, String nextproductname) throws SQLException, ClassNotFoundException {
	        int currentproductID = 0, nextproductID = 0, currentproductsetcount = 0, nextproductsetcount = 0;
	        //database connection
	        Connection connection = Utils.db_connect();
	        Statement stmt = connection.createStatement();
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
	        //find total surface area between two products
	        List<Float> TotalactualshreadList = new ArrayList<>();
	        for (int i = 1; i <= currentproductsetcount; i++) {
	            List<Integer> currentequipmentID = new ArrayList<>();
	            //check if only equipmnet used in the product
	            ResultSet getequipfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_equipments where product_id='" + currentproductID + "' && set_id ='" + i + "'"); // get product name id
	            while (getequipfromset.next()) {
	                System.out.println("ony equipment selected");
	                currentequipmentID.add(getequipfromset.getInt(4));
	            }
	            //check if only equipment group used in the product -current product
	            // if equipment  group means - use the below query
	            List<Integer> eqgroupIDs = new ArrayList<>();
	            // List<Integer> equipmentgroup = new ArrayList<>();
	            ResultSet getequipgrpfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + currentproductID + " && set_id =" + i + ""); // get product name id
	            while (getequipgrpfromset.next()) {
	                System.out.println("ony equipment group selected");
	                eqgroupIDs.add(getequipgrpfromset.getInt(4));
	            }
	            for (int id : eqgroupIDs) // iterate group id one by one (from train)
	            {
	                int equipmentusedcount = 0;
	                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + currentproductID + " && group_id=" + id + ""); // get product name id
	                while (geteqcountfromgrpID.next()) {
	                    equipmentusedcount = geteqcountfromgrpID.getInt(5);
	                }
	                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + id + " order by sorted_id limit " + equipmentusedcount + ""); // get product name id
	                while (geteqfromgrpID.next()) {
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
	            ResultSet eqfromtraingroup = stmt.executeQuery("SELECT * FROM equipment_train_group where train_id=" + gettrainID + ""); // get product name id
	            while (eqfromtraingroup.next()) {
	                groupIDs.add(eqfromtraingroup.getInt(2));
	            }
	            for (int id : groupIDs) // iterate group id one by one (from train)
	            {
	                //Set<Integer> equipID = new HashSet();
	                int equipmentusedcount = 0;
	                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT * FROM equipment_train_group where group_id=" + id + ""); // get product name id
	                while (geteqcountfromgrpID.next()) {
	                    equipmentusedcount = geteqcountfromgrpID.getInt(3);
	                }
	                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + id + " order by sorted_id limit " + equipmentusedcount + ""); // get product name id
	                while (geteqfromgrpID.next()) {
	                    currentequipmentID.add(geteqfromgrpID.getInt(2));
	                }
	            }
//end: check if only equipment train used in the product -current product
	            
	            List<Float> actualshread = new ArrayList<>();
//Next product equipment set and total surface area
	            for (int j = 1; j <= nextproductsetcount; j++) {
	                List<Integer> nextequipmentIDs = new ArrayList<>();
	                //check if only equipment used in the product -Next product
	                ResultSet getequipIDprodset = stmt.executeQuery("SELECT * FROM product_equipment_set_equipments where product_id='" + nextproductID + "' && set_id ='" + j + "'"); // get product name id
	                while (getequipIDprodset.next()) {
	                    nextequipmentIDs.add(getequipIDprodset.getInt(4));
	                }
 //check if only equipment group used in the product -Next product
	                // if equipment  group means - use the below query
	                List<Integer> eqgroupIDsofNext = new ArrayList<>();
	                // List<Integer> equipmentgroupofNext = new ArrayList<>();
	                ResultSet getequipgrpfromsetNextprod = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + nextproductID + " && set_id =" + j + ""); // get product name id
	                while (getequipgrpfromsetNextprod.next()) {
	                    System.out.println("ony equipment group selected");
	                    eqgroupIDsofNext.add(getequipgrpfromsetNextprod.getInt(4));
	                }
	                for (int id : eqgroupIDsofNext) // iterate group id one by one (from train)
	                {
	                    int equipmentusedcount = 0;
	                    ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + nextproductID + " && group_id=" + id + ""); // get product name id
	                    while (geteqcountfromgrpID.next()) {
	                        equipmentusedcount = geteqcountfromgrpID.getInt(5);
	                    }
	                    ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + id + " order by sorted_id limit " + equipmentusedcount + ""); // get product name id
	                    while (geteqfromgrpID.next()) {
	                        nextequipmentIDs.add(geteqfromgrpID.getInt(2));
	                    }
	                    // nextequipmentIDs.addAll(equipmentgroupofNext);
	                }
	                //End: check if only equipment group used in the product -Next product
	                //check if only equipment train used in the product -current product
	                int nextprodtrainID = 0;
	                ResultSet nextgetequiptrainIDfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_train where product_id=" + nextproductID + " && set_id =" + j + ""); // get product name id
	                while (nextgetequiptrainIDfromset.next()) {
	                    System.out.println("ony equipment train selected");
	                    nextprodtrainID = nextgetequiptrainIDfromset.getInt(4);
	                }
	                // if train used only equip means used the below query
	                ResultSet eqfromtrainnextprod = stmt.executeQuery("SELECT * FROM equipment_train_equipments where train_id=" + nextprodtrainID + ""); // get product name id
	                while (eqfromtrainnextprod.next()) {
	                    nextequipmentIDs.add(eqfromtrainnextprod.getInt(2));
	                }
	                // if train used group means - use the below query
	                Set<Integer> nextprodgroupIDs = new HashSet<>();
	                ResultSet eqfromtraingroupNextProd = stmt.executeQuery("SELECT group_id FROM equipment_train_group where train_id=" + nextprodtrainID + ""); // get product name id
	                while (eqfromtraingroupNextProd.next()) {
	                    nextprodgroupIDs.add(eqfromtraingroupNextProd.getInt(2));
	                }
	                for (int ids : nextprodgroupIDs) // iterate group id one by one (from train)
	                {
	                    //Set<Integer> equipID = new HashSet();
	                    int equipmentusedcount = 0;
	                    ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT equipment_used_count FROM equipment_train_group where group_id=" + ids + ""); // get product name id
	                    while (geteqcountfromgrpID.next()) {
	                        equipmentusedcount = geteqcountfromgrpID.getInt(1);
	                    }
	                    ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + ids + " order by sorted_id limit " + equipmentusedcount + ""); // get product name id
	                    while (geteqfromgrpID.next()) {
	                        nextequipmentIDs.add(geteqfromgrpID.getInt(2));
	                    }
	                }
	                //end: check if only equipment train used in the product -current product
	                System.out.println("Current equipmentID" + currentequipmentID);
	                System.out.println("Next equipmentID" + nextequipmentIDs);
	                nextequipmentIDs.retainAll(currentequipmentID); // get common id between current and next
	                System.out.println("Common: " + nextequipmentIDs);
	                float equipTotalSharedSF = 0;
	                for (int sharedID : nextequipmentIDs) //calculate shared total SF
	                {
	                    ResultSet eqSF = stmt.executeQuery("SELECT * FROM equipment where id='" + sharedID + "'"); // get product name id
	                    while (eqSF.next()) {
	                        equipTotalSharedSF = equipTotalSharedSF + eqSF.getFloat(13);
	                    }
	                }
	                actualshread.add(equipTotalSharedSF);
	            }
	            TotalactualshreadList.addAll(actualshread);
	        }
	        float actualsharedbetween2 = Collections.max(TotalactualshreadList);
	        System.out.println("Maximum shared SF value:" + actualsharedbetween2);
	        connection.close();
	        return actualsharedbetween2;
	    }
	
}
