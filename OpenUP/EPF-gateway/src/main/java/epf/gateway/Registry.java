package epf.gateway;

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
public class Registry {

	private static final String SERVICE_URL = System.getenv("epf.service.url");
	
	private Map<String, URI> remotes;
	
	@Inject
	private ClientQueue clients;
	
	@Inject
	private Logger logger;
    
	@PostConstruct
	void postConstruct() {
		remotes = new ConcurrentHashMap<>();
		try(Client client = new Client(clients, new URI(SERVICE_URL).resolve("registry"), b -> b)){
			client
			.request(
					target -> target, 
					req -> req
					)
			.get()
			.getLinks()
			.forEach(link -> {
				remotes.put(link.getRel(), link.getUri());
			});
		} 
		catch (Exception e) {
			logger.log(Level.SEVERE, SERVICE_URL, e);
		}
	}
	
	public URI lookup(String name) {
		return remotes.get(name);
	}
}
