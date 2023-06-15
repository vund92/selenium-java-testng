package webdriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Function;

public class Topic_27_FluentWait {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	
	FluentWait<WebDriver> fluentDriver; 
	FluentWait<WebElement> fluentElement;
	
	long allTime = 15; // Second
	long pollingTime = 100; // Milisecond

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		explicitWait = new WebDriverWait(driver, 15);

	}

	@Test
	public void TC_01_Fluent() {
		driver.get("https://automationfc.github.io/dynamic-loading");
		
		findElement("//div[@id='start']/button").click();
		
		Assert.assertEquals(findElement("//div[@id='finish']/h4").getText(), "Hello World!");
	}
	
	@Test
	public void TC_02_Fluent() {
		driver.get("https://automationfc.github.io/fluent-wait/");
		
		WebElement countdountTime = findElement("//div[@id='javascript_countdown_time']");
		
		fluentElement = new FluentWait<WebElement>(countdountTime);
		
		fluentElement.withTimeout(Duration.ofSeconds(allTime)).pollingEvery(Duration.ofMillis(pollingTime))
				.ignoring(NoSuchElementException.class);
		
		fluentElement.until(new Function<WebElement, Boolean>() {
			@Override
			public Boolean apply(WebElement element) {
				String text = element.getText();
				System.out.println(text);
				return text.endsWith("00");
			}
		});
	}

	public WebElement findElement(String xpathLocator) {
		fluentDriver = new FluentWait<WebDriver>(driver);
		fluentDriver.withTimeout(Duration.ofSeconds(allTime))
		.pollingEvery(Duration.ofMillis(pollingTime)).ignoring(NoSuchElementException.class);
		return fluentDriver.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.xpath(xpathLocator));
			}
		});
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