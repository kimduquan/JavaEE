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
import epf.util.logging.LogManager;
import epf.util.websocket.Server;

/**
 * @author PC
 *
 */
@ServerEndpoint(value = "/messaging/{path}/{event}", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
@ApplicationScoped
public class Event {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Event.class.getName());
	
	/**
	 * 
	 */
	private final static String PATH = "path";
	
	/**
	 * 
	 */
	private final static String EVENT = "event";
	
	/**
	 * 
	 */
	private final transient Map<String, Map<String, Server>> servers = new ConcurrentHashMap<>();
	
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
		final Map<String, Server> scheduleServer = new ConcurrentHashMap<>();
		servers.put(Naming.SCHEDULE, scheduleServer);
		final Server shellEvent = new Server();
		scheduleServer.put("shell", shellEvent);
		executor.submit(shellEvent);
		
		final Map<String, Server> persistenceServer = new ConcurrentHashMap<>();
		servers.put(Naming.PERSISTENCE, persistenceServer);
		final Server postLoadEvent = new Server();
		persistenceServer.put("post-load", postLoadEvent);
		executor.submit(postLoadEvent);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		servers.values().parallelStream().forEach(server -> {
			server.values().parallelStream().forEach(event -> {
				try {
					event.close();
				} 
				catch (Exception e) {
					LOGGER.throwing(LOGGER.getName(), "preDestroy", e);
				}
			});
			server.clear();
		});
		servers.clear();
	}

	/**
	 * @param path
	 * @param event
	 * @param session
	 */
	@OnOpen
    public void onOpen(@PathParam(PATH) final String path, @PathParam(EVENT) final String event, final Session session) {
		servers.computeIfPresent(path, (p, server) -> {
			server.computeIfPresent(event, (e, eventServer) -> {
				eventServer.onOpen(session);
				return eventServer;
			});
			return server;
			}
		);
	}
	
	/**
	 * @param path
	 * @param event
	 * @param session
	 * @param closeReason
	 */
	@OnClose
    public void onClose(@PathParam(PATH) final String path, @PathParam(EVENT) final String event, final Session session, final CloseReason closeReason) {
		servers.computeIfPresent(path, (p, server) -> {
			server.computeIfPresent(event, (e, eventServer) -> {
				eventServer.onClose(session, closeReason);
				return eventServer;
			});
			return server;
			}
		);
	}
	
	/**
	 * @param path
	 * @param event
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(@PathParam(PATH) final String path, @PathParam(EVENT) final String event, final String message, final Session session) {
		servers.computeIfPresent(path, (p, server) -> {
			server.computeIfPresent(event, (e, eventServer) -> {
				eventServer.onMessage(message, session);
				return eventServer;
			});
			return server;
			}
		);
	}
	
	/**
	 * @param path
	 * @param event
	 * @param session
	 * @param throwable
	 */
	@OnError
    public void onError(@PathParam(PATH) final String path, @PathParam(EVENT) final String event, final Session session, final Throwable throwable) {
		servers.computeIfPresent(path, (p, server) -> {
			server.computeIfPresent(event, (e, eventServer) -> {
				eventServer.onError(session, throwable);
				return eventServer;
			});
			return server;
			}
		);
	}
}
