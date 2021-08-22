/**
 * 
 */
package epf.gateway.stream;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.util.SystemUtil;
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
	private transient final Map<String, Broadcaster> broadcasters = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, Client> clients = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject
	private transient Logger logger;
	
	/**
	 * 
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI messagingUrl = new URI(SystemUtil.getenv("epf.messaging.url"));
			final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			clients.put("persistence", Client.connectToServer(container, messagingUrl.resolve("persistence")));
		} 
		catch (URISyntaxException | DeploymentException | IOException e) {
			logger.log(Level.SEVERE, "postConstruct", e);
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
				logger.throwing(client.getClass().getName(), "close", e);
			}
		});
		this.clients.clear();
		this.broadcasters.values().parallelStream().forEach(broadcaster -> {
			try {
				broadcaster.close();
			} 
			catch (Exception e) {
				logger.throwing(broadcaster.getClass().getName(), "close", e);
			}
		});
		this.broadcasters.clear();
	}

	/**
	 * @param sink
	 * @param sse
	 */
	@GET
	@Path("{path}")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public void stream(
			@PathParam("path")
			@NotBlank
			final String path, 
			@Context 
			final SseEventSink sink, 
			@Context 
			final Sse sse) {
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
