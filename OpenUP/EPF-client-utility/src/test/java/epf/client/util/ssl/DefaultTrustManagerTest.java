/**
 * 
 */
package epf.client.util.ssl;

import java.security.cert.X509Certificate;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author PC
 *
 */
public class DefaultTrustManagerTest {

	/**
	 * Test method for {@link epf.client.util.ssl.DefaultTrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testCheckClientTrusted() throws Exception {
		new DefaultTrustManager().checkClientTrusted(null, null);
	}

	/**
	 * Test method for {@link epf.client.util.ssl.DefaultTrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testCheckServerTrusted() throws Exception {
		new DefaultTrustManager().checkServerTrusted(null, null);
	}

	/**
	 * Test method for {@link epf.client.util.ssl.DefaultTrustManager#getAcceptedIssuers()}.
	 */
	@Test
	public void testGetAcceptedIssuers() {
		X509Certificate[] issuers = new DefaultTrustManager().getAcceptedIssuers();
		Assert.assertArrayEquals(new X509Certificate[0], issuers);
	}

}
