package TestSelenium.webdriver.helper;

import TestSelenium.webdriver.helper.Timer;

import java.util.List;

/**
 * Stats reporter Interface.
 */
public interface StatsReporter {
  public void reportStats();
  public void setCollectedStats(List<Timer> collectedStats);
}
