/**
 * 
 */
package epf.util.websocket;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class MessageQueue implements Runnable, Closeable {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(MessageQueue.class.getName());
	
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
	private transient final Session session;
	
	/**
	 * 
	 */
	private transient final AtomicBoolean isClose;
	
	/**
	 * @param session
	 */
	public MessageQueue(final Session session) {
		Objects.requireNonNull(session, "Session");
		this.session = session;
		this.messages = new LinkedBlockingQueue<>();
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
				message.send(session);
				message.close();
			} 
			catch (IOException|EncodeException | InterruptedException e) {
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
}
