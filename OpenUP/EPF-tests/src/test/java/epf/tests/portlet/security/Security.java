/**
 * 
 */
package epf.tests.portlet.security;

import java.util.Map.Entry;
import org.openqa.selenium.WebDriver;
import epf.tests.portlet.PortletView;
import epf.tests.security.SecurityUtil;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Security {
	
	private PortletView view;
	private WebDriver driver;
	
	@Inject
	public Security(PortletView view, WebDriver driver) {
		this.view = view;
		this.driver = driver;
	}
	
	@PostConstruct
	public void navigateTo() {
		view.navigateToSecurity();
	}
	
	public void login() throws Exception {
		Entry<String, String> cred = SecurityUtil.peekCredential();
		Credential credential = new Credential(driver);
		credential.setCaller(cred.getKey());
		credential.setPassword(cred.getValue().toCharArray());
		credential.login();
		Principal principal = new Principal(driver);
		principal.getFullName();
	}
	
	public void logout() {
		view.navigateToSecurity();
		Principal principal = new Principal(driver);
		principal.logout();
	}
}
