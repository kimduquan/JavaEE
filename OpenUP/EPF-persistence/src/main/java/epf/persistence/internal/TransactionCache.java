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
import epf.util.json.Decoder;
import epf.util.json.Encoder;

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
	
	/**
	 * 
	 */
	private transient final Encoder encoder = new Encoder();
	
	/**
	 * 
	 */
	private transient final Decoder decoder = new Decoder();
	
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
	 * @throws Exception 
	 */
	public void put(final EntityTransaction transaction) throws Exception {
		Objects.requireNonNull(transaction, "EntityTransaction");
		Objects.requireNonNull(transaction.getId(), "EntityTransaction.Id");
		cache.put(transaction.getId(), encoder.encode(transaction));
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public EntityTransaction remove(final String id) throws Exception {
		Objects.requireNonNull(id, "String");
		EntityTransaction transaction = null;
		final Object object = cache.get(id);
		if(object != null) {
			transaction = (EntityTransaction) decoder.decode(object.toString());
		}
		return transaction;
	}
}
