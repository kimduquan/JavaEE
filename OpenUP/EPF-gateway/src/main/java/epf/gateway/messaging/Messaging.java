package epf.gateway.messaging;

import java.net.URI;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.eclipse.microprofile.health.Readiness;
import epf.gateway.Registry;
import epf.gateway.security.SecurityUtil;
import epf.naming.Naming;
import epf.util.StringUtil;
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
	private final transient Map<String, Remote> remotes = new ConcurrentHashMap<>();
	
	/**
	 *
	 */
	private URI messagingUrl;
	
	/**
	 * 
	 */
	@Inject @Readiness
	transient Registry registry;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			messagingUrl = registry.lookup(Naming.MESSAGING).orElseThrow(() -> new NoSuchElementException(Naming.MESSAGING));
			final Remote remote = new Remote(messagingUrl.resolve(Remote.urlOf(Optional.empty(), Naming.QUERY)), Remote.pathOf(Optional.empty(), Naming.QUERY));
			remote.connectToServer();
			remotes.put(remote.getPath(), remote);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[Messaging.remotes]", e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		remotes.values().stream().forEach(server -> {
			try {
				server.close();
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "preDestroy", e);
			}
		});
	}

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
		final String remotePath = getRemotePath(tenant, service, path);
		if(remotes.containsKey(remotePath)) {
			final Optional<String> token = SecurityUtil.getToken(session);
			if(token.isPresent()) {
				authenticate(token.get(), remotePath, session);
			}
			else {
				closeSession(session, new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, ""));
				throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
			}
		}
		else {
			closeSession(session, new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, ""));
			throw new NotFoundException();
		}
	}
	
	/**
	 * @param tenant
	 * @param service
	 * @param path
	 * @return
	 * @throws Exception 
	 */
	private String getRemotePath(final String tenant, final String service, final String path) throws Exception {
		final String decodedPath = StringUtil.decodeURL(path);
		return Remote.pathOf(Optional.of(tenant), service, decodedPath.split("/"));
	}
	
	/**
	 * @param token
	 * @param remotePath
	 * @param session
	 */
	private void authenticate(final String token, final String remotePath, final Session session) {
		final URI securityUrl = registry.lookup(Naming.SECURITY).orElseThrow(() -> new NoSuchElementException(Naming.SECURITY));
		final Client client = ClientBuilder.newClient();
		final CompletionStage<Response> response = SecurityUtil.authenticate(client, securityUrl, token);
		response.thenApply(res -> res.getStatus() == Status.OK.getStatusCode()).whenComplete((isOk, err) -> {
			if(err != null || !isOk) {
				closeSession(session, new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, ""));
			}
			else {
				openSession(remotePath, session);
			}
			client.close();
		});
	}
	
	/**
	 * @param path
	 * @param session
	 */
	private void openSession(final String path, final Session session) {
		remotes.computeIfPresent(path, (p, remote) -> {
			remote.putSession(session);
			return remote;
		});
	}
	
	/**
	 * @param session
	 * @param reason
	 */
	private void closeSession(final Session session, final CloseReason reason) {
		try {
			session.close(reason);
			session.close();
		}
		catch(Exception ex) {
			LOGGER.log(Level.WARNING, "Session.close", ex);
		}
	}
	
	/**
	 * @param path
	 * @param session
	 * @param closeReason
	 * @throws Exception 
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
		final String remotePath = getRemotePath(tenant, service, path);
		remotes.computeIfPresent(remotePath, (p, remote) -> {
			remote.removeSession(session);
			return null;
			}
		);
	}
}
