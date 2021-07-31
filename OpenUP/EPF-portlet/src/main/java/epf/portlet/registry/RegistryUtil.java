/**
 * 
 */
package epf.portlet.registry;

import java.net.URI;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import epf.portlet.client.ClientUtil;
import epf.portlet.gateway.GatewayUtil;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
@RequestScoped
public class RegistryUtil {
	
	/**
	 * 
	 */
	@Inject
	private transient GatewayUtil gateway;
	
	/**
	 * 
	 */
	@Inject
	private transient Registry registry;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	public URI get(final String name) throws Exception {
		if(registry.isEmpty()) {
			final URI gatewayUrl = gateway.getGatewayUrl();
			final URI registryUrl = new URI(gatewayUrl.toString() + "registry");
			try(Client client = clientUtil.newClient(registryUrl)){
				registry.list(client);
			}
		}
		return registry.get(name);
	}
}
