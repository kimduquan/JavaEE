package epf.tests.webapp;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class DefaultPage extends Page {
	
	@Inject
	public DefaultPage(RemoteWebDriver driver) {
		super(driver);
	}
	
	public void clickProfile() {
		driver.findElement(By.id("userDropdown")).click();
	}
}
