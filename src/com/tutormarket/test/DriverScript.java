package com.tutormarket.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.tutormarket.test.Constants;
import com.tutormarket.test.Xls_Reader;
import com.tutormarket.test.DriverScript;
import com.tutormarket.test.Keywords;

public class DriverScript {
	public static Logger APP_LOGS;

	// suite.xlsx
	public Xls_Reader suiteXLS;
	public int currentSuiteID;
	public String currentTestSuite;

	// current test suite
	public static Xls_Reader currentTestSuiteXLS;
	public static int currentTestCaseID;
	public static String currentTestCaseName;
	public static int currentTestStepID;
	public static String currentKeyword;
	public static int currentTestDataSetID = 2;
	public static Method method[];
	public static Method capturescreenShot_method;

	public static Keywords keywords;
	public static String keyword_execution_result;
	public static ArrayList<String> resultSet;
	public static String data;
	public static String object;
	//this is the commited code
	// properties
	public static Properties CONFIG;
	public static Properties OR;

	public DriverScript() throws NoSuchMethodException, SecurityException {
		keywords = new Keywords();
		method = keywords.getClass().getMethods();
		// capturescreenShot_method =keywords.getClass().getMethod("captureScreenshot",String.class,String.class);
	}

	public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		FileInputStream fs = new FileInputStream("config.properties");
		CONFIG = new Properties();
		CONFIG.load(fs);

		fs = new FileInputStream("or.properties");
		OR = new Properties();
		OR.load(fs);

