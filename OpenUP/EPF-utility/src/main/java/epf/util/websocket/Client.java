/**
 * 
 */
package epf.util.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.function.Consumer;
import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 * @author PC
 *
 */
@ClientEndpoint
public class Client extends Endpoint implements AutoCloseable {
	
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
    public void onMessage(final Object message, final Session session) {
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
			final ClientEndpointConfig config,
			final URI uri) throws DeploymentException, IOException {
		final Client client = new Client();
		final Session session = container.connectToServer(client, config, uri);
		client.session = session;
		return client;
	}

	@Override
	public void onOpen(final Session session, final EndpointConfig config) {
	}
}
