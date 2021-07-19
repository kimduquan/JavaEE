package epf.client.registry;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.client.gateway.Gateway;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import epf.util.SystemUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class LocateRegistry {
	
	/**
	 * 
	 */
	private final transient Map<String, URI> remotes;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientQueue clients;
	
	/**
	 * 
	 */
	@Inject
	private transient Logger logger;
	
	/**
	 * 
	 */
	public LocateRegistry() {
		remotes = new ConcurrentHashMap<>();
	}
    
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try(Client client = new Client(clients, new URI(SystemUtil.getenv(Gateway.GATEWAY_URL)).resolve("registry"), b -> b)){
			Registry
			.list(client, null)
			.forEach(link -> {
				remotes.put(link.getRel(), link.getUri());
			});
		} 
		catch (Exception e) {
			logger.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	/**
	 * @param name
	 * @return
	 */
	public URI lookup(final String name) {
		return remotes.get(name);
	}
}
