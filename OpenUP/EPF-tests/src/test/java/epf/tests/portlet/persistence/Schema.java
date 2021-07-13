/**
 * 
 */
package epf.tests.portlet.persistence;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import epf.client.portlet.persistence.SchemaView;
import epf.tests.portlet.security.SecurityUtil;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class Schema implements SchemaView {
	
	WebDriver driver;
	SecurityUtil securityUtil;
	
	@Inject
	public Schema(WebDriver driver, SecurityUtil securityUtil) {
		this.driver = driver;
		this.securityUtil = securityUtil;
	}
	
	@PostConstruct
	void navigate() {
		securityUtil.login();
	}

	@Override
	public String getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEntity(String entity) {
		// TODO Auto-generated method stub
		
	}

	public void navigateToPersistence() {
		driver.findElement(By.linkText("Persistence")).click();
	}
}
