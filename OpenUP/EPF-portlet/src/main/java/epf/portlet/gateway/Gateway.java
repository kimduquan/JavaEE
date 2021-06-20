/**
 * 
 */
package epf.portlet.gateway;

import java.net.URI;
import javax.enterprise.context.ApplicationScoped;
import javax.portlet.PortletPreferences;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Gateway {

	/**
	 * @param preferences
	 * @return
	 * @throws Exception
	 */
	public URI getGatewayUrl(final PortletPreferences preferences) throws Exception {
		return new URI(preferences.getValue(epf.client.gateway.Gateway.GATEWAY_URL, ""));
	}
}
