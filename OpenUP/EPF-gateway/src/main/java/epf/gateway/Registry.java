package epf.gateway;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.naming.Naming;
import epf.util.MapUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Registry {
	
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
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		ClientBuilder.newClient().target(registryUrl).queryParam(Naming.Registry.Filter.SCHEME, "http", "ws").request().get()
		.getLinks()
		.forEach(link -> {
			remotes.put(link.getRel(), link.getUri());
		});
	}
	
	/**
	 * @param name
	 * @return
	 */
	public Optional<URI> lookup(final String name) {
		return MapUtil.get(remotes, name);
	}
}
