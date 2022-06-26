package epf.webapp.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.client.config.Config;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.util.MapUtil;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class ConfigSource {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(ConfigSource.class.getName());

	/**
	 * 
	 */
	private final Map<String, String> properties = new ConcurrentHashMap<>();
	
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
		try(Client client = gatewayUtil.newClient(Naming.CONFIG)){
			final Map<String, String> props = Config.getProperties(client, "");
			props.forEach(properties::put);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[ConfigSource.properties]", e);
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
