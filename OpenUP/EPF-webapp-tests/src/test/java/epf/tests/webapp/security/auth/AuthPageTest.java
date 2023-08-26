package epf.tests.webapp.security.auth;

import java.time.Duration;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import epf.tests.TestUtil;
import epf.tests.webapp.security.LogOutConfirm;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

public class AuthPageTest {

	@ClassRule
	public static WeldInitiator weld = WeldInitiator.from(WeldInitiator.createWeld().enableDiscovery()).activate(RequestScoped.class).build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	@Inject
	AuthPage authPage;
	
	@Inject
	LogOutConfirm confirm;
	
	@Test
	public void testloginWithGoogle_LoginOk() throws Exception {
		authPage.loginWithGoogle();
		TestUtil.waitUntil((t) -> authPage.getTitle().equals("SB Admin 2 - Dashboard"), Duration.ofSeconds(20));
		Assert.assertEquals("title", "SB Admin 2 - Dashboard", authPage.getTitle());
		confirm.showConfirm();
		confirm.logout();
		TestUtil.waitUntil((t) -> "https://localhost/security-auth/security/logout.html".equals(authPage.getCurrentUrl()), Duration.ofSeconds(10));
	}
	
	@Test
	public void testloginWithFacebook_LoginOk() throws Exception {
		authPage.loginWithFacebook();
		TestUtil.waitUntil((t) -> authPage.getTitle().equals("SB Admin 2 - Dashboard"), Duration.ofSeconds(20));
		Assert.assertEquals("title", "SB Admin 2 - Dashboard", authPage.getTitle());
		confirm.showConfirm();
		confirm.logout();
		TestUtil.waitUntil((t) -> "https://localhost/security-auth/security/logout.html".equals(authPage.getCurrentUrl()), Duration.ofSeconds(10));
	}
}
