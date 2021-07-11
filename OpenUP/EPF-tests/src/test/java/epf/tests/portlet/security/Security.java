/**
 * 
 */
package epf.tests.portlet.security;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import epf.tests.portlet.View;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class Security {

	@Inject
	View view;
	
	@Inject
	WebDriver webDriver;
	
	@PostConstruct
	void navigate() {
		view.navigateToSecurity();
	}
	
	public void setUsername(String username) {
		webDriver.findElement(By.cssSelector(".security.credential.caller")).sendKeys(username);
	}
	
	public void setPassword(String password) {
		webDriver.findElement(By.cssSelector(".security.credential.password")).sendKeys(password);
	}
	
	public void login() {
		webDriver.findElement(By.cssSelector(".security.login")).click();
	}
	
	public void logout() {
		webDriver.findElement(By.cssSelector(".security.principal.logout")).click();
	}
}
