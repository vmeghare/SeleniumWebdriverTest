package TestSelenium.webdriver.test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VirginAtlanticTest extends BaseTestcase {
	
	private String baseUrl = "http://www.virgin-atlantic.com/us/en.html";

	@Test
	public void testVirginAtlanticTestcase() throws Exception {
		EurostarCompetitorTestSuite.logger.info("-----------------------Starting virgin-atlantic.com ---------------------------");
		identifierString = "virgin-atlantic.com.";
		driver.get(baseUrl);
		driver.findElement(By.id("flightSearchArrivalField")).clear();
		driver.findElement(By.id("flightSearchArrivalField")).sendKeys(
				"London, (LON) GB");
		driver.findElement(By.id("findFlights")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("container")));

		this.stats.markTime(identifierString+"outbound");
		this.reportPerformanceStats();
		
		/*long before = System.currentTimeMillis();

		EurostarTestSuite.logger.info("++++++++++++++ Captured before time ++++++++++++++");

		if (waitForExchangeFieldsToLoad("container") == 1) {
			long after = System.currentTimeMillis();

			EurostarTestSuite.logger.info("Total Time taken ::" + (after - before) + " ms");

			driver.close();
		}*/
	}
	
}
