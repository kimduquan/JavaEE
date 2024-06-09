package epf.gateway.messaging;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ServerEndpoint("/messaging/{service}/{topic}")
@ApplicationScoped
public class Messaging {
	
	/**
	 *
	 */
	private static final Logger LOGGER = LogManager.getLogger(Messaging.class.getName());
	
	/**
	 * 
	 */
	private final static String SERVICE = "service";
	
	/**
	 * 
	 */
	private final static String TOPIC = "topic";

	/**
	 * @param service
	 * @param topic
	 * @param session
	 * @throws Exception
	 */
	@OnOpen
    public void onOpen(
    		@PathParam(SERVICE)
    		final String service,
    		@PathParam(TOPIC)
    		final String topic,
    		final Session session) throws Exception {
		LOGGER.log(Level.INFO, String.format("[Messaging.open][%s/%s]session.id=%s", service, topic, session.getId()));
	}
	
	/**
	 * @param service
	 * @param topic
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(
    		@PathParam(SERVICE)
    		final String service,
    		@PathParam(TOPIC)
    		final String topic,
    		final String message, 
    		final Session session) {
		LOGGER.log(Level.INFO, String.format("[Messaging.message][%s/%s]session.id=%s", service, topic, session.getId()));
	}
	
	@OnMessage
    public void onMessage(
    		@PathParam(SERVICE)
    		final String service,
    		@PathParam(TOPIC)
    		final String topic,
    		final InputStream message, 
    		final Session session) {
		LOGGER.log(Level.INFO, String.format("[Messaging.message][%s/%s]session.id=%s", service, topic, session.getId()));
	}
	
	/**
	 * @param service
	 * @param topic
	 * @param session
	 * @param throwable
	 */
	@OnError
    public void onError(
    		@PathParam(SERVICE)
    		final String service,
    		@PathParam(TOPIC)
    		final String topic,
    		final Session session, 
    		final Throwable throwable) {
		LOGGER.log(Level.SEVERE, String.format("[Messaging.error][%s/%s]session.id=%s", service, topic, session.getId()), throwable);
	}
	
	/**
	 * @param service
	 * @param topic
	 * @param session
	 * @param closeReason
	 * @throws Exception
	 */
	@OnClose
    public void onClose(
    		@PathParam(SERVICE)
    		final String service,
    		@PathParam(TOPIC)
    		final String topic,
    		final Session session, 
    		final CloseReason closeReason) throws Exception {
		LOGGER.log(Level.INFO, String.format("[Messaging.close][%s/%s]session.id=%s", service, topic, session.getId()));
	}
}
