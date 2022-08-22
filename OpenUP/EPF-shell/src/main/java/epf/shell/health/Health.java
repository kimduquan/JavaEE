package epf.shell.health;

import java.net.URI;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.client.ClientUtil;
import picocli.CommandLine.Command;

/**
 * @author PC
 *
 */
@Command(name = Naming.HEALTH)
@RequestScoped
@Function
public class Health {
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.Gateway.HEALTH_URL)
	@Inject
	URI healthUrl;
	
	/**
	 * 
	 */
	@Inject
	transient ClientUtil clientUtil;
	
	/**
	 * @return
	 * @throws Exception
	 */
	@Command(name = "ready")
	public String ready() throws Exception {
		final HealthClient health = clientUtil.newClient(healthUrl, HealthClient.class);
		try(Response response = health.ready()){
			return response.readEntity(String.class);
		}
	}
}
