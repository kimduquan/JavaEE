/**
 * 
 */
package epf.tests.health;

import javax.ws.rs.core.Response;
import epf.client.health.Health;
import epf.tests.client.ClientUtil;
import epf.util.client.Client;
import epf.util.config.ConfigUtil;

/**
 * @author PC
 *
 */
public interface HealthUtil {

	static boolean readỵ̣() throws Exception {
		try(Client client = ClientUtil.newClient(ConfigUtil.getURI(Health.HEALTH_URL))){
			try(Response response = Health.ready(client)){
				return response.getStatus() == Response.Status.OK.getStatusCode();
			}
		}
	}
}
