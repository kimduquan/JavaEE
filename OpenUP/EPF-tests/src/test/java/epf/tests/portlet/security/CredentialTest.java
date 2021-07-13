/**
 * 
 */
package epf.tests.portlet.security;

import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Assert;
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
	Instance<Principal> principal;
	
	@Test
	public void testLogin_ValidCredential_Succeed() throws Exception {
		credential.setCaller("any_role1");
		credential.setPassword("any_role".toCharArray());
		credential.login();
		Assert.assertEquals("Security.principalName", "any_role1", principal.get().getName());
		principal.get().logout();
	}
}
