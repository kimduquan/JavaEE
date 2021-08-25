/**
 * 
 */
package epf.cache.persistence;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Id;
import javax.websocket.DeploymentException;
import org.eclipse.microprofile.health.Readiness;
import epf.cache.Manager;
import epf.client.messaging.Client;
import epf.client.messaging.Messaging;
import epf.schema.PostLoad;
import epf.schema.PostPersist;
import epf.schema.PostRemove;
import epf.schema.PostUpdate;
import epf.util.SystemUtil;
import epf.util.concurrent.ObjectQueue;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Persistence {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Persistence.class.getName());
	
	/**
	 * 
	 */
	private static final String CACHE_KEY_FORMAT = "epf.cache.entry.key\0%s\0%s";
	
	/**
	 * 
	 */
	private static final String CACHE_KEYS_FORMAT = "epf.cache.entry.keys\0%s";
	
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
	@Inject @Readiness
	private transient Manager manager;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			cache = manager.getCache("persistence");
			final URI messagingUrl = new URI(SystemUtil.getenv(Messaging.MESSAGING_URL));
			client = Messaging.connectToServer(messagingUrl.resolve("persistence"));
			client.onMessage(this::onEntityEvent);
		} 
		catch (URISyntaxException | DeploymentException | IOException e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
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
			LOGGER.throwing(client.getClass().getName(), "close", e);
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
				putKey(postLoad.getEntity().getClass(), key);
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
				putKey(postPersist.getEntity().getClass(), key);
				cache.put(key, postPersist.getEntity());
			}
		}
		else if(message instanceof PostRemove) {
			final PostRemove postRemove = (PostRemove) message;
			final String key = getKey(postRemove.getEntity());
			if(key != null) {
				removeKey(postRemove.getEntity().getClass(), key);
				cache.remove(key);
			}
		}
	}

	/**
	 * @param cls
	 * @param key
	 */
	protected void putKey(final Class<?> cls, final String key) {
		final String entityName = getEntityName(cls);
		final String keys = String.format(CACHE_KEYS_FORMAT, entityName);
		cache.putIfAbsent(keys, new HashSet<>());
		@SuppressWarnings("unchecked")
		final Set<String> cacheKeys = (Set<String>) cache.get(keys);
		cacheKeys.add(key);
		cache.replace(keys, cacheKeys);
	}
	
	/**
	 * @param cls
	 * @param key
	 */
	protected void removeKey(final Class<?> cls, final String key) {
		final String entityName = getEntityName(cls);
		final String keys = String.format(CACHE_KEYS_FORMAT, entityName);
		cache.putIfAbsent(keys, new HashSet<>());
		@SuppressWarnings("unchecked")
		final Set<String> cacheKeys = (Set<String>) cache.get(keys);
		cacheKeys.remove(key);
		cache.replace(keys, cacheKeys);
	}
	
	/**
	 * @param entity
	 * @return
	 */
	protected String getKey(final Object entity) {
		final Class<?> cls = entity.getClass();
		final String entityName = getEntityName(cls);
		final Field idField = getEntityIdField(cls);
		Object entityId = null;
		if(entityName != null && idField != null) {
			try {
				entityId = idField.get(entity);
			} 
			catch (IllegalAccessException | IllegalArgumentException e) {
				LOGGER.throwing(cls.getName(), idField.getName(), e);
			}
		}
		String key = null;
		if(entityName != null && entityId != null) {
			key = String.format(CACHE_KEY_FORMAT, entityName, String.valueOf(entityId));
		}
		return key;
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
			final Optional<Field> idField = Stream.of(cls.getDeclaredFields())
					.filter(field -> field.isAnnotationPresent(Id.class))
					.findAny();
			idField.ifPresent(f -> f.setAccessible(true));
			return idField.orElse(null);
		});
	}
	
	/**
	 * @param name
	 * @param entityId
	 * @return
	 */
	public Object getEntity(
            final String name,
            final String entityId
            ) {
		final String key = String.format(CACHE_KEY_FORMAT, name, entityId);
		Object entity = null;
		if(cache.containsKey(key)) {
			entity = cache.get(key);
		}
		return entity;
	}
	
	/**
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Entry<String, Object>> getEntities(final String name){
		final String keys = String.format(CACHE_KEYS_FORMAT, name);
		if(cache.containsKey(keys)) {
			Set<String> cacheKeys;
			Map<String, Object> entries;
			do {
				cacheKeys = (Set<String>) cache.get(keys);
				entries = cache.getAll(cacheKeys);
			}
			while(entries.size() < cacheKeys.size());
			return entries.entrySet().stream().collect(Collectors.toList());
		}
		else {
			final Queue<Entry<String, Object>> entries = new ConcurrentLinkedQueue<>();
			final String key = String.format(CACHE_KEY_FORMAT, name, "");
			cache.forEach(entry -> {
				if(entry.getKey().startsWith(key)) {
					entries.add(new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), entry.getValue()));
				}
			});
			entries.stream().collect(Collectors.toList());
			return Arrays.asList();
		}
	}
	
	/**
	 * @param name
	 * @return
	 */
	public void forEachEntity(final String name, final ObjectQueue<Entry<String, Object>> queue) {
		final String key = String.format(CACHE_KEY_FORMAT, name, "");
		cache.forEach(entry -> {
			if(entry.getKey().startsWith(key)) {
				queue.add(new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), entry.getValue()));
			}
		});
		queue.close();
	}
}
