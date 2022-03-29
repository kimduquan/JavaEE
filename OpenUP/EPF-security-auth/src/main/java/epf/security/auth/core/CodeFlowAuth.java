package epf.security.auth.core;

import java.net.URLEncoder;
import java.time.Instant;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import epf.security.auth.discovery.ProviderMetadata;
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
	 * @param discoveryUrl
	 * @return
	 * @throws Exception
	 */
	default ProviderMetadata getProviderConfig(final String discoveryUrl) throws Exception {
		final Client client = ClientBuilder.newClient();
		final ProviderMetadata metadata = client.target(discoveryUrl).request(MediaType.APPLICATION_JSON).get(ProviderMetadata.class);
		client.close();
		return metadata;
	}
	
	/**
	 * @param authRequest
	 * @return
	 */
	default String getAuthorizeUrl(final ProviderMetadata metadata, final AuthRequest authRequest) {
		authRequest.setResponse_type("code");
		authRequest.setScope("openid email profile");
		authRequest.setNonce(String.valueOf(Instant.now().getEpochSecond()));
		
		final StringBuilder authRequestUrl = new StringBuilder();
		authRequestUrl.append(metadata.getAuthorization_endpoint());
		authRequestUrl.append('?');
		Optional.ofNullable(authRequest.getClient_id()).ifPresent(client_id -> {
			authRequestUrl.append("&client_id=");
			authRequestUrl.append(client_id);
		});
		Optional.ofNullable(authRequest.getNonce()).ifPresent(nonce -> {
			authRequestUrl.append("&nonce=");
			authRequestUrl.append(nonce);
		});
		Optional.ofNullable(authRequest.getRedirect_uri()).ifPresent(redirect_uri -> {
			authRequestUrl.append("&redirect_uri=");
			try {
				authRequestUrl.append(URLEncoder.encode(redirect_uri, "UTF-8"));
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
				authRequestUrl.append(URLEncoder.encode(state, "UTF-8"));
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
	 * @param metadata
	 * @param tokenRequest
	 * @return
	 * @throws TokenErrorResponse
	 */
	default TokenResponse requestToken(final ProviderMetadata metadata, final TokenRequest tokenRequest) throws TokenErrorResponse {
		final Client client = ClientBuilder.newClient();
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
	
	/**
	 * @param metadata
	 * @param tokenResponse
	 * @return
	 * @throws UserInfoError
	 */
	default UserInfo getUserInfo(final ProviderMetadata metadata, final TokenResponse tokenResponse) throws UserInfoError {
		final Client client = ClientBuilder.newClient();
		try(Response response = client
				.target(metadata.getUserinfo_endpoint())
				.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, tokenResponse.getToken_type() + " " + tokenResponse.getAccess_token())
				.get()){
			if(response.getStatus() == Status.OK.getStatusCode()) {
				return response.readEntity(UserInfo.class);
			}
			else {
				final UserInfoError userInfoError = response.readEntity(UserInfoError.class);
				LOGGER.log(Level.SEVERE, "[CodeFlowAuth.userInfo]" + userInfoError.toString());
				throw userInfoError;
			}
		}
		finally {
			client.close();
		}
	}
}
