package epf.webapp.security.view;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.microprofile.jwt.Claims;
import epf.client.util.Client;
import epf.security.auth.Provider;
import epf.security.auth.core.StandardClaims;
import epf.security.auth.core.UserInfo;
import epf.security.client.Security;
import epf.security.schema.Token;
import epf.security.view.PrincipalView;
import epf.util.logging.LogManager;
import epf.webapp.GatewayUtil;
import epf.webapp.naming.Naming;
import epf.webapp.security.TokenPrincipal;
import epf.webapp.security.auth.OpenIDPrincipal;
import epf.webapp.security.auth.SecurityAuth;

/**
 * @author PC
 *
 */
@RequestScoped
@Named(Naming.Security.PRINCIPAL)
public class PrincipalPage implements PrincipalView {
	
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
	private transient SecurityAuth securityAuth;
	
	/**
	 * 
	 */
	private transient Token token;
	
	/**
	 * 
	 */
	private transient UserInfo userInfo;
	
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
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[PrincipalPage.token]", e);
			}
		}
		else if(principal instanceof OpenIDPrincipal) {
			final OpenIDPrincipal openidPrincipal = (OpenIDPrincipal) principal;
			try {
				final Provider provider = securityAuth.getProvider(openidPrincipal.getProviderMetadata().getIssuer());
				userInfo = provider.getUserInfo(openidPrincipal.getToken().getAccess_token(), openidPrincipal.getToken().getToken_type());
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[PrincipalPage.userInfo]", e);
			}
		}
	}
	
	/**
	 * @return
	 */
	@Override
	public String getName() {
		if(userInfo != null) {
			return userInfo.getName();
		}
		return token.getClaims().get(Claims.full_name.name()).toString();
	}

	@Override
	public String logout() throws Exception {
		request.logout();
		request.getSession(false).invalidate();
		return Naming.DEFAULT_VIEW;
	}

	@Override
	public List<String> getClaimNames() {
		if(userInfo != null) {
			return Arrays.asList(StandardClaims.values()).stream().map(claim -> claim.name()).collect(Collectors.toList());
		}
		final List<String> names = Arrays.asList(token.getClaims().keySet().toArray(new String[0]));
		Collections.sort(names);
		return names;
	}

	@Override
	public String getClaim(final String name) {
		if(userInfo != null) {
			return "";
		}
		return String.valueOf(token.getClaims().get(name));
	}
}
