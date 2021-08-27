/**
 * 
 */
package epf.util.websocket;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import javax.websocket.Session;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class MessageTopic implements Runnable, Closeable {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(MessageTopic.class.getName());

	/**
	 * 
	 */
	public transient final Message BREAK = new Message(new Object());
	
	/**
	 * 
	 */
	private transient final BlockingQueue<Message> messages;
	
	/**
	 * 
	 */
	private final transient Map<String, Session> sessions;
	
	/**
	 * 
	 */
	private transient final AtomicBoolean isClose;
	
	/**
	 * 
	 */
	public MessageTopic() {
		messages = new LinkedBlockingQueue<>();
		sessions = new ConcurrentHashMap<>();
		isClose = new AtomicBoolean(false);
	}
	
	@Override
	public void run() {
		while(!isClose.get()) {
			try {
				final Message message = messages.take();
				if(BREAK == message) {
					break;
				}
				sessions.values().parallelStream().forEach(session -> {
					try {
						message.send(session);
					} 
					catch (Exception e) {
						LOGGER.throwing(getClass().getName(), "run", e);
					}
				});
			} 
			catch (Exception e) {
				LOGGER.throwing(getClass().getName(), "run", e);
			}
		}
	}

	@Override
	public void close() throws IOException {
		isClose.set(true);
		messages.add(BREAK);
	}
	
	/**
	 * @param message
	 */
	public void add(final Message message) {
		Objects.requireNonNull(message, "Message");
		messages.add(message);
	}
	
	/**
	 * @param session
	 */
	public void addSession(final Session session) {
		Objects.requireNonNull(session, "Session");
		sessions.put(session.getId(), session);
	}
	
	/**
	 * @param session
	 */
	public void removeSession(final Session session) {
		Objects.requireNonNull(session, "Session");
		sessions.remove(session.getId());
	}
}
