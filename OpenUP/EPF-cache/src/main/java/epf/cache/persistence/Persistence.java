/**
 * 
 */
package epf.cache.persistence;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Id;
import javax.websocket.DeploymentException;
import epf.client.messaging.Client;
import epf.client.messaging.Messaging;
import epf.schema.PostLoad;
import epf.schema.PostPersist;
import epf.schema.PostRemove;
import epf.schema.PostUpdate;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Persistence {
	
	/**
	 * 
	 */
	private static final String CACHE_KEY_FORMAT = "%s/%s/%s";
	
	/**
	 * 
	 */
	private transient Cache<String, Object> cache;
	
	/**
	 * 
	 */
	private transient Client client;
	
	/**
	 * 
	 */
	private transient final Map<String, Field> entityIdFields = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, String> entityNames = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, String> entitySchemas = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject
	private transient Logger logger;

	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI messagingUrl = new URI(System.getenv("epf.messaging.url"));
			client = Messaging.connectToServer(messagingUrl.resolve("persistence"));
			client.onMessage(this::onEntityEvent);
		} 
		catch (URISyntaxException | DeploymentException | IOException e) {
			logger.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		try {
			client.close();
			client.onMessage(mag -> {});
		} 
		catch (Exception e) {
			logger.throwing(client.getClass().getName(), "close", e);
		}
	}
	
	/**
	 * @param message
	 */
	protected void onEntityEvent(final Object message) {
		if(message instanceof PostLoad) {
			final PostLoad postLoad = (PostLoad) message;
			final String key = getKey(postLoad.getEntity());
			if(key != null) {
				cache.putIfAbsent(key, postLoad.getEntity());
			}
		}
		else if(message instanceof PostUpdate) {
			final PostUpdate postUpdate = (PostUpdate) message;
			final String key = getKey(postUpdate.getEntity());
			if(key != null) {
				cache.replace(key, postUpdate.getEntity());
			}
		}
		else if(message instanceof PostPersist) {
			final PostPersist postPersist = (PostPersist) message;
			final String key = getKey(postPersist.getEntity());
			if(key != null) {
				cache.put(key, postPersist.getEntity());
			}
		}
		else if(message instanceof PostRemove) {
			final PostRemove postRemove = (PostRemove) message;
			final String key = getKey(postRemove.getEntity());
			if(key != null) {
				cache.remove(key);
			}
		}
	}
	
	/**
	 * @param entity
	 * @return
	 */
	protected String getKey(final Object entity) {
		final Class<?> cls = entity.getClass();
		final String schema = getEntitySchema(cls);
		final String entityName = getEntityName(cls);
		final Field idField = getEntityIdField(cls);
		Object entityId = null;
		if(idField != null) {
			try {
				idField.setAccessible(true);
				entityId = idField.get(entity);
			} 
			catch (IllegalAccessException | IllegalArgumentException e) {
				logger.throwing(cls.getName(), idField.getName(), e);
			}
		}
		String key = null;
		if(schema != null && entityName != null && entityId != null) {
			key = String.format(CACHE_KEY_FORMAT, schema, entityName, entityId);
		}
		return key;
	}
	
	/**
	 * @param cls
	 * @return
	 */
	protected String getEntitySchema(final Class<?> cls) {
		return entitySchemas.computeIfAbsent(cls.getName(), key -> {
			String name = null;
			if(cls.isAnnotationPresent(javax.persistence.Table.class)) {
				name = ((javax.persistence.Table)cls.getAnnotation(javax.persistence.Table.class)).schema();
			}
			return name;
		});
	}
	
	/**
	 * @param cls
	 * @return
	 */
	protected String getEntityName(final Class<?> cls) {
		return entityNames.computeIfAbsent(cls.getName(), key -> {
			String name = null;
			if(cls.isAnnotationPresent(javax.persistence.Entity.class)) {
				name = ((javax.persistence.Entity)cls.getAnnotation(javax.persistence.Entity.class)).name();
			}
			return name;
		});
	}
	
	/**
	 * @param cls
	 * @return
	 */
	protected Field getEntityIdField(final Class<?> cls) {
		return entityIdFields.computeIfAbsent(cls.getName(), name -> {
			return Stream.of(cls.getDeclaredFields())
					.filter(field -> field.isAnnotationPresent(Id.class))
					.findAny()
					.orElse(null);
		});
	}
	
	/**
	 * @param schema
	 * @param name
	 * @param entityId
	 * @return
	 */
	public Object getEntity(
            final String schema,
            final String name,
            final String entityId
            ) {
		final String key = String.format(CACHE_KEY_FORMAT, schema, name, entityId);
		Object entity = null;
		if(cache.containsKey(key)) {
			entity = cache.get(key);
		}
		return entity;
	}
	
	public void setCache(final Cache<String, Object> cache) {
		this.cache = cache;
	}
}
