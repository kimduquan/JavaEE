package epf.portlet.internal.security;

import java.net.URI;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import epf.client.util.Client;
import epf.portlet.internal.client.ClientUtil;
import epf.portlet.naming.Naming;
import epf.portlet.util.http.CookieUtil;

/**
 * @author PC
 *
 */
@RequestScoped
public class SecurityUtil {

	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
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
		final Client client = clientUtil.newClient(url);
		cookieUtil.getCookie(Naming.Security.SECURITY_TOKEN).ifPresent(cookie -> {
			client.authorization(cookie.getValue().toCharArray());
		});
		return client;
	}
	
}
