package epf.webapp.internal.view;

import java.io.Serializable;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.microprofile.jwt.Claims;
import epf.security.view.SecurityView;
import epf.util.StringUtil;
import epf.webapp.internal.Session;
import epf.webapp.internal.TokenCredential;
import epf.webapp.naming.Naming;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.Security.PRINCIPAL)
public class SecurityPage implements SecurityView, Serializable {
	
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
	@Inject
	private transient SecurityContext context;
	
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
		final String fullName = session.getClaim(Claims.full_name.name());
		String name = fullName;
		if(name == null) {
			final String firstName = session.getClaim(epf.naming.Naming.Security.Claims.FIRST_NAME);
			final String lastName = session.getClaim(epf.naming.Naming.Security.Claims.LAST_NAME);
			if(firstName != null && lastName != null && !lastName.isEmpty()) {
				name = firstName + " " + lastName;
			}
			else {
				name = "";
			}
		}
		return name;
	}

	@Override
	public String logout() throws Exception {
		session.clear();
		request.logout();
		externalContext.invalidateSession();
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

	@Override
	public String login() throws Exception {
		externalContext.redirect("/security-auth?url=" + StringUtil.encodeURL("/webapp/security/auth.html"));
		return "";
	}

	@Override
	public String authenticate() throws Exception {
		final TokenCredential credential = new TokenCredential(request.getParameter("credential").toCharArray());
		final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(false);
		final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		context.authenticate(request, response, params);
		return "";
	}
}
