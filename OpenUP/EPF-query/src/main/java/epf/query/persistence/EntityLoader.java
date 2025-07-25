package epf.query.persistence;

import java.util.Optional;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.BadRequestException;
import org.eclipse.microprofile.health.Readiness;
import epf.persistence.util.EntityUtil;
import epf.query.cache.EntityLoad;
import epf.query.internal.EntityKey;
import epf.query.internal.SchemaCache;
import epf.util.json.ext.JsonUtil;

@Dependent
public class EntityLoader implements Loader<String, Object> {
	
	@PersistenceContext(unitName = epf.query.Naming.QUERY_UNIT_NAME)
	transient EntityManager manager;

	@Inject @Readiness
	transient SchemaCache schemaCache;

	@Override
	public Object load(final String key) throws Exception {
		final Optional<EntityKey> entityKey = EntityKey.parseString(key);
		if(entityKey.isPresent()) {
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
	
	@ActivateRequestContext
	public void loadAll(@Observes final EntityLoad event) throws Exception {
		event.setEntries(loadAll(event.getKeys()));
	}
}
