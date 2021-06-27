/**
 * 
 */
package epf.portlet.security;

import epf.client.security.Credential;
import epf.client.security.Token;
import epf.portlet.Event;
import epf.portlet.EventUtil;
import epf.portlet.Naming;
import epf.portlet.RequestUtil;
import epf.portlet.SessionUtil;
import epf.portlet.client.ClientUtil;
import epf.portlet.registry.RegistryUtil;
import epf.util.client.Client;
import epf.util.security.PasswordUtil;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author PC
 *
 */
@Named(Naming.SECURITY)
@RequestScoped
public class Security {
	
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
	 * @throws Exception 
	 */
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
		session.setSecurityUrl(securityUrl);
		sessionUtil.setAttribute(Naming.SECURITY_TOKEN, token);
		eventUtil.setEvent(Event.SECURITY_TOKEN, token);
		return "session";
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	public String logout() throws Exception {
		try(Client client = clientUtil.newClient(registryUtil.get("security"))){
			client.authorization(session.getToken().getRawToken());
			epf.client.security.Security.logOut(client);
			session.setToken(null);
			sessionUtil.setAttribute(Naming.SECURITY_TOKEN, null);
			eventUtil.setEvent(Event.SECURITY_TOKEN, null);
		}
		return "security";
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	public String update() throws Exception {
		final Map<String, String> info = new HashMap<>();
		info.put("password", new String(credential.getPassword()));
		try(Client client = clientUtil.newClient(registryUtil.get("security"))){
			client.authorization(session.getToken().getRawToken());
			epf.client.security.Security.update(client, info);
		}
		return "security";
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	public String revoke() throws Exception {
		final URI securityUrl = registryUtil.get("security");
		String rawToken;
		try(Client client = clientUtil.newClient(securityUrl)){
			client.authorization(session.getToken().getRawToken());
			rawToken = epf.client.security.Security.revoke(client);
		}
		Token token;
		try(Client client = clientUtil.newClient(securityUrl)){
			client.authorization(rawToken);
			token = epf.client.security.Security.authenticate(client);
		}
		token.setRawToken(rawToken);
		session.setToken(token);
		session.setSecurityUrl(securityUrl);
		sessionUtil.setAttribute(Naming.SECURITY_TOKEN, token);
		eventUtil.setEvent(Event.SECURITY_TOKEN, token);
		return "security";
	}
}
