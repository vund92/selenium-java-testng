package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Button_Radio_Checkbox {
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

	@Test
	public void TC_01_Button() {
		driver.get("https://www.fahasa.com/customer/account/create");
		
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
		
		By loginButton = By.cssSelector("button.fhs-btn-login");
		
		//Verify login button is disable
		Assert.assertFalse(driver.findElement(loginButton).isEnabled());
		
		String loginButtonBackground = driver.findElement(loginButton).getCssValue("background-image");
		System.out.println(loginButtonBackground);
		
		driver.findElement(By.cssSelector("input#login_username")).sendKeys("0987666777");
		driver.findElement(By.cssSelector("input#login_password")).sendKeys("123456789");
		sleepInSecond(2);
		
		//Verify login button is enabled
		Assert.assertTrue(driver.findElement(loginButton).isEnabled());
		
		loginButtonBackground = driver.findElement(loginButton).getCssValue("background-color");
		
		Color loginButtonBackgroundColor = Color.fromString(loginButtonBackground);
		
		Assert.assertEquals(loginButtonBackgroundColor.asRgb().toUpperCase(), "rgb(201, 33, 39)");
		Assert.assertEquals(loginButtonBackgroundColor.asHex().toUpperCase(), "#C922127");
		
		System.out.println(loginButtonBackground);
	}

	@Test
	public void TC_02_Default_Checkbox_Radio_Single() {

		driver.get("https://autonationfc.github.io/multiple-fields/");
		
		// Click chọn 1 checkbox
		driver.findElement(By.xpath("//label [contains(text(), 'Diabetes')]/preceding-sibling::input")).click();
		
		// Click chọn 1 radio
		driver.findElement(By.xpath("//label[contains(text(),\"I don't drink\")]/preceding-sibling::input")).click();
		
		// Verify các checkbox/ radio đã được chọn rồi
		Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(), 'Diabetes')]/preceding-sibling::input")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//label [contains(text(),\"I don't drink\")]/preceding-sibling::input")).isSelected());
		
		// Checkbox có thể tự bỏ chọn được
		driver.findElement(By.xpath("//label[contains(text(), 'Diabetes')]/preceding-sibling::input")).click();
		
		// Verify checkbox đã được bỏ chọn rồi
		Assert.assertFalse(driver.findElement(By.xpath("//label [contains(text(), 'Diabetes')]/preceding-sibling::input")).isSelected());
		
		// Radio ko thể tự bỏ chọn được
		driver.findElement(By.xpath("//label[contains(text(),\"I don't drink\")]/preceding-sibling::input")).click();
		
		// Verify radio vẫn được chọn rồi
		Assert.assertTrue(driver.findElement(By.xpath("//label [contains(text(),\"I don't drink\")]/preceding-sibling::input")).isSelected());

	}

	@Test
	public void TC_03_Default_Checkbox_Multiple() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		
		List<WebElement> allCheckboxes = driver.findElements(By.cssSelector("input.form-checkbox"));
		
		//Dùng vòng lặp để duyệt qua và click vào tất cả các checkbox này
		for(WebElement checkboxElement : allCheckboxes) {
			checkboxElement.click();
		}
		
		//Verify tất cả các checkbox được chọn thành công
		for(WebElement checkboxElement : allCheckboxes) {
			Assert.assertTrue(checkboxElement.isSelected());
		}
		
		//Nếu như gặp 1 checkbox có tên là X thì mới click
		for(WebElement checkboxElement : allCheckboxes) {
			if(checkboxElement.getAttribute("value").equals("Arthritis")){
				checkboxElement.click();
			}
		}
	}
	
	@Test
	public void TC_04_Default_Checkbox() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		
		// Chọn nó
		checkToCheckbox(By.xpath("//label [text()='Dual-zone air conditioning']/preceding-sibling::input"));
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input")).isSelected());
		
		// Bỏ chọn nó
		uncheckToCheckbox(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input"));
		Assert.assertFalse(driver.findElement(By.xpath("//label[text()= 'Dual-zone air conditioning']/preceding-sibling::input")).isSelected());

	}
	
	public void checkToCheckbox(By by) {
		if (!driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
		}
	}

	public void uncheckToCheckbox(By by) {
		if (driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
		}
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