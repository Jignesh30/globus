package com.globus.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * Define Common Webdriver
 */
public class Common {

	
	Date date = new Date();
	protected static Wait<WebDriver> wait;
	public static String alerttext;
	
	/**
	 * Find web-element for given locator.
	 * 
	 * @param elementName
	 * @return
	 */
	public static WebElement findElement(WebDriver driver, String elementName) {

		String locator;

		locator = elementName;

		int count = 0;
		while (count < 4) {
			try {
				if (locator.startsWith("link=") || locator.startsWith("LINK=")) {
					locator = locator.substring(5); // remove "link=" from
													// locator
					try {
						if (locator.contains(" "))
							return driver.findElement(By.partialLinkText(locator));

						return driver.findElement(By.linkText(locator));
					} catch (Exception e) {
						return null;
					}
				}
				if (locator.startsWith("id=")) {
					locator = locator.substring(3); // remove "id=" from locator
					try {
						return driver.findElement(By.id(locator));
					} catch (Exception e) {
						return null;
					}
				} else if (locator.startsWith("//")) {
					try {
						return driver.findElement(By.xpath(locator));
					} catch (Exception e) {
						return null;
					}
				} else if (locator.startsWith("css=")) {

					locator = locator.substring(4); // remove "css=" from
													// locator
					try {
						return driver.findElement(By.cssSelector(locator));
					} catch (Exception e) {
						return null;
					}
				} else if (locator.startsWith("name=")) {

					locator = locator.substring(5); // remove "name=" from
													// locator
					try {
						return driver.findElement(By.name(locator));
					} catch (Exception e) {
						return null;
					}
				} else {
					try {
						return driver.findElement(By.id(locator));
					} catch (Exception e) {
						return null;
					}

				}
			} catch (StaleElementReferenceException e) {
				e.toString();

				count = count + 1;
				// System.out.println("Trying["+
				// count+"] to recover from a stale element :" +
				// e.getMessage());
			}
			count = count + 4;
		}
		return null;
	}
	
	public static void scrollToElement(WebDriver driver, WebElement element)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	public static void scrollToHorizontal(WebDriver driver, WebElement element) {

		Actions dragger = new Actions(driver);
		WebElement draggablePartOfScrollbar = element;

		// drag downwards
		int numberOfPixelsToDragTheScrollbarDown = 50;
		for (int i = 10; i < 500; i = i + numberOfPixelsToDragTheScrollbarDown) {
			try {
				// this causes a gradual drag of the scroll bar, 10 units at a
				// time
				dragger.moveToElement(draggablePartOfScrollbar).clickAndHold()
						.moveByOffset(numberOfPixelsToDragTheScrollbarDown, 0).release().perform();
				Thread.sleep(1000L);
			} catch (Exception e1) {

			}
		}
	}

	/**
	 * Perform vertical scrolling
	 * 
	 * @param driver
	 * @param element
	 */
	public static void scrollToVertical(WebDriver driver, WebElement element) {

		Actions dragger = new Actions(driver);
		WebElement draggablePartOfScrollbar = element;

		// drag downwards
		int numberOfPixelsToDragTheScrollbarDown = 50;
		for (int i = 10; i < 500; i = i + numberOfPixelsToDragTheScrollbarDown) {

			System.out.println("1");
			try {
				// this causes a gradual drag of the scroll bar, 10 units at a
				// time
				dragger.moveToElement(draggablePartOfScrollbar).clickAndHold()
						.moveByOffset(0, numberOfPixelsToDragTheScrollbarDown).release().perform();
				Thread.sleep(1000L);
			} catch (Exception e1) {

			}
		}

	}

	public static void enterDataIn(WebDriver driver, WebElement element, String search_phrase) {
		element.sendKeys(search_phrase);
	}
	
	public static void clickOn(WebDriver driver, WebElement element) {
		highlightElement(driver, element);
		Common.pause(1);
		element.click();
	}

