/**
 * 
 */
package epf.persistence.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
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
	private transient URI messagingUrl;
	
	/**
	 * 
	 */
	private transient Client client;
	
	/**
	 * 
	 */
	private transient final Queue<Message> objects = new ConcurrentLinkedQueue<>();
	
	/**
	 * 
	 */
	private transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	private transient MessageTask task;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			messagingUrl = new URI(SystemUtil.getenv(Messaging.MESSAGING_URL));
			client = Messaging.connectToServer(messagingUrl.resolve("persistence"));
			executor = ManagedExecutor.builder().propagated(ThreadContext.APPLICATION).build();
			task = new MessageTask(client, objects);
			executor.submit(task);
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
			task.close();
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
	protected void send(final Object object) {
		final Message message = new Message(object);
		objects.add(message);
		message.waitObject();
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
		send(event);
	}
}
