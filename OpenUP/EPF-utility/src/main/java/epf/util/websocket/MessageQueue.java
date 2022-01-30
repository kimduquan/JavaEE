/**
 * 
 */
package epf.util.websocket;

import java.util.Objects;
import java.util.logging.Logger;
import javax.websocket.Session;
import epf.util.concurrent.ObjectQueue;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class MessageQueue extends ObjectQueue<Message> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(MessageQueue.class.getName());
	
	/**
	 * 
	 */
	private transient final Session session;
	
	/**
	 * @param session
	 */
	public MessageQueue(final Session session) {
		super();
		Objects.requireNonNull(session, "Session");
		this.session = session;
	}

	@Override
	public void accept(final Message message) {
		try {
			message.send(session);
		} 
		catch (Exception e) {
			LOGGER.throwing(getClass().getName(), "accept", e);
		}
	}
}
