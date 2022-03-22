package epf.webapp.security.auth;

import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import epf.naming.Naming;
import epf.security.auth.openid.AuthRequest;
import epf.security.auth.openid.TokenErrorResponse;
import epf.security.auth.openid.TokenRequest;
import epf.security.auth.openid.TokenResponse;
import epf.security.auth.openid.spi.ProviderMetadata;
import epf.util.logging.LogManager;
import epf.webapp.ConfigSource;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class SecurityAuth {
	
	/**
	 * 
	 */
	private transient final static Logger LOGGER = LogManager.getLogger(SecurityAuth.class.getName());
	
	/**
	 * 
	 */
	@Inject
	private transient ConfigSource config;
	
	/**
	 * 
	 */
	private ProviderMetadata googleProvider;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final Client client = ClientBuilder.newClient();
			googleProvider = client.target(config.getProperty(Naming.Security.Auth.GOOGLE_PROVIDER)).request(MediaType.APPLICATION_JSON).get(ProviderMetadata.class);
			client.close();
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[SecurityAuth.ProviderMetadata]");
		}
	}
	
	/**
	 * @param authFlow
	 * @throws Exception 
	 */
	public String prepareLoginWithGoogle(final AuthFlow authFlow) throws Exception {
		authFlow.setProviderMetadata(googleProvider);
		
		final AuthRequest authRequest = new AuthRequest();
		authRequest.setClient_id(config.getProperty(Naming.Security.Auth.GOOGLE_CLIENT_ID));
		authRequest.setRedirect_uri(config.getProperty(Naming.Security.Auth.AUTH_URL));
		authRequest.setResponse_type("code");
		authRequest.setScope("openid email profile");
		authRequest.setState(authFlow.getId());
		authFlow.setAuthRequest(authRequest);
		
		final StringBuilder authRequestUrl = new StringBuilder();
		authRequestUrl.append(authFlow.getProviderMetadata().getAuthorization_endpoint());
		if(authFlow.getAuthRequest().getClient_id() != null) {
			authRequestUrl.append("&client_id=");
			authRequestUrl.append(authFlow.getAuthRequest().getClient_id());
		}
		if(authFlow.getAuthRequest().getNonce() != null) {
			authRequestUrl.append("&nonce=");
			authRequestUrl.append(authFlow.getAuthRequest().getNonce());
		}
		if(authFlow.getAuthRequest().getRedirect_uri() != null) {
			authRequestUrl.append("&redirect_uri=");
			authRequestUrl.append(URLEncoder.encode(authFlow.getAuthRequest().getRedirect_uri(), "UTF-8"));
		}
		if(authFlow.getAuthRequest().getResponse_type() != null) {
			authRequestUrl.append("&response_type=");
			authRequestUrl.append(authFlow.getAuthRequest().getResponse_type());
		}
		if(authFlow.getAuthRequest().getScope() != null) {
			authRequestUrl.append("&scope=");
			authRequestUrl.append(authFlow.getAuthRequest().getScope());
		}
		if(authFlow.getAuthRequest().getState() != null) {
			authRequestUrl.append("&state=");
			authRequestUrl.append(authFlow.getAuthRequest().getState());
		}
		return authRequestUrl.toString();
	}
	
	/**
	 * @param authFlow
	 * @return
	 */
	public TokenRequest prepareTokenRequest(final AuthFlow authFlow) {
		final TokenRequest tokenRequest = new TokenRequest();
		tokenRequest.setClient_id(authFlow.getAuthRequest().getClient_id());
		tokenRequest.setCode(authFlow.getAuthResponse().getCode());
		tokenRequest.setGrant_type("authorization_code");
		tokenRequest.setRedirect_uri(authFlow.getAuthRequest().getRedirect_uri());
		return tokenRequest;
	}
	
	/**
	 * @param tokenEndpoint
	 * @param tokenRequest
	 * @return
	 */
	public TokenResponse requestToken(final String tokenEndpoint, final TokenRequest tokenRequest) {
		TokenResponse tokenResponse = null;
		final Form form = new Form();
		final Client client = ClientBuilder.newClient();
		final Response response = client
				.target(tokenEndpoint)
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.form(form));
		if(response.getStatus() == Status.OK.getStatusCode()) {
			tokenResponse = response.readEntity(TokenResponse.class);
		}
		else {
			final TokenErrorResponse tokenError = response.readEntity(TokenErrorResponse.class);
			LOGGER.log(Level.SEVERE, "[SecurityAuth.requestToken]" + tokenError.toString());
		}
		client.close();
		return tokenResponse;
	}
}
