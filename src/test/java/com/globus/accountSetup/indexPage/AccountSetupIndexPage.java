package com.globus.accountSetup.indexPage;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.globus.accountSetup.indexPageVerification.AccountSetupIndexPageVerification;
import com.globus.init.AbstractPage;
import com.globus.init.Common;

public class AccountSetupIndexPage extends AbstractPage{

	public AccountSetupIndexPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy (id="Btn_AddNew")
	WebElement btn_AddNew;
	
	@FindBy (xpath="//*[@id='my_tabbar']/div/div[2]/div[1]/div[1]/iframe")
	WebElement ele_iframe;
	
	
	@FindBy (xpath="//input[@name='Txt_Rep_AccNm']/../../../../../../../../../../../..")
	WebElement ele_document;
	
	@FindBy (xpath="//input[@name='Txt_Rep_AccNm']/../../../../../../../../../..")
	WebElement ele_form;
	
	@FindBy (xpath="//table[@id='tblAcctInfo']/../../../../..")
	WebElement repInfo_table;
	
	@FindBy (xpath="(//*[@id='tblAcctInfo']/tbody/tr[1]/td[2]/input)")
	WebElement txt_Rep_AccNm;
	
	@FindBy (id="Cbo_CompId")
	WebElement cbo_CompId;
	
	@FindBy (id="searchCbo_DistId")
	WebElement search_Cbo_DistId;
	
	@FindBy (name="Cbo_RepId")
	WebElement cbo_RepId;
	
	@FindBy (id="Txt_IncDate")
	WebElement txt_IncDate;
	
	@FindBy (id="Cbo_Currency")
	WebElement cbo_Currency;
	
	@FindBy (id="Cbo_AccType")
	WebElement cbo_AccType;
	
	@FindBy (name="Txt_Parent_AccNm")
	WebElement txt_Parent_AccNm;
	
	@FindBy (name="Txt_ContPer")
	WebElement txt_ContPer;
	
	@FindBy (name="Txt_BillName")
	WebElement txt_BillName;
	
	@FindBy (name="Txt_BillAdd1")
	WebElement txt_BillAdd1;
	
	@FindBy (name="Txt_BillCity")
	WebElement txt_BillCity;
	
	@FindBy (id="Cbo_BillState")
	WebElement cbo_BillState;
	
	@FindBy (name="Txt_BillZipCode")
	WebElement txt_BillZipCode;
	
	@FindBy (name="Txt_ShipName")
	WebElement txt_ShipName;
	
	@FindBy (name="Txt_ShipAdd1")
	WebElement txt_ShipAdd1;
	
	@FindBy (name="Txt_ShipCity")
	WebElement txt_ShipCity;
	
	@FindBy (id="Cbo_ShipState")
	WebElement cbo_ShipState;
	
	@FindBy (name="Txt_ShipZipCode")
	WebElement txt_ShipZipCode;
	
	@FindBy (id="comments")
	WebElement txt_comments;
	
	@FindBy (name="Btn_Submit")
	WebElement btn_Submit;
	
	public AccountSetupIndexPageVerification newAccountSetup()
	{
		
		//driver.switchTo().defaultContent();
		//driver.switchTo().frame("rightFrame");
		
		Common.clickOn(driver, btn_AddNew);
		Common.pause(5);
		
		//Common.highlightElement(driver, ele_iframe);
		driver.switchTo().frame(ele_iframe);
		Common.pause(5);
		
		txt_Rep_AccNm.sendKeys("test_Rep_Ac"+Common.randomNumericValueGenerate(999)+Keys.TAB);
		
		Common.selectFromComboByVisibleText(cbo_CompId, "Globus Medical Inc");
		
		search_Cbo_DistId.sendKeys("Micah Schmitt");
		Common.pause(1);
		driver.findElement(By.xpath(".//ul[@id='ui-id-1']/li/a")).click();
		Common.pause(3);
		search_Cbo_DistId.sendKeys(Keys.TAB);
		Common.pause(5);
		
		Common.selectFromComboByVisibleText(cbo_RepId, "Micah Schmitt");
		
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		sdf.format(d);
		txt_IncDate.sendKeys(sdf.format(d)+Keys.TAB);
		
		Common.selectFromComboByVisibleText(cbo_Currency, "$");
		
		Common.selectFromComboByVisibleText(cbo_AccType, "Hospital-Company");
		
		txt_Parent_AccNm.sendKeys("jigs_Rep_Acc"+Common.randomNumericValueGenerate(999)+Keys.TAB);
		
		txt_ContPer.sendKeys("Jignesh"+Keys.TAB);
		
		txt_BillName.sendKeys("Jignesh"+Keys.TAB);
		
		txt_BillAdd1.sendKeys("Jignesh"+Keys.TAB);
		
		txt_BillCity.sendKeys("Calera"+Keys.TAB);
		
		Common.selectFromComboByVisibleText(cbo_BillState, "Alabama");
		
		txt_BillZipCode.sendKeys("35010"+Keys.TAB);
		
		txt_ShipName.sendKeys("Jignesh"+Keys.TAB);
		
		txt_ShipAdd1.sendKeys("Jignesh"+Keys.TAB);
		
		txt_ShipCity.sendKeys("Calera"+Keys.TAB);
		
		Common.selectFromComboByVisibleText(cbo_ShipState, "Alabama");
		
		txt_ShipZipCode.sendKeys("35010"+Keys.TAB);
		
		txt_comments.sendKeys("This is test account."+Keys.TAB);
		
		Common.clickOn(driver, btn_Submit);
		
		Common.pause(3);
		driver.switchTo().alert().accept();
		Common.pause(5);
		
		
		return new AccountSetupIndexPageVerification(driver);
	}

}
