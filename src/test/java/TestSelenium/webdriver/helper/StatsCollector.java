package TestSelenium.webdriver.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Simple performance stats collection utility.
 */
//@Component
public final class StatsCollector {
  private List<Timer> eventList;
  private List<Timer> tempTimeCollector;
  private Iterator<Timer> iterator;
  private Timer timer;
  private WebDriver webDriver;

  // Performance Variables.
  // NOTE: All these should be called after a page request.
  private Long loadEventEnd;
  private Long navigationStart;
  private Long domainLookupStart;
  private Long domainLookupEnd;
  private Long connectEnd;
  private Long connectStart;
  private Long requestStart;
  private Long fetchStart;
  private Long responseEnd;
  private Long domContentLoadedEventStart;
  private Long domComplete;

  // Constants.
  private static final String DNS_TIMER = "dns";
  private static final String CONNECTION_TIMER = "connection";
  private static final String REQUEST_TIMER = "request";
  private static final String FETCH_TIMER = "fetch";
  private static final String DOM_LOAD_TIMER = "dom.load";
  private static final String DOM_READY_TIMER = "dom.ready";
  private static final String USER_TIMER = "";

  // Logger.
  private static Logger logger;

  /**
   * Constructor.
   */
  public StatsCollector(WebDriver browserWebDriver) {
    this.eventList = new ArrayList<Timer>();
    this.webDriver = browserWebDriver;
    logger = Logger.getLogger(StatsCollector.class);
  }

  /**
   * String that Identifies the Graphite stats.
   *
   * @param identifierString
   */
  public synchronized void markTime(String identifierString) {
    // If nothing has set create a new timer.
    if (this.searchTimer(identifierString, false) != null) {
      throw new RuntimeException("A timer marker with identifier " + identifierString + " exist.");
    }

    // We do not want to record the batch if one has incorrect values such as negative values
    // because the browser JS performance object gas incorrect stats. Therefore we try and collect the
    // stats first and then record them for reporting.
    try {
      this.tempTimeCollector = new ArrayList<Timer>();
      this.tempTimeCollector.add(this.getTimer(identifierString, StatsCollector.DNS_TIMER));
      this.tempTimeCollector.add(this.getTimer(identifierString, StatsCollector.CONNECTION_TIMER));
      this.tempTimeCollector.add(this.getTimer(identifierString, StatsCollector.REQUEST_TIMER));
      this.tempTimeCollector.add(this.getTimer(identifierString, StatsCollector.FETCH_TIMER));
      this.tempTimeCollector.add(this.getTimer(identifierString, StatsCollector.DOM_LOAD_TIMER));
      this.tempTimeCollector.add(this.getTimer(identifierString, StatsCollector.DOM_READY_TIMER));
      this.tempTimeCollector.add(this.getTimer(identifierString, StatsCollector.USER_TIMER));

      // At this point we have all the stats. Loop through and add the timing to the main collector.
      this.iterator = this.tempTimeCollector.iterator();

      while (this.iterator.hasNext()) {
        this.eventList.add((Timer) this.iterator.next());
      }

      // Clear the temp collector.
      this.tempTimeCollector.clear();
    }
    catch (Exception exp) {
      logger.error("ERROR : " + exp.getMessage());
    }
  }

