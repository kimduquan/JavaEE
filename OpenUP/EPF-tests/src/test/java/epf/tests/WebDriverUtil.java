/**
 * 
 */
package epf.tests;

import org.openqa.selenium.WebDriver;
import com.microsoft.edge.seleniumtools.EdgeDriver;
import epf.client.portlet.Portlet;
import epf.client.webapp.WebApp;
import epf.util.logging.Logging;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

/**
 * @author PC
 *
 */
public class WebDriverUtil {
	
	private static Logger LOGGER = Logging.getLogger(WebDriverUtil.class.getName());

	@Produces @ApplicationScoped
	public static WebDriver newWebDriver() {
		return new EdgeDriver();
	}
	
	public static void close(@Disposes WebDriver webDriver) {
		webDriver.quit();
	}
	
	@Produces @Named(WebApp.WEBAPP_URL)
	public static URL getDefaultURL() {
		URL url = null;
		try {
			url = new URL(System.getProperty(WebApp.WEBAPP_URL, ""));
		} 
		catch (MalformedURLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return url;
	}
	
	@Produces @Named(Portlet.PORTLET_URL)
	public static URL getPortletURL() {
		URL url = null;
		try {
			String temp = System.getProperty(Portlet.PORTLET_URL, "");
			if(temp.isEmpty()) {
				temp = "http://localhost:8080/pluto/portal/";
			}
			url = new URL(temp);
		} 
		catch (MalformedURLException e) {
			
		}
		return url;
	}
}
