package epf.tests.webapp.security;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import epf.security.view.LoginView;
import epf.tests.webapp.DefaultPage;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class LoginPage implements LoginView {
	
	final WebDriver driver;
	
	@Inject
	public LoginPage(DefaultPage page, WebDriver driver) {
		this.driver = driver;
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
		passwordElement.clear();
		passwordElement.sendKeys(new String(password));
	}

	@Override
	public String login() throws Exception {
		WebElement login = driver.findElement(By.linkText("Login"));
		login.submit();
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