  /**
   * Add a timer to the collection.
   * @param baseIdentifier
   * @param fragmentIdentifier
   */
  private Timer getTimer(String baseIdentifier, String fragmentIdentifier) {
    this.timer = new Timer();

    switch (fragmentIdentifier.trim()) {
      case StatsCollector.DNS_TIMER:
        this.timer.setIdentifier(baseIdentifier + "." + StatsCollector.DNS_TIMER);
        this.timer.setElapsedTime(this.getDNSTime());
        break;

      case StatsCollector.CONNECTION_TIMER:
        this.timer.setIdentifier(baseIdentifier + "." + StatsCollector.CONNECTION_TIMER);
        this.timer.setElapsedTime(this.getConnectionTime());
        break;

      case StatsCollector.REQUEST_TIMER:
        this.timer.setIdentifier(baseIdentifier + "." + StatsCollector.REQUEST_TIMER);
        this.timer.setElapsedTime(this.getRequestTime());
        break;

      case StatsCollector.FETCH_TIMER:
        this.timer.setIdentifier(baseIdentifier + "." + StatsCollector.FETCH_TIMER);
        this.timer.setElapsedTime(this.getFetchTime());
        break;

      case StatsCollector.DOM_LOAD_TIMER:
        this.timer.setIdentifier(baseIdentifier + "." + StatsCollector.DOM_LOAD_TIMER);
        this.timer.setElapsedTime(this.getDOMLoadTime());
        break;

      case StatsCollector.DOM_READY_TIMER:
        this.timer.setIdentifier(baseIdentifier + "." + StatsCollector.DOM_READY_TIMER);
        this.timer.setElapsedTime(this.getDOMCompleteTime());
        break;

      case StatsCollector.USER_TIMER:
        this.timer.setIdentifier(baseIdentifier);
        this.timer.setElapsedTime(this.getUserTime());
        this.timer.setAggregatedResponseTime(this.getUserTime());
        break;

      default:
        throw new RuntimeException("Invalid fragment identifier : " + fragmentIdentifier);
    }

    return this.timer;
  }

  /**
   * Search the array list on a given identifier.
   * @param identifierString
   *   Timer object identifier string
   *
   * @return
   *   Return the timer object if found.
   */
  public synchronized Timer searchTimer(String identifierString, boolean removeItem) {
    // Create the iterator and traverse through.
    this.iterator = this.eventList.iterator();

    while (this.iterator.hasNext()) {
      // Get the timer.
      this.timer = (Timer) this.iterator.next();
      if (this.timer.getIdentifier().trim().equals(identifierString.trim())) {
        if (removeItem) {
          // Remove the item.
          this.iterator.remove();
        }
        return this.timer;
      }
    }

    return null;
  }

  /**
   * Remove any base identifier related times just in case if there is a failure.
   * @param baseIdentifierString
   */
  public void removeTimerData(String baseIdentifierString) {
    // Create the iterator and traverse through.
    this.iterator = this.eventList.iterator();
    // Get the last character of the string.
    String baseIdentifierStringModified = baseIdentifierString.trim();
    char lastCharacter = baseIdentifierStringModified.charAt((baseIdentifierStringModified.length() - 1));

    // Append a full stop so we can search the proper base.
    if (lastCharacter != '.') {
      baseIdentifierStringModified = baseIdentifierStringModified + '.';
    }

    while (this.iterator.hasNext()) {
      // Get the timer.
      this.timer = (Timer) this.iterator.next();

      if (this.timer.getIdentifier().trim().equals(baseIdentifierString.trim())) {
        this.iterator.remove();
      }
      else if (this.timer.getIdentifier().trim().contains(baseIdentifierStringModified)) {
        this.iterator.remove();
      }
    }
  }

  /**
   * Get the collected stats.
   * @return
   */
  public synchronized List getCollectedStats() {
    return this.eventList;
  }

  public synchronized List<StatsData> transformStats() {
	  List<StatsData> statsDataList = new ArrayList<StatsData>();
	  Iterator<Timer> duplIterator = this.eventList.iterator();
	    while (duplIterator.hasNext()) {
	    	StatsData data = new StatsData();
	    	Timer reportTimer = (Timer) duplIterator.next();
	    	if(reportTimer.getAggregatedResponseTime() > 0) {
	    		data.setIdentifier(reportTimer.getIdentifier());
		    	data.setResponseTime(reportTimer.getAggregatedResponseTime());
		    	statsDataList.add(data);
	    	}
	    }
	    return statsDataList;
  }
  
  /**
   * Clear the array list which holds the stats.
   */
  public synchronized void clearCollectedStats() {
    this.eventList.clear();
  }

