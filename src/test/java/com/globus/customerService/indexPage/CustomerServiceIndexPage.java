package com.globus.customerService.indexPage;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.globus.customerService.indexPageVerification.CustomerServiceIndexPageVerification;
import com.globus.init.AbstractPage;
import com.globus.init.Common;

public class CustomerServiceIndexPage extends AbstractPage{

	public CustomerServiceIndexPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy (id="Txt_AccId")
	WebElement Txt_AccId;
	
	@FindBy (id="searchCbo_BillTo")
	WebElement cbo_Acc_Search;
	
	public CustomerServiceIndexPageVerification getAccDetails()
	{
		//Common.waitForPageLoaded(driver);
		//Common.waitForElement(driver,"*//*[@id='RightFrame']");
		driver.switchTo().defaultContent();
		driver.switchTo().frame("rightFrame");
		
		cbo_Acc_Search.sendKeys("acc");
		Common.pause(1);
		driver.findElement(By.xpath(".//ul[@id='ui-id-1']/li/a")).click();
		Common.pause(1);
		cbo_Acc_Search.sendKeys(Keys.TAB);
		Common.pause(5);
		
		String txt_AccId= Txt_AccId.getText();
		System.out.println("txt_AccId === "+txt_AccId);
		/*
		Select accNameList = new Select(driver.findElement(By.id("searchCbo_BillTo")));
		String accNameValue = accNameList.getFirstSelectedOption().getText();
		*/
		globalMap.put("Acc_Id", txt_AccId);
		//globalMap.put("Acc_Name", accNameValue);
		//Common.writeDataProperties("testData", "Txt_AccId", txt_AccId);
		//Common.writeDataProperties("testData", "Acc_Name", accNameValue);
		
		Common.pause(1);
		
		return new CustomerServiceIndexPageVerification(driver);
	}
	
	@FindBy(id="Txt_SurgDate")
	WebElement surgDate;
	
	@FindBy (xpath=".//*[@id='Txt_OrdId']")
	WebElement Txt_OrdId;
	
	@FindBy (xpath=".//img[@src='/images/success.gif']")
	WebElement successImg;
	
	@FindBy (id="Cbo_Mode")
	WebElement cbo_mode;
	
	@FindBy (xpath=".//input[@name='Txt_PNum0']")
	WebElement txt_PNum0;
	
	@FindBy (xpath=".//input[@name='Txt_Qty0']")
	WebElement txt_Qty0;
	
	@FindBy (xpath=".//textarea[@name='Txt_LogReason']")
	WebElement txt_logReason;
	
	@FindBy (name="Btn_PlaceOrd")
	WebElement btn_placeOrd;
	
	@FindBy (xpath="//input[@name='Btn_Close']")
	WebElement btn_close;
	
	@FindBy (name="Btn_ResetForm")
	WebElement btn_resetForm;
	
