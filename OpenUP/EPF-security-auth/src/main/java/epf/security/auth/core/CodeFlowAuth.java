package epf.security.auth.core;

import java.net.URI;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import epf.security.auth.discovery.ProviderMetadata;
import epf.util.StringUtil;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public interface CodeFlowAuth {
	
	/**
	 * 
	 */
	Logger LOGGER = LogManager.getLogger(CodeFlowAuth.class.getName());
	
	/**
	 * @param builder
	 * @param discoveryUrl
	 * @return
	 * @throws Exception
	 */
	default ProviderMetadata getProviderConfig(final ClientBuilder builder, final URI discoveryUrl) throws Exception {
		final Client client = builder.build();
		final ProviderMetadata metadata = client.target(discoveryUrl).request(MediaType.APPLICATION_JSON).get(ProviderMetadata.class);
		client.close();
		return metadata;
	}
	
	/**
	 * @param authRequest
	 * @return
	 * @throws Exception 
	 */
	default String getAuthorizeUrl(final ProviderMetadata metadata, final AuthRequest authRequest) throws Exception {
		authRequest.setResponse_type("code");
		authRequest.setScope("openid email profile");
		final StringBuilder authRequestUrl = new StringBuilder();
		authRequestUrl.append(metadata.getAuthorization_endpoint());
		authRequestUrl.append('?');
		Optional.ofNullable(authRequest.getClient_id()).ifPresent(client_id -> {
			authRequestUrl.append("&client_id=");
			authRequestUrl.append(client_id);
		});
		Optional.ofNullable(authRequest.getNonce()).ifPresent(nonce -> {
			authRequestUrl.append("&nonce=");
			try {
				authRequestUrl.append(StringUtil.encodeURL(nonce));
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[CodeFlowAuth.authRequest.nonce]", e);
			}
		});
		Optional.ofNullable(authRequest.getRedirect_uri()).ifPresent(redirect_uri -> {
			authRequestUrl.append("&redirect_uri=");
			try {
				authRequestUrl.append(StringUtil.encodeURL(redirect_uri));
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[CodeFlowAuth.authRequest.redirect_uri]", e);
			}
		});
		Optional.ofNullable(authRequest.getResponse_type()).ifPresent(response_type -> {
			authRequestUrl.append("&response_type=");
			authRequestUrl.append(response_type);
		});
		Optional.ofNullable(authRequest.getScope()).ifPresent(scope -> {
			authRequestUrl.append("&scope=");
			authRequestUrl.append(scope);
		});
		Optional.ofNullable(authRequest.getState()).ifPresent(state -> {
			authRequestUrl.append("&state=");
			try {
				authRequestUrl.append(StringUtil.encodeURL(state));
			}
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[CodeFlowAuth.authRequest.state]", e);
			}
		});
		return authRequestUrl.toString();
	}
	
	/**
	 * @param authResponse
	 * @return
	 */
	boolean validate(final AuthResponse authResponse);
	
	/**
	 * @param builder
	 * @param metadata
	 * @param tokenRequest
	 * @return
	 * @throws TokenErrorResponse
	 */
	default TokenResponse requestToken(final ClientBuilder builder, final ProviderMetadata metadata, final TokenRequest tokenRequest) throws TokenErrorResponse {
		final Client client = builder.build();
		try(Response response = client
				.target(metadata.getToken_endpoint())
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(tokenRequest, MediaType.APPLICATION_FORM_URLENCODED))){
			if(response.getStatus() == Status.OK.getStatusCode()) {
				return response.readEntity(TokenResponse.class);
			}
			else {
				final TokenErrorResponse tokenError = response.readEntity(TokenErrorResponse.class);
				LOGGER.log(Level.SEVERE, "[CodeFlowAuth.accessToken]" + tokenError.toString());
				throw tokenError;
			}
		}
		finally {
			client.close();
		}
	}
	
	/**
	 * @param tokenResponse
	 * @return
	 */
	boolean validate(final TokenResponse tokenResponse);
}
