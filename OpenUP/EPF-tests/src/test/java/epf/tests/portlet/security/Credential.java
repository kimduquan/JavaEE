/**
 * 
 */
package epf.tests.portlet.security;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import epf.client.portlet.security.CredentialView;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class Credential implements CredentialView {

	private final WebDriver driver;
	
	@Inject
	public Credential(WebDriver driver){
		this.driver = driver;
	}

	@Override
	public String getCaller() {
		return driver.findElement(By.cssSelector(".security.credential.caller")).getText();
	}

	@Override
	public void setCaller(String caller) {
		driver.findElement(By.cssSelector(".security.credential.caller")).sendKeys(caller);
	}

	@Override
	public char[] getPassword() {
		return driver.findElement(By.cssSelector(".security.credential.password")).getText().toCharArray();
	}

	@Override
	public void setPassword(char... password) {
		driver.findElement(By.cssSelector(".security.credential.password")).sendKeys(new String(password));
	}

	@Override
	public String login() throws Exception {
		driver.findElement(By.cssSelector(".security.login")).click();
		return "";
	}
}