	public CustomerServiceIndexPageVerification fillPlaceOrderInfo() throws InterruptedException
	{
		//driver.switchTo().defaultContent();
		//driver.switchTo().frame("rightFrame");
		
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		sdf.format(d);
		//surgDate.sendKeys("10/30/2018"+Keys.TAB);
		surgDate.sendKeys(sdf.format(d)+Keys.TAB);
		Common.pause(1);
		
		String txt_OrdId= Txt_OrdId.getAttribute("value")+Common.randomNumericValueGenerate(999);
		
		Txt_OrdId.clear();
		Common.pause(1);
		Txt_OrdId.sendKeys(txt_OrdId+Keys.TAB);
		Common.selectFromComboByVisibleText(cbo_mode, "Phone");
		Common.pause(2);
		driver.switchTo().frame("fmCart");
		txt_PNum0.sendKeys("124.000");
		Common.pause(2);
		txt_Qty0.sendKeys("2"+Keys.TAB);
		Common.pause(2);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("rightFrame");
		txt_logReason.sendKeys("test");
		
		Common.clickOn(driver, btn_placeOrd);
		Common.pause(5);
		
///////////////////////////////////////////////////////////////////////////////////////////////		
		/*
		String ParentWindow=driver.getWindowHandle();
		System.err.println("Parent Window: " +ParentWindow);
		
		Set<String> winSize=driver.getWindowHandles();
		System.out.println("winSize -- "+winSize.size());
		
		Iterator<String> iterator=winSize.iterator();
		
		String subwindow=null;
		while(iterator.hasNext())
		{
			subwindow=iterator.next();
		}
		System.out.println("subwindow== "+subwindow);
		
		driver.switchTo().window(subwindow);
		//((JavascriptExecutor) driver).executeScript("window.focus();");

		Common.pause(3);
		System.out.println("Get Title== "+driver.getTitle());
		System.out.println("Get URL== "+driver.getCurrentUrl());
		
		Common.pause(3);
		Common.highlightElement(driver, btn_close);
		Common.jsClick(driver, btn_close);
		Common.pause(5);
		driver.switchTo().window(ParentWindow);
		*/
		
		/*
		Set<String> windows = driver.getWindowHandles();
		String firstWindow = null;
		String handleSecondWindow = null;
		int firstWindowCount=1;
			  for (String Windowa : driver.getWindowHandles()) {
				  if(firstWindowCount==1) {
					  firstWindow = driver.getWindowHandle();
					  System.out.println("first Windows : "+firstWindow);
					  firstWindowCount++;
				  }
				  System.out.println("Windowa== : "+Windowa);
				  try{
				 
			   driver.switchTo().window(Windowa);
				  }catch(Exception e) {
					  System.out.println(e.getLocalizedMessage());
					  continue;
				  }
			   System.out.println("Size of Windows : "+Windowa);

			  }
			  System.out.println("Driver switched");
			  driver.findElement(By.xpath("//input[@name='Btn_Close']")).click();
			  
			  driver.switchTo().window(firstWindow);
			  
			  Common.clickOn(driver, btn_resetForm);
				Common.pause(5);
		*/
		
		/*
		
		String currentWindowHandle = driver.getWindowHandle();
		System.err.println("INNNNNNNNNNNNNNNNNNN");
		Switch_Window(currentWindowHandle);
		//driver.switchTo().window(currentWindowHandle);
		System.err.println("Window sixe is:"+driver.getWindowHandles().size());
		try{
		   
		if (isAlertPresent(driver)) {
			WebDriverWait wait = new WebDriverWait(driver, 5);
		    wait.until(ExpectedConditions.alertIsPresent());
		    Alert alert = driver.switchTo().alert();
		    alert.dismiss();
		  }
		   
		  }
		  catch(Exception e)
		  {
		   System.out.println("There is no alert popup.");
		  }
		  Thread.sleep(5000);
		  */
		  //driver.get("http://aamsdev02.cloudapp.net:8082/ProduceAD");
		
		System.out.println("txt_OrdId=== "+txt_OrdId);
		
		globalMap.put("Surg_Date", sdf.format(d));
		globalMap.put("Do_ID", txt_OrdId);
		//Common.writeDataProperties("testData", "Surg_Date", sdf.format(d));
		//Common.writeDataProperties("testData", "Do_ID", txt_OrdId);
		//Common.writeDataProperties("testData", "Surg_Date", sdf.format(d));
		
		return new CustomerServiceIndexPageVerification(driver);
	}
	public void Switch_Window(String parentWindow) {

		   // if (SeleniumInit.targetBrowser.contains("Android") || SeleniumInit.targetBrowser.contains("saucelabiphone")) {
		     //ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		     ///driver.switchTo().window(tabs2.get(1));
		     
		     for (String popUpHandle : driver.getWindowHandles()) {
		      if (!popUpHandle.equals(parentWindow)) {
		       driver.switchTo().window(popUpHandle);

		      }
		     }
	}
	
	 public static boolean isAlertPresent(WebDriver driver)
	 {
	  try {

	   Thread.sleep(2000);

	   driver.switchTo().alert();
	   return true;
	  }// try
	  catch (Exception e) {
	   return false;
	  }// catch
	 }
	
}
