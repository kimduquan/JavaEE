/**
 * 
 */
package epf.util.config;

import java.net.URI;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author PC
 *
 */
public class ConfigUtilTest {

	/**
	 * Test method for {@link epf.util.config.ConfigUtil#getString(java.lang.String)}.
	 */
	@Test
	public void testGetValue() {
		String value = ConfigUtil.getString("abc");
		Assert.assertNull(value);
	}

	/**
	 * Test method for {@link epf.util.config.ConfigUtil#getURI(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testGetURI() throws Exception {
		System.setProperty("abc", "http://localhost:8282/");
		URI uri = ConfigUtil.getURI("abc");
		Assert.assertEquals(new URI("http://localhost:8282/"), uri);
	}
}
