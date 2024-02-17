package epf.concurrent.client.ext;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
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
public class Synchronized implements AutoCloseable {
	
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
	private final ReentrantLock lock = new ReentrantLock();
	
	/**
	 * 
	 */
	private Condition isOpen = lock.newCondition();
	
	/**
	 * 
	 */
	private Condition isSynchronized = lock.newCondition();
	
	/**
	 * 
	 */
	private transient Session session;

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
			if(id.isEmpty()) {
				open();
			}
			else {
				synchronized_(id, session);
			}
		}
		else if("0".equals(flag)) {
			close(id);
			if(session.getId().equals(id)) {
				lock.lock();
				isSynchronized.signalAll();
			}
		}
	}
	
	private void open() {
		lock.lock();
		isOpen.signalAll();
	}
	
	private void synchronized_(final String id, final Session session) {
		synchronizedSessions.put(id, session);
	}
	
	private void close(final String id) {
		synchronizedSessions.remove(id);
	}
	
	/**
	 * @param uri
	 * @throws Exception
	 */
	protected void connectToServer(final URI uri) throws Exception {
		lock.lock();
		session = ContainerProvider.getWebSocketContainer().connectToServer(this, uri);
		isOpen.await();
	}
	
	/**
	 *
	 */
	public void close() throws Exception {
	}
	
	/**
	 * @throws Exception
	 */
	protected void clear() throws Exception {
		session.close();
	}
	
	/**
	 * @return
	 */
	protected boolean isSynchronized() {
		return synchronizedSessions.containsKey(session.getId());
	}
	
	/**
	 * 
	 */
	protected void try_() {
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
	protected void finally_() {
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
	
	public void synchronized_() throws Exception {
		lock.lock();
		if(isSynchronized()) {
			isSynchronized.await();
		}
	}
	
	/**
	 * @return
	 */
	public String getId() {
		return session.getId();
	}
}
