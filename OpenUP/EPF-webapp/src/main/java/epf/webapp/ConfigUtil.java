package epf.webapp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import epf.client.config.Config;
import epf.client.util.Client;
import epf.client.util.ClientUtil;
import epf.naming.Naming;
import epf.util.MapUtil;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Named(Naming.CONFIG)
public class ConfigUtil {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(ConfigUtil.class.getName());

	/**
	 * 
	 */
	private final Map<String, String> properties = new ConcurrentHashMap<>();
	
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
		try(Client client = clientUtil.newClient(epf.util.config.ConfigUtil.getURI(Naming.Gateway.GATEWAY_URL))){
			final Map<String, String> props = Config.getProperties(client, "");
			props.forEach(properties::put);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	/**
	 * @param name
	 * @return
	 */
	public String getProperty(final String name) {
		return MapUtil.get(properties, name).orElse("");
	}
}
