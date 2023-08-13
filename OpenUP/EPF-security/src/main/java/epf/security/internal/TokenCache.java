package epf.security.internal;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.cache.CacheManager;
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
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.cache.util.CacheProvider;
import epf.naming.Naming;
import epf.util.logging.LogManager;

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
	private transient static final Logger LOGGER = LogManager.getLogger(TokenCache.class.getName());
	
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
		try {
			final CacheProvider provider = new CacheProvider();
			final CacheManager manager = provider.getManager(CreatedExpiryPolicy.class.getClassLoader());
			final MutableConfiguration<String, String> config = new MutableConfiguration<>();
			final Duration expire = Duration.parse(expireDuration);
			final javax.cache.expiry.Duration expiryDuration = new javax.cache.expiry.Duration(TimeUnit.MINUTES, expire.toMinutes());
			final Factory<ExpiryPolicy> factory = CreatedExpiryPolicy.factoryOf(expiryDuration);
			config.setExpiryPolicyFactory(factory);
			tokenCache = manager.getCache(Naming.Security.Internal.SECURITY_CACHE);
			if(tokenCache == null) {
				tokenCache = manager.createCache(Naming.Security.Internal.SECURITY_CACHE, config);
			}
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[TokenCache.tokenCache]", ex);
		}
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
		if(tokenCache == null || tokenCache.isClosed()) {
			return HealthCheckResponse.down("epf-security-cache");
		}
		return HealthCheckResponse.up("epf-security-cache");
	}

	/**
	 * @param jwt
	 */
	public void expireToken(final JsonWebToken jwt) {
		tokenCache.put(jwt.getTokenID(), jwt.getRawToken());
	}
}
