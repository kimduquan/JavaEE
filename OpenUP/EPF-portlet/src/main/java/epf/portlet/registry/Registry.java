/**
 * 
 */
package epf.portlet.registry;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import epf.client.gateway.Gateway;
import epf.portlet.Request;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@RequestScoped
public class Registry {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Registry.class.getName());
	
	/**
	 * 
	 */
	private final Map<String, URI> remotes = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject
	private transient Request request;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientQueue clients;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI gateway = new URI(request.getPreferences().getValue(Gateway.GATEWAY_URL, ""));
			final URI registry = new URI(gateway.toString() + "/registry");
			try(Client client = new Client(clients, registry, b -> b)){
				epf.client.registry.Registry.list(client, null).forEach(link -> {
					remotes.put(link.getRel(), link.getUri());
				});
			}
		}  
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		remotes.clear();
	}
	
	/**
	 * @param name
	 * @return
	 */
	public URI get(final String name) {
		return remotes.get(name);
	}
}
