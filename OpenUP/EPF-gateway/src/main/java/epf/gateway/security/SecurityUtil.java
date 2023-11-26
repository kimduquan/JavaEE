package epf.gateway.security;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.websocket.Session;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
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
	 * @param tenant
	 * @param token
	 * @return
	 */
	static Response authenticate(final Client client, final URI securityUrl, final Optional<String> tenant, final String token) {
		final StringBuilder builder = new StringBuilder();
		builder.append("Bearer ").append(token);
		WebTarget target = client.target(securityUrl);
		if(tenant.isPresent()) {
			target = target.matrixParam(Naming.Management.TENANT, tenant);
		}
		return target.request(MediaType.APPLICATION_JSON_TYPE)
				.header(HttpHeaders.AUTHORIZATION, builder.toString())
				.get();
	}
}
