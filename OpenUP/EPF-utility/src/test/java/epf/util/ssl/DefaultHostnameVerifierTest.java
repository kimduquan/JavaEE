/**
 * 
 */
package epf.util.ssl;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author PC
 *
 */
public class DefaultHostnameVerifierTest {

	/**
	 * Test method for {@link epf.util.ssl.DefaultHostnameVerifier#verify(java.lang.String, javax.net.ssl.SSLSession)}.
	 */
	@Test
	public void testVerify() {
		boolean result = new DefaultHostnameVerifier().verify(null, null);
		Assert.assertTrue(result);
	}

}
