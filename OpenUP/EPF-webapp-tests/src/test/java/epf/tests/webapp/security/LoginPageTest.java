package epf.tests.webapp.security;

import java.time.Duration;
import java.util.Map.Entry;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import epf.tests.webapp.DefaultPage;
import epf.tests.webapp.util.SecurityUtil;
import epf.tests.webapp.util.TestUtil;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */

public class LoginPageTest {
	
	@ClassRule
	public static WeldInitiator weld = WeldInitiator.from(WeldInitiator.createWeld().enableDiscovery()).activate(RequestScoped.class).build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	@Inject
	LoginPage loginPage;
	
	@Inject
	LogOutConfirm confirm;
	
	@Inject
	DefaultPage defPage;
	
	@Before
	public void navigateTo() {
		loginPage.navigateTo();
	}

	@Test
	public void testLoginCorrectUsernamePassword_LoginOk() throws Exception {
		Entry<String, String> credential = SecurityUtil.peekCredential();
		loginPage.setCaller(credential.getKey());
		loginPage.setPassword(credential.getValue().toCharArray());
		loginPage.login();
		TestUtil.waitUntil((t) -> loginPage.getTitle().equals("SB Admin 2 - Dashboard"), Duration.ofSeconds(20));
		confirm.showConfirm();
		confirm.logout();
		TestUtil.waitUntil((t) -> loginPage.isComplete(), Duration.ofSeconds(10));
		Assert.assertEquals("currentUrl", "https://localhost/security-auth/security/logout.html", loginPage.getCurrentUrl());
	}
	
	@Test
	public void testLoginInvalidPassword_NotLogin() throws Exception {
		Entry<String, String> credential = SecurityUtil.peekCredential();
		loginPage.setCaller(credential.getKey());
		loginPage.setPassword("invalid".toCharArray());
		loginPage.login();
		TestUtil.waitUntil((t) -> loginPage.isComplete(), Duration.ofSeconds(10));
		Assert.assertNotEquals("title", "SB Admin 2 - Dashboard", loginPage.getTitle());
	}
	
	@Test
	public void testLoginCancel() throws Exception {
		Entry<String, String> credential = SecurityUtil.peekCredential();
		loginPage.setCaller(credential.getKey());
		loginPage.setPassword(credential.getValue().toCharArray());
		loginPage.login();
		TestUtil.waitUntil((t) -> loginPage.getTitle().equals("SB Admin 2 - Dashboard"), Duration.ofSeconds(10));
		defPage.clickProfile();
		confirm.showConfirm();
		confirm.cancel();
		Assert.assertEquals("title", "SB Admin 2 - Dashboard", loginPage.getTitle());
	}

}
