/**
 * 
 */
package epf.tests.portlet.security;

import org.openqa.selenium.WebDriver;
import epf.tests.portlet.View;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class SecurityUtil {
	
	Credential credential;
	
	@Inject
	WebDriver driver;
	
	@Inject
	View view;
	
	@PostConstruct
	void postConstruct() {
		view.navigateToSecurity();
		credential = new Credential(driver, view);
		credential.setCaller("any_role1");
		credential.setPassword("any_role".toCharArray());
		try {
			credential.login();
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PreDestroy
	void preDestroy() {
		view.navigateToSecurity();
		Principal principal = new Principal(driver);
		principal.logout();
	}

	public void login(){
		
	}
}
