/**
 * 
 */
package epf.persistence.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;
import epf.util.SystemUtil;
import epf.util.logging.Logging;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.websocket.DeploymentException;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;
import epf.client.messaging.Client;
import epf.client.messaging.Messaging;
import epf.schema.PostLoad;
import epf.schema.PostPersist;
import epf.schema.PostRemove;
import epf.schema.PostUpdate;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Listener {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Listener.class.getName());
	
	/**
	 * 
	 */
	private transient Client client;
	
	/**
	 * 
	 */
	private transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	private transient MessageQueue messages;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI messagingUrl = new URI(SystemUtil.getenv(Messaging.MESSAGING_URL));
			client = Messaging.connectToServer(messagingUrl.resolve("persistence"));
			executor = ManagedExecutor.builder().propagated(ThreadContext.APPLICATION).build();
			messages = new MessageQueue(client);
			executor.submit(messages);
		} 
		catch (DeploymentException|IOException|URISyntaxException e) {
			LOGGER.throwing(Messaging.class.getName(), "connectToServer", e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		try {
			messages.close();
			executor.shutdownNow();
			client.close();
		} 
		catch (Exception e) {
			LOGGER.throwing(client.getClass().getName(), "close", e);
		}
	}
	
	/**
	 * @param object
	 */
	protected void send(final Object object, final boolean sync) {
		final Message message = new Message(object);
		messages.add(message);
		if(sync) {
			message.waitToClose();
		}
	}
	
	/**
	 * @param event
	 */
	public void postPersist(@Observes final PostPersist event) {
		send(event, true);
	}
	
	/**
	 * @param event
	 */
	public void postRemove(@Observes final PostRemove event) {
		send(event, true);
	}
	
	/**
	 * @param event
	 */
	public void postUpdate(@Observes final PostUpdate event) {
		send(event, true);
	}
	
	/**
	 * @param event
	 */
	public void postLoad(@Observes final PostLoad event) {
		send(event, false);
	}
}
