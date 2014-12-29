package TestSelenium.webdriver.helper;

import org.apache.log4j.Logger;

/**
 * Timer class that holds performance timing information.
 */
public class Timer {
  private long ajaxStartTime;
  private long elapsedTime;
  private String identifier;
  private static Logger logger;

  /**
   * Constructor.
   */
  public Timer() {
    logger = Logger.getLogger(Timer.class);
  }

  /**
   * Get identifier.
   * @return
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Set Identifier.
   *
   * @param identifier
   */
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  /**
   * Set Elapsed Time.
   *
   * @param elapsedTime
   * @return
   */
  public void setElapsedTime(long elapsedTime) {
    this.elapsedTime = elapsedTime;
    // Just for notification purposes.
    logger.info(this.getIdentifier() + ": " + this.elapsedTime + "ms");
  }

  /**
   * Get the elapsed time.
   *
   * @return
   */
  public int getElapsedTime() {
    if (this.elapsedTime >= 0) {
      return Timer.safeLongToInt(this.elapsedTime);
    }
    else {
      // Throw and exception if start time is greater than end time.
      throw new RuntimeException("End time not recorded properly. Start time : " + this.elapsedTime);
    }
  }

  /**
   * Get Ajax Start Time.
   * @return
   */
  public long getAjaxStartTime() {
    return ajaxStartTime;
  }

  /**
   * Set Ajax Start Time.
   * @param ajaxStartTime
   */
  public void setAjaxStartTime(long ajaxStartTime) {
    this.ajaxStartTime = ajaxStartTime;
  }

  /**
   * Safely convert long to int.
   * @param l
   * @return
   */
  private static int safeLongToInt(long l) {
    if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
      throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
    }
    return (int) l;
  }
}
