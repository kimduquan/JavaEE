package epf.tests.webapp.security;

import org.jboss.weld.junit4.WeldInitiator;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

public class RegisterPageTest {

	@ClassRule
	public static WeldInitiator weld = WeldInitiator.from(WeldInitiator.createWeld().enableDiscovery()).activate(RequestScoped.class).build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	@Inject
	RegisterPage registerPage;
	
	@Test
	public void testRegisterPage_RegisterOk() throws Exception {
		registerPage.setEmail("kimduquan01@gmail.com");
		registerPage.setFirstName("Kim Du");
		registerPage.setLastName("Quan");
		registerPage.setPassword("123456");
		registerPage.setRepeatPassword("123456");
		registerPage.register();
	}
}
