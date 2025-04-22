package epf.security.auth.core;

import java.net.URI;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import epf.security.auth.discovery.ProviderMetadata;
import epf.util.StringUtil;
import epf.util.logging.LogManager;

public interface ImplicitFlowAuth {
	
	Logger LOGGER = LogManager.getLogger(ImplicitFlowAuth.class.getName());
	
	String DEFAULT_PROFILE = "profile";
	
	default ProviderMetadata getProviderConfig(final ClientBuilder builder, final URI discoveryUrl) throws Exception {
		final Client client = builder.build();
		final ProviderMetadata metadata = client.target(discoveryUrl).request(MediaType.APPLICATION_JSON).get(ProviderMetadata.class);
		client.close();
		return metadata;
	}
	
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
				authRequestUrl.append(StringUtil.encodeURL(redirect_uri));
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
				authRequestUrl.append(StringUtil.encodeURL(state));
			}
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[ImplicitFlowAuth.authRequest.state]", e);
			}
		});
		return authRequestUrl.toString();
	}
	
	boolean validate(final ImplicitAuthResponse implicitAuthResponse);
}
