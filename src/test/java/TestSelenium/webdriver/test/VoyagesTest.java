package TestSelenium.webdriver.test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VoyagesTest extends BaseTestcase{

	private static String baseUrl = "http://uk.voyages-sncf.com/";

  @Test
  public void testVoyagesTestcase() throws Exception {
	EurostarCompetitorTestSuite.logger.info("-----------------------Starting voyages-sncf.com ---------------------------");
	identifierString = "voyages-sncf.com.";
	driver.get(baseUrl + "/en/?redirect=yes");
    driver.findElement(By.id("train-origin")).clear();
    driver.findElement(By.id("train-origin")).sendKeys("lon");
    driver.findElement(By.cssSelector("li.ac_even")).click();
    driver.findElement(By.id("train-destination")).clear();
    driver.findElement(By.id("train-destination")).sendKeys("par");
    driver.findElement(By.xpath("//div[9]/ul/li/ul/li")).click();
    driver.findElement(By.id("train-travel-type-oneway")).click();
    driver.findElement(By.id("train-submit")).click();
    
    WebDriverWait wait = new WebDriverWait(driver, 20);
	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dvViewJourneyHeader")));
	
	this.stats.markTime(identifierString+"outbound");
	this.reportPerformanceStats();
  }

}
