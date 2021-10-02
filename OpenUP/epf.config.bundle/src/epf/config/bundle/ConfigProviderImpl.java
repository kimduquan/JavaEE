package epf.config.bundle;

import org.osgi.service.component.annotations.*;
import epf.config.Config;
import epf.config.ConfigProvider;

@Component
public class ConfigProviderImpl implements ConfigProvider {
	
	/**
	 * 
	 */
	private transient final Config config = new ConfigImpl();

	@Override
	public Config getConfig() {
		return config;
	}
}
