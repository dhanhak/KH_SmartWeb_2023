
import java.time.Duration;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class quiz_01 {
	public static void main(String[] args) throws Exception {
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		driver.manage().window().setSize(new Dimension(1700, 900));
		driver.get("https://www.monamhee.com/");

		driver.findElement(By.linkText("로그인 위젯 문구")).click();
		driver.findElement(By.name("uid")).sendKeys(Statics.monamhee_ID);
		driver.findElement(By.name("passwd")).sendKeys(Statics.monamhee_PW);

		driver.findElement(By.className("btn-primary")).click();

		Thread.sleep(500);
		driver.findElement(By.id("inline_menu_alarm_badge")).click();
		Thread.sleep(500);
		driver.findElement(By.linkText("shop")).click();
		Thread.sleep(500);
		driver.get("https://www.monamhee.com/grocerystore/?idx=78");
		Thread.sleep(500);
		driver.findElement(By.className("bt-plus")).click();
		driver.findElement(By.linkText("구매하기")).click();

		Thread.sleep(500);
		driver.findElement(By.name("deliv_name")).sendKeys("권지수");
		Thread.sleep(500);

		List<WebElement> deliCall = driver.findElements(By.name("deliv_call"));
		deliCall.get(0).sendKeys("010-5213-4666");
		Thread.sleep(500);
		driver.findElement(By.linkText("주소찾기")).click();

		WebElement outerFrame = driver.findElement(By.cssSelector("#__daum__layer_1 > iframe"));

		WebElement innerFrame = driver.switchTo().frame(outerFrame).findElement(By.tagName("iframe"));

		driver.switchTo().frame(innerFrame).findElement(By.id("region_name")).sendKeys("청계그레이츠");
		Thread.sleep(500);
		driver.findElement(By.className("btn_search")).click();
//		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("link_post"), 2));
		driver.findElements(By.className("link_post")).get(2).click();
		Thread.sleep(500);
		driver.findElement(By.name("shop_form[f202303099e5d85055beb9]")).click();
		driver.findElement(By.id("paymentAllCheck")).click();
		driver.findElement(By.linkText("결제하기")).click();


	}



}


//로그인 버튼 클래스 이름 nav-btn p0-sm d-sm-b body-4 u-bold ml2-sm mr2-sm