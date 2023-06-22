package testng;

import org.testng.annotations.Test;

public class Topic_03_Priority {
	// Priority mặc định của TestNG là 0-9 A-Z
	// Nếu muốn set Priority case nào chạy trước chạy sau thì dùng từ khóa priority
	
	@Test(priority = 1, enabled = true, description = "JIRA_0787 - Create a new Employee and verify the employee created success")
	public void EndUser_Create_New_Employee() {
	}
	
	@Test(priority = 2, enabled = false)
	public void EndUser_View_Employee() {
	}
	
	@Test(priority = 3, enabled = false)
	public void EndUser_Edit_Employee() {
	}
	
	@Test(priority = 4)
	public void EndUser_Move_Employee() {
	}
}
