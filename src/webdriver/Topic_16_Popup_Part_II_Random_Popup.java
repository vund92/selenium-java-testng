package webdriver;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.grid.internal.exception.NewSessionException;
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

public class Topic_16_Popup_Part_II_Random_Popup {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String emailAddress = "testdemo" + getRandomNumber() + "@gmail.com";

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
	
	//Yêu cầu:
	//Random popup nên nó có thể hiển thị 1 cách ngẫu nhiên hoặc ko hiển thị
	//Nếu như nó hiển thị thì mình cần thao tác lên popup -> Đóng nó đi để qua step tiếp theo
	//Khi mà đóng nó lại thì có thể refresh trang nó hiện lên lại/ hoặc là ko
	//Nếu như nó ko hiển thị thì qua step tiếp theo luôn

	@Test
	public void TC_01_Random_In_DOM() {
		driver.get("https://www.javacodegeeks.com/");
		sleepInSecond(20);
		
		By lePopup = By.cssSelector("div.lepopup-popup-container>div:not([style='display:none'])");
		
		// Vì nó luôn có trong DOM nên có thể dùng hàm isDisplayed() để kiểm tra được
		if (driver.findElement(lePopup).isDisplayed()) {
			// Nhập Email vào
			driver.findElement(By.cssSelector("div.lepopup-input>input")).sendKeys(emailAddress);
			sleepInSecond(3);
			driver.findElement(By.cssSelector("a[data-label='Get the Books']>span")).click();
			sleepInSecond(5);
			
			// Verify
			Assert.assertEquals(driver.findElement(By.cssSelector("div.lepopup-element-html-content>h4")).getText(),
					"Thank you!");
			
			Assert.assertTrue(driver.findElement(By.cssSelector("div.lepopup-element-html-content>p")).getText()
					.contains("Your sign-up request was successful."));
			
			// Đóng popup đi -> Qua step tiếp theo
			// Sau ~5s nó sẽ tự đóng popup
			sleepInSecond(15);
		}
		
		String articleName = "Agile Testing Explained";
		
		// Qua step này
		driver.findElement(By.cssSelector("input#s")).sendKeys(articleName);
		driver.findElement(By.cssSelector("button.search-button")).click();
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.post-listing>article:first-child>h2>a")).getText(),
				articleName);
	}
	
	@Test
	public void TC_02_Random_In_DOM() {
		driver.get("https://vnk.edu.vn/");
		sleepInSecond(40);
		
		By popup = By.cssSelector("div#tve-p-scroller");
		
		// Hàm isDisplayed() chỉ check cho element có trong DOM
		// Ko có trong DOM thì ko có check được -) Fail ngay đoạn findElement rồi
		if (driver.findElement(popup).isDisplayed()) {
			// Close popup này đi hoặc là click vào link để join các Group Zalo
			driver.findElement(By.cssSelector("div#tve-p-scroller div.thrv_icon")).click();
			sleepInSecond(3);
		}
		
		driver.findElement(By.xpath("//button[text()='Danh sách khóa học']")).click();
		sleepInSecond(5);
		
		Assert.assertEquals(driver.getTitle(), "Lịch khai giảng các khóa học tại VNK EDU | VNK EDU");
	}
	
	@Test
	public void TC_03_Random_Not_In_DOM() {
		driver.get("https://dehieu.vn/");
		sleepInSecond(10);
		
		By popup = By.cssSelector("div.popup-content");
		
		// findElement -) Sẽ bị fail khi ko tìm thấy element -) Ném ra 1 ngoại lệ: NoSuchElementException
		// findElements -) Ko bị fail khi ko tìm thấy element -> Trả về 1 List rỗng (empty)
		
		// isDisplayed ()
		// Không còn trong DOM thì khi nó vào tìm element: findElements
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		if (driver.findElements(popup).size() > 0 && driver.findElements(popup).get(0).isDisplayed()) {
			driver.findElement(By.id("popup-name")).sendKeys("Elon Musk");
			driver.findElement(By.id("popup-email")).sendKeys(emailAddress);
			driver.findElement(By.id("popup-phone")).sendKeys("0987654321");
			sleepInSecond(3);
			
			driver.findElement(By.cssSelector("button#close-popup")).click();
			sleepInSecond(3);
		}
		
		driver.findElement(By.xpath("//a[text()='Tất cả khóa học']")).click();
		sleepInSecond(3);

		String courseName = "Khóa học Thiết kế và Thi công Hệ thống BMS";
		driver.findElement(By.id("search-courses")).sendKeys(courseName);
		driver.findElement(By.cssSelector("button#search-course-button")).click();
		sleepInSecond(3);
		
		// Duy nhất 1 course hiển thị
		Assert.assertEquals(driver.findElements(By.cssSelector("div.course")).size(), 1);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.course-content>h4")).getText(), courseName);
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
	
	public int getRandomNumber() {
		return new Random().nextInt(99999);
	}
}