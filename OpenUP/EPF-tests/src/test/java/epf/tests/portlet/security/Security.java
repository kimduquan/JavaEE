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
public class Security {
	
	private final Credential credential;
	private final Principal principal;
	
	private final View view;
	
	@Inject
	public Security(WebDriver driver, View view) {
		this.view = view;
		credential = new Credential(driver);
		principal = new Principal(driver);
	}
	
	@PostConstruct
	void postConstruct() {
		view.navigateToSecurity();
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
		principal.logout();
	}

	public void login(){
		
	}
}
