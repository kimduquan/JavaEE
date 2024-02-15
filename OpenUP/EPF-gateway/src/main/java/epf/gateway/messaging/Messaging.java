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
import io.smallrye.common.annotation.RunOnVirtualThread;

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
	private final static String SERVICE = "service";
	
	/**
	 * 
	 */
	@Inject
	@Readiness
	transient Application application;

	/**
	 * @param path
	 * @param session
	 * @throws Exception 
	 */
	@OnOpen
    @RunOnVirtualThread
    public void onOpen(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(SERVICE)
    		final String service,
    		@PathParam(PATH) 
    		final String path, 
    		final Session session) throws Exception {
		LOGGER.log(Level.INFO, String.format("[Messaging.open][%s/%s/%s]session.id=%s", tenant, service, path, session.getId()));
		application.onOpen(path, session);
	}
	
	/**
	 * @param tenant
	 * @param service
	 * @param path
	 * @param message
	 * @param session
	 */
	@OnMessage
    @RunOnVirtualThread
    public void onMessage(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(SERVICE)
    		final String service,
    		@PathParam(PATH) 
    		final String path,
    		final String message, 
    		final Session session) {
		LOGGER.log(Level.INFO, String.format("[Messaging.message][%s/%s/%s]session.id=%s", tenant, service, path, session.getId()));
		application.sendText(message);
	}
	
	@OnMessage
    @RunOnVirtualThread
    public void onMessage(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(SERVICE)
    		final String service,
    		@PathParam(PATH) 
    		final String path,
    		final byte[] message, 
    		final Session session) {
		LOGGER.log(Level.INFO, String.format("[Messaging.message][%s/%s/%s]session.id=%s", tenant, service, path, session.getId()));
		application.sendBinary(message);
	}
	
	/**
	 * @param tenant
	 * @param service
	 * @param path
	 * @param session
	 * @param throwable
	 */
	@OnError
    @RunOnVirtualThread
    public void onError(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(SERVICE)
    		final String service,
    		@PathParam(PATH) 
    		final String path,
    		final Session session, 
    		final Throwable throwable) {
		LOGGER.log(Level.WARNING, String.format("[Messaging.error][%s/%s/%s]session.id=%s", tenant, service, path, session.getId()), throwable);
		application.onError(path, session);
	}
	
	/**
	 * @param tenant
	 * @param service
	 * @param path
	 * @param session
	 * @param closeReason
	 */
	@OnClose
    @RunOnVirtualThread
    public void onClose(
    		@PathParam(Naming.Management.TENANT)
    		final String tenant,
    		@PathParam(SERVICE)
    		final String service,
    		@PathParam(PATH) 
    		final String path,
    		final Session session, 
    		final CloseReason closeReason) throws Exception {
		LOGGER.log(Level.INFO, String.format("[Messaging.close][%s/%s/%s]session.id=%s", tenant, service, path, session.getId()));
		application.onClose(path, session);
	}
}
