/**
 * 
 */
package epf.tests.webapp;

import javax.inject.Inject;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;

import epf.tests.webapp.page.DefaultPage;
import epf.tests.webapp.page.impl.DefaultPageImpl;

/**
 * @author PC
 *
 */
public class WebAppTest {
	
	@ClassRule
    public static WeldInitiator weld = WeldInitiator.from(
    		WebDriverUtil.class, 
    		WebAppTest.class,
    		DefaultPageImpl.class
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
	DefaultPage defaultPage;

	@Test
	public void test() {
		defaultPage.linkWorkProducts().click();
	}

}
