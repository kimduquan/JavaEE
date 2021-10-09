/**
 * 
 */
package epf.persistence.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;
import epf.util.concurrent.ObjectPublisher;
import epf.util.config.ConfigUtil;
import epf.util.logging.Logging;
import epf.util.websocket.Message;
import epf.util.websocket.MessageQueue;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.DeploymentException;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;
import epf.client.messaging.Client;
import epf.client.messaging.Messaging;
import epf.schema.EntityEvent;
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
	private transient ObjectPublisher<EntityEvent> publisher;
	
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
	private transient Client postLoadClient;
	
	/**
	 * 
	 */
	private transient MessageQueue postLoadMessages;
	
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
			client = Messaging.connectToServer(messagingUrl.resolve("persistence"));
			client.onMessage(msg -> {});
			messages = new MessageQueue(client.getSession());
			executor.submit(messages);
			postLoadClient = Messaging.connectToServer(messagingUrl.resolve("persistence/post-load"));
			postLoadClient.onMessage(msg -> {});
			postLoadMessages = new MessageQueue(postLoadClient.getSession());
			executor.submit(postLoadMessages);
			publisher = new ObjectPublisher<EntityEvent>(executor);
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
			publisher.close();
			messages.close();
			client.close();
			postLoadMessages.close();
			postLoadClient.close();
		} 
		catch (Exception e) {
			LOGGER.throwing(client.getClass().getName(), "close", e);
		}
	}
	
	/**
	 * @param object
	 */
	protected void send(final Object event) {
		final Message message = new Message(event);
		messages.add(message);
	}
	
	/**
	 * @param event
	 */
	public void postPersist(@Observes final PostPersist event) {
		send(event);
	}
	
	/**
	 * @param event
	 */
	public void postRemove(@Observes final PostRemove event) {
		send(event);
	}
	
	/**
	 * @param event
	 */
	public void postUpdate(@Observes final PostUpdate event) {
		send(event);
	}
	
	/**
	 * @param event
	 */
	public void postLoad(@Observes final PostLoad event) {
		final Message message = new Message(event);
		postLoadMessages.add(message);
	}
	
	@Outgoing("persistence")
    public Publisher<EntityEvent> getPublisher(){
		return publisher;
	}
}
