package epf.webapp.internal;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import epf.util.MapUtil;
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
	private transient TokenPrincipal principal;
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityContext context;
	
	/**
	 * 
	 */
	@Inject
	private transient Event<Principal> event;
	
	/**
	 * @return
	 */
	private TokenPrincipal getPrincipal() {
		if(principal == null) {
			principal = (TokenPrincipal) context.getCallerPrincipal();
			event.fire(principal);
		}
		return principal;
	}

	public List<String> getClaimNames() {
		return getPrincipal().getClaims().keySet().stream().collect(Collectors.toList());
	}
	
	/**
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	public String getClaim(final String name) {
		return MapUtil.get(getPrincipal().getClaims(), name).orElse("").toString();
	}

	public char[] getToken() {
		return getPrincipal().getRawToken();
	}
	
	/**
	 * 
	 */
	public void clear() {
		principal = null;
	}
}
