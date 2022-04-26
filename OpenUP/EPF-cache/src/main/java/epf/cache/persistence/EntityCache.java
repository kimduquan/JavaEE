package epf.cache.persistence;

import java.util.Optional;
import javax.cache.Cache;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;

/**
 * @author PC
 *
 */
public class EntityCache {
	
	/**
	 * 
	 */
	private transient final SchemaCache schemaCache;
	
	/**
	 * 
	 */
	private transient final Cache<String, Object> cache;
	
	/**
	 * @param cache
	 * @param schemaCache
	 */
	public EntityCache(final Cache<String, Object> cache, final SchemaCache schemaCache) {
		this.schemaCache = schemaCache;
		this.cache = cache;
	}

	/**
	 * @param event
	 */
	public void accept(final EntityEvent event) {
		final Optional<EntityKey> key = schemaCache.getKey(event.getEntity());
		if(key.isPresent()) {
			if(event instanceof PostUpdate) {
				cache.replace(key.get().toString(), event.getEntity());
			}
			else if(event instanceof PostPersist) {
				cache.put(key.get().toString(), event.getEntity());
			}
			else if(event instanceof PostRemove) {
				cache.remove(key.get().toString());
			}
		}
	}
	
	/**
	 * @param name
	 * @param entityId
	 * @return
	 */
	public Optional<Object> getEntity(
			final String schema,
            final String name,
            final String entityId
            ) {
		final EntityKey key = schemaCache.getKey(schema, name, entityId);
		Optional<Object> entity = Optional.empty();
		if(cache.containsKey(key.toString())) {
			entity = Optional.ofNullable(cache.get(key.toString()));
		}
		return entity;
	}
}
