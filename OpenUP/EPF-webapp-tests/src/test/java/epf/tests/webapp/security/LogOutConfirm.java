package epf.tests.webapp.security;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import epf.tests.webapp.DefaultPage;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class LogOutConfirm {
	
	@Inject
	RemoteWebDriver driver;
	
	@Inject
	DefaultPage defaultPage;
	
	@PostConstruct
	void showProfileMenu() {
		defaultPage.clickProfile();
	}
	
	public void showConfirm() {
		driver.findElement(By.linkText("Logout")).click();
	}
	
	public void logout() {
		driver.findElement(By.className("modal-footer")).findElement(By.linkText("Logout")).click();
	}
	
	public void cancel() {
		driver.findElement(By.className("modal-footer")).findElement(By.cssSelector("button.btn-secondary")).click();
	}
}
