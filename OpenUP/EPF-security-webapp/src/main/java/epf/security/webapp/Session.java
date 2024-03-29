package epf.security.webapp;

import java.io.Serializable;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import epf.security.webapp.internal.TokenPrincipal;

/**
 * @author PC
 *
 */
@SessionScoped
@Named(Naming.SESSION)
public class Session implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private TokenPrincipal principal;
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityContext context;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		final Set<TokenPrincipal> principals = context.getPrincipalsByType(TokenPrincipal.class);
		principal = principals.isEmpty() ? null : principals.iterator().next();
	}

	public TokenPrincipal getPrincipal() {
		return principal;
	}
}
