package epf.tests.webapp.security;

import java.net.URL;
import java.time.Duration;
import java.util.Map.Entry;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import epf.naming.Naming;
import epf.tests.TestUtil;
import epf.tests.WebDriverUtil;
import epf.tests.security.SecurityUtil;
import epf.tests.webapp.DefaultPage;
import epf.tests.webapp.WebAppUtil;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * @author PC
 *
 */

public class LoginPageTest {
	
	@ClassRule
	public static WeldInitiator weld = WeldInitiator.from(
			WebDriverUtil.class, 
			WebAppUtil.class, 
			DefaultPage.class,
			LogOutConfirm.class,
			LoginPage.class, 
			LoginPageTest.class)
			.activate(RequestScoped.class).build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	@Inject
	LoginPage page;
	
	@Inject
	LogOutConfirm confirm;
	
	@Inject @Named(Naming.WebApp.WEB_APP_URL)
	URL webappUrl;
	
	@Inject
	DefaultPage defPage;

	@Test
	public void testLoginOk() throws Exception {
		Entry<String, String> credential = SecurityUtil.peekCredential();
		page.setCaller(credential.getKey());
		page.setPassword(credential.getValue().toCharArray());
		page.login();
		TestUtil.waitUntil((t) -> page.getTitle().equals("SB Admin 2 - Dashboard"), Duration.ofSeconds(20));
		confirm.showConfirm();
		confirm.logout();
		TestUtil.waitUntil((t) -> page.isComplete(), Duration.ofSeconds(10));
		Assert.assertEquals("currentUrl", "https://localhost/security-auth/security/logout.html", page.getCurrentUrl());
	}
	
	@Test
	public void testLoginCancel() throws Exception {
		page.getDriver().navigate().to(webappUrl);
		Entry<String, String> credential = SecurityUtil.peekCredential();
		page.setCaller(credential.getKey());
		page.setPassword(credential.getValue().toCharArray());
		page.login();
		TestUtil.waitUntil((t) -> page.getTitle().equals("SB Admin 2 - Dashboard"), Duration.ofSeconds(10));
		defPage.clickProfile();
		confirm.showConfirm();
		confirm.cancel();
		Assert.assertEquals("title", "SB Admin 2 - Dashboard", page.getTitle());
	}

}
