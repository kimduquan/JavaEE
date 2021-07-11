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
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class SecurityTest {
	
	@ClassRule
    public static WeldInitiator weld = WeldInitiator.from(
    		WebDriverUtil.class, 
    		View.class,
    		Security.class,
    		SecurityTest.class
    		)
    .build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	@Inject
	Security security;

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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		security.setUsername("any_role1");
		security.setPassword("any_role");
		security.login();
	}

}
