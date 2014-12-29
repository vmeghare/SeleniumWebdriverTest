package TestSelenium.webdriver.helper;

import TestSelenium.webdriver.helper.Timer;

import java.util.Iterator;
import java.util.List;

/**
 * Console Reporter Implementation.
 */
public class ConsoleReporter implements StatsReporter {
  private List<Timer> collectedStats;

  /**
   * Implementing reportStats().
   */
  public void reportStats() {
    Iterator statsIterator = this.collectedStats.iterator();

    while (statsIterator.hasNext()) {
      Timer reportTimer = (Timer) statsIterator.next();
      System.out.println("CONSOLE REPORTER : " + reportTimer.getIdentifier() + ": " + reportTimer.getElapsedTime() + "ms");
    }
  }

  /**
   * Implementing collectedStats.
   * @param collectedStats
   */
  public void setCollectedStats(List<Timer> collectedStats) {
    this.collectedStats = collectedStats;
  }
}
