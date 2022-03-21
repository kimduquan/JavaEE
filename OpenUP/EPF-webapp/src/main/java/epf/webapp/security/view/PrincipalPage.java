package epf.webapp.security.view;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import org.eclipse.microprofile.jwt.Claims;
import epf.client.util.Client;
import epf.security.client.Security;
import epf.security.schema.Token;
import epf.security.view.PrincipalView;
import epf.util.logging.LogManager;
import epf.webapp.GatewayUtil;
import epf.webapp.security.TokenPrincipal;

/**
 * @author PC
 *
 */
@RequestScoped
@Named(PrincipalView.NAME)
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
	public String getFullName() {
		return token.getClaims().get(Claims.full_name.name()).toString();
	}
}
