package epf.tests.webapp;

import java.net.URL;
import org.openqa.selenium.remote.RemoteWebDriver;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;

public abstract class Page {
	
	protected final RemoteWebDriver driver;
	protected final URL webappUrl;

	public Page(RemoteWebDriver driver) throws Exception {
		this.driver = driver;
		webappUrl = ConfigUtil.getURL(Naming.WebApp.WEB_APP_URL);
	}
	
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}
	
	public String getTitle() {
		return driver.getTitle();
	}
	
	public boolean isComplete() {
		return (Boolean)driver.executeScript("return document.readyState == \"complete\";");
	}
	
	public abstract void navigateTo();
}
