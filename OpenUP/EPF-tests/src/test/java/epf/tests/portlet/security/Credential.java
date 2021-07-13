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
public class Credential {

	@Inject
	WebDriver driver;
	
	public void setPassword(String password) {
		driver.findElement(By.cssSelector(".security.credential.password")).sendKeys(password);
	}
	
	public void update() {
		driver.findElement(By.cssSelector(".security.update")).click();
	}
}
