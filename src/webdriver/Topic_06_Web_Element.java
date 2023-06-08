package webdriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class Topic_06_Web_Element {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@Test
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		
		//Tương tác vs Browser thì sẽ thông qua biến WebDriver driver
		//Tương tác vs Element thì sẽ thông qua biến WebElement element
	}

	@Test
	public void TC_01_() {
		driver.get("https://demo.nopCommerce.com"); //change URL if you wish
		// Tương tác được với Element thì cần phải tìm được element đó
		// Thông qua các locator của nó
		// By: id/ class/ name/ xpath/ css/ tagname/ linkText/ partialLinkText
		
		WebElement element = driver.findElement(By.className(""));
		
		//Dùng cho các textbox/ textarea/ dropdown (Editable)
		//Xóa dữ liệu đi trước khi nhập text
		element.clear();
		
		//Dùng cho các textbox/ textarea/ dropdown (Editable)
		element.sendKeys("");
		
		//Click vào các button/ link/ checkbox/ radio/ image/...
		element.click();
		
		String searchAttributeString = element.getAttribute("placeholder");
		String emailTextboxAttribute = element.getAttribute("value");
		//Ket qua: Search store
		
		//GUI: Font/ Size/ Color/ Location/ Position/...
		element.getCssValue("background-color");
		//Ket qua: #4ab2f1
		
		//Vi tri cua element so với web
		element.getLocation();
		Point point = element.getLocation(); //import thư viện của Point coi chừng nhầm với 1 thư viện của java, nhớ dùng của Selenium
		point.x = 324;
		point.y = 324; //Set tọa độ
		
		//Kích thước của element (bên trong)
		element.getSize();
		Dimension di = element.getSize(); //import thư viện của Dimension coi chừng nhầm với 1 thư viện của java, nhớ dùng của Selenium
		di.getHeight();
		di.getWidth();
		
		System.out.println(di.height);
		System.out.println(di.width);
		
		//Location + Size
		element.getRect();
		Rectangle rec  = element.getRect();
		
		//Chụp hình khi testcase fail
		element.getScreenshotAs(OutputType.FILE);
		element.getScreenshotAs(OutputType.BYTES);
		element.getScreenshotAs(OutputType.BASE64);
		
		//Cần lấy ra tên thẻ HTML của element đó khi mình không biết trước tên thẻ của nó là gì ==> mình lấy nó ra để truyền vào cho 1 locator khác
		driver.findElement(By.xpath("//input[@id='Email']")).getTagName(); // cái này là biết rồi, thật ra ko dùng theo cách này mà dùng theo 3 cách ví dụ bên dưới
		//Kết quả: input
		driver.findElement(By.id("Email")).getTagName();
		driver.findElement(By.name("Email")).getTagName();
		driver.findElement(By.cssSelector("#Email")).getTagName();
		//Kết quả của cả ba cái trên, ví dụ, là: input
		
		//Cần lấy ra tên thẻ HTML của element đó khi mình không biết trước tên thẻ của nó là gì ==> mình lấy nó ra để truyền vào cho 1 locator khác
		String emailTextboxTagnameString = driver.findElement(By.cssSelector("#Email")).getTagName();
		driver.findElement(By.xpath("//" + emailTextboxTagnameString + "[@id='email']"));
		
		//Lấy text từ Error message/ success message/ label/ header...
		element.getText();
		//Ví dụ:  Please enter your email
		
		//Khi nào dùng getText or getAttribute
		//Khi cái value mình cần lấy nó nằm bên ngoài -> getText
		//Khi cái value mình cần lấy nó nằm bên trong -> getAttribute
		
		//Dùng để verify xem 1 element hiển thị hoặc ko
		//Phạm vi: Tất cả các element
		Assert.assertTrue(element.isDisplayed());
		Assert.assertFalse(element.isDisplayed());
		
		//Dùng để verify xem 1 element có thao tác được hay không
		// Phạm vi: Tất cả các element
		Assert.assertTrue(element.isEnabled());
		Assert.assertFalse(element.isEnabled());
		
		//Dùng để verify xem 1 element có được chọn hay chưa
		// Phạm vi: Checkbox/ Radio
		Assert.assertTrue(element.isSelected());
		Assert.assertFalse(element.isSelected());
		
		//Bắt buộc các element nằm trong các form (hoặc thẻ form)
		//submit() có ý nghĩa là nhấn phím Enter, ko cần phải click lên Submit button trên màn hình
		element.submit(); // nhưng cái này ít dùng, thường người ta dùng hàm element.click() vì độ chính xác cao hơn và ít gây nhầm lẫn hơn
		
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}