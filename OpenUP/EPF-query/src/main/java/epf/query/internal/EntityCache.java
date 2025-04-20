package epf.query.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import javax.cache.Cache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.query.cache.CachingManager;
import epf.query.client.EntityId;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.Request;
import epf.util.logging.LogManager;

@ApplicationScoped
@Readiness
public class EntityCache implements HealthCheck {
	
	private transient static final Logger LOGGER = LogManager.getLogger(EntityCache.class.getName());
	
	@Inject
	transient CachingManager manager;
	
	@Inject @Readiness
	transient SchemaCache schemaCache;
	
	@Inject
	Request request;
	
	transient Cache<String, Object> entityCache;
	
	@PostConstruct
	protected void postConstruct() {
		try {
			entityCache = manager.getEntityCache(null);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[EntityCache.entityCache]", ex);
		}
	}
	
	@PreDestroy
	protected void preDestroy() {
		entityCache.close();
	}

	public void accept(final EntityEvent event) {
		final Optional<EntityKey> key = schemaCache.getKey(event.getEntity());
		if(key.isPresent()) {
			manager.getEntityCache(event.getTenant()).remove(key.get().toString());
		}
	}
	
	public Optional<Object> getEntity(
            final String entity,
            final String entityId
            ) {
		final EntityKey key = schemaCache.getKey(request.getSchema(), entity, entityId);
		return Optional.ofNullable(manager.getEntityCache(request.getTenant()).get(key.toString()));
	}
	
	public List<Object> getEntities(final List<EntityId> entityIds){
		final List<EntityKey> entityKeys = entityIds.stream().map(key -> schemaCache.getSearchKey(key)).collect(Collectors.toList());
		final List<String> keys = entityKeys.stream().map(EntityKey::toString).collect(Collectors.toList());
		final Map<String, Object> values = manager.getEntityCache(request.getTenant()).getAll(keys.stream().collect(Collectors.toSet()));
		final List<Object> result = new ArrayList<>();
		keys.forEach(key -> {
			result.add(values.get(key));
		});
		return result;
	}

	@Override
	public HealthCheckResponse call() {
		if(entityCache == null || entityCache.isClosed()) {
			return HealthCheckResponse.down("EPF-query-entity-cache");
		}
		return HealthCheckResponse.up("EPF-query-entity-cache");
	}
}
