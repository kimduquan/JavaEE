package epf.query.persistence;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.query.internal.SchemaCache;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.schema.utility.Request;
import epf.schema.utility.TenantUtil;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class EntityPersistence implements HealthCheck {
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(EntityPersistence.class.getName());
	
	/**
	 *
	 */
	@PersistenceContext(unitName = epf.query.Naming.QUERY_UNIT_NAME)
	transient EntityManager entityManager;
	
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
	
	
	/**
	 * @param event
	 */
	@Transactional
	@ActivateRequestContext
	public void accept(final EntityEvent event) {
		try {
			request.setSchema(event.getSchema());
			request.setTenant(event.getTenant());
			final String tenant = TenantUtil.getTenantId(event.getSchema(), event.getTenant());
			entityManager.setProperty(Naming.Management.MANAGEMENT_TENANT, tenant);
			if(event instanceof PostUpdate) {
				entityManager.merge(event.getEntity());
				entityManager.flush();
			}
			else if(event instanceof PostPersist) {
				entityManager.persist(event.getEntity());
				entityManager.flush();
			}
			else if(event instanceof PostRemove) {
				final Optional<Object> entityId = schemaCache.getEntityId(event.getEntity());
				if(entityId.isPresent()) {
					entityManager.remove(entityManager.getReference(event.getEntity().getClass(), entityId.get()));
					entityManager.flush();
				}
			}
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[PersistenceCache.accept]", ex);
		}
	}

	@Override
	public HealthCheckResponse call() {
		if(!entityManager.isOpen()) {
			return HealthCheckResponse.down("EPF-query-entity-persistence");
		}
		return HealthCheckResponse.up("EPF-query-entity-persistence");
	}
}
