package com.eDocs.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Utils {

	private static HashMap<String, WebDriver> driverObjMap=new HashMap<String, WebDriver>();
	
	//Method of screenshot
	public static void getScreenShot(String file) throws IOException {
		try {

			File scrFile = ((TakesScreenshot) Constant.driver)
					.getScreenshotAs(OutputType.FILE);
			DateFormat dateFormat = new SimpleDateFormat("HH_mm_ssa yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			FileUtils.copyFile(scrFile,
					new File(file + dateFormat.format(cal.getTime())));
		} catch (IOException ioe) {
			ioe.getStackTrace();
		}
	}

	
	  /*public static XSSFSheet getExcelSheet() throws IOException 
	  {
	  FileInputStream file = new FileInputStream(Constant.EXCEL_PATH);
	  @SuppressWarnings("resource")
	  XSSFWorkbook workbook = new XSSFWorkbook(file); 
	  XSSFSheet sheet = workbook.getSheetAt(1); 
	  return sheet; 
	  }*/
	
	  
	  //get xlsx sheet
	  public static XSSFWorkbook getExcelSheet(String Excelpath) throws IOException 
	  {
	  FileInputStream file = new FileInputStream(Excelpath);
	  XSSFWorkbook workbook = new XSSFWorkbook(file); 
	  return workbook; 
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
	  
	  
	  
	  public  WebDriver getWebDriver() {
		 // System.setProperty("webdriver.chrome.driver", "C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\chromedriver.exe");
			WebDriver driver = new FirefoxDriver();
			driverObjMap.put(getClass().getName(),driver);
			return driver;
		}
		public static WebDriver getDriverDetails(String className){
			
			return driverObjMap.get(className);
		}
	  
	  
		
		
		public static CellStyle style(XSSFWorkbook workbook, String result ) // print green or red font for status into excel
	  	{
	  	 CellStyle style = ((org.apache.poi.ss.usermodel.Workbook) workbook).createCellStyle();
		// style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		 XSSFFont font = (XSSFFont) ((org.apache.poi.ss.usermodel.Workbook) workbook).createFont();
		 if(result.equals("Pass"))
		 {
	     font.setColor(IndexedColors.GREEN.getIndex());
		 }
		 else
		 {
			 font.setColor(IndexedColors.RED.getIndex());
		 }
	     style.setFont(font);
	     return style;
		 //emptyname_actual_print.setCellStyle(style);
	  	}
	  	
	  
	  	
	  	
		public static String captureScreenshot_eachClass(WebDriver driver, String sFileName, String classname) // to capture screenshot and save into separate folder
	    {
			String Seperator=System.getProperty("file.separator");
	        sFileName = sFileName+".png";
	        try
	        {
	            File file = new File("screenshots All TC" + Seperator + classname);
	            if (!file.exists())
	            {
	                System.out.println("File created somewhere" + file);
	                file.mkdir();
	            }

	            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	            File targetFile = new File("screenshots All TC" + Seperator + classname + Seperator + sFileName);
	            FileUtils.copyFile(sourceFile, targetFile);
	            return sFileName;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	        
	    }
		
		
		
		public static Connection db_connect() throws SQLException, ClassNotFoundException {
			// Load mysql jdbc driver
			Class.forName("com.mysql.jdbc.Driver");

			// Create Connection to DB
			Connection connection = (Connection) DriverManager.getConnection(Constant.dbUrl, Constant.username, Constant.password);

			// Create Statement Object
					//Statement stmt = (Statement) connection.createStatement();
			return connection;
			
		}
		
		
	
		
		
		public static String toOptimizeDecimalPlacesRoundedOff(double valueDouble) {

	        /** The PdfTemplate with the total number of pages. */
	        /*
	         * Double roundedValue = Math.round(valueDouble * 100.0) / 100.0; return
	         * roundedValue.toString();
	         */
	        if (0.00 >= valueDouble)
	            return "";

	        BigDecimal value = BigDecimal.valueOf(valueDouble);

	        if (value.compareTo(new BigDecimal(100)) >= 0) {
	            return value.setScale(3, BigDecimal.ROUND_HALF_UP).toString();
	        } else if (value.compareTo(new BigDecimal(10)) >= 0) {
	            return value.setScale(3, BigDecimal.ROUND_HALF_UP).toString();
	        } else if (value.compareTo(new BigDecimal(1)) >= 0) {
	            return value.setScale(3, BigDecimal.ROUND_HALF_UP).toString();
	        } else {
	            int zeros = 0;
	            BigDecimal valueTest = value;
	            while (valueTest.compareTo(new BigDecimal(1)) < 0) {
	                valueTest = valueTest.multiply(new BigDecimal(10));
	                zeros++;
	                if (zeros == 10) {
	                    break;
	                }
	            }
	            zeros += 2;

	            if (value.setScale(zeros, BigDecimal.ROUND_HALF_UP).toString().contains("E")) {
	                return value.setScale(zeros, BigDecimal.ROUND_HALF_UP).toString().replace("E", " x 10<sup>") + "</sup>";
	            }

	            return value.setScale(zeros, BigDecimal.ROUND_HALF_UP).toString();
	        }
	    }
	  
	 

}
