/**
 * 
 */
package epf.portlet.security;

import java.io.Serializable;
import java.net.URI;
import java.time.Instant;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import epf.client.portlet.security.PrincipalView;
import epf.client.security.Credential;
import epf.client.security.Token;
import epf.portlet.CookieUtil;
import epf.portlet.RequestUtil;
import epf.portlet.gateway.GatewayUtil;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.SECURITY_PRINCIPAL)
public class Principal implements PrincipalView, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private final Credential credential = new Credential();
	
	/**
	 * 
	 */
	@Inject
	private Session session;
	
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
	 * 
	 */
	@Inject
	private transient CookieUtil cookieUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient RequestUtil requestUtil;
	
	/**
	 * @return
	 */
	public boolean isExpired() {
		return session.getToken() != null && Instant.now().getEpochSecond() > session.getToken().getExpirationTime();
	}

	/**
	 * @return
	 * @throws Exception 
	 */
	@Override
	public String logout() throws Exception {
		try(Client client = clientUtil.newClient(gatewayUtil.get("security"))){
			epf.client.security.Security.logOut(client);
		}
		session.setToken(null);
		cookieUtil.deleteCookie(Naming.SECURITY_TOKEN);
		return "security";
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@Override
	public String update() throws Exception {
		try(Client client = clientUtil.newClient(gatewayUtil.get("security"))){
			epf.client.security.Security.update(client, new String(credential.getPassword()));
		}
		return "principal";
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@Override
	public void revoke() throws Exception {
		final URI securityUrl = gatewayUtil.get("security");
		String rawToken;
		try(Client client = clientUtil.newClient(securityUrl)){
			rawToken = epf.client.security.Security.revoke(client);
		}
		Token token;
		try(Client client = clientUtil.newClient(securityUrl)){
			client.authorization(rawToken);
			token = epf.client.security.Security.authenticate(client);
		}
		token.setRawToken(rawToken);
		session.setToken(token);
		final Cookie cookie = new Cookie(Naming.SECURITY_TOKEN, rawToken);
		cookie.setMaxAge((int)(token.getExpirationTime() - Instant.EPOCH.getEpochSecond()));
		cookie.setDomain(requestUtil.getRequest().getServerName());
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookieUtil.addCookie(cookie);
	}

	@Override
	public char[] getPassword() {
		return credential.getPassword();
	}

	@Override
	public void setPassword(final char... password) {
		credential.setPassword(password);
	}

	@Override
	public String getFullName() {
		return (String)session.getToken().getClaims().get("full_name");
	}
}
