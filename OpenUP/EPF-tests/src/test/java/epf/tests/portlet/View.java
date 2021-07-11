/**
 * 
 */
package epf.tests.portlet;

import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import epf.client.portlet.Portlet;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * @author PC
 *
 */
public class View {

	@Inject
	private transient WebDriver webDriver;
	
	@Inject @Named(Portlet.PORTLET_URL)
	private transient URL url;
	
	@PostConstruct
	void navigate() {
		webDriver.navigate().to(url);
	}
	
	public void setUsername(String username) {
		webDriver.findElement(By.name("j_username")).sendKeys(username);
	}
	
	public void setPassword(String password) {
		webDriver.findElement(By.name("j_password")).sendKeys(password);;
	}
	
	public void login() {
		webDriver.findElement(By.name("login")).submit();
	}
}
