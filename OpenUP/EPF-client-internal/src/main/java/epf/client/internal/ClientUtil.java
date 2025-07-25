package epf.client.internal;

import java.net.URI;
import java.security.KeyStore;
import java.util.Objects;
import java.util.Optional;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import epf.client.util.Client;

@Dependent
public class ClientUtil {
	
	private transient Optional<KeyStore> keyStore = Optional.empty();
	
	private transient Optional<char[]> keyPassword = Optional.empty();
	
	private transient Optional<KeyStore> trustStore = Optional.empty();

	@Inject
	protected transient ClientQueue clients;
	
	public void setKeyStore(final KeyStore keyStore, final char[] keyPassword) {
		this.keyStore = Optional.of(keyStore);
		this.keyPassword = Optional.of(keyPassword);
	}
	
	public void setTrustStore(final KeyStore trustStore) {
		this.trustStore = Optional.of(trustStore);
	}
	
	protected ClientBuilder buildClient(final ClientBuilder builder) {
		ClientBuilder newClient = builder;
		if(keyStore.isPresent() && keyPassword.isPresent()) {
			newClient = newClient.keyStore(keyStore.get(), keyPassword.get());
		}
		if(trustStore.isPresent()) {
			newClient = newClient.trustStore(trustStore.get());
		}
		return newClient;
	}
	
	public Client newClient(final URI url) {
		Objects.requireNonNull(url, "URI");
		final jakarta.ws.rs.client.Client rsClient = clients.poll(url, this::buildClient);
		return new Client(rsClient, url, clients::add);
	}
}
