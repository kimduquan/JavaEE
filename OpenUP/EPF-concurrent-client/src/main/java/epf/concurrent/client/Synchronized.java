package epf.concurrent.client;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import epf.util.logging.LogManager;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.PongMessage;
import jakarta.websocket.Session;

/**
 * 
 */
@ClientEndpoint
public class Synchronized {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(Synchronized.class.getName());
	
	/**
	 * 
	 */
	private transient final Map<String, Session> synchronizedSessions = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private final URI uri;
	
	/**
	 * 
	 */
	private transient Session session;
	
	/**
	 * @param uri
	 */
	public Synchronized(final URI uri) {
		this.uri = uri;
	}

	/**
	 * @param session
	 */
	@OnOpen
	public void onOpen(final Session session) {
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
	
	public void connectToServer() throws Exception {
		session = ContainerProvider.getWebSocketContainer().connectToServer(this, uri);
		while(!synchronizedSessions.containsKey("")) {
			Thread.sleep(41);
		}
	}
	
	public void close() {
		session.close();
	}
	
	/**
	 * @return
	 */
	public boolean isSynchronized() {
		return synchronizedSessions.containsKey(session.getId());
	}
	
	/**
	 * 
	 */
	public void try_() {
		final String message = "1" + session.getId();
		final ByteBuffer data = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
		session.getOpenSessions().forEach(ss -> {
			try {
				if(ss.isOpen()) {
					ss.getBasicRemote().sendPong(data);
				}
			} 
			catch (Exception e) {
				LOGGER.log(Level.WARNING, "try", e);
			}
		});
	}
	
	/**
	 * 
	 */
	public void finally_() {
		final String message = "0" + session.getId();
		final ByteBuffer data = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
		session.getOpenSessions().forEach(ss -> {
			try {
				if(ss.isOpen()) {
					ss.getBasicRemote().sendPong(data);
				}
			} 
			catch (Exception e) {
				LOGGER.log(Level.WARNING, "finally", e);
			}
		});
	}
}
