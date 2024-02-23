package epf.concurrent.client;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;

/**
 * 
 */
@ClientEndpoint
public class Synchronized {
	
	/**
	 * 
	 */
	private transient final Map<String, Session> closeSessions = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, Condition> conditions = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private final ReentrantLock lock = new ReentrantLock();
	
	/**
	 * 
	 */
	private final Condition new_ = lock.newCondition();
	
	/**
	 * 
	 */
	private Condition break_ = lock.newCondition();
	
	/**
	 * 
	 */
	private transient Session this_;

	/**
	 * 
	 */
	private transient Session left;
	
	/**
	 * 
	 */
	private transient Session right;
	
	/**
	 * 
	 */
	private final AtomicReference<State> state = new AtomicReference<>();
	
	private boolean isNull() {
		return left == null && right == null;
	}
	
	private boolean isLast() {
		return left != null && right == null;
	}
	
	private boolean isLeft(final String id) {
		return left != null && left.getId().equals(id);
	}
	
	private boolean isRight(final String id) {
		return right != null && right.getId().equals(id);
	}
	
	private ByteBuffer message(final Message message, final String id) {
		int code = message.ordinal();
		final String string = String.valueOf(code) + id;
		return ByteBuffer.wrap(string.getBytes(StandardCharsets.UTF_8));
	}
	
	private Message message(final ByteBuffer bytes, final StringBuilder id) {
		final String string = new String(bytes.array(), StandardCharsets.UTF_8);
		final String code = string.substring(0, 1);
		id.append(string.substring(1));
		return Message.values()[Integer.parseInt(code)];
	}
	
	private void send(final Session session, final Message message, final String id) throws Exception {
		final ByteBuffer data = message(message, id);
		session.getAsyncRemote().sendPong(data);
	}
	
	private void sendAll(final Message message, final String id) throws Exception {
		final ByteBuffer data = message(message, id);
		if(left != null) {
			left.getAsyncRemote().sendPong(data);
		}
		if(right != null) {
			right.getAsyncRemote().sendPong(data);
		}
	}
	
	private Session session(final String id) {
		return this_.getOpenSessions().stream().filter(session -> session.getId().equals(id)).findFirst().get();
	}
	
	private void break_(final Session session) throws Exception {
		lock.lock();
		if(isLeft(session.getId())) {
			if(!closeSessions.containsKey(session.getId())) {
				conditions.put(session.getId(), break_);
				break_.await();
			}
			send(left, Message.left, this_.getId());
			right = closeSessions.remove(session.getId());
		}
		else if(isRight(session.getId())){
			closeSessions.put(session.getId(), this_);
			final Condition condition = conditions.get(session.getId());
			if(condition != null) {
				condition.signal();
			}
		}
		lock.unlock();
	}

	/**
	 * @param session
	 * @throws Exception 
	 */
	@OnOpen
	public void onOpen(final Session session) throws Exception {
		if(isNull()) {
			left = session;
		}
		else if(isLast()) {
			right = session;
		}
	}
	
	/**
	 * @param session
	 * @param closeReason
	 */
	@OnClose
	public void onClose(final Session session, final CloseReason closeReason) throws Exception {
		
	}
	
	/**
	 * @param session
	 * @param throwable
	 */
	@OnError
	public void onError(final Session session, final Throwable throwable) throws Exception {
		break_(session);
	}
	
	/**
	 * @param session
	 * @param message
	 * @throws Exception 
	 */
	@OnMessage
	public void onMessage(final Session session, final PongMessage message) throws Exception {
		final ByteBuffer data = message.getApplicationData();
		final StringBuilder string = new StringBuilder();
		final Message msg = message(data, string);
		final String id = string.toString();
		switch(msg) {
			case new_:
				left = session(id);
				new_.signalAll();
				break;
			case return_:
				if(this_.getId().equals(id)) {
					state.set(State.new_);
				}
				else {
					sendAll(msg, id);
				}
				break;
			case synchronized_:
				if(this_.getId().equals(id)) {
					state.set(State.synchronized_);
				}
				else {
					sendAll(msg, id);
				}
				break;
			default:
				break;
		}
	}
	
	/**
	 * @param uri
	 * @throws Exception
	 */
	protected void connectToServer(final URI uri) throws Exception {
		lock.lock();
		if(this_ == null) {
			this_ = ContainerProvider.getWebSocketContainer().connectToServer(this, uri);
			this.new_.await();
		}
		lock.unlock();
	}
	
	/**
	 * @throws Exception
	 */
	protected void clear() throws Exception {
		this_.close();
	}
	
	/**
	 * @return
	 */
	protected boolean isSynchronized() {
		return State.synchronized_.equals(state.get());
	}
	
	/**
	 * @throws Exception
	 */
	public void finally_() throws Exception {
		lock.unlock();
		sendAll(Message.return_, this_.getId());
		lock.unlock();
	}
	
	/**
	 * @return
	 */
	public String getId() {
		return this_.getId();
	}
	
	/**
	 * @param default_
	 * @param id
	 * @return
	 */
	protected static Synchronized find(final Synchronized default_, final String id) {
		Synchronized synchronized_ = null;
		final Optional<Session> session = default_.this_.getOpenSessions().stream().filter(ss -> ss.getId().equals(id)).findFirst();
		if(session.isPresent()) {
			synchronized_ = new Synchronized();
			synchronized_.this_ = session.get();
		}
		return synchronized_;
	}
	
	/**
	 * @throws Exception
	 */
	public void synchronized_() throws Exception {
		lock.lock();
		lock.unlock();
	}
	
	public void synchronized_(final String id) throws Exception {
		lock.lock();
		lock.unlock();
	}
}
