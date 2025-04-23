package epf.webapp.internal.view;

import java.io.Serializable;
import java.util.List;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.microprofile.jwt.Claims;
import epf.security.view.SecurityView;
import epf.util.StringUtil;
import epf.webapp.internal.Session;
import epf.webapp.internal.TokenCredential;
import epf.webapp.naming.Naming;
import epf.webapp.util.CookieUtil;

@ViewScoped
@Named(Naming.Security.PRINCIPAL)
public class SecurityPage implements SecurityView, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private transient HttpServletRequest request;
	
	@Inject
    private transient ExternalContext externalContext;
	
	@Inject
	private transient SecurityContext context;
	
	@Inject @Named(Naming.Security.SESSION)
	private transient Session session;
	
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
		CookieUtil.getCookie(request, "JSESSIONID").forEach(cookie -> CookieUtil.deleteCookie(externalContext, cookie));
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
	public String login(final String url) throws Exception {
		externalContext.redirect("/security-auth?url=" + StringUtil.encodeURL(url));
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
