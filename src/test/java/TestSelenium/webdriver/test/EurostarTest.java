package TestSelenium.webdriver.test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EurostarTest extends BaseTestcase{

	private static String baseUrl = "http://www.eurostar.com/uk-en";

  @Test
  public void testEurostarTestcase() throws Exception {
	EurostarCompetitorTestSuite.logger.info("-----------------------Starting eurostar.com ---------------------------");
	driver.get(baseUrl);
	identifierString = "eurostar.com.";
	driver.findElement(By.id("dialog-element")).click();
//	this.stats.markTime(identifierString+"homepage");
	WebDriverWait wait = new WebDriverWait(driver, 20);
	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("main-content")));
	this.stats.markTime(identifierString+"outbound");
	this.reportPerformanceStats();
  }

}
