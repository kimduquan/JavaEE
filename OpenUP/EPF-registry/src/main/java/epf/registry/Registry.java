package epf.registry;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
import epf.client.file.Files;
import epf.client.gateway.Gateway;
import epf.client.management.Management;
import epf.client.messaging.Messaging;
import epf.client.script.Script;
import epf.client.persistence.Persistence;
import epf.client.planning.Planning;
import epf.client.rules.Rules;
import epf.client.schema.Schema;
import epf.client.security.Security;

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
			final URI gatewayUrl = new URI(System.getenv(Gateway.GATEWAY_URL));
			String remote = "file";
			final URI fileUrl = new URI(System.getenv(Files.FILE_URL));
			remotes.put(remote, fileUrl);
			final URI persistenceUrl = new URI(System.getenv(Persistence.PERSISTENCE_URL));
			remote = "persistence";
			remotes.put(remote, persistenceUrl);
			final URI securityUrl = new URI(System.getenv(Security.SECURITY_URL));
			remote = "security";
			remotes.put(remote, securityUrl);
			remote = "stream";
			remotes.put(remote, gatewayUrl.resolve(remote));
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
			final URI scriptUrl = new URI(System.getenv(Script.SCRIPT_URL));
			remote = "script";
			remotes.put(remote, scriptUrl);
			final URI managementUrl = new URI(System.getenv(Management.MANAGEMENT_URL));
			remote = "management";
			remotes.put(remote, managementUrl);
			final URI rulesUrl = new URI(System.getenv(Rules.RULES_URL));
			remote = "rules";
			remotes.put(remote, rulesUrl);
			final URI schemaUrl = new URI(System.getenv(Schema.SCHEMA_URL));
			remote = "schema";
			remotes.put(remote, schemaUrl);
			final URI planningUrl = new URI(System.getenv(Planning.PLANNING_URL));
			remote = "planning";
			remotes.put(remote, planningUrl);
		} 
		catch (Exception e) {
			logger.throwing(getClass().getName(), "postConstruct", e);
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
