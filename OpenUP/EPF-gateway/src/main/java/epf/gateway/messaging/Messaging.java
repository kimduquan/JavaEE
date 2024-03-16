package epf.gateway.messaging;

import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ServerEndpoint("/messaging/{tenant}/{id}")
@ApplicationScoped
public class Messaging {
	
	/**
	 *
	 */
	private static final Logger LOGGER = LogManager.getLogger(Messaging.class.getName());
	
	/**
	 * 
	 */
	private final static String ID = "id";

	/**
	 * @param tenant
	 * @param id
	 * @param session
	 * @throws Exception
	 */
	@OnOpen
    public void onOpen(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(ID)
    		final String id,
    		final Session session) throws Exception {
		LOGGER.log(Level.INFO, String.format("[Messaging.open][%s/%s]session.id=%s", tenant, id, session.getId()));
	}
	
	/**
	 * @param tenant
	 * @param id
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(ID)
    		final String id,
    		final String message, 
    		final Session session) {
		LOGGER.log(Level.INFO, String.format("[Messaging.message][%s/%s]session.id=%s", tenant, id, session.getId()));
	}
	
	/**
	 * @param tenant
	 * @param id
	 * @param message
	 * @param session
	 */
	@OnMessage
    public void onMessage(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(ID)
    		final String id,
    		final byte[] message, 
    		final Session session) {
		LOGGER.log(Level.INFO, String.format("[Messaging.message][%s/%s]session.id=%s", tenant, id, session.getId()));
	}
	
	/**
	 * @param tenant
	 * @param id
	 * @param session
	 * @param throwable
	 */
	@OnError
    public void onError(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(ID)
    		final String id,
    		final Session session, 
    		final Throwable throwable) {
		LOGGER.log(Level.WARNING, String.format("[Messaging.error][%s/%s]session.id=%s", tenant, id, session.getId()), throwable);
	}
	
	/**
	 * @param tenant
	 * @param id
	 * @param session
	 * @param closeReason
	 * @throws Exception
	 */
	@OnClose
    public void onClose(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(ID)
    		final String id,
    		final Session session, 
    		final CloseReason closeReason) throws Exception {
		LOGGER.log(Level.INFO, String.format("[Messaging.close][%s/%s]session.id=%s", tenant, id, session.getId()));
	}
}
