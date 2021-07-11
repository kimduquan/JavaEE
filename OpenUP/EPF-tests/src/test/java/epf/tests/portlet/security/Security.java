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
		view.setUsername("pluto");
		view.setPassword("pluto");
		view.login();
		webDriver.findElement(By.linkText("Security")).click();
	}
	
	public void setUsername(String username) {
		
	}
	
	public void setPassword(String password) {
		
	}
	
	public void login() {
		
	}
}
