/**
 * 
 */
package epf.persistence.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.DeploymentException;
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
	private transient Client client;
	
	/**
	 * 
	 */
	private transient Queue<Future<Void>> futures;
	
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
			futures = new ConcurrentLinkedQueue<>();
			client = Messaging.connectToServer(new URI(System.getenv(Messaging.MESSAGING_URL)).resolve("persistence"));
		} 
		catch (DeploymentException|IOException|URISyntaxException e) {
			logger.throwing(Messaging.class.getName(), "connectToServer", e);
		}
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
	 */
	protected void sendObject(final Object object, final boolean async) {
		if(async) {
			futures.add(client.getSession().getAsyncRemote().sendObject(object));
		}
		else {
			try {
				Future<Void> future;
				do {
					future = futures.poll();
					if(future != null) {
						future.get();
					}
				}
				while(future != null);
				synchronized(client.getSession()) {
					client.getSession().getBasicRemote().sendObject(object);
				}
			}
			catch(Exception ex) {
				logger.throwing(getClass().getName(), "sendObject", ex);
			}
		}
	}
}
