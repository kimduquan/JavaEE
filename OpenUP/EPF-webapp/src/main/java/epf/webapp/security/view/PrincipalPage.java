package epf.webapp.security.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.microprofile.jwt.Claims;
import epf.client.util.Client;
import epf.security.client.Security;
import epf.security.schema.Token;
import epf.security.view.PrincipalView;
import epf.util.logging.LogManager;
import epf.webapp.GatewayUtil;
import epf.webapp.naming.Naming;
import epf.webapp.security.TokenPrincipal;

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
	private transient Token token;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		final TokenPrincipal principal = (TokenPrincipal) context.getCallerPrincipal();
		try(Client client = gatewayUtil.newClient(epf.naming.Naming.SECURITY)){
			client.authorization(principal.getRememberToken().orElse(principal.getRawToken()));
			token = Security.authenticate(client);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[PrincipalPage.token]", e);
		}
	}
	
	/**
	 * @return
	 */
	@Override
	public String getName() {
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
		final List<String> names = Arrays.asList(token.getClaims().keySet().toArray(new String[0]));
		Collections.sort(names);
		return names;
	}

	@Override
	public String getClaim(final String name) {
		return String.valueOf(token.getClaims().get(name));
	}
}
