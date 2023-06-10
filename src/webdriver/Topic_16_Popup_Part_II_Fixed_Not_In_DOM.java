package webdriver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Popup_Part_II_Fixed_Not_In_DOM {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac OS")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		}
		
		////Turn off notification for Firefox
		// FirefoxOptions options = new FirefoxOptions();
		// options.setProfile (new FirefoxProfile());
		// options.addPreference ("dom.webnotifications.enabled", false);
		// driver = new FirefoxDriver (options);
		
		//Turn off notification for Chrome
		Map<String, Integer> prefs = new HashMap<String, Integer>();
		prefs.put("profile.default_content_setting_values. notifications", 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		driver = new ChromeDriver(options);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	//@Test
	public void TC_01_Fixed_Not_In_DOM_Tiki() {
		driver.get("https://tiki.vn/");
		
		// By: Nó chưa có đi tìm element
		By loginPopup = By.cssSelector("div. ReactModal___Content");
		
		// Verify nó chưa hiển thị khi chưa click vào Login button
		Assert.assertEquals(driver.findElements (loginPopup).size(), 0);
		
		// Click cho bật login popup lên
		driver.findElement(By.cssSelector("div[data-view-id='header_account']")).click();
		sleepInSecond(2);
		
		// Verify nó hiển thị
		Assert.assertEquals(driver.findElements(loginPopup).size(), 1);
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());
		driver.findElement(By.cssSelector("p.login-with-email")).click();
		sleepInSecond(2);
		driver.findElement(By.xpath("//button[text() = 'Đăng nhập']")).click();
		sleepInSecond(2);
		
		// Verify error message displayed
		Assert.assertTrue(
				driver.findElement(By.xpath("//span[@class='error-mess' and text()='Email không được để trống']"))
						.isDisplayed());
		Assert.assertTrue(
				driver.findElement(By.xpath("//span[@class='error-mess' and text()='Mật khẩu không được để trống']"))
						.isDisplayed());
		sleepInSecond(2);
		
		// Close popup
		driver.findElement(By.cssSelector("img.close-img")).click();
		sleepInSecond(2);
		
		// Verify nó không còn hiển thị
		Assert.assertEquals(driver.findElements(loginPopup).size(), 0);
	}
	
	@Test
	public void TC_02_Fixed_Not_In_DOM_Facebook() { 
	driver.get("https://www.facebook.com/");
	
	By createAccountPopup = By.xpath("//div[text()='Sign Up']/parent::div/parent::div");
	
	// Verify Create Account popup ko hiển thị
	Assert.assertEquals(driver.findElements(createAccountPopup).size(), 0);
	
	driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();
	sleepInSecond (2);
	
	// Verify Create Account popup hiển thị
	Assert.assertEquals(driver.findElements (createAccountPopup).size(), 1);
	
	driver.findElement(By.name("firstname")).sendKeys("Automation"); 
	driver.findElement(By.name("lastname")).sendKeys("FC");
	driver.findElement(By.name("reg_email___")).sendKeys("0987654321"); 
	driver.findElement(By.name("reg_passwd____")).sendKeys("123456789"); 
	new Select(driver.findElement(By.id("day"))).selectByVisibleText("18"); 
	new Select(driver.findElement(By.id("month"))).selectByVisibleText("Aug"); 
	new Select(driver.findElement(By.id("year"))).selectByVisibleText("1999");
	driver.findElement(By.xpath("//label[text()='Female']/following-sibling::input")).click();
	sleepInSecond (2);
	
	driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/preceding-sibling::img")).click();
	sleepInSecond (2);
	
	// Verify Create Account popup ko hiển thị
	Assert.assertEquals(driver.findElements (createAccountPopup).size(), 0);
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