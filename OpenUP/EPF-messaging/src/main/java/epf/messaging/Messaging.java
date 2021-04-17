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
import epf.client.messaging.MessageDecoder;
import epf.client.messaging.MessageEncoder;
import epf.util.websocket.Server;

/**
 * @author PC
 *
 */
@ServerEndpoint(value = "/{path}", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
@ApplicationScoped
public class Messaging {
	
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
	private transient Logger logger;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		servers.put("persistence", new Server());
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
				logger.throwing(Server.class.getName(), "close", e);
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
    public void onMessage(@PathParam(PATH) final String path, final Object message, final Session session) {
		servers.computeIfPresent(path, (p, server) -> {
			server.onMessage(message, session);
			server.forEach(ss -> ss.getAsyncRemote().sendObject(message));
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
