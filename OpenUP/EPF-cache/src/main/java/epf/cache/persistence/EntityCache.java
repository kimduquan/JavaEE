/**
 * 
 */
package epf.cache.persistence;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.cache.Cache;
import epf.schema.EntityEvent;
import epf.schema.PostLoad;
import epf.schema.PostPersist;
import epf.schema.PostRemove;
import epf.schema.PostUpdate;
import epf.schema.SchemaUtil;
import epf.util.StringUtil;
import epf.util.concurrent.ObjectQueue;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class EntityCache extends ObjectQueue<EntityEvent> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(EntityCache.class.getName());
	
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
	protected String getKey(final Object entity) {
		final Class<?> cls = entity.getClass();
		final String entityName = schemaUtil.getEntityName(cls);
		final Field idField = schemaUtil.getEntityIdField(cls);
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
			key = StringUtil.join(CACHE_KEY, entityName, String.valueOf(entityId));
		}
		return key;
	}

	@Override
	public void accept(final EntityEvent event) {
		if(event instanceof PostLoad) {
			final PostLoad postLoad = (PostLoad) event;
			final String key = getKey(postLoad.getEntity());
			if(key != null) {
				final String entityName = schemaUtil.getEntityName(postLoad.getEntity().getClass());
				putKey(entityName, key);
				cache.putIfAbsent(key, postLoad.getEntity());
			}
		}
		else if(event instanceof PostUpdate) {
			final PostUpdate postUpdate = (PostUpdate) event;
			final String key = getKey(postUpdate.getEntity());
			if(key != null) {
				cache.replace(key, postUpdate.getEntity());
			}
		}
		else if(event instanceof PostPersist) {
			final PostPersist postPersist = (PostPersist) event;
			final String key = getKey(postPersist.getEntity());
			if(key != null) {
				final String entityName = schemaUtil.getEntityName(postPersist.getEntity().getClass());
				putKey(entityName, key);
				cache.put(key, postPersist.getEntity());
			}
		}
		else if(event instanceof PostRemove) {
			final PostRemove postRemove = (PostRemove) event;
			final String key = getKey(postRemove.getEntity());
			if(key != null) {
				final String entityName = schemaUtil.getEntityName(postRemove.getEntity().getClass());
				removeKey(entityName, key);
				cache.remove(key);
			}
		}
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
		final String key = StringUtil.join(CACHE_KEY, name, entityId);
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
