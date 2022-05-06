package epf.cache.persistence;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author PC
 *
 */
public class QueryCacheLoader implements CacheLoader<String, Integer> {
	
	/**
	 * 
	 */
	private transient final EntityManager manager;
	
	/**
	 * 
	 */
	private transient final SchemaCache schemaCache;
	
	/**
	 * @param manager
	 * @param schemaCache
	 */
	public QueryCacheLoader(final EntityManager manager, final SchemaCache schemaCache) {
		this.manager = manager;
		this.schemaCache = schemaCache;
	}

	@Override
	public Integer load(final String key) throws CacheLoaderException {
		final Optional<QueryKey> queryKey = schemaCache.parseQueryKey(key);
		if(queryKey.isPresent()) {
			final Optional<Class<?>> entityClass = schemaCache.getEntityClass(queryKey.get().getSchema(), queryKey.get().getEntity());
			if(entityClass.isPresent()) {
				final CriteriaBuilder builder = manager.getCriteriaBuilder();
				final CriteriaQuery<Long> query = builder.createQuery(Long.class);
				final Root<?> from = query.from(entityClass.get());
				query.select(builder.count(from));
				return manager.createQuery(query).getSingleResult().intValue();
			}
		}
		return null;
	}

	@Override
	public Map<String, Integer> loadAll(final Iterable<? extends String> keys) throws CacheLoaderException {
		final Map<String, Integer> values = new ConcurrentHashMap<>();
		final Iterator<? extends String> it = keys.iterator();
		while(it.hasNext()) {
			final String key = it.next();
			final Integer value = load(key);
			if(value != null) {
				values.put(key, value);
			}
		}
		return values;
	}
}
