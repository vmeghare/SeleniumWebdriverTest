package TestSelenium.webdriver.test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class EurostarCompetitorTestSuite {
	
	static final String LOG_PROPERTIES_FILE = "/log4j.properties";
	public static Logger logger = Logger.getLogger(EurostarCompetitorTestSuite.class);
	
	/*static{
		initializeLogger();
	}*/
	
	  public static Test suite() {
	    TestSuite suite = new TestSuite();
	    suite.addTestSuite(EurostarTest.class); //1
	    suite.addTestSuite(RyanairTest.class);//2
	    suite.addTestSuite(VoyagesTest.class);//3
	    suite.addTestSuite(BaTest.class);//4
	    suite.addTestSuite(VirginAtlanticTest.class);//5
	    return suite;
	  }

	  public static void main(String[] args) {
	    junit.textui.TestRunner.run(suite());
	  }
	  
	  private static void initializeLogger()
	  {
	    Properties logProperties = new Properties();
	 
	    try
	    {
	      // load our log4j properties / configuration file
	      logProperties.load(new FileInputStream(LOG_PROPERTIES_FILE));
	      PropertyConfigurator.configure(logProperties);
	      logger.info("Logging initialized.");
	    }
	    catch(IOException e)
	    {
	      throw new RuntimeException("Unable to load logging property " + LOG_PROPERTIES_FILE);
	    }
	  }
}