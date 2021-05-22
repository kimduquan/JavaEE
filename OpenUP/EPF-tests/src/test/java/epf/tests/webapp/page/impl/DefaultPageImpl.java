/**
 * 
 */
package epf.tests.webapp.page.impl;

import java.net.URL;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import epf.tests.webapp.page.DefaultPage;

/**
 * @author PC
 *
 */
public class DefaultPageImpl implements DefaultPage {

	@Inject
	private WebDriver webDriver;
	
	@Inject @Named("WEB_APP_URL")
	private URL webAppUrl;
	
	@PostConstruct
	void navigateTo() {
		webDriver.navigate().to(webAppUrl);
	}

	@Override
	public WebElement linkWorkProducts() {
		return webDriver.findElement(By.tagName("a"));
	}

	@Override
	public WebElement linkTasks() {
		return webDriver.findElement(By.tagName("a"));
	}
}
