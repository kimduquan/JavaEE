/**
 * 
 */
package epf.tests.portlet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import epf.portlet.naming.Naming;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
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
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.valueOf(implicit)));
		}
		else {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		}
		
		final String pageLoad = System.getProperty("webdriver.timeouts.pageLoad");
		if(pageLoad != null) {
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.valueOf(pageLoad)));
		}
		
		final String script = System.getProperty("webdriver.timeouts.script");
		if(script != null) {
			driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(Long.valueOf(script)));
		}
		return driver;
	}
	
	public static void close(@Disposes WebDriver webDriver) {
		webDriver.quit();
	}
	
	@Produces @Named(Naming.PORTLET_URL)
	public static URL getPortletURL() {
		URL url = null;
		try {
			url = new URL(System.getProperty(Naming.PORTLET_URL, "http://localhost:8282/pluto/portal/"));
		} 
		catch (MalformedURLException e) {
			
		}
		return url;
	}
}
