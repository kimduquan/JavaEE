/**
 * 
 */
package epf.tests.portlet.persistence;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import epf.client.portlet.persistence.PersistenceView;
import epf.tests.portlet.security.Security;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class Persistence implements PersistenceView {
	
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
		driver.findElement(By.cssSelector(".persistence.query")).click();
	}
	
	public void navigateToPersist() {
		driver.findElement(By.cssSelector(".persistence.entity.persist")).click();
	}
	
	public void navigateToSearch() {
		driver.findElement(By.cssSelector(".persistence.search")).click();
	}

	@Override
	public String merge(Object entity) {
		int index = indexOf(entity);
		driver.findElements(By.cssSelector(".persistence.entity.merge")).get(index).click();
		return "";
	}

	@Override
	public void remove(Object entity) throws Exception {
		int index = indexOf(entity);
		driver.findElements(By.cssSelector(".persistence.entity.remove")).get(index).click();
	}

	@Override
	public int indexOf(Object entity) {
		List<WebElement> elements = driver.findElements(By.cssSelector("label.persistence.query.entity.id.name"));
		Optional<WebElement> found = elements.stream().filter(ele -> ele.getText().equals(entity)).findFirst();
		return found.isPresent() ? elements.indexOf(found.get()) : -1;
	}
}
