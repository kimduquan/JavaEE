package epf.security.auth;

import epf.security.auth.core.TokenErrorResponse;
import epf.security.auth.core.TokenRequest;
import epf.security.auth.core.TokenResponse;
import epf.security.auth.core.UserInfo;
import epf.security.auth.discovery.ProviderMetadata;

public interface Provider {
	
	ProviderMetadata discovery() throws Exception;
	
	TokenResponse accessToken(final TokenRequest tokenRequest) throws TokenErrorResponse, Exception;
	
	boolean validateIDToken(final String idToken, final String sessionId) throws Exception;
	
	UserInfo getUserInfo(final String accessToken, final String tokenType) throws Exception;
}
