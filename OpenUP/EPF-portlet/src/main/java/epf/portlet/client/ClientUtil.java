/**
 * 
 */
package epf.portlet.client;

import java.net.URI;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import epf.portlet.CookieUtil;
import epf.portlet.security.Naming;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
@RequestScoped
public class ClientUtil {

	/**
	 * 
	 */
	@Inject
	private transient Application application;
	
	/**
	 * 
	 */
	@Inject
	private transient CookieUtil cookieUtil;
	
	/**
	 * @param service
	 * @return
	 */
	public Client newClient(final URI url) {
		final Client client = new Client(application.getClients(), url, b -> b);
		cookieUtil.getCookie(Naming.SECURITY_TOKEN).ifPresent(cookie -> {
			client.authorization(cookie.getValue());
		});
		return client;
	}
	
}
