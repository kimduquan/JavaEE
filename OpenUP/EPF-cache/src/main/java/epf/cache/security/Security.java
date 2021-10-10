/**
 * 
 */
package epf.cache.security;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.cache.Manager;
import epf.client.security.Token;

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
	@Inject @Readiness
	private transient Manager manager;
	
	/**
	 * 
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		tokenCache = new TokenCache(manager.getCache("security"));
		executor.submit(tokenCache);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		tokenCache.close();
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
	
	@Incoming("security")
	public void newToken(final Token token) {
		tokenCache.add(token);
	}
}
