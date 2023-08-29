package epf.tests.webapp.security;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import epf.security.view.ForgotPasswordView;
import epf.tests.webapp.Page;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class ForgotPasswordPage extends Page implements ForgotPasswordView {
	
	@Inject
	LoginPage loginPage;

	@Inject
	public ForgotPasswordPage(RemoteWebDriver driver) throws Exception {
		super(driver);
	}

	@Override
	public String getEmail() {
		return driver.findElement(By.id("exampleInputEmail")).getText();
	}

	@Override
	public void setEmail(String email) {
		WebElement e = driver.findElement(By.id("exampleInputEmail"));
		e.clear();
		e.sendKeys(email);
	}

	@Override
	public String reset() throws Exception {
		driver.findElement(By.linkText("Reset Password")).click();
		return "";
	}

	@Override
	public void navigateTo() {
		loginPage.navigateTo();
		loginPage.forgotPassword();
	}

}
