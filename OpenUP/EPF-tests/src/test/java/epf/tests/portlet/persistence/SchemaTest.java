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
import epf.tests.WebDriverUtil;
import epf.tests.portlet.View;
import epf.tests.portlet.security.SecurityUtil;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class SchemaTest {
	
	@ClassRule
    public static WeldInitiator weld = WeldInitiator.from(
    		WebDriverUtil.class, 
    		View.class,
    		SecurityUtil.class,
    		Schema.class,
    		SchemaTest.class
    		)
    .build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	@Inject
	Schema schema;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		schema.navigateToPersistence();
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
