package config;

public interface Config {

	ConfigValue getConfigValue(String propertyName);
	
	Iterable<String> getPropertyNames();
}
