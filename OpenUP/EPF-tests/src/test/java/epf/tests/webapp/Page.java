package epf.tests.webapp;

import org.openqa.selenium.remote.RemoteWebDriver;

public class Page {
	
	protected final RemoteWebDriver driver;

	public Page(RemoteWebDriver driver) {
		this.driver = driver;
	}
	
	public RemoteWebDriver getDriver() {
		return driver;
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
}
