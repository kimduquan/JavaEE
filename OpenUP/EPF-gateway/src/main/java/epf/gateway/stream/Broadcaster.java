/**
 * 
 */
package epf.gateway.stream;

import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.SseBroadcaster;
import epf.util.websocket.Client;

/**
 * @author PC
 *
 */
public class Broadcaster implements AutoCloseable {
	
	/**
	 * 
	 */
	private transient final OutboundSseEvent.Builder builder;
	
	/**
	 * 
	 */
	private transient final SseBroadcaster broadcaster;

	/**
	 * 
	 */
	private transient final Client client;

	/**
	 * 
	 */
	public Broadcaster(final Client client, final OutboundSseEvent.Builder builder, final SseBroadcaster broadcaster) {
		this.client = client;
		this.builder = builder;
		this.broadcaster = broadcaster;
		client.onMessage(this::broadcast);
	}
	
	/**
	 * @param message
	 */
	protected void broadcast(final String message) {
		broadcaster.broadcast(builder.data(message).build());
	}

	@Override
	public void close() throws Exception {
		client.close();
		broadcaster.close();
	}
	
	public SseBroadcaster getBroadcaster() {
		return broadcaster;
	}
}
