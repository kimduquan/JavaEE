package epf.cache.persistence;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class EntityCacheLoader implements CacheLoader<String, Object> {
	
	/**
	 *
	 */
	@PersistenceContext(unitName = "EPF_Cache")
	private transient EntityManager manager;

	/**
	 *
	 */
	@Inject
	private transient SchemaCache schemaCache;

	@Override
	public Object load(final String key) throws CacheLoaderException {
		final Optional<EntityKey> entityKey = EntityKey.parseString(key);
		if(entityKey.isPresent()) {
			final Optional<Class<?>> entityClass = schemaCache.getSchemaUtil().getEntityClass(entityKey.get().getEntity());
			if(entityClass.isPresent()) {
				return manager.find(entityClass.get(), entityKey.get().getId());
			}
		}
		return null;
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
