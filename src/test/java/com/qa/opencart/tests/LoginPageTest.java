package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static com.qa.opencart.constants.AppConstants.*;



@Feature("F 50: Open Cart - Login Feature")
@Epic("Epic 100: design pages for open cart application")
@Story("US 101: implement login page for open cart application")
public class LoginPageTest extends BaseTest {
	//no mention of driver in this class, this is POM standard.
	
	
	
	@Description("checking open cart login page title...")
	@Severity(SeverityLevel.MINOR)
	@Owner("Sandeep Dahiya")
	@Test(description="This test validates the Loginpage  Title")
	public void validateLoginPageTitle() {
		
		String actualTitle = loginpage.loginPageTitle();
		Assert.assertEquals(actualTitle, LOGIN_PAGE_TITLE );
	}
	
	@Description("check user is able to login with valid user credentials...")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Sandeep Dahiya")
	@Test(priority = Short.MAX_VALUE, description="This test performs the login in the application")
	public void doLoginTest() {
		
		accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.accountPageTitle(), HOME_PAGE_TITLE );
	}
}
