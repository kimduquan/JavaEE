package epf.config;

public interface Config {

	/**
	 * @param propertyName
	 * @return
	 */
	ConfigValue getConfigValue(final String propertyName);
}
