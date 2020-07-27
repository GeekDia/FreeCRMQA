package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.crm.qa.base.Base;

public class LoginPage extends Base {

	@FindBy(xpath = "//input[@name='email']")
	@CacheLookup
	private WebElement email;

	@FindBy(xpath = "//input[@name='password']")
	@CacheLookup
	private WebElement password;

	@FindBy(xpath = "//div[@class='ui fluid large blue submit button']")
	@CacheLookup
	private WebElement loginButton;

	@FindBy(xpath = "//a[contains(text(),'Forgot your password?')]")
	@CacheLookup
	private WebElement forgetPassword;

	@FindBy(xpath = "//a[contains(text(),'Classic CRM')]")
	@CacheLookup
	private WebElement classicCRM;

	@FindBy(xpath = "//a[contains(text(),'Sign Up')]")
	@CacheLookup
	private WebElement signUp;

	/*
	 * Enter email
	 * 
	 */

	public LoginPage enterEmail(String email, String msg) {

		sendKeys(this.email, email, msg);
		return this;
	}

	/*
	 * Enter password
	 * 
	 */

	public LoginPage enterPassword(String password, String msg) {

		sendKeys(this.password, password, msg);
		return this;
	}

	/*
	 * Click on login Button
	 */

	public BackOfficePage pressLoginButton(String msg) {

		click(loginButton, msg);

		return new BackOfficePage();
	}
	
	/*
	 * Click on login Button
	 */

	public String getURLOnLoginPage() {
		
		getDriver().getCurrentUrl();

		return getDriver().getCurrentUrl();
	}
	
	/*
	 * login to Backoffice
	 */
	
	public BackOfficePage loginToBacOffice(String userEmail, String password) {
		
		
		return new BackOfficePage();
	}

}
