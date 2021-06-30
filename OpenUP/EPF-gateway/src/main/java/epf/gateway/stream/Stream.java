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
	@PostConstruct
	protected void postConstruct() {
		try {
			final URI messagingUrl = new URI(System.getenv("epf.messaging.url"));
			final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			clients.put("persistence", Client.connectToServer(container, messagingUrl.resolve("persistence")));
		} 
		catch (URISyntaxException | DeploymentException | IOException e) {
			logger.log(Level.SEVERE, "postConstruct", e);
		}
	}
	
	protected Broadcaster newBroadcaster(final Sse sse, final Client client) {
		final Broadcaster broadcaster = new Broadcaster(sse.newEventBuilder(), sse.newBroadcaster());
		client.onMessage(broadcaster::broadcast);
		return broadcaster;
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
					path, p2 -> newBroadcaster(sse, client)
					);
			broadcaster.getBroadcaster().register(sink);
			return client;
		});
	}
}
