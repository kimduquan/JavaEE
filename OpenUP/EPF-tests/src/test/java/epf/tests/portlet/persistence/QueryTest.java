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
import epf.tests.portlet.PortletView;
import epf.tests.portlet.WebDriverUtil;
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
    		PortletView.class,
    		Persistence.class,
    		Security.class,
    		Schema.class,
    		Query.class,
    		QueryTest.class
    		)
    .build();
	
	@Rule
    public MethodRule testClassInjectorRule = weld.getTestClassInjectorRule();
	
	@Inject
	PortletView view;
	
	@Inject
	Schema schema;
	
	@Inject
	Query query;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		schema.setEntity("Artifact");
		schema.getEntity();
		query.getResultSize();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_DefaultValues_TheSameConfiguredValues() {
		/*int resultSize = query.getResultSize();
		if(resultSize != 29) {
			view.refresh();
			schema.setEntity("Artifact");
			schema.getEntity();
			query.getResultSize();
		}*/
		Assert.assertEquals("Query.firstResult", 0, query.getFirstResult());
		Assert.assertEquals("Query.maxResults", 100, query.getMaxResults());
		Assert.assertEquals("Query.resultSize", 29, query.getResultSize());
		Assert.assertEquals("Query.result.size", 29, query.getResultList("name").size());
	}

	@Test
	public void test_SetFirstResult_TheFirstObjectIndexIsCorrect() throws Exception {
		int index = query.getIndexOf("name", "Release");
		query.setFirstResult(query.getFirstResult() + 5);
		query.executeQuery();
		Assert.assertEquals(index - 5, query.getIndexOf("name", "Release"));
	}
	
	@Test
	public void test_SetMaxResults_TheResultSizeIsCorrect() throws Exception {
		query.setMaxResults(29);
		query.executeQuery();
		Assert.assertEquals("Query.resultSize", 29, query.getResultSize());
		Assert.assertEquals("Query.result.size", 29, query.getResultList("name").size());
	}
	
	@Test
	public void test_Merge_navigateToEntity() {
		query.merge("name", "Release");
	}
	
	@Test
	public void test_Sort_Ascending_TheResultOrderIsAscending() {
		query.sort("name");
	}
	
	@Test
	public void test_Sort_Descending_TheResultOrderIsDescending() {
		query.sort("name");
		query.sort("name");
	}
}
