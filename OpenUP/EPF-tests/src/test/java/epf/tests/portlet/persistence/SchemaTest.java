/**
 * 
 */
package epf.tests.portlet.persistence;

import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
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
public class SchemaTest {
	
	@Rule
    public TestName testName = new TestName();
	
	@ClassRule
    public static WeldInitiator weld = WeldInitiator.from(
    		WebDriverUtil.class, 
    		PortletView.class,
    		Persistence.class,
    		Security.class,
    		Schema.class,
    		SchemaTest.class
    		)
    .build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	@Inject
	Persistence pesistence;
	
	@Inject
	Schema schema;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pesistence.navigateToSchema();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetEntity_ValidEntity_Succeed() {
		schema.setEntity("Artifact");
		Assert.assertEquals("schema.entity", "Artifact", schema.getEntity());
	}

}
