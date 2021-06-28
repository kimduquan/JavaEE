/**
 * 
 */
package epf.portlet.registry;

import java.net.URI;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import epf.portlet.gateway.GatewayUtil;

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
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	public URI get(final String name) throws Exception {
		final URI gatewayUrl = gateway.getGatewayUrl();
		return registry.get(name, gatewayUrl);
	}
}
