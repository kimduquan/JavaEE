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
import javax.portlet.PortletPreferences;
import epf.portlet.Application;
import epf.portlet.gateway.Gateway;
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
	private transient Application application;
	
	/**
	 * 
	 */
	@Inject
	private transient Gateway gateway;
	
	/**
	 * @throws Exception 
	 * 
	 */
	protected void getRemotes(final PortletPreferences preferences) throws Exception {
		final URI gatewayUrl = gateway.getGatewayUrl(preferences);
		final URI registry = new URI(gatewayUrl.toString() + "registry");
		try(Client client = new Client(application.getClients(), registry, b -> b)){
			epf.client.registry.Registry.list(client, null).forEach(link -> {
				remotes.put(link.getRel(), link.getUri());
			});
		}
	}
	
	/**
	 * @param name
	 * @return
	 */
	public URI get(final String name, final PortletPreferences preferences) {
		if(remotes.isEmpty()) {
			try {
				getRemotes(preferences);
			} 
			catch (Exception e) {
				LOGGER.throwing(getClass().getName(), "getRemotes", e);
			}
		}
		return remotes.get(name);
	}
}
