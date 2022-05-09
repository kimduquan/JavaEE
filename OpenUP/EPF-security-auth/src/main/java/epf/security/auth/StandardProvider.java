package epf.security.auth;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.JwksVerificationKeyResolver;
import epf.security.auth.core.TokenErrorResponse;
import epf.security.auth.core.TokenRequest;
import epf.security.auth.core.TokenResponse;
import epf.security.auth.core.UserInfo;
import epf.security.auth.core.UserInfoError;
import epf.security.auth.discovery.ProviderMetadata;
import epf.util.logging.LogManager;
import epf.util.security.CryptoUtil;

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
	private transient final String clientId;
	
	/**
	 * 
	 */
	private transient final char[] clientSecret;
	
	/**
	 * 
	 */
	private transient ProviderMetadata metadata;
	
	/**
	 * 
	 */
	private transient HttpsJwks jwks;
	
	/**
	 * 
	 */
	private transient JwtConsumer jwtConsumer;
	
	/**
	 * @param discoveryUrl
	 * @param clientSecret
	 * @param clientId
	 */
	public StandardProvider(final URI discoveryUrl, final char[] clientSecret, final String clientId) {
		this.discoveryUrl = discoveryUrl;
		this.clientSecret = clientSecret;
		this.clientId = clientId;
	}

	@Override
	public ProviderMetadata discovery() throws Exception {
		final Client client = ClientBuilder.newClient();
		metadata = client.target(discoveryUrl).request(MediaType.APPLICATION_JSON).get(ProviderMetadata.class);
		client.close();
		jwks = new HttpsJwks(metadata.getJwks_uri());
		jwtConsumer = new JwtConsumerBuilder()
				.setEnableRequireIntegrity()
				.setExpectedAudience(clientId)
				.setExpectedIssuer(metadata.getIssuer())
				.setRequireExpirationTime()
				.setRequireIssuedAt()
				.setRequireJwtId()
				.setRequireNotBefore()
				.setRequireSubject()
				.setVerificationKeyResolver(new JwksVerificationKeyResolver(jwks.getJsonWebKeys()))
	            .build();
		return metadata;
	}

	@Override
	public TokenResponse accessToken(final TokenRequest tokenRequest) throws TokenErrorResponse, Exception {
		if(metadata == null) {
			discovery();
		}
		tokenRequest.setGrant_type("authorization_code");
		final Form form = new Form();
		form.param("client_id", tokenRequest.getClient_id());
		form.param("code", tokenRequest.getCode());
		form.param("grant_type", tokenRequest.getGrant_type());
		form.param("redirect_uri", tokenRequest.getRedirect_uri());
		form.param("client_secret", new String(clientSecret));
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
	public boolean validateIDToken(final String idToken, final String sessionId) throws Exception {
		if(metadata == null) {
			discovery();
		}
		boolean isValid = false;
		try {
			final JwtClaims claims = jwtConsumer.processToClaims(idToken);
			isValid = true;
			if(claims.getAudience().size() > 1) {
				final String azp = claims.getClaimValueAsString("azp");
				isValid = azp != null;
				if(isValid) {
					isValid = clientId.equals(azp);
				}
			}
			if(isValid) {
				final String nonce = claims.getClaimValueAsString("nonce");
				if(nonce != null && !nonce.isEmpty()) {
					isValid = nonce.equals(CryptoUtil.hash(sessionId));
				}
			}
		}
		catch (Exception ex) {
			LOGGER.log(Level.INFO, "[SecurityAuth.validateToken]", ex);
		}
		return isValid;
	}

	@Override
	public UserInfo getUserInfo(final String accessToken, final String tokenType) throws Exception {
		if(metadata == null) {
			discovery();
		}
		final Client client = ClientBuilder.newClient();
		try(Response response = client
				.target(metadata.getUserinfo_endpoint())
				.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, tokenType + " " + accessToken)
				.get()){
			if(response.getStatus() == Status.OK.getStatusCode()) {
				return response.readEntity(UserInfo.class);
			}
			else {
				final UserInfoError userInfoError = response.readEntity(UserInfoError.class);
				LOGGER.log(Level.SEVERE, "[SecurityAuth.userInfo]" + userInfoError.toString());
				throw userInfoError;
			}
		}
		finally {
			client.close();
		}
	}
}
