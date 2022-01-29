package epf.registry;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.naming.Naming;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@Path(Naming.REGISTRY)
@ApplicationScoped
public class Registry implements epf.client.registry.Registry {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Registry.class.getName());
	
	/**
	 * 
	 */
	private transient final Map<String, URI> remotes = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, Map<String, URI>> remoteVersions = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Net.NET_URL)
	private transient URI netUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.File.FILE_URL)
	private transient URI fileUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Persistence.PERSISTENCE_URL)
	private transient URI persistenceUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Security.SECURITY_URL)
	private transient URI securityUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Gateway.GATEWAY_URL)
	private transient URI gatewayUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Registry.REGISTRY_URL)
	private transient URI registryUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Messaging.MESSAGING_URL)
	private transient URI messagingUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Cache.CACHE_URL)
	private transient URI cacheUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Config.CONFIG_URL)
	private transient URI configUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Script.SCRIPT_URL)
	private transient URI scriptUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Management.MANAGEMENT_URL)
	private transient URI managementUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Rules.RULES_URL)
	private transient URI rulesUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Schema.SCHEMA_URL)
	private transient URI schemaUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Planning.PLANNING_URL)
	private transient URI planningUrl;

	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Image.IMAGE_URL)
	private transient URI imageUrl;

	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Lang.LANG_URL)
	private transient URI langUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Net.HTTP_PORT)
	private transient int httpPort;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			remotes.put(Naming.NET, netUrl);
			remotes.put(Naming.FILE, fileUrl);
			remotes.put(Naming.PERSISTENCE, persistenceUrl);
			remotes.put(Naming.SECURITY, securityUrl);
			remotes.put(Naming.REGISTRY, registryUrl);
			remotes.put(Naming.MESSAGING, messagingUrl);
			remotes.put(Naming.CACHE, cacheUrl);
			remotes.put(Naming.CONFIG, configUrl);
			remotes.put(Naming.SCRIPT, scriptUrl);
			remotes.put(Naming.MANAGEMENT, managementUrl);
			remotes.put(Naming.RULES, rulesUrl);
			remotes.put(Naming.SCHEMA, schemaUrl);
			remotes.put(Naming.PLANNING, planningUrl);
			remotes.put(Naming.IMAGE, imageUrl);
			remotes.put("lang", langUrl);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "postConstruct", e);
		}
		
	}
	
	/**
	 * @param version
	 * @return
	 */
	protected Map<String, URI> getRemotes(final String version){
		Map<String, URI> remoteURIs = remotes;
		if(version != null && !version.isEmpty()) {
			remoteURIs = remoteVersions.computeIfAbsent(version, ver -> new ConcurrentHashMap<>());
		}
		return remoteURIs;
	}
	
	/**
	 * @param links
	 * @param scheme
	 * @return
	 */
	protected Stream<Link> mapScheme(final Stream<Link> links, final String scheme, final String defaultSchem, final int port){
		return links.flatMap(link -> {
			if(defaultSchem.equals(link.getUri().getScheme())) {
				final Link httpLink = Link.fromLink(link).uriBuilder(link.getUriBuilder().scheme(scheme).port(port)).build();
				return Stream.of(httpLink);
			}
			return Stream.of(link);
		});
	}
	
	/**
	 * @param links
	 * @param schemes
	 * @return
	 */
	protected Stream<Link> filterScheme(final Stream<Link> links, final List<String> schemes){
		Stream<Link> stream = links;
		if(schemes.contains("http")) {
			stream = mapScheme(stream, "http", "https", httpPort);
		}
		if(schemes.contains("ws")) {
			stream = mapScheme(stream, "ws", "wss", httpPort);
		}
		return stream;
	}
	
	/**
	 * @param links
	 * @param uriInfo
	 * @return
	 */
	protected Stream<Link> filter(final Stream<Link> links, final UriInfo uriInfo){
		final List<Predicate<? super Link>> filters = new ArrayList<>();
		Stream<Link> stream = links;
		uriInfo.getQueryParameters().forEach((name, values) -> {
			switch(name) {
			case Naming.Registry.Filter.ABSOLUTE:
				filters.add(link -> values.contains("" + link.getUri().isAbsolute()));
				break;
			case Naming.Registry.Filter.OPAQUE:
				filters.add(link -> values.contains("" + link.getUri().isOpaque()));
				break;
			case Naming.Registry.Filter.AUTHORITY:
				filters.add(link -> values.contains(link.getUri().getAuthority()));
				break;
			case Naming.Registry.Filter.FRAGMENT:
				filters.add(link -> values.contains(link.getUri().getFragment()));
				break;
			case Naming.Registry.Filter.HOST:
				filters.add(link -> values.contains(link.getUri().getHost()));
				break;
			case Naming.Registry.Filter.PATH:
				filters.add(link -> values.contains(link.getUri().getPath()));
				break;
			case Naming.Registry.Filter.PORT:
				filters.add(link -> values.contains("" + link.getUri().getPort()));
				break;
			case Naming.Registry.Filter.QUERY:
				filters.add(link -> values.contains(link.getUri().getQuery()));
				break;
			case Naming.Registry.Filter.USER_INFO:
				filters.add(link -> values.contains(link.getUri().getUserInfo()));
				break;
			}
		});
		for(Predicate<? super Link> filter : filters) {
			stream = stream.filter(filter);
		}
		return stream;
	}

	@Override
	public void bind(final String name, final URI remote, final String version) {
		getRemotes(version).put(name, remote);
	}

	@Override
	public Response list(final String version, final UriInfo uriInfo) {
		ResponseBuilder builder = Response.ok();
		Stream<Link> links = getRemotes(version)
				.entrySet()
				.stream()
				.map(entry -> Link
						.fromUri(entry.getValue())
						.rel(entry.getKey())
						.build()
						);
		if(uriInfo.getQueryParameters().containsKey(Naming.Registry.Filter.SCHEME)) {
			links = filterScheme(links, uriInfo.getQueryParameters().get(Naming.Registry.Filter.SCHEME));
		}
		links = filter(links, uriInfo);
		builder = builder.links(links.collect(Collectors.toList()).toArray(new Link[0]));
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