		DriverScript test = new DriverScript();
		test.start();
	}

	public void start() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		APP_LOGS = Logger.getLogger("devpinoyLogger");
		APP_LOGS.debug("Hello");
		APP_LOGS.debug("Properties loaded. Starting testing");
		APP_LOGS.debug("Intialize Suite xlsx");

		suiteXLS = new Xls_Reader("/Users/anandmahajan/Desktop/Anoop/VivinoWorkspace/tutormarket/Suite.xlsx");

		for (currentSuiteID = 2; currentSuiteID <= suiteXLS.getRowCount(Constants.TEST_SUITE_SHEET); currentSuiteID++) {
			APP_LOGS.debug(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID) + " -- " + suiteXLS.getCellData("Test Suite", "Runmode", currentSuiteID));
			// System.out.println(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID)+" -- "+ suiteXLS.getCellData("Test Suite", "Runmode", currentSuiteID));
			currentTestSuite=suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID);
			if (suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE, currentSuiteID).equals(Constants.RUNMODE_YES)) {
				// APP_LOGS.debug("******Executing the Suite******"+suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, currentSuiteID)); // Sheet name , Col name, Row no.
				currentTestSuiteXLS = new Xls_Reader("/Users/anandmahajan/Desktop/Anoop/VivinoWorkspace/tutormarket/"+currentTestSuite+".xlsx");// Read the xls file which is mention in TSID in Suit.xls file
																																		// Runmode=Y
				for (currentTestCaseID = 2; currentTestCaseID < currentTestSuiteXLS.getRowCount("Test Cases"); currentTestCaseID++) { // Its a loop for read data form Test cases sheet row wise
																																		// APP_LOGS.debug(currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET,
																																		// Constants.TCID, currentTestCaseID)+" --
																																		// "+currentTestSuiteXLS.getCellData("Test Cases", "Runmode",
																																		// currentTestCaseID));
					currentTestCaseName = currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, currentTestCaseID);
					if (currentTestSuiteXLS.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID).equals(Constants.RUNMODE_YES)) {
						// APP_LOGS.debug("Executing the test case -> "+currentTestCaseName);
						// System.out.println("Executing the test case -> "+currentTestCaseName);
						if (currentTestSuiteXLS.isSheetExist(currentTestCaseName)) {// Checking Test case sheet present or not in login page XLS file
							for (currentTestDataSetID = 2; currentTestDataSetID <= currentTestSuiteXLS.getRowCount(currentTestCaseName); currentTestDataSetID++) {
								resultSet = new ArrayList<String>();
								// APP_LOGS.debug("Iteration number "+(currentTestDataSetID-1));
								// checking the runmode for the current data set
								// if(currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.RUNMODE, currentTestDataSetID).equals(Constants.RUNMODE_YES)){
								if (currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.RUNMODE, currentTestDataSetID).equals(Constants.RUNMODE_YES)) {
									// iterating through all keywords
									executeKeywords(); // multiple sets of data
								}
								createXLSReport();

							}

						} else {
							// iterating through all keywords
							resultSet = new ArrayList<String>();
							executeKeywords();// no data with the test
							createXLSReport();
						}

					}

				}

			}

		}

	}

	public void executeKeywords() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (currentTestStepID = 2; currentTestStepID <= currentTestSuiteXLS.getRowCount(Constants.TEST_STEPS_SHEET); currentTestStepID++) {
			if (currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID, currentTestStepID))) {
				data = currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.DATA, currentTestStepID);
				System.out.println(data);
				if (data.startsWith(Constants.DATA_START_COL)) {
					// read actual data value from the corresponding column
					// String newsplittext=data.split(Constants.DATA_SPLIT)[1];
					// data=newsplittext;
					data = currentTestSuiteXLS.getCellData(currentTestCaseName, data.split(Constants.DATA_SPLIT)[1], currentTestDataSetID);
					System.out.println("This is the value of data: " + data);
					// System.out.println(newsplittext);
					// System.out.println("This is the value of data---"+data);
				} else if (data.startsWith(Constants.CONFIG)) {
					// read actual data value from config.properties
					data = CONFIG.getProperty(data.split(Constants.DATA_SPLIT)[1]);
				} else {
					// by default read actual data value from or.properties
					data = OR.getProperty(data);
				}
				object = currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.OBJECT, currentTestStepID);
				currentKeyword = currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.KEYWORD, currentTestStepID);
				// APP_LOGS.debug(currentKeyword);

				// code to execute the keywords as well
				// reflection API

				for (int i = 0; i < method.length; i++) {
					// System.out.println(method[i].getName());
					// System.out.println("");
					if (method[i].getName().equals(currentKeyword)) {
						// System.out.println(method[i].getName());
						// System.out.println(object );
						// System.out.println(data);
						keyword_execution_result = (String) method[i].invoke(keywords, object, data);
						// APP_LOGS.debug(keyword_execution_result);
						resultSet.add(keyword_execution_result);
						
					//	if(keyword_execution_result.equals("FAIL"))
					//	capturescreenShot_method.invoke(keywords,currentTestSuite+"_"+currentTestCaseName+"_TS"+currentTestStepID+"_"+(currentTestDataSetID-1),keyword_execution_result);

					}

				}

			}
		}
	}

	public void createXLSReport() {

		String colName = Constants.RESULT + (currentTestDataSetID - 1);
		boolean isColExist = false;

		for (int c = 0; c < currentTestSuiteXLS.getColumnCount(Constants.TEST_STEPS_SHEET); c++) {
			if (currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, c, 1).equals(colName)) {
				isColExist = true;
				break;
			}
		}

		if (!isColExist)
			currentTestSuiteXLS.addColumn(Constants.TEST_STEPS_SHEET, colName);
		int index = 0;
		for (int i = 2 ; i <= currentTestSuiteXLS.getRowCount(Constants.TEST_STEPS_SHEET) ; i++) {

			if (currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID, i))) {
				if (resultSet.size() == 0)
					currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i, Constants.KEYWORD_SKIP);
				else
					currentTestSuiteXLS.setCellData(Constants.TEST_STEPS_SHEET, colName, i, resultSet.get(index));
				index++;
			}

		}

		if (resultSet.size() == 0) {
			// skip
			currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID, Constants.KEYWORD_SKIP);
			return;
		} else {
			for (int i = 0; i < resultSet.size(); i++) {
				if (!resultSet.get(i).equals(Constants.KEYWORD_PASS)) {
					currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID, resultSet.get(i));
					return;
				}
			}
		}
		currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID, Constants.KEYWORD_PASS);
		// if(!currentTestSuiteXLS.getCellData(currentTestCaseName, "Runmode",currentTestDataSetID).equals("Y")){}

	}

}

