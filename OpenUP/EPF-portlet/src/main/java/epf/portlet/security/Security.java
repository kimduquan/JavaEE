/**
 * 
 */
package epf.portlet.security;

import epf.client.security.Credential;
import epf.client.security.Token;
import epf.portlet.Application;
import epf.portlet.Portlet;
import epf.portlet.Request;
import epf.portlet.registry.Registry;
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
@Named(Portlet.SECURITY)
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
	private transient Request request;
	
	/**
	 * 
	 */
	@Inject 
	private transient Session session;
	
	/**
	 * 
	 */
	@Inject
	private transient Registry registry;
	
	/**
	 * 
	 */
	@Inject
	private transient Application application;
	 

	public Credential getCredential() {
		return credential;
	}
	
	/**
	 * @return
	 */
	public String login() {
		try {
			final URI securityUrl = registry.get("security", request.getPreferences());
			final String passwordHash = PasswordHelper.hash(credential.getCaller(), credential.getPassword());
			final URL url = new URL(request.getRequest().getScheme(), request.getRequest().getServerName(), request.getRequest().getServerPort(), "");
			try(Client client = new Client(application.getClients(), securityUrl, b -> b)){
				final String token = epf.client.security.Security.login(
						client, 
						null, 
						credential.getCaller(), 
						passwordHash, 
						url
						);
				session.setToken(token);
			}
			try(Client client = new Client(application.getClients(), securityUrl, b -> b)){
				client.authorization(session.getToken());
				final Token token = epf.client.security.Security.authenticate(client, null);
				session.setPrincipal(token);
			}
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
		final URI securityUrl = registry.get("security", request.getPreferences());
		try(Client client = new Client(application.getClients(), securityUrl, b -> b)){
			client.authorization(session.getToken());
			epf.client.security.Security.logOut(client, null);
			session.setToken(null);
			session.setPrincipal(null);
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
		final URI securityUrl = registry.get("security", request.getPreferences());
		final Map<String, String> info = new HashMap<>();
		info.put("password", new String(credential.getPassword()));
		try(Client client = new Client(application.getClients(), securityUrl, b -> b)){
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
		final URI securityUrl = registry.get("security", request.getPreferences());
		try(Client client = new Client(application.getClients(), securityUrl, b -> b)){
			client.authorization(session.getToken());
			epf.client.security.Security.revoke(client, null);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "revoke", e);
		}
		return "security";
	}
}
