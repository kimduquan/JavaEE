package epf.cache.persistence.internal;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import epf.cache.persistence.QueryKey;
import epf.cache.persistence.SchemaCache;
import epf.cache.persistence.event.QueryLoad;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class QueryLoader implements CacheLoader<String, Integer> {
	
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
	public Integer load(final String key) throws Exception {
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
	
	/**
	 * @param event
	 * @throws Exception
	 */
	public void loadAll(@ObservesAsync final QueryLoad event) throws Exception {
		event.setEntries(loadAll(event.getKeys()));
	}
}
