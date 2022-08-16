package epf.tests.webapp.security;

import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import epf.naming.Naming;
import epf.security.view.LoginView;
import epf.tests.webapp.Page;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
public class LoginPage extends Page implements LoginView {
	
	final URL url;
	
	@Inject
	public LoginPage(RemoteWebDriver driver, @Named(Naming.WebApp.WEB_APP_URL) URL url) {
		super(driver);
		this.url = url;
	}
	
	@PostConstruct
	void navigateTo() {
		driver.navigate().to(url);
	}

	@Override
	public String getCaller() {
		return driver.findElement(By.id("exampleInputEmail")).getText();
	}

	@Override
	public void setCaller(String caller) {
		WebElement callerElement = driver.findElement(By.id("exampleInputEmail"));
		callerElement.clear();
		callerElement.sendKeys(caller);
	}

	@Override
	public void setPassword(char[] password) {
		WebElement passwordElement = driver.findElement(By.id("exampleInputPassword"));
		passwordElement.sendKeys(new String(password));
	}

	@Override
	public String login() throws Exception {
		WebElement login = driver.findElement(By.linkText("Login"));
		login.click();
		return driver.getTitle();
	}

	@Override
	public char[] getPassword() {
		WebElement passwordElement = driver.findElement(By.id("exampleInputPassword"));
		return passwordElement.getText().toCharArray();
	}

	@Override
	public boolean isRememberMe() {
		WebElement isRememberMeElement = driver.findElement(By.id("customCheck"));
		return "true".equals(isRememberMeElement.getDomProperty("checked").toString());
	}

	@Override
	public void setRememberMe(boolean rememberMe) {
		WebElement isRememberMeElement = driver.findElement(By.id("customCheck"));
		isRememberMeElement.click();
	}
}
