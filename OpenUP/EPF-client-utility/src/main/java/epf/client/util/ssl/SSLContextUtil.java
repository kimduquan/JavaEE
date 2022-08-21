package epf.client.util.ssl;

import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * 
 */
public interface SSLContextUtil {
	
	/**
	 *
	 */
	String TLS = "TLS";

	/**
	 * @param protocol
	 * @param keyStore
	 * @param password
	 * @return
	 * @throws Exception
	 */
	static SSLContext getDefault(final String protocol, final KeyStore keyStore, final char[] password) throws Exception {
		final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		keyManagerFactory.init(keyStore, password);
		SSLContext context = SSLContext.getInstance(protocol);
		TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		factory.init(keyStore);
		context.init(keyManagerFactory.getKeyManagers(), factory.getTrustManagers(), null);
		return context;
	}
}
