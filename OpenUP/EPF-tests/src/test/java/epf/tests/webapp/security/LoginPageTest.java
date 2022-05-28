package epf.tests.webapp.security;

import java.util.Map.Entry;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import epf.tests.WebDriverUtil;
import epf.tests.security.SecurityUtil;
import epf.tests.webapp.WebAppUtil;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
@Ignore
public class LoginPageTest {
	
	@ClassRule
    public static WeldInitiator weld = WeldInitiator.from(
    		WebDriverUtil.class, 
    		WebAppUtil.class,
    		LoginPage.class,
    		LoginPageTest.class)
    .activate(RequestScoped.class)
    .build();
	
	@Inject
	LoginPage page;

	@Test
	public void testLogin() throws Exception {
		Entry<String, String> credential = SecurityUtil.peekCredential();
		page.setCaller(credential.getKey());
		page.setPassword(credential.getValue().toCharArray());
		page.login();
	}

}
