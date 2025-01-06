package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import listeners.ExtentListeners;
import utilities.ExcelReader;

public class BaseTest {

	/*
	 * WebDriver - Selenium only helps us to automate web applications on browsers
	 * TestNG - Framework - 
	 * Excel
	 * Log4j
	 * Properties
	 * ExtentReports
	 * Screenshots
	 * Implicit Waits and Explicit waits
	 * Keywords  - type, click 
	 * Listeners
	 * 
	 * 
	 * 
	 */
	
	public static WebDriver driver;
	public static ExcelReader excel = new ExcelReader("./src/test/resources/excel/testdata.xlsx", 0);
	public static Properties OR = new Properties();
	public static Properties config = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger(BaseTest.class);
	public static WebDriverWait wait;
	
	
	@BeforeMethod
	public static void setUp()
	{
		
		if(driver==null)
		{
			PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");
			log.info("Test execution started");
			try {
				fis = new FileInputStream("./src/test/resources/properties/config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fis = new FileInputStream("./src/test/resources/properties/OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(config.getProperty("browser").equals("chrome"))
					{
					driver = new ChromeDriver();
					log.info("Chrome Browser Launch");
					}
			else 
				if(config.getProperty("browser").equals("firefox"))
				{
				driver = new FirefoxDriver();
				log.info("Firefox Browser Launch");
				}
			
			driver.get(config.getProperty("testsiteurl"));
			log.info("Navigated to "+config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicitwait"))));
			
			wait = new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(config.getProperty("explicitwait"))));
		}
	
		}
	public static void type(String locatorKey,String value)
	{
		try {
			if(locatorKey.endsWith("_XPATH"))
			{
				driver.findElement(By.xpath(OR.getProperty(locatorKey))).sendKeys(value);
			}
			else if(locatorKey.endsWith("_ID"))
			{
				driver.findElement(By.id(OR.getProperty(locatorKey))).sendKeys(value);
			}
			else if(locatorKey.endsWith("_NAME"))
			{
				driver.findElement(By.name(OR.getProperty(locatorKey))).sendKeys(value);
			}
			else if(locatorKey.endsWith("_CSS"))
			{
				driver.findElement(By.cssSelector(OR.getProperty(locatorKey))).sendKeys(value);
			}
			log.info("Typing in " + locatorKey + "and entered value as "+value);
			ExtentListeners.test.info("Typing in " + locatorKey + "and entered value as "+value);			
		}
		catch(Throwable t)
		{
			log.error(t.getMessage());
			log.error("Error while typing in an element "+locatorKey);
			ExtentListeners.test.fail("Typing in " + locatorKey + "and entered value as "+value);	
		}
	}
	
	public static void click(String locatorKey)
	{
		try {
			if(locatorKey.endsWith("_XPATH"))
			{
				driver.findElement(By.xpath(OR.getProperty(locatorKey))).click();
			}
			else if(locatorKey.endsWith("_ID"))
			{
				driver.findElement(By.id(OR.getProperty(locatorKey))).click();
			}
			else if(locatorKey.endsWith("_NAME"))
			{
				driver.findElement(By.name(OR.getProperty(locatorKey))).click();
			}
			else if(locatorKey.endsWith("_CSS"))
			{
				driver.findElement(By.cssSelector(OR.getProperty(locatorKey))).click();
			}
			log.info("Clicking on " + locatorKey );
			ExtentListeners.test.info("Clicking on " + locatorKey);			
		}
		catch(Throwable t)
		{
			log.error(t.getMessage());
			log.error("Error while clicking on an element "+locatorKey);
			ExtentListeners.test.fail("Error while clicking on an element "+locatorKey);
			Assert.fail(t.getMessage());
		}
	}
	public static void quit() {
		driver.quit();
		log.info("Browser is closed and test execution complete ");
		ExtentListeners.test.info("Browser is closed and test execution complete ");
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
		log.info("Test execution ends");
	}
	
}
