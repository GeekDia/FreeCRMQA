package com.crm.qa.base;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.DealsPage;
import com.crm.qa.pages.TaskPage;

public class MenuPage extends Base {

	
	@FindBy(xpath = "//span[contains(text(),'Contacts')]")
	@CacheLookup
	private WebElement contactsLink;

	@FindBy(xpath = "//span[contains(text(),'Deals')]")
	@CacheLookup
	private WebElement dealsLink;

	@FindBy(xpath = "//span[contains(text(),'Tasks')]")
	@CacheLookup
	private WebElement taskLink;
	/*
	 * Click on Contacts link
	 */

	public ContactsPage clickOnContacsLink() {

		click(contactsLink, "Click On contacts link");

		return new ContactsPage();
	}

	/*
	 * Click on Contacts link
	 */

	public DealsPage clickOnDealsLink() {

		click(dealsLink, "Click On deals link");

		return new DealsPage();
	}

	/*
	 * Click on Contacts link
	 */

	public TaskPage clickOnTaskLink() {

		click(taskLink, "Click On deals link");

		return new TaskPage();
	}

}
