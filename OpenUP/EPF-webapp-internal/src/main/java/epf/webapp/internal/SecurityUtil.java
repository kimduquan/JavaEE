package epf.webapp.internal;

import java.nio.file.Path;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.util.security.KeyStoreUtil;

@ApplicationScoped
public class SecurityUtil {
	
	private transient static final Logger LOGGER = LogManager.getLogger(SecurityUtil.class.getName());
	
	private transient KeyStore keyStore;
	
	private transient String keyAlias;
	
	private transient char[] keyPassword;
	
	private transient KeyStore trustStore;
	
	private transient PrivateKey privateKey;
	
	private transient PublicKey publicKey;

	@PostConstruct
	protected void postConstruct() {
		final Path keyStoreFile = ConfigUtil.getPath(Naming.Client.SSL_KEY_STORE);
    	final String keyStoreType = ConfigUtil.getString(Naming.Client.SSL_KEY_STORE_TYPE);
    	final char[] keyStorePassword = ConfigUtil.getChars(Naming.Client.SSL_KEY_STORE_PASSWORD);
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
		keyAlias = ConfigUtil.getString(Naming.Client.SSL_KEY_ALIAS);
		keyPassword = ConfigUtil.getChars(Naming.Client.SSL_KEY_PASSWORD);
		try {
			privateKey = (PrivateKey) keyStore.getKey(keyAlias, keyPassword);
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[SecurityUtil.privateKey]", e);
		}
		try {
			publicKey = trustStore.getCertificate(keyAlias).getPublicKey();
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[SecurityUtil.publicKey]", e);
		}
	}

	public KeyStore getKeyStore() {
		return keyStore;
	}

	public KeyStore getTrustStore() {
		return trustStore;
	}

	public char[] getKeyPassword() {
		return keyPassword;
	}

	public String getKeyAlias() {
		return keyAlias;
	}
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
}
