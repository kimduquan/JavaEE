package epf.service.registry;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import epf.client.config.ConfigNames;

/**
 * @author PC
 *
 */
@Path("registry")
@ApplicationScoped
public class Registry implements epf.client.registry.Registry {
	
	/**
	 * 
	 */
	private transient final Map<String, URI> remotes;
	
	/**
	 * 
	 */
	@Inject
	private transient Logger logger;
	
	/**
	 * 
	 */
	public Registry() {
		remotes = new ConcurrentHashMap<>();
	}
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI serviceUrl = new URI(System.getenv(ConfigNames.SERVICE_URL));
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
	public void bind(final String name, final URI remote) {
		remotes.put(name, remote);
	}

	@Override
	public Response list() {
		ResponseBuilder builder = Response.ok();
		final List<Link> links = remotes
		.entrySet()
		.stream()
		.map(entry -> Link
				.fromUri(entry.getValue())
				.rel(entry.getKey())
				.build()
				)
		.collect(Collectors.toList());
		builder = builder.links(links.toArray(new Link[0]));
		return builder.build();
	}

	@Override
	public Response lookup(final String name) {
		final ResponseBuilder builder = Response.ok();
		remotes.computeIfPresent(name, (key, remote) -> {
			builder.link(remote, name);
			return remote;
		});
		return builder.build();
	}

	@Override
	public void rebind(final String name, final URI remote) {
		remotes.computeIfPresent(name, (key, value) -> {
			return remote;
		});
	}

	@Override
	public void unbind(final String name) {
		remotes.remove(name);
	}

}
