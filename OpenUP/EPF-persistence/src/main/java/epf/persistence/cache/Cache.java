package epf.persistence.cache;

import epf.naming.Naming;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Cache {

	@CacheResult(cacheName = Naming.Persistence.Internal.TRANSACTION_CACHE)
	public String put(@CacheKey final String key, final String value) {
		return value;
	}
	
	@CacheResult(cacheName = Naming.Persistence.Internal.TRANSACTION_CACHE)
	public String get(@CacheKey final String key) {
		return null;
	}
	
	@CacheInvalidate(cacheName = Naming.Persistence.Internal.TRANSACTION_CACHE)
	public void remove(@CacheKey final String key) {
	}
}
