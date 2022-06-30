package epf.webapp.internal;

import java.io.Serializable;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import epf.client.util.Client;
import epf.security.client.Security;
import epf.security.schema.Token;
import epf.util.logging.LogManager;
import epf.webapp.naming.Naming;

/**
 * @author PC
 *
 */
@SessionScoped
@Named(Naming.Security.SESSION)
public class Session implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(Session.class.getName());
	
	/**
	 * 
	 */
	private Map<String, Object> claims;
	
	/**
	 * 
	 */
	private List<String> claimNames;
	
	/**
	 *
	 */
	private transient char[] token;
	
	/**
	 * 
	 */
	@Inject
	private transient GatewayUtil gatewayUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient Event<Principal> event;
	
	/**
	 * @param tokenPrincipal
	 */
	public void initialize(final TokenPrincipal tokenPrincipal) {
		token = tokenPrincipal.getRememberToken() != null ? tokenPrincipal.getRememberToken() : tokenPrincipal.getRawToken();
		try(Client client = gatewayUtil.newClient(epf.naming.Naming.SECURITY)){
			client.authorization(token);
			final Token token = Security.authenticate(client);
			claims = token.getClaims();
			claimNames = Arrays.asList(token.getClaims().keySet().toArray(new String[0]));
			event.fire(tokenPrincipal);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[Session.initialize]", e);
		}
	}

	public List<String> getClaimNames() {
		return claimNames;
	}
	
	/**
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	public String getClaim(final String name) {
		Object claim = null;
		if(claims != null) {
			claim = claims.get(name);
		}
		return claim != null ? String.valueOf(claim) : null;
	}

	public char[] getToken() {
		return token;
	}
	
	/**
	 * 
	 */
	public void clear() {
		if(claims != null) {
			claims.clear();
		}
		if(claimNames != null) {
			claimNames.clear();
		}
		token = null;
	}
}