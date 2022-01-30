package epf.webapp;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.ws.rs.core.Link;
import epf.client.config.Config;
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
@FacesConfig
public class WebApp {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(WebApp.class.getName());

	/**
	 * 
	 */
	private final Map<String, String> properties = new ConcurrentHashMap<>();
	
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
			final Map<String, String> props = Config.getProperties(client, "");
			props.forEach(properties::put);
			final Set<Link> links = Registry.list(client, null);
			links.forEach(link -> remotes.put(link.getTitle(), link.getUri().toString()));
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	/**
	 * @param name
	 * @return
	 */
	public String getUrl(final String name) {
		return MapUtil.get(remotes, name).orElse(MapUtil.get(properties, name).orElse(""));
	}
}
