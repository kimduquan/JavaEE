/**
 * 
 */
package epf.gateway.stream;

import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.SseBroadcaster;

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
	public Broadcaster(final OutboundSseEvent.Builder builder, final SseBroadcaster broadcaster) {
		this.builder = builder;
		this.broadcaster = broadcaster;
	}
	
	/**
	 * @param message
	 */
	public void broadcast(final String message) {
		broadcaster.broadcast(builder.data(message).build());
	}

	@Override
	public void close() throws Exception {
		broadcaster.close();
	}
	
	public SseBroadcaster getBroadcaster() {
		return broadcaster;
	}
}
