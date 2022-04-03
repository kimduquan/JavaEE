package epf.webapp.security.auth.view;

import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import epf.security.auth.core.AuthError;
import epf.security.auth.core.AuthRequest;
import epf.security.auth.core.AuthResponse;
import epf.security.auth.core.ImplicitAuthError;
import epf.security.auth.core.ImplicitAuthRequest;
import epf.security.auth.core.ImplicitAuthResponse;
import epf.security.auth.core.TokenRequest;
import epf.security.auth.discovery.ProviderMetadata;
import epf.security.auth.view.AuthView;
import epf.webapp.naming.Naming;
import epf.webapp.security.Session;
import epf.webapp.security.auth.AuthCodeCredential;
import epf.webapp.security.auth.ImplicitCredential;
import epf.webapp.security.auth.SecurityAuth;
import epf.webapp.security.auth.core.CodeFlow;
import epf.webapp.security.auth.core.ImplicitFlow;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.Security.AUTH)
public class AuthPage implements AuthView, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	private CodeFlow codeFlow;
	
	/**
	 * 
	 */
	@Inject
	private ImplicitFlow implicitFlow;
	
	/**
	 * 
	 */
	private String provider = "";

	public String getProvider() {
		return provider;
	}

	public void setProvider(final String provider) {
		this.provider = provider;
	}

	@Override
	public String loginWithGoogle() throws Exception {
		final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		final String csrfToken = request.getParameter("javax.faces.Token");
		final String windowId = externalContext.getClientWindow().getId();
		conversation.begin();
		final AuthRequest authRequest = new AuthRequest();
		authRequest.setNonce(windowId);
		authRequest.setState("Code" + System.lineSeparator() + windowId + System.lineSeparator() + conversation.getId() + System.lineSeparator() + csrfToken);
		final ProviderMetadata metadata = securityAuth.initGoogleProvider(codeFlow, authRequest);
		codeFlow.setProviderMetadata(metadata);
		codeFlow.setAuthRequest(authRequest);
		final String authRequestUrl = codeFlow.getAuthorizeUrl(metadata, authRequest);
		externalContext.redirect(authRequestUrl);
		return "";
	}

	@Override
	public String loginWithFacebook() throws Exception {
		final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		final String csrfToken = request.getParameter("javax.faces.Token");
		final String windowId = externalContext.getClientWindow().getId();
		conversation.begin();
		final ImplicitAuthRequest authRequest = new ImplicitAuthRequest();
		authRequest.setNonce(windowId);
		authRequest.setState("Implicit" + System.lineSeparator() + windowId + System.lineSeparator() + conversation.getId() + System.lineSeparator() + csrfToken);
		final ProviderMetadata metadata = securityAuth.initFacebookProvider(implicitFlow, authRequest);
		implicitFlow.setProviderMetadata(metadata);
		implicitFlow.setAuthRequest(authRequest);
		final String authRequestUrl = implicitFlow.getAuthorizeUrl(metadata, authRequest, "public_profile");
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
			codeFlow.setAuthResponse(authResponse);
			
			final TokenRequest tokenRequest = new TokenRequest();
			tokenRequest.setClient_id(codeFlow.getAuthRequest().getClient_id());
			tokenRequest.setCode(codeFlow.getAuthResponse().getCode());
			tokenRequest.setRedirect_uri(codeFlow.getAuthRequest().getRedirect_uri());
			final AuthCodeCredential credential = new AuthCodeCredential(codeFlow.getProviderMetadata(), tokenRequest, codeFlow);
			session.setRemember(true);
			final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(session.isRemember());
			final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
			final AuthenticationStatus status = context.authenticate(request, response, params);
			if(AuthenticationStatus.SUCCESS.equals(status)) {
				conversation.end();
				return Naming.DEFAULT_VIEW;
			}
		}
		else {
			final AuthError authError = new AuthError();
			authError.setError(error);
			authError.setState(request.getParameter("state"));
			codeFlow.setAuthError(authError);
		}
		conversation.end();
		return "";
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
			implicitFlow.setAuthResponse(authResponse);
			
			final ImplicitCredential credential = new ImplicitCredential(authResponse);
			session.setRemember(true);
			final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(session.isRemember());
			final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
			final AuthenticationStatus status = context.authenticate(request, response, params);
			if(AuthenticationStatus.SUCCESS.equals(status)) {
				conversation.end();
				return Naming.DEFAULT_VIEW;
			}
		}
		else {
			final ImplicitAuthError authError = new ImplicitAuthError();
			authError.setError(error);
			authError.setError_description(request.getParameter("error_description"));
			authError.setError_uri(request.getParameter("error_uri"));
			authError.setState(request.getParameter("state"));
			implicitFlow.setAuthError(authError);
		}
		conversation.end();
		return "";
	}
	
	/**
	 * @return
	 */
	public String authenticate() {
		final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		final String flow = request.getParameter("flow");
		if("Code".equals(flow)) {
			return this.authenticateCodeFlow(request);
		}
		else if("Implicit".equals(flow)) {
			return this.authenticateImplicitFlow(request);
		}
		return "";
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		if("Google".equals(provider)) {
			return loginWithGoogle();
		}
		else if("Facebook".equals(provider)) {
			return loginWithFacebook();
		}
		return "";
	}
}
