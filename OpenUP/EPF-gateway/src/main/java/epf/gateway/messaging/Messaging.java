package epf.gateway.messaging;

import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ServerEndpoint("/messaging/{tenant}/{service}/{path}")
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
	@Inject @Readiness
	transient Server server;

	/**
	 * @param path
	 * @param session
	 * @throws Exception 
	 */
	@OnOpen
    public void onOpen(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam("service")
    		final String service,
    		@PathParam(PATH) 
    		final String path, 
    		final Session session) throws Exception {
		LOGGER.log(Level.INFO, String.format("[Messaging.open][%s/%s/%s]session.id=%s", tenant, service, path, session.getId()));
		server.openSession(tenant, service, path, session);
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
    		final CloseReason closeReason) throws Exception {
		LOGGER.log(Level.INFO, String.format("[Messaging.close][%s/%s/%s]session.id=%s", tenant, service, path, session.getId()));
		server.closeSession(tenant, service, path, session, closeReason);
	}
}
