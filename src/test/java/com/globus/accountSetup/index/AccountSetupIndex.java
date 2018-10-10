package com.globus.accountSetup.index;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.globus.accountSetup.indexPageVerification.AccountSetupIndexPageVerification;
import com.globus.init.SeleniumInit;

public class AccountSetupIndex extends SeleniumInit{

	@Parameters({"userName","password","moduleName","leftPanelLink"})
	@Test
	public void accountSetup(String userName, String password, String moduleName, String leftPanelLink) {
		
		int numOfFailure = 0;
		
		log("Sign in with valid credentials.");
		signInIndexPageVerification = signInIndexPage.signIn(userName,password);
		
		invoiceGenerationIndexPageVerification = invoiceGenerationIndexPage.selectLeftPanelModule(moduleName,leftPanelLink);
		
		log("Verify initial fields on account setup page.");
		if (accountSetupIndexPageVerification.verifyInitialFieldsAccountSetup()) {
			log("<Strong><font color=#008000>Pass</font></strong>");
		} else {
			log("Fail");
			numOfFailure++;
		}
		
		log("Insert details for the mandatory fields on account setup page.");
		accountSetupIndexPageVerification = accountSetupIndexPage.newAccountSetup();
		
		log("Verify few fields on Account setup page as account is created.");
		if (accountSetupIndexPageVerification.verifyNewAccountCreated()) {
			log("<Strong><font color=#008000>Pass</font></strong>");
		} else {
			log("Fail");
			numOfFailure++;
		}
		
	}
	
}
