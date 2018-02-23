package com.eDocs.Calculation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import com.eDocs.Utils.Utils;

public class NewTest {

	@Test
	public void test() throws ClassNotFoundException, SQLException {
	
		l3();
	}
	
	public double l3() throws SQLException, ClassNotFoundException
	{
		System.out.println("Test");
		double L3 = 0;
		int id=157;
		Statement stmt = Utils.db_connect();// Create Statement Object
		 ResultSet Setid = stmt.executeQuery("SELECT * FROM product_equipment_set_train where product_id='" + id + "'"); // get product name id
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
 		   /* ArrayList<Double> myAList = new ArrayList<>(Arrays.asList(1.1,2.1));
 		    double doubleSum = myAList.stream().mapToDouble(Double::doubleValue).sum();
 		    System.out.println("Add all" +doubleSum);*/
 		    }
 		  
	    
	    }
	    return L3;
	}
	
	
}
