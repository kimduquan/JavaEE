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
import epf.client.image.Image;
import epf.client.lang.Lang;
import epf.client.management.Management;
import epf.client.net.Net;
import epf.client.script.Script;
import epf.client.persistence.Persistence;
import epf.client.planning.Planning;
import epf.client.rules.Rules;
import epf.client.schema.Schema;
import epf.client.security.Security;
import epf.messaging.client.Messaging;
import epf.util.config.ConfigUtil;
import openup.client.delivery_processes.DeliveryProcesses;
import openup.client.roles.Roles;
import openup.client.tasks.Tasks;
import openup.client.work_products.WorkProducts;

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
			final URI netUrl = ConfigUtil.getURI(Net.NET_URL);
			String remote = "net";
			remotes.put(remote, netUrl);
			remote = "file";
			final URI fileUrl = ConfigUtil.getURI(Files.FILE_URL);
			remotes.put(remote, fileUrl);
			final URI persistenceUrl = ConfigUtil.getURI(Persistence.PERSISTENCE_URL);
			remote = "persistence";
			remotes.put(remote, persistenceUrl);
			final URI securityUrl = ConfigUtil.getURI(Security.SECURITY_URL);
			remote = "security";
			remotes.put(remote, securityUrl);
			final URI gatewayUrl = ConfigUtil.getURI(Gateway.GATEWAY_URL);
			remote = "stream";
			remotes.put(remote, gatewayUrl.resolve(remote));
			final URI registryUrl = ConfigUtil.getURI(Registry.REGISTRY_URL);
			remote = "registry";
			remotes.put(remote, registryUrl);
			final URI messagingUrl = ConfigUtil.getURI(Messaging.MESSAGING_URL);
			remote = "messaging";
			remotes.put(remote, messagingUrl);
			final URI cacheUrl = ConfigUtil.getURI(Cache.CACHE_URL);
			remote = "cache";
			remotes.put(remote, cacheUrl);
			final URI configUrl = ConfigUtil.getURI(Config.CONFIG_URL);
			remote = "config";
			remotes.put(remote, configUrl);
			final URI scriptUrl = ConfigUtil.getURI(Script.SCRIPT_URL);
			remote = "script";
			remotes.put(remote, scriptUrl);
			final URI managementUrl = ConfigUtil.getURI(Management.MANAGEMENT_URL);
			remote = "management";
			remotes.put(remote, managementUrl);
			final URI rulesUrl = ConfigUtil.getURI(Rules.RULES_URL);
			remote = "rules";
			remotes.put(remote, rulesUrl);
			final URI schemaUrl = ConfigUtil.getURI(Schema.SCHEMA_URL);
			remote = "schema";
			remotes.put(remote, schemaUrl);
			final URI planningUrl = ConfigUtil.getURI(Planning.PLANNING_URL);
			remote = "planning";
			remotes.put(remote, planningUrl);
			final URI imageUrl = ConfigUtil.getURI(Image.IMAGE_URL);
			remote = "image";
			remotes.put(remote, imageUrl);
			final URI langUrl = ConfigUtil.getURI(Lang.LANG_URL);
			remote = "lang";
			remotes.put(remote, langUrl);
			final URI rolesUrl = ConfigUtil.getURI(Roles.ROLES_URL);
			remote = "roles";
			remotes.put(remote, rolesUrl);
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
