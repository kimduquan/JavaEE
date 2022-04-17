package epf.cache.persistence;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.cache.Cache;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.schema.utility.SchemaUtil;
import epf.util.StringUtil;
import epf.util.concurrent.ObjectQueue;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class EntityCache extends ObjectQueue<EntityEvent> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(EntityCache.class.getName());
	
	/**
	 * 
	 */
	private static final String CACHE_KEY = "epf.cache.entry.key";
	
	/**
	 * 
	 */
	private static final String CACHE_KEYS = "epf.cache.entry.keys";
	
	/**
	 * 
	 */
	private transient final SchemaUtil schemaUtil = new SchemaUtil();
	
	/**
	 * 
	 */
	private transient final Cache<String, Object> cache;
	
	/**
	 * @param cache
	 */
	public EntityCache(final Cache<String, Object> cache) {
		this.cache = cache;
	}
	
	/**
	 * @param cls
	 * @param key
	 */
	protected void putKey(final String entityName, final String key) {
		final String keys = StringUtil.join(CACHE_KEYS, entityName);
		@SuppressWarnings("unchecked")
		final Set<String> cacheKeys = (Set<String>) cache.get(keys);
		if(cacheKeys == null) {
			final ConcurrentSkipListSet<String> newKeys = new ConcurrentSkipListSet<>();
			newKeys.add(key);
			cache.putIfAbsent(keys, newKeys);
		}
		else if(!cacheKeys.contains(key)) {
			cacheKeys.add(key);
			cache.replace(keys, cacheKeys);
		}
	}
	
	/**
	 * @param cls
	 * @param key
	 */
	protected void removeKey(final String entityName, final String key) {
		final String keys = StringUtil.join(CACHE_KEYS, entityName);
		@SuppressWarnings("unchecked")
		final Set<String> cacheKeys = (Set<String>) cache.get(keys);
		if(cacheKeys != null && cacheKeys.contains(keys)) {
			cacheKeys.remove(key);
			cache.replace(keys, cacheKeys);
		}
	}
	
	/**
	 * @param entity
	 * @return
	 */
	protected Optional<String> getKey(final Object entity) {
		final Class<?> cls = entity.getClass();
		final Optional<String> entityName = schemaUtil.getEntityName(cls);
		final Optional<Field> idField = schemaUtil.getEntityIdField(cls);
		Optional<Object> entityId = Optional.empty();
		if(entityName.isPresent() && idField.isPresent()) {
			try {
				entityId = Optional.ofNullable(idField.get().get(entity));
			} 
			catch (IllegalAccessException | IllegalArgumentException e) {
				LOGGER.log(Level.SEVERE, cls.getName() + "." + idField.get().getName(), e);
			}
		}
		Optional<String> key = Optional.empty();
		if(entityName.isPresent() && entityId.isPresent()) {
			key = Optional.of(StringUtil.join(CACHE_KEY, entityName.get(), String.valueOf(entityId.get())));
		}
		return key;
	}

	@Override
	public void accept(final EntityEvent event) {
		if(event instanceof PostUpdate) {
			final PostUpdate postUpdate = (PostUpdate) event;
			final Optional<String> key = getKey(postUpdate.getEntity());
			if(key.isPresent()) {
				cache.replace(key.get(), postUpdate.getEntity());
			}
		}
		else if(event instanceof PostPersist) {
			final PostPersist postPersist = (PostPersist) event;
			final Optional<String> key = getKey(postPersist.getEntity());
			if(key.isPresent()) {
				final Optional<String> entityName = schemaUtil.getEntityName(postPersist.getEntity().getClass());
				if(entityName.isPresent()) {
					putKey(entityName.get(), key.get());
					cache.put(key.get(), postPersist.getEntity());
				}
			}
		}
		else if(event instanceof PostRemove) {
			final PostRemove postRemove = (PostRemove) event;
			final Optional<String> key = getKey(postRemove.getEntity());
			if(key.isPresent()) {
				final Optional<String> entityName = schemaUtil.getEntityName(postRemove.getEntity().getClass());
				if(entityName.isPresent()) {
					removeKey(entityName.get(), key.get());
					cache.remove(key.get());
				}
			}
		}
	}
	
	/**
	 * @param name
	 * @param entityId
	 * @return
	 */
	public Optional<Object> getEntity(
            final String name,
            final String entityId
            ) {
		final String key = StringUtil.join(CACHE_KEY, name, entityId);
		Optional<Object> entity = Optional.empty();
		if(cache.containsKey(key)) {
			entity = Optional.ofNullable(cache.get(key));
		}
		return entity;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public List<Entry<String, Object>> getEntities(final String name){
		final String keys = StringUtil.join(CACHE_KEYS, name);
		@SuppressWarnings("unchecked")
		final Set<String> cacheKeys = (Set<String>) cache.get(keys);
		if(cacheKeys != null) {
			final Map<String, Object> entries = cache.getAll(cacheKeys);
			if(cacheKeys.size() == entries.size()) {
				return entries.entrySet().stream().collect(Collectors.toList());
			}
		}
		return null;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public void forEachEntity(final String name, final ObjectQueue<Entry<String, Object>> queue) {
		final String key = StringUtil.join(CACHE_KEY, name, "");
		cache.forEach(entry -> {
			if(entry.getKey().startsWith(key)) {
				queue.add(new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), entry.getValue()));
			}
		});
	}
}
