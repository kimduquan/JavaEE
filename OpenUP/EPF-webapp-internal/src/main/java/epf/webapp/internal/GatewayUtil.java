package epf.webapp.internal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import epf.client.internal.ClientUtil;
import epf.client.util.Client;

/**
 * 
 */
@ApplicationScoped
public class GatewayUtil {
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * @param service
	 * @return
	 * @throws Exception
	 */
	public Client newClient(final String service) throws Exception {
		return clientUtil.newClient(epf.client.gateway.GatewayUtil.get(service));
	}
}
