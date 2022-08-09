package epf.tests.health;

import javax.ws.rs.core.Response;
import epf.client.health.Health;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.tests.client.ClientUtil;
import epf.util.config.ConfigUtil;

/**
 * @author PC
 *
 */
public interface HealthUtil {

	/**
	 * @return
	 * @throws Exception
	 */
	static boolean isReady() throws Exception {
		try(Client client = ClientUtil.newClient(ConfigUtil.getURI(Naming.Health.HEALTH_URL))){
			try(Response response = Health.ready(client)){
				if(response.getStatus() == Response.Status.OK.getStatusCode()) {
					try(Client client2 = ClientUtil.newClient(ConfigUtil.getURI(Naming.Gateway.HEALTH_URL))){
						try(Response response2 = Health.ready(client2)){
							return response2.getStatus() == Response.Status.OK.getStatusCode();
						}
					}
				}
				else {
					return false;
				}
			}
		}
	}
}
