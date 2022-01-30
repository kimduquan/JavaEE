package epf.webapp;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Link;
import epf.client.registry.Registry;
import epf.client.util.Client;
import epf.client.util.ClientUtil;
import epf.naming.Naming;
import epf.util.MapUtil;
import epf.util.config.ConfigUtil;
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
	private transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try(Client client = clientUtil.newClient(ConfigUtil.getURI(Naming.Gateway.GATEWAY_URL))){
			final Set<Link> links = Registry.list(client, null);
			links.forEach(link -> remotes.put(link.getTitle(), link.getUri().toString()));
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	public String getSecurityUrl() {
		return MapUtil.get(remotes, Naming.WebApp.SECURITY_WEB_APP_URL).orElse("");
	}
}
