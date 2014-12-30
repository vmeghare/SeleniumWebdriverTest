package TestSelenium.webdriver.test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EasyjetTestcase extends BaseTestcase {
  private String baseUrl = "http://www.easyjet.com";


  @Test
  public void testEasyjetTestcase() throws Exception {
	EurostarCompetitorTestSuite.logger.info("-----------------------Starting easyjet.com ---------------------------");
	identifierString = "easyjet.com.";
    driver.get(baseUrl + "/EN/");
    driver.findElement(By.id("acDestinationAirport")).click();
    driver.findElement(By.id("acDestinationAirport")).clear();
    driver.findElement(By.id("acDestinationAirport")).sendKeys("Paris Charles de Gaulle CDG, France");
    driver.findElement(By.xpath("(//div[@id='acDestinationAirport_ddl']/ul/li[41])[2]")).click();
    driver.findElement(By.id("searchPodSubmitButton")).click();
    WebDriverWait wait = new WebDriverWait(driver, 20);
	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("selectFlightsDetails")));
	this.stats.markTime(identifierString+"outbound");
	this.reportPerformanceStats();
  }

}
