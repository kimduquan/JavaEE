package epf.webapp.security.view;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.microprofile.jwt.Claims;
import epf.security.auth.core.StandardClaims;
import epf.security.view.PrincipalView;
import epf.webapp.CookieUtil;
import epf.webapp.naming.Naming;
import epf.webapp.security.Session;

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
	@Inject @Named(Naming.Security.SESSION)
	private transient Session session;
	
	/**
	 * @return
	 */
	@Override
	public String getName() {
		String name = session.getClaim(StandardClaims.name.name());
		if(name == null) {
			name = session.getClaim(Claims.full_name.name());
		}
		return name;
	}

	@Override
	public String logout() throws Exception {
		request.logout();
		externalContext.invalidateSession();
		externalContext.redirect(Naming.CONTEXT_ROOT);
		final Optional<Cookie> sessionIdCookie = CookieUtil.getCookie(request, "JSESSIONID");
		CookieUtil.deleteCookie(externalContext, sessionIdCookie.get());
		return "";
	}

	@Override
	public List<String> getClaimNames() {
		return session.getClaimNames();
	}

	@Override
	public String getClaim(String name) {
		return session.getClaim(name);
	}
}
