package epf.query.internal.persistence;

import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import epf.naming.Naming;
import epf.query.internal.EntityKey;
import epf.query.internal.SchemaCache;
import epf.query.internal.event.EntityLoad;
import epf.util.json.JsonUtil;

/**
 * @author PC
 *
 */
@Dependent
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
			String tenant = entityKey.get().getTenant();
			if(tenant == null) {
				tenant = entityKey.get().getSchema();
			}
			manager.setProperty(Naming.Management.MANAGEMENT_TENANT, tenant);
			final Optional<Class<?>> entityClass = schemaCache.getEntityClass(entityKey.get().getEntity());
			if(entityClass.isPresent()) {
				final Optional<String> entityIdFieldType = schemaCache.getEntityIdFieldType(entityKey.get().getEntity());
				if(entityIdFieldType.isPresent()) {
					Object entityId = entityKey.get().getId();
					switch(entityIdFieldType.get()) {
			    		case "java.lang.Integer":
			    			entityId = Integer.valueOf(String.valueOf(entityId));
			    			break;
			    		case "java.lang.Long":
			    			entityId = Long.valueOf(String.valueOf(entityId));
			    			break;
			    		default:
			    			break;
			    	}
					final Object entity = manager.find(entityClass.get(), entityId);
					if(entity != null) {
						JsonUtil.toString(entity);
						manager.detach(entity);
					}
					return entity;
				}
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
