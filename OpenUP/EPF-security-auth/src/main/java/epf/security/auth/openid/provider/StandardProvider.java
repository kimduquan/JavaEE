package epf.security.auth.openid.provider;

import java.net.URI;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.JwksVerificationKeyResolver;
import epf.security.auth.openid.AuthRequest;
import epf.security.auth.openid.Provider;
import epf.security.auth.openid.ProviderMetadata;
import epf.security.auth.openid.TokenErrorResponse;
import epf.security.auth.openid.TokenRequest;
import epf.security.auth.openid.TokenResponse;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class StandardProvider implements Provider {
	
	/**
	 * 
	 */
	private transient final static Logger LOGGER = LogManager.getLogger(StandardProvider.class.getName());
	
	/**
	 * 
	 */
	private transient final URI discoveryUrl;
	
	/**
	 * 
	 */
	private transient ProviderMetadata metadata;
	
	/**
	 * 
	 */
	private transient HttpsJwks jwks;
	
	/**
	 * @param discoveryUrl
	 */
	public StandardProvider(final URI discoveryUrl) {
		this.discoveryUrl = discoveryUrl;
	}

	@Override
	public ProviderMetadata discovery() {
		final Client client = ClientBuilder.newClient();
		metadata = client.target(discoveryUrl).request(MediaType.APPLICATION_JSON).get(ProviderMetadata.class);
		client.close();
		jwks = new HttpsJwks(metadata.getJwks_uri());
		return metadata;
	}

	@Override
	public String authorizeUrl(final AuthRequest authRequest) {
		if(metadata == null) {
			discovery();
		}
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
				LOGGER.log(Level.SEVERE, "[AuthRequest.redirect_uri]", e);
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
				LOGGER.log(Level.SEVERE, "[AuthRequest.state]", e);
			}
		});
		return authRequestUrl.toString();
	}

	@Override
	public TokenResponse accessToken(final TokenRequest tokenRequest, final List<Entry<String, String>> params) throws TokenErrorResponse {
		if(metadata == null) {
			discovery();
		}
		tokenRequest.setGrant_type("authorization_code");
		final Form form = new Form();
		form.param("client_id", tokenRequest.getClient_id());
		form.param("code", tokenRequest.getCode());
		form.param("grant_type", tokenRequest.getGrant_type());
		form.param("redirect_uri", tokenRequest.getRedirect_uri());
		if(params != null) {
			for(Entry<String, String> param : params) {
				form.param(param.getKey(), param.getValue());
			}
		}
		final Client client = ClientBuilder.newClient();
		try(Response response = client
				.target(metadata.getToken_endpoint())
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.form(form))){
			if(response.getStatus() == Status.OK.getStatusCode()) {
				return response.readEntity(TokenResponse.class);
			}
			else {
				final TokenErrorResponse tokenError = response.readEntity(TokenErrorResponse.class);
				LOGGER.log(Level.SEVERE, "[StandardProvider.accessToken]" + tokenError.toString());
				throw tokenError;
			}
		}
		finally {
			client.close();
		}
	}

	@Override
	public boolean validateIDToken(final String idToken, final String clientId) {
		boolean isValid = false;
		try {
			final JwksVerificationKeyResolver jwksResolver = new JwksVerificationKeyResolver(jwks.getJsonWebKeys());
			new JwtConsumerBuilder()
			.setVerificationKeyResolver(jwksResolver)
			.setExpectedIssuer(metadata.getIssuer())
			.setExpectedAudience(clientId)
			.build()
			.processToClaims(idToken);
			isValid = true;
		}
		catch (Exception ex) {
			LOGGER.log(Level.INFO, "[SecurityAuth.validateToken]", ex);
		}
		return isValid;
	}
}
