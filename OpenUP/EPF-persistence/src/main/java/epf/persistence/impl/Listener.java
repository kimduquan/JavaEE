/**
 * 
 */
package epf.persistence.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import epf.util.logging.Logging;

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
	@Inject
	private transient Logger logger;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
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
		sendObject(event);
	}
	
	/**
	 * @param event
	 */
	public void postRemove(@Observes final PostRemove event) {
		sendObject(event);
	}
	
	/**
	 * @param event
	 */
	public void postUpdate(@Observes final PostUpdate event) {
		sendObject(event);
	}
	
	/**
	 * @param event
	 */
	public void postLoad(@Observes final PostLoad event) {
		sendObject(event);
	}
	
	/**
	 * @param object
	 */
	protected void sendObject(final Object object) {
		try {
			client.getSession().getBasicRemote().sendObject(object);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "sendObject", e);
		}
	}
}
