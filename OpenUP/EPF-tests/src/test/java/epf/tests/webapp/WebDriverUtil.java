/**
 * 
 */
package epf.tests.webapp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import epf.client.config.ConfigNames;
import epf.util.logging.Logging;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 * @author PC
 *
 */
public class WebDriverUtil {
	
	private static Logger LOGGER = Logging.getLogger(WebDriverUtil.class.getName());

	@Produces
	public static WebDriver newWebDriver() {
		return new ChromeDriver();
	}
	
	public static void close(@Disposes WebDriver webDriver) {
		webDriver.quit();
	}
	
	@Produces @Named("WEB_APP_URL")
	public static URL getDefaultURL() {
		URL url = null;
		try {
			url = new URL(System.getProperty(ConfigNames.WEB_APP_URL, ""));
		} 
		catch (MalformedURLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return url;
	}
}
