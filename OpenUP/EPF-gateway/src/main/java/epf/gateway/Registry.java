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
import epf.naming.Naming;
import epf.util.MapUtil;
import epf.util.logging.LogManager;

@ApplicationScoped
@Readiness
public class Registry implements HealthCheck  {
	
	private transient static final Logger LOGGER = LogManager.getLogger(Registry.class.getName());
	
	private transient final Map<String, URI> remotes = new ConcurrentHashMap<>();
	
	private URI localhost;
	
	@ConfigProperty(name = Naming.Registry.REGISTRY_URL)
	@Inject
	URI registryUrl;
	
	@ConfigProperty(name = "quarkus.http.port")
	@Inject
	String port;
	
	@PostConstruct
	protected void postConstruct() {
		initialize();
	}
	
	private void initialize() {
		if(remotes.isEmpty()) {
			try(Client client = ClientBuilder.newClient()) {
				localhost = new URI("http://localhost:" + port);
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
		}
	}
	
	public Optional<URI> lookup(final String name) {
		if(Naming.GATEWAY.equals(name)) {
			return Optional.of(localhost);
		}
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
