package com.globus.invoiceGeneration.index;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.globus.init.SeleniumInit;

public class InvoiceGenerationIndex extends SeleniumInit {
	
	@Parameters({"userName","password","moduleName","leftPanelLink"})
	@Test
	public void generateRegularInvoice(String userName, String password, String moduleName, String leftPanelLink) {
		
		int numOfFailure = 0;
		
		log("Sign in with valid credentials.");
		signInIndexPageVerification = signInIndexPage.signIn(userName,password);
		
		invoiceGenerationIndexPageVerification = invoiceGenerationIndexPage.selectLeftPanelModule(moduleName,leftPanelLink);
		
		log("Select first customer PO.");
		invoiceGenerationIndexPageVerification = invoiceGenerationIndexPage.clickPOAndGenerateInvoice(moduleName,leftPanelLink);
		
		log("Verify customer PO is not available.");
		if (invoiceGenerationIndexPageVerification.isPoAvailable()) {
			log("<Strong><font color=#008000>Pass</font></strong>");
		} else {
			log("Fail");
			numOfFailure++;
		}

		if (numOfFailure > 0) {
			Assert.assertTrue(false);
		}
	}

	@Parameters({"userName","password","moduleName","leftPanelLink"})
	@Test
	public void voidGeneratedInvoice(String userName, String password, String moduleName, String leftPanelLink) throws InterruptedException {
		
		int numOfFailure = 0;
		
		log("Sign in with valid credentials.");
		signInIndexPageVerification = signInIndexPage.signIn(userName,password);
		
		invoiceGenerationIndexPageVerification = invoiceGenerationIndexPage.selectLeftPanelModule(moduleName,leftPanelLink);
		
		invoiceGenerationIndexPageVerification = invoiceGenerationIndexPage.voidInvoice(leftPanelLink);
		
		log("Verify customer PO is not available.");
		if (invoiceGenerationIndexPageVerification.isPoAvailable()) {
			log("<Strong><font color=#008000>Pass</font></strong>");
		} else {
			log("Fail");
			numOfFailure++;
		}

		if (numOfFailure > 0) {
			Assert.assertTrue(false);
		}
	}
}
