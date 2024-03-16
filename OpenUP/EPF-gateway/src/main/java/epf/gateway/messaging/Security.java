package epf.gateway.messaging;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.eclipse.microprofile.health.Readiness;
import epf.gateway.Registry;
import epf.naming.Naming;

/**
 * 
 */
@ApplicationScoped
public class Security {
	
	/**
	 *
	 */
	private URI securityUrl;
	
	/**
	 * 
	 */
	@Inject @Readiness
	transient Registry registry;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		securityUrl = registry.lookup(Naming.SECURITY).orElseThrow(() -> new NoSuchElementException(Naming.SECURITY));
	}
	
	/**
	 * @param session
	 * @return
	 */
	public Optional<String> getToken(final Session session) {
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
	public Response authenticate(final Client client, final URI securityUrl, final Optional<String> tenant, final String token) {
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
	
	/**
	 * @param tenant
	 * @param token
	 * @param remotePath
	 * @param session
	 * @return
	 */
	public boolean authenticate(final Optional<String> tenant, final String token, final String remotePath, final Session session) {
		final Client client = ClientBuilder.newClient();
		try(Response response = authenticate(client, securityUrl, tenant, token)){
			if(response.getStatus() == Status.OK.getStatusCode()) {
				return true;
			}
		}
		catch(Exception ex) {
			return false;
		}
		finally {
			client.close();
		}
		return false;
	}
}
