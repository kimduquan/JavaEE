package epf.client.gateway;

import java.net.URI;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;

public interface GatewayUtil {

	static URI get(final String name) throws Exception {
		return ConfigUtil.getURI(Naming.Gateway.GATEWAY_URL).resolve(name);
	}
	
	static URI get(final String gatewayUrl, final String name) throws Exception {
		return new URI(gatewayUrl).resolve(name);
	}
}
