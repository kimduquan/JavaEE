package epf.util.websocket.ext;

import java.io.IOException;
import java.net.URI;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;

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
	private transient BiConsumer<String, Session> messageConsumer;
	
	/**
	 * 
	 */
	private transient BiConsumer<Throwable, Session> errorConsumer;
	
	/**
	 * 
	 */
	private transient BiConsumer<CloseReason, Session> closeConsumer;
	
	/**
	 * 
	 */
	private transient Consumer<Session> openConsumer;
	
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
	 * @param session
	 */
	@OnOpen
	public void onOpen(final Session session) {
		if(openConsumer != null) {
			openConsumer.accept(session);
		}
	}
	
	/**
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(final String message, final Session session) {
		if(messageConsumer != null) {
			messageConsumer.accept(message, session);
		}
	}
	
	/**
	 * @param reason
	 * @param session
	 */
	@OnClose
	public void onClose(final CloseReason reason, final Session session) {
		if(closeConsumer != null) {
			closeConsumer.accept(reason, session);
		}
	}
	
	/**
	 * @param throwable
	 * @param session
	 */
	@OnError
	public void onError(final Throwable throwable, final Session session) {
		if(errorConsumer != null) {
			errorConsumer.accept(throwable, session);
		}
	}
	
	/**
	 * @param openConsumer
	 */
	public void onOpen(final Consumer<Session> openConsumer) {
		this.openConsumer = openConsumer;
	}
	
	/**
	 * @param messageConsumer
	 */
	public void onMessage(final BiConsumer<String, Session> messageConsumer) {
		this.messageConsumer = messageConsumer;
	}
	
	/**
	 * @param closeConsumer
	 */
	public void onClose(final BiConsumer<CloseReason, Session> closeConsumer) {
		this.closeConsumer = closeConsumer;
	}
	
	/**
	 * @param errorConsumer
	 */
	public void onError(final BiConsumer<Throwable, Session> errorConsumer) {
		this.errorConsumer = errorConsumer;
	}
	
	/**
	 * @param container
	 * @param uri
	 * @return
	 * @throws DeploymentException
	 * @throws IOException
	 */
	protected static Client connectToServer(
			final WebSocketContainer container,
			final URI uri) throws DeploymentException, IOException {
		final Client client = new Client();
		client.session = container.connectToServer(client, uri);
		return client;
	}
	
	/**
	 * @param uri
	 * @return
	 * @throws DeploymentException
	 * @throws IOException
	 */
	public static Client connectToServer(final URI uri) throws DeploymentException, IOException {
		return connectToServer(ContainerProvider.getWebSocketContainer(), uri);
	}
}
