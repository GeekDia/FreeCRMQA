package com.crm.qa.base;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.Status;
import com.crm.qa.reports.ExtentReport;
import com.crm.qa.utils.TestUtils;
import com.crm.qa.utils.WebEventListener;

public class Base {

	public static WebDriver driver;
	public static EventFiringWebDriver e_driver;

	WebEventListener eventListener;
	static Properties prop;
	TestUtils utils = new TestUtils();

	public static final String USERNAME = "bdia";
	public static final String ACCESS_KEY = "8a41c01f-c384-450c-95d5-f82863e2d509";
	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY
			+ "@ondemand.eu-central-1.saucelabs.com:443/wd/hub";

	/*
	 * Initialze object Page Factory
	 */

	public Base() {
		PageFactory.initElements(driver, this);
	}

	public void invokeWebDriver() throws Exception {

		InputStream file = null;
		String propertyFileName = "config.properties";

		try {
			file = getClass().getClassLoader().getResourceAsStream(propertyFileName);

			prop = new Properties();
			prop.load(file);
			// Get property from dta.properties
			String browserName = prop.getProperty("browser");

			// Get data from maven commande using System.property
			// mvn test -Dbrowser=chrome

			// String browserName = System.getProperty("browser");

			if (browserName.equalsIgnoreCase("chrome")) {
				// Uncomment this line to run test case on local

				String path = System.getProperty("user.dir") + "/src/main/resources/chromedriver";
				System.setProperty("webdriver.chrome.driver", path);
				driver = new ChromeDriver();

				// Uncomment this lines to Run TEST ON SOUCE LABS CLOUD
				/*
				 * MutableCapabilities sauceOptions = new MutableCapabilities();
				 * 
				 * ChromeOptions browserOptions = new ChromeOptions();
				 * browserOptions.setExperimentalOption("w3c", true);
				 * browserOptions.setCapability("platformName", "macOS 10.15");
				 * browserOptions.setCapability("browserVersion", "72.0");
				 * browserOptions.setCapability("sauce:options", sauceOptions);
				 * 
				 * driver = new RemoteWebDriver(new java.net.URL(URL), browserOptions);
				 */
			} else if (browserName.equalsIgnoreCase("firefox")) {

				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/src/main/resources/geckodriver");
				driver = new FirefoxDriver();

			} else if (browserName.equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "/src/main/resources/IEDriverServer.exe");
				driver = new InternetExplorerDriver();

			}

			/*
			 * Printing on console all webdriver event
			 * 
			 * e_driver = new EventFiringWebDriver(driver); eventListener = new
			 * WebEventListener(); e_driver.register(eventListener);
			 * 
			 * driver = e_driver;
			 */

			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(TestUtils.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(TestUtils.WAIT, TimeUnit.SECONDS);

			driver.get(prop.getProperty("url"));

		} catch (Exception e) {

			System.out.println("Driver initialization ABORT !!!!!" + "\n");
			throw e;

		} finally {
			if (file != null) {
				file.close();
			}
		}

	}

	/**
	 * 
	 * override some methods to use log4j2 and extentReport
	 * 
	 */

	public void sendKeys(WebElement e, String keysToSend, String msg) {

		System.out.println(msg);
		e.sendKeys(keysToSend);

	}

	public void click(WebElement e, String msg) {

		System.out.println(msg);
		e.click();

	}

	public String getText(WebElement e, String msg) {

		System.out.println(msg);
		return e.getText();

	}

	/*
	 * Getter driver Method
	 */

	public WebDriver getDriver() {

		return driver;
	}

}
