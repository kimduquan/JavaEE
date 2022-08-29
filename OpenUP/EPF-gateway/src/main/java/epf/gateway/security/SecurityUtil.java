package epf.gateway.security;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import javax.websocket.Session;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author PC
 *
 */
public interface SecurityUtil {
	
	/**
	 * @param session
	 * @return
	 */
	static Optional<String> getToken(final Session session) {
		Optional<String> tokenId = Optional.empty();
		final Map<String, List<String>> params = session.getRequestParameterMap();
		if(params != null && params.containsKey("token")) {
			final List<String> tid = session.getRequestParameterMap().get("token");
			if(tid != null && tid.size() > 0) {
				tokenId = Optional.of(tid.get(0));
			}
		}
		return tokenId;
	}
	
	/**
	 * @param client
	 * @param securityUrl
	 * @param token
	 * @return
	 */
	static CompletionStage<Response> authenticate(final Client client, final URI securityUrl, final String token) {
		final StringBuilder builder = new StringBuilder();
		builder.append("Bearer ").append(token);
		return client.target(securityUrl).request(MediaType.APPLICATION_JSON_TYPE).header(HttpHeaders.AUTHORIZATION, builder.toString()).rx().get();
	}
}
