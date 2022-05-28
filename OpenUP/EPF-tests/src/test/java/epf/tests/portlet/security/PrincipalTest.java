package epf.tests.portlet.security;

import java.util.Map.Entry;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TestName;
import epf.tests.WebDriverUtil;
import epf.tests.portlet.PortletUtil;
import epf.tests.portlet.PortletView;
import epf.tests.security.SecurityUtil;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
@Ignore
public class PrincipalTest {
	
	@Rule
    public TestName testName = new TestName();
	
	Entry<String, String> cred;
	
	@ClassRule
    public static WeldInitiator weld = WeldInitiator.from(
    		WebDriverUtil.class, 
    		PortletUtil.class,
    		PortletView.class,
    		Security.class,
    		Credential.class,
    		Principal.class,
    		PrincipalTest.class
    		)
    .build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	@Inject
	PortletView view;
	
	@Inject
	Credential credential;
	
	@Inject
	Principal principal;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		view.navigateToSecurity();
		cred = SecurityUtil.peekCredential();
		credential.setCaller(cred.getKey());
		credential.setPassword(cred.getValue().toCharArray());
		credential.login();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLogout_UserLoggedIn_Succeed() {
		principal.getFullName();
		principal.logout();
	}
	
	@Test
	public void testUpdate_ValidPassword_Succeed() throws Exception {
		principal.getFullName();
		principal.navigateToUpdate();
		principal.setPassword(cred.getValue().toCharArray());
		principal.update();
		principal.logout();
	}
}
