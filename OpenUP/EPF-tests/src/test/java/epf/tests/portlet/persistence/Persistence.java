/**
 * 
 */
package epf.tests.portlet.persistence;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import epf.tests.portlet.security.SecurityUtil;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class Persistence {
	
	WebDriver driver;
	SecurityUtil securityUtil;
	
	@Inject
	public Persistence(WebDriver driver, SecurityUtil securityUtil) {
		this.driver = driver;
		this.securityUtil = securityUtil;
	}
	
	@PostConstruct
	void navigate() {
		securityUtil.login();
	}

	public void navigateToPersistence() {
		driver.findElement(By.linkText("Persistence")).click();
	}
}
