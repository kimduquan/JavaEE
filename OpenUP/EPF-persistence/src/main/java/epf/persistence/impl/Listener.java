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
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.DeploymentException;
import epf.client.messaging.Client;
import epf.client.messaging.Handler;
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
	private transient URI messagingUrl;
	
	/**
	 * 
	 */
	private transient final Queue<Client> clients = new ConcurrentLinkedQueue<>();
	
	/**
	 * 
	 */
	@Inject
	private transient Logger logger;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			messagingUrl = new URI(SystemUtil.getenv(Messaging.MESSAGING_URL));
			final Client client = Messaging.connectToServer(messagingUrl.resolve("persistence"));
			clients.add(client);
		} 
		catch (DeploymentException|IOException|URISyntaxException e) {
			logger.throwing(Messaging.class.getName(), "connectToServer", e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		clients.forEach(client -> {
			try {
				client.close();
			} 
			catch (Exception e) {
				logger.throwing(client.getClass().getName(), "close", e);
			}
		});
		clients.clear();
	}
	
	/**
	 * @param event
	 */
	public void postPersist(@Observes final PostPersist event) {
		sendObject(event, false);
	}
	
	/**
	 * @param event
	 */
	public void postRemove(@Observes final PostRemove event) {
		sendObject(event, false);
	}
	
	/**
	 * @param event
	 */
	public void postUpdate(@Observes final PostUpdate event) {
		sendObject(event, false);
	}
	
	/**
	 * @param event
	 */
	public void postLoad(@Observes final PostLoad event) {
		sendObject(event, true);
	}
	
	/**
	 * @param object
	 * @param async
	 */
	protected void sendObject(final Object object, final boolean async) {
		try(Handler handler = new Handler(clients, async, messagingUrl.resolve("persistence"))) {
			handler.sendObject(object);
		} 
		catch (Exception e) {
			logger.throwing(Messaging.class.getName(), "sendObject", e);
		}
	}
}
