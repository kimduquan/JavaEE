package epf.tests.webapp;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class DefaultPage extends Page {
	
	@Inject
	public DefaultPage(RemoteWebDriver driver) throws Exception {
		super(driver);
	}
	
	public void clickProfile() {
		driver.findElement(By.id("userDropdown")).click();
	}

	@PostConstruct
	public void navigateTo() {
		driver.navigate().to(webappUrl);
	}
}
