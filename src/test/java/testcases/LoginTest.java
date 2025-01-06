package testcases;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.DataUtil;

public class LoginTest extends BaseTest {

	@Test(dataProviderClass = DataUtil.class,dataProvider="xy")
	public void doLogin(String userName,String password)
	{
		//driver.findElement(null)
		type("username_XPATH", userName);
		type("password_ID",password);
		click("login_NAME");
	}
	
	
	
}
