package TestSelenium.webdriver.test;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import TestSelenium.webdriver.graphite.SimpleGraphiteClient;
import TestSelenium.webdriver.helper.GraphiteReporter;
import TestSelenium.webdriver.helper.StatsCollector;
import TestSelenium.webdriver.helper.StatsReporter;

public class BaseTestcase extends TestCase {
	
	protected WebDriver driver;
	protected StatsCollector stats;
	protected StatsReporter statsReporter;
	protected String identifierString;
	
	private int minute = 0;
	private int timeOut = 2;
	private StringBuffer verificationErrors = new StringBuffer();
	
	
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		stats = new StatsCollector(driver);
		statsReporter = new GraphiteReporter(new SimpleGraphiteClient("10.91.80.132", 2003));
	}
	
	public int waitForExchangeFieldsToLoad(String pageToBeLoad) {
		for (this.minute = 0;; minute++) {
			if (minute >= timeOut)
				if (isElementPresent(By.id(pageToBeLoad)))
					break;
		}
		return 1;
	}

	public boolean isElementPresent(By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			EurostarCompetitorTestSuite.logger
					.error("ER-3: Cannot find specificed element:" + e);
			return false;
		}
	}

	public void reportPerformanceStats() {
		this.statsReporter.setCollectedStats(this.stats.getCollectedStats());
		this.statsReporter.reportStats();
	}

	@After
	public void tearDown() throws Exception {
		this.stats.removeTimerData(identifierString);
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}
