package com.qa.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.crm.qa.base.Base;
import com.crm.qa.base.MenuPage;
import com.crm.qa.pages.BackOfficePage;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;

public class BackOfficeTest extends Base {

	HomePage homePage;
	MenuPage menuPage;
	ContactsPage contactsPage;
	LoginPage loginPage;
	BackOfficePage backOfficePage;
	JSONObject loginUsers;

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

	@Test(priority = 1)
	public void verifyBackofficeTitle() {

		SoftAssert soft = new SoftAssert();

		String actualBackOfficeTitle = backOfficePage.getBackOfficePageTitle();

		String expectedBacOfficeTitle = "Cogmento CRM";

		soft.assertEquals(actualBackOfficeTitle, expectedBacOfficeTitle, "BackOffice Title not matched");
		soft.assertAll();

	}

	@Test(priority = 2)
	public void verifyUserName() {

		SoftAssert as = new SoftAssert();
		String actualUserName = backOfficePage.getUserName();
		String expectedUserName = "Cabdia Dia";

		as.assertEquals(actualUserName, expectedUserName);
		as.assertAll();

	}

	@Test(priority = 3)
	public void verifyContactsLink() throws InterruptedException {

		SoftAssert soft = new SoftAssert();
		menuPage = new MenuPage();
		contactsPage = menuPage.clickOnContacsLink();
		Thread.sleep(2000);

		String actualContactsPageTitle = contactsPage.getContactPageTitle();

		String expectedContactsPageTitle = "Contacts";

		soft.assertEquals(actualContactsPageTitle, expectedContactsPageTitle);

		soft.assertAll();

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
