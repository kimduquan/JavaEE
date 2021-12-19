package epf.config;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author PC
 *
 */
@ProviderType
public interface ConfigProvider {

	/**
	 * @return
	 */
	Config getConfig();
}
