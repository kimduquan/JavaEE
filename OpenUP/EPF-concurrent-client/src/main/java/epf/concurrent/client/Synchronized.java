package epf.concurrent.client;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
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
 * The triple: A,B,C sessions
 * 		A.left = B
 * 		B.left = C
 * 		A.right = C
 * 
 * The empty session : session does not have left and right
 * The first session : session has right but left
 * The last session : session has left but right
 * The normal session : session has left and right
 */
@ClientEndpoint
public class Synchronized {
	
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
	private final Condition synchronized_ = lock.newCondition();
	
	/**
	 * 
	 */
	private final Condition return_ = lock.newCondition();
	
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
	private transient Session root;
	
	/**
	 * 
	 */
	private final AtomicReference<Session> _synchronized = new AtomicReference<>();
	
	/*
	 * check the session is empty session or not
	 */
	private boolean isNull() {
		return left == null && right == null;
	}
	
	/**
	 * check this session is first session or not
	 */
	private boolean isFirst() {
		return left == null && right != null;
	}
	
	private boolean isRoot() {
		return root != null && root.getId().equals(this_.getId());
	}
	
	private boolean hasLeft() {
		return left != null;
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
		session.getBasicRemote().sendPong(data);
	}
	
	private void sendAll(final Session parent, final Message message, final String id) throws Exception {
		final ByteBuffer data = message(message, id);
		if(left != null && !left.getId().equals(parent.getId())) {
			left.getAsyncRemote().sendPong(data);
		}
		if(right != null) {
			right.getAsyncRemote().sendPong(data);
		}
	}
	
	private Session getOpenSession(final String id) {
		return this_.getOpenSessions().stream().filter(session -> session.getId().equals(id)).findFirst().get();
	}

	/**
	 * handle when session 'x' is broken:
	 * case 1:
	 * 		x.left	= A
	 * 		A.left	= B
	 * 		x.right	= B
	 * case 2:
	 * 		A.left	= x
	 * 		x.left	= B
	 * 		A.right	= B
	 * 	=>	detect A by checking condition A.left = x
	 * 		make A as last session
	 * case 3:
	 *  	A.left	= B
	 *  	B.left	= x
	 *  	A.right	= x
	 *  	B.right	= C
	 *  =>	detect A by checking condition A.right = x
	 *  	set B.left = B.right(C)
	 *  	set A.rigth = B.right(C)
	 *  	make B as last session by set B.right = null
	 */
	private void break_(final Session session) throws Exception {
		if(isLeft(session.getId())) {
			left = right;
		}
		else if(isRight(session.getId())) {
			if(hasLeft()) {
				send(left, Message.break_, this_.getId());
			}
			else {
				right = null;
			}
		}
	}

	/**
	 * @param session
	 * @throws Exception 
	 */
	/*
	 * set the current first session as normal session
	 * set the current null session as first session 
	 * set the new session as empty session
	 */
	@OnOpen
	public void onOpen(final Session session) throws Exception {
		if(isRoot()) {
			send(session, Message.root, this_.getId());
		}
		if(isNull()) {
			right = session;
		}
		else if(isFirst()) {
			left = right;
			right = session;
			send(left, Message.left, right.getId());
		}
	}
	
	/**
	 * @param session
	 * @param closeReason
	 */
	@OnClose
	public void onClose(final Session session, final CloseReason closeReason) throws Exception {
		break_(session);
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
		boolean null_ = false;
		switch(msg) {
			case synchronized_:
				if(_synchronized.get() == null) {
					if(this_.getId().equals(id)) {
						_synchronized.set(session);
					}
					else {
						final Session synchronizedSession = getOpenSession(id);
						_synchronized.set(synchronizedSession);
						send(synchronizedSession, Message.synchronized_, synchronizedSession.getId());
					}
					synchronized_.signalAll();
				}
				break;
			case return_:
				if(this_.getId().equals(id)) {
					if(_synchronized.get() != null) {
						send(_synchronized.get(), Message.return_, "");
					}
					_synchronized.set(null);
					return_.signalAll();
				}
				else if(id.isEmpty()) {
					_synchronized.set(null);
					return_.signalAll();
				}
				else {
					sendAll(session, Message.return_, id);
				}
				break;
			case break_:
				send(getOpenSession(id), Message.right, right.getId());
				left = right;
				right = null;
				break;
			case left:
				null_ = isNull();
				left = getOpenSession(id);
				if(null_) {
					new_.signalAll();
				}
				break;
			case right:
				null_ = isNull();
				right = getOpenSession(id);
				if(null_) {
					new_.signalAll();
				}
				break;
			case root:
				root = getOpenSession(id);
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
		return this._synchronized.get() != null;
	}
	
	/**
	 * @throws Exception
	 */
	public void finally_() throws Exception {
		lock.lock();
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
		synchronized_(this_.getId());
	}
	
	/**
	 * @param id
	 * @throws Exception
	 */
	public void synchronized_(final String id) throws Exception {
		lock.lock();
		send(getOpenSession(id), Message.synchronized_, this_.getId());
		synchronized_.await();
		lock.unlock();
	}
	
	/**
	 * @throws Exception
	 */
	public void return_() throws Exception {
		return_(this_.getId());
	}
	
	/**
	 * @param id
	 * @throws Exception
	 */
	public void return_(final String id) throws Exception {
		lock.lock();
		send(root, Message.return_, id);
		return_.await();
		lock.unlock();
	}
}
