package epf.webapp.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import epf.webapp.security.view.LoginView;

/**
 * @author PC
 *
 */
@RequestScoped
@Named("login")
public class LoginPage implements LoginView {
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityContext context;
	
	/**
	 * 
	 */
	@Inject
    private transient ExternalContext externalContext;
	
	/**
	 * 
	 */
	private final Credential credential = new Credential();
	
	/**
	 * @return
	 */
	public String authenticate() {
		final UsernamePasswordCredential usernamePassword = new UsernamePasswordCredential(credential.getCaller(), credential.getPassword());
		final AuthenticationParameters params = AuthenticationParameters.withParams().credential(usernamePassword);
		final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		final AuthenticationStatus status = context.authenticate(request, response, params);
		if(AuthenticationStatus.SUCCESS.equals(status)) {
			return "index";
		}
		return "login";
	}

	public Credential getCredential() {
		return credential;
	}
}
