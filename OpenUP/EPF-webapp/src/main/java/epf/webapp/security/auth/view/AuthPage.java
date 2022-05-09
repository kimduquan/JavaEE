package epf.webapp.security.auth.view;

import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
import epf.util.security.CryptoUtil;
import epf.webapp.naming.Naming;
import epf.webapp.security.AuthParams;
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
	private transient HttpServletRequest request;
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityAuth securityAuth;
	
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
	@Inject
	private AuthParams authParams;
	
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
	
	/**
	 * @param flow
	 * @return
	 */
	private String buildAuthRequestState(final String flow) {
		final String csrfToken = request.getParameter("javax.faces.Token");
		final String windowId = externalContext.getClientWindow().getId();
		if(conversation.isTransient()) {
			conversation.begin();
		}
		return flow + System.lineSeparator() + windowId + System.lineSeparator() + conversation.getId() + System.lineSeparator() + csrfToken;
	}
	
	/**
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	private String buildAuthRequestNonce(final String sessionId) throws Exception {
		return CryptoUtil.hash(sessionId);
	}

	@Override
	public String loginWithGoogle() throws Exception {
		final String sessionId = externalContext.getSessionId(true);
		final AuthRequest authRequest = new AuthRequest();
		authRequest.setNonce(buildAuthRequestNonce(sessionId));
		authRequest.setState(buildAuthRequestState("Code"));
		codeFlow.setProvider(epf.naming.Naming.Security.Auth.GOOGLE);
		codeFlow.setSessionId(sessionId);
		final ProviderMetadata metadata = securityAuth.initGoogleProvider(codeFlow, authRequest);
		codeFlow.setProviderMetadata(metadata);
		codeFlow.setAuthRequest(authRequest);
		final String authRequestUrl = codeFlow.getAuthorizeUrl(metadata, authRequest);
		externalContext.redirect(authRequestUrl);
		return "";
	}

	@Override
	public String loginWithFacebook() throws Exception {
		final String sessionId = externalContext.getSessionId(true);
		final ImplicitAuthRequest authRequest = new ImplicitAuthRequest();
		authRequest.setNonce(buildAuthRequestNonce(sessionId));
		authRequest.setState(buildAuthRequestState("Implicit"));
		implicitFlow.setProvider(epf.naming.Naming.Security.Auth.FACEBOOK);
		implicitFlow.setSessionId(sessionId);
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
			final AuthCodeCredential credential = new AuthCodeCredential(codeFlow.getProviderMetadata(), tokenRequest, codeFlow.getProvider(), codeFlow.getSessionId());
			final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(true);
			authParams.setRememberMe(true);
			final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
			final AuthenticationStatus status = context.authenticate(request, response, params);
			if(AuthenticationStatus.SUCCESS.equals(status)) {
				conversation.end();
				return "";
			}
		}
		else {
			final AuthError authError = new AuthError();
			authError.setError(error);
			authError.setError_description(request.getParameter("error_description"));
			authError.setError_uri(request.getParameter("error_uri"));
			authError.setState(request.getParameter("state"));
			codeFlow.setAuthError(authError);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, authError.getError(), authError.getError_description()));
		}
		conversation.end();
		return "";
	}
	
	/**
	 * @param request
	 * @return
	 */
	private String authenticateImplicitFlow(final HttpServletRequest request, final String sessionId) {
		final String error = request.getParameter("error");
		if(error == null) {
			final ImplicitAuthResponse authResponse = new ImplicitAuthResponse();
			authResponse.setAccess_token(request.getParameter("access_token"));
			authResponse.setExpires_in(Long.parseLong(request.getParameter("expires_in")));
			authResponse.setId_token(request.getParameter("id_token"));
			authResponse.setState(request.getParameter("state"));
			authResponse.setToken_type(request.getParameter("token_type"));
			implicitFlow.setAuthResponse(authResponse);
			
			final ImplicitCredential credential = new ImplicitCredential(authResponse, implicitFlow.getProvider(), sessionId);
			final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(true);
			authParams.setRememberMe(true);
			final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
			final AuthenticationStatus status = context.authenticate(request, response, params);
			if(AuthenticationStatus.SUCCESS.equals(status)) {
				conversation.end();
				return "";
			}
		}
		else {
			final ImplicitAuthError authError = new ImplicitAuthError();
			authError.setError(error);
			authError.setError_description(request.getParameter("error_description"));
			authError.setError_uri(request.getParameter("error_uri"));
			authError.setState(request.getParameter("state"));
			implicitFlow.setAuthError(authError);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, authError.getError(), authError.getError_description()));
		}
		conversation.end();
		return "";
	}
	
	/**
	 * @return
	 */
	public String authenticate() {
		final String flow = request.getParameter("flow");
		if("Code".equals(flow)) {
			return this.authenticateCodeFlow(request);
		}
		else if("Implicit".equals(flow)) {
			final String sessionId = externalContext.getSessionId(false);
			return this.authenticateImplicitFlow(request, sessionId);
		}
		return "";
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		if(epf.naming.Naming.Security.Auth.GOOGLE.equals(provider)) {
			return loginWithGoogle();
		}
		else if(epf.naming.Naming.Security.Auth.FACEBOOK.equals(provider)) {
			return loginWithFacebook();
		}
		return "";
	}
}
