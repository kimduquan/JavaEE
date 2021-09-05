/**
 * 
 */
package epf.security;

import java.net.URI;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.client.messaging.Client;
import epf.client.messaging.Messaging;
import epf.client.security.Token;
import epf.util.config.ConfigUtil;
import epf.util.logging.Logging;
import epf.util.websocket.Message;
import epf.util.websocket.MessageQueue;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Application {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Application.class.getName());
	
	/**
	 * 
	 */
	private transient Client client;
	
	/**
	 * 
	 */
	private transient MessageQueue messages;

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
			final URI messagingUrl = ConfigUtil.getURI(Messaging.MESSAGING_URL);
			client = Messaging.connectToServer(messagingUrl.resolve("security"));
			client.onMessage(msg -> {});
			messages = new MessageQueue(client.getSession());
			executor.submit(messages);
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
		messages.close();
		try {
			client.close();
		} 
		catch (Exception e) {
			LOGGER.throwing(LOGGER.getName(), "preDestroy", e);
		}
	}
	
	/**
	 * @param token
	 */
	public void login(@Observes final Token token) {
		messages.add(new Message(token));
	}
}
