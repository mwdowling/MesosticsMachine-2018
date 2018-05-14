package mesosticSystemOOP;

/**
 * @author Martin Dowling 
 * 
 * Information for writing this class taken from: 
 * http://toolsqa.com/selenium-webdriver/webelement-commands/
 * 
 * This class permits the creation of an object that: 
 * (1) takes a word from target text produced by the method Word.Item
 * (2) searches the web site HowManySyllables.com to find the syllabic division of the word
 * (3) identifies and returns the appropriate syllable in the word
 * (4) if the web site does not provide a syllable division, 
 * 	   saves the whole word and prints a warning message
 * 
 * This functionality is unstable,
 * subject to unpredictable changes to systems outside the user's control, 
 * including the target website, and updated versions of selenium, webdrivers, and browsers 
 * In February 2018, Chrome worked better than Firefox
 * 
 */

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public final class Syllable {

	public Syllable() {		
	}

	// a method to return a syllable saved from www.howmanysyllables.com
	public final String Saved(String word) throws InterruptedException, IOException {

		// The target letter in the row
		String Letter = ItemAbstract.RowArray[ItemAbstract.RowArrayIndex];
	
		//Firefox not working well February 2018
		//System.setProperty("webdriver.gecko.driver", "C:\\Users\\Martin\\Documents\\Java Libraries\\geckodriver-v0.20.0-win64\\geckodriver.exe");
		//WebDriver Driver = new FirefoxDriver();
		
		//Use a ChromeDriver instead 		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Martin\\Documents\\Java Libraries\\chromedriver_win32\\chromedriver.exe");
		WebDriver Driver = new ChromeDriver();	
		TimeUnit.SECONDS.sleep(1);
		Driver.get("http://www.howmanysyllables.com/");

		// Get word divided into syllables from website
		Driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);//helps!
		WebElement input = Driver.findElement(By.name("SearchQuery_FromUser"));
		input.sendKeys(word);
		WebElement submit = Driver.findElement(By.id("SearchDictionary_Button"));
		submit.click();

		/*
		 * On some pages of the website there is no syllable division given and
		 * the following line throws:
		 * 
		 * "Exception in thread main org.openqa.selenium.NoSuchElementException:
		 * Unable to locate element: .Answer_Red"
		 * 
		 * If this exception is thrown, the word will be saved to repository 
		 * AS IS and the repository must be edited manually by the user to record
		 * the correct syllable used.
		 */
		
		//WebElement result;
		String[] wordSyllables = new String[1];
		try {
			
			WebElement result = Driver.findElement(By.id("SyllableContentContainer")).findElement(By.className("Answer_Red"));
			// divide the output String into an array of syllables
			wordSyllables = result.getText().split("-");
		} catch (Exception ex) {
			System.out.println("Syllable division not found. Saving whole word as one syllable.");
			wordSyllables[0] = word;
		}

		// save syllable that contains the target letter
		String savedSyllable = wordSyllables[0];
		for (int i = 0; i < wordSyllables.length; i++) {

			if (wordSyllables[i].contains(Letter)) {
				savedSyllable = wordSyllables[i].trim();
			}
		}
		Driver.quit();
		return savedSyllable;	
	}
}
