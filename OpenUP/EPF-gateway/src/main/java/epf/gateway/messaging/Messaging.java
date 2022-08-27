package epf.gateway.messaging;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;

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
		server.openSession(tenant, service, path, session);
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
		server.closeSession(tenant, service, path, session, closeReason);
	}
}
