package epf.shell.security;

import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.naming.Naming;
import epf.util.logging.LogManager;
import epf.util.security.KeyStoreUtil;

/**
 * 
 */
@ApplicationScoped
public class SecurityUtil {
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(SecurityUtil.class.getName());

	
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
	@Inject
	transient String keyStoreFile;
	
	/**
	 *
	 */
	@ConfigProperty(name = Naming.Client.SSL_KEY_STORE_TYPE)
	@Inject
	transient String keyStoreType;
	
	/**
	 *
	 */
	@ConfigProperty(name = Naming.Client.SSL_KEY_STORE_PASSWORD)
	@Inject
	transient String keyStorePassword;
	
	/**
	 *
	 */
	@ConfigProperty(name = Naming.Client.SSL_KEY_PASSWORD)
	@Inject
	transient String keyPassword;
	
	/**
	 *
	 */
	@ConfigProperty(name = Naming.Client.SSL_TRUST_STORE)
	@Inject
	transient String trustStoreFile;
	
	/**
	 *
	 */
	@ConfigProperty(name = Naming.Client.SSL_TRUST_STORE_TYPE)
	@Inject
	transient String trustStoreType;
	
	/**
	 *
	 */
	@ConfigProperty(name = Naming.Client.SSL_TRUST_STORE_PASSWORD)
	@Inject
	transient String trustStorePassword;
	
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
			LOGGER.log(Level.SEVERE, "[SecurityUtil][postConstruct]", e);
		}
	}
	
	public KeyStore getKeyStore() {
		return keyStore;
	}
	
	public KeyStore getTrustStore() {
		return trustStore;
	}
	
	public String getKeyPassword() {
		return keyPassword;
	}
}
