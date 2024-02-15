package epf.gateway.messaging;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import epf.naming.Naming.Messaging.Internal;
import epf.util.logging.LogManager;
import io.smallrye.common.annotation.RunOnVirtualThread;
import javax.inject.Inject;
import javax.websocket.Session;

/**
 * 
 */
@ApplicationScoped
@Readiness
public class Application implements HealthCheck {
	
	private transient static final Logger LOGGER = LogManager.getLogger(Application.class.getName());
	
	/**
	 * 
	 */
	private transient final Map<String, Session> sessions = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject
    @Channel(Internal.MESSAGING_TEXT_IN)
	transient Emitter<Message<Object>> text;
	
	/**
	 * 
	 */
	@Inject
    @Channel(Internal.MESSAGING_BYTES_IN)
	transient Emitter<Message<Object>> bytes;
	
	/**
	 * @param message
	 */
	@Incoming(Internal.MESSAGING_TEXT_OUT)
	@RunOnVirtualThread
	public CompletionStage<Void> onTextMessage(final Message<Object> message) {
		return CompletableFuture.runAsync(() -> {
			sessions.forEach((path, session) -> {
				try {
					session.getBasicRemote().sendText(message.getPayload().toString());
				} 
				catch (Exception e) {
					LOGGER.log(Level.WARNING, "onTextMessage", e);
				}
			});
		});
	}
	
	/**
	 * @param message
	 */
	@Incoming(Internal.MESSAGING_BYTES_OUT)
	@RunOnVirtualThread
	public CompletionStage<Void> onBytesMessage(final Message<Object> message) {
		return CompletableFuture.runAsync(() -> {
			sessions.forEach((path, session) -> {
				try {
					session.getBasicRemote().sendBinary(ByteBuffer.wrap((byte[])message.getPayload()));
				} 
				catch (Exception e) {
					LOGGER.log(Level.WARNING, "onBytesMessage", e);
				}
			});
		});
	}
	
	/**
	 * @param path
	 * @param session
	 */
	public void onOpen(final String path, final Session session) {
		sessions.put(path, session);
	}
	
	/**
	 * @param path
	 * @param session
	 */
	public void onClose(final String path, final Session session) {
		sessions.remove(path);
	}
	
	/**
	 * @param path
	 * @param session
	 */
	public void onError(final String path, final Session session) {
		sessions.remove(path);
	}
	
	/**
	 * @param message
	 */
	public void sendText(final String message) {
		text.send(Message.of(message));
	}
	
	/**
	 * @param message
	 */
	public void sendBinary(final byte[] message) {
		text.send(Message.of(message));
	}

	@Override
	public HealthCheckResponse call() {
		if(text.isCancelled()) {
			return HealthCheckResponse.down(Internal.MESSAGING_TEXT_IN);
		}
		if(bytes.isCancelled()) {
			return HealthCheckResponse.down(Internal.MESSAGING_BYTES_IN);
		}
		return HealthCheckResponse.up("epf-gateway-messaging");
	}
}
