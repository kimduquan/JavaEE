/**
 * 
 */
package epf.gateway.security;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import javax.websocket.Session;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;

/**
 * @author PC
 *
 */
public interface SecurityUtil {
	
	/**
	 * @param session
	 * @return
	 */
	static Optional<String> getTokenId(final Session session) {
		Optional<String> tokenId = Optional.empty();
		final Map<String, List<String>> params = session.getRequestParameterMap();
		if(params != null && params.containsKey("tid")) {
			final List<String> tid = session.getRequestParameterMap().get("tid");
			if(tid != null && tid.size() > 0) {
				tokenId = Optional.of(tid.get(0));
			}
		}
		return tokenId;
	}
	
	/**
	 * @param tokenId
	 * @return
	 * @throws Exception
	 */
	static CompletionStage<Boolean> authenticateTokenId(final String tokenId) throws Exception {
		final URI cacheUrl = ConfigUtil.getURI(Naming.Cache.CACHE_URL);
		final CompletionStage<Map<String, Object>> tokenClaims = ClientBuilder.newClient().target(cacheUrl).path(Naming.SECURITY).queryParam("tid", tokenId).request(MediaType.APPLICATION_JSON_TYPE).rx().get(new GenericType<Map<String, Object>>(){});
		return tokenClaims
		.thenApply(SecurityUtil::getRawToken)
		.thenCompose(SecurityUtil::buildAuthenticateRequest)
		.thenApply(response -> response.getStatus() == Response.Status.OK.getStatusCode());
	}
	
	/**
	 * @param claims
	 * @return
	 */
	static String getRawToken(final Map<String, Object> claims) {
		if(claims != null && claims.containsKey("rawToken")) {
			final String rawToken = (String) claims.get("rawToken");
			return rawToken;
		}
		return "";
	}
	
	/**
	 * @param token
	 * @return
	 * @throws Exception
	 */
	static CompletionStage<Response> buildAuthenticateRequest(final String token){
		URI securityUrl;
		try {
			securityUrl = ConfigUtil.getURI(Naming.Security.SECURITY_URL);
		}
		catch (URISyntaxException e) {
			securityUrl = null;
		}
		final StringBuilder builder = new StringBuilder();
		builder.append("Bearer ").append(token);
		return ClientBuilder.newClient().target(securityUrl).request(MediaType.APPLICATION_JSON_TYPE).header(HttpHeaders.AUTHORIZATION, builder.toString()).rx().get();
	}
}
