package com.eDocs.Utils;

import org.openqa.selenium.WebDriver;

public class Constant {
	// call getcko driver (For latest fire fox version)
	public static String GECKO_DRIVER = "webdriver.gecko.driver";

	// Selenium getcko driver .exe path
	public static String GECKO_DRIVER_PATH = "C:\\selenium\\eResidue_CV_eDocs\\geckodriver.exe";

	// Site URL
	public static String URL = "https://www.google.co.in";

	// Screenshot folder - Contains screenshot of testcases
	public static final String SCREENSHOT_FOLDER = ".\\Screenshot\\";

	// Excel path - get input data from excel sheet
	public static String EXCEL_PATH = "C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\Test Data\\eResidue_eDocs_TestCase.xlsx";
	
	public static String EXCEL_PATH_Result = "C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\Test Data\\eResidue_eDocs_TestCase_result.xlsx";

	// Call web driver
	static Utils WD = new Utils();
	
	public static WebDriver driver = WD.getWebDriver();

/*	public static final String dbUrl = "jdbc:mysql://192.168.1.111:3306/edocs_db_integrity";
	
	public static final String username = "thiyagu";
	
	public static final String password = "Quascenta@521";	*/
	
	public static final String dbUrl = "jdbc:mysql://localhost:3306/edocs";
	
	public static final String username = "root";
	
	public static final String password = "root123";	
	
	
	public static final String equipment_Properties_Path = "C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties";
	
	public static final String product_Properties_Path = "C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Product.properties";
	
	
	
	
	
	
}
