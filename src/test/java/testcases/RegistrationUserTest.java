package testcases;

import org.testng.annotations.Test;

import utilities.DataUtil;

public class RegistrationUserTest {

	@Test(dataProviderClass = DataUtil.class,dataProvider="xy")
	public void registerUser(String firstName,String lastName)
	{
		//open browser
		//enter url
		
		System.out.println(firstName+"---"+lastName);
	}
}
