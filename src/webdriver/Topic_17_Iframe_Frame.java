package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_17_Iframe_Frame {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	Actions action;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Kyna_Iframe() {
		driver.get("https://kyna.vn/");
		
		// Verify Facebook iframe hiển thị
		// Facebook iframe vẫn là 1 element của trang kyna
		Assert.assertTrue(driver.findElement(By.xpath("//iframe[contains(@src, 'kyna.vn')]")).isDisplayed());
		
		// Cần phải switch vào đúng cái thẻ iframe chứa các element đó
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src, 'kyna.vn')]")));
		
		String facebookLike = driver.findElement(By.xpath("//a[text()='Kyna.vn']/parent::div/following-sibling::div"))
				.getText();
		System.out.println(facebookLike);
		
		Assert.assertEquals(facebookLike, "165K followers");
		
		// Cần switch về main page
		driver.switchTo().defaultContent();
		
		// Từ main page switch qua iframe Chat
		driver.switchTo().frame("cs_chat_iframe");
		
		// Click vào Chat để show lên Chat support
		driver.findElement(By.cssSelector("div.button_bar")).click();
		driver.findElement(By.cssSelector("input.input_name")).sendKeys("John Kennedy");
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0987666555");
		new Select(driver.findElement(By.id("serviceSelect"))).selectByVisibleText("TƯ VẤN TUYỂN SINH");
		driver.findElement(By.name("message")).sendKeys("Tư vấn khóa học Excel");
		sleepInSecond(3);
		
		// Từ chat iframe quay về trang main
		driver.switchTo().defaultContent();
		
		//Search với từ khóa Excel
		driver.findElement(By.id("live-search-bar")).sendKeys("Excel");
		driver.findElement(By.cssSelector("button.search-button")).click();
		
		List<WebElement> courseName = driver.findElements(By.cssSelector("div.content>h4"));
		
		for(WebElement course : courseName) {
			Assert.assertTrue(course.getText().contains("Excel"));
		}
	}

	@Test
	public void TC_02_HDFC_Bank_Frame() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		
		// Switch qua Frame chứa Login textbox
		driver.switchTo().frame("login_page");

		driver.findElement(By.name("fldLoginUserId")).sendKeys("john2022");
		driver.findElement(By.cssSelector("a.login-btn")).click();
		
		Assert.assertTrue(driver.findElement(By.cssSelector("input#fldPasswordDispId")).isDisplayed());
		driver.findElement(By.cssSelector("input#fldPassword DispId")).sendKeys("john20222003");
	}

	//@Test
	public void TC_03_Form() {
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