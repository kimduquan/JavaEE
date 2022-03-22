package epf.webapp.security.auth.view;

import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import epf.security.auth.openid.AuthError;
import epf.security.auth.openid.AuthResponse;
import epf.security.auth.openid.TokenRequest;
import epf.webapp.naming.Naming;
import epf.webapp.security.Session;
import epf.webapp.security.auth.AuthCodeCredential;
import epf.webapp.security.auth.AuthFlow;
import epf.webapp.security.auth.SecurityAuth;

@RequestScoped
@Named(Naming.Security.AUTH)
public class AuthPage {
	
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
	private transient SecurityAuth securityAuth;
	
	/**
	 * 
	 */
	@Inject
	private transient Session session;
	
	/**
	 * 
	 */
	@Inject
	private transient Conversation conversation;

	/**
	 * 
	 */
	@Inject
	private AuthFlow authFlow;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		final Map<String, String> queryParams = externalContext.getRequestParameterMap();
		final String error = queryParams.get("error");
		if(error == null) {
			final AuthResponse authResponse = new AuthResponse();
			authResponse.setCode(queryParams.get("code"));
			authResponse.setState(queryParams.get("state"));
			authFlow.setAuthResponse(authResponse);
		}
		else {
			final epf.security.auth.openid.AuthError.Error err = epf.security.auth.openid.AuthError.Error.valueOf(error);
			final AuthError authError = new AuthError();
			authError.setError(err);
			authError.setState(queryParams.get("state"));
			authFlow.setAuthError(authError);
		}
	}
	
	/**
	 * @return
	 */
	public String login() {
		final TokenRequest tokenRequest = securityAuth.prepareTokenRequest(authFlow);
		final AuthCodeCredential credential = new AuthCodeCredential(tokenRequest, authFlow.getProviderMetadata());
		session.setRemember(true);
		final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(session.isRemember());
		final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		final AuthenticationStatus status = context.authenticate(request, response, params);
		conversation.end();
		if(AuthenticationStatus.SUCCESS.equals(status)) {
			return Naming.DEFAULT_VIEW;
		}
		return Naming.Security.LOGIN;
	}
}
