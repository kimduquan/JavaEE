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
import jakarta.websocket.server.ServerEndpoint;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ServerEndpoint("/messaging")
@ApplicationScoped
public class Messaging {
	
	/**
	 *
	 */
	private static final Logger LOGGER = LogManager.getLogger(Messaging.class.getName());

	/**
	 * @param session
	 * @throws Exception
	 */
	@OnOpen
    public void onOpen(final Session session) throws Exception {
		LOGGER.log(Level.INFO, String.format("[Messaging.open]session.id=%s", session.getId()));
	}
	
	/**
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(
    		final String message, 
    		final Session session) {
	}
	
	/**
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(
    		final InputStream message, 
    		final Session session) {
	}
	
	/**
	 * @param session
	 * @param throwable
	 */
	@OnError
    public void onError(
    		final Session session, 
    		final Throwable throwable) {
		LOGGER.log(Level.SEVERE, String.format("[Messaging.error]session.id=%s, throwable:%s", session.getId(), throwable), throwable);
	}
	
	@OnClose
    public void onClose(
    		final Session session, 
    		final CloseReason closeReason) throws Exception {
		LOGGER.log(Level.INFO, String.format("[Messaging.close]session.id=%s, close reason:%s", session.getId(), closeReason));
	}
}
