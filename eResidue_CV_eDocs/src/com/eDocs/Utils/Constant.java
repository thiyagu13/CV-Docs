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
	public static String EXCEL_PATH = "C:\\selenium\\eResidue_CV_eDocs\\eResidue_eDocs_TestCase.xlsx";
	public static String EXCEL_PATH_Result = "C:\\selenium\\eResidue_CV_eDocs\\eResidue_eDocs_TestCase_Result.xlsx";

	// Call web driver
	static Utils WD = new Utils();
	public static WebDriver driver = WD.getWebDriver();

	public static final String dbUrl = "jdbc:mysql://localhost:3306/selenium";
	
	public static final String username = "root";
	
	public static final String password = "root123";	
	
	public static final String productName1 = "P11";
	public static final String productName2 = "P22";
	public static final String productName3 = "P33";
	public static final String productName4 = "P44";
	
	
	
	
	
}
