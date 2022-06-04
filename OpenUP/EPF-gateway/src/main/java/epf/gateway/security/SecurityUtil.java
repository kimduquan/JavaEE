/**
 * 
 */
package epf.gateway.security;

import java.net.URI;
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
		if(params != null && params.containsKey("t")) {
			final List<String> tid = session.getRequestParameterMap().get("t");
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
	static CompletionStage<Boolean> authenticateTokenId(final String tokenId, final URI queryUrl, final URI securityUrl) throws Exception {
		final CompletionStage<Map<String, Object>> tokenClaims = ClientBuilder.newClient().target(queryUrl).path(Naming.SECURITY).queryParam("t", tokenId).request(MediaType.APPLICATION_JSON_TYPE).rx().get(new GenericType<Map<String, Object>>(){});
		return tokenClaims
		.thenApply(SecurityUtil::getRawToken)
		.thenCompose(token -> buildAuthenticateRequest(token, securityUrl))
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
	static CompletionStage<Response> buildAuthenticateRequest(final String token, final URI securityUrl){
		final StringBuilder builder = new StringBuilder();
		builder.append("Bearer ").append(token);
		return ClientBuilder.newClient().target(securityUrl).request(MediaType.APPLICATION_JSON_TYPE).header(HttpHeaders.AUTHORIZATION, builder.toString()).rx().get();
	}
}
