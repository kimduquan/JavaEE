/**
 * 
 */
package epf.util.ssl;

import javax.net.ssl.SSLContext;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author PC
 *
 */
public class SSLContextHelperTest {

	/**
	 * Test method for {@link epf.util.ssl.SSLContextHelper#build()}.
	 */
	@Test
	public void testBuild() {
		SSLContext ssl = SSLContextHelper.build();
		Assert.assertNotNull(ssl);
	}

}
