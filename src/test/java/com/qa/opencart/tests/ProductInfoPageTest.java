package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.Utils.CSVUtil;
import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest{
	
	
	@BeforeClass
	public void productInfoPageSetup() {
		
		accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"macbook", "MacBook Air"},
			{"imac", "iMac"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"samsung", "Samsung Galaxy Tab 10.1"}
		};
	}
	
	@Test(dataProvider = "getProductTestData")
	public void productHeaderTest(String searchKey, String productName) {
		searchPage =  accPage.searchProduct(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		String actHeader = productInfoPage.getHeader();
		Assert.assertEquals(actHeader, productName);
	}
	
	@DataProvider
	public Object[][] getProductCSVData() {
		return CSVUtil.csvData("product");
	
	}
	
	@Test(dataProvider = "getProductCSVData")
	public void productImageCountTest(String searchKey, String productName, String expectedImageCount) {
		searchPage =  accPage.searchProduct(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		int actImageCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(String.valueOf(actImageCount), expectedImageCount);
	}
	
	@Test
	public void productDetailsTest() {
		
		searchPage = accPage.searchProduct("macbook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		Map<String,String> actualProductDetails = productInfoPage.getProductDetails();
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(actualProductDetails.get("Brand"), "Apple");//P
		softAssert.assertEquals(actualProductDetails.get("Product Code"), "Product 18");//P
		softAssert.assertEquals(actualProductDetails.get("Availability"), "Out Of Stock");//P
		softAssert.assertEquals(actualProductDetails.get("ProductInclusiveTaxPrice"), "$2,000.00");//P
		softAssert.assertEquals(actualProductDetails.get("ProductExclusiveTaxPrice"), "$2,000.00");//P
		
		softAssert.assertAll();
		
	}
	

}
