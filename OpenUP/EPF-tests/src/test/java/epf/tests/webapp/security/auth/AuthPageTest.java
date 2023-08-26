package epf.tests.webapp.security.auth;

import java.time.Duration;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import epf.tests.TestUtil;
import epf.tests.WebDriverUtil;
import epf.tests.webapp.WebAppUtil;
import epf.tests.webapp.security.LogOutConfirm;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

public class AuthPageTest {

	@ClassRule
	public static WeldInitiator weld = WeldInitiator.from(
			WebDriverUtil.class, 
			WebAppUtil.class, 
			AuthPage.class,
			AuthPageTest.class)
			.activate(RequestScoped.class).build();
	
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
		confirm.showConfirm();
		confirm.logout();
	}
}
