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

public class Topic_21_FindElement_FindElements {
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
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		//driver.manage().window().maximize();
	}

	@Test
	public void TC_01_FindElement() {
		// - Tìm thấy duy nhất 1 element/ node
		// Tìm thấy và thao tác trực tiếp lên node đó
		// Vì nó tìm thấy nên ko cần phải chờ hết timeout 15s
		driver.findElement(By.cssSelector("input#email"));

		// - Tìm thấy nhiều hơn 1 element/ node
		// Nó sẽ thao tác vs node đầu tiên
		// Ko quan tâm các node còn lại
		// Trong trường hợp các bạn bắt locator sai thì nó tìm sai
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("“dam@gmail.com");

		// - Không tìm thấy element/ node nào
		// Có cơ chế tìm lại = nửa giây (0.5s) sẽ tìm lại 1 lần
		// Nếu trong thời gian đang tìm lại mà thấy element thì thỏa mãn điều kiện - Pass
		// Nếu hết thời gian (15s) mà vẫn ko thấy element thì 
		// + Đánh fail testcase này tại step này
		// + Throw ra 1 ngoại lệ: NoSuchElementException
		driver.findElement(By.cssSelector("input[type='check']"));
	}

	@Test
	public void TC_02_FindElements() {
		// - Tìm thấy duy nhất 1 element/ node
		// Tìm thấy và lưu nó vào 1ist = 1 element
		// Và nó tìm thấy nên ko cần phải chờ hết timeout 15s
		List<WebElement> elements = driver.findElements(By.cssSelector("input#email"));
		System.out.println("List element numben = " + elements.size());

		// - Tìm thấy nhiều hơn 1 element/ node
		//Tìm thấy và lưu nó vào list = element tương ứng
		elements = driver.findElements(By.cssSelector("input[type='email']"));
		System.out.println("List element number = " + elements.size());
		
		// - Không tìm thấy element/ node nào
		// Có cơ chế tìm lại = nửa giây (0.5s) sẽ tìm lại 1 lần
		// Nếu trong thời gian đang tìm lại thấy element thì thỏa mãn điều kiện - Pass
		// Nếu hết thời gian (15s) mà vẫn ko thấy element
		// + Ko đánh fail testcase + vẫn chạy step tiếp theo
		// + Trả về 1 list rỗng (empty) = 0
		elements = driver.findElements(By.cssSelector("input[type='check']"));
		System.out.println("List element number = " + elements.size());
	}


	@Test
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