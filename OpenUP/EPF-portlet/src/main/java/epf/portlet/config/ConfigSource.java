/**
 * 
 */
package epf.portlet.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;

import epf.client.util.Client;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class ConfigSource {

	/**
	 * 
	 */
	private transient final Map<String, String> properties = new ConcurrentHashMap<>();
	
	/**
	 * @param client
	 */
	protected void getProperties(final Client client, final String name) {
		epf.client.config.Config.getProperties(client, name).forEach(properties::put);
	}
	
	/**
	 * @return
	 */
	public boolean isEmpty() {
		return properties.isEmpty();
	}
	
	/**
	 * @param name
	 * @return
	 */
	public String getProperty(final String name) {
		return properties.get(name);
	}
}
