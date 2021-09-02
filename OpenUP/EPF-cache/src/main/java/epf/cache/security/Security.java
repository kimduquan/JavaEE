/**
 * 
 */
package epf.cache.security;

import java.net.URI;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.health.Readiness;
import epf.cache.Manager;
import epf.client.messaging.Client;
import epf.client.messaging.Messaging;
import epf.client.security.Token;
import epf.util.SystemUtil;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Security {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Security.class.getName());
	
	/**
	 * 
	 */
	private transient Cache<String, Object> cache;
	
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
	@PostConstruct
	protected void postConstruct() {
		try {
			cache = manager.getCache("security");
			final URI messagingUrl = new URI(SystemUtil.getenv(Messaging.MESSAGING_URL));
			client = Messaging.connectToServer(messagingUrl.resolve("security"));
			client.onMessage(message -> {
				if(message instanceof Token) {
					final Token token = (Token) message;
					cache.put(token.getTokenID(), token);
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
		return (Token) cache.get(tokenId);
	}
}
