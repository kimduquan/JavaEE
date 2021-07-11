/**
 * 
 */
package epf.tests.portlet.security;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class Principal {
	
	@Inject
	WebDriver driver;
	
	public String getName() {
		return driver.findElement(By.cssSelector(".security.session.token.name")).getText();
	}
	
	public void navigateToSetPassword() {
		driver.findElement(By.cssSelector(".security.update")).click();
	}
	
	public void logout() {
		driver.findElement(By.cssSelector(".security.principal.logout")).click();
	}
}
