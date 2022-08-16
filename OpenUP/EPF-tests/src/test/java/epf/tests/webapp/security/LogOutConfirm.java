package epf.tests.webapp.security;

import org.openqa.selenium.By;
import epf.tests.webapp.DefaultPage;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class LogOutConfirm {

	@Inject
	DefaultPage page;
	
	@PostConstruct
	void showProfileMenu() {
		page.clickProfile();
	}
	
	void showConfirm() {
		page.getDriver().findElement(By.linkText("Logout")).click();
	}
	
	void logout() {
		page.getDriver().findElement(By.className("modal-footer")).findElement(By.linkText("Logout")).click();
	}
	
	void cancel() {
		page.getDriver().findElement(By.className("modal-footer")).findElement(By.cssSelector("button.btn-secondary")).click();
	}
}
