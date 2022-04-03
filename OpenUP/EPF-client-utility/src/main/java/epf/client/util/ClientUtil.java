package epf.client.util;

import java.net.URI;
import java.security.KeyStore;
import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;

/**
 * @author PC
 *
 */
@Dependent
public class ClientUtil {
	
	/**
	 * 
	 */
	private transient Optional<KeyStore> keyStore = Optional.empty();
	/**
	 * 
	 */
	private transient Optional<char[]> keyStorePassword = Optional.empty();
	/**
	 * 
	 */
	private transient Optional<KeyStore> trustStore = Optional.empty();

	/**
	 * 
	 */
	@Inject
	protected transient ClientQueue clients;
	
	/**
	 * @param keyStore
	 * @param keyStorePassword
	 */
	public void setKeyStore(final KeyStore keyStore, final char[] keyStorePassword) {
		this.keyStore = Optional.of(keyStore);
		this.keyStorePassword = Optional.of(keyStorePassword);
	}
	
	/**
	 * @param trustStore
	 */
	public void setTrustStore(final KeyStore trustStore) {
		this.trustStore = Optional.of(trustStore);
	}
	
	/**
	 * @param builder
	 * @return
	 */
	protected ClientBuilder buildClient(final ClientBuilder builder) {
		ClientBuilder newClient = builder;
		if(keyStore.isPresent() && keyStorePassword.isPresent()) {
			newClient = newClient.keyStore(keyStore.get(), keyStorePassword.get());
		}
		if(trustStore.isPresent()) {
			newClient = newClient.trustStore(trustStore.get());
		}
		return newClient;
	}
	
	/**
	 * @param url
	 * @return
	 */
	public Client newClient(final URI url) {
		return new Client(clients, url, b -> buildClient(b));
	}
}
