package epf.client.gateway;

import java.net.URI;
import epf.naming.Naming;
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
		return ConfigUtil.getURI(Naming.Gateway.GATEWAY_URL).resolve(name);
	}
	
	/**
	 * @param gatewayUrl
	 * @param name
	 * @return
	 * @throws Exception
	 */
	static URI get(final String gatewayUrl, final String name) throws Exception {
		return new URI(gatewayUrl).resolve(name);
	}
}
