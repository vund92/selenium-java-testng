package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Custom_Dropdown {
	WebDriver driver;
	WebDriverWait explicitWait;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		//explicitWait = new WebDriverWait(driver, 30); --> để đây là sai vị trí vì driver chưa sinh ra
		driver = new FirefoxDriver();
		explicitWait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	//@Test
	public void TC_01_JQuery() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		
		/* CHỌN LẦN ĐẦU TIÊN */
		//1 - Click vào 1 thẻ bất kì để làm sao cho nó xổ ra hết các item của dropdown
		driver.findElement(By.cssSelector("span#speed-button")).click();
		
		//2 - Chờ cho tất cả các item được load ra thành công
		// Locator phải lấy để đại diện cho tất cả các item
		// Lấy đến thẻ chứa text
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#speed-menu div[role='option']")));
		
		//Đưa hết tất cả item trong dropdown vào 1 list
		List<WebElement> speedDropdownItems = driver.findElements(By.cssSelector("ul#speed-menu div[role='option']"));
		
		// 3 - Tìm item xem đúng cái đang cần hay ko
		// 3.1 - Nếu nó nằm trong khoảng nhìn thấy của User ko cần phải scroll xuống
		// 3.2 - Nếu nó ko nằm trong khoảng nhìn thấy của User thì cần scroll xuống đến item đó
		for(WebElement tempItem : speedDropdownItems) {
			String itemText = tempItem.getText();
			System.out.println(itemText);
			
			//4 - Kiểm tra cái text của item đúng vs cái mình mong muốn
			if(itemText.equals("Fast")) {
				//5 - Click vào item đó
				System.out.println("Click vào item");
				tempItem.click();
				
				//Thoát ra khỏi vòng lặp không xét cho các case còn lại nữa
				break;
			} else {
				System.out.println("Không click vào item");
			}
		}
		
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button>span.ui-selectmenu-text")).getText(), "Fast");
		
		/* CHỌN LẦN THỨ HAI */
		selectItemDropdown("span#speed-button", "ul#speed-menu div[role='option']", "Slower");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("span#speed-button>span.ui-selectmenu-text")).getText(), "Slower");
	}

	//@Test
	public void TC_02_ReactJS() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		
		selectItemDropdown("i.dropdown.icon", "span.text", "Stevie Feliciano");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Stevie Feliciano");
		
	}

	//@Test
	public void TC_03_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		
		selectItemDropdown("li.dropdown-toggle", "ul.dropdown-menu a", "Second Option");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "Second Option");
	}
	
	@Test
	public void TC_04_Editable() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		
		selectItemDropdown("input.search", "span.text", "Australia");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Australia");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	//Tránh lặp lại code nhiều lần thì chỉ cần gọi hàm ra để dùng
	//Đi kèm với tham số
	//Nếu truyền cứng 1 giá trị vào trong hàm = vô nghĩa
	//Nên define để dùng đi dùng lại nhiều lần
	public void selectItemDropdown(String parentCss, String allItemCss, String expectedTextItem) {
		//1 - Click vào 1 thẻ bất kì để làm sao cho nó xổ ra hết các item của dropdown
		driver.findElement(By.cssSelector(parentCss)).click();
		
		//2 - Chờ cho tất cả các item được load ra thành công
		// Locator phải lấy để đại diện cho tất cả các item
		// Lấy đến thẻ chứa text
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(allItemCss)));
		
		//Đưa hết tất cả item trong dropdown vào 1 list
		List<WebElement> speedDropdownItems = driver.findElements(By.cssSelector(allItemCss));
		
		// 3 - Tìm item xem đúng cái đang cần hay ko
		// 3.1 - Nếu nó nằm trong khoảng nhìn thấy của User ko cần phải scroll xuống
		// 3.2 - Nếu nó ko nằm trong khoảng nhìn thấy của User thì cần scroll xuống đến item đó
		for(WebElement tempItem : speedDropdownItems) {
			String itemText = tempItem.getText().trim(); //TRIM STRING
			System.out.println(itemText);
			
			//4 - Kiểm tra cái text của item đúng vs cái mình mong muốn
			if(itemText.equals(expectedTextItem)) {
				//5 - Click vào item đó
				System.out.println("Click vào item");
				tempItem.click();
				
				//Thoát ra khỏi vòng lặp không xét cho các case còn lại nữa
				break;
			} else {
				System.out.println("Không click vào item");
			}
		}
	}
	
	public void enterAndSelectItemDropdown(String textboxCss, String allItemCss, String expectedTextItem) {
		driver.findElement(By.cssSelector(textboxCss)).sendKeys(expectedTextItem);
		sleepInSecond(1);
		
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(allItemCss)));
		
		List<WebElement> speedDropdownItems = driver.findElements(By.cssSelector(allItemCss));
		
		for(WebElement tempItem : speedDropdownItems) {
			String itemText = tempItem.getText().trim(); //TRIM STRING
			System.out.println(itemText);
			if(itemText.equals(expectedTextItem)) {
				System.out.println("Click vào item");
				tempItem.click();
				break;
			} else {
				System.out.println("Không click vào item");
			}
		}
	}
	
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}