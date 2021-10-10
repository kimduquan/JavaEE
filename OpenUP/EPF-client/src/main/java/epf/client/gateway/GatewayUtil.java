/**
 * 
 */
package epf.client.gateway;

import java.net.URI;
import epf.util.config.ConfigUtil;

/**
 * @author PC
 *
 */
public interface GatewayUtil {

	/**
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	static URI get(final String name) throws Exception {
		final String uri = ConfigUtil.getString(Gateway.GATEWAY_URL);
		return new URI(uri).resolve(name);
	}
}
