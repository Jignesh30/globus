package com.globus.signInIndex;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.globus.init.SeleniumInit;

public class SignInIndex extends SeleniumInit {
	
	@Parameters({"userName","password"})
	@Test
	public void signIn_with_ValidCredential(String userName, String password) {
		
		int numOfFailure = 0;
		int stepCount = 0;
		
		log("Sign in with valid credentials.");
		signInIndexPageVerification = signInIndexPage.signIn(userName,password);
		
	}

}
