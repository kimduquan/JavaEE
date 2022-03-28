package epf.security.auth.openid;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	TokenResponse accessToken(final TokenRequest tokenRequest) throws TokenErrorResponse;
	
	/**
	 * @param idToken
	 * @return
	 * @throws Exception
	 */
	boolean validateIDToken(final String idToken);
}
