package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.SearchResultPage;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import io.qameta.allure.Description;


public class BaseTest {
	
	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	protected LoginPage loginpage; //By default access modifier is Default due to which it will not be accessible in child class.
								  //by declaring it as protected it would be accessible in child class.
	protected AccountsPage accPage;
	protected SearchResultPage searchPage;
	protected ProductInfoPage productInfoPage;
	
	private static final Logger log = LogManager.getLogger(BaseTest.class);
	
	@Description("Initializing the driver and properties")
	@Parameters({"browser"})
	@BeforeTest
	public void setUP(String browserName) {
	df= new DriverFactory();
	prop = df.initProp();
	
	if (browserName!=null) {
		
		prop.setProperty("browser", browserName);
	}
	
	
	driver = df.initDriver(prop);
	loginpage = new LoginPage(driver);
	
	}
	
	@AfterMethod //will be running after each @test method
	public void attachScreenshot(ITestResult result) {
		if(!result.isSuccess()) {//only for failure test cases -- true
			log.info("---screenshot is taken---");
			ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
		}
		
		//ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");


	}
	
	@Description("Closing the Browser..")
	@AfterTest
	public void tearDown()
	{
		driver.quit();
		log.info("---Closing the Browser---");
	}
	
}
