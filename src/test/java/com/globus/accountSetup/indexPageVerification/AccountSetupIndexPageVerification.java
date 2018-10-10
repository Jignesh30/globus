package com.globus.accountSetup.indexPageVerification;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.globus.init.AbstractPage;
import com.globus.init.Common;

public class AccountSetupIndexPageVerification extends AbstractPage{

	public AccountSetupIndexPageVerification(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(name="searchCbo_AccId")
	WebElement rep_acc;
	
	@FindBy (id="Txt_AccId")
	WebElement Txt_AccId;
	
	@FindBy (id="Btn_AddNew")
	WebElement btn_AddNew;
	
	public boolean verifyInitialFieldsAccountSetup()
	{
		boolean bool = false;
		
		//Common.waitForElement(driver,".//form[@name='frmAccount']/table[1]/tbody/tr/td");
		driver.switchTo().defaultContent();
		
		driver.switchTo().frame("rightFrame");
		
		if(rep_acc.getText().isEmpty()
				&& Txt_AccId.getAttribute("value").isEmpty()
				&& btn_AddNew.isEnabled())
			{
				bool=true;
			}else{
				bool=false;
			}
			return bool;
	}
	
	@FindBy (xpath="//*[@id='my_tabbar']/div/div[2]/div[1]/div[1]/iframe")
	WebElement ele_iframe;
	
	@FindBy (id="AssocRepMapping")
	WebElement btn_AssocRepMapping;
	
	@FindBy (name="Btn_ShipSetup")
	WebElement btn_CreditHold;
	
	public boolean verifyNewAccountCreated()
	{
		boolean bool = false;
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame("rightFrame");
		
		String txt_rep_acc = rep_acc.getText();
		String txt_AccId = Txt_AccId.getAttribute("value");
		
		if(!txt_rep_acc.isEmpty() && !txt_AccId.isEmpty())
		{
			bool=true;
		}else{
			bool=false;
		}
		
		driver.switchTo().frame(ele_iframe);
		Common.pause(3);
		
		if(btn_AssocRepMapping.isDisplayed() && btn_CreditHold.isDisplayed())
		{
			bool=true;
		}else{
			bool=false;
		}
		return bool;
	}
}
