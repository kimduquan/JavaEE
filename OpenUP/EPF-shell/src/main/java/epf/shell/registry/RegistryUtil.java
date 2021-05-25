/**
 * 
 */
package epf.shell.registry;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import epf.client.gateway.Gateway;
import epf.client.registry.Registry;
import epf.shell.client.ClientUtil;
import epf.util.Var;
import epf.util.client.Client;
import epf.util.logging.Logging;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * @author PC
 *
 */
@RequestScoped
public class RegistryUtil {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(RegistryUtil.class.getName());

	/**
	 * 
	 */
	private transient final Map<String, URI> remotes = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject @Named(Gateway.GATEWAY_URL)
	private transient Var<URI> gatewayUrl;
	
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
		final URI uri = gatewayUrl.get();
		try(Client client = clientUtil.newClient(uri.resolve("registry"))){
			Registry.list(client, null)
			.forEach(link -> {
				remotes.put(link.getRel(), link.getUri());
			});
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	/**
	 * @return
	 * @throws MalformedURLException 
	 */
	@Produces @Named(epf.client.security.Security.SECURITY_URL)
	public Var<URI> getSecurityUrl() throws MalformedURLException {
		return new Var<>(remotes.get("security"));
	}
	
	/**
	 * @return
	 * @throws MalformedURLException
	 */
	@Produces @Named(epf.client.file.Files.FILE_URL)
	public Var<URI> getFileUrl() throws MalformedURLException {
		return new Var<>(remotes.get("file"));
	}
	
	/**
	 * @return
	 */
	@Produces @Named(epf.client.persistence.Persistence.PERSISTENCE_URL)
	public Var<URI> getPersistenceUrl(){
		return new Var<>(remotes.get("persistence"));
	}
}
