/**
 * 
 */
package epf.util.websocket;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
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
	private transient final Queue<Message> messages;
	
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
		messages = new ConcurrentLinkedQueue<>();
		sessions = new ConcurrentHashMap<>();
		isClose = new AtomicBoolean(false);
	}
	
	@Override
	public void run() {
		while(!isClose.get()) {
			while(!messages.isEmpty()) {
				final Message message = messages.peek();
				sessions.values().parallelStream().forEach(session -> {
					try {
						message.send(session);
					} 
					catch (IOException|EncodeException e) {
						LOGGER.throwing(getClass().getName(), "run", e);
					}
				});
				try {
					messages.poll().close();
				} 
				catch (IOException e) {
					LOGGER.throwing(getClass().getName(), "run", e);
				}
			}
			try {
				Thread.sleep(40);
			} 
			catch (InterruptedException e) {
				LOGGER.throwing(getClass().getName(), "run", e);
			}
		}
	}

	@Override
	public void close() throws IOException {
		isClose.set(true);
	}
	
	/**
	 * @param message
	 */
	public void add(final Message message) {
		messages.add(message);
	}
	
	/**
	 * @param session
	 */
	public void addSession(final Session session) {
		sessions.put(session.getId(), session);
	}
	
	/**
	 * @param session
	 */
	public void removeSession(final Session session) {
		sessions.remove(session.getId());
	}
}
