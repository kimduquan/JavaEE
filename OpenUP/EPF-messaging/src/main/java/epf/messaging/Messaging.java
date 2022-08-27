package epf.messaging;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import epf.messaging.client.MessageDecoder;
import epf.messaging.client.MessageEncoder;
import epf.naming.Naming;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ServerEndpoint(value = "/messaging/{tenant}/{service}/{path}", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
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
