package epf.webapp.internal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import epf.client.internal.ClientUtil;
import epf.client.util.Client;

@ApplicationScoped
public class GatewayUtil {
	
	@Inject
	private transient ClientUtil clientUtil;
	
	public Client newClient(final String service) throws Exception {
		return clientUtil.newClient(epf.client.gateway.GatewayUtil.get(service));
	}
}
