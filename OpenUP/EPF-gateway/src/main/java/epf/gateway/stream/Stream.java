package epf.gateway.stream;

import java.net.URI;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.gateway.Registry;
import epf.gateway.security.SecurityUtil;
import epf.naming.Naming;
import epf.util.logging.LogManager;
import epf.util.websocket.Client;
import io.smallrye.common.annotation.Blocking;

/**
 * @author PC
 *
 */
@Blocking
@Path("stream")
@ApplicationScoped
public class Stream {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Stream.class.getName());
	
	/**
	 * 
	 */
	private transient final Map<String, Broadcaster> broadcasters = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, Client> clients = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject
	transient Registry registry;
	
	/**
	 * 
	 */
	@Inject
	transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI messagingUrl = registry.lookup(Naming.MESSAGING).orElseThrow(() -> new NoSuchElementException(Naming.MESSAGING));
			clients.put(Naming.PERSISTENCE, Client.connectToServer(messagingUrl.resolve(Naming.PERSISTENCE)));
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "postConstruct", e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		this.clients.values().parallelStream().forEach(client -> {
			try {
				client.close();
			} 
			catch (Exception e) {
				LOGGER.throwing(client.getClass().getName(), "close", e);
			}
		});
		this.clients.clear();
		this.broadcasters.values().parallelStream().forEach(broadcaster -> {
			try {
				broadcaster.close();
			} 
			catch (Exception e) {
				LOGGER.throwing(broadcaster.getClass().getName(), "close", e);
			}
		});
		this.broadcasters.clear();
	}

	/**
	 * @param sink
	 * @param sse
	 * @throws Exception 
	 */
	@GET
	@Path("{path}")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	@PermitAll
	public void stream(
			@PathParam("path")
			final String path, 
			@Context 
			final SseEventSink sink, 
			@Context 
			final Sse sse,
			@MatrixParam("tid")
			final String tokenId) throws Exception {
		final URI cacheUrl = registry.lookup(Naming.CACHE).orElseThrow(() -> new NoSuchElementException(Naming.CACHE));
		final URI securityUrl = registry.lookup(Naming.SECURITY).orElseThrow(() -> new NoSuchElementException(Naming.SECURITY));
		SecurityUtil.authenticateTokenId(tokenId, cacheUrl, securityUrl).thenAccept(succeed -> {
			if(succeed) {
				clients.computeIfPresent(path, (p, client) -> {
					final Broadcaster broadcaster = broadcasters.computeIfAbsent(
							path, p2 -> {
								final Broadcaster newBroadcaster = new Broadcaster(client, sse);
								executor.submit(newBroadcaster);
								return newBroadcaster;
							});
					broadcaster.register(sink);
					return client;
				});
			}
			else {
				throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
			}
		});
	}
}
