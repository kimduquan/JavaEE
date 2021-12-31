package epf.webapp.security;

import java.io.Serializable;
import java.util.Set;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.servlet.http.HttpServletRequest;
import epf.security.schema.Token;
import epf.webapp.Naming;

/**
 * @author PC
 *
 */
@SessionScoped
@Named(Naming.SECURITY)
public class Security implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private final TokenPrincipal principal;
	
	/**
	 * 
	 */
	private Token token;
	
	/**
	 * 
	 */
	@Inject
	private transient HttpServletRequest request;
	
	@Inject
	public Security(final SecurityContext context) {
		final Set<TokenPrincipal> principals = context.getPrincipalsByType(TokenPrincipal.class);
		principal = principals.isEmpty() ? null : principals.iterator().next();
	}

	public TokenPrincipal getPrincipal() {
		return principal;
	}
	
	public Token getToken() {
		return token;
	}
	
	public void logout() throws Exception {
		request.logout();
	}
}
