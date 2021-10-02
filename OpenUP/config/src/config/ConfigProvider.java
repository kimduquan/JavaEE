package config;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface ConfigProvider {

	Config getConfig();
}
