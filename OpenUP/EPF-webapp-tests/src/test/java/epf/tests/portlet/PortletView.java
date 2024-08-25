package epf.tests.portlet;

import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import epf.portlet.naming.Naming;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class PortletView {

	private final WebDriver driver;
	
	private final URL url;
	
	@Inject
	public PortletView(WebDriver driver, @Named(Naming.PORTLET_URL) URL url) {
		this.driver = driver;
		this.url = url;
	}
	
	@PostConstruct
	void navigateTo() {
		driver.get(url.toString());
		setUsername("pluto");
		setPassword("pluto");
		login();
	}
	
	@PreDestroy
	void logout() {
		driver.findElement(By.linkText("Logout")).click();
	}
	
	void setUsername(String username) {
		driver.findElement(By.name("j_username")).sendKeys(username);
	}
	
	void setPassword(String password) {
		driver.findElement(By.name("j_password")).sendKeys(password);;
	}
	
	void login() {
		driver.findElement(By.name("login")).submit();
	}
	
	public void refresh() {
		driver.navigate().refresh();
	}

	public void navigateToPersistence() {
		driver.findElement(By.linkText("Persistence")).click();
	}
	
	public void navigateToSecurity() {
		driver.findElement(By.linkText("Security")).click();
	}
	
	public void navigateToRoles() {
		driver.findElement(By.linkText("Roles")).click();
	}
}
