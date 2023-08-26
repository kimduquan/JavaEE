package epf.tests.webapp.security;

import org.jboss.weld.junit4.WeldInitiator;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

public class RegisterPageTest {

	@ClassRule
	public static WeldInitiator weld = WeldInitiator.from(WeldInitiator.createWeld().enableDiscovery()).activate(RequestScoped.class).build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	@Inject
	LoginPage loginPage;
	
	public void testRegisterPage_RegisterOk() throws Exception {
		loginPage.register();
	}
}
