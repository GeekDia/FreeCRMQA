package com.qa.tests;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.crm.qa.base.Base;
import com.crm.qa.base.MenuPage;
import com.crm.qa.pages.BackOfficePage;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.pages.NewContact;
import com.crm.qa.utils.TestUtils;

public class NewContactTest extends Base {

	HomePage homePage;
	MenuPage menuPage;
	ContactsPage contactsPage;
	LoginPage loginPage;
	BackOfficePage backOfficePage;
	NewContact newContactPage;
	JSONObject loginUsers;
	
	String sheetName = "contacts";

	@BeforeClass
	public void beforeClass() throws Exception {

		InputStream stream = null;
		String pathData = "Data/loginUsers.json";

		try {
			stream = getClass().getClassLoader().getResourceAsStream(pathData);

			JSONTokener tokener = new JSONTokener(stream);

			loginUsers = new JSONObject(tokener);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		} finally {
			if (stream != null)
				stream.close();
		}

		invokeWebDriver();

		homePage = new HomePage();
		Thread.sleep(7000);
		loginPage = homePage.clickOnLoginLink("Clicking on signin link");
		Thread.sleep(2000);

	}

	@BeforeMethod
	public void beforeMethod(Method m) throws InterruptedException {

		// homePage = new HomePage();
		Thread.sleep(2000);
		System.out.println("\n" + " ******** Starting test: " + m.getName() + " ************" + "\n");
		backOfficePage = loginPage.loginToBacOffice(
				loginUsers.getJSONObject("freeCrmValidCredentials").getString("useremail"),
				loginUsers.getJSONObject("freeCrmValidCredentials").getString("userpassword"));

	}
	
	
	@Test(dataProvider = "getContactsData")
	public void validateCreateNewContact(String firstName, String lastName, String company, String email) throws InterruptedException {
		
		menuPage = new MenuPage();
		contactsPage = menuPage.clickOnContacsLink();
		Thread.sleep(4000);
		newContactPage = contactsPage.clickOnNewContactButton();
		Thread.sleep(4000);
		newContactPage.createNewContact(firstName, lastName, company, email);
		
		Thread.sleep(3000);
		
	}

	
	
	
	@DataProvider
	public Object[][] getContactsData() throws InvalidFormatException, IOException {
		
		return TestUtils.getExcelData(sheetName);
		
	}
	
	@AfterMethod
	public void AfterMethod() {

		loginPage = backOfficePage.logout();

	}

	@AfterClass
	public void tearDown() throws Exception {
		try {
			getDriver().close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			getDriver().quit();
		}

	}
}
