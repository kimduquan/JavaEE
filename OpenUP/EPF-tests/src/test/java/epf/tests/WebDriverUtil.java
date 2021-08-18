/**
 * 
 */
package epf.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import epf.client.portlet.Portlet;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

/**
 * @author PC
 *
 */
public class WebDriverUtil {
	
	/**
	 * @param options
	 * @return
	 */
	@Produces @ApplicationScoped
	public static WebDriver newWebDriver() {
		final FirefoxOptions options = new FirefoxOptions();
		
		final String headless = System.getProperty("webdriver.firefox.headless");
		if(headless != null) {
			options.setHeadless(Boolean.valueOf(headless));
		}
		
		final String webdriverDriver = System.getProperty("webdriver.gecko.driver");
		if(webdriverDriver == null) {
			System.setProperty("webdriver.gecko.driver", "./geckodriver.exe");
		}
		final WebDriver driver = new FirefoxDriver(options);
		
		final String implicit = System.getProperty("webdriver.timeouts.implicit");
		if(implicit != null) {
			driver.manage().timeouts().implicitlyWait(Long.valueOf(implicit), TimeUnit.SECONDS);
		}
		else {
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
		
		final String pageLoad = System.getProperty("webdriver.timeouts.pageLoad");
		if(pageLoad != null) {
			driver.manage().timeouts()
			.pageLoadTimeout(Long.valueOf(pageLoad), TimeUnit.SECONDS);
		}
		
		final String script = System.getProperty("webdriver.timeouts.script");
		if(script != null) {
			driver.manage().timeouts()
			.setScriptTimeout(Long.valueOf(script), TimeUnit.SECONDS);
		}
		return driver;
	}
	
	public static void close(@Disposes WebDriver webDriver) {
		webDriver.quit();
	}
	
	@Produces @Named(Portlet.PORTLET_URL)
	public static URL getPortletURL() {
		URL url = null;
		try {
			url = new URL(System.getProperty(Portlet.PORTLET_URL, "http://localhost:8080/pluto/portal/"));
		} 
		catch (MalformedURLException e) {
			
		}
		return url;
	}
}
