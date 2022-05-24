package epf.shell.client;

import java.net.URI;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import epf.naming.Naming;
import epf.util.logging.LogManager;
import epf.util.security.KeyStoreUtil;

/**
 * 
 */
@ApplicationScoped
public class RestClientUtil {
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(RestClientUtil.class.getName());
	
	/**
	 *
	 */
	private transient KeyStore keyStore;
	
	/**
	 *
	 */
	private transient KeyStore trustStore;
	
	/**
	 *
	 */
	@ConfigProperty(name = Naming.Client.SSL_KEY_STORE)
	transient String keyStoreFile;
	
	/**
	 *
	 */
	@ConfigProperty(name = Naming.Client.SSL_KEY_STORE_TYPE)
	transient String keyStoreType;
	
	/**
	 *
	 */
	@ConfigProperty(name = Naming.Client.SSL_KEY_STORE_PASSWORD)
	transient String keyStorePassword;
	
	/**
	 *
	 */
	@ConfigProperty(name = Naming.Client.SSL_TRUST_STORE)
	transient String trustStoreFile;
	
	/**
	 *
	 */
	@ConfigProperty(name = Naming.Client.SSL_TRUST_STORE_TYPE)
	transient String trustStoreType;
	
	/**
	 *
	 */
	@ConfigProperty(name = Naming.Client.SSL_TRUST_STORE_PASSWORD)
	transient String trustStorePassword;
	
	/**
	 *
	 */
	@ConfigProperty(name = Naming.Client.CLIENT_CONFIG + "/mp-rest/uri")
	transient URI gatewayUrl;

	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			keyStore = KeyStoreUtil.loadKeyStore(keyStoreType, Paths.get(keyStoreFile), keyStorePassword.toCharArray());
			trustStore = KeyStoreUtil.loadKeyStore(trustStoreType, Paths.get(trustStoreFile), trustStorePassword.toCharArray());
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[RestClientUtil][postConstruct]", e);
		}
	}
	
	/**
	 * @param <T>
	 * @param cls
	 * @return
	 */
	public <T> T newClient(final Class<T> cls) {
		return RestClientBuilder.newBuilder().keyStore(keyStore, keyStorePassword).trustStore(trustStore).baseUri(gatewayUrl).build(cls);
	}
}
