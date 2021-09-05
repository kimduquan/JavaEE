/**
 * 
 */
package epf.util.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.function.Consumer;
import javax.websocket.ClientEndpoint;
import javax.websocket.DeploymentException;
import javax.websocket.OnError;
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
	private transient Consumer<? super Throwable> errorConsumer;
	
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
		messageConsumer = null;
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
	 * @param session
	 * @param throwable
	 */
	@OnError
	public void onError(final Session session, final Throwable throwable) {
		if(errorConsumer != null) {
			errorConsumer.accept(throwable);
		}
	}
	
	/**
	 * @param messageConsumer
	 */
	public void onMessage(final Consumer<? super String> messageConsumer) {
		this.messageConsumer = messageConsumer;
	}
	
	/**
	 * @param errorConsumer
	 */
	public void onError(final Consumer<? super Throwable> errorConsumer) {
		this.errorConsumer = errorConsumer;
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
}
