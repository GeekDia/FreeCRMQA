package com.crm.qa.listener;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {

	
	public void onTestFailure(ITestResult result) {
		// print complete exception on console also as on testNg
		if (result.getThrowable() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			result.getThrowable().printStackTrace(pw);
			System.out.println(sw.toString());
		}
	}

}
