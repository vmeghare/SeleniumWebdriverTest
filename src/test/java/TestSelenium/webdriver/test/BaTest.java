package TestSelenium.webdriver.test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaTest extends BaseTestcase{
  
  private static String baseUrl="http://www.britishairways.com/travel/home/public/en_gb";

 
  @Test
  public void testBaTestcase() throws Exception {
	EurostarCompetitorTestSuite.logger.info("-----------------------Starting britishairways.com ---------------------------");
    driver.get(baseUrl + "/travel/home/public/en_gb");
    identifierString = "britishairways.com.";
    driver.findElement(By.cssSelector("a.arrow")).click();
    driver.findElement(By.id("planTripFlightDestination")).clear();
    driver.findElement(By.id("planTripFlightDestination")).sendKeys("Par");
    driver.findElement(By.id("PAR")).click();
    driver.findElement(By.id("journeyTypeOW")).click();
    driver.findElement(By.id("journeyTypeOW")).click();
    driver.findElement(By.cssSelector("span.calendarIcon.depDate")).click();
    driver.findElement(By.linkText("30")).click();
    driver.findElement(By.id("flightSearchButton")).click();
    
//    long before = System.currentTimeMillis();
    
//    EurostarTestSuite.logger.info("++++++++++++++ Captured before time ++++++++++++++");
    
    WebDriverWait wait = new WebDriverWait(driver, 20);
	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("t-page")));
    
    
    /*if (waitForExchangeFieldsToLoad("t-page") == 1) {
    	long after = System.currentTimeMillis();
        
        EurostarTestSuite.logger.info("Total Time taken ::" + (after-before) + " ms");
        
    }*/
	
	this.stats.markTime(identifierString+"outbound");
	this.reportPerformanceStats();

  }
  
}
