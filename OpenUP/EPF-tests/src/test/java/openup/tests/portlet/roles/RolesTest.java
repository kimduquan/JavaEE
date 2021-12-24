/**
 * 
 */
package openup.tests.portlet.roles;

import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TestName;

import epf.tests.portlet.PortletView;
import epf.tests.portlet.WebDriverUtil;
import epf.tests.portlet.security.Security;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
@Ignore
public class RolesTest {
	
	@Rule
    public TestName testName = new TestName();

	@ClassRule
    public static WeldInitiator weld = WeldInitiator.from(
    		WebDriverUtil.class, 
    		PortletView.class,
    		Security.class,
    		Roles.class,
    		RolesTest.class
    		)
    .build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();

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
	
	@Inject
	Roles roles;

	@Test
	public void test() {
		Assert.assertEquals("Roles.roles.size", 15, roles.getRoles().size());
	}

}
