/**
 * 
 */
package epf.tests.portlet.security;

import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;

import epf.tests.portlet.PortletView;
import epf.tests.portlet.WebDriverUtil;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class CredentialTest {
	
	@ClassRule
    public static WeldInitiator weld = WeldInitiator.from(
    		WebDriverUtil.class,
    		PortletView.class,
    		Security.class,
    		Credential.class,
    		Principal.class,
    		CredentialTest.class
    		)
    .build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	@Inject
	Credential credential;
	
	@Inject
	Principal principal;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testLogin_ValidCredential_Succeed() throws Exception {
		credential.setCaller("any_role1");
		credential.setPassword("any_role".toCharArray());
		credential.login();
		Assert.assertEquals("Security.principalFullName", "Any Role 1", principal.getFullName());
		principal.logout();
	}
}
