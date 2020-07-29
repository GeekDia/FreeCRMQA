package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.crm.qa.base.MenuPage;

public class NewContact extends MenuPage {

	@FindBy(xpath = "//input[@name='first_name']")
	@CacheLookup
	private WebElement firstName;

	@FindBy(xpath = "//input[@name='last_name']")
	@CacheLookup
	private WebElement lastName;

	@FindBy(xpath = "//div[@name='company']//input[@class='search']")
	@CacheLookup
	private WebElement companyName;

	@FindBy(xpath = "//input[@placeholder='Email address']")
	@CacheLookup
	private WebElement email;

	@FindBy(xpath = "//div[@name='category']")
	@CacheLookup
	private WebElement category;

	@FindBy(xpath = "//div[@class='visible menu transition']//span[@class='text'][contains(text(),'Customer')]")
	@CacheLookup
	private WebElement customer;

	@FindBy(xpath = "//button[@class='ui linkedin button']")
	@CacheLookup
	private WebElement saveButton;

	public void createNewContact(String firstName, String lastName, String companyName, String email) {

		sendKeys(this.firstName, firstName, "Entering firstName : " + firstName);
		sendKeys(this.lastName, lastName, "Entering lastName : " + lastName);
		sendKeys(this.companyName, companyName, "Entering companyName : " + companyName);
		sendKeys(this.email, email, "Entering email : " + email);

		click(category, "Cliking on category dropdown menu");
		click(customer, "Selecting the category : " + customer.getText());

		click(saveButton, "Saving new contact...");

	}

}
