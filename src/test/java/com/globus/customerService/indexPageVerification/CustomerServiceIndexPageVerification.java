package com.globus.customerService.indexPageVerification;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.globus.init.AbstractPage;
import com.globus.init.Common;

public class CustomerServiceIndexPageVerification extends AbstractPage{

	public CustomerServiceIndexPageVerification(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy (id="Txt_AccId")
	WebElement Txt_AccId;
	
	@FindBy (xpath=".//*[@id='Txt_OrdId']")
	WebElement Txt_OrdId;
	
	@FindBy (xpath=".//select[@id='names']")
	WebElement cbo_names;
	
	@FindBy (xpath=".//select[@id='addressid']")
	WebElement cbo_address;
	
	@FindBy(id="Txt_SurgDate")
	WebElement surgDate;
	
	public boolean verifyfieldsAutoPopulate()
	{
		Common.pause(2);
		boolean bool = false;
		String txt_AccId= Txt_AccId.getAttribute("value");
		Common.pause(2);
		Select nameList = new Select(driver.findElement(By.id("names")));
		String selectedNameValue = nameList.getFirstSelectedOption().getText();
		Common.pause(2);
		Select addressList = new Select(driver.findElement(By.id("addressid")));
		String selectedAddressValue = addressList.getFirstSelectedOption().getText();
		
		if(!txt_AccId.isEmpty()
				&& !selectedNameValue.equalsIgnoreCase("[Choose One]")
				&& !selectedAddressValue.isEmpty())
		{
			bool=true;
		}else{
			bool=false;
		}
		return bool;
	}
	
	@FindBy(xpath=".//td[@class='RightDashBoardHeader'][contains(text(),'Delivered Order Summary')]")
	WebElement sum_header;
	
	public boolean verifySalesOrderGeneratedCorrectly()
	{
		//Common.pause(6);
		boolean bool = false;
		Common.waitForElement(driver,".//td[@class='RightDashBoardHeader'][contains(text(),'Delivered Order Summary')]");
		driver.switchTo().defaultContent();
		
		driver.switchTo().frame("rightFrame");
		
		//String del_Ord_Sum = driver.findElement(By.xpath(".//td[@class='RightDashBoardHeader'][contains(text(),'Delivered Order Summary')]")).getText();
		
		String acc_id = globalMap.get("Acc_Id");
		String acc_name = globalMap.get("Acc_Name");
		String surg_date = globalMap.get("Surg_Date");
		String do_id = globalMap.get("Do_ID");
		
		//log("--- acc_id ----"+acc_id);
		//log("--- surg_date ----"+surg_date);
		//log("--- do_id ----"+do_id);
		
		String acc_ID = driver.findElement(By.xpath(".//form[@name='frmOrder']/table[1]/tbody/tr[4]/td/table/tbody/tr[1]/td/table/tbody/tr[7]/td[5][contains(text(),'"+acc_id+"')]")).getText().trim();
		String sum_surg_date = driver.findElement(By.xpath(".//form[@name='frmOrder']/table[1]/tbody/tr[4]/td/table/tbody/tr[1]/td/table/tbody/tr[3]//td[3][contains(text(),'"+surg_date+"')]")).getText().trim();
		String Do_id= driver.findElement(By.xpath(".//form[@name='frmOrder']/table[1]/tbody/tr[4]/td/table/tbody/tr[1]/td/table/tbody/tr[3]//td[5]//td[contains(text(),'"+do_id+"')]")).getText();
		
		if(sum_header.isDisplayed()
			&& sum_surg_date.equalsIgnoreCase(surg_date)
			&& Do_id.equalsIgnoreCase(do_id)
			&& acc_ID.equalsIgnoreCase(acc_id) )
		{
			bool=true;
		}else{
			bool=false;
		}
		return bool;
	}
	

}
