package epf.webapp;

import java.nio.file.Path;
import java.security.KeyStore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;
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
	private transient char[] keyStorePassword;
	
	/**
	 *
	 */
	private transient KeyStore trustStore;

	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		final Path keyStoreFile = ConfigUtil.getPath(Naming.Client.SSL_KEY_STORE);
    	final String keyStoreType = ConfigUtil.getString(Naming.Client.SSL_KEY_STORE_TYPE);
    	keyStorePassword = ConfigUtil.getChars(Naming.Client.SSL_KEY_STORE_PASSWORD);
    	try {
    		keyStore = KeyStoreUtil.loadKeyStore(keyStoreType, keyStoreFile, keyStorePassword);
		} 
    	catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[SecurityUtil.keyStore]", e);
		}
    	final Path trustStoreFile = ConfigUtil.getPath(Naming.Client.SSL_TRUST_STORE);
    	final String trustStoreType = ConfigUtil.getString(Naming.Client.SSL_TRUST_STORE_TYPE);
    	final char[] trustStorePassword = ConfigUtil.getChars(Naming.Client.SSL_TRUST_STORE_PASSWORD);
		try {
			trustStore = KeyStoreUtil.loadKeyStore(trustStoreType, trustStoreFile, trustStorePassword);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[SecurityUtil.trustStore]", e);
		}
	}

	public KeyStore getKeyStore() {
		return keyStore;
	}

	public KeyStore getTrustStore() {
		return trustStore;
	}

	public char[] getKeyStorePassword() {
		return keyStorePassword;
	}
}
