package epf.gateway.messaging;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.Session;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.health.Readiness;
import epf.gateway.Registry;
import epf.naming.Naming;

@ApplicationScoped
public class Security {
	
	private URI securityUrl;
	
	@Inject @Readiness
	transient Registry registry;
	
	@PostConstruct
	protected void postConstruct() {
		securityUrl = registry.lookup(Naming.SECURITY).orElseThrow(() -> new NoSuchElementException(Naming.SECURITY));
	}
	
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
