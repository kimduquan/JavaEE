package epf.config.bundle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import epf.config.Config;
import epf.config.ConfigValue;

public class ConfigImpl implements Config {
	
	/**
	 * 
	 */
	private final transient Map<String, ConfigValue> properties = new ConcurrentHashMap<>();

	@Override
	public ConfigValue getConfigValue(final String propertyName) {
		return properties.get(propertyName);
	}

}
