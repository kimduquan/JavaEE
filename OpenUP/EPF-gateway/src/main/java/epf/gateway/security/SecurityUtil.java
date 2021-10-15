/**
 * 
 */
package epf.gateway.security;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.websocket.Session;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import epf.gateway.cache.CacheUtil;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;

/**
 * @author PC
 *
 */
@Path("/")
public interface SecurityUtil {
	
	/**
	 * @param token
	 * @return
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	Response authenticate(@HeaderParam(HttpHeaders.AUTHORIZATION) final String token);
	
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
	static boolean authenticateTokenId(final String tokenId) throws Exception {
		boolean succeed = false;
		final URI cacheUrl = ConfigUtil.getURI(Naming.Cache.CACHE_URL);
		final Map<String, Object> data = RestClientBuilder.newBuilder().baseUri(cacheUrl).build(CacheUtil.class).getToken(tokenId);
		if(data != null && data.containsKey("rawToken")) {
			final String rawToken = (String) data.get("rawToken");
			final URI securityUrl = ConfigUtil.getURI(Naming.Security.SECURITY_URL);
			final StringBuilder builder = new StringBuilder();
			builder.append("Bearer ").append(rawToken);
			try(Response response = RestClientBuilder.newBuilder().baseUri(securityUrl).build(SecurityUtil.class).authenticate(builder.toString())){
				if(response.getStatus() == Response.Status.OK.getStatusCode()) {
					succeed = true;
				}
			}
		}
		return succeed;
	}
}
