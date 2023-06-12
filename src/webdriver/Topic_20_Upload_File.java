package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_20_Upload_File {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	
	String imagesFileName = "images.jpg";
	String imagesOneFileName = "imagesone.jpg";
	String imagesTwoFileName = "imagestwo.jpg";
	
	String imagesFilePath = projectPath + "\\uploadFiles\\" + imagesFileName;
	String imagesOneFilePath = projectPath + "\\uploadFiles\\" + imagesOneFileName;
	String imagesTwoFilePath= projectPath + "\\uploadFiles\\" + imagesTwoFileName;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_81_Multiple_File_Per_Time() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		
		// Load file lên
		driver.findElement(By.cssSelector("input[type='file']"))
				.sendKeys(imagesFilePath + "\n" + imagesOneFilePath + "\n" + imagesTwoFilePath);
		sleepInSecond(1);
		//2 cách dưới đây cũng đúng
//		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(imagesOneFilePath);
//		sleepInSecond(1);
//		driver.findElement(By.cssSelector("input[type='file']")).sendKeys(imagesTwoFilePath);
//		sleepInSecond(1);
		
		// Verify file được load lên thành công
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@class='name' and text()='" + imagesFileName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + imagesOneFileName + "']"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + imagesTwoFileName + "']"))
				.isDisplayed());
		
		// Click upload
		List<WebElement> buttonUpload = driver.findElements(By.cssSelector("table button.start"));
		for (WebElement button : buttonUpload) {
			button.click();
			sleepInSecond(3);
		}
		
		// Verify upload thành công (Link)
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + imagesFileName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + imagesOneFileName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + imagesTwoFileName + "']")).isDisplayed());

		// Verify upload thành công (Image)
		Assert.assertTrue(isImageLoaded("//img[contains(@src,'" + imagesFileName + "')]"));
		Assert.assertTrue(isImageLoaded("//img[contains(@src,'" + imagesOneFileName + "')]"));
		Assert.assertTrue(isImageLoaded("//img[contains(@src,'" + imagesTwoFileName + "')]"));
	}
	
	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", getElement(locator));
		return status;
	}
	
	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
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