/**
 * 
 */
package epf.util.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.Session;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class Server implements AutoCloseable {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Server.class.getName());
	
	/**
	 * 
	 */
	private final transient Map<String, Session> sessions = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private final transient AtomicLong messageCount = new AtomicLong(0);
	
	/**
	 * @param id
	 * @return
	 */
	protected Session getSession(final String sessionId) {
		return sessions.get(sessionId);
	}
	
	/**
	 * @param session
	 */
	public void onOpen(final Session session) {
        sessions.put(session.getId(), session);
    }
 
    /**
     * @param session
     * @param closeReason
     */
    public void onClose(final Session session, final CloseReason closeReason) {
        sessions.remove(session.getId());
    }
    
    /**
     * @param message
     * @param session
     */
    public void onMessage(final Object message, final Session session) {
    	messageCount.incrementAndGet();
	}
    
    /**
     * @param session
     * @param throwable
     */
    public void onError(final Session session, final Throwable throwable) {
    	LOGGER.throwing(getClass().getName(), SessionUtil.toString(session), throwable);
    }
    
    /**
     * @param consumer
     */
    public void forEach(final Consumer<? super Session> consumer) {
    	sessions.values()
    	.stream()
    	.filter(Session::isOpen)
    	.forEach(consumer);
    }

	@Override
	public void close() throws Exception {
		sessions.values()
    	.parallelStream()
    	.filter(Session::isOpen)
    	.forEach(session -> {
    		try {
				session.close();
			} 
			catch (IOException e) {
				LOGGER.throwing(Session.class.getName(), "close", e);
			}
		});
		sessions.clear();
	}
}
