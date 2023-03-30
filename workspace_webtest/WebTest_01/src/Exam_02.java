import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Exam_02 {
	public static void main(String[] args) throws Exception {
		
		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--headless");
		
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.get("https://nid.naver.com/nidlogin.login?mode=form&url=https%3A%2F%2Fwww.naver.com");
		js.executeScript("document.getElementById('id').value = arguments[0]", Statics.USER_ID);
		js.executeScript("document.getElementById('pw').value = arguments[0]", Statics.USER_PW);

		driver.findElement(By.id("log.login")).click();

		// 타지역 로그인 해제 ? / 새로운 기기 등록 ?
		driver.get("https://mail.naver.com/v2/folders/0/all");

		//driver.switchTo().frame("gfp_iframe_uuid");

		// Thread.sleep(5000);
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.className("button_write_to_me")));
		wait.until(ExpectedConditions.numberOfElementsToBe(By.className("button_write_to_me"), 1));

		driver.findElement(By.className("button_write_to_me")).click();
		//System.out.println(btnWriteToMe.size());

		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.id("subject_title"), 0));
		driver.findElement(By.id("subject_title")).sendKeys("권지수 하앙");

		List<WebElement> frameList = driver.findElements(By.tagName("iframe"));
		for(WebElement e : frameList) {
			String tabindex = e.getDomAttribute("tabindex");
			if(tabindex != null && tabindex.equals("5")) {
				driver.switchTo().frame(e).findElement(By.className("workseditor-content")).sendKeys("권지수 바보");
				break;
			}
		}

		driver.switchTo().parentFrame();
		WebElement clickbtn = driver.findElement(By.className("button_write_task"));
		clickbtn.click();



		//		WebElement element = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("iframe"), 5)).get(5);
		//		element.

		//		System.out.println(
		//				wait.until(ExpectedConditions.numberOfElementsToBe(By.className("workseditor-content"), 1))
		//					.findElement(By.className("workseditor-content"))
		//		);


		//		workseditorContent.sendKeys("내용입력");




	}
}
