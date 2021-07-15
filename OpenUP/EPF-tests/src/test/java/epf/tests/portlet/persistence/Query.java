/**
 * 
 */
package epf.tests.portlet.persistence;

import java.util.List;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import epf.client.portlet.persistence.QueryView;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class Query implements QueryView {
	
	private final WebDriver driver;
	
	@Inject
	public Query(WebDriver driver) {
		this.driver = driver;
	}

	@Override
	public int getFirstResult() {
		return Integer.parseInt(driver.findElement(By.cssSelector(".persistence.query.firstResult")).getAttribute("value"));
	}

	@Override
	public void setFirstResult(int firstResult) {
		WebElement ele = driver.findElement(By.cssSelector(".persistence.query.firstResult"));
		ele.clear();
		ele.sendKeys(String.valueOf(firstResult));
	}

	@Override
	public int getMaxResults() {
		return Integer.parseInt(driver.findElement(By.cssSelector(".persistence.query.maxResults")).getAttribute("value"));
	}

	@Override
	public void setMaxResults(int maxResults) {
		WebElement ele = driver.findElement(By.cssSelector(".persistence.query.maxResults"));
		ele.clear();
		ele.sendKeys(String.valueOf(maxResults));
	}

	@Override
	public void executeQuery() throws Exception {
		driver.findElement(By.cssSelector(".persistence.query.executeQuery")).click();
	}

	@Override
	public int getIndexOf(Object object) {
		List<WebElement> elements = driver.findElements(By.cssSelector("label.persistence.query.entity.id.name"));
		Optional<WebElement> found = elements.stream().filter(ele -> ele.getText().equals(object)).findFirst();
		return found.isPresent() ? elements.indexOf(found.get()) : -1;
	}

	@Override
	public int getResultSize() {
		 return Integer.parseInt(driver.findElement(By.cssSelector(".persistence.query.result.size")).getText());
	}
	
	public List<?> getResult(){
		return driver.findElements(By.cssSelector("label.persistence.query.entity.id.name"));
	}
}
