package com.qa.tests;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.crm.qa.base.Base;
import com.crm.qa.pages.BackOfficePage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;

public class LoginTest extends Base {

	HomePage homePage;
	LoginPage loginPage;
	BackOfficePage backOfficePage;
	JSONObject loginUsers;

	@BeforeClass
	public void beforeClass() throws Exception {
		InputStream stream = null;

		String loginDataPath = "Data/loginUsers.json";

		try {

			stream = getClass().getClassLoader().getResourceAsStream(loginDataPath);

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
	}

	@BeforeMethod
	public void beforeMethod(Method m) throws InterruptedException {
		homePage = new HomePage();
		Thread.sleep(7000);

		loginPage = homePage.clickOnLoginLink("Home Page click on login Link");

		System.out.println("\n" + " ******** Starting test: " + m.getName() + " ************" + "\n");

	}

	@Test
	public void successFullLogin() {

		SoftAssert soft = new SoftAssert();

		String actualLoginURL = loginPage.getURLOnLoginPage();

		String expectedLoginURL = "https://ui.freecrm.com/";

		soft.assertEquals(actualLoginURL, expectedLoginURL);
		String email = loginUsers.getJSONObject("freeCrmValidCredentials").getString("useremail");
		String password = loginUsers.getJSONObject("freeCrmValidCredentials").getString("userpassword");

		loginPage.enterEmail(email, "Entering email : " + " " + email);
		loginPage.enterPassword(password, "Sending password :" + " " + password);
		backOfficePage = loginPage.pressLoginButton("Login Button pressed");

		String actualbackOfficeURL = backOfficePage.getBackOfficePageUrl();

		soft.assertEquals(actualbackOfficeURL, "bbbn,bn,bn");

		soft.assertAll();

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
