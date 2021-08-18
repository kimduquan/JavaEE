/**
 * 
 */
package epf.portlet.config;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import epf.portlet.gateway.GatewayUtil;
import epf.portlet.security.SecurityUtil;
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
	private transient GatewayUtil gatewayUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityUtil clientUtil;
	
	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public String getProperty(final String name) throws Exception {
		if(configSource.isEmpty()) {
			try(Client client = clientUtil.newClient(gatewayUtil.get("config"))){
				configSource.getProperties(client, "config");
			}
		}
		return configSource.getProperty(name);
	}
}
