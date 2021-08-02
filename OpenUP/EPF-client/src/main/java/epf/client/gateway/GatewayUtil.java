/**
 * 
 */
package epf.client.gateway;

import java.net.URI;
import epf.util.SystemUtil;

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
		return new URI(SystemUtil.getenv(Gateway.GATEWAY_URL)).resolve(name);
	}
}
