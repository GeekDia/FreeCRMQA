package com.crm.qa.listener;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.crm.qa.base.Base;
import com.crm.qa.reports.ExtentReport;
import com.crm.qa.utils.TestUtils;




public class Listener implements ITestListener {

	Map<String, String> testNGparams;
	TestUtils utils = new TestUtils();

	public void onTestFailure(ITestResult result) {
		// print complete exception on console also as on testNg
		if (result.getThrowable() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			result.getThrowable().printStackTrace(pw);
			System.out.println(sw.toString());
		}

		// Take screenshot on test failure
		// Here is the file format : screenshots/<browsermName>/<datetime /<testClass>
		// /methodName.png

		

		Base base = new Base();

		// take screenshot and saved as a file object
		File srcFile = ((TakesScreenshot) base.getDriver()).getScreenshotAs(OutputType.FILE);

		// Get the path were to copy tghe file :

		String imagePath = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator
				+ testNGparams.get("browser") + File.separator + utils.getDateTime() + File.separator
				+ result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";

		// copy this screenshot in a file on the system

		try {
			FileUtils.copyFile(srcFile, new File(imagePath));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void onTestStart(ITestResult result) {
		// Retrieve browser name from testng parameters
				testNGparams = new HashMap<String, String>();
				testNGparams = result.getTestContext().getCurrentXmlTest().getAllParameters();
			// Get the test Author
			String author = testNGparams.get("author");
			String browser = testNGparams.get("browser");
		Base base = new Base();
		ExtentReport.startTest(result.getName(), result.getMethod().getDescription())
				.assignCategory(browser)
				.assignAuthor(author);
	}
	
	
	public void onTestSuccess(ITestResult result) {


		ExtentReport.getTest().log(Status.PASS, "Test passed");
	}

	
	public void onTestSkipped(ITestResult result) {
		ExtentReport.getTest().log(Status.SKIP, "Test skipped");
	}

	
	public void onFinish(ITestContext context) {
		ExtentReport.getReporter().flush();
	}

}
