/**
 * 
 */
package epf.util.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import javax.websocket.ClientEndpoint;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 * @author PC
 *
 */
@ClientEndpoint
public class Client implements AutoCloseable {

	/**
	 * 
	 */
	private transient Session session;

	/**
	 * 
	 */
	private transient Consumer<? super String> messageConsumer;
	
	/**
	 * 
	 */
	private transient final Queue<String> messages;
	
	/**
	 * 
	 */
	protected Client() {
		super();
		messages = new ConcurrentLinkedQueue<>();
	}
	
	public Session getSession() {
		return session;
	}

	@Override
	public void close() throws Exception {
		session.close();
		messages.clear();
	}
	
	/**
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(final String message, final Session session) {
		if(messageConsumer == null) {
			messages.add(message);
		}
		else {
			messageConsumer.accept(message);
		}
	}
	
	/**
	 * @param messageConsumer
	 */
	public void onMessage(final Consumer<? super String> messageConsumer) {
		this.messageConsumer = messageConsumer;
	}
	
	/**
	 * @param container
	 * @param uri
	 * @return
	 * @throws DeploymentException
	 * @throws IOException
	 */
	public static Client connectToServer(
			final WebSocketContainer container,
			final URI uri) throws DeploymentException, IOException {
		final Client client = new Client();
		client.session = container.connectToServer(client, uri);
		return client;
	}
	
	public Queue<String> getMessages() {
		return messages;
	}
}
