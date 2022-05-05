package epf.portlet.security;

import epf.client.portlet.security.CredentialView;
import epf.client.util.Client;
import epf.portlet.internal.client.ClientUtil;
import epf.portlet.internal.gateway.GatewayUtil;
import epf.portlet.internal.security.SecurityUtil;
import epf.portlet.naming.Naming;
import epf.portlet.util.RequestUtil;
import epf.portlet.util.http.CookieUtil;
import epf.security.client.Credential;
import epf.security.schema.Token;
import epf.util.logging.LogManager;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author PC
 *
 */
@Named(Naming.Security.SECURITY)
@ViewScoped
public class Security implements CredentialView, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Security.class.getName());

	/**
	 * 
	 */
	private final Credential credential = new Credential();
	
	/**
	 * 
	 */
	@Inject
	private transient RequestUtil request;
	
	/**
	 * 
	 */
	@Inject 
	private transient Session session;
	
	/**
	 * 
	 */
	@Inject
	private transient GatewayUtil gatewayUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityUtil securityUtil;
	
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
	 * @return
	 * @throws Exception 
	 */
	@Override
	public String login() throws Exception {
		final URI securityUrl = gatewayUtil.get(epf.naming.Naming.SECURITY);
		final URL url = new URL(
				request.getRequest().getScheme(), 
				request.getRequest().getServerName(), 
				request.getRequest().getServerPort(), 
				""
				);
		String rawToken;
		try(Client client = clientUtil.newClient(securityUrl)){
			rawToken = epf.security.client.Security.login(
					client,
					credential.getCaller(), 
					new String(credential.getPassword()), 
					url
					);
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
		cookie.setDomain(request.getRequest().getServerName());
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookieUtil.addCookie(cookie);
		return "principal";
	}
	
	/**
	 * @return
	 */
	public String authenticate() {
		final Optional<Cookie> cookie = cookieUtil.getCookie(Naming.Security.SECURITY_TOKEN);
		if(cookie.isPresent() && session.getToken() == null) {
			try(Client client = securityUtil.newClient(gatewayUtil.get(epf.naming.Naming.SECURITY))){
				final Token token = epf.security.client.Security.authenticate(client);
				final String rawToken = cookie.get().getValue();
				token.setRawToken(rawToken);
				session.setToken(token);
			} 
			catch (Exception e) {
				LOGGER.throwing(getClass().getName(), "authenticate", e);
			}
		}
		if(!cookie.isPresent() || session.getToken() == null) {
			return "security";
		}
		return "principal";
	}

	@Override
	public String getCaller() {
		return credential.getCaller();
	}

	@Override
	public void setCaller(final String caller) {
		credential.setCaller(caller);
	}

	@Override
	public char[] getPassword() {
		return credential.getPassword();
	}

	@Override
	public void setPassword(final char... password) {
		credential.setPassword(password);
	}
}
