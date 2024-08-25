package epf.tests.webapp.security;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import epf.security.view.RegisterView;
import epf.tests.webapp.Page;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class RegisterPage extends Page implements RegisterView {
	
	@Inject
	LoginPage loginPage;

	@Inject
	public RegisterPage(RemoteWebDriver driver) throws Exception {
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
	public String getFirstName() {
		return driver.findElement(By.id("exampleFirstName")).getText();
	}

	@Override
	public void setFirstName(String firstName) {
		WebElement e = driver.findElement(By.id("exampleFirstName"));
		e.clear();
		e.sendKeys(firstName);
	}

	@Override
	public String getLastName() {
		return driver.findElement(By.id("exampleLastName")).getText();
	}

	@Override
	public void setLastName(String lastName) {
		WebElement e = driver.findElement(By.id("exampleLastName"));
		e.clear();
		e.sendKeys(lastName);
	}

	@Override
	public String getPassword() {
		return driver.findElement(By.id("exampleInputPassword")).getText();
	}

	@Override
	public void setPassword(String password) {
		WebElement e = driver.findElement(By.id("exampleInputPassword"));
		e.clear();
		e.sendKeys(password);
	}

	@Override
	public String getRepeatPassword() {
		return driver.findElement(By.id("exampleRepeatPassword")).getText();
	}

	@Override
	public void setRepeatPassword(String repeatPassword) {
		WebElement e = driver.findElement(By.id("exampleRepeatPassword"));
		e.clear();
		e.sendKeys(repeatPassword);
	}

	@Override
	public String register() throws Exception {
		driver.findElement(By.partialLinkText("Register Account")).click();
		return "";
	}

	@PostConstruct
	@Override
	public void navigateTo() {
		loginPage.register();
	}

}
