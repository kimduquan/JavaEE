/**
 * 
 */
package epf.portlet.security;

import epf.client.security.Credential;
import epf.client.security.Token;
import epf.portlet.Portlet;
import epf.portlet.registry.Registry;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import epf.util.logging.Logging;
import epf.util.security.PasswordHelper;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.net.URI;
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
	private transient ClientQueue clients;
	 

	public Credential getCredential() {
		return credential;
	}
	
	/**
	 * @return
	 */
	public String login() {
		try {
			final URI securityUrl = registry.get("security");
			final String passwordHash = PasswordHelper.hash(credential.getCaller(), credential.getPassword());
			try(Client client = new Client(clients, securityUrl, b -> b)){
				final String token = epf.client.security.Security.login(client, null, credential.getCaller(), passwordHash, securityUrl.toURL());
				session.setToken(token);
			}
			try(Client client = new Client(clients, securityUrl, b -> b)){
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
}
