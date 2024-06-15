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
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.naming.Naming;
import epf.util.logging.LogManager;
import io.smallrye.common.annotation.RunOnVirtualThread;

/**
 * @author PC
 *
 */
@Path(Naming.REGISTRY)
@ApplicationScoped
public class Registry {
	
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
	transient URI netUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.File.FILE_URL)
	transient URI fileUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Mail.MAIL_URL)
	transient URI mailUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Persistence.PERSISTENCE_URL)
	transient URI persistenceUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Security.SECURITY_URL)
	transient URI securityUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Security.Management.SECURITY_MANAGEMENT_URL)
	transient URI securityManagementUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Registry.REGISTRY_URL)
	transient URI registryUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Messaging.MESSAGING_URL)
	transient URI messagingUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Cache.CACHE_URL)
	transient URI cacheUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Query.QUERY_URL)
	transient URI queryUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Config.CONFIG_URL)
	transient URI configUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Script.SCRIPT_URL)
	transient URI scriptUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Management.MANAGEMENT_URL)
	transient URI managementUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Rules.RULES_URL)
	transient URI rulesUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Schema.SCHEMA_URL)
	transient URI schemaUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Planning.PLANNING_URL)
	transient URI planningUrl;

	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Image.IMAGE_URL)
	transient URI imageUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Workflow.WORKFLOW_URL)
	transient URI workflowUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Workflow.Management.WORKFLOW_MANAGEMENT_URL)
	transient URI workflowManagementUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Event.EVENT_URL)
	transient URI eventUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Concurrent.CONCURRENT_URL)
	transient URI concurrentUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Lang.LANG_URL)
	transient URI langUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.OLLAMA)
	transient URI ollamaUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Net.HTTP_PORT)
	transient int httpPort;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			remotes.put(Naming.NET, netUrl);
			remotes.put(Naming.FILE, fileUrl);
			remotes.put(Naming.MAIL, mailUrl);
			remotes.put(Naming.PERSISTENCE, persistenceUrl);
			remotes.put(Naming.SECURITY, securityUrl);
			remotes.put(Naming.Security.SECURITY_MANAGEMENT, securityManagementUrl);
			remotes.put(Naming.REGISTRY, registryUrl);
			remotes.put(Naming.MESSAGING, messagingUrl);
			remotes.put(Naming.CACHE, cacheUrl);
			remotes.put(Naming.QUERY, queryUrl);
			remotes.put(Naming.CONFIG, configUrl);
			remotes.put(Naming.SCRIPT, scriptUrl);
			remotes.put(Naming.MANAGEMENT, managementUrl);
			remotes.put(Naming.RULES, rulesUrl);
			remotes.put(Naming.SCHEMA, schemaUrl);
			remotes.put(Naming.PLANNING, planningUrl);
			remotes.put(Naming.IMAGE, imageUrl);
			remotes.put(Naming.WORKFLOW, workflowUrl);
			remotes.put(Naming.Workflow.WORKFLOW_MANAGEMENT, workflowManagementUrl);
			remotes.put(Naming.EVENT, eventUrl);
			remotes.put(Naming.CONCURRENT, concurrentUrl);
			remotes.put(Naming.LANG, langUrl);
			remotes.put(Naming.Lang.Internal.OLLAMA, ollamaUrl);
			remotes.forEach((name, url) -> {
				LOGGER.info(String.format("%s=%s", name, url));
			});
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
		remoteURIs.forEach((name, url) -> {
			LOGGER.info(String.format("%s=%s", name, url));
		});
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

	/**
	 * @param name
	 * @param remote
	 * @param version
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@RunOnVirtualThread
	public void bind(
			@FormParam(Naming.Registry.Client.NAME) 
			final String name, 
			@FormParam(Naming.Registry.Client.REMOTE) 
			final URI remote, 
			@QueryParam(Naming.Registry.Client.VERSION) 
			final String version) {
		getRemotes(version).put(name, remote);
	}

	/**
	 * @param version
	 * @param uriInfo
	 * @return
	 */
	@GET
	@RunOnVirtualThread
	public Response list(
			@QueryParam(Naming.Registry.Client.VERSION) 
			final String version, 
			@Context 
			final UriInfo uriInfo) {
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

	/**
	 * @param name
	 * @param version
	 * @return
	 */
	@GET
	@Path("{name}")
	@RunOnVirtualThread
	public Response lookup(
			@PathParam(Naming.Registry.Client.NAME) 
			final String name, 
			@QueryParam(Naming.Registry.Client.VERSION) 
			final String version) {
		final ResponseBuilder builder = Response.ok();
		getRemotes(version).computeIfPresent(name, (key, remote) -> {
			builder.link(remote, name);
			return remote;
		});
		return builder.build();
	}

	/**
	 * @param name
	 * @param remote
	 * @param version
	 */
	@PUT
	@Path("{name}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@RunOnVirtualThread
	public void rebind(
			@PathParam(Naming.Registry.Client.NAME) 
			final String name, 
			@FormParam(Naming.Registry.Client.REMOTE) 
			final URI remote, 
			@QueryParam(Naming.Registry.Client.VERSION) 
			final String version) {
		getRemotes(version).computeIfPresent(name, (key, value) -> {
			return remote;
		});
	}

	/**
	 * @param name
	 * @param version
	 */
	@DELETE
	@Path("{name}")
	@RunOnVirtualThread
	public void unbind(
			@PathParam(Naming.Registry.Client.NAME) 
			final String name, 
			@QueryParam(Naming.Registry.Client.VERSION) 
			final String version) {
		getRemotes(version).remove(name);
	}

}
