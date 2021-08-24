/**
 * 
 */
package epf.tests.portlet.security;

import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import epf.tests.WebDriverUtil;
import epf.tests.portlet.View;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class PrincipalTest {
	
	@ClassRule
    public static WeldInitiator weld = WeldInitiator.from(
    		WebDriverUtil.class, 
    		View.class,
    		Credential.class,
    		Principal.class,
    		PrincipalTest.class
    		)
    .build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	@Inject
	View view;
	
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
		credential.setCaller("any_role1");
		credential.setPassword("any_role".toCharArray());
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
		principal.logout();
	}
	
	@Test
	public void testUpdate_ValidPassword_Succeed() throws Exception {
		principal.navigateToUpdate();
		principal.setPassword("any_role".toCharArray());
		principal.update();
		principal.logout();
	}
}
