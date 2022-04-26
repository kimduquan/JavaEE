package epf.cache.security;

import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.naming.Naming;
import epf.security.schema.Token;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class Security implements HealthCheck {
	
	/**
	 * 
	 */
	private transient TokenCache tokenCache;



	/**
	 * 
	 */
	private transient CacheManager manager;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		manager = Caching.getCachingProvider().getCacheManager();
		final MutableConfiguration<String, Object> config = new MutableConfiguration<>();
		final Cache<String, Object> cache = manager.createCache(Naming.SECURITY, config);
		tokenCache = new TokenCache(cache);
	}
	
	/**
	 * @param tokenId
	 * @return
	 */
	public Token getToken(final String tokenId) {
		return tokenCache.get(tokenId);
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-security-cache");
	}
	
	/**
	 * @param token
	 */
	@Incoming(Naming.SECURITY)
	public void newToken(final Token token) {
		if(token != null) {
			tokenCache.accept(token);
		}
	}
}
