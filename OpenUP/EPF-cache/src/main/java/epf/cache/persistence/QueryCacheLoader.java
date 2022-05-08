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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class QueryCacheLoader implements CacheLoader<String, Integer> {
	
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
	public Integer load(final String key) throws CacheLoaderException {
		final Optional<QueryKey> queryKey = QueryKey.parseString(key);
		if(queryKey.isPresent()) {
			final Optional<Class<?>> entityClass = schemaCache.getSchemaUtil().getEntityClass(queryKey.get().getEntity());
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
