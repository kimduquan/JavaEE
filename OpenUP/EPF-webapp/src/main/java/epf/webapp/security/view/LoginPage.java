package epf.webapp.security.view;

import javax.enterprise.context.Conversation;
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
import epf.webapp.naming.Naming;
import epf.webapp.security.Session;
import epf.webapp.security.auth.AuthFlow;
import epf.webapp.security.auth.SecurityAuth;

/**
 * @author PC
 *
 */
@RequestScoped
@Named(Naming.Security.LOGIN)
public class LoginPage implements LoginView {
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityAuth securityAuth;
	
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
	@Inject
	private transient Conversation conversation;
	
	/**
	 * 
	 */
	@Inject @Named(Naming.Security.SESSION)
	private Session session;
	
	/**
	 * 
	 */
	@Inject
	private AuthFlow authFlow;
	
	/**
	 * 
	 */
	private String caller;
	
	/**
	 * 
	 */
	private transient char[] password;

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
		return session.isRemember();
	}

	@Override
	public void setRememberMe(final boolean rememberMe) {
		session.setRemember(rememberMe);
	}

	@Override
	public String login() throws Exception {
		final UsernamePasswordCredential credential = new UsernamePasswordCredential(caller, new Password(password));
		final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(session.isRemember());
		final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		final AuthenticationStatus status = context.authenticate(request, response, params);
		if(AuthenticationStatus.SUCCESS.equals(status)) {
			return Naming.DEFAULT_VIEW;
		}
		return Naming.Security.LOGIN;
	}

	@Override
	public String loginWithGoogle() throws Exception {
		conversation.begin();
		authFlow.setId(conversation.getId());
		final String authRequestUrl = securityAuth.prepareLoginWithGoogle(authFlow);
		externalContext.redirect(authRequestUrl);
		return "";
	}

	@Override
	public String loginWithFacebook() throws Exception {
		
		return "";
	}
}
