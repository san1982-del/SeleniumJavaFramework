package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import static com.qa.opencart.constants.AppConstants.*;

public class SearchPageTest extends BaseTest{
	
	
	@BeforeClass
	public void SearchResultPageSetup() {
		accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	
	}
	
	
	@Test
	public void searchTest() {
		searchPage = accPage.searchProduct("macbook");
		int actResultsCount = searchPage.getResultsProductCount();
		Assert.assertEquals(actResultsCount, 3);
	}

}
