package com.globus.invoiceGeneration.indexPage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.globus.init.AbstractPage;
import com.globus.init.Common;
import com.globus.invoiceGeneration.indexPageVerification.InvoiceGenerationIndexPageVerification;
import com.globus.signInIndexPageVerification.SignInIndexPageVerification;

public class InvoiceGenerationIndexPage extends AbstractPage{
	
	public InvoiceGenerationIndexPage(WebDriver driver) {
		super(driver);	  
		    
	}
	
	public static String invoiceNo;
	public static String custPO;
	
	@FindBy(xpath = "*//*[@id='LeftFrame']")
	WebElement leftFrame;
	
	@FindBy(xpath = "*//select[@id='userGroup']")
	WebElement leftPanel_module;
	
	@FindBy (xpath=".//td//*[contains(text(),'Home')]")
	WebElement homeLink;
	
	public InvoiceGenerationIndexPageVerification selectLeftPanelModule(String moduleName, String leftPanelLink)
	{
		
		driver.switchTo().defaultContent();
		Common.waitForElement(driver,"*//*[@id='LeftFrame']");
		driver.switchTo().frame("leftFrame");
		log("Select " +moduleName+ " module.");  
		Common.selectFromComboByVisibleText(leftPanel_module, moduleName);
		Common.pause(2);
		log("Click on " +leftPanelLink+ " link.");
		WebElement leftLink = driver.findElement(By.xpath(".//td//*[contains(text(),'"+leftPanelLink+"')]"));
		Common.clickOn(driver, leftLink);
		return new InvoiceGenerationIndexPageVerification(driver);
	}
	
	@FindBy(xpath = "*//*[@id='RightFrame']")
	WebElement rightFrame;
	
	//@FindBy (xpath="//tr[@class='odd' or @class='even']/td[contains(text(),'Shipped')]/../td[1]/a")
	@FindBy (xpath="//tr[@class='odd' or @class='even']/td[1]/a[contains(text(),'MAT')]")
	List<WebElement> custPOList;
	
	@FindBy (id="Cbo_userBatch")
	WebElement cmbChooseAction;
	
	@FindBy (xpath=".//input[@name='Btn_Submit']")
	WebElement btnSubmit;
	
	@FindBy (xpath=".//tr[@class='ShadeRightTableCaption'][2]/..//tr[7]/td")
	WebElement invoiceNoTxt;
	
	
	public InvoiceGenerationIndexPageVerification clickPOAndGenerateInvoice(String moduleName,String leftPanelLink)
	{
		driver.switchTo().defaultContent();
		
		driver.switchTo().frame("rightFrame");
		
		log("Click on first customer PO - "+custPOList.get(0).getText());
		custPO = custPOList.get(0).getText();
		Common.jsClick(driver, custPOList.get(0));
		Common.pause(2);
		
		Common.waitForElement(driver, "//*[@id='Cbo_userBatch']");
		
		log("Select 'Generate Invoice' from Choose Action dropdown.");
		Common.selectFromComboByVisibleText(cmbChooseAction, "Generate Invoice");
		Common.pause(2);
		
		log("Click 'Submit' button.");
		Common.clickOn(driver, btnSubmit);
		Common.waitForElement(driver, ".//input[@name='Btn_Print']");
		log("Invoice Generated.");
		invoiceNo=invoiceNoTxt.getText();
		log("Invoice Number : "+invoiceNo);
		Common.writeDataProperties("testData", "Invoice_No", invoiceNo);
		Common.pause(2);
		
		selectLeftPanelModule(moduleName,leftPanelLink);
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame(rightFrame);
		Common.waitForElement(driver, ".//*[name='Btn_Load']");
		
		return new InvoiceGenerationIndexPageVerification(driver);
	}
	
	@FindBy (xpath=".//td//*[@name='Txt_InvoiceID']")
	WebElement invoiceID;
	
	@FindBy (xpath=".//table[@class='DtTable900']/tbody/tr[3]/td[2]/input")
	WebElement btnLoad;
	
	@FindBy (xpath=".//select[@name='Cbo_Action1']")
	WebElement modifyCboChooseAction;
	
	@FindBy (xpath=".//select[@id='Cbo_Reason']/option")
	List<WebElement> CboVoidReasonList;
	
	@FindBy (xpath=".//select[@id='Cbo_Reason']")
	WebElement CboVoidReason;
	
	@FindBy (xpath=".//input[contains(@value,'Submit')]")
	WebElement modifyBtnSubmit;
	
	@FindBy (xpath=".//textarea[@name='Txt_Comments']")
	WebElement txt_VoidComments;
	
	public InvoiceGenerationIndexPageVerification voidInvoice(String leftPanelLink) throws InterruptedException
	{
		driver.switchTo().defaultContent();
		
		driver.switchTo().frame("rightFrame");
		String invoiceNo = Common.readDataProperties("testData","Invoice_No").trim();
		log(".//input[@id='"+invoiceNo+"']");
		invoiceID.sendKeys(invoiceNo);
		Common.clickOn(driver, btnLoad);
		Common.pause(3);
		
		Common.waitForElement(driver, ".//input[@id='"+invoiceNo+"']");
		
		driver.findElement(By.xpath(".//input[@id='"+invoiceNo+"']")).click();
		Common.pause(1);
		log("Select 'Void Invoice' from Choose Action dropdown.");
		Common.selectFromComboByVisibleText(modifyCboChooseAction, "Void Invoice");
		Common.pause(2);
		Common.clickOn(driver, modifyBtnSubmit);
		
		Common.waitForElement(driver, ".//td[contains(text(),'Reason')]");
		int cbo_index = Common.randBetween(1, CboVoidReasonList.size()-1);
		Common.selectComboByIndex(CboVoidReason, cbo_index);
		//Common.selectRandomOptionFromCombo(driver, CboVoidReason);
		txt_VoidComments.sendKeys("test");
		Common.clickOn(driver, modifyBtnSubmit);
		return new InvoiceGenerationIndexPageVerification(driver);
	}
	
	
}
