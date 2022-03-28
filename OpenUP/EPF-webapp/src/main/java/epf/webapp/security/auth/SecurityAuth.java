package epf.webapp.security.auth;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.JwksVerificationKeyResolver;
import epf.naming.Naming;
import epf.security.auth.openid.AuthRequest;
import epf.security.auth.openid.Provider;
import epf.security.auth.openid.UserInfo;
import epf.security.auth.openid.UserInfoErrorResponse;
import epf.security.auth.openid.provider.StandardProvider;
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
	private transient Provider googleProvider;
	
	/**
	 * 
	 */
	private transient HttpsJwks googleJwks;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final Client client = ClientBuilder.newClient();
			final URI discoveryUrl = new URI(config.getProperty(Naming.Security.Auth.GOOGLE_PROVIDER));
			googleProvider = new StandardProvider(discoveryUrl);
			client.close();
			googleJwks = new HttpsJwks(googleProvider.discovery().getJwks_uri());
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[SecurityAuth.ProviderMetadata]");
		}
	}
	
	/**
	 * @param authFlow
	 * @param authRequest
	 * @return
	 */
	public Provider initGoogleProvider(final AuthFlow authFlow, final AuthRequest authRequest) {
		authFlow.setClientSecret(config.getProperty(Naming.Security.Auth.GOOGLE_CLIENT_SECRET).toCharArray());
		authRequest.setClient_id(config.getProperty(Naming.Security.Auth.GOOGLE_CLIENT_ID));
		authRequest.setRedirect_uri(config.getProperty(Naming.Security.Auth.AUTH_URL));
		return googleProvider;
	}
	
	/**
	 * @param issuer
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public boolean validateIDToken(final String issuer, final String jwt) throws Exception {
		final JwksVerificationKeyResolver jwksKeyResolver = new JwksVerificationKeyResolver(googleJwks.getJsonWebKeys());
		boolean isValid = false;
		try {
			new JwtConsumerBuilder().setVerificationKeyResolver(jwksKeyResolver).build().processToClaims(jwt);
			isValid = true;
		}
		catch (Exception ex) {
			LOGGER.log(Level.INFO, "[SecurityAuth.validateToken]", ex);
		}
		return isValid;
	}
	
	/**
	 * @param userInfoEndpoint
	 * @param accessToken
	 * @param tokenType
	 * @return
	 * @throws Exception
	 */
	public UserInfo getUserInfo(final String userInfoEndpoint, final String accessToken, final String tokenType) throws Exception {
		UserInfo userInfo = null;
		final Client client = ClientBuilder.newClient();
		final Response response = client
				.target(userInfoEndpoint)
				.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, tokenType + " " + accessToken)
				.get();
		if(response.getStatus() == Status.OK.getStatusCode()) {
			userInfo = response.readEntity(UserInfo.class);
		}
		else {
			final UserInfoErrorResponse userInfoError = response.readEntity(UserInfoErrorResponse.class);
			LOGGER.log(Level.SEVERE, "[SecurityAuth.userInfo]" + userInfoError.toString());
		}
		client.close();
		return userInfo;
	}
}
