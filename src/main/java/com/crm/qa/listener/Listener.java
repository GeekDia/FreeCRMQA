package com.crm.qa.listener;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.crm.qa.base.Base;
import com.crm.qa.utils.TestUtils;

import com.crm.qa.reports.ExtentReport;

public class Listener implements ITestListener {

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

		// Retrieve browser name from testng parameters
		Map<String, String> testNGparams = new HashMap<String, String>();
		testNGparams = result.getTestContext().getCurrentXmlTest().getAllParameters();

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

		byte[] encoded = null;
		try {
			encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(srcFile));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ExtentReport.getTest().fail("Test failed",
					MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());

			ExtentReport.getTest().fail("Test fail", MediaEntityBuilder
					.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.UTF_8)).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onTestStart(ITestResult result) {
		Base base = new Base();
		ExtentReport.startTest(result.getName(), result.getMethod().getDescription()).assignCategory("Chrome")
				.assignAuthor("Bamba Dia");
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
