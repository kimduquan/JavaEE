package epf.tests.portlet.persistence;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import epf.client.portlet.persistence.SchemaView;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class Schema implements SchemaView {
	
	private final WebDriver driver;
	
	@Inject
	public Schema(WebDriver driver) {
		this.driver = driver;
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
}
