package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import static com.qa.opencart.constants.AppConstants.*;

public class AccountsPageTest extends BaseTest {
	
	
	@BeforeClass
	public void accountPageSetup() {
		
		accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@Test(description="This test validates the Accountspage  Title")
	public void accountPageTitleTest() {
		
		Assert.assertEquals(accPage.accountPageTitle(), HOME_PAGE_TITLE );
	}
	
	@Test(description="This test validates the AccountsPage Header  Title")
	public void accountPageHeaderValidation() {
		
		Assert.assertEquals(accPage.getAccPageHeaderList(), expectedAccPageHeadersList);
	}
	
	@Test
	public void searchProduct() {
		
		accPage.searchProduct("macbook");
		
	}
	

}
