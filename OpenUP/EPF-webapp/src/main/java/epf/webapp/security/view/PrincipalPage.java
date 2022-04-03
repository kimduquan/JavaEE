package epf.webapp.security.view;

import java.io.Serializable;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.microprofile.jwt.Claims;
import epf.client.util.Client;
import epf.security.auth.core.StandardClaims;
import epf.security.client.Security;
import epf.security.schema.Token;
import epf.security.view.PrincipalView;
import epf.util.logging.LogManager;
import epf.webapp.GatewayUtil;
import epf.webapp.naming.Naming;
import epf.webapp.security.TokenPrincipal;
import epf.webapp.security.auth.IDTokenPrincipal;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.Security.PRINCIPAL)
public class PrincipalPage implements PrincipalView, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(PrincipalPage.class.getName());
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityContext context;
	
	/**
	 * 
	 */
	@Inject
	private transient GatewayUtil gatewayUtil;
	
	/**
	 * 
	 */
	@Inject
	private transient HttpServletRequest request;
	
	/**
	 * 
	 */
	@Inject
    private transient ExternalContext externalContext;
	
	/**
	 * 
	 */
	private Token token;
	
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
	@PostConstruct
	protected void postConstruct() {
		final Principal principal = context.getCallerPrincipal();
		if(principal instanceof TokenPrincipal) {
			final TokenPrincipal tokenPrincipal = (TokenPrincipal) principal;
			try(Client client = gatewayUtil.newClient(epf.naming.Naming.SECURITY)){
				client.authorization(tokenPrincipal.getRememberToken().orElse(tokenPrincipal.getRawToken()));
				token = Security.authenticate(client);
				claimNames = Arrays.asList(token.getClaims().keySet().toArray(new String[0]));
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[PrincipalPage.token]", e);
			}
		}
		else if(principal instanceof IDTokenPrincipal) {
			final IDTokenPrincipal idTokenPrincipal = (IDTokenPrincipal) principal;
			try {
				claims = idTokenPrincipal.getClaims();
				claimNames = Arrays.asList(StandardClaims.values()).stream().map(claim -> claim.name()).collect(Collectors.toList());
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[PrincipalPage.claims]", e);
			}
		}
	}
	
	/**
	 * @return
	 */
	@Override
	public String getName() {
		if(claims != null) {
			return claims.get(StandardClaims.name.name()).toString();
		}
		return token.getClaims().get(Claims.full_name.name()).toString();
	}

	@Override
	public String logout() throws Exception {
		request.logout();
		externalContext.invalidateSession();
		externalContext.redirect(Naming.CONTEXT_ROOT);
		return "";
	}

	@Override
	public List<String> getClaimNames() {
		return claimNames;
	}

	@Override
	public String getClaim(final String name) {
		Object claim = null;
		if(claims != null) {
			claim = claims.get(name);
		}
		else {
			claim = token.getClaims().get(name);
		}
		return claim != null ? String.valueOf(claim) : null;
	}
}
