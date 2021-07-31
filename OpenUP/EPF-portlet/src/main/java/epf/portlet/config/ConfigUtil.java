/**
 * 
 */
package epf.portlet.config;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import epf.portlet.client.ClientUtil;
import epf.portlet.registry.RegistryUtil;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
@RequestScoped
public class ConfigUtil {

	/**
	 * 
	 */
	@Inject
	private transient ConfigSource configSource;
	
	/**
	 * 
	 */
	@Inject
	private transient RegistryUtil registryUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public String getProperty(final String name) throws Exception {
		if(configSource.isEmpty()) {
			try(Client client = clientUtil.newClient(registryUtil.get("config"))){
				configSource.getProperties(client, "config");
			}
		}
		return configSource.getProperty(name);
	}
}
