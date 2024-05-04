package epf.util.websocket.ext;

import java.util.function.Consumer;
import java.util.logging.Logger;
import jakarta.websocket.Session;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class MessageHandler implements Consumer<Message> {
	
	/**
	 * 
	 */
	private transient final Logger LOGGER = LogManager.getLogger(MessageHandler.class.getName());
	
	/**
	 * 
	 */
	private transient final Session session;
	
	/**
	 * @param session
	 */
	public MessageHandler(final Session session) {
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
