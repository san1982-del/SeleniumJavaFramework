package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

import io.qameta.allure.Step;

public class DriverFactory {
	
	 WebDriver driver;
	 Properties prop;
	 public static String highlight;
	 public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();
	 private static final Logger log = LogManager.getLogger(DriverFactory.class);

	
	/**
	 * This function initializes the Browser driver
	 * @param prop
	 * @return driver
	 */
	 @Step("init driver with properties: {0}")
	public WebDriver initDriver(Properties prop) {
		 
		 log.info("properties: " + prop);
		
		String Browser = prop.getProperty("browser");
		log.info("browser name : " + Browser);
		
		String Url = prop.getProperty("url");
		highlight = prop.getProperty("highlight");
		OptionsManager optionsManager = new OptionsManager(prop);
		
		switch (Browser.trim().toLowerCase()) {
		case "chrome":
			tldriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
			tldriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
			tldriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		case "safari":
			tldriver.set(new SafariDriver());
			break;
	
		default:
			//System.out.println("Plz pass the valid browser name:" + Browser);
			log.error("Plz pass the valid browser name..." + Browser);
			throw new BrowserException("Invalid Browser");
		}	
		
		getDriver().get(Url);
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		return getDriver();
		
	}
	
	
	/**
	 * getDriver: get the local thread copy of the driver
	 */
	public static WebDriver getDriver() {
		
		return tldriver.get();
	}
	
	
	/**
	 * this is used to initialize the config properties
	 * @return prop, object of Properties with loaded config.properties file
	 */
	// mvn clean install -Denv="stage"
		public Properties initProp() {

			String envName = System.getProperty("env");
			FileInputStream ip = null;
			prop = new Properties();

			try {
				if (envName == null) {
					// System.out.println("env is null, hence running the tests on QA env by default...");
					
					log.warn("env is null, hence running the tests on QA env by default...");
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				} else {
					System.out.println("Running tests on env: " + envName);
					log.info("Running tests on env: " + envName);
					switch (envName.toLowerCase().trim()) {
					case "qa":
						ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
						break;
					case "dev":
						ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
						break;
					case "stage":
						ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
						break;
					case "uat":
						ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
						break;
					case "prod":
						ip = new FileInputStream("./src/test/resources/config/prod.config.properties");
						break;

					default:
						log.error("----invalid env name---" + envName);
						throw new FrameworkException("===INVALID ENV NAME==== : " + envName);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			try {
				prop.load(ip);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return prop;
		}
		
		/**
		 * takescreenshot
		 */

		public static File getScreenshotFile() {
			File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
			return srcFile;
		}

		public static byte[] getScreenshotByte() {
			return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

		}

		public static String getScreenshotBase64() {
			return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

		}
		
		
}
