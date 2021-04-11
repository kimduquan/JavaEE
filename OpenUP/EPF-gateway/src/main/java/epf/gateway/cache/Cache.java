/**
 * 
 */
package epf.gateway.cache;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import epf.util.websocket.Client;
import epf.util.websocket.Server;

/**
 * @author PC
 *
 */
@ServerEndpoint("/cache")
@ApplicationScoped
public class Cache {
	
	/**
	 * 
	 */
	private final transient Server server = new Server();
	
	/**
	 * 
	 */
	private transient Client client;
	
	/**
	 * 
	 */
	@Inject
	private transient Logger logger;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI cacheUri = new URI(System.getenv("epf.messaging.url")).resolve("cache");
			client = Client.connectToServer(ContainerProvider.getWebSocketContainer(), cacheUri);
			client.onMessage(this::sendObject);
		} 
		catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		try {
			client.close();
		} 
		catch (Exception e) {
			logger.throwing(Client.class.getName(), "close", e);
		}
		try {
			server.close();
		} 
		catch (Exception e) {
			logger.throwing(Server.class.getName(), "close", e);
		}
	}
	
	/**
	 * @param message
	 */
	protected void sendObject(final Object message) {
		server.forEach(session -> session.getAsyncRemote().sendObject(message));
	}
	
	/**
	 * @param session
	 */
	@OnOpen
    public void onOpen(final Session session) {
		server.onOpen(session);
	}
	
	/**
	 * @param session
	 * @param closeReason
	 */
	@OnClose
    public void onClose(final Session session, final CloseReason closeReason) {
		server.onClose(session, closeReason);
	}
	
	/**
	 * @param session
	 * @param throwable
	 */
	@OnError
    public void onError(final Session session, final Throwable throwable) {
		server.onError(session, throwable);
	}
	
	/**
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(final String message, final Session session) {
		server.onMessage(message, session);
	}
}
