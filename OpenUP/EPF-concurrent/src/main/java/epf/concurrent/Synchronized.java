package epf.concurrent;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
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
@ServerEndpoint(value = Naming.Concurrent.SYNCHRONIZED)
@ApplicationScoped
public class Synchronized {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(Synchronized.class.getName());
	
	/**
	 * 
	 */
	private transient final Map<String, Session> synchronizedSessions = new ConcurrentHashMap<>();
	
	private ByteBuffer message(final String flag, final String id) {
		final String message = flag + id;
		return ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
	}
	
	private void send(final Session session, final String flag, final String id) throws Exception {
		final ByteBuffer data = message(flag, id);
		session.getBasicRemote().sendPong(data);
	}

	/**
	 * @param session
	 */
	@OnOpen
	public void onOpen(final Session session) {
		synchronizedSessions.forEach((id, ss) -> {
			try {
				send(ss, "1", id);
			}
			catch (Exception e) {
				LOGGER.log(Level.WARNING, "onOpen", e);
			}
		});
		try {
			send(session, "2", "");
		}
		catch (Exception e) {
			LOGGER.log(Level.WARNING, "onOpen", e);
		}
	}
	
	/**
	 * @param session
	 * @param closeReason
	 */
	@OnClose
	public void onClose(final Session session, final CloseReason closeReason) {
		synchronizedSessions.remove(session.getId());
	}
	
	/**
	 * @param session
	 * @param throwable
	 */
	@OnError
	public void onError(final Session session, final Throwable throwable) {
		synchronizedSessions.remove(session.getId());
	}
	
	/**
	 * @param session
	 * @param message
	 */
	@OnMessage
	public void onMessage(final Session session, final PongMessage message) {
		final ByteBuffer data = message.getApplicationData();
		final String messageData = new String(data.array(), StandardCharsets.UTF_8);
		final String flag = messageData.substring(0, 1);
		final String id = messageData.substring(1);
		if("1".equals(flag)) {
			synchronizedSessions.put(id, session);
		}
		else if("0".equals(flag)) {
			synchronizedSessions.remove(id);
		}
	}
}
