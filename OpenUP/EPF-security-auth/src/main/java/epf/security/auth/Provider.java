package epf.security.auth;

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
	ProviderMetadata discovery() throws Exception;
	
	/**
	 * @param request
	 * @return
	 * @throws TokenErrorResponse
	 */
	TokenResponse accessToken(final TokenRequest tokenRequest) throws TokenErrorResponse, Exception;
	
	/**
	 * @param idToken
	 * @param sessionId
	 * @return
	 */
	boolean validateIDToken(final String idToken, final String sessionId) throws Exception;
	
	/**
	 * @param accessToken
	 * @param tokenType
	 * @return
	 * @throws Exception
	 */
	UserInfo getUserInfo(final String accessToken, final String tokenType) throws Exception;
}