  /**
   * Total page load delay experienced by the user.
   *
   * Note: The window.performance.timing.loadEventEnd is HTML 5 and only work with limited set of browsers.
   * https://developer.mozilla.org/en-US/docs/Web/API/PerformanceTiming
   * https://developer.mozilla.org/en-US/docs/Navigation_timing
   *
   * @return
   */
  public long getUserTime() {
    this.loadEventEnd = (Long) ((JavascriptExecutor) this.webDriver).executeScript("return window.performance.timing.loadEventEnd");
    this.navigationStart = (Long) ((JavascriptExecutor) this.webDriver).executeScript("return window.performance.timing.navigationStart");

    if ((this.loadEventEnd - this.navigationStart) >= 0) {
      return this.loadEventEnd - this.navigationStart;
    }
    else {
      throw new RuntimeException("Invalid User Time time recorded " + (this.loadEventEnd - this.navigationStart));
    }
  }

  /**
   * Times taken to perform DNS lookup
   *
   * Note: The window.performance.timing.loadEventEnd is HTML 5 and only work with limited set of browsers.
   * https://developer.mozilla.org/en-US/docs/Web/API/PerformanceTiming
   * https://developer.mozilla.org/en-US/docs/Navigation_timing
   *
   * @return
   */
  public long getDNSTime() {
    this.domainLookupEnd = (Long) ((JavascriptExecutor) this.webDriver).executeScript("return window.performance.timing.domainLookupEnd");
    this.domainLookupStart = (Long) ((JavascriptExecutor) this.webDriver).executeScript("return window.performance.timing.domainLookupStart");

    if ((this.domainLookupEnd - this.domainLookupStart) >= 0) {
      return this.domainLookupEnd - this.domainLookupStart;
    }
    else {
      throw new RuntimeException("Invalid DNS Time time recorded " + (this.domainLookupEnd - this.domainLookupStart));
    }
  }

  /**
   * Time to connect to the server.
   *
   * Note: The window.performance.timing.loadEventEnd is HTML 5 and only work with limited set of browsers.
   * https://developer.mozilla.org/en-US/docs/Web/API/PerformanceTiming
   * https://developer.mozilla.org/en-US/docs/Navigation_timing
   *
   * @return
   */
  public long getConnectionTime() {
    this.connectEnd = (Long) ((JavascriptExecutor) this.webDriver).executeScript("return window.performance.timing.connectEnd");
    this.connectStart = (Long) ((JavascriptExecutor) this.webDriver).executeScript("return window.performance.timing.connectStart");

    if ((this.connectEnd - this.connectStart) >= 0) {
      return this.connectEnd - this.connectStart;
    }
    else {
      throw new RuntimeException("Invalid Connection Time time recorded " + (this.connectEnd - this.connectStart));
    }
  }

  /**
   * The total time taken to send a request to the server and receive the response.
   *
   * Note: The window.performance.timing.loadEventEnd is HTML 5 and only work with limited set of browsers.
   * https://developer.mozilla.org/en-US/docs/Web/API/PerformanceTiming
   * https://developer.mozilla.org/en-US/docs/Navigation_timing
   *
   * @return
   */
  public long getRequestTime() {
    this.requestStart = (Long) ((JavascriptExecutor) this.webDriver).executeScript("return window.performance.timing.requestStart");
    this.responseEnd = (Long) ((JavascriptExecutor) this.webDriver).executeScript("return window.performance.timing.responseEnd");

    if ((this.responseEnd - this.requestStart) >= 0) {
      return this.responseEnd - this.requestStart;
    }
    else {
      throw new RuntimeException("Invalid Request Time time recorded " + (this.responseEnd - this.requestStart));
    }
  }

