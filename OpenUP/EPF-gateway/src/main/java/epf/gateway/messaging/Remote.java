/**
 * 
 */
package epf.gateway.messaging;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import epf.util.logging.Logging;
import epf.util.websocket.Client;
import epf.util.websocket.Message;
import epf.util.websocket.MessageHandler;
import epf.util.websocket.MessageTopic;

/**
 * @author PC
 *
 */
public class Remote implements Runnable, AutoCloseable {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Remote.class.getName());
	
	/**
	 * 
	 */
	private transient final Client client;
	
	/**
	 * 
	 */
	private transient final MessageTopic messages;
	
	/**
	 * @param uri
	 * @throws DeploymentException
	 * @throws IOException
	 */
	public Remote(final URI uri) throws DeploymentException, IOException {
		Objects.requireNonNull(uri, "URI");
		client = Client.connectToServer(uri);
		messages = new MessageTopic();
		client.onMessage(this::addMessage);
	}
	
	/**
	 * @param message
	 */
	protected void addMessage(final String message) {
		messages.add(new Message(message));
	}

	@Override
	public void close() throws Exception {
		client.close();
		messages.close();
	}
	
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
    	LOGGER.throwing(getClass().getName(), "onError", throwable);
    }

	@Override
	public void run() {
		messages.run();
	}
}
