/**
 * 
 */
package epf.tests.portlet.security;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import epf.client.portlet.security.PrincipalView;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class Principal implements PrincipalView {
	
	@Inject
	WebDriver driver;
	
	@Override
	public String logout() {
		driver.findElement(By.cssSelector(".security.principal.logout")).click();
		return "";
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
	public String update() throws Exception {
		driver.findElement(By.cssSelector(".security.update")).click();
		return "";
	}

	@Override
	public void revoke() throws Exception {
		
	}
	
	@Override
	public String getName() {
		return driver.findElement(By.cssSelector(".security.session.token.name")).getText();
	}
	
	public void navigateToUpdate() {
		driver.findElement(By.cssSelector(".security.update")).click();
	}
}
