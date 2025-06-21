package epf.query.persistence;

import java.util.Optional;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.eclipse.microprofile.health.Readiness;
import epf.query.cache.QueryLoad;
import epf.query.internal.QueryKey;
import epf.query.internal.SchemaCache;

@Dependent
public class QueryLoader implements Loader<String, Integer> {
	
	@PersistenceContext(unitName = epf.query.Naming.QUERY_UNIT_NAME)
	transient EntityManager manager;

	@Inject @Readiness
	transient SchemaCache schemaCache;

	@Override
	public Integer load(final String key) throws Exception {
		final Optional<QueryKey> queryKey = QueryKey.parseString(key);
		if(queryKey.isPresent()) {
			final Optional<Class<?>> entityClass = schemaCache.getEntityClass(queryKey.get().getEntity());
			if(entityClass.isPresent()) {
				final EntityManager manager = this.manager.getEntityManagerFactory().createEntityManager();
				final CriteriaBuilder builder = manager.getCriteriaBuilder();
				final CriteriaQuery<Long> query = builder.createQuery(Long.class);
				final Root<?> from = query.from(entityClass.get());
				query.select(builder.count(from));
				final Integer value = manager.createQuery(query).getSingleResult().intValue();
				manager.close();
				return value;
			}
			manager.close();
		}
		return null;
	}
	
	@ActivateRequestContext
	public void loadAll(@Observes final QueryLoad event) throws Exception {
		event.setEntries(loadAll(event.getKeys()));
	}
}
