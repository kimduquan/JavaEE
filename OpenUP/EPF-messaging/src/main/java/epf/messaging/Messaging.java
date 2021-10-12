/**
 * 
 */
package epf.messaging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.messaging.client.MessageDecoder;
import epf.messaging.client.MessageEncoder;
import epf.naming.Naming;
import epf.util.logging.Logging;
import epf.util.websocket.Server;

/**
 * @author PC
 *
 */
@ServerEndpoint(value = "/messaging/{path}", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
@ApplicationScoped
public class Messaging {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Messaging.class.getName());
	
	/**
	 * 
	 */
	private final static String PATH = "path";
	
	/**
	 * 
	 */
	private final transient Map<String, Server> servers = new ConcurrentHashMap<>();
	
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
		final Server persistence = new Server();
		servers.put(Naming.PERSISTENCE, persistence);
		executor.submit(persistence);
		final Server cache = new Server();
		servers.put(Naming.CACHE, cache);
		executor.submit(cache);
		final Server security = new Server();
		servers.put(Naming.SECURITY, security);
		executor.submit(security);
		final Server schedule = new Server();
		servers.put(Naming.SCHEDULE, schedule);
		executor.submit(schedule);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		servers.values().parallelStream().forEach(server -> {
			try {
				server.close();
			} 
			catch (Exception e) {
				LOGGER.throwing(Server.class.getName(), "close", e);
			}
		});
	}

	/**
	 * @param path
	 * @param session
	 */
	@OnOpen
    public void onOpen(@PathParam(PATH) final String path, final Session session) {
		servers.computeIfPresent(path, (p, server) -> {
			server.onOpen(session);
			return server;
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
		servers.computeIfPresent(path, (p, server) -> {
			server.onClose(session, closeReason);
			return server;
		});
	}
	
	/**
	 * @param path
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(@PathParam(PATH) final String path, final String message, final Session session) {
		servers.computeIfPresent(path, (p, server) -> {
			server.onMessage(message, session);
			return server;
		});
	}
	
	/**
	 * @param path
	 * @param session
	 * @param throwable
	 */
	@OnError
    public void onError(@PathParam(PATH) final String path, final Session session, final Throwable throwable) {
		servers.computeIfPresent(path, (p, server) -> {
			server.onError(session, throwable);
			return server;
		});
	}
}
