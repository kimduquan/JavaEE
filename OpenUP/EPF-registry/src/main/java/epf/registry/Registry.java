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
import epf.naming.Naming;
import epf.util.config.ConfigUtil;
import openup.client.delivery_processes.DeliveryProcesses;
import openup.client.tasks.Tasks;
import openup.client.work_products.WorkProducts;

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
			final URI netUrl = ConfigUtil.getURI(Naming.Net.NET_URL);
			String remote;
			remotes.put(Naming.NET, netUrl);
			final URI fileUrl = ConfigUtil.getURI(Naming.File.FILE_URL);
			remotes.put(Naming.FILE, fileUrl);
			final URI persistenceUrl = ConfigUtil.getURI(Naming.Persistence.PERSISTENCE_URL);
			remotes.put(Naming.PERSISTENCE, persistenceUrl);
			final URI securityUrl = ConfigUtil.getURI(Naming.Security.SECURITY_URL);
			remotes.put(Naming.SECURITY, securityUrl);
			final URI gatewayUrl = ConfigUtil.getURI(Naming.Gateway.GATEWAY_URL);
			remote = "stream";
			remotes.put("stream", gatewayUrl.resolve(remote));
			final URI registryUrl = ConfigUtil.getURI(Naming.Registry.REGISTRY_URL);
			remotes.put(Naming.REGISTRY, registryUrl);
			final URI messagingUrl = ConfigUtil.getURI(Naming.Messaging.MESSAGING_URL);
			remotes.put(Naming.MESSAGING, messagingUrl);
			final URI cacheUrl = ConfigUtil.getURI(Naming.Cache.CACHE_URL);
			remotes.put(Naming.CACHE, cacheUrl);
			final URI configUrl = ConfigUtil.getURI(Naming.Config.CONFIG_URL);
			remotes.put(Naming.CONFIG, configUrl);
			final URI scriptUrl = ConfigUtil.getURI(Naming.Script.SCRIPT_URL);
			remotes.put(Naming.SCRIPT, scriptUrl);
			final URI managementUrl = ConfigUtil.getURI(Naming.Management.MANAGEMENT_URL);
			remotes.put(Naming.MANAGEMENT, managementUrl);
			final URI rulesUrl = ConfigUtil.getURI(Naming.Rules.RULES_URL);
			remotes.put(Naming.RULES, rulesUrl);
			final URI schemaUrl = ConfigUtil.getURI(Naming.Schema.SCHEMA_URL);
			remotes.put(Naming.SCHEMA, schemaUrl);
			final URI planningUrl = ConfigUtil.getURI(Naming.Planning.PLANNING_URL);
			remotes.put(Naming.PLANNING, planningUrl);
			final URI imageUrl = ConfigUtil.getURI(Naming.Image.IMAGE_URL);
			remotes.put(Naming.IMAGE, imageUrl);
			final URI langUrl = ConfigUtil.getURI(Naming.Lang.LANG_URL);
			remote = "lang";
			remotes.put(remote, langUrl);
			final URI deliveryProcessesUrl = ConfigUtil.getURI(DeliveryProcesses.DELIVERY_PROCESSES_URL);
			remote = "delivery-processes";
			remotes.put(remote, deliveryProcessesUrl);
			final URI tasksUrl = ConfigUtil.getURI(Tasks.TASKS_URL);
			remote = "tasks";
			remotes.put(remote, tasksUrl);
			final URI workProductsUrl = ConfigUtil.getURI(WorkProducts.WORK_PRODUCTS_URL);
			remote = "work-products";
			remotes.put(remote, workProductsUrl);
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
		if(version != null && !version.isEmpty()) {
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
