package com.globus.signInIndexPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.globus.init.AbstractPage;
import com.globus.init.Common;
import com.globus.init.Configuration;
import com.globus.signInIndexPageVerification.SignInIndexPageVerification;

public class SignInIndexPage extends AbstractPage{

	public SignInIndexPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id = "username")
	WebElement username_field;
	
	@FindBy(id = "password")
	WebElement password_field;
	
	@FindBy(xpath = ".//button[@type='submit']")
	WebElement login_btn;
	
	public SignInIndexPageVerification signIn(String userName, String password)
	{
		//Common.waitForPageLoaded(driver);
		Common.pause(5);
		username_field.sendKeys(userName);
		Common.pause(2);
		password_field.sendKeys(password);
		Common.pause(2);
		Common.clickOn(driver, login_btn);
		Common.waitForPageLoaded(driver);
		
		return new SignInIndexPageVerification(driver);
	}

}
