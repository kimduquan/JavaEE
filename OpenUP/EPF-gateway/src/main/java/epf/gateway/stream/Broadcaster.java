/**
 * 
 */
package epf.gateway.stream;

import java.util.logging.Logger;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import epf.util.concurrent.ObjectQueue;
import epf.util.logging.Logging;
import epf.util.websocket.Client;

/**
 * @author PC
 *
 */
public class Broadcaster extends ObjectQueue<String> {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(Broadcaster.class.getName());
	
	/**
	 * 
	 */
	private transient final OutboundSseEvent.Builder builder;
	
	/**
	 * 
	 */
	private transient final SseBroadcaster broadcaster;

	/**
	 * @param builder
	 * @param broadcaster
	 */
	public Broadcaster(final Client client, final Sse sse) {
		this.builder = sse.newEventBuilder();
		this.broadcaster = sse.newBroadcaster();
		client.onMessage(this::add);
	}

	@Override
	public void close() {
		super.close();
		broadcaster.close();
	}

	@Override
	public void accept(final String message) {
		try {
			broadcaster.broadcast(builder.data(message).build());
		}
		catch(Exception ex) {
			LOGGER.throwing(getClass().getName(), "accept", ex);
		}
	}
	
	public void register(final SseEventSink sink) {
		broadcaster.register(sink);
	}
}
