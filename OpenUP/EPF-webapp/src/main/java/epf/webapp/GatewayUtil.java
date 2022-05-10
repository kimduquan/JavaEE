package epf.webapp;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.client.util.Client;
import epf.client.util.ClientUtil;

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
	 *
	 */
	@Inject
	private transient SecurityUtil securityUtil;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		clientUtil.setKeyStore(securityUtil.getKeyStore(), securityUtil.getKeyStorePassword());
		clientUtil.setTrustStore(securityUtil.getTrustStore());
	}
	
	/**
	 * @param service
	 * @return
	 * @throws Exception
	 */
	public Client newClient(final String service) throws Exception {
		return clientUtil.newClient(epf.client.gateway.GatewayUtil.get(service));
	}
}
