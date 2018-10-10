package com.globus.init;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.internal.Utils;

import com.globus.accountSetup.indexPage.AccountSetupIndexPage;
import com.globus.accountSetup.indexPageVerification.AccountSetupIndexPageVerification;
import com.globus.customerService.indexPage.CustomerServiceIndexPage;
import com.globus.customerService.indexPageVerification.CustomerServiceIndexPageVerification;
import com.globus.invoiceGeneration.indexPage.InvoiceGenerationIndexPage;
import com.globus.invoiceGeneration.indexPageVerification.InvoiceGenerationIndexPageVerification;
import com.globus.signInIndexPage.SignInIndexPage;
import com.globus.signInIndexPageVerification.SignInIndexPageVerification;


public class SeleniumInit {

	
	public static int note_data = 0;
	public static String suiteName = "";
	public static String testName = "";
	public static int stepCount = 1;

	/* Minimum requirement for test configuration */

	public  static String testUrl; // Test url
	protected static String seleniumHost; // Selenium hub IP
	protected static String seleniumHubPort; // Selenium hub port
	protected  static String targetBrowser; // Target browser
	protected static String test_data_folder_path = null;
	protected static String screenshot_folder_path = null; // Path to screenshot folder
	protected static String downloads_folder_path = null;
	public static String currentWindowHandle = "";// Get Current Window handle
	public static String browserName = "";
	public static String osName = "";
	public static String browserVersion = "";
	public static String userDir = System.getProperty("user.dir");
	public static HashMap<String,String> globalMap=new HashMap<String,String>();
	public static ArrayList<String> arrayList=new ArrayList<String>();

	
	public static String currentTest; // current running test

	protected static Logger logger = Logger.getLogger("testing");
	protected WebDriver driver;

	InputStream input = null;
	public static Properties properties = new Properties();


	/* Page's declaration */

	public SignInIndexPage signInIndexPage;
	public SignInIndexPageVerification signInIndexPageVerification;
	
	public  InvoiceGenerationIndexPage invoiceGenerationIndexPage;
	public  InvoiceGenerationIndexPageVerification invoiceGenerationIndexPageVerification;
	
	public  CustomerServiceIndexPage customerServiceIndexPage;
	public  CustomerServiceIndexPageVerification customerServiceIndexPageVerification;
	
	public AccountSetupIndexPage accountSetupIndexPage;
	public AccountSetupIndexPageVerification accountSetupIndexPageVerification;

	/**
	 * Fetches suite-configuration from XML suite file.
	 * 
	 * @param testContext
	 * @throws IOException 
	 */

