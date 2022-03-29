package epf.webapp.security.auth.view;

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
import epf.security.auth.Provider;
import epf.security.auth.core.AuthError;
import epf.security.auth.core.AuthRequest;
import epf.security.auth.core.AuthResponse;
import epf.security.auth.core.ImplicitAuthError;
import epf.security.auth.core.ImplicitAuthResponse;
import epf.security.auth.core.TokenRequest;
import epf.security.auth.view.AuthView;
import epf.webapp.naming.Naming;
import epf.webapp.security.Session;
import epf.webapp.security.auth.AuthCodeCredential;
import epf.webapp.security.auth.AuthFlow;
import epf.webapp.security.auth.ImplicitCredential;
import epf.webapp.security.auth.SecurityAuth;

/**
 * @author PC
 *
 */
@RequestScoped
@Named(Naming.Security.AUTH)
public class AuthPage implements AuthView {
	
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
	@Inject @Named(Naming.Security.SESSION)
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

	@Override
	public String loginWithGoogle() throws Exception {
		final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		final String csrfToken = request.getParameter("javax.faces.Token");
		conversation.begin();
		authFlow.setId(conversation.getId() + System.lineSeparator() + csrfToken);
		final AuthRequest authRequest = new AuthRequest();
		authRequest.setState("CodeFlowAuth" + System.lineSeparator() + conversation.getId() + System.lineSeparator() + csrfToken);
		final Provider provider = securityAuth.initGoogleProvider(authFlow, authRequest);
		authFlow.setAuthRequest(authRequest);
		authFlow.setProvider(provider);
		final String authRequestUrl = provider.authorizeUrl(authRequest);
		externalContext.redirect(authRequestUrl);
		return "";
	}

	@Override
	public String loginWithFacebook() throws Exception {
		final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		final String csrfToken = request.getParameter("javax.faces.Token");
		conversation.begin();
		authFlow.setId(conversation.getId() + System.lineSeparator() + csrfToken);
		final AuthRequest authRequest = new AuthRequest();
		authRequest.setState("ImplicitFlowAuth" + System.lineSeparator() + conversation.getId() + System.lineSeparator() + csrfToken);
		final Provider provider = securityAuth.initFacebookProvider(authFlow, authRequest);
		authFlow.setAuthRequest(authRequest);
		authFlow.setProvider(provider);
		final String authRequestUrl = provider.authorizeUrl(authRequest);
		externalContext.redirect(authRequestUrl);
		return "";
	}
	
	/**
	 * @param request
	 * @return
	 */
	private String authenticateCodeFlow(final HttpServletRequest request) {
		final String error = request.getParameter("error");
		if(error == null) {
			final AuthResponse authResponse = new AuthResponse();
			authResponse.setCode(request.getParameter("code"));
			authResponse.setState(request.getParameter("state"));
			authFlow.setAuthResponse(authResponse);
		}
		else {
			final AuthError authError = new AuthError();
			authError.setError(error);
			authError.setState(request.getParameter("state"));
			authFlow.setAuthError(authError);
		}
		
		final TokenRequest tokenRequest = new TokenRequest();
		tokenRequest.setClient_id(authFlow.getAuthRequest().getClient_id());
		tokenRequest.setCode(authFlow.getAuthResponse().getCode());
		tokenRequest.setRedirect_uri(authFlow.getAuthRequest().getRedirect_uri());
		final AuthCodeCredential credential = new AuthCodeCredential(tokenRequest, authFlow.getProvider(), authFlow.getClientSecret());
		session.setRemember(true);
		final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(session.isRemember());
		final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		final AuthenticationStatus status = context.authenticate(request, response, params);
		conversation.end();
		if(AuthenticationStatus.SUCCESS.equals(status)) {
			return Naming.DEFAULT_VIEW;
		}
		return Naming.Security.LOGIN;
	}
	
	/**
	 * @param request
	 * @return
	 */
	private String authenticateImplicitFlow(final HttpServletRequest request) {
		final String error = request.getParameter("error");
		if(error == null) {
			final ImplicitAuthResponse authResponse = new ImplicitAuthResponse();
			authResponse.setAccess_token(request.getParameter("access_token"));
			authResponse.setExpires_in(Long.parseLong(request.getParameter("expires_in")));
			authResponse.setId_token(request.getParameter("id_token"));
			authResponse.setState(request.getParameter("state"));
			authResponse.setToken_type(request.getParameter("token_type"));
			
			final ImplicitCredential credential = new ImplicitCredential(authResponse);
			session.setRemember(true);
			final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(session.isRemember());
			final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
			final AuthenticationStatus status = context.authenticate(request, response, params);
			conversation.end();
			if(AuthenticationStatus.SUCCESS.equals(status)) {
				return Naming.DEFAULT_VIEW;
			}
		}
		else {
			final ImplicitAuthError authError = new ImplicitAuthError();
			authError.setError(error);
			authError.setError_description(request.getParameter("error_description"));
			authError.setError_uri(request.getParameter("error_uri"));
			authError.setState(request.getParameter("state"));
			authFlow.setAuthError(authError);
		}
		return Naming.Security.LOGIN;
	}
	
	/**
	 * @return
	 */
	public String authenticate() {
		final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		final String flow = request.getParameter("flow");
		if("CodeFlowAuth".equals(flow)) {
			return this.authenticateCodeFlow(request);
		}
		else if("ImplicitFlowAuth".equals(flow)) {
			return this.authenticateImplicitFlow(request);
		}
		return Naming.Security.LOGIN;
	}
}
