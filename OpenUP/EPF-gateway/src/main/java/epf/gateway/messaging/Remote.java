/**
 * 
 */
package epf.gateway.messaging;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import epf.util.websocket.Client;
import epf.util.websocket.Message;
import epf.util.websocket.MessageQueue;
import epf.util.websocket.Server;

/**
 * @author PC
 *
 */
public class Remote implements Runnable, AutoCloseable {
	
	/**
	 * 
	 */
	private transient final Client client;
	
	/**
	 * 
	 */
	private transient final Server server;
	
	/**
	 * 
	 */
	private transient final MessageQueue messages;
	
	/**
	 * @param container
	 * @param uri
	 * @throws DeploymentException
	 * @throws IOException
	 */
	public Remote(final WebSocketContainer container, final URI uri) throws DeploymentException, IOException {
		Objects.requireNonNull(container, "WebSocketContainer");
		Objects.requireNonNull(uri, "URI");
		client = Client.connectToServer(container, uri);
		server = new Server();
		client.onMessage(this::addMessage);
		messages = new MessageQueue(client.getSession());
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
		server.close();
	}
	
	/**
	 * @param session
	 */
	public void onOpen(final Session session) {
		server.onOpen(session);
    }
 
    /**
     * @param session
     * @param closeReason
     */
    public void onClose(final Session session, final CloseReason closeReason) {
    	server.onClose(session, closeReason);
    }
    
    /**
     * @param session
     * @param throwable
     */
    public void onError(final Session session, final Throwable throwable) {
    	server.onError(session, throwable);
    }

	@Override
	public void run() {
		messages.run();
	}
}
