package com.crm.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestUtils {

	public static long PAGE_LOAD_TIMEOUT = 60;

	public static long WAIT = 100;

	static Workbook workbook;
	static Sheet sheet;

	public static String TEST_DATASHEET_PATH = System.getProperty("user.dir")
			+ "/src/main/resources/Data/contactsData.xlsx";

	public static Object[][] getExcelData(String sheetName) throws IOException, InvalidFormatException {

		FileInputStream file = null;

		try {
			file = new FileInputStream(TEST_DATASHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			workbook = WorkbookFactory.create(file);

		} catch (IOException e) {
			e.printStackTrace();
		}

		Sheet sheet = workbook.getSheet(sheetName); // we access to our sheet name data

		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		for (int j = 0; j < sheet.getLastRowNum(); j++) {

			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {

				data[j][k] = sheet.getRow(j + 1).getCell(k).toString();
			}

		}
		return data;

	}

	/*
	 * 
	 * This method get the datetime in this format : "dd-MM-yyyy-HH-mm-ss" It is
	 * used for screenshot in failure testCase
	 * 
	 */

	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

		Date date = new Date();

		return dateFormat.format(date);

	}

}
