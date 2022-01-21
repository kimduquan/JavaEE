/**
 * 
 */
package epf.gateway.messaging;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.gateway.security.SecurityUtil;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;

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
	private static final Logger LOGGER = LogManager.getLogger(Messaging.class.getName());
	
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
	private transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI messagingUrl = ConfigUtil.getURI(Naming.Messaging.MESSAGING_URL);
			final Remote persistence = new Remote(messagingUrl.resolve(Naming.PERSISTENCE));
			remotes.put(Naming.PERSISTENCE, persistence);
			executor.submit(persistence);
		} 
		catch (URISyntaxException | DeploymentException | IOException e) {
			LOGGER.log(Level.SEVERE, "postConstruct", e);
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
				LOGGER.throwing(Remote.class.getName(), "close", e);
			}
		});
	}

	/**
	 * @param path
	 * @param session
	 * @throws Exception 
	 */
	@OnOpen
    public void onOpen(@PathParam(PATH) final String path, final Session session) throws Exception {
		final Optional<String> tokenId = SecurityUtil.getTokenId(session);
		if(tokenId.isPresent()) {
			remotes.computeIfPresent(path, (p, remote) -> {
				remote.onOpen(session);
				return remote;
				}
			);
		}
		if(tokenId.isEmpty()) {
			closeSession(path, session);
		}
		else {
			SecurityUtil.authenticateTokenId(tokenId.get()).thenAccept(succeed -> {
				if(!succeed) {
					closeSession(path, session);
				}
			});
		}
	}
	
	/**
	 * @param path
	 * @param session
	 */
	protected void closeSession(final String path, final Session session) {
		final CloseReason reason = new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "");
		remotes.computeIfPresent(path, (p, remote) -> {
			remote.onClose(session, reason);
			return remote;
			}
		);
		try {
			session.close(reason);
		} 
		catch (IOException e) {
			LOGGER.throwing(LOGGER.getName(), "onOpen", e);
		}
	}
	
	/**
	 * @param path
	 * @param session
	 * @param closeReason
	 */
	@OnClose
    public void onClose(@PathParam(PATH) final String path, final Session session, final CloseReason closeReason) {
		remotes.computeIfPresent(path, (p, remote) -> {
			remote.onClose(session, closeReason);
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
		
	}
	
	/**
	 * @param path
	 * @param session
	 * @param throwable
	 */
	@OnError
    public void onError(@PathParam(PATH) final String path, final Session session, final Throwable throwable) {
		remotes.computeIfPresent(path, (p, remote) -> {
			remote.onError(session, throwable);
			return remote;
			}
		);
	}
}