	@BeforeTest(alwaysRun = true)
	 protected void fetchSuiteConfiguration(ITestContext testContext) throws IOException {

		Configuration.initializeSettings();
		seleniumHost=Configuration.selenium_host;
		seleniumHubPort= Configuration.selenium_port;
		testUrl=Configuration.test_url;
		targetBrowser=Configuration.selenium_browser;
		
		System.out.println("----"+targetBrowser);
	}
	
	
	/**
	 * WebDriver initialization
	 * 
	 * @return WebDriver object
	 * @throws IOException
	 * @throws InterruptedException
	 */

	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, ITestContext testContext) throws IOException, InterruptedException {
		currentTest = method.getName(); // get Name of current test.
		System.out.println("current test --->  " + currentTest);

		URL remote_grid = new URL("http://" + seleniumHost + ":" + seleniumHubPort + "/wd/hub");

		String SCREENSHOT_FOLDER_NAME = "screenshots";
		String TESTDATA_FOLDER_NAME = "test_data";
		String DOWNLOADS_FOLDER_NAME = "downloads";

		test_data_folder_path = new File(TESTDATA_FOLDER_NAME).getAbsolutePath();
		screenshot_folder_path = new File(SCREENSHOT_FOLDER_NAME).getAbsolutePath();
		downloads_folder_path = new File(DOWNLOADS_FOLDER_NAME).getAbsolutePath();
		
		DesiredCapabilities capability =  null;

		if (targetBrowser == null || targetBrowser.contains("ie")) {

			capability = DesiredCapabilities.internetExplorer();
			
			File file = new File(userDir + "//lib//IEDriverServer_3_14_32.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());

			/*capability.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "https://erppreprod.spineit.net/GmLogin.jsp");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			
			capability.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
			capability.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
			capability.setCapability("unexpectedAlertBehaviour", "ignore");
			capability.setCapability("ie.ensureCleanSession", true);
			capability.setCapability("handlesAlerts", true);
			
			capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			capability.setCapability(InternetExplorerDriver.IE_SWITCHES,true);
			capability.setCapability("requireWindowFocus", true);
			capability.setCapability("ignoreProtectedModeSettings", true);
			*/
			
			InternetExplorerOptions options=new InternetExplorerOptions();
			options.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "https://erppreprod.spineit.net/GmLogin.jsp");
			options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			//options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			
			//options.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
			options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
			//options.setCapability("unexpectedAlertBehaviour", "ignore");
			options.setCapability("unexpectedAlertBehaviour", "accept");
			options.setCapability("ignoreProtectedModeSettings", true);
			options.setCapability("enablePersistentHover", true);
			options.setCapability("UnhandledPromptBehavior", "accept");
			//options.setCapability("ie.ensureCleanSession", true);
			options.setCapability("handlesAlerts", true);
			
			options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			options.setCapability(InternetExplorerDriver.IE_SWITCHES,true);
			options.setCapability("requireWindowFocus", true);
			
			
			
			/*
			InternetExplorerOptions ieCapabilities=new InternetExplorerOptions();
			ieCapabilities.setCapability("nativeEvents", false);
			ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
			ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
			ieCapabilities.setCapability("disable-popup-blocking", true);
			ieCapabilities.setCapability("enablePersistentHover", true);
			ieCapabilities.setCapability("ignoreZoomSetting", true);
*/
			browserName = capability.getBrowserName();
			osName = capability.getPlatform().name();
			browserVersion = capability.getVersion();

			driver = new InternetExplorerDriver(options);
			//driver = new RemoteWebDriver(remote_grid, options);

			// this.driver = new InternetExplorerDriver(capability);

			System.out.println("=========" + " Internet Explorer Browser " + "==========");
			
		} else if (targetBrowser.contains("chrome")) {

			capability = DesiredCapabilities.chrome();

			//File file = new File(userDir + "//lib//chromedriver.exe");
			File file = new File(userDir + "//lib//chromedriver-1.exe");
			
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloads_folder_path);
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-infobars");
			options.setExperimentalOption("prefs", chromePrefs);
			
			capability.setJavascriptEnabled(true);
			capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capability.setCapability(ChromeOptions.CAPABILITY, options);
			//options.merge(capability);

			browserName = capability.getBrowserName();
			osName = capability.getPlatform().name();
			browserVersion = capability.getVersion();
			
			driver = new ChromeDriver(options);

			System.out.println("=========" + " Google Chrome Browser " + "==========");

		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//driver.get("https://erppreprod.spineit.net/GmLogin.jsp");

		driver.manage().window().maximize();

		Common.waitForPageLoaded(driver);
		// Common.setConfigDate(driver);

		currentWindowHandle = driver.getWindowHandle();

		System.out.println("Current Window Handle ID:--->" + currentWindowHandle);

		suiteName = testContext.getSuite().getName();
		System.out.println("Current Xml Suite is:---->" + suiteName);

		// Page Object Initialization According To Its Test Suite.
		
		signInIndexPage = new SignInIndexPage(driver);
		signInIndexPageVerification = new SignInIndexPageVerification(driver);
		
		invoiceGenerationIndexPage = new InvoiceGenerationIndexPage(driver);
		invoiceGenerationIndexPageVerification = new InvoiceGenerationIndexPageVerification(driver);
		
		accountSetupIndexPage = new AccountSetupIndexPage(driver);
		accountSetupIndexPageVerification = new AccountSetupIndexPageVerification(driver);

		customerServiceIndexPage = new CustomerServiceIndexPage(driver);
		customerServiceIndexPageVerification = new CustomerServiceIndexPageVerification(driver);

	}

	/**
	 * Log For Failure Test Exception.
	 * 
	 * @param tests
	 */
	private void getShortException(IResultMap tests) {

		for (ITestResult result : tests.getAllResults()) {

			Throwable exception = result.getThrowable();
			List<String> msgs = Reporter.getOutput(result);
			boolean hasReporterOutput = msgs.size() > 0;
			boolean hasThrowable = exception != null;
			if (hasThrowable) {
				boolean wantsMinimalOutput = result.getStatus() == ITestResult.SUCCESS;
				if (hasReporterOutput) {
					log("<b><i>" + (wantsMinimalOutput ? "Expected Exception" : "Failure Reason:") + "</b></i>");
				}

				// Getting first line of the stack trace
				String str = Utils.stackTrace(exception, true)[0];
				@SuppressWarnings("resource")
				Scanner scanner = new Scanner(str);
				String firstLine = scanner.nextLine();
				log("<Strong><font color=#ff0000>" + firstLine + "</font></strong>");
			}
		}
	}

	/**
	 * After Method
	 * 
	 * @param testResult
	 */
	 public static int passed_count = 0;
	    public static int failed_count = 0;
		public static int skipped_count = 0;
    
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult) {
		
		
		ITestContext ex = testResult.getTestContext();

		if(testResult.getStatus() == ITestResult.SUCCESS)
	    {
			passed_count++;
	       
	    }

	    else if(testResult.getStatus() == ITestResult.FAILURE)
	    {
	    	failed_count++;  

	    }

	     else if(testResult.getStatus() == ITestResult.SKIP )
	     {	 
	    	 skipped_count++;

	     }
		try {
			testName = testResult.getName();
			if (!testResult.isSuccess()) {

				Common.saveScreenshot(driver, currentTest);
				// Print test result to Jenkins Console
				System.out.println();
				System.out.println("TEST FAILED - " + testName);
				System.out.println();
				System.out.println("ERROR MESSAGE: " + testResult.getThrowable());
				System.out.println("\n");
				Reporter.setCurrentTestResult(testResult);
				
				Reporter.log("<br></br><Strong><font color=#ff0000>Fail                  </font></strong><img src='"
						+ userDir + "\\report-imgs\\fail.png' alt='fail' height='15' width='15'/>");
				getShortException(ex.getFailedTests());
				Common.saveScreenshot(driver, currentTest);
			} else {

				// Print test result to Jenkins console
				System.out.println("TEST PASSED - " + testName + "\n");
			}
			System.out.println("--------------- Test status : " + testResult.getStatus() + " ---------------");

			stepCount = 1;
			driver.manage().deleteAllCookies();
			driver.close();
			driver.quit();

		} catch (Exception throwable) {
			System.out.println("message from tear down" + throwable);
		} finally {

			if (browserName.contains("internet explorer")) {
				killIEServer();
				Common.pause(5);
			}
		}

	}

	public void analyzeLog() {
		LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
		for (LogEntry entry : logEntries) {
			System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
		}
	}

	/**
	 * This method is killing the IE instance once test is completed. After killing
	 * IE instance it's also clear and kill the IE Server driver.
	 * 
	 */
	public void killIEServer() {
		try {

			Common.pause(5);

			String[] cmd = new String[3];
			cmd[0] = "cmd.exe";
			cmd[1] = "/C";
			cmd[2] = "taskkill /F /IM iexplore.exe";

			Process process = Runtime.getRuntime().exec(cmd);

			Process process1 = Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");

			System.err.println(process + "" + process1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg
	 *            Message/Log to be reported.
	 */

	public static void log1(String msg) {

		System.out.println("======" + msg + "======");
		Reporter.log("<br></br>" + msg);
	}
	
	public static void log(String msg) {

		System.out.println("====== Step "+stepCount+": "+msg+" ======");
		Reporter.log("<br></br>"+"Step "+stepCount+": "+msg);
		stepCount++;
	}
/*
	public void failedLog(String msg)
	{
		log("<strong>" + msg + "</strong>");
	}
	public void testCaselog(String msg) {

		log("<strong>======" + msg + "======</strong>");

	}

	public void testInfoLog(String msg1, String msg2) {

		log("<strong>" + msg1 + " : </strong><font color=#9400D3>" + msg2 + "</font>&nbsp; <img src='" + userDir
				+ "\\report-imgs\\info.png' alt='info' height='15' width='15'/>");
	}

	public void testStepsLog(int logStep, String msg) {

		log("Step " + (logStep++) + " : " + msg);

	}

	public void testVerifyLog(String msg) {

		log("<font color=#000080>" + msg + "</font>");

	}

	public void testValidationLog(String msg) {

		log("Validation Message : <Strong><font color=#ff0000>" + msg + "</strong></font>");

	}

	public void testConfirmationLog(String msg) {

		log("Confirmation Message : <Strong><font color=#008000>" + msg + "</strong></font>");

	}

	public void testWarningLog(String msg) {

		log("Warning Message : <Strong><font color=#FF2070>" + msg + "</strong></font>&nbsp; <img src='" + userDir
				+ "\\report-imgs\\warning.png' alt='info' height='15' width='15'/>");

	}

	*//**
	 * Log success message to Reporter output.
	 * 
	 *//*

	public static void success() {

		System.out.println("<Strong><font color=#008000>Pass</font></strong>");
		Reporter.log("<br></br><Strong><font color=#008000>Pass                  </font></strong><img src='" + userDir
				+ "\\report-imgs\\pass.png' alt='pass' height='15' width='15'/>");
	}

	*//**
	 * Log failure message to Reporter output.
	 * 
	 *//*

	public static void failure() {

		System.out.println("<Strong><font color=#ff0000>Fail</font></strong>");
		Reporter.log("<br></br><Strong><font color=#ff0000>Fail                  </font></strong><img src='" + userDir
				+ "\\report-imgs\\fail.png' alt='fail' height='15' width='15'/>");
	}

	*/
}
