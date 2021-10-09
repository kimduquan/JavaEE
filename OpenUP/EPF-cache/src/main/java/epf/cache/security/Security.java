/**
 * 
 */
package epf.cache.security;

import java.net.URI;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.cache.Manager;
import epf.client.security.Token;
import epf.messaging.client.Client;
import epf.messaging.client.Messaging;
import epf.util.config.ConfigUtil;
import epf.util.logging.Logging;

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
	private static final Logger LOGGER = Logging.getLogger(Security.class.getName());
	
	/**
	 * 
	 */
	private transient TokenCache tokenCache;
	
	/**
	 * 
	 */
	private transient Client client;

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
		try {
			tokenCache = new TokenCache(manager.getCache("security"));
			executor.submit(tokenCache);
			final URI messagingUrl = ConfigUtil.getURI(Messaging.MESSAGING_URL);
			client = Messaging.connectToServer(messagingUrl.resolve("security"));
			client.onMessage(message -> {
				if(message instanceof Token) {
					tokenCache.add((Token) message);
				}
			});
		}
		catch(Exception ex) {
			LOGGER.throwing(LOGGER.getName(), "postConstruct", ex);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		try {
			client.close();
		} 
		catch (Exception e) {
			LOGGER.throwing(LOGGER.getName(), "preDestroy", e);
		}
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
}
