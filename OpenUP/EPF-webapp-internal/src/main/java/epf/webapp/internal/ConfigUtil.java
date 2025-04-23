package epf.webapp.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import epf.client.internal.ClientUtil;
import epf.client.util.Client;
import epf.config.client.Config;
import epf.naming.Naming;
import epf.util.MapUtil;
import epf.util.logging.LogManager;

@ApplicationScoped
public class ConfigUtil {
	
	private static final Logger LOGGER = LogManager.getLogger(ConfigUtil.class.getName());

	private final Map<String, String> properties = new ConcurrentHashMap<>();
	
	@Inject
	private transient ClientUtil clientUtil;

	@PostConstruct
	protected void postConstruct() {
		try(Client client = clientUtil.newClient(epf.util.config.ConfigUtil.getURI(Naming.Config.CONFIG_URL))){
			final Map<String, String> props = Config.getProperties(client, "");
			props.forEach(properties::put);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[ConfigSource.properties]", e);
		}
	}
	
	public String getProperty(final String name) {
		return MapUtil.get(properties, name).orElse("");
	}
}
