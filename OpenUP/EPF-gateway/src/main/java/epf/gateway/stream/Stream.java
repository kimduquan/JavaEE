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
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.gateway.security.SecurityUtil;
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
	@ConfigProperty(name = Naming.Messaging.MESSAGING_URL)
	String messagingUrl;
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.Cache.CACHE_URL)
	String cacheUrl;
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.Security.SECURITY_URL)
	String securityUrl;
	
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
			final URI url = new URI(messagingUrl);
			clients.put(Naming.PERSISTENCE, Client.connectToServer(url.resolve(Naming.PERSISTENCE)));
		} 
		catch (URISyntaxException | DeploymentException | IOException e) {
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
	public void stream(
			@PathParam("path")
			final String path, 
			@Context 
			final SseEventSink sink, 
			@Context 
			final Sse sse,
			@MatrixParam("tid")
			final String tokenId) throws Exception {
		SecurityUtil.authenticateTokenId(tokenId, new URI(cacheUrl), new URI(securityUrl)).thenAccept(succeed -> {
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
