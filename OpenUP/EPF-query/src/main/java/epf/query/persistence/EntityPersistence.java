package epf.query.persistence;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.query.internal.SchemaCache;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.util.logging.LogManager;

@ApplicationScoped
@Readiness
public class EntityPersistence implements HealthCheck {
	
	private transient static final Logger LOGGER = LogManager.getLogger(EntityPersistence.class.getName());
	
	@PersistenceContext(unitName = epf.query.Naming.QUERY_UNIT_NAME)
	transient EntityManager entityManager;
	
	@Inject @Readiness
	transient SchemaCache schemaCache;
	
	
	@Transactional
	@ActivateRequestContext
	public void accept(final EntityEvent event) {
		try {
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
