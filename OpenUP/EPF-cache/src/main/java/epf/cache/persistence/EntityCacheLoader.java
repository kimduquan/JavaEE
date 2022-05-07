package epf.cache.persistence;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import javax.persistence.EntityManager;
import epf.schema.utility.SchemaUtil;

/**
 * @author PC
 *
 */
public class EntityCacheLoader implements CacheLoader<String, Object> {
	
	/**
	 * 
	 */
	private transient final EntityManager manager;
	
	/**
	 * 
	 */
	private transient final SchemaUtil schemaUtil;
	
	/**
	 * @param manager
	 * @param schemaUtil
	 */
	public EntityCacheLoader(final EntityManager manager, final SchemaUtil schemaUtil) {
		this.manager = manager;
		this.schemaUtil = schemaUtil;
	}

	@Override
	public Object load(final String key) throws CacheLoaderException {
		Object value = null;
		final Optional<EntityKey> entityKey = EntityKey.parseString(key);
		if(entityKey.isPresent()) {
			final Optional<Class<?>> entityClass = schemaUtil.getEntityClass(entityKey.get().getEntity());
			if(entityClass.isPresent()) {
				value = manager.find(entityClass.get(), entityKey.get().getId());
			}
		}
		return value;
	}

	@Override
	public Map<String, Object> loadAll(final Iterable<? extends String> keys) throws CacheLoaderException {
		final Map<String, Object> values = new ConcurrentHashMap<>();
		final Iterator<? extends String> it = keys.iterator();
		while(it.hasNext()) {
			final String key = it.next();
			final Object value = load(key);
			if(value != null) {
				values.put(key, value);
			}
		}
		return values;
	}
}