	/**
	  * Clicks on visible or not visible element.
	  * 
	  * @param element
	  *            Web element.
	  */

	 public static void jsClick(WebDriver driver, WebElement element) {
	  highlightElement(driver, element);
	  ((JavascriptExecutor) driver).executeScript(
	    "return arguments[0].click();", element);
	  // this.waitForAjax("0");
	 }
	
	public static void highlightElement(WebDriver driver, WebElement element) {
		/*
		 * for (int i = 0; i < 2; i++) { JavascriptExecutor js =
		 * (JavascriptExecutor) driver;
		 * js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
		 * element, "color: yellow; border: 2px solid yellow;");
		 * js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
		 * element, ""); }
		 */

		// draw a border around the found element

		((JavascriptExecutor) driver).executeScript("arguments[0].style.border = '3px solid yellow'", element);

		pause(2);

		((JavascriptExecutor) driver).executeScript("arguments[0].style.border = '0px'", element);
	}

	public static void refreshPage(WebDriver driver)
	{
		driver.navigate().refresh();
	}
	public static void waitForPageLoaded(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 70);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}
/*
	public static boolean visibilityOfElementLocated(WebDriver driver, WebElement element, int timeoutInSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (TimeoutException | NoSuchElementException e) {
			System.out.println(e);
			return false;
		}
	}*/

	/**
	  * Wait up to String locator present
	  * 
	  * @param selector
	  */
	 public static void waitForElement(WebDriver driver, String xpath) {
	  wait = new WebDriverWait(driver, 60);
	  try {
	   wait.until(visibilityOfElementLocated(By.xpath(xpath)));
	  } catch (Exception e) {
	  }
	 }
	
	 /**
	  * Checks whether the visibility of Element Located
	  * 
	  * @param by
	  * @return
	  */
	 public static ExpectedCondition<WebElement> visibilityOfElementLocated(final By by) {
	  return new ExpectedCondition<WebElement>() {

	   public WebElement apply(WebDriver driver) {
	    WebElement element = driver.findElement(by);
	    return element.isDisplayed() ? element : null;
	   }
	  };
	 }
	 
	/**
	 * Pauses for given seconds.
	 * 
	 * @param secs
	 */
	public static void pause(int secs) {
		try {
			Thread.sleep(secs * 1000);
		} catch (InterruptedException interruptedException) {

		}
	}

	/**
	 * Gets current time in the following format Month, Date, Hours, Minutes,
	 * Seconds, Millisecond.
	 * 
	 * @return Current date.
	 */
	public static String getCurrentTimeStampString() {

		java.util.Date date = new java.util.Date();

		SimpleDateFormat sd = new SimpleDateFormat("ddMMHHmmssSS");
		TimeZone timeZone = TimeZone.getDefault();
		Calendar cal = Calendar.getInstance(new SimpleTimeZone(timeZone.getOffset(date.getTime()), "GMT"));
		sd.setCalendar(cal);
		return sd.format(date);
	}

	/**
	 * Takes screenshot and adds it to TestNG report.
	 * 
	 * @param driver
	 *            WebDriver instance.
	 */
	public static void makeScreenshot(WebDriver driver, String screenshotName) {

		WebDriver augmentedDriver = new Augmenter().augment(driver);

		/* Take a screenshot */
		File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		String nameWithExtention = screenshotName + ".png";

		/* Copy screenshot to specific folder */
		try {
			/*
			 * String reportFolder = "target" + File.separator +
			 * "failsafe-reports" + File.separator + "firefox" + File.separator;
			 */
			String reportFolder = "test-output" + File.separator;
			String screenshotsFolder = "screenshots";
			File screenshotFolder = new File(reportFolder + screenshotsFolder);
			if (!screenshotFolder.getAbsoluteFile().exists()) {
				screenshotFolder.mkdir();
			}
			FileUtils.copyFile(screenshot,
					new File(screenshotFolder + File.separator + nameWithExtention).getAbsoluteFile());
		} catch (IOException e) {
			log("Failed to capture screenshot: " + e.getMessage());
		}
		log(getScreenshotLink(nameWithExtention, nameWithExtention)); // add
																		// screenshot
																		// link
																		// to
																		// the
																		// report
	}
	
	public static double roundTo2DecimalPlaces(double value)
	{
		return Math.round(value * 100.0)/100.0;
	}
	
	public static String doubleToStringUpto2DecimalPlaces(double value)
	{
		DecimalFormat format = new DecimalFormat("#0.00");
		return format.format(value);
	}

	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg
	 *            Message/Log to be reported.
	 */
	public static void log(String msg) {

		System.out.println("msg");
		Reporter.log(msg);
	}

	/**
	 * Generates link for TestNG report.
	 * 
	 * @param screenshot_name
	 *            Screenshot name.
	 * @param link_text
	 *            Link text.
	 * @return Formatted link for TestNG report.
	 */
	public static String getScreenshotLink(String screenshot_name, String link_text) {

		log("<br><Strong><font color=#FF0000>--Failed</font></strong>");
		return "<a href='../test-output/screenshots/" + screenshot_name + "' target='_new'>" + link_text + "</a>";
	}

	public static void logStatus(String Status) {
		if (Status.equalsIgnoreCase("Pass")) {
			log("<br><Strong><font color=#008000>Pass</font></strong></br>");
		} else if (Status.equalsIgnoreCase("Fail")) {
			log("<br><Strong><font color=#FF0000>Fail</font></strong></br>");

		}
	}

	public static int randBetween(int start, int end) {
	    return start + (int) Math.round(Math.random() * (end - start));
	   }
	
	public static void selectComboByIndex(WebElement element, int index){
		Select select = new Select(element);
		select.selectByIndex(index);
	}
	
	/**
	 * Get random numeric of given lenth.
	 * 
	 * @param length
	 *            desired length.
	 * @return
	 */
	// will return number between 1 and length (both inclusive)
	public static int randomNumericValueGenerate(int length) {

		Random randomGenerator = new Random();

		int randomInt = randomGenerator.nextInt(length);
		return (randomInt + 1);
	}
	
	public static int[] randomNumericValuesGenerate(int length, int count) {

		Random randomGenerator = new Random();

		int[] randomInts = randomGenerator.ints(1, (length + 1)).distinct().limit(count).toArray();
		return randomInts;
	}

	public static String randomStringGenerate() {

		int length = randomNumericValueGenerate(10);
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder( length );
		   for( int i = 0; i < length; i++ ) 
		      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		   return sb.toString();
		

	}
	
	public static String getUUID()
	{
		return UUID.randomUUID().toString();		
	}
	/**
	 * Select Random String From Combobox.
	 * 
	 * @param by
	 * @param driver
	 * @return selected random string
	 * @throws InterruptedException
	 */
	public static String selectRandomOptionFromCombo(WebDriver driver, WebElement selectCombo) throws InterruptedException {
		String selectedOption = "";
		Thread.sleep(2);
		List<WebElement> getAllOption = selectCombo.findElements(By.xpath("option"));
		ArrayList<String> arrayOfAllOption = new ArrayList<String>();
		for (WebElement ele : getAllOption) {
			if (!ele.getText().startsWith("All")) {
				arrayOfAllOption.add(ele.getText());
			}

		}
		int index = new Random().nextInt(arrayOfAllOption.size());
		if (Integer.signum(index) == -1) {
			index = -index;
			// index=Math.abs(index);
		}
		selectedOption = arrayOfAllOption.get(index);
		System.out.println("Selected Option Is---->" + selectedOption);
		return selectedOption;
	}

	/**
	 * Select data form dropdown or combobox by visible element
	 * 
	 * @param element
	 * @param value
	 */
	public static void selectFromComboByVisibleElement(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByVisibleText(value);
	}
	
	/**
	  * Select data form dropdown or combobox by visible element
	  * 
	  * @param element
	  * @param value
	  */
	 public static void selectFromComboByVisibleText(WebElement element, String value) {
	  Select select = new Select(element);
	  select.selectByVisibleText(value);
	  
	 }
	
	public static String getRandomColorCode()
	{
		Random random = new Random();
        int nextInt = random.nextInt(256*256*256);
        String colorCode = String.format("#%06x", nextInt);
        return colorCode;
	}

	public static int getNumberFromString(String input_string) {
		input_string = input_string.replaceAll("[^0-9]", "");
		return Integer.parseInt(input_string);
	}

	public static double getDoubleFromString(String input_string)
	{
		input_string = input_string.replaceAll("[^0-9.]", "");
		return Double.parseDouble(input_string);
	}
	
	public static void saveScreenshot(WebDriver driver, String testName)
	{
		String screenshotName = Common.getCurrentTimeStampString() + testName;
		Reporter.log("<br> <b>Please look to the screenshot - </b>");
		Common.makeScreenshot(driver, screenshotName);
	}
	
	public static String readDataProperties(String fileName,String propertieName)
	  {
	     String result="";
	     File file = new File(fileName+".properties");
	     FileInputStream fileInput = null;
	     try {
	      fileInput = new FileInputStream(file);
	     } catch (FileNotFoundException e) {
	      e.printStackTrace();
	     }
	     Properties prop = new Properties();
	     try {
	       prop.load(fileInput);
	       result = prop.getProperty(propertieName);
	       System.out.println(result);
	     } catch (Exception e) {
	      System.out.println("Exception: " + e);
	     } finally {
	     }
	     
	     return result;
	  }
	  
	  public static void writeDataProperties(String fileName,String propertieName,String propertieValue)
	  {
	   Properties propwrite = new Properties();
	      
	       try {
	       
	        propwrite.setProperty(propertieName, propertieValue);
	   
	          propwrite.store(new FileOutputStream(fileName+".properties",false),"");
	         } catch (IOException ex) {
	        ex.printStackTrace();
	          }
	  }
	
	  public static boolean isAlertPresent(WebDriver driver)
		{
			try {

				Common.pause(1);
				System.out.println("1. alret is present.");
				
				return true;
			}// try
			catch (Exception e) {
				return false;
			}// catch
		}
	  
		/**
		 * Finds handle to second window other than given handle to current window
		 * and switches to as well.
		 * 
		 * @param handleCurrentWindow
		 * @return handleSecondWindow
		 * @throws InterruptedException 
		 */
		public static String findAndSwitchToSecondWindow(WebDriver driver,String handleCurrentWindow) throws InterruptedException {

			//pause(1);
			Set<String> windows = driver.getWindowHandles();
			String handleSecondWindow = null;
			for (String window : windows) {
				if (!window.contains(handleCurrentWindow)) {
					handleSecondWindow = window;
				}
			}

			// Switch to the second window.
			try {

				Thread.sleep(1000);

				driver.switchTo().window(handleSecondWindow);

			} catch (Throwable failure) { // If there is problem in switching
				// window, then re-try.

				Thread.sleep(1000);

				driver.switchTo().window(handleSecondWindow);

			}

			return handleSecondWindow;

		}
		
		public static void SwitchToLastOpenWindow(WebDriver driver, String WindowHandle)
		 {
		  for (String Windowa : driver.getWindowHandles()) {
		   driver.switchTo().window(Windowa);
		   System.out.println("Size of Windows : "+Windowa);

		  }
		 }
		
		public static void SwitchtoTab(WebDriver driver,int tabNumber)
		  {
		   ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		   System.out.println("Size of Windows : "+tabs.size());
		   driver.switchTo().window(tabs.get(tabNumber));
		  }
		
}
