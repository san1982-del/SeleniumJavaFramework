package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.Utils.ElementUtil;
import com.qa.opencart.constants.AppConstants;
public class ProductInfoPage {

	protected WebDriver driver;
	protected ElementUtil eleUtil;
	
	//public page constructor
	public ProductInfoPage(WebDriver driver) {
		
		this.driver=driver;
		eleUtil = new ElementUtil(driver);
	}
	
	// 1. private By locators: OR
	private final By productHeader = By.tagName("h1");
	private final By productImages = By.cssSelector("ul.thumbnails img");
	private final By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private final By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	private static final Logger log = LogManager.getLogger(ProductInfoPage.class);
	
	private Map<String,String> productMap;
	
	public String getHeader() {
		
		String actualHeader = eleUtil.doElementGetText(productHeader);
		System.out.println(actualHeader);
		log.info("Actual Header is: " + actualHeader);
		return actualHeader;
		
	}
	
	public int getProductImagesCount() {
		int imageCount = 
				eleUtil.waitForAllElementsVisible(productImages, AppConstants.MEDIUM_DEFAULT_TIMEOUT).size();
		log.info("Total number of images: " + imageCount);
		return imageCount;
		
	}
	
	public Map<String, String> getProductDetails() {
		
		//productMap = new HashMap<String, String>();//HashMap maintains unordered data. Random ordrer, non-index order
		//productMap = new LinkedHashMap<String, String>();//Linked HashMap maintains the data in insertion order.
		productMap = new TreeMap<String, String>();//TreeMap maintains the data in proper key insertion order. It will will sort on the basis of Keys and not values.
		
		productMap.put("Product Header", getHeader());
		productMap.put("Product Images Count", String.valueOf(getProductImagesCount())); //String.valueof converts the int value to String.
		getProductDetailsMap();
		getProductPrice();
		log.info("Full product Details: " + productMap);
		return productMap;
	}
	
	private void getProductDetailsMap() {
		 
		List<WebElement> productDetails = eleUtil.getElements(productMetaData);
		for (WebElement e: productDetails) {
			
			String metaData = e.getText();
			String meta [] = metaData.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue);
			
		}
				
	}
	
	private void getProductPrice() {
		
		List<WebElement> productPriceDetails = eleUtil.getElements(productPriceData);
		String InclusiveTaxPrice = productPriceDetails.get(0).getText();
		String ExlcusiveTaxPriceDetails [] = productPriceDetails.get(1).getText().split(":");
		String ExlcusiveTaxPrice = ExlcusiveTaxPriceDetails[1].trim();
		
		productMap.put("ProductInclusiveTaxPrice", InclusiveTaxPrice);
		productMap.put("ProductExclusiveTaxPrice", ExlcusiveTaxPrice);
	}
	
}
