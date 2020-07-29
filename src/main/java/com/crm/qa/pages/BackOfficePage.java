package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.crm.qa.base.MenuPage;

public class BackOfficePage extends MenuPage {

	@FindBy(xpath = "//span[@class='user-display']")
	@CacheLookup
	private WebElement username;

	@FindBy(xpath = "//div/i[@class='settings icon']")
	@CacheLookup
	private WebElement settingsIcon;

	@FindBy(xpath = "//span[contains(text(),'Log Out')]")
	@CacheLookup
	private WebElement logout;

	/*
	 * Get the page URL
	 */

	public String getBackOfficePageUrl() {
		return getDriver().getCurrentUrl();
	}

	/*
	 * Get the BackOfice page title
	 */

	public String getBackOfficePageTitle() {
		return getDriver().getTitle();
	}

	/*
	 * Verify the user Name
	 */

	public String getUserName() {

		return getText(username, "Getting UserName logged in BackOffice");
	}

	

	/*
	 * Click on settings for logout
	 */

	public void clickOnsettingsIcon() {
		click(settingsIcon, "Click on Settings button for logout");
	}

	/*
	 * logout
	 */

	public LoginPage logout() {
		click(settingsIcon, "Cliking on settings Icon");
		click(logout, "Logout");
		return new LoginPage();
	}
}
