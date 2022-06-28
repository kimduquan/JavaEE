package epf.webapp.security.auth.view;

import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;
import epf.security.auth.core.AuthError;
import epf.security.auth.core.AuthRequest;
import epf.security.auth.core.AuthResponse;
import epf.security.auth.core.ImplicitAuthError;
import epf.security.auth.core.ImplicitAuthRequest;
import epf.security.auth.core.ImplicitAuthResponse;
import epf.security.auth.core.TokenRequest;
import epf.security.auth.discovery.ProviderMetadata;
import epf.security.auth.view.AuthView;
import epf.util.security.SecurityUtil;
import epf.webapp.naming.Naming;
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
		if(conversation.isTransient()) {
			conversation.begin();
		}
		return flow + System.lineSeparator() + conversation.getId() + System.lineSeparator() + csrfToken;
	}
	
	/**
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	private String buildAuthRequestNonce(final String sessionId) throws Exception {
		return SecurityUtil.hash(sessionId);
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
	 * @throws Exception 
	 */
	private String authenticateCodeFlow(final HttpServletRequest request) throws Exception {
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
			final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(false);
			final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
			context.authenticate(request, response, params);
		}
		else {
			final AuthError authError = new AuthError();
			authError.setError(error);
			authError.setError_description(request.getParameter("error_description"));
			authError.setError_uri(request.getParameter("error_uri"));
			authError.setState(request.getParameter("state"));
			codeFlow.setAuthError(authError);
			externalContext.responseSendError(Status.UNAUTHORIZED.getStatusCode(), authError.getError_description());
		}
		conversation.end();
		return "";
	}
	
	/**
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	private String authenticateImplicitFlow(final HttpServletRequest request, final String sessionId) throws Exception {
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
			final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(false);
			final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
			context.authenticate(request, response, params);
		}
		else {
			final ImplicitAuthError authError = new ImplicitAuthError();
			authError.setError(error);
			authError.setError_description(request.getParameter("error_description"));
			authError.setError_uri(request.getParameter("error_uri"));
			authError.setState(request.getParameter("state"));
			implicitFlow.setAuthError(authError);
			externalContext.responseSendError(Status.UNAUTHORIZED.getStatusCode(), authError.getError_description());
		}
		conversation.end();
		return "";
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	public String authenticate() throws Exception {
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
