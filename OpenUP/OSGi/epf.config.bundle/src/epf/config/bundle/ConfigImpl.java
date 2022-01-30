package epf.config.bundle;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import epf.config.Config;
import epf.config.ConfigValue;

public class ConfigImpl implements Config {
	
	/**
	 * 
	 */
	private final transient Map<String, ConfigValue> properties = new ConcurrentHashMap<>();
	
	/**
	 * @param props
	 */
	public ConfigImpl(final Properties props) {
		props.entrySet().forEach(entry -> {
			properties.put(entry.getKey().toString(), new ConfigValueImpl(entry));
		});
	}

	@Override
	public ConfigValue getConfigValue(final String propertyName) {
		return properties.get(propertyName);
	}

}
