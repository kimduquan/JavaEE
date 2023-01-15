package epf.query.persistence;

import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.persistence.util.EntityUtil;
import epf.query.cache.EntityLoad;
import epf.query.internal.EntityKey;
import epf.query.internal.SchemaCache;
import epf.schema.utility.TenantUtil;
import epf.util.json.JsonUtil;

/**
 * @author PC
 *
 */
@Dependent
public class EntityLoader implements Loader<String, Object> {
	
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
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object load(final String key) throws Exception {
		final Optional<EntityKey> entityKey = EntityKey.parseString(key);
		if(entityKey.isPresent()) {
			final String tenant = TenantUtil.getTenantId(entityKey.get().getSchema(), entityKey.get().getTenant());
			final Optional<Class<?>> entityClass = schemaCache.getEntityClass(entityKey.get().getEntity());
			if(entityClass.isPresent()) {
				final Optional<String> entityIdFieldType = schemaCache.getEntityIdFieldType(entityKey.get().getEntity());
				if(entityIdFieldType.isPresent()) {
					Object entityId = null;
					try {
						entityId = EntityUtil.getEntityId(entityIdFieldType.get(), entityKey.get().getId());
			    	}
			    	catch(NumberFormatException ex) {
			    		throw new BadRequestException(ex);
			    	}
					if(entityId != null) {
						final EntityManager manager = this.manager.getEntityManagerFactory().createEntityManager();
						try {
							manager.setProperty(Naming.Management.MANAGEMENT_TENANT, tenant);
							final Object entity = manager.find(entityClass.get(), entityId);
							if(entity != null) {
								JsonUtil.toString(entity);
								manager.detach(entity);
								return entity;
							}
						}
						finally {
							manager.close();
						}
					}
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
