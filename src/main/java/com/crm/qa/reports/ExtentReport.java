package com.crm.qa.reports;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {
	
	static ExtentReports extent;
	final static String filePath = "Extent.html";
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

	// we use synchronized because we need to support parallel execution
	public synchronized static ExtentReports getReporter() {
		if (extent == null) {

			ExtentHtmlReporter html = new ExtentHtmlReporter("Extent.html");
			extent = new ExtentReports();
			// add configuration
			html.config().setDocumentTitle("Free CRM App");
			html.config().setReportName("Free CRM");
			html.config().setTheme(Theme.DARK); // set theme to dark
			extent.attachReporter(html);
			
			
			
		}

		return extent;
	}

	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public static synchronized ExtentTest startTest(String testName, String desc) {
		ExtentTest test = getReporter().createTest(testName, desc);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);

		
		return test;
	}

}
