/**
 * 
 */
package epf.tests.portlet.persistence;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
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
		Select select = new Select(driver.findElement(By.cssSelector(".schema.entities")));
		return select.getFirstSelectedOption().getText();
	}

	@Override
	public void setEntity(String entity) {
		Select select = new Select(driver.findElement(By.cssSelector(".schema.entities")));
		select.selectByVisibleText(entity);
	}

	public void navigateToPersistence() {
		driver.findElement(By.linkText("Persistence")).click();
	}
}
