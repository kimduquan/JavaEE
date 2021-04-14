/**
 * 
 */
package epf.gateway.messaging;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author PC
 *
 */
@ServerEndpoint("/messaging/{path}")
@ApplicationScoped
public class Messaging {
	
	/**
	 * 
	 */
	private final static String PATH = "path";
	
	/**
	 * 
	 */
	private final transient Map<String, Remote> remotes = new ConcurrentHashMap<>();
	
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
			final URI messagingUrl = new URI(System.getenv("epf.messaging.url"));
			final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			remotes.put("cache", new Remote(container, messagingUrl.resolve("cache")));
		} 
		catch (URISyntaxException | DeploymentException | IOException e) {
			logger.log(Level.SEVERE, "postConstruct", e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		remotes.values().parallelStream().forEach(server -> {
			try {
				server.close();
			} 
			catch (Exception e) {
				logger.throwing(Remote.class.getName(), "close", e);
			}
		});
	}

	/**
	 * @param path
	 * @param session
	 */
	@OnOpen
    public void onOpen(@PathParam(PATH) final String path, final Session session) {
		remotes.computeIfPresent(path, (p, remote) -> {
			remote.getServer().onOpen(session);
			return remote;
			}
		);
	}
	
	/**
	 * @param path
	 * @param session
	 * @param closeReason
	 */
	@OnClose
    public void onClose(@PathParam(PATH) final String path, final Session session, final CloseReason closeReason) {
		remotes.computeIfPresent(path, (p, remote) -> {
			remote.getServer().onClose(session, closeReason);
			return remote;
			}
		);
	}
	
	/**
	 * @param path
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(@PathParam(PATH) final String path, final String message, final Session session) {
		remotes.computeIfPresent(path, (p, remote) -> {
			remote.getServer().onMessage(message, session);
			return remote;
			}
		);
	}
	
	/**
	 * @param path
	 * @param session
	 * @param throwable
	 */
	@OnError
    public void onError(@PathParam(PATH) final String path, final Session session, final Throwable throwable) {
		remotes.computeIfPresent(path, (p, remote) -> {
			remote.getServer().onError(session, throwable);
			return remote;
			}
		);
	}
}
