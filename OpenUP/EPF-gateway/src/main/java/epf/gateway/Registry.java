package epf.gateway;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.util.MapUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class Registry implements HealthCheck  {
	
	/**
	 * 
	 */
	private transient final Map<String, URI> remotes = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.Registry.REGISTRY_URL)
	@Inject
	String registryUrl;
	
	/**
	 * @param name
	 * @return
	 */
	public Optional<URI> lookup(final String name) {
		return MapUtil.get(remotes, name);
	}

	@Override
	public HealthCheckResponse call() {
		if(remotes.isEmpty()) {
			ClientBuilder
			.newClient()
			.target(registryUrl)
			.queryParam(Naming.Registry.Filter.SCHEME, "http", "ws")
			.request()
			.get()
			.getLinks()
			.forEach(link -> remotes.put(link.getRel(), link.getUri()));
		}
		if(remotes.isEmpty()) {
			return HealthCheckResponse.down("EPF-gateway-registry");
		}
		return HealthCheckResponse.up("EPF-gateway-registry");
	}
}
