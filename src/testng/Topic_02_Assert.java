package testng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Topic_02_Assert {
	WebDriver driver;
	
	@Test
	public void TC_01() {
	// 3 hàm assert hay dùng
	// Kiểm tra tính đúng đắn của dữ liệu
		
	// 1 - Kiểm tra dữ liệu mình mong muốn là ĐÚNG
	// Email textbox hiển thị
	Assert.assertTrue(driver.findElement(By.id("email")).isDisplayed());
	
	// 2 - Kiểm tra dữ liệu mình mong muốn là SAI
	// Email textbox ko hiển thị
	Assert.assertFalse(driver.findElement(By.id("email")).isDisplayed());
	
	// 3 – Kiểm tra dữ liệu mình mong muốn với dữ liệu thực tế là BẰNG NHAU
	// Tuyệt đối 2 cái bằng nhau
	Assert.assertEquals(driver.findElement(By.id("search")).getAttribute("placeholder"),
	"Search entire store here...");
	Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(),
	"This is a required field.");
	
	// Tương đối
	String benefitText = driver.findElement(By.cssSelector("ul.benefits")).getText(); 
	Assert.assertTrue(benefitText.contains("Faster checkout"));
	Assert.assertTrue(benefitText.contains("Save multiple shipping addresses")); 
	Assert.assertTrue(benefitText.contains("View and track orders and more"));
	
	// Hamcrest/ AssertJ/..
	
	}
}
