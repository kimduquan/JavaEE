package epf.tests.webapp.security;

import java.time.Duration;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import epf.tests.webapp.security.auth.google.GoogleMailPage;
import epf.tests.webapp.util.TestUtil;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

public class RegisterPageTest {

	@ClassRule
	public static WeldInitiator weld = WeldInitiator.from(WeldInitiator.createWeld().enableDiscovery()).activate(RequestScoped.class).build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	@Inject
	RegisterPage registerPage;
	
	@Inject
	GoogleMailPage gmailPage;
	
	@Test
	public void testRegisterPage_RegisterOk() throws Exception {
		registerPage.setEmail("kimduquan10@gmail.com");
		registerPage.setFirstName("Kim Du");
		registerPage.setLastName("Quan 10");
		registerPage.setPassword("123456");
		registerPage.setRepeatPassword("123456");
		registerPage.register();
		TestUtil.waitUntil(t -> registerPage.getTitle().contains("Login"), Duration.ofMinutes(1));
		gmailPage.setIdentifierId("kimduquan10@gmail.com");
		gmailPage.next();
	}
}
