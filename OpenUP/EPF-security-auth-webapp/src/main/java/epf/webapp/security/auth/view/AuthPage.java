package epf.webapp.security.auth.view;

import java.io.Serializable;
import java.util.Base64;
import jakarta.enterprise.context.Conversation;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.Response.Status;
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
import epf.webapp.security.AuthParams;
import epf.webapp.security.auth.AuthCodeCredential;
import epf.webapp.security.auth.ImplicitCredential;
import epf.webapp.security.auth.SecurityAuth;
import epf.webapp.security.auth.core.AuthFlowConversation;
import epf.webapp.security.auth.core.CodeFlow;
import epf.webapp.security.auth.core.Flow;
import epf.webapp.security.auth.core.ImplicitFlow;

@ViewScoped
@Named(Naming.Security.AUTH)
public class AuthPage implements AuthView, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private transient SecurityContext context;
	
	@Inject
    private transient ExternalContext externalContext;
	
	@Inject
	private transient HttpServletRequest request;
	
	@Inject
	private transient SecurityAuth securityAuth;
	
	@Inject
	private transient Conversation conversation;
	
	@Inject
	private AuthFlowConversation authFlow;

	@Inject
	private CodeFlow codeFlow;
	
	@Inject
	private ImplicitFlow implicitFlow;
	
	@Inject
	private AuthParams authParams;
	
	private String url;
	
	private String provider = "";

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(final String provider) {
		this.provider = provider;
	}
	
	private String buildAuthRequestState() {
		final String csrfToken = request.getParameter("javax.faces.Token");
		if(conversation.isTransient()) {
			conversation.begin();
		}
		return conversation.getId() + System.lineSeparator() + csrfToken;
	}
	
	private String buildAuthRequestNonce(final String sessionId) throws Exception {
		return SecurityUtil.hash(sessionId, Base64.getMimeEncoder());
	}

	@Override
	public String loginWithGoogle() throws Exception {
		final String sessionId = externalContext.getSessionId(true);
		final AuthRequest authRequest = new AuthRequest();
		authRequest.setNonce(buildAuthRequestNonce(sessionId));
		authRequest.setState(buildAuthRequestState());
		authFlow.setFlow(Flow.Code);
		authFlow.setProvider(epf.naming.Naming.Security.Auth.GOOGLE);
		authFlow.setUrl(url);
		final ProviderMetadata metadata = securityAuth.initGoogleProvider(codeFlow, authRequest);
		authFlow.setProviderMetadata(metadata);
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
		authRequest.setState(buildAuthRequestState());
		authFlow.setFlow(Flow.Implicit);
		authFlow.setProvider(epf.naming.Naming.Security.Auth.FACEBOOK);
		authFlow.setUrl(url);
		final ProviderMetadata metadata = securityAuth.initFacebookProvider(implicitFlow, authRequest);
		authFlow.setProviderMetadata(metadata);
		implicitFlow.setAuthRequest(authRequest);
		final String authRequestUrl = implicitFlow.getAuthorizeUrl(metadata, authRequest, "public_profile");
		externalContext.redirect(authRequestUrl);
		return "";
	}
	
	private String authenticateCodeFlow(final HttpServletRequest request, final String sessionId) throws Exception {
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
			final AuthCodeCredential credential = new AuthCodeCredential(authFlow.getProviderMetadata(), tokenRequest, authFlow.getProvider(), sessionId);
			final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(false);
			authParams.setRememberMe(false);
			final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
			final AuthenticationStatus status = context.authenticate(request, response, params);
			if(AuthenticationStatus.SUCCESS.equals(status) || AuthenticationStatus.SEND_CONTINUE.equals(status)) {
				externalContext.redirect("webapp/index.html?cid=" + conversation.getId());
			}
		}
		else {
			final AuthError authError = new AuthError();
			authError.setError(error);
			authError.setError_description(request.getParameter("error_description"));
			authError.setError_uri(request.getParameter("error_uri"));
			authError.setState(request.getParameter("state"));
			codeFlow.setAuthError(authError);
			conversation.end();
			externalContext.responseSendError(Status.UNAUTHORIZED.getStatusCode(), authError.getError_description());
		}
		return "";
	}
	
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
			
			final ImplicitCredential credential = new ImplicitCredential(authResponse, authFlow.getProvider(), sessionId);
			final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential).rememberMe(false);
			authParams.setRememberMe(false);
			final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
			final AuthenticationStatus status = context.authenticate(request, response, params);
			if(AuthenticationStatus.SUCCESS.equals(status) || AuthenticationStatus.SEND_CONTINUE.equals(status)) {
				externalContext.redirect("webapp/index.html?cid=" + conversation.getId());
			}
		}
		else {
			final ImplicitAuthError authError = new ImplicitAuthError();
			authError.setError(error);
			authError.setError_description(request.getParameter("error_description"));
			authError.setError_uri(request.getParameter("error_uri"));
			authError.setState(request.getParameter("state"));
			implicitFlow.setAuthError(authError);
			conversation.end();
			externalContext.responseSendError(Status.UNAUTHORIZED.getStatusCode(), authError.getError_description());
		}
		return "";
	}
	
	public String authenticate() throws Exception {
		if(!conversation.isTransient()) {
			final String sessionId = externalContext.getSessionId(false);
			switch(authFlow.getFlow()) {
				case Code:
					return authenticateCodeFlow(request, sessionId);
				case Implicit:
					return authenticateImplicitFlow(request, sessionId);
				default:
					break;
			}
		}
		return "";
	}
	
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
