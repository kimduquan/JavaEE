package epf.query.internal.persistence;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import epf.query.internal.EntityKey;
import epf.query.internal.SchemaCache;
import epf.query.internal.event.EntityLoad;
import epf.util.json.JsonUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class EntityLoader implements CacheLoader<String, Object> {
	
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

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object load(final String key) throws Exception {
		final Optional<EntityKey> entityKey = EntityKey.parseString(key);
		if(entityKey.isPresent()) {
			final Optional<String> entityClassName = schemaCache.getEntityClassName(entityKey.get().getEntity());
			if(entityClassName.isPresent()) {
				final Class<?> entityClass = Class.forName(entityClassName.get());
				final Object entity = manager.find(entityClass, entityKey.get().getId());
				if(entity != null) {
					JsonUtil.toString(entity);
					manager.detach(entity);
				}
				return entity;
			}
		}
		return null;
	}
	
	/**
	 * @param event
	 * @throws Exception 
	 */
	public void loadAll(@ObservesAsync final EntityLoad event) throws Exception {
		event.setEntries(loadAll(event.getKeys()));
	}
}
