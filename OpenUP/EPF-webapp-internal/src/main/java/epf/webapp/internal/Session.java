package epf.webapp.internal;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import epf.util.MapUtil;
import epf.webapp.naming.Naming;

@SessionScoped
@Named(Naming.Security.SESSION)
public class Session implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private transient TokenPrincipal principal;
	
	@Inject
	private transient SecurityContext context;
	
	@Inject
	private transient Event<Principal> event;
	
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
	
	public String getClaim(final String name) {
		return MapUtil.get(getPrincipal().getClaims(), name).orElse("").toString();
	}

	public char[] getToken() {
		return getPrincipal().getRawToken();
	}
	
	public void clear() {
		principal = null;
	}
}
