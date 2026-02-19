package com.qa.opencart.pages;


import static com.qa.opencart.constants.AppConstants.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.Utils.ElementUtil;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//public page constructor
	public AccountsPage(WebDriver driver) {
		
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
		
	}
	
	// 1. private By locators: OR
	private final By headers = By.cssSelector("div.col-sm-9 > h2");
	private final By search = By.cssSelector("#search > input");
	private final By searchButton = By.cssSelector("div#search button");
	 private static final Logger log = LogManager.getLogger(AccountsPage.class);
	 
	 
	@Step("Getting acc page title")
	public String accountPageTitle() {
		String actualTitle = eleUtil.waitForTitleIs(HOME_PAGE_TITLE, DEFAULT_TIMEOUT);
		log.info("Home Page Title is: " + actualTitle);
		return actualTitle;
	}
	
	@Step("getting acc page headers")
	public List<String> getAccPageHeaderList(){
		
		int headersCount = eleUtil.getElementsCount(headers);
		List<WebElement> headerList = eleUtil.getElements(headers);
		List<String> actualHeaderList = new ArrayList<>();
		
		for (WebElement headerName : headerList) {
			String actualHeader = headerName.getText();
			actualHeaderList.add(actualHeader);
			
		}
		
		return actualHeaderList;
	}
	
	@Step("perform search: {0}")
	public SearchResultPage searchProduct(String porductName) {
		
		eleUtil.doSendKeys(search, porductName);
		eleUtil.doClick(searchButton);
		
		return new SearchResultPage(driver);
	}
	
}
