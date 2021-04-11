/**
 * 
 */
package epf.util.websocket;

import java.io.IOException;
import java.net.URI;
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
	private transient Consumer<Object> messageConsumer;
	
	/**
	 * 
	 */
	protected Client() {
		super();
	}
	
	public Session getSession() {
		return session;
	}

	@Override
	public void close() throws Exception {
		session.close();
	}
	
	/**
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(final String message, final Session session) {
		if(messageConsumer != null) {
			messageConsumer.accept(message);
		}
	}
	
	/**
	 * @param messageConsumer
	 */
	public void onMessage(final Consumer<? super Object> messageConsumer) {
		this.messageConsumer = messageConsumer;
	}
	
	/**
	 * @param container
	 * @param uri
	 * @param messageConsumer
	 * @return
	 * @throws DeploymentException
	 * @throws IOException
	 */
	public static Client connectToServer(
			final WebSocketContainer container, 
			final URI uri) throws DeploymentException, IOException {
		final Client client = new Client();
		final Session session = container.connectToServer(client, uri);
		client.session = session;
		return client;
	}
}
