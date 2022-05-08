package epf.cache.persistence;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.messaging.client.Client;
import epf.messaging.client.Messaging;
import epf.naming.Naming;
import epf.schema.utility.EntityEvent;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.util.websocket.Message;
import epf.util.websocket.MessageQueue;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class Persistence implements HealthCheck {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Persistence.class.getName());
	
	/**
	 * 
	 */
	private transient Client client;
	
	/**
	 * 
	 */
	private transient MessageQueue messages;
	
	/**
	 * 
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@Inject @Readiness
	private transient EntityCache entityCache;
	
	/**
	 * 
	 */
	@Inject @Readiness
	private transient QueryCache queryCache;
	
	/**
	 * 
	 */
	@Inject @Readiness
	private transient PersistenceCache cache;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI messagingUrl = ConfigUtil.getURI(Naming.Messaging.MESSAGING_URL);
			client = Messaging.connectToServer(messagingUrl.resolve(Naming.PERSISTENCE));
			client.onMessage(msg -> {});
			messages = new MessageQueue(client.getSession());
			executor.submit(messages);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[Persistence.postConstruct]", ex);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		try {
			messages.close();
			client.close();
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[Persistence.preDestroy]", e);
		}
	}
	
	/**
	 * @param schema
	 * @param name
	 * @param entityId
	 * @return
	 */
	public Optional<Object> getEntity(
			final String schema,
            final String name,
            final String entityId
            ) {
		return entityCache.getEntity(schema, name, entityId);
	}
	
	/**
	 * @param schema
	 * @param entity
	 * @return
	 */
	public Optional<Integer> countEntity(final String schema, final String entity) {
		return queryCache.countEntity(schema, entity);
	}
	
	/**
	 * @param schema
	 * @param paths
	 * @param firstResult
	 * @param maxResults
	 * @param context
	 * @param sort
	 */
	public Response executeQuery(
			final String schema, 
			final List<PathSegment> paths, 
			final Integer firstResult, 
			final Integer maxResults,
			final SecurityContext context,
			final List<String> sort) throws Exception {
		return cache.executeQuery(schema, paths, firstResult, maxResults, context, sort);
	}
	
	/**
	 * @param schema
	 * @param paths
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public Response executeCountQuery(
			final String schema, 
			final List<PathSegment> paths, 
			final SecurityContext context)
			throws Exception {
		return cache.executeCountQuery(schema, paths, context);
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-persistence");
	}
	
	/**
	 * @param event
	 */
	@Incoming(Naming.Persistence.PERSISTENCE_ENTITY_LISTENERS)
	public void postEvent(final EntityEvent event) {
		if(event != null) {
			try {
				//queryCache.accept(event);
				cache.accept(event);
				entityCache.accept(event);
				messages.add(new Message(event));
			}
			catch(Exception ex) {
				LOGGER.log(Level.SEVERE, "[Persistence.postEvent]", ex);
			}
		}
	}
}
