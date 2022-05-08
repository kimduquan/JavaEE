package epf.cache.persistence.internal;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import epf.cache.persistence.EntityKey;
import epf.cache.persistence.SchemaCache;
import epf.cache.persistence.event.EntityLoad;
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
	@PersistenceContext(unitName = "EPF_Cache")
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
			final Optional<Class<?>> entityClass = schemaCache.getSchemaUtil().getEntityClass(entityKey.get().getEntity());
			if(entityClass.isPresent()) {
				final Object entity = manager.find(entityClass.get(), entityKey.get().getId());
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
