package webdriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Topic_05_Web_Browser {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@Test
	public void TC_01_Run_Firefox() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/"); //change URL if you wish
		driver.quit();
		
		//Tương tác vs Browser thì sẽ thông qua biến WebDriver driver
		//Tương tác vs Element thì sẽ thông qua biến WebElement element
	}

	@Test
	public void TC_02_Run_Chrome() {
		// >=2: Nó sẽ đóng tab/window mà nó đang đứng
		// =1: Nó sẽ đóng Browser
		driver.close();
		
		//Không quan tâm bao nhiêu tab/window --> đóng Browser
		driver.quit();
		
		// Có thể lưu nó vào 1 biến để sử dụng cho các step sau -> dùng lại nhiều lần
		// Clean code
		// Làm sao cho code đúng/ chạy được
		WebElement emailTextbox = driver.findElement(By.xpath("//input[@id='email']"));
		emailTextbox.clear();
		emailTextbox.sendKeys("");
		
		// Bad code
		driver.findElement(By.xpath("//input[@id='email']")).clear();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("");
		
		// Có thể sử dụng luôn (không cần tạo biến) nếu chỉ dùng 1 lần --> tìm 1 element
		driver.findElement(By.xpath("//button[@id='login']")).click();
		
		// Tìm nhiều element
		List<WebElement> checkboxes = driver.findElements(By.xpath(""));
		
		// Mở ra 1 Url nào đó
		driver.get("https://www.facebook.com/");
		
		// Click vào link: Tiếng Việt
		
		// Trả về Url của page hiện tại
		Assert.assertEquals(driver.getCurrentUrl(), "https://vi-vn.facebook.com/");
		
		// Trả về Source Code HTML của page hiện tại
		// Verify tương đối
		Assert.assertTrue(driver.getPageSource().contains("Facebook giúp bạn kết nối và chia sẻ với mọi người trong cuộc sống của bạn"));
		Assert.assertTrue(driver.getPageSource().contains("trong cuộc sống của bạn."));
		Assert.assertTrue(driver.getPageSource().contains("Facebook giúp bạn kết nối và chia sẻ"));
		
		// Trả về title của page hiện tại
		Assert.assertEquals(driver.getTitle(), "Facebook - Đăng nhập hoặc đăng ký");
		
		// WebDriver API - Windows/Tabs sẽ học 2 cái dưới
		// Lấy ra được ID của Window/Tab mà driver đang đứng (active)
		String loginWindowIDString = driver.getWindowHandle();
		
		// Lấy ra ID của tất cả Window/Tab
		Set<String> allIDSet = driver.getWindowHandles();
		
		// Cookie / Cache: sẽ học sau
		Options option = driver.manage();
		//Login thành công -> Lưu lại
		option.getCookies();
		//Testcase khác -> Set cookie vào lại -> Không cần phải Login nữa
		
		option.logs();
		
		Timeouts timeouts = option.timeouts();
		
		//Implicit wait and depend on: FindElement / FindElements
		//Khoảng thời gian chờ element xuất hiện trong vòng x giây
		timeouts.implicitlyWait(5, TimeUnit.SECONDS);
		timeouts.implicitlyWait(5000,TimeUnit.MILLISECONDS);
		timeouts.implicitlyWait(5000000, TimeUnit.MICROSECONDS);
		
		//Khoảng thời gian chờ page load xong trong vòng x giây
		timeouts.pageLoadTimeout(5, TimeUnit.SECONDS);
		
		//WebDriver API - Javascript Executor (JavascriptExecutor library)
		//Khoảng thời gian chờ script được thực thi xong trong vòng x giây
		timeouts.setScriptTimeout(5, TimeUnit.SECONDS);
		
		Window window = option.window();
		window.fullscreen();
		window.maximize();
		
		//Test FUI: Functional
		//Test GUI: Font/ Size/ Color/ Position/ Location/...
		window.getPosition();
		window.getSize();
		
		Navigation navigation = driver.navigate();
		navigation.back();
		navigation.refresh();
		navigation.forward();
		
		navigation.to("https://www.facebook.com/"); //hai tụi này bằng nhau, nhưng người ta hay dùng driver.get nhiều hơn
		driver.get("https://www.facebook.com/");
		
		TargetLocator targetLocator = driver.switchTo();
		//WebDriver API - Alert/ Authentication Alert (Alert library)
		targetLocator.alert();
		
		//WebDriver API - Frame/ Iframe (Frame library)
		targetLocator.frame("");
		
		//WebDriver API - Windows/Tabs
		targetLocator.window("");
		
	}
}