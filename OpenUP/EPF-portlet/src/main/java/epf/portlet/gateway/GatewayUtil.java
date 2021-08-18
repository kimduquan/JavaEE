/**
 * 
 */
package epf.portlet.gateway;

import java.net.URI;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import epf.portlet.PreferenceUtil;

/**
 * @author PC
 *
 */
@RequestScoped
public class GatewayUtil {
	
	/**
	 * 
	 */
	@Inject
	private transient PreferenceUtil preferenceUtil;

	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public URI get(final String name) throws Exception {
		final URI gatewayUrl = new URI(preferenceUtil.getValue(epf.client.gateway.Gateway.GATEWAY_URL, ""));
		return gatewayUrl.resolve(name);
	}
}
