package epf.security.auth;

import java.util.List;
import java.util.Map.Entry;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import epf.security.auth.core.AuthRequest;
import epf.security.auth.core.TokenErrorResponse;
import epf.security.auth.core.TokenRequest;
import epf.security.auth.core.TokenResponse;
import epf.security.auth.core.UserInfo;
import epf.security.auth.discovery.ProviderMetadata;

/**
 * @author PC
 *
 */
public interface Provider {

	/**
	 * @return
	 */
	@Path(".well-known/openid-configuration")
	@Produces(MediaType.APPLICATION_JSON)
	ProviderMetadata discovery();
	
	/**
	 * @param request
	 * @return
	 * @throws AuthError
	 */
	String authorizeUrl(final AuthRequest request);
	
	/**
	 * @param request
	 * @return
	 * @throws TokenErrorResponse
	 */
	TokenResponse accessToken(final TokenRequest tokenRequest, final List<Entry<String, String>> params) throws TokenErrorResponse;
	
	/**
	 * @param idToken
	 * @return
	 */
	boolean validateIDToken(final String idToken);
	
	/**
	 * @param accessToken
	 * @param tokenType
	 * @return
	 * @throws Exception
	 */
	UserInfo getUserInfo(final String accessToken, final String tokenType) throws Exception;
}
