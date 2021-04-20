package epf.registry;

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
import epf.client.cache.Cache;
import epf.client.config.Config;
import epf.client.messaging.Messaging;

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
	private transient final Map<String, Map<String, URI>> remoteVersions;
	
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
		remoteVersions = new ConcurrentHashMap<>();
	}
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI serviceUrl = new URI(System.getenv(SERVICE_URL));
			String remote = "file";
			remotes.put(remote, serviceUrl.resolve(remote));
			remote = "persistence";
			remotes.put(remote, serviceUrl.resolve(remote));
			remote = "schema";
			remotes.put(remote, serviceUrl.resolve(remote));
			remote = "security";
			remotes.put(remote, serviceUrl.resolve(remote));
			remote = "system";
			remotes.put(remote, serviceUrl.resolve(remote));
			remote = "stream";
			remotes.put(remote, serviceUrl.resolve(remote));
			final URI registryUrl = new URI(System.getenv(Registry.REGISTRY_URL));
			remote = "registry";
			remotes.put(remote, registryUrl);
			final URI messagingUrl = new URI(System.getenv(Messaging.MESSAGING_URL));
			remote = "messaging";
			remotes.put(remote, messagingUrl);
			final URI cacheUrl = new URI(System.getenv(Cache.CACHE_URL));
			remote = "cache";
			remotes.put(remote, cacheUrl);
			final URI configUrl = new URI(System.getenv(Config.CONFIG_URL));
			remote = "config";
			remotes.put(remote, configUrl);
		} 
		catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		
	}
	
	/**
	 * @param version
	 * @return
	 */
	protected Map<String, URI> getRemotes(final String version){
		Map<String, URI> remoteURIs = remotes;
		if(version != null) {
			remoteURIs = remoteVersions.computeIfAbsent(version, ver -> new ConcurrentHashMap<>());
		}
		return remoteURIs;
	}

	@Override
	public void bind(final String name, final URI remote, final String version) {
		getRemotes(version).put(name, remote);
	}

	@Override
	public Response list(final String version) {
		ResponseBuilder builder = Response.ok();
		final List<Link> links = getRemotes(version)
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
	public Response lookup(final String name, final String version) {
		final ResponseBuilder builder = Response.ok();
		getRemotes(version).computeIfPresent(name, (key, remote) -> {
			builder.link(remote, name);
			return remote;
		});
		return builder.build();
	}

	@Override
	public void rebind(final String name, final URI remote, final String version) {
		getRemotes(version).computeIfPresent(name, (key, value) -> {
			return remote;
		});
	}

	@Override
	public void unbind(final String name, final String version) {
		getRemotes(version).remove(name);
	}

}
