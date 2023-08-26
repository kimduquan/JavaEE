package epf.tests.webapp.security.auth;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import epf.security.auth.view.AuthView;
import epf.tests.webapp.Page;
import epf.tests.webapp.security.LoginPage;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class AuthPage extends Page implements AuthView {
	
	@Inject
	LoginPage loginPage;

	@Inject
	public AuthPage(RemoteWebDriver driver) throws Exception {
		super(driver);
	}

	@Override
	public String loginWithGoogle() throws Exception {
		driver.findElement(By.cssSelector("a.btn-google")).click();;
		return "";
	}

	@Override
	public String loginWithFacebook() throws Exception {
		driver.findElement(By.cssSelector("a.btn-facebook")).click();
		return "";
	}

	@PostConstruct
	@Override
	public void navigateTo() {
		loginPage.navigateTo();
	}

}
