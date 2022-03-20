package epf.webapp;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Link;
import epf.client.registry.Registry;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Named(Naming.REGISTRY)
public class RegistryUtil {

	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(RegistryUtil.class.getName());
	
	/**
	 * 
	 */
	private final Map<String, String> remotes = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject
	private transient GatewayUtil gatewayUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try(Client client = gatewayUtil.newClient(Naming.REGISTRY)){
			final Set<Link> links = Registry.list(client, null);
			links.forEach(link -> remotes.put(link.getTitle(), link.getUri().toString()));
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[RegistryUtil.remotes]", e);
		}
	}
}
