/**
 * 
 */
package epf.portlet.registry;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.portlet.client.ClientUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@ApplicationScoped
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
	private transient ClientUtil clientUtil;
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected void getRemotes(final URI gatewayUrl) throws Exception {
		final URI registry = new URI(gatewayUrl.toString() + "registry");
		try(Client client = clientUtil.newClient(registry)){
			epf.client.registry.Registry.list(client, null).forEach(link -> {
				remotes.put(link.getRel(), link.getUri());
			});
		}
	}
	
	/**
	 * @param name
	 * @return
	 */
	protected URI get(final String name, final URI gatewayUrl) {
		if(remotes.isEmpty()) {
			try {
				getRemotes(gatewayUrl);
			} 
			catch (Exception e) {
				LOGGER.throwing(getClass().getName(), "getRemotes", e);
			}
		}
		return remotes.get(name);
	}
}
