package webdriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_18_Window_Tab {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	//@Test
	public void TC_01_ID() {
		// Parent Page
		driver.get("https://automationfc.github.io/basic-form/");
		
		// Lấy ra được ID của tab hiện tại
		String basicFormID = driver.getWindowHandle();
		System.out.println("Parent Page = " + basicFormID);
		
		// Click vào Google link để bật ra 1 tab mới
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		sleepInSecond(2);
		
		switchToWindowByID(basicFormID);
		
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");
		
		//Vì sao dùng hàm này lại --> lấy ra được ID của tab Google (child)
		String googleWindowID = driver.getWindowHandle();
		
		switchToWindowByID(googleWindowID);
		Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/");
	}
	
	//@Test
	public void TC_02_Title() {
		// Parent Page
		driver.get("https://automationfc.github.io/basic-form/");
		
		// Click vào Google link để bật ra 1 tab mới
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		sleepInSecond(2);
		
		switchToWindowByPageTitle("Google");
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");
		
		switchToWindowByPageTitle("Selenium WebDriver");
		Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/");
		
		//Click vào Facebook link để bật ra 1 tab mới
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		sleepInSecond(2);
		
		switchToWindowByPageTitle("Facebook – log in or sign up");
		driver.findElement(By.cssSelector("input#email")).sendKeys("automation@gmail.com");
		driver.findElement(By.cssSelector("input#pass")).sendKeys("Password123");
		
		switchToWindowByPageTitle("Selenium WebDriver");
		Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/");
		
		// Click vào Tiki link để bật ra 1 tab mới
		driver.findElement(By.xpath("//a[text()='TIKI']")).click();
		sleepInSecond(2);
		
		switchToWindowByPageTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		driver.findElement(By.cssSelector("input[class^='FormSearch']")).sendKeys("Macbook Pro 2022");
	}
	
	@Test
	public void TC_03_Live_Guru() {
		driver.get("http://live.techpanda.org/index.php/mobile.html");
		
		String parentID = driver.getWindowHandle();
		
		//Click vào Xperia - Compare
		driver.findElement(By.xpath("//a[@title='Sony Xperia']/parent::h2/following-sibling::div[@class='actions']//a[@class='link-compare']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "The product Sony Xperia has been added to comparison list.");
		
		driver.findElement(By.xpath("//a[@title='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//a[@class='link-compare']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "The product Samsung Galaxy has been added to comparison list.");
		
		driver.findElement(By.xpath("//button[@title='Compare']")).click();
		
		switchToWindowByPageTitle("Products Comparison List - Magento Commerce");
		Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Compare Products']")).isDisplayed());
		
		// Sau khi click vào Close Window button thì driver sẽ chuyển về trang nào? 
		//driver.findElement(By.xpath("//button[@title='Close Window']")).click();
		closeAllWindowWithoutParent(parentID);
		
		switchToWindowByPageTitle("Mobile");
		
		driver.findElement(By.xpath("//a[@title='IPhone']/parent::h2/following-sibling::div[@class='actions']//a[@class='link-compare']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "The product IPhone has been added to comparison list.");
	}
	
	// Dùng được cho duy nhất 2 ID (Window/Tab)
	public void switchToWindowByID(String otherID) {
		// Lấy hết tất cả các ID ra
		Set<String> allWindowIDs = driver.getWindowHandles();
		// Sau đó dùng vòng lặp duyệt qua và kiểm tra
		for (String id : allWindowIDs) {
			if (!id.equals(otherID)) {
				driver.switchTo().window(id);
				sleepInSecond(1);
			}
		}
	}
	
	// Dùng được cho từ 2 ID trở lên (Window/Tab)
	public void switchToWindowByPageTitle(String expectedPageTitle) {
		// Lấy hết tất cả các ID ra
		Set<String> allWindowIDs = driver.getWindowHandles();

		int count = 1;

		// Sau đó dùng vòng lặp duyệt qua và lấy ra hết các page title
		for (String id : allWindowIDs) {
			// Switch từng ID trước
			driver.switchTo().window(id);
			sleepInSecond(1);

			// Lấy ra title của page này
			String actualPageTitle = driver.getTitle();
			System.out.println("Actual title = " + actualPageTitle + " - " + count);
			count++;

			if (actualPageTitle.equals(expectedPageTitle)) {
				sleepInSecond(1);
				break;
			}
		}
	}
		
	public void closeAllWindowWithoutParent(String parentID) {
		Set<String> allwindowIDs = driver.getWindowHandles();
		for (String id : allwindowIDs) {
			if (!id.equals(parentID)) {
				driver.switchTo().window(id);
				driver.close();
				sleepInSecond(2);
			}
		}
		driver.switchTo().window(parentID);
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