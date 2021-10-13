/**
 * 
 */
package epf.portlet.util.gateway;

import java.net.URI;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import epf.naming.Naming;
import epf.portlet.util.PreferenceUtil;

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
		final URI gatewayUrl = new URI(preferenceUtil.getValue(Naming.Gateway.GATEWAY_URL, ""));
		return gatewayUrl.resolve(name);
	}
}
