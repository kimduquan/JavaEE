package epf.gateway;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.naming.Naming;
import epf.util.MapUtil;
import epf.util.logging.LogManager;
import io.smallrye.common.annotation.RunOnVirtualThread;

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
	@ConfigProperty(name = Naming.Registry.REGISTRY_URL)
	@Inject
	URI registryUrl;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		initialize();
	}
	
	private void initialize() {
		if(remotes.isEmpty()) {
			final Client client = ClientBuilder.newClient();
			try {
				client.target(registryUrl)
				.queryParam(Naming.Registry.Filter.SCHEME, "http", "ws")
				.request()
				.get()
				.getLinks()
				.forEach(link -> remotes.put(link.getRel(), link.getUri()));
				remotes.forEach((name, url) -> {
					LOGGER.info(String.format("%s=%s", name, url));
				});
			}
			catch(Exception ex) {
				LOGGER.log(Level.SEVERE, "[Registry.call]", ex);
			}
			finally {
				client.close();
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
	
	/**
	 * @param remotes
	 */
	@Incoming(Naming.Registry.EPF_REGISTRY_REMOTES)
	@RunOnVirtualThread
	public void setRemotes(final Map<String, Object> remotes) {
		remotes.forEach((name, value) -> {
			try {
				this.remotes.put(name, new URI(value.toString()));
			}
			catch(Exception ex) {
				LOGGER.log(Level.SEVERE, "[Registry.setRemotes]", ex);
			}
		});
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
