package TestSelenium.webdriver.helper;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import TestSelenium.webdriver.graphite.SimpleGraphiteClient;

/**
 * Graphite reporter Implementation.
 */
public class GraphiteReporter implements StatsReporter {
  private List<Timer> collectedStats;
  private SimpleGraphiteClient simpleGraphiteClient;
  private static Logger logger;

  /**
   * Constructor
   */
  public GraphiteReporter(SimpleGraphiteClient simpleGraphiteClient) {
    this.simpleGraphiteClient = simpleGraphiteClient;
    logger = Logger.getLogger(GraphiteReporter.class);
  }

  /**
   * Implementing reportStats().
   */
  public void reportStats() {
    Iterator statsIterator = this.collectedStats.iterator();
    logger.info("Sending data to Graphite");

    while (statsIterator.hasNext()) {
      try {
        Timer reportTimer = (Timer) statsIterator.next();
        this.simpleGraphiteClient.sendMetric(reportTimer.getIdentifier(), reportTimer.getElapsedTime());
        logger.info("Sending " + reportTimer.getIdentifier() + ": " + reportTimer.getElapsedTime() + "ms to Graphite");
      }
      catch(RuntimeException exception) {
        logger.error(exception.getMessage());
      }
    }

    logger.info("Sending data completed.");
  }

  /**
   * Implementing collectedStats.
   * @param collectedStats
   */
  public void setCollectedStats(List<Timer> collectedStats) {
    this.collectedStats = collectedStats;
  }
}
