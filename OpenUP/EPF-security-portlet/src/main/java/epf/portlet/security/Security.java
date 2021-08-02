/**
 * 
 */
package epf.portlet.security;

import epf.client.portlet.security.CredentialView;
import epf.client.security.Credential;
import epf.client.security.Token;
import epf.portlet.CookieUtil;
import epf.portlet.RequestUtil;
import epf.portlet.client.ClientUtil;
import epf.portlet.registry.RegistryUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;
import epf.util.security.PasswordUtil;
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
@Named(Naming.SECURITY)
@ViewScoped
public class Security implements CredentialView, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Security.class.getName());

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
	private transient RegistryUtil registryUtil;
	
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
		final URI securityUrl = registryUtil.get("security");
		final String passwordHash = PasswordUtil.hash(credential.getCaller(), credential.getPassword());
		final URL url = new URL(
				request.getRequest().getScheme(), 
				request.getRequest().getServerName(), 
				request.getRequest().getServerPort(), 
				""
				);
		String rawToken;
		try(Client client = clientUtil.newClient(securityUrl)){
			rawToken = epf.client.security.Security.login(
					client,
					credential.getCaller(), 
					passwordHash, 
					url
					);
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
		final Optional<Cookie> cookie = cookieUtil.getCookie(Naming.SECURITY_TOKEN);
		if(cookie.isPresent() && session.getToken() == null) {
			try(Client client = securityUtil.newClient(registryUtil.get("security"))){
				final Token token = epf.client.security.Security.authenticate(client);
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
