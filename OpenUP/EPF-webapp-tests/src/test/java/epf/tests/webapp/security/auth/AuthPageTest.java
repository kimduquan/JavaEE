package epf.tests.webapp.security.auth;

import org.jboss.weld.junit4.WeldInitiator;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import epf.tests.webapp.security.auth.google.GoogleMailPage;
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
	GoogleMailPage gmail;
	
	@Test
	public void testloginWithGoogle_LoginOk() throws Exception {
		gmail.setIdentifierId("kimduquan01@gmail.com");
		gmail.next();
		gmail.tryAgain();
	}
	
	@Test
	public void testloginWithFacebook_LoginOk() throws Exception {
		authPage.loginWithFacebook();
	}
}
