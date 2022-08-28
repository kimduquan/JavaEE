package epf.gateway;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.client.util.ClientQueue;
import epf.naming.Naming;
import epf.util.MapUtil;
import epf.util.logging.LogManager;

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
	private transient static final Logger LOGGER = LogManager.getLogger(Registry.class.getName());
	
	/**
	 * 
	 */
	private transient final Map<String, URI> remotes = new ConcurrentHashMap<>();
	
	/**
	 *
	 */
	@Inject
	transient ClientQueue clients;
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.Registry.REGISTRY_URL)
	@Inject
	String registryUrl;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		initialize();
	}
	
	private void initialize() {
		if(remotes.isEmpty()) {
			try {
				final Client client = clients.poll(new URI(registryUrl), null);
				client.target(registryUrl)
				.queryParam(Naming.Registry.Filter.SCHEME, "http", "ws")
				.request()
				.get()
				.getLinks()
				.forEach(link -> remotes.put(link.getRel(), link.getUri()));
				clients.add(new URI(registryUrl), client);
			}
			catch(Exception ex) {
				LOGGER.log(Level.SEVERE, "[Registry.call]", ex);
			}
		}
	}
	
	/**
	 * @param name
	 * @return
	 */
	public Optional<URI> lookup(final String name) {
		return MapUtil.get(remotes, name);
	}

	@Override
	public HealthCheckResponse call() {
		initialize();
		if(remotes.isEmpty()) {
			return HealthCheckResponse.down("EPF-gateway-registry");
		}
		return HealthCheckResponse.up("EPF-gateway-registry");
	}
}
