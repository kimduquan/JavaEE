package epf.client.registry;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.util.client.Client;
import epf.util.client.ClientQueue;

@ApplicationScoped
public class LocateRegistry {

	private static final String REGISTRY_URL = System.getenv("epf.registry.url");
	
	private Map<String, URI> remotes;
	
	@Inject
	private ClientQueue clients;
	
	@Inject
	private Logger logger;
    
	@PostConstruct
	void postConstruct() {
		remotes = new ConcurrentHashMap<>();
		try(Client client = new Client(clients, new URI(REGISTRY_URL), b -> b)){
			Registry
			.list(client)
			.forEach(link -> {
				remotes.put(link.getRel(), link.getUri());
			});
		} 
		catch (Exception e) {
			logger.log(Level.SEVERE, REGISTRY_URL, e);
		}
	}
	
	public URI lookup(String name) {
		return remotes.get(name);
	}
}
