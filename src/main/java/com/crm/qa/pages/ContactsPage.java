package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.crm.qa.base.MenuPage;

public class ContactsPage extends MenuPage {

	@FindBy(xpath = "//div[@class='ui header item mb5 light-black']")
	@CacheLookup
	private WebElement contactTitle;

	@FindBy(xpath = "//button[contains(text(),'New')]")
	@CacheLookup
	private WebElement newContactButton;

	/*
	 * get contact page tile
	 */

	public String getContactPageTitle() {

		return getText(contactTitle, "Geting contact page Title");
	}

	/*
	 * click on new contact button
	 */

	public NewContact clickOnNewContactButton() {
		click(newContactButton, "Cliking on new Contact button");

		return new NewContact();
	}
}
