package epf.concurrent.client.ext;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
	private final ReentrantLock lock = new ReentrantLock();
	
	/**
	 * 
	 */
	private final Condition isOpen = lock.newCondition();
	
	/**
	 * 
	 */
	private final Condition synchronized_ = lock.newCondition();
	
	/**
	 * 
	 */
	private transient Session synchronizedSession;

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
			synchronizedSessions.put(id, session(id));
		}
		else if("0".equals(flag)) {
			synchronizedSessions.remove(id);
			if(id.equals(synchronizedSession.getId())) {
				synchronized_.signalAll();
			}
		}
		else if("2".equals(flag)) {
			isOpen.signalAll();
		}
	}
	
	private ByteBuffer message(final String flag, final String id) {
		final String message = flag + id;
		return ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
	}
	
	private Session session(final String id) {
		return synchronizedSession.getOpenSessions().stream().filter(ss -> ss.getId().equals(id)).findFirst().get();
	}
	
	private void send(final Set<Session> sessions, final String flag, final String id) {
		final ByteBuffer data = message(flag, id);
		sessions.stream().forEach(session -> {
			try {
				session.getBasicRemote().sendPong(data);
			}
			catch(Exception ex) {
				LOGGER.log(Level.WARNING, "send", ex);
			}
		});
	}
	
	/**
	 * @param uri
	 * @throws Exception
	 */
	protected void connectToServer(final URI uri) throws Exception {
		lock.lock();
		if(synchronizedSession == null) {
			synchronizedSession = ContainerProvider.getWebSocketContainer().connectToServer(this, uri);
			isOpen.await();
		}
		lock.unlock();
	}
	
	/**
	 * @throws Exception
	 */
	protected void clear() throws Exception {
		synchronizedSession.close();
	}
	
	/**
	 * @return
	 */
	protected boolean isSynchronized() {
		return synchronizedSessions.containsKey(synchronizedSession.getId());
	}
	
	/**
	 * 
	 */
	protected void try_() {
		send(synchronizedSession.getOpenSessions(), "1", synchronizedSession.getId());
	}
	
	/**
	 * 
	 */
	protected void finally_() {
		send(synchronizedSession.getOpenSessions(), "0", synchronizedSession.getId());
	}
	
	/**
	 * @return
	 */
	protected String getId() {
		return synchronizedSession.getId();
	}
	
	/**
	 * @param default_
	 * @param id
	 * @return
	 */
	protected static Synchronized try_(final Synchronized default_, final String id) {
		Synchronized synchronized_ = null;
		final Optional<Session> session = default_.synchronizedSession.getOpenSessions().stream().filter(ss -> ss.getId().equals(id)).findFirst();
		if(session.isPresent()) {
			synchronized_ = new Synchronized();
			synchronized_.synchronizedSession = session.get();
		}
		return synchronized_;
	}
	
	/**
	 * @throws Exception
	 */
	public void synchronized_() throws Exception {
		lock.lock();
		if(isSynchronized()) {
			synchronized_.await();
		}
		lock.unlock();
	}
}
