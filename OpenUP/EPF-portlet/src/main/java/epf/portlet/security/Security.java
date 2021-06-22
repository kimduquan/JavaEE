/**
 * 
 */
package epf.portlet.security;

import epf.client.security.Credential;
import epf.client.security.Token;
import epf.portlet.Event;
import epf.portlet.EventUtil;
import epf.portlet.Name;
import epf.portlet.RequestUtil;
import epf.portlet.SessionUtil;
import epf.portlet.client.ClientUtil;
import epf.portlet.registry.RegistryUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;
import epf.util.security.PasswordHelper;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author PC
 *
 */
@Named(Name.SECURITY)
@RequestScoped
public class Security implements Serializable {
	
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
	private transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient EventUtil eventUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient SessionUtil sessionUtil;
	 

	public Credential getCredential() {
		return credential;
	}
	
	/**
	 * @return
	 */
	public String login() {
		try {
			final URI securityUrl = registryUtil.get("security");
			final String passwordHash = PasswordHelper.hash(credential.getCaller(), credential.getPassword());
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
						null, 
						credential.getCaller(), 
						passwordHash, 
						url
						);
			}
			Token token;
			try(Client client = clientUtil.newClient(securityUrl)){
				client.authorization(rawToken);
				token = epf.client.security.Security.authenticate(client, null);
			}
			final Principal principal = newPrincipal(rawToken, token);
			session.setPrincipal(principal);
			sessionUtil.setAttribute(Name.SECURITY_PRINCIPAL, principal);
			eventUtil.setEvent(Event.SECURITY_PRINCIPAL, principal);
			return "session";
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "login", e);
		}
		return "";
	}
	
	/**
	 * @return
	 */
	public String logout() {
		try(Client client = clientUtil.newClient(registryUtil.get("security"))){
			client.authorization(session.getToken());
			epf.client.security.Security.logOut(client, null);
			session.setPrincipal(null);
			sessionUtil.setAttribute(Name.SECURITY_PRINCIPAL, null);
			eventUtil.setEvent(Event.SECURITY_PRINCIPAL, null);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "logout", e);
		}
		return "security";
	}
	
	/**
	 * @return
	 */
	public String update() {
		final Map<String, String> info = new HashMap<>();
		info.put("password", new String(credential.getPassword()));
		try(Client client = clientUtil.newClient(registryUtil.get("security"))){
			client.authorization(session.getToken());
			epf.client.security.Security.update(client, null, info);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "update", e);
		}
		return "security";
	}
	
	/**
	 * @return
	 */
	public String revoke() {
		try {
			final URI securityUrl = registryUtil.get("security");
			String rawToken;
			try(Client client = clientUtil.newClient(securityUrl)){
				client.authorization(session.getToken());
				rawToken = epf.client.security.Security.revoke(client, null);
			}
			Token token;
			try(Client client = clientUtil.newClient(securityUrl)){
				client.authorization(rawToken);
				token = epf.client.security.Security.authenticate(client, null);
			}
			final Principal principal = newPrincipal(rawToken, token);
			session.setPrincipal(principal);
			sessionUtil.setAttribute(Name.SECURITY_PRINCIPAL, principal);
			eventUtil.setEvent(Event.SECURITY_PRINCIPAL, principal);
		}
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "revoke", e);
		}
		return "security";
	}
	
	/**
	 * @return
	 */
	public Principal getPrincipal() {
		return sessionUtil.getAttribute(Name.SECURITY_PRINCIPAL);
	}
	
	/**
	 * @param rawToken
	 * @param token
	 * @return
	 */
	protected static Principal newPrincipal(final String rawToken, final Token token) {
		token.setRawToken(rawToken);
		final Principal principal = new Principal();
		principal.setToken(token);
		return principal;
	}
}
