package epf.service.registry;

import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import epf.client.config.ConfigNames;

@Path("registry")
@ApplicationScoped
public class Registry implements epf.client.registry.Registry {
	
	private Map<String, URI> remotes;
	
	@Inject
	private Logger logger;
	
	@PostConstruct
	void postConstruct() {
		remotes = new ConcurrentHashMap<>();
		try {
			URI serviceUrl = new URI(System.getenv(ConfigNames.SERVICE_URL));
			String remote = "config";
			remotes.put(remote, serviceUrl.resolve(remote));
			remote = "file";
			remotes.put(remote, serviceUrl.resolve(remote));
			remote = "persistence";
			remotes.put(remote, serviceUrl.resolve(remote));
			remote = "registry";
			remotes.put(remote, serviceUrl.resolve(remote));
			remote = "schema";
			remotes.put(remote, serviceUrl.resolve(remote));
			remote = "security";
			remotes.put(remote, serviceUrl.resolve(remote));
			remote = "system";
			remotes.put(remote, serviceUrl.resolve(remote));
		} 
		catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		
	}

	@Override
	public void bind(String name, URI remote) throws Exception {
		remotes.put(name, remote);
	}

	@Override
	public Response list() throws Exception {
		ResponseBuilder builder = Response.ok();
		for(Entry<String, URI> link : remotes.entrySet()) {
			builder = builder.link(link.getValue(), link.getKey());
		}
		return builder.build();
	}

	@Override
	public Response lookup(String name) throws Exception {
		ResponseBuilder builder = Response.ok();
		remotes.computeIfPresent(name, (key, remote) -> {
			builder.link(remote, name);
			return remote;
		});
		return builder.build();
	}

	@Override
	public void rebind(String name, URI remote) throws Exception {
		remotes.computeIfPresent(name, (key, value) -> {
			return remote;
		});
	}

	@Override
	public void unbind(String name) throws Exception {
		remotes.remove(name);
	}

}
