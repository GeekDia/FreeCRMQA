package com.crm.qa.pages;

import com.crm.qa.base.Base;

public class BackOfficePage extends Base {

	
	
	/*
	 * Get the page URL
	 */
	
	public String getBackOfficePageUrl() {
		return getDriver().getCurrentUrl();
	}
	
	
}
