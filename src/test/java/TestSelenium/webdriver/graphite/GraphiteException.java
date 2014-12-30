/**
 * NOTE: The classes has been taken out from https://github.com/zanox/simplegraphiteclient
 * Has been copied because the maven distribution does not exist anymore.
 */

package TestSelenium.webdriver.graphite;

/**
 * Exception that indicates problems when writing to graphite.
 *
 * @author Helmut Zechmann
 *
 */
public class GraphiteException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public GraphiteException(String message) {
    super(message);
  }

  public GraphiteException(String message, Throwable cause) {
    super(message, cause);
  }
}
