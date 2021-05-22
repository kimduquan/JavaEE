/**
 * 
 */
package epf.tests.service;

import java.net.URI;
import java.net.URISyntaxException;
import epf.client.gateway.Gateway;

/**
 * @author PC
 *
 */
public class GatewayUtil {

	private static URI gatewayUrl;
	
	public static URI getGatewayUrl() throws URISyntaxException {
		if(gatewayUrl == null) {
			gatewayUrl = new URI(System.getProperty(Gateway.GATEWAY_URL));
		}
		return gatewayUrl;
	}
}
