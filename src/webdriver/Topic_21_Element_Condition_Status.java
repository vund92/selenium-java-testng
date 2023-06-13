package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_21_Element_Condition_Status {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	WebDriverWait explicitWait;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		explicitWait = new WebDriverWait(driver, 10);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//driver.manage().window().maximize();
	}

	public void TC_01_Visible_Displayed_Visibility() {
		driver.get("https://www.facebook.com/");
		// 1. Có trên UI (bắt buộc)
		// 1. Có trong HTML (bắt buộc)
		
		// Chờ cho email address textbox hiển thị trong vòng 10s
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
	}
	
	@Test
	public void TC_02_Invisible_Undisplayed_Invisibility_I() {
		driver.get("https://www.facebook.com/");
		// 2. Không có trên UI (bắt buộc)
		// 1. Có trong HTML
		
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();

		// Chờ cho re-enter Email address textbox không hiển thị trong vòng 10s
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("reg_email_confirmation___")));
	}
	
	//@Test
	public void TC_02_Invisible_Undisplayed_Invisibility_II() {
		// 2. Không có trên UI (bắt buộc)
		// 2. Không có trong HTML
		driver.get("https://www.facebook.com/");

		// Chờ cho Re-enter Email textbox không hiển thị trong vòng 10s
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("reg_email_confirmation___")));
	}

	@Test
	public void TC_03_Presence_I() {
		
		driver.get("https://www.facebook.com/");
		//1. Có ở trên UI
		//1. Có ở trong cây HTLM (bắt buộc)
		
		//CHờ cho email address textbox presence trong HTML trong vòng 10s
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
	}
	
	@Test
	public void TC_03_Presence_II() {
		// 2. Không có trên UI
		// 1. Có ở trong cây HTML (bắt buộc)
		driver.get("https://www.facebook.com/");
		
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
		
		// Chờ cho Re-enter Email textbox không hiển thị trong vòng 10s
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.name("reg_email_confirmation___")));
	}
	
	@Test
	public void TC_04_Staleness() {
		// 2. Không có trên UI (bắt buộc)
		// 2. Không có trong HTML
		driver.get("https://www.facebook.com/");

		driver.findElement(By.xpath("//a[@data-testid= 'open-registration-form-button']")).click();

		// Phase 1: Element có trong cây HTML
		WebElement reEnterEmailAddressTextbox = driver.findElement(By.name("reg_email_confirmation___"));
		// Thao tác vs element khác làm cho element re-enter email ko còn trong DOM nữa
		// ...

		// Close popup đi
		driver.findElement(By.cssSelector("img._8idr")).click();

		// Chờ cho Re-enter Email textbox không còn trong DOM trong vòng 10s
		explicitWait.until(ExpectedConditions.stalenessOf(reEnterEmailAddressTextbox));
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