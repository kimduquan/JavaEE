package epf.gateway.stream;

import java.net.URI;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.Readiness;
import epf.gateway.Registry;
import epf.gateway.messaging.Remote;
import epf.naming.Naming;
import epf.util.logging.LogManager;
import epf.util.websocket.Client;

/**
 * @author PC
 *
 */
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
	@Inject @Readiness
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
			clients.put(Naming.QUERY, Client.connectToServer(messagingUrl.resolve(Remote.urlOf(Optional.empty(), Naming.QUERY))));
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
			@QueryParam("token")
			final String token) throws Exception {
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
}
