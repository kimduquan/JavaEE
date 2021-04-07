/**
 * 
 */
package epf.webapp;

import java.net.URL;
import javax.inject.Inject;
import javax.inject.Named;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.openqa.selenium.WebDriver;

/**
 * @author PC
 *
 */
public class WebAppTest {
	
	@ClassRule
    public static WeldInitiator weld = WeldInitiator.from(WebAppUtil.class, WebAppTest.class).build();
	
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
	WebDriver webDriver;
	
	@Inject @Named("WEB_APP_URL")
	URL webAppUrl;

	@Test
	public void test() {
		webDriver.navigate().to(webAppUrl);
	}

}
