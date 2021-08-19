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
		return getUrl().resolve(name);
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	static URI getUrl() throws Exception {
		String uri = SystemUtil.getenv(Gateway.GATEWAY_URL);
		if(uri == null || uri.isEmpty()) {
			uri = System.getProperty(Gateway.GATEWAY_URL);
		}
		return new URI(uri);
	}
}
