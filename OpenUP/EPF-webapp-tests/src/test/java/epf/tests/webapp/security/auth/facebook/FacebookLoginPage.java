package epf.tests.webapp.security.auth.facebook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import epf.tests.webapp.Page;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class FacebookLoginPage extends Page {

	@Inject
	public FacebookLoginPage(RemoteWebDriver driver) throws Exception {
		super(driver);
	}

	public void setEmail(String email) {
		WebElement e = driver.findElement(By.id("email"));
		e.clear();
		e.sendKeys(email);
	}
	
	public void setPassword(String password) {
		WebElement e = driver.findElement(By.id("pass"));
		e.clear();
		e.sendKeys(password);
	}
	
	public void login() {
		driver.findElement(By.id("loginbutton")).click();
	}

	@Override
	public void navigateTo() {
		
	}
}
