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
import javax.websocket.Session;
import javax.websocket.SessionException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.client.util.ClientQueue;
import epf.gateway.Registry;
import epf.gateway.security.SecurityUtil;
import epf.naming.Naming;
import epf.util.MapUtil;
import epf.util.StringUtil;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class Server implements HealthCheck {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Server.class.getName());
	
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
	private URI securityUrl;
	
	/**
	 * 
	 */
	@Inject @Readiness
	transient Registry registry;
	
	/**
	 *
	 */
	@Inject
	transient ClientQueue clients;
	
	@PostConstruct
	protected void postConstruct() {
		initialize();
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
	
	private void initialize() {
		if(remotes.isEmpty()) {
			messagingUrl = registry.lookup(Naming.MESSAGING).orElseThrow(() -> new NoSuchElementException(Naming.MESSAGING));
			securityUrl = registry.lookup(Naming.SECURITY).orElseThrow(() -> new NoSuchElementException(Naming.SECURITY));
			final Remote remote = new Remote(messagingUrl.resolve(Remote.urlOf(Optional.empty(), Naming.QUERY)), Remote.pathOf(Optional.empty(), Naming.QUERY));
			try {
				remote.connectToServer();
				remotes.put(remote.getPath(), remote);
			} 
			catch (Exception e) {
				LOGGER.log(Level.SEVERE, "[Server.remotes]", e);
			}
		}
	}

	/**
	 * @param path
	 * @param session
	 * @throws Exception 
	 */
	public void openSession(
    		final String tenant,
    		final String service,
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
				throw new SessionException(CloseReason.CloseCodes.VIOLATED_POLICY.name(), new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build()), session);
			}
		}
		else {
			closeSession(session, new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, ""));
			throw new SessionException(CloseReason.CloseCodes.NORMAL_CLOSURE.name(), new NotFoundException(), session);
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
		final Client client = clients.poll(securityUrl, null);
		final CompletionStage<Response> response = SecurityUtil.authenticate(client, securityUrl, token);
		response.thenApply(res -> res.getStatus() == Status.OK.getStatusCode()).whenComplete((isOk, err) -> {
			if(err != null || !isOk) {
				closeSession(session, new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, ""));
			}
			else {
				putSession(remotePath, session);
			}
			clients.add(securityUrl, client);
		});
	}
	
	private void putSession(final String path, final Session session) {
		remotes.computeIfPresent(path, (p, remote) -> {
			remote.putSession(session);
			return remote;
		});
	}
	
	private void removeSession(final String path, final Session session) {
		remotes.computeIfPresent(path, (p, remote) -> {
			remote.removeSession(session);
			return remote;
			}
		);
	}
	
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
	 * @param tenant
	 * @param service
	 * @param path
	 * @param session
	 * @param closeReason
	 */
	public void closeSession(
    		final String tenant,
    		final String service,
    		final String path,
    		final Session session, 
    		final CloseReason closeReason) throws Exception {
		final String remotePath = getRemotePath(tenant, service, path);
		removeSession(remotePath, session);
	}
	
	/**
	 * @param tenant
	 * @param service
	 * @param path
	 * @return
	 */
	public Optional<Remote> getRemote(final Optional<String> tenant, final String service, final String... path) {
		final String remotePath = Remote.pathOf(tenant, service, path);
		return MapUtil.get(remotes, remotePath);
	}

	@Override
	public HealthCheckResponse call() {
		initialize();
		if(remotes.isEmpty()) {
			return HealthCheckResponse.down("EPF-gateway-messaging");
		}
		return HealthCheckResponse.up("EPF-gateway-messaging");
	}
}
