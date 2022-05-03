package epf.webapp;

import java.nio.file.Path;
import java.security.KeyStore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.client.util.Client;
import epf.client.util.ClientUtil;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.util.security.KeyStoreUtil;

/**
 * 
 */
@ApplicationScoped
public class GatewayUtil {
	
	/**
	 * 
	 */
	private transient final static Logger LOGGER = LogManager.getLogger(GatewayUtil.class.getName());
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		final Path keyStoreFile = ConfigUtil.getPath(Naming.Client.SSL_KEY_STORE);
    	final String keyStoreType = ConfigUtil.getString(Naming.Client.SSL_KEY_STORE_TYPE);
    	final char[] keyStorePassword = ConfigUtil.getChars(Naming.Client.SSL_KEY_STORE_PASSWORD);
    	try {
    		final KeyStore keyStore = KeyStoreUtil.loadKeyStore(keyStoreType, keyStoreFile, keyStorePassword);
			clientUtil.setKeyStore(keyStore, keyStorePassword);
		} 
    	catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[GatewayUtil.keyStore]", e);
		}
    	final Path trustStoreFile = ConfigUtil.getPath(Naming.Client.SSL_TRUST_STORE);
    	final String trustStoreType = ConfigUtil.getString(Naming.Client.SSL_TRUST_STORE_TYPE);
    	final char[] trustStorePassword = ConfigUtil.getChars(Naming.Client.SSL_TRUST_STORE_PASSWORD);
		try {
			final KeyStore trustStore = KeyStoreUtil.loadKeyStore(trustStoreType, trustStoreFile, trustStorePassword);
			clientUtil.setTrustStore(trustStore);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[GatewayUtil.trustStore]", e);
		}
	}
	
	/**
	 * @param service
	 * @return
	 * @throws Exception
	 */
	public Client newClient(final String service) throws Exception {
		return clientUtil.newClient(epf.client.gateway.GatewayUtil.get(service));
	}
}
