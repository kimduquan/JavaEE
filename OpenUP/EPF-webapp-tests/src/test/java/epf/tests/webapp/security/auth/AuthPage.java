package epf.tests.webapp.security.auth;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import epf.security.auth.view.AuthView;
import epf.tests.webapp.Page;
import jakarta.inject.Inject;

public class AuthPage extends Page implements AuthView {

	@Inject
	public AuthPage(RemoteWebDriver driver) {
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

}