  /**
   * Get DOM Load time before any scripts execute.
   *
   * Note: The window.performance.timing.loadEventEnd is HTML 5 and only work with limited set of browsers.
   * https://developer.mozilla.org/en-US/docs/Web/API/PerformanceTiming
   * https://developer.mozilla.org/en-US/docs/Navigation_timing
   *
   * @return
   */
  public long getDOMLoadTime() {
    this.navigationStart = (Long) ((JavascriptExecutor) this.webDriver).executeScript("return window.performance.timing.navigationStart");
    this.domContentLoadedEventStart = (Long) ((JavascriptExecutor) this.webDriver).executeScript("return window.performance.timing.domContentLoadedEventStart");

    if ((this.domContentLoadedEventStart - this.navigationStart) >= 0) {
      return this.domContentLoadedEventStart - this.navigationStart;
    }
    else {
      throw new RuntimeException("Invalid DOM Complete Time time recorded " + (this.domContentLoadedEventStart - this.navigationStart));
    }
  }

  /**
   * Get DOM Complete time. This fires the documentReady state. All the scripts has been loaded at this point.
   *
   * Note: The window.performance.timing.loadEventEnd is HTML 5 and only work with limited set of browsers.
   * https://developer.mozilla.org/en-US/docs/Web/API/PerformanceTiming
   * https://developer.mozilla.org/en-US/docs/Navigation_timing
   *
   * @return
   */
  public long getDOMCompleteTime() {
    this.navigationStart = (Long) ((JavascriptExecutor) this.webDriver).executeScript("return window.performance.timing.navigationStart");
    this.domComplete = (Long) ((JavascriptExecutor) this.webDriver).executeScript("return window.performance.timing.domComplete");

    if ((this.domComplete - this.navigationStart) >= 0) {
      return this.domComplete - this.navigationStart;
    }
    else {
      throw new RuntimeException("Invalid DOM Complete Time time recorded " + (this.domComplete - this.navigationStart));
    }
  }

  /**
   * Finally, the total time to complete the document fetch (including accessing any caches, etc.)
   *
   * Note: The window.performance.timing.loadEventEnd is HTML 5 and only work with limited set of browsers.
   * https://developer.mozilla.org/en-US/docs/Web/API/PerformanceTiming
   * https://developer.mozilla.org/en-US/docs/Navigation_timing
   *
   * @return
   */
  public long getFetchTime() {
    this.fetchStart = (Long) ((JavascriptExecutor) this.webDriver).executeScript("return window.performance.timing.fetchStart");
    this.responseEnd = (Long) ((JavascriptExecutor) this.webDriver).executeScript("return window.performance.timing.responseEnd");

    if ((this.responseEnd - this.fetchStart) >= 0) {
      return this.responseEnd - this.fetchStart;
    }
    else {
      throw new RuntimeException("Invalid fetch time recorded " + (this.responseEnd - this.fetchStart));
    }
  }

  /**
   * Ajax Call Start.
   *   Will start recording time against a given string identifier.
   *
   * @param callName
   *   Ajax caller name identifier.
   */
  public void ajaxCallStart(String callName) {
    // Create the timer object and set the current time in milliseconds.
    // The idea is to grab this time and set the difference when you call End
    this.timer = new Timer();
    this.timer.setIdentifier(callName.trim());
    this.timer.setAjaxStartTime(System.currentTimeMillis());
    this.eventList.add(this.timer);
  }

  /**
   * Ajax Call End.
   *   Will stop recording time against a given string identifier.
   *
   * @param callName
   *   Ajax caller name identifier.
   */
  public void ajaxCallEnd(String callName) {
    // Search the timer.
    Timer ajaxTimer = this.searchTimer(callName.trim(), true);

    if (ajaxTimer != null) {
      ajaxTimer.setElapsedTime(System.currentTimeMillis() - ajaxTimer.getAjaxStartTime());
      this.eventList.add(ajaxTimer);
    }
    else {
      logger.error("No timer start object found with identifier : " + callName);
    }
  }

  /**
   * Get the web driver.
   * @return
   */
  public WebDriver getWebDriver() {
    return this.webDriver;
  }
}
