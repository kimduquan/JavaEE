package epf.query.internal;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import javax.cache.Cache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.query.cache.CachingManager;
import epf.query.cache.QueryLoad;
import epf.schema.utility.EntityEvent;
import epf.util.logging.LogManager;

@ApplicationScoped
@Readiness
public class QueryCache implements HealthCheck {
	
	private transient static final Logger LOGGER = LogManager.getLogger(QueryCache.class.getName());
	
	@Inject
	transient CachingManager manager;
	
	@Inject  @Readiness
	transient SchemaCache schemaCache;
	
	@Inject
	transient Event<QueryLoad> event;
	
	private transient Cache<String, Integer> queryCache;
	
	@PostConstruct
	protected void postConstruct() {
		try {
			queryCache = manager.getQueryCache(null);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[QueryCache.queryCache]", ex);
		}
	}
	
	@PreDestroy
	protected void preDestroy() {
		queryCache.close();
	}

	public void accept(final EntityEvent event) {
		final Optional<QueryKey> queryKey = schemaCache.getQueryKey(event.getEntity().getClass());
		if(queryKey.isPresent()) {
			final String key = queryKey.get().toString();
			manager.getQueryCache(event.getTenant()).remove(key);
		}
	}
	
	public Optional<Integer> countEntity(
			final String tenant,
			final String schema,
            final String entity
            ) {
		final QueryKey queryKey = schemaCache.getQueryKey(schema, entity);
		return Optional.ofNullable(manager.getQueryCache(tenant).get(queryKey.toString()));
	}

	@Override
	public HealthCheckResponse call() {
		if(queryCache == null || queryCache.isClosed()) {
			return HealthCheckResponse.down("EPF-query-query-cache");
		}
		return HealthCheckResponse.up("EPF-query-query-cache");
	}
}
