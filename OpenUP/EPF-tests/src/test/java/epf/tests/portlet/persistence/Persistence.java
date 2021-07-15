/**
 * 
 */
package epf.tests.portlet.persistence;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import epf.tests.portlet.security.Security;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class Persistence {
	
	private final WebDriver driver;
	private final Security security;
	
	@Inject
	public Persistence(WebDriver driver, Security security) {
		this.driver = driver;
		this.security = security;
	}
	
	@PostConstruct
	void navigate() {
		security.login();
	}

	public void navigateToPersistence() {
		driver.findElement(By.linkText("Persistence")).click();
	}
	
	public void navigateToQuery() {
		driver.findElement(By.linkText("Query")).click();
	}
}
