import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Exam_01 {
	public static void main(String[] args) {

		// UI 테스트 ( 웹 자동화 기술 )
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.naver.com");
		
		WebElement inputSearch = driver.findElement(By.id("query"));
		inputSearch.sendKeys("권지수 안녕");
		
		WebElement btnSearch = driver.findElement(By.id("search_btn"));
		btnSearch.click();
		
	}
}
