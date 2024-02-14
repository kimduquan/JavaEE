package epf.messaging;

import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import epf.naming.Naming;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ServerEndpoint(value = "/messaging/{tenant}/{service}/{path}")
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
	 * @param tenant
	 * @param service
	 * @param path
	 * @param session
	 */
	@OnOpen
    public void onOpen(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam("service")
    		final String service,
    		@PathParam(PATH) 
    		final String path, 
    		final Session session) {
		LOGGER.log(Level.INFO, String.format("[Messaging.open][%s/%s/%s]session.id=%s", tenant, service, path, session.getId()));
	}
	
	/**
	 * @param tenant
	 * @param service
	 * @param path
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam("service")
    		final String service,
    		@PathParam(PATH) 
    		final String path,
    		final String message, 
    		final Session session) {
		LOGGER.log(Level.INFO, String.format("[Messaging.message][%s/%s/%s]session.id=%s", tenant, service, path, session.getId()));
		session.getOpenSessions().stream().forEach(ss -> {
			ss.getAsyncRemote().sendText(message);
		});
	}
	
	/**
	 * @param tenant
	 * @param service
	 * @param path
	 * @param session
	 * @param closeReason
	 */
	@OnClose
    public void onClose(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam("service")
    		final String service,
    		@PathParam(PATH) 
    		final String path, 
    		final Session session, 
    		final CloseReason closeReason) {
		LOGGER.log(Level.INFO, String.format("[Messaging.close][%s/%s/%s]session.id=%s", tenant, service, path, session.getId()));
	}
	
	/**
	 * @param tenant
	 * @param service
	 * @param path
	 * @param session
	 * @param throwable
	 */
	@OnError
    public void onError(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam("service")
    		final String service,
    		@PathParam(PATH) 
    		final String path,
    		final Session session, 
    		final Throwable throwable) {
		LOGGER.log(Level.WARNING, String.format("[Messaging.error][%s/%s/%s]session.id=%s", tenant, service, path, session.getId()), throwable);
	}
}
