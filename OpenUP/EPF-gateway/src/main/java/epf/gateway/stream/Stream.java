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
import javax.websocket.DeploymentException;
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
import epf.gateway.security.SecurityUtil;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;
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
			final URI messagingUrl = ConfigUtil.getURI(Naming.Messaging.MESSAGING_URL);
			clients.put("persistence", Client.connectToServer(messagingUrl.resolve(Naming.PERSISTENCE)));
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
	 * @throws Exception 
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
			final Sse sse,
			@MatrixParam("tid")
			@NotBlank
			final String tokenId) throws Exception {
		if(SecurityUtil.authenticateTokenId(tokenId)) {
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
	}
}
