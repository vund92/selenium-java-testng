package webdriver;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Topic_29_Assert_Verify {
	
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");
	}

	@Test
	public void TC_01_ValidateCurrentUrl() {
		System.out.println("Assert 01");
		String loginPageUrl = driver.getCurrentUrl();
		assertEquals(loginPageUrl, "https://www.facebook.com/"); //do da import static org.testng.Assert.assertEquals;
		
		System.out.println("Assert 02");
		String loginPageTitle = driver.getTitle();
		assertEquals(loginPageTitle, "Facebook – log in or sign up...");
		
		System.out.println("Assert 03");
		assertTrue(driver.findElement(By.xpath("//form[@data-testid='royal_login_form']")).isDisplayed()); //do da import static org.testng.Assert.assertTrue;
		
		System.out.println("Assert 04");
		assertTrue(driver.findElement(By.xpath("//form[@data-testid='royal_login_form']")).isDisplayed());
		
		System.out.println("Assert 05");
		assertFalse(driver.findElement(By.xpath("//form[@data-testid='royal_login_form']")).isDisplayed()); //do da import static org.testng.Assert.assertFalse;
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}