package webdriver;

import java.io.File;
import java.util.List;
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

public class Topic_25_ExplicitWait {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	WebDriverWait explicitWait;
	
	//Image name
	String vietnam = "Viet Nam.jpg";
	String thailan = "Thai Lan.jpg";
	String indonesia = "Indonesia.jpg";
	
	//Upload file folder
	String uploadFileFolderPath = projectPath + File.separator + "uploadFiles" + File.separator;
	
	//Image Path
	String vietnamFilePath = uploadFileFolderPath + vietnam;
	String thailanFilePath = uploadFileFolderPath + thailan;
	String indonesiaFilePath = uploadFileFolderPath + indonesia;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		//explicitWait = new WebDriverWait(driver, 15);
		// Cũng giống implicitWait, explicitWait nếu tìm ra được element thì thời gian dư nó sẽ bỏ qua, tìm thấy là pass liền
		
	}
	
	//@Test
	public void TC_01_Visible() {
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		explicitWait = new WebDriverWait(driver, 3);
		
		// Click vào Start button
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		// Thiếu thời gian để cho 1 element tiếp theo hoạt động được
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#finish>h4")));
		
		// Get text và verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
	}

	//@Test
	public void TC_02_Invisible() {
		explicitWait = new WebDriverWait(driver, 5);
		
		driver.get("https://automationfc.github.io/dynamic-loading/");
		
		// Click vào Start button
		driver.findElement(By.cssSelector("div#start>button")).click();
		
		// Wait cho loading icon biến mất
	   explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loading")));
		
		// Get text và verify
		Assert.assertEquals(driver.findElement(By.cssSelector("div#finish>h4")).getText(), "Hello World!");
	}
	
	//@Test 
	public void TC_03_Ajax_Loading() {
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		
		explicitWait = new WebDriverWait(driver, 15);
		
		// Wait cho Date Picker được hiển thị
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.RadCalendar")));
		
		// Verify cho Selected Dates là ko có ngày nào được chọn
		Assert.assertEquals(driver.findElement(By.cssSelector("span#ctl00_Content Placeholder1_Label1")).getText(), "No Selected Dates to display.");
		
		// Wait cho ngày 19 được phép click
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='19']")));
		
		// Click vào ngày 19
		driver.findElement(By.xpath("//a[text()='19']")).click();
		
		// Wait cho Ajax icon loading biến mất (invisible)
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id*='RadCalendar1']>div.raDiv")));
		
		// Wait cho ngày vừa được chọn là được phép click trở lại
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class='rcSelected']/a[text()='19']")));
		
		// Verify cho Selected Dates là "Tuesday, July 19, 2022"
		Assert.assertEquals(driver.findElement(By.cssSelector("span#ctl100_ContentPlaceholder1_Label1")).getText(), "Tuesday, July 19, 2022");
	
	}
	
	@Test
	public void TC_04_Upload_File() {
	driver.get("https://gofile.io/uploadFiles");
	
	explicitWait = new WebDriverWait(driver, 45, 1000);
	
	// Wait cho Add Files button được visible
	explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#filesUpload > div > div:nth-child(1) > div > button")));
	
	driver.findElement(By.cssSelector("input[type='file']")).sendKeys(vietnamFilePath + "\n" + thailanFilePath + "\n" + indonesiaFilePath);
	
	// Wait cho các loading icon của từng file biến mất
	explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div#rowUploadProgress-list div.progress")));
	
	// Wait cho Upload message thành công được visible
	explicitWait.until (ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5 [text()='Your files have been successfully uploaded']"))); 
	
	// Verify message này displayed
	Assert.assertTrue(driver.findElement(By.xpath("//h5 [text()='Your files have been successfully uploaded']")).isDisplayed());
	
	// Wait + Click cho Show file button được clickable
	explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#rowUploadSuccess-showFiles"))).click();
	
	// Wait + verify luôn: cho file name vs button download hiển thị
	Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + thailan +"']/parent::a/parent::div/following-sibling::div//button[@id='contentId-download']")))
			.isDisplayed());
	Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + vietnam +"']/parent::a/parent::div/following-sibling::div//button[@id='contentId-download']")))
			.isDisplayed());
	Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + indonesia +"']/parent::a/parent::div/following-sibling::div//button[@id='contentId-download']")))
			.isDisplayed());
	
	// Wait + verify luôn: cho file name vs button play hiển thị 
	Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + thailan + "']/parent::a/parent::div/following-sibling::div//button[contains(@class, 'contentPlay')]")))
			.isDisplayed());
	Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + vietnam + "']/parent::a/parent::div/following-sibling::div//button[contains(@class, 'contentPlay')]")))
			.isDisplayed());
	Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + indonesia + "']/parent::a/parent::div/following-sibling::div//button[contains(@class, 'contentPlay')]")))
			.isDisplayed());
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