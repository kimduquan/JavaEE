package epf.webapp.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import epf.security.view.LoginView;

/**
 * @author PC
 *
 */
@RequestScoped
@Named(LoginView.NAME)
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
	private String caller;
	
	/**
	 * 
	 */
	private transient char[] password;
	
	/**
	 * 
	 */
	private boolean rememberMe;

	@Override
	public String getCaller() {
		return caller;
	}

	@Override
	public void setCaller(final String caller) {
		this.caller = caller;
	}

	@Override
	public char[] getPassword() {
		return password;
	}

	@Override
	public void setPassword(final char[] password) {
		this.password = password;
	}

	@Override
	public boolean isRememberMe() {
		return rememberMe;
	}

	@Override
	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Override
	public String login() throws Exception {
		final UsernamePasswordCredential credential = new UsernamePasswordCredential(caller, new Password(password));
		final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(rememberMe);
		final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		final AuthenticationStatus status = context.authenticate(request, response, params);
		if(AuthenticationStatus.SUCCESS.equals(status)) {
			return "index";
		}
		return "login";
	}
}
