package epf.security.internal;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.Factory;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.ExpiryPolicy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class TokenCache implements HealthCheck {
	
	/**
	 * 
	 */
	private transient Cache<String, String> tokenCache;
	
	/**
     * 
     */
    @Inject
    @ConfigProperty(name = Naming.Security.JWT.EXPIRE_DURATION)
    transient String expireDuration;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		final CacheManager manager = Caching.getCachingProvider().getCacheManager();
		final MutableConfiguration<String, String> config = new MutableConfiguration<>();
		final Duration expire = Duration.parse(expireDuration);
		final javax.cache.expiry.Duration expiryDuration = new javax.cache.expiry.Duration(TimeUnit.MINUTES, expire.toMinutes());
		final Factory<ExpiryPolicy> factory = CreatedExpiryPolicy.factoryOf(expiryDuration);
		config.setExpiryPolicyFactory(factory);
		tokenCache = manager.createCache(epf.security.Naming.TOKEN_CACHE, config);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		tokenCache.close();
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("epf-security-token-cache");
	}

	/**
	 * @param token
	 */
	public void expireToken(final String tokenId) {
		tokenCache.put(tokenId, tokenId);
	}
	
	/**
	 * @param tokenId
	 * @return
	 */
	public boolean isExpired(final String tokenId) {
		return tokenCache.containsKey(tokenId);
	}
}
