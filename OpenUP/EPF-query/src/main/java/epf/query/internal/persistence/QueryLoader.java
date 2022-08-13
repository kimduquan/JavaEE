package epf.query.internal.persistence;

import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import epf.naming.Naming;
import epf.query.internal.QueryKey;
import epf.query.internal.SchemaCache;
import epf.query.internal.event.QueryLoad;

/**
 * @author PC
 *
 */
@Dependent
public class QueryLoader implements CacheLoader<String, Integer> {
	
	/**
	 *
	 */
	@PersistenceContext(unitName = epf.query.Naming.QUERY_UNIT_NAME)
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
			final Optional<Class<?>> entityClass = schemaCache.getEntityClass(queryKey.get().getEntity());
			if(entityClass.isPresent()) {
				final CriteriaBuilder builder = manager.getCriteriaBuilder();
				final CriteriaQuery<Long> query = builder.createQuery(Long.class);
				final Root<?> from = query.from(entityClass.get());
				query.select(builder.count(from));
				final String tenant = queryKey.get().getTenant();
				if(tenant != null) {
					manager.setProperty(Naming.Management.MANAGEMENT_TENANT, tenant);
				}
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
