package com.globus.customerService.index;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.globus.init.SeleniumInit;

public class CustomerServiceIndex extends SeleniumInit{
	
	@Parameters({"userName","password","moduleName","leftPanelLink"})
	@Test
	public void processNewSalesOrder(String userName, String password, String moduleName, String leftPanelLink) throws InterruptedException {
		
		int numOfFailure = 0;
		
		log("Sign in with valid credentials.");
		signInIndexPageVerification = signInIndexPage.signIn(userName,password);
		
		log("Click Account Setup from sales Admin.");
		invoiceGenerationIndexPageVerification = invoiceGenerationIndexPage.selectLeftPanelModule(moduleName,leftPanelLink);
		
		log("Get the details related to account.");
		customerServiceIndexPageVerification = customerServiceIndexPage.getAccDetails();
		/*
		log("Verify fields are auto populate on sales order page.");
		if (customerServiceIndexPageVerification.verifyfieldsAutoPopulate()) {
			log("<Strong><font color=#008000>Pass</font></strong>");
		} else {
			log("Fail");
			numOfFailure++;
		}
		*/
		log("Insert mandatory fields on new sales order page.");
		customerServiceIndexPageVerification = customerServiceIndexPage.fillPlaceOrderInfo();
		/*
		if (customerServiceIndexPageVerification.verifySalesOrderGeneratedCorrectly()) {
			log("<Strong><font color=#008000>Pass</font></strong>");
		} else {
			log("Fail");
			numOfFailure++;
		}
		*/
		if (numOfFailure > 0) {
			Assert.assertTrue(false);
		}
		
	}

}
