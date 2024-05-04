package epf.util.websocket.ext;

import java.io.Closeable;
import java.util.logging.Logger;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class Server implements Runnable, Closeable {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Server.class.getName());
	
	/**
	 * 
	 */
	private final transient MessageTopic messages = new MessageTopic();
	
	/**
	 * @param session
	 */
	public void onOpen(final Session session) {
        messages.addConsumer(session.getId(), new MessageHandler(session));
    }
 
    /**
     * @param session
     * @param closeReason
     */
    public void onClose(final Session session, final CloseReason closeReason) {
        messages.removeConsumer(session.getId());
    }
    
    /**
     * @param session
     * @param throwable
     */
    public void onError(final Session session, final Throwable throwable) {
    	LOGGER.throwing(getClass().getName(), SessionUtil.toString(session), throwable);
    }
    
    /**
     * @param message
     * @param session
     */
    public void onMessage(final Object message, final Session session) {
    	messages.add(new Message(message));
    }

	@Override
	public void close() {
		messages.close();
	}

	@Override
	public void run() {
		messages.run();
	}
}
