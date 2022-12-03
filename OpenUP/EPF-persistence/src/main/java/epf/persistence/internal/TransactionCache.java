package epf.persistence.internal;

import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class TransactionCache implements HealthCheck {
	
	/**
	 * 
	 */
	private transient Cache<String, Object> cache;
	
	@PostConstruct
	protected void postConstruct() {
		final String cacheName = "transaction-cache";
		final MutableConfiguration<String, Object> config = new MutableConfiguration<>();
		final CacheManager manager = Caching.getCachingProvider().getCacheManager();
		cache = manager.getCache(cacheName);
		if(cache == null) {
			cache = manager.createCache(cacheName, config);
		}
	}

	@Override
	public HealthCheckResponse call() {
		if(cache == null || cache.isClosed()) {
			return HealthCheckResponse.down("epf-persistence-transaction-cache");
		}
		return HealthCheckResponse.up("epf-persistence-transaction-cache");
	}

	/**
	 * @param transaction
	 */
	public void put(final EntityTransaction transaction) {
		Objects.requireNonNull(transaction, "EntityTransaction");
		cache.put(transaction.getId(), transaction);
	}
	
	/**
	 * @param id
	 * @return
	 */
	public EntityTransaction remove(final String id) {
		Objects.requireNonNull(id, "String");
		return (EntityTransaction) cache.get(id);
	}
}
