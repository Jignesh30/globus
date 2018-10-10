package com.globus.invoiceGeneration.indexPageVerification;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.globus.init.AbstractPage;
import com.globus.init.Common;
import com.globus.invoiceGeneration.indexPage.InvoiceGenerationIndexPage;

public class InvoiceGenerationIndexPageVerification extends AbstractPage{

	public InvoiceGenerationIndexPageVerification(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public boolean isPoAvailable()
	{
		String start="//tr[@class='odd' or @class='even']/td[1]/a[text()='";
		String end="/']";
		List<WebElement> custPOList=driver.findElements(By.xpath(start+InvoiceGenerationIndexPage.custPO+end));
	  
		if(custPOList.size()==0){
			log("custPO: "+InvoiceGenerationIndexPage.custPO+" is not available into list.");
			return true;
		}
		return false;
	}

}
