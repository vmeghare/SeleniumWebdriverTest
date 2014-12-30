package TestSelenium.webdriver.test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RyanairTest extends BaseTestcase{

	private static String baseUrl = "http://www.ryanair.com/";

  @Test
  public void testVoyagesTestcase() throws Exception {
	  	EurostarCompetitorTestSuite.logger.info("-----------------------Starting ryanair.com ---------------------------");
		identifierString = "ryanair.com.";
		driver.get(baseUrl);
		driver.findElement(By.name("toAirportIATA")).click();
		driver.findElement(By.cssSelector("div.airport-selector-item")).click();
		driver.findElement(By.cssSelector("button.flight-form_button.submit-button-validation.tabbed")).submit();
		driver.findElement(By.cssSelector("input.btn.btn-ok.active")).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ng-app")));
		this.stats.markTime(identifierString+"outbound");
		this.reportPerformanceStats();
  }

}
