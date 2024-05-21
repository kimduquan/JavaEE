package epf.concurrent;

import java.util.logging.Logger;
import epf.naming.Naming;
import epf.util.logging.LogManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.PongMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

/**
 * 
 */
@ServerEndpoint(value = "/" + Naming.Concurrent.SYNCHRONIZED)
@ApplicationScoped
public class Synchronized {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(Synchronized.class.getName());

	/**
	 * @param session
	 */
	@OnOpen
	public void onOpen(final Session session) {
		LOGGER.info("onOpen:" + session.getId());
	}
	
	/**
	 * @param session
	 * @param closeReason
	 */
	@OnClose
	public void onClose(final Session session, final CloseReason closeReason) {
		LOGGER.info("onClose:" + session.getId());
		LOGGER.info("CloseReason:" + closeReason);
	}
	
	/**
	 * @param session
	 * @param throwable
	 */
	@OnError
	public void onError(final Session session, final Throwable throwable) {
		LOGGER.info("onError:" + session.getId());
		LOGGER.info("Throwable:" + throwable);
	}
	
	/**
	 * @param session
	 * @param message
	 */
	@OnMessage
	public void onMessage(final Session session, final PongMessage message) {
		LOGGER.info("onMessage:" + session.getId());
	}
}
