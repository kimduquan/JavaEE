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
import epf.tests.portlet.security.Security;
import jakarta.inject.Inject;

/**
 * @author PC
 *
 */
public class QueryTest {
	
	@ClassRule
    public static WeldInitiator weld = WeldInitiator.from(
    		WebDriverUtil.class, 
    		View.class,
    		Security.class,
    		Persistence.class,
    		Schema.class,
    		Query.class,
    		QueryTest.class
    		)
    .build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	@Inject
	Persistence persistence;
	
	@Inject
	Schema schema;
	
	@Inject
	Query query;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		persistence.navigateToPersistence();
		schema.setEntity("Artifact");
		persistence.navigateToQuery();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_DefaultValues_TheSameConfiguredValues() {
		Assert.assertEquals("Query.firstResult", 0, query.getFirstResult());
		Assert.assertEquals("Query.maxResults", 100, query.getMaxResults());
		Assert.assertEquals("Query.resultSize", 100, query.getResultSize());
		Assert.assertEquals("Query.result.size", 100, query.getResult().size());
	}

	@Test
	public void test_SetFirstResult_TheFirstObjectIndexIsCorrect() throws Exception {
		int index = query.getIndexOf("Release");
		query.setFirstResult(query.getFirstResult() + 5);
		query.executeQuery();
		Assert.assertEquals(index - 5, query.getIndexOf("Release"));
	}
	
	@Test
	public void test_SetMaxResults_TheResultSizeIsCorrect() throws Exception {
		query.setMaxResults(20);
		query.executeQuery();
		Assert.assertEquals("Query.resultSize", 20, query.getResultSize());
		Assert.assertEquals("Query.result.size", 20, query.getResult().size());
	}
}
