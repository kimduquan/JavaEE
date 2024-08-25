package epf.portlet.security;

import java.io.Serializable;
import java.net.URI;
import java.time.Instant;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import epf.client.portlet.security.PrincipalView;
import epf.client.util.Client;
import epf.portlet.internal.gateway.GatewayUtil;
import epf.portlet.internal.security.SecurityUtil;
import epf.portlet.util.RequestUtil;
import epf.portlet.util.http.CookieUtil;
import epf.security.client.Credential;
import epf.security.schema.Token;
import epf.portlet.naming.Naming;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.Security.SECURITY_PRINCIPAL)
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
		try(Client client = clientUtil.newClient(gatewayUtil.get(epf.naming.Naming.SECURITY))){
			epf.security.client.Security.logOut(client);
		}
		session.setToken(null);
		cookieUtil.deleteCookie(Naming.Security.SECURITY_TOKEN);
		return "security";
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@Override
	public String update() throws Exception {
		try(Client client = clientUtil.newClient(gatewayUtil.get(epf.naming.Naming.SECURITY))){
			epf.security.client.Security.update(client, new String(credential.getPassword()));
		}
		return "principal";
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@Override
	public void revoke() throws Exception {
		final URI securityUrl = gatewayUtil.get(epf.naming.Naming.SECURITY);
		String rawToken;
		try(Client client = clientUtil.newClient(securityUrl)){
			rawToken = epf.security.client.Security.revoke(client, null);
		}
		Token token;
		try(Client client = clientUtil.newClient(securityUrl)){
			client.authorization(rawToken.toCharArray());
			token = epf.security.client.Security.authenticate(client);
		}
		token.setRawToken(rawToken);
		session.setToken(token);
		final Cookie cookie = new Cookie(Naming.Security.SECURITY_TOKEN, rawToken);
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
