package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.Utils.ElementUtil;

import io.qameta.allure.Step;

import static com.qa.opencart.constants.AppConstants.*;

public class SearchResultPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//public page constructor
	public SearchResultPage(WebDriver driver) {
		
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	// 1. private By locators: OR
	private final By resultsProduct = By.cssSelector("div.product-thumb");
	
	@Step("Getting the product count on search page")
	public int getResultsProductCount() {
		
		int searchCount = eleUtil.waitForAllElementsVisible(resultsProduct, MEDIUM_DEFAULT_TIMEOUT).size();
		System.out.println(searchCount);
		return searchCount;
		
	}
	
	@Step("Clicking the product: {0}")
	public ProductInfoPage selectProduct(String ProductName) {
		
		eleUtil.doClick(By.linkText(ProductName));
		
		return new ProductInfoPage(driver);
		
		
	}

}
