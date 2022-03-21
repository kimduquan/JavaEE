package epf.tests.webapp.security;

import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import epf.naming.Naming;
import epf.security.view.LoginView;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
public class LoginPage implements LoginView {
	
	@Inject
	private WebDriver driver;
	
	@Inject @Named(Naming.WebApp.WEB_APP_URL)
	private URL url;
	
	@PostConstruct
	void navigate() {
		driver.navigate().to(url);
	}

	@Override
	public String getCaller() {
		return driver.findElement(By.name("caller")).getText();
	}

	@Override
	public void setCaller(String caller) {
		WebElement callerElement = driver.findElement(By.name("caller"));
		callerElement.clear();
		callerElement.sendKeys(caller);
	}

	@Override
	public void setPassword(char[] password) {
		WebElement passwordElement = driver.findElement(By.name("password"));
		passwordElement.clear();
		passwordElement.sendKeys(new String(password));
	}

	@Override
	public String login() throws Exception {
		WebElement login = driver.findElement(By.name("login"));
		login.submit();
		return driver.getTitle();
	}

	@Override
	public char[] getPassword() {
		WebElement passwordElement = driver.findElement(By.name("password"));
		return passwordElement.getText().toCharArray();
	}

	@Override
	public boolean isRememberMe() {
		return false;
	}

	@Override
	public void setRememberMe(boolean rememberMe) {
		
	}

}
