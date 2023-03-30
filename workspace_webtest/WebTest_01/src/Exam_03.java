import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Exam_03 {
	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1500, 800));
		driver.get("https://www.interpark.com/");
		
		String parentHandle = driver.getWindowHandle();
		
		WebElement list = driver.findElement(By.linkText("·Î±×ÀÎ"));
		list.click();
		
		Set<String> handles = driver.getWindowHandles();
//		System.out.println(handles);
		
		for(String handle : handles) {
			if(!handle.equals(parentHandle)) {
				driver.switchTo().window(handle);
			}
		}
		
		driver.findElement(By.id("userId")).sendKeys("dhanhak");
		
		driver.findElement(By.id("userPwd"))
			.sendKeys("123456");
		
		driver.findElement(By.id("btn_login"))
			.click();
		
		
		
	}
}
