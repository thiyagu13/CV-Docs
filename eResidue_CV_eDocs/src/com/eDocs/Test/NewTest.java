package com.eDocs.Test;

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
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class NewTest {
			
	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException 
	{
		String CurrenProductName= "train product";
		float LowestoneExpectedL3 = (float) 10.0;
		float Rinsevolume =  10;
		
		getEquipmentTrain(CurrenProductName, LowestoneExpectedL3);
		
		
	}
	
	   //get current product equipment Train
		public static Float getEquipmentTrain(String CurrenProductName,Float LowestoneExpectedL3) throws SQLException, ClassNotFoundException, IOException  
		{
			XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); 
			XSSFSheet sheet = workbook.getSheet("test");
			
	        int currentproductID = 0, currentproductsetcount = 0; float L4cTrain=0;
	       
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
	        
	        
	        List<Object> setlist = new ArrayList<>();
	        List<Float> equipSetTotalSF = new ArrayList<>();
	        List<Object> equipSetNamelist = new ArrayList<>();
	        
	        	int L4Row = 41;
	        
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
	           
	            List<String>  eqnamelist = new ArrayList<>();
	            String equipmentName =null;
            	float surfaceArea = 0;
	            for(Integer eqid:currentequipmentID)
	            {
	            	
	            	ResultSet getequipdetails = stmt.executeQuery("SELECT name,surface_area FROM equipment where id="+eqid+"");
	            	while(getequipdetails.next())
	            	{
	            		eqnamelist.add(getequipdetails.getString(1));
	            		//equipmentName = getequipdetails.getString(1);	            		
	            		surfaceArea = surfaceArea + getequipdetails.getFloat(2);
	            	}
	            	//System.out.println("Equipment Name Setlist-> "+eqnamelist);
	            }
	            System.out.println("Equipment Name Setlist-> "+eqnamelist);
	            setlist.add(currentequipmentID);
	            equipSetTotalSF.add(surfaceArea);
	            equipSetNamelist.add(eqnamelist);
	          //  System.out.println("equipSetNamelist-> "+equipSetNamelist);
	            
	           
		        // Get Train rinse volume for each set
		        		Integer trainID = null ;
		        	    ResultSet set = stmt.executeQuery("SELECT train_id FROM product_equipment_set_train where product_id=" + currentproductID + " && set_id="+i+"");
			         	while(set.next())
			         	{
			         		trainID = set.getInt(1);       		
			         	}
		      
			        float TrainRinsevolume=0;
		 	        ResultSet eqtrain = stmt.executeQuery("SELECT rinse_volume FROM equipment_train where id="+trainID+"");
		         	while(eqtrain.next())
		         	{
		         		TrainRinsevolume = eqtrain.getFloat(1);       		
		         	}
		      
		         	Calculation getrinsevolume = new Calculation();
		         	getrinsevolume.eqRinseVolume();
		         	
		         	System.out.println("getrinsevolume.eqRinseVolume() "+getrinsevolume.eqRinseVolume());
		         // equipment rinse volume()
					 if(getrinsevolume.eqRinseVolume()==0) //check rinset from univ setting or each equipment
					 {
						 L4cTrain = (LowestoneExpectedL3 *  surfaceArea) / (TrainRinsevolume * 1000) ;
						 Cell equipRinse = sheet.getRow(L4Row).getCell(32);
						 equipRinse.setCellValue(TrainRinsevolume); 
						 
						 Cell trainL4c = sheet.getRow(L4Row).getCell(33);
						 trainL4c.setCellValue(L4cTrain); 		
						 System.out.println("L4cTrain"+L4cTrain);
					 }else {
						 L4cTrain = (float) ((LowestoneExpectedL3 *  surfaceArea) / (getrinsevolume.eqRinseVolume() * 1000)) ;
						 Cell equipRinse = sheet.getRow(L4Row).getCell(32);
						 equipRinse.setCellValue(getrinsevolume.eqRinseVolume()); 
						 
						 Cell trainL4c = sheet.getRow(L4Row).getCell(33);
						 trainL4c.setCellValue(L4cTrain); 		
						 System.out.println("L4cTrain"+L4cTrain);
					 }
					 
		         	System.out.println("surfaceArea: "+surfaceArea);
		         	System.out.println("TrainRinsevolume: "+TrainRinsevolume);
		 	     //   L4cTrain = (LowestoneExpectedL3 *  surfaceArea) / (TrainRinsevolume * 1000) ;
		 	        System.out.println("L4cTrain "+L4cTrain);      
		 	        
		 	       L4Row++;
		 	       
		 	      // System.out.println("equipSetNamelist"+equipSetNamelist);
	        }
	        L4Row++;
	        
	        System.out.println("Equipment ID Setlist-> "+setlist);  	        
	        System.out.println("Equipment SF Setlist-> "+equipSetTotalSF);	        
	        System.out.println("Equipment Name Setlist-> "+equipSetNamelist);

	               
	        connection.close();
	        writeTooutputFile(workbook); // write output into work sheet
	        return L4cTrain;
	        
	    }
		
		
		
		
		
		
		
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