package epf.security.auth.openid.core;

/**
 * @author PC
 *
 */
public interface CodeFlowAuth {

	/**
	 * @param authRequest
	 * @return
	 * @throws AuthError
	 */
	AuthResponse authenticate(final AuthRequest authRequest) throws AuthError;
	
	/**
	 * @param tokenRequest
	 * @return
	 * @throws TokenErrorResponse
	 */
	TokenResponse requestToken(final TokenRequest tokenRequest) throws TokenErrorResponse;
}
