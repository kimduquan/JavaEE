/**
 * 
 */
package epf.portlet.security;

import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import epf.client.security.Token;
import epf.portlet.Naming;
import epf.portlet.client.ClientUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;
import java.io.Serializable;
import java.net.URI;
import java.time.Instant;
import java.util.logging.Logger;

/**
 * @author PC
 *
 */
@SessionScoped
@Named(Naming.SECURITY_SESSION)
public class Session implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Session.class.getName());
	
	/**
	 * 
	 */
	private Token token;
	
	/**
	 * 
	 */
	private URI securityUrl;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		if(token != null && securityUrl != null)
		try(Client client = clientUtil.newClient(securityUrl)){
			client.authorization(token.getRawToken());
			epf.client.security.Security.logOut(client, null);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "preDestroy", e);
		}
	}
	
	/**
	 * @param url
	 */
	protected void setSecurityUrl(final URI url) {
		this.securityUrl = url;
	}
	
	/**
	 * @return
	 */
	public boolean isExpired() {
		return token != null && Instant.now().getEpochSecond() > token.getExpirationTime();
	}

	public Token getToken() {
		return token;
	}

	public void setToken(final Token token) {
		this.token = token;
	}
}
