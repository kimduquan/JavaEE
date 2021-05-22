/**
 * 
 */
package epf.shell.gateway;

import java.net.URI;
import java.net.URISyntaxException;
import epf.client.gateway.Gateway;
import epf.util.Var;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class GatewayUtil {

	/**
	 * @return
	 * @throws URISyntaxException
	 */
	@Produces @ApplicationScoped @Named(Gateway.GATEWAY_URL)
	public static Var<URI> getGateWayUrl() throws URISyntaxException {
		String url = System.getenv(Gateway.GATEWAY_URL);
		if(url == null || url.isEmpty()) {
			url = System.getProperty(Gateway.GATEWAY_URL);
		}
		return new Var<>(new URI(url));
	}
}
