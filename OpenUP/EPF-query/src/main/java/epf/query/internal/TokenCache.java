package epf.query.internal;

import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.security.schema.Token;

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
	private transient Cache<String, Token> cache;

	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		final CacheManager manager = Caching.getCachingProvider().getCacheManager();
		final MutableConfiguration<String, Token> config = new MutableConfiguration<>();
		cache = manager.createCache(Naming.SECURITY, config);
	}

	/**
	 * @param token
	 * @throws Exception 
	 */
	public void accept(final Token token) throws Exception {
		final TokenKey tokenKey = TokenKey.valueOf(token);
		cache.put(tokenKey.toString(), token);
	}
	
	/**
	 * @param tokenKey
	 * @return
	 */
	public Optional<Token> getToken(final String tokenHash) {
		final TokenKey tokenKey = TokenKey.parseString(tokenHash);
		return Optional.ofNullable(cache.get(tokenKey.toString()));
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-security-cache");
	}
}
