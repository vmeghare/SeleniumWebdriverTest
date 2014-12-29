package TestSelenium.webdriver.test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EasyjetTest extends BaseTestcase {
  private String baseUrl = "http://www.easyjet.com/";


  @Test
  public void testEasyjetTestcase() throws Exception {
	EurostarCompetitorTestSuite.logger.info("-----------------------Starting easyjet.com ---------------------------");
	identifierString = "easyjet.com.";
    driver.get(baseUrl + "/en/");
    driver.findElement(By.id("acDestinationAirportShowAll")).click();
    driver.findElement(By.cssSelector("li.acl_odd.acl_visible")).click();
    driver.findElement(By.id("searchPodSubmitButton")).click();
    WebDriverWait wait = new WebDriverWait(driver, 20);
	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("selectFlightsDetails")));
	this.stats.markTime(identifierString+"outbound");
  }

}
