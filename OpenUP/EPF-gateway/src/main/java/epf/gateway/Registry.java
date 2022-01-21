package epf.gateway;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.ClientBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.naming.Naming;

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
	String registryUrl;
    
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		ClientBuilder.newClient().target(registryUrl).request().rx().get()
		.thenAccept(response -> {
			response
			.getLinks()
			.forEach(link -> {
				remotes.put(link.getRel(), link.getUri());
			});
		});
	}
	
	/**
	 * @param name
	 * @return
	 */
	public URI lookup(final String name) {
		return remotes.get(name);
	}
}
