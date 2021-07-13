/**
 * 
 */
package epf.tests.portlet.security;

import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import epf.tests.WebDriverUtil;
import epf.tests.portlet.View;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class CredentialTest {

	@ClassRule
    public static WeldInitiator weld = WeldInitiator.from(
    		WebDriverUtil.class, 
    		View.class,
    		Security.class,
    		Principal.class,
    		Credential.class,
    		CredentialTest.class
    		)
    .build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		security.setUsername("any_role1");
		security.setPassword("any_role");
		security.login();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		security.logout();
	}
	
	@Inject
	Security security;
	
	@Inject
	Instance<Principal> principal;
	
	@Inject
	Instance<Credential> credential;

	@Test
	public void testUpdate() {
		principal.get().navigateToSetPassword();
		credential.get().setPassword("any_role");
		credential.get().update();
	}

}
