/**
 * 
 */
package epf.tests.portlet.security;

import java.util.Map.Entry;

import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
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
public class CredentialTest {
	
	@Rule
    public TestName testName = new TestName();
	
	@ClassRule
    public static WeldInitiator weld = WeldInitiator.from(
    		WebDriverUtil.class,
    		PortletUtil.class,
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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testLogin_ValidCredential_Succeed() throws Exception {
		Entry<String, String> cred = SecurityUtil.peekCredential();
		credential.setCaller(cred.getKey());
		credential.setPassword(cred.getValue().toCharArray());
		credential.login();
		Assert.assertFalse("Security.principalFullName", principal.getFullName().isEmpty());
		principal.logout();
	}
}
