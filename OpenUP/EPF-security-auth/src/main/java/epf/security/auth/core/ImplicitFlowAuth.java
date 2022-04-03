package epf.security.auth.core;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import epf.security.auth.discovery.ProviderMetadata;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public interface ImplicitFlowAuth {
	
	/**
	 * 
	 */
	Logger LOGGER = LogManager.getLogger(ImplicitFlowAuth.class.getName());
	
	/**
	 * 
	 */
	String DEFAULT_PROFILE = "profile";
	
	/**
	 * @param discoveryUrl
	 * @return
	 * @throws Exception
	 */
	default ProviderMetadata getProviderConfig(final URI discoveryUrl) throws Exception {
		final Client client = ClientBuilder.newClient();
		final ProviderMetadata metadata = client.target(discoveryUrl).request(MediaType.APPLICATION_JSON).get(ProviderMetadata.class);
		client.close();
		return metadata;
	}
	
	/**
	 * @param metadata
	 * @param authRequest
	 * @return
	 * @throws Exception
	 */
	default String getAuthorizeUrl(final ProviderMetadata metadata, final ImplicitAuthRequest authRequest, final String profile) throws Exception {
		authRequest.setResponse_type("id_token token");
		authRequest.setScope("openid email " + profile != null ? profile : DEFAULT_PROFILE);
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
				LOGGER.log(Level.SEVERE, "[ImplicitFlowAuth.authRequest.redirect_uri]", e);
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
				LOGGER.log(Level.SEVERE, "[ImplicitFlowAuth.authRequest.state]", e);
			}
		});
		return authRequestUrl.toString();
	}
	
	/**
	 * @param implicitAuthResponse
	 * @return
	 */
	boolean validate(final ImplicitAuthResponse implicitAuthResponse);
}
