package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.Utils.ElementUtil;

import io.qameta.allure.Step;

import static com.qa.opencart.constants.AppConstants.*;

//no mention of assertions in this class, this is POM standard
public class LoginPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	// 1. private By locators: OR
		private final By email = By.id("input-email");
		private final By password = By.id("input-password");
		private final By loginBtn = By.xpath("//input[@value='Login']");
		private final By forgotPwdLink = By.linkText("Forgotten Password11");
		private final By registerLink = By.linkText("Register");
		
		private static final Logger log = LogManager.getLogger(LoginPage.class);
		
	//public page constructor
	public LoginPage(WebDriver driver) {

		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//public page/action methods
	@Step("Getting login page title")
	public String loginPageTitle() {
		
		String actualTitle = eleUtil.waitForTitleIs(LOGIN_PAGE_TITLE, DEFAULT_TIMEOUT);
	
		log.info("Login Page Title is: " + actualTitle);
		return actualTitle;
	}
	
	@Step("login with valid username: {0} and password: {1}")
	public AccountsPage doLogin(String username, String pwd) {
		
		log.info("user credentials: " + username + ":" + pwd);
		eleUtil.waitForElementVisible(email, MEDIUM_DEFAULT_TIMEOUT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}
}
