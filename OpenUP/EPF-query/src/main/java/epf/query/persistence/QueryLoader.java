package epf.query.persistence;

import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.eclipse.microprofile.health.Readiness;
import epf.query.cache.QueryLoad;
import epf.query.internal.QueryKey;
import epf.query.internal.SchemaCache;
import epf.schema.utility.Request;

/**
 * @author PC
 *
 */
@Dependent
public class QueryLoader implements Loader<String, Integer> {
	
	/**
	 *
	 */
	@PersistenceContext(unitName = epf.query.Naming.QUERY_UNIT_NAME)
	transient EntityManager manager;

	/**
	 *
	 */
	@Inject @Readiness
	transient SchemaCache schemaCache;
	
	/**
	 * 
	 */
	@Inject
	Request request;

	@Override
	public Integer load(final String key) throws Exception {
		final Optional<QueryKey> queryKey = QueryKey.parseString(key);
		if(queryKey.isPresent()) {
			final Optional<Class<?>> entityClass = schemaCache.getEntityClass(queryKey.get().getEntity());
			if(entityClass.isPresent()) {
				request.setSchema(queryKey.get().getSchema());
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
	
	/**
	 * @param event
	 * @throws Exception
	 */
	@ActivateRequestContext
	public void loadAll(@Observes final QueryLoad event) throws Exception {
		request.setTenant(event.getTenant());
		event.setEntries(loadAll(event.getKeys()));
	}
}
