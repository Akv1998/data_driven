package testcases;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestParameterizationXML {

	public WebDriver driver;
	public Capabilities cap;
	
	@Parameters({"url","browser"})
	@Test
	public void doLogin(String u,String b) throws MalformedURLException, InterruptedException
	{
		Date d = new Date();
		System.out.println(u+"------"+b+"----"+d.toString());
		if(b.equals("chrome"))
		{
			cap = new ChromeOptions();
		}else if(b.equals("firefox"))
		{
			cap = new FirefoxOptions();
		}
		
		driver = new RemoteWebDriver(new URL("http://0.0.0.0:4444"),cap);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.get(u);
		driver.manage().window().maximize();
		driver.findElement(By.name("q")).sendKeys("Hello browser: "+b);
		
		Thread.sleep(20000);
		driver.quit();
	}
}
