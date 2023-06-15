package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_24_StaticWait {
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
		
		// 1 - Ảnh hưởng trực tiếp tới 2 hàm: findElement/ findElements
		//2 - Ngoại lệ
		// Implicit Wait set ở đâu nó sẽ apply từ đó trở xuống
		// Nếu bị gán lại thì sẽ dùng cái giá trị mới/ ko dùng giá trị cũ
		
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		

	}
	
	@Test
	public void TC_01_Not_Enough_Time() {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		sleepInSecond(2);
		
		// Click vào Start button
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		sleepInSecond(2);
		
		// Get text và verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
	}

	@Test
	public void TC_02_Enough_Time() {
		sleepInSecond(5);
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		// Click vào Start button
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		//Loading icon mất 5s mới biến mất chỗ này
		sleepInSecond(5);
		
		// Get text và verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
	}

	@Test
	public void TC_03_More_Time() {
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		// Click vào Start button
		sleepInSecond(10);
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		sleepInSecond(10);
		// Get text và verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
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